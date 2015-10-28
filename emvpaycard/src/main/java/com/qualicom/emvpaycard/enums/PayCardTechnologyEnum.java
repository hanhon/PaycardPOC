package com.qualicom.emvpaycard.enums;

/**
 * Created by kangelov on 2015-10-14.
 */
public enum PayCardTechnologyEnum {

    ISO_DEP("android.nfc.tech.IsoDep"),
    NFC_A("android.nfc.tech.NfcA"),
    NFC_B("android.nfc.tech.NfcB");
    private final String name;

    private PayCardTechnologyEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
