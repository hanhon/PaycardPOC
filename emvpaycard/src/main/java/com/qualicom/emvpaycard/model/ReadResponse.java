package com.qualicom.emvpaycard.model;

import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kangelov on 2015-10-27.
 */
public class ReadResponse extends EmvResponse {

    @Expose
    private ApplicationData applicationData;
    private final static String TAG_APPLICATION_DATA="70";

    public ReadResponse(byte[] response) {
        super(response);
        if (getData() != null && getData().length > 0 && isSuccessfulResponse()) {
            Map<String, byte[]> parsedResponse = new HashMap<String, byte[]>();
            parseTLVResponse(getData(), new String[]{TAG_APPLICATION_DATA}, parsedResponse);
            if (parsedResponse.containsKey(TAG_APPLICATION_DATA))
                this.applicationData = new ApplicationData(parsedResponse.get(TAG_APPLICATION_DATA));
        }
    }

    public ApplicationData getApplicationData() {
        return applicationData;
    }
}
