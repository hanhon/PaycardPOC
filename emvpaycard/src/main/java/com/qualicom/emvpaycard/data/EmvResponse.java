package com.qualicom.emvpaycard.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.enums.StatusClassifierEnum;
import com.qualicom.emvpaycard.enums.StatusWordEnum;
import com.qualicom.emvpaycard.utils.ByteString;

import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by kangelov on 2015-10-18.
 */
public class EmvResponse extends EmvData {

    static {
        gsonBuilder = gsonBuilder.registerTypeAdapter(StatusClassifierEnum.class, new JsonSerializer<StatusClassifierEnum>() {
            @Override
            public JsonElement serialize(StatusClassifierEnum src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject object = new JsonObject();
                object.add("hasSuccess",context.serialize(src.hasSuccess()));
                object.add("hasWarning",context.serialize(src.hasWarning()));
                object.add("hasError", context.serialize(src.hasError()));
                object.add("message",context.serialize(src.getMessage()));
                return object;
            }
        }).registerTypeAdapter(StatusWordEnum.class, new JsonSerializer<StatusWordEnum>() {
            @Override
            public JsonElement serialize(StatusWordEnum src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject object = new JsonObject();
                object.add("message", context.serialize(src.getMessage()));
                object.add("statusClassifier", context.serialize(src.getStatusClassifier()));
                return object;
            }
        });
    }


    public final static String SUCCESS_STATUS_CODE="90";
    public final static String SUCCESS_QUALIFIER_CODE = "00";

    /**
     * Contains the raw data portion of the raw response.
     */
    private byte[] data;

    /**
     * Contains the processing status byte of the raw response.
     * Always store unsigned bytes as strings to prevent Java from tampering with them.
     */
    @Expose
    private String processingStatus;

    /**
     * Contains the processing qualifier byte of the raw response.
     * Always store unsigned bytes as strings to prevent Java from tampering with them.
     */
    @Expose
    private String processingQualifier;

    @Expose
    private StatusWordEnum statusWordEnum;

    public EmvResponse(byte[] raw) {
        super(raw);
        if (raw != null) {
            this.data = Arrays.copyOfRange(raw, 0, raw.length - 2);
            this.processingStatus = ByteString.byteToHexString(raw[raw.length - 2]);
            this.processingQualifier = ByteString.byteToHexString(raw[raw.length - 1]);
            this.statusWordEnum = StatusWordEnum.getStatusWordEnum(this.processingStatus, this.processingQualifier);
        }
    }

    public byte[] getData() {
        return data;
    }

    protected byte[] stripTopLevelTag(String tag) {
        return Arrays.copyOfRange(getData(), tag.length()+1,getData().length);
    }

    public String getProcessingQualifier() {
        return processingQualifier;
    }

    public String getProcessingStatus() {
        return processingStatus;
    }

    public String getStatusWord() {
        return getProcessingStatus() + getProcessingQualifier();
    }

    public StatusWordEnum getStatusWordEnum() {
        return statusWordEnum;
    }

    public String getStatusMessage() {
        if (getStatusWordEnum() != null)
            return getStatusWordEnum().getMessage(getProcessingStatus(), getProcessingQualifier());
        return null;
    }

    public boolean isSuccessfulResponse() {
        if (getStatusWordEnum() != null && getStatusWordEnum().getStatusClassifier() != null)
            return this.getStatusWordEnum().getStatusClassifier().hasSuccess();
        return false;
    }


}
