package com.qualicom.emvpaycard.enums;

/**
 * Created by kangelov on 2015-10-14.
 */
public enum EmvPayCardTechnology {

    ISO_DEP("android.nfc.tech.IsoDep"),
    NFC_A("android.nfc.tech.NfcA"),
    NFC_B("android.nfc.tech.NfcB");
    private final String name;

    private EmvPayCardTechnology(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getName();
    }

}
