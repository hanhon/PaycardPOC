package com.qualicom.emvpaycard.enums;

/**
 * These are the identifiers for the different payment kernels used to process payments. These
 * are the payment "orchestrators", decoupled from the card schemes they serve. A chipcard can
 * request a different kernel for its payment, or it can rely on the pinpad to use a default.
 *
 *
 * Created by kangelov on 2015-10-27.
 */
public enum KernelEnum {

    DEFAULT(0), //DEFAULT or OTHER - this is the "no value" constant.
    TYPE_1(1), //Some VISA and some JCB cards
    TYPE_2(2), //Mastercard
    TYPE_3(3), //Visa
    TYPE_4(4), //American Express
    TYPE_5(5), //JCB
    TYPE_6(6), //Discover / Diner's Club
    TYPE_7(7); //UnionPay


    public int getKernelId() {
        return kernelId;
    }

    private final int kernelId;

    private KernelEnum(int kernelId) {
        this.kernelId = kernelId;
    }

    public static KernelEnum parseValue(int value) {
        for (KernelEnum kernel : KernelEnum.values()) {
            if (kernel.kernelId == value)
                return kernel;
        }
        return null;
    }

}
