package com.qualicom.emvpaycard.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qualicom.emvpaycard.utils.ByteString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by kangelov on 2015-10-18.
 */
public abstract class EmvData {

    private final static Gson gson = new GsonBuilder().
            excludeFieldsWithoutExposeAnnotation().
            create();

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
            if (tagSet.contains(prefix.toString())) {
                int length = response[parsePos++];
                byte[] data = Arrays.copyOfRange(response, parsePos, parsePos + length);
                parsedResponse.put(prefix.toString(), data);
                startPos = parsePos = parsePos + length;
                prefix = new StringBuffer();
            } else {
                if (parsePos >= response.length) {
                    parsePos = ++startPos;
                    prefix = new StringBuffer();
                }
            }
        }
    }

    public String toString() {
        return gson.toJson(this);
    }

}
