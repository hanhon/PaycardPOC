package com.qualicom.emvpaycard.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.utils.ByteString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kangelov on 2015-10-22.
 */
public class FCIProprietaryTemplate extends EmvData {

    /**
     * Short File Identifier of the directory elementary file, value 88
     */
    @Expose
    private String sfiFileName;
    private static final String TAG_SFI_FILENAME = "88";

    /**
     * Language preference for the card, value 5F2D
     */
    @Expose
    private String languagePreference;
    private static final String TAG_LANGUAGE_PREFERENCE = "5F2D";

    /**
     * Issuer code table index, value 9F11
     */
    @Expose
    private String issuerCodeTableIndex;
    private static final String TAG_ISSUER_CODE_TABLE_INDEX = "9F11";

    /**
     * Issuer discretionary data, value BF0C
     */
    @Expose
    private List<FCIDirectoryEntry> issuerDiscretionaryData;
    private static final String TAG_ISSUER_DISCRETIONARY_DATA = "BF0C";
    private static final String TAG_FCI_DISCRETIONARY_ENTRIES = "61";


    /**
     * Application label, value 50
     */
    @Expose
    private String applicationLabel;
    private static final String TAG_APPLICATION_LABEL="50";

    /**
     * Application priority indicator, value 87
     */
    @Expose
    private String applicationPriorityIndicator;
    private static final String TAG_APPLICATION_PRIORITY_INDICATOR="87";

    /**
     * Processing Objects Data Object List, value 9F38
     */
    @Expose
    private String pdol;
    private static final String TAG_PDOL = "9F38";

    /**
     * Application Preferred Name, value 9F12
     */
    @Expose
    private String applicationPreferredName;
    private static final String TAG_APPLICATION_PREFERRED_NAME = "9F12";

    public FCIProprietaryTemplate(byte[] response) {
        super(response);
        if (response != null) {
            Map<String, byte[]> parsedResponse = new HashMap<String, byte[]>();
            parseTLVResponse(response, new String[]{
                    TAG_APPLICATION_LABEL,
                    TAG_APPLICATION_PREFERRED_NAME,
                    TAG_APPLICATION_PRIORITY_INDICATOR,
                    TAG_ISSUER_CODE_TABLE_INDEX,
                    TAG_ISSUER_DISCRETIONARY_DATA,
                    TAG_LANGUAGE_PREFERENCE,
                    TAG_PDOL,
                    TAG_SFI_FILENAME}, parsedResponse);

            this.applicationLabel = ByteString.byteArrayToUTF8String(parsedResponse.get(TAG_APPLICATION_LABEL));
            this.applicationPreferredName = ByteString.byteArrayToUTF8String(parsedResponse.get(TAG_APPLICATION_PREFERRED_NAME));
            this.applicationPriorityIndicator = ByteString.byteArrayToHexString(parsedResponse.get(TAG_APPLICATION_PRIORITY_INDICATOR));
            this.issuerCodeTableIndex = ByteString.byteArrayToHexString(parsedResponse.get(TAG_ISSUER_CODE_TABLE_INDEX));
            this.issuerDiscretionaryData = new ArrayList<FCIDirectoryEntry>();
            List<byte[]> parsedList = new ArrayList<byte[]>();
            parseTLVList(response, TAG_FCI_DISCRETIONARY_ENTRIES, parsedList);
            for(byte[] entry : parsedList) {
                this.issuerDiscretionaryData.add(new FCIDirectoryEntry(entry));
            }
            this.languagePreference = ByteString.byteArrayToUTF8String(parsedResponse.get(TAG_LANGUAGE_PREFERENCE));
            this.pdol = ByteString.byteArrayToHexString(parsedResponse.get(TAG_PDOL));
            this.sfiFileName = ByteString.byteArrayToUTF8String(parsedResponse.get(TAG_SFI_FILENAME));
        }
    }

    public String getApplicationLabel() {
        return applicationLabel;
    }

    public String getApplicationPreferredName() {
        return applicationPreferredName;
    }

    public String getApplicationPriorityIndicator() {
        return applicationPriorityIndicator;
    }

    public String getIssuerCodeTableIndex() {
        return issuerCodeTableIndex;
    }

    public List<FCIDirectoryEntry> getIssuerDiscretionaryData() {
        return issuerDiscretionaryData;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public String getPdol() {
        return pdol;
    }

    public String getSfiFileName() {
        return sfiFileName;
    }

}