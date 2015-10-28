package com.qualicom.emvpaycard.enums;

import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.utils.ByteString;

/**
 * Created by kangelov on 2015-10-23.
 */
public enum KernelTypeEnum {

    INTERNATIONAL_KERNEL((byte)0x00, "International Kernel"),
    DOMESTIC_KERNEL((byte)0x80, "Domestic Kernel"),
    PROPRIETARY_DOMESTIC_KERNEL((byte)0xC0, "Domestic Kernel with Proprietary Kernel Identifier");

    @Expose
    private final byte kernelType;

    @Expose
    private final String description;

    private KernelTypeEnum(byte kernelType, String description) {
        this.kernelType = kernelType;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public byte getKernelType() {
        return kernelType;
    }

}
