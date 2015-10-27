package com.qualicom.emvpaycard.model;

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

    class ApplicationPriorityIndicator implements Comparable<ApplicationPriorityIndicator> {

        @Expose
        private byte applicationPriorityIndicator = 0x00;

        public ApplicationPriorityIndicator(byte[] applicationPriorityIndicator) {
            if (applicationPriorityIndicator != null && applicationPriorityIndicator.length > 0) {
                this.applicationPriorityIndicator = applicationPriorityIndicator[0];
            }
        }

        public boolean hasAssignedPriority() {
            return getApplicationPriority() > 0x00;
        }

        public byte getApplicationPriorityIndicator() {
            return applicationPriorityIndicator;
        }

        public int getApplicationPriority() {
            return this.applicationPriorityIndicator & 0x0f; //take out all but bits 1-4. All others are reserved for future use.
        }

        @Override
        public String toString() {
            return ByteString.byteToHexString(this.applicationPriorityIndicator);
        }

        @Override
        public int compareTo(ApplicationPriorityIndicator another) {
            return Integer.compare(getApplicationPriority(), another.getApplicationPriority());
        }
    }

    class KernelIdentifier {

        @Expose
        private byte kernelIdentifier = 0;

        public KernelIdentifier(byte[] kernelIdentifier) {
            if (kernelIdentifier != null && kernelIdentifier.length > 0) {
                this.kernelIdentifier = kernelIdentifier[0];
            }
        }

        public KernelTypeEnum getKernelType() {
            for (KernelTypeEnum kernel : KernelTypeEnum.values()) {
                if ((kernelIdentifier & (byte)0xC0) == kernel.getKernelType())
                    return kernel;
            }
            return null;
        }

        public KernelEnum getKernel() {
            return KernelEnum.parseValue(kernelIdentifier & 0x3F);
        }

        public boolean isDefaultKernelForADF() {
            return getKernel() == KernelEnum.DEFAULT;
        }

        public byte getKernelIdentifier() {
            return kernelIdentifier;
        }

        @Override
        public String toString() {
            return ByteString.byteToHexString(this.kernelIdentifier);
        }

    }


}
