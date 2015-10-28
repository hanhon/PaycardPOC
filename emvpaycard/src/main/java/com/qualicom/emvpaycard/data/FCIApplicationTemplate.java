package com.qualicom.emvpaycard.data;

import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.enums.KernelEnum;
import com.qualicom.emvpaycard.enums.KernelTypeEnum;
import com.qualicom.emvpaycard.utils.ByteString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kangelov on 2015-10-23.
 */
public class FCIApplicationTemplate extends EmvData {

    @Expose
    private String adfName;
    private static final String TAG_ADF_NAME = "4F";

    @Expose
    private String applicationLabel;
    private static final String TAG_APPLICATION_LABEL = "50";

    @Expose
    private ApplicationPriorityIndicator applicationPriorityIndicator;
    private static final String TAG_APPLICATION_PRIORITY_INDICATOR="87";

    @Expose
    private KernelIdentifier kernelIdentifier;
    private static final String TAG_KERNEL_IDENTIFIER = "9F2A";

    @Expose
    private String extendedSelection;
    private static final String TAG_EXTENDED_SELECTION = "9F29";


    public FCIApplicationTemplate(byte[] response) {
        super(response);
        if (response != null) {
            Map<String, byte[]> parsedResponse = new HashMap<String, byte[]>();
            parseTLVResponse(response, new String[] {
                    TAG_ADF_NAME,
                    TAG_APPLICATION_LABEL,
                    TAG_APPLICATION_PRIORITY_INDICATOR,
                    TAG_KERNEL_IDENTIFIER,
                    TAG_EXTENDED_SELECTION }, parsedResponse);
            this.adfName = ByteString.byteArrayToHexString(parsedResponse.get(TAG_ADF_NAME));
            this.applicationLabel = ByteString.byteArrayToUTF8String(parsedResponse.get(TAG_APPLICATION_LABEL));
            this.applicationPriorityIndicator = new ApplicationPriorityIndicator(parsedResponse.get(TAG_APPLICATION_PRIORITY_INDICATOR));
            this.kernelIdentifier = new KernelIdentifier(parsedResponse.get(TAG_KERNEL_IDENTIFIER));
            this.extendedSelection = ByteString.byteArrayToHexString(parsedResponse.get(TAG_EXTENDED_SELECTION));
        }
    }

    public String getAdfName() {
        return adfName;
    }

    public String getApplicationLabel() {
        return applicationLabel;
    }

    public ApplicationPriorityIndicator getApplicationPriorityIndicator() {
        return applicationPriorityIndicator;
    }

    public String getExtendedSelection() {
        return extendedSelection;
    }

    public KernelIdentifier getKernelIdentifier() {
        return kernelIdentifier;
    }

}
