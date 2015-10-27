package com.qualicom.emvpaycard.enums;

import android.text.TextUtils;

import com.qualicom.emvpaycard.utils.ByteString;

import java.util.regex.Pattern;

/**
 * Credits: https://github.com/devnied/EMV-NFC-Paycard-Enrollment/blob/master/library/src/main/java/com/github/devnied/emvnfccard/enums/EmvCardScheme.java
 *
 * Created by kangelov on 2015-10-27.
 */
public enum CardSchemeEnum {

    VISA("VISA", "^4[0-9]{6,}$", KernelEnum.TYPE_3, "A000000003", "A0000000031010", "A0000000980848"), //
    MASTER_CARD("Master card", "^5[1-5][0-9]{5,}$", KernelEnum.TYPE_2, "A000000004", "A000000005"), //
    AMERICAN_EXPRESS("American express", "^3[47][0-9]{5,}$", KernelEnum.TYPE_4, "A000000025"), //
    CB("CB", null, null, "A000000042"), //
    LINK("LINK", null, null, "A000000029"), //
    JCB("JCB", "^(?:2131|1800|35[0-9]{3})[0-9]{3,}$", KernelEnum.TYPE_5, "A000000065"), //
    DANKORT("Dankort", null, null, "A0000001211010"), //
    COGEBAN("CoGeBan", null, null, "A0000001410001"), //
    DISCOVER("Discover", "(6011|65|64[4-9]|622)[0-9]*", KernelEnum.TYPE_6, "A0000001523010"), //
    BANRISUL("Banrisul", null, null, "A000000154"), //
    SPAN("Saudi Payments Network", null, null, "A000000228"), //
    INTERAC("Interac", null, null, "A000000277"), //
    ZIP("Discover Card", null, null, "A000000324"), //
    UNIONPAY("UnionPay", "^62[0-9]{14,17}", KernelEnum.TYPE_7, "A000000333"), //
    EAPS("Euro Alliance of Payment Schemes", null, null, "A000000359"), //
    VERVE("Verve", null, null, "A000000371"), //
    TENN("The Exchange Network ATM Network", null, null, "A000000439"), //
    RUPAY("Rupay", null, null, "A0000005241010"), //
    ПРО100("ПРО100", null, null, "A0000004320001"), //
    ZKA("ZKA", null, null, "D27600002545500100"), //
    BANKAXEPT("Bankaxept", null, null, "D5780000021010"), //
    BRADESCO("BRADESCO", null, null, "F0000000030001"),
    MIDLAND("Midland", null, null, "A00000002401"), //
    PBS("PBS", null, null, "A0000001211010"), //
    ETRANZACT("eTranzact", null, null, "A000000454"), //
    GOOGLE("Google", null, null, "A0000004766C"), //
    INTER_SWITCH("InterSwitch", null, null, "A0000003710001");

    /**
     * array of Card AID or partial AID (RID)
     */
    private final String[] aids;

    /**
     * array of Aid in byte
     */
    private final byte[][] aidsByte;

    /**
     * Card scheme (card number IIN ranges)
     */
    private final String name;

    /**
     * Card number pattern regex
     */
    private final Pattern pattern;

    /**
     * An default identifier for a payment kernel to be used, unless the chip asks for something
     * else.
     */
    private final KernelEnum defaultKernelId;

    /**
     * Constructor using fields
     *
     * @param pAids
     *            Card AID or RID
     * @param pScheme
     *            scheme name
     * @param pRegex
     *            Card regex
     */
    private CardSchemeEnum(final String pScheme, final String pRegex, final KernelEnum pDefaultKernelId, final String... pAids) {
        aids = pAids;
        aidsByte = new byte[pAids.length][];
        for (int i = 0; i < aids.length; i++) {
            aidsByte[i] = ByteString.hexStringToByteArray(pAids[i]);
        }
        name = pScheme;
        if (!TextUtils.isEmpty(pRegex)) {
            pattern = Pattern.compile(pRegex);
        } else {
            pattern = null;
        }
        defaultKernelId = pDefaultKernelId;
    }

    /**
     * Method used to get the field aid
     *
     * @return the aid
     */
    public String[] getAid() {
        return aids;
    }

    /**
     * Method used to get the field name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get card type by AID
     *
     * @param pAid
     *            card AID
     * @return CardType or null
     */
    public static CardSchemeEnum getCardTypeByAid(final String pAid) {
        CardSchemeEnum ret = null;
        if (pAid != null) {
            String aid = pAid.trim();
            for (CardSchemeEnum val : CardSchemeEnum.values()) {
                for (String schemeAid : val.getAid()) {
                    if (aid.startsWith(schemeAid.trim())) {
                        ret = val;
                        break;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Method used to the the card type with regex
     *
     * @param pCardNumber
     *            card number
     * @return the type of the card using regex
     */
    public static CardSchemeEnum getCardTypeByCardNumber(final String pCardNumber) {
        CardSchemeEnum ret = null;
        if (pCardNumber != null) {
            for (CardSchemeEnum val : CardSchemeEnum.values()) {
                if (val.pattern != null && val.pattern.matcher(pCardNumber.trim()).matches()) {
                    ret = val;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * Method used to get the field aidByte
     *
     * @return the aidByte
     */
    public byte[][] getAidByte() {
        return aidsByte;
    }

}
