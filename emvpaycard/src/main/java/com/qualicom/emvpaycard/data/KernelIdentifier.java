package com.qualicom.emvpaycard.data;

import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.enums.KernelEnum;
import com.qualicom.emvpaycard.enums.KernelTypeEnum;

/**
 * Created by kangelov on 2015-10-28.
 */
public class KernelIdentifier {

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

}