package com.qualicom.emvpaycard.data;

import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kangelov on 2015-10-18.
 */
public class SelectResponse extends EmvResponse {

    public final static String RECORD_NOT_EXIST_STATUS_CODE="6A";

    /*
    File Control Information field, field name 6F
     */
    @Expose
    private FCITemplate fciTemplate;
    private final static String TAG_FCI_TEMPLATE = "6F";

    public SelectResponse(byte[] response) {
        super(response);
        if (getData() != null && getData().length > 0 && isRecordFound()) {
            //Take the last 2 bytes of the response as a response code.
            Map<String, byte[]> parsedResponse = new HashMap<String, byte[]>();
            parseTLVResponse(getData(), new String[]{TAG_FCI_TEMPLATE}, parsedResponse);
            if (parsedResponse.containsKey(TAG_FCI_TEMPLATE))
                //We don't trust the top-level tag.
                this.fciTemplate = new FCITemplate(getData());
        }
    }

    public FCITemplate getFciTemplate() {
        return fciTemplate;
    }

    public boolean isNonexistentRecordResponseCode() {
        return RECORD_NOT_EXIST_STATUS_CODE.equals(getProcessingStatus());
    }

    public boolean isRecordFound() {
        return isSuccessfulResponse();
    }
}
