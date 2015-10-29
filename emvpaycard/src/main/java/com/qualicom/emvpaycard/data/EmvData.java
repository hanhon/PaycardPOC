package com.qualicom.emvpaycard.data;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.EmvPayCardException;
import com.qualicom.emvpaycard.utils.ByteString;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by kangelov on 2015-10-18.
 */
public abstract class EmvData {

    protected static GsonBuilder gsonBuilder = new GsonBuilder().
            excludeFieldsWithoutExposeAnnotation().
            setPrettyPrinting().
            registerTypeAdapter(byte[].class, new JsonSerializer<byte[]>() {

                @Override
                public JsonElement serialize ( byte[] src, Type typeOfSrc, JsonSerializationContext context)
                {
                    return new JsonPrimitive(ByteString.printByteStream(src));
                }
            }).
            registerTypeAdapter(byte.class, new JsonSerializer<Byte>() {

                @Override
                public JsonElement serialize(Byte src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(ByteString.byteToHexString(src));
                }
            });

    @Expose
    private final byte[] raw;

    public EmvData(byte[] raw) {
        this.raw = raw;
    }

    public byte[] getRaw() {
        return raw;
    }

    /*
        This is a rather naive implementation to locate and parse a list of known tags of arbitrary length along with their values
        in a byte array with possibly other (irrelevant and/or unparsable) information. The fields are assumed to be in TLV format
        (Known tag of arbitrary length, followed by a 1-byte length field for the value to follow, followed by the value itself)

        Algorithm is O(n^2), but can be optimized to O(n+k) with the (modified) Knuth Morris Pratt algorithm. I am not going to do
        so presently as the chip does not return a lot of information so that this optimization would save anything significant.
         */
    static void parseTLVResponse(byte[] response, String[] expectedTags, Map<String,byte[]> parsedResponse) {
        HashSet<String> tagSet = new HashSet<String>(Arrays.asList(expectedTags));
        int startPos = 0;
        int parsePos = 0;
        StringBuffer prefix = new StringBuffer();
        while (startPos < response.length) {
            prefix.append(ByteString.byteToHexString(response[parsePos++]));
            if (parsePos < response.length && tagSet.contains(prefix.toString())) {
                int length = 0xff & response[parsePos++];
                byte[] data = Arrays.copyOfRange(response, parsePos, Math.min(response.length, parsePos + length));
                parsedResponse.put(prefix.toString(), data);
                startPos = parsePos = Math.min(response.length, parsePos + length);
                prefix = new StringBuffer();
            } else {
                if (parsePos >= response.length) {
                    parsePos = ++startPos;
                    prefix = new StringBuffer();
                }
            }
        }
    }

    static void parseTLVList(byte[] response, String tag, List<byte[]> parsedList) {
        int startPos = 0;
        int parsePos = 0;
        StringBuffer prefix = new StringBuffer();
        while (startPos < response.length) {
            prefix.append(ByteString.byteToHexString(response[parsePos++]));
            if (parsePos < response.length && tag.contains(prefix.toString())) {
                int length = 0xff & response[parsePos++];
                byte[] data = Arrays.copyOfRange(response, parsePos, Math.min(response.length, parsePos + length));
                parsedList.add(data);
                startPos = parsePos = Math.min(response.length, parsePos + length);
                prefix = new StringBuffer();
            } else {
                if (parsePos >= response.length) {
                    parsePos = ++startPos;
                    prefix = new StringBuffer();
                }
            }
        }
    }

    protected byte getMaskedValue(int byteIndex, int mask) {
        if (getRaw() != null && getRaw().length > byteIndex)
            return (byte)(0xff & getRaw()[byteIndex] & mask);
        return 0;
    }

    public String toString() {
        return gsonBuilder.create().toJson(this);
    }

}
