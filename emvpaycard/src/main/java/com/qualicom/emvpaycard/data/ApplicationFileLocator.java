package com.qualicom.emvpaycard.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.qualicom.emvpaycard.EmvPayCardException;

import java.lang.reflect.Type;

/**
 * Created by kangelov on 2015-10-27.
 */
public class ApplicationFileLocator extends EmvData {

    static {
        gsonBuilder = gsonBuilder.registerTypeAdapter(ApplicationFileLocator.class, new JsonSerializer<ApplicationFileLocator>() {
            @Override
            public JsonElement serialize(ApplicationFileLocator src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject object = new JsonObject();
                object.add("raw", context.serialize(src.getRaw()));
                if (src.getRaw() != null) {
                    object.add("shortFileIdentifier", context.serialize(src.getShortFileIdentifier()));
                    object.add("firstRecordNum", context.serialize(src.getFirstRecordNum()));
                    object.add("lastRecordNum", context.serialize(src.getLastRecordNum()));
                    object.add("numOfflineRecords", context.serialize(src.getNumRecordsForOfflineDataAuthentication()));
                }
                return object;
            }
        });
    }

    public ApplicationFileLocator(byte[] response) throws EmvPayCardException {
        super(response);
        if (response != null && response.length > 0 && response.length % 4 != 0) //AFL is always non-empty and a multiple of 4 bytes.
            throw new EmvPayCardException("Invalid Application File Locator passed.");

    }

    public byte getShortFileIdentifier() {
        return (byte)(getMaskedValue(0, 0xF8) >> 3);
    }

    public byte getFirstRecordNum() {
        return (byte)(getMaskedValue(1, 0xFF));
    }

    public byte getLastRecordNum() {
        return (byte)(getMaskedValue(2, 0xFF));
    }

    public int getNumRecordsForOfflineDataAuthentication() {
        return (byte)(getMaskedValue(3, 0xFF));
    }

}
