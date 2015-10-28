package com.qualicom.emvpaycard.data;

import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.utils.ByteString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kangelov on 2015-10-23.
 */
public class FCIIssuerDiscretionaryData extends EmvData {

    @Expose
    private List<FCIApplicationTemplate> applicationTemplateData;
    private static final String TAG_FCI_DISCRETIONARY_ENTRIES = "61";

    @Expose
    private String logData;
    private static final String TAG_FCI_LOG_ENTRY = "9F4D";

    @Expose
    private String issuerCountryCode;
    private static final String TAG_FCI_ISSUER_COUNTRY_CODE = "5F56";


    public FCIIssuerDiscretionaryData(byte[] response) {
        super(response);
        if (response != null) {
            List<byte[]> parsedList = new ArrayList<byte[]>();
            parseTLVList(response, TAG_FCI_DISCRETIONARY_ENTRIES, parsedList);
            if (!parsedList.isEmpty()) {
                this.applicationTemplateData = new ArrayList<FCIApplicationTemplate>();
                for(byte[] entry : parsedList) {
                    this.applicationTemplateData.add(new FCIApplicationTemplate(entry));
                }
            }
            Map<String, byte[]> parsedResponse = new HashMap<String, byte[]>();
            parseTLVResponse(response, new String[]{
                    TAG_FCI_ISSUER_COUNTRY_CODE,
                    TAG_FCI_LOG_ENTRY}, parsedResponse);
            this.logData = ByteString.byteArrayToHexString(parsedResponse.get(TAG_FCI_LOG_ENTRY));
            this.issuerCountryCode = ByteString.byteArrayToUTF8String(parsedResponse.get(TAG_FCI_ISSUER_COUNTRY_CODE));
        }
    }

    public List<FCIApplicationTemplate> getApplicationTemplateData() {
        return applicationTemplateData;
    }

    public String getIssuerCountryCode() {
        return issuerCountryCode;
    }

    public String getLogData() {
        return logData;
    }
}
