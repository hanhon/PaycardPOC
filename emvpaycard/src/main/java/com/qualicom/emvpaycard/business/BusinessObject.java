package com.qualicom.emvpaycard.business;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.qualicom.emvpaycard.utils.ByteString;

import java.lang.reflect.Type;

/**
 * Created by kangelov on 2015-10-28.
 */
public class BusinessObject {

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

    public String toString() {
        return gsonBuilder.create().toJson(this);
    }

}
