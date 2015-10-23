package com.qualicom.emvpaycard.utils;

import android.nfc.Tag;

import com.qualicom.emvpaycard.enums.EmvPayCardTechnology;

/**
 * Created by kangelov on 2015-10-14.
 */
public class EmvPayCardUtils {

    private EmvPayCardUtils() {}

    public static final boolean hasEmvPayCardTechnology(Tag tag, EmvPayCardTechnology tech) {
        if (tag == null) return false;
        for(String supportedTech : tag.getTechList()) {
            if (supportedTech.contains(tech.getName()))
                return true;
        }
        return false;
    }

    public static final boolean isValidEmvPayCard(Tag tag) {
        return hasEmvPayCardTechnology(tag, EmvPayCardTechnology.ISO_DEP) &&
                (isTypeAPayCard(tag) || isTypeBPayCard(tag));
    }

    public static final boolean isTypeAPayCard(Tag tag) {
        return hasEmvPayCardTechnology(tag, EmvPayCardTechnology.NFC_A);
    }

    public static final boolean isTypeBPayCard(Tag tag) {
        return hasEmvPayCardTechnology(tag, EmvPayCardTechnology.NFC_B);
    }

}
