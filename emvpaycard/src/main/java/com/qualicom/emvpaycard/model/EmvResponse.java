package com.qualicom.emvpaycard.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.utils.ByteString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by kangelov on 2015-10-18.
 */
public class EmvResponse extends EmvData {

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

    public EmvResponse(byte[] raw) {
        super(raw);
        if (raw != null) {
            this.data = Arrays.copyOfRange(raw, 0, raw.length - 2);
            this.processingStatus = ByteString.byteToHexString(raw[raw.length - 2]);
            this.processingQualifier = ByteString.byteToHexString(raw[raw.length - 1]);
        }
    }

    public byte[] getData() {
        return data;
    }

    public String getProcessingQualifier() {
        return processingQualifier;
    }

    public String getProcessingStatus() {
        return processingStatus;
    }

    public boolean isSuccessfulResponse() {
        return SUCCESS_STATUS_CODE.equals(getProcessingStatus()) && SUCCESS_QUALIFIER_CODE.equalsIgnoreCase(getProcessingQualifier());
    }
}
