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
public class ApplicationInterchangeProfile extends EmvData {

    static {
        gsonBuilder = gsonBuilder.registerTypeAdapter(ApplicationInterchangeProfile.class, new JsonSerializer<ApplicationInterchangeProfile>() {
            @Override
            public JsonElement serialize(ApplicationInterchangeProfile src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject object = new JsonObject();
                object.add("raw", context.serialize(src.getRaw()));
                if (src.getRaw() != null) {
                    object.add("isStaticDataAuthenticationSupported", context.serialize(src.isStaticDataAuthenticationSupported()));
                    object.add("isDynamicDataAuthenticationSupported", context.serialize(src.isDynamicDataAuthenticationSupported()));
                    object.add("isCodedDataAuthenticationSupported", context.serialize(src.isCodedDataAuthenticationSupported()));
                    object.add("isCardholderVerificationSupported", context.serialize(src.isCardholderVerificationSupported()));
                    object.add("isTerminalRiskManagementRequired", context.serialize(src.isTerminalRiskManagementRequired()));
                    object.add("isIssuerAuthenticationSupported", context.serialize(src.isIssuerAuthenticationSupported()));
                }
                return object;
            }
        });
    }


    public ApplicationInterchangeProfile(byte[] response) throws EmvPayCardException {
        super(response);
        if (response != null && response.length != 2)
            throw new EmvPayCardException("Invalid Application Interchange Profile passed.");
    }

    public boolean isStaticDataAuthenticationSupported() {
        return getMaskedValue(0, (byte) 0x40) > 0;
    }

    public boolean isDynamicDataAuthenticationSupported() {
        return getMaskedValue(0, (byte) 0x20) > 0;
    }

    public boolean isCardholderVerificationSupported() {
        return getMaskedValue(0, (byte) 0x10) > 0;
    }

    public boolean isTerminalRiskManagementRequired() {
        return getMaskedValue(0, (byte) 0x08) > 0;
    }

    public boolean isIssuerAuthenticationSupported() {
        return getMaskedValue(0, (byte) 0x04) > 0;
    }

    public boolean isCodedDataAuthenticationSupported() {
        return getMaskedValue(0, (byte) 0x01) > 0;
    }

}
