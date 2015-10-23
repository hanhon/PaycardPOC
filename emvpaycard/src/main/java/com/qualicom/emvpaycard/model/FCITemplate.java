package com.qualicom.emvpaycard.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.utils.ByteString;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * File Control Information template.
 *
 * Created by kangelov on 2015-10-18.
 */
public class FCITemplate extends EmvData {

    /**
     * Dedicated File Name, field name 84.
     */
    @Expose
    private final String dfName;
    private final static String TAG_DF_NAME = "84";

    /**
     * File Control Information Proprietary information, value A5
     */
    @Expose
    private final FCIProprietaryTemplate fciProprietaryTemplate;
    private final static String TAG_FCI_PROPRIETARY_TEMPLATE = "A5";

    public FCITemplate(byte[] response) {
        super(response);
        if (response != null) {
            Map<String, byte[]> parsedResponse = new HashMap<String, byte[]>();
            parseTLVResponse(response, new String[]{TAG_DF_NAME, TAG_FCI_PROPRIETARY_TEMPLATE}, parsedResponse);
            this.dfName = ByteString.byteArrayToHexString(parsedResponse.get(TAG_DF_NAME));
            this.fciProprietaryTemplate = new FCIProprietaryTemplate(parsedResponse.get(TAG_FCI_PROPRIETARY_TEMPLATE));
        } else {
            this.dfName = null;
            this.fciProprietaryTemplate = null;
        }
    }

    public String getDfName() {
        return dfName;
    }

    public FCIProprietaryTemplate getFciProprietaryTemplate() {
        return fciProprietaryTemplate;
    }

}


