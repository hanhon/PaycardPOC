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
public abstract class EmvResponse extends EmvData {

    /**
     * Contains the raw data portion of the raw response.
     */
    private final byte[] data;

    /**
     * Contains the processing status byte of the raw response.
     * Always store unsigned bytes as strings to prevent Java from tampering with them.
     */
    @Expose
    private final String processingStatus;

    /**
     * Contains the processing qualifier byte of the raw response.
     * Always store unsigned bytes as strings to prevent Java from tampering with them.
     */
    @Expose
    private final String processingQualifier;

    public EmvResponse(byte[] raw) {
        super(raw);
        if (raw != null) {
            this.data = Arrays.copyOfRange(raw, 0, raw.length - 2);
            this.processingStatus = ByteString.byteToHexString(raw[raw.length - 2]);
            this.processingQualifier = ByteString.byteToHexString(raw[raw.length - 1]);
        } else {
            this.data = null;
            this.processingQualifier = null;
            this.processingStatus = null;
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
}
