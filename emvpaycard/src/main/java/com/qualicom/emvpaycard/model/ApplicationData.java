package com.qualicom.emvpaycard.model;

import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.utils.ByteString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kangelov on 2015-10-27.
 */
public class ApplicationData extends EmvData {

    @Expose
    private String track2EquivalentData;
    private static final String TAG_TRACK2 = "57";

    @Expose
    private String cardholderName;
    private static final String TAG_CARDHOLDER_NAME = "5F20";

    @Expose
    private String track1DiscretionaryData;
    private static final String TAG_TRACK1 = "9F1F";

    public ApplicationData(byte[] response) {
        super(response);
        if (response != null) {
            Map<String, byte[]> parsedResponse = new HashMap<String, byte[]>();
            parseTLVResponse(response, new String[]{
                    TAG_TRACK2,
                    TAG_CARDHOLDER_NAME,
                    TAG_TRACK1}, parsedResponse);
            this.track2EquivalentData = ByteString.byteArrayToHexString(parsedResponse.get(TAG_TRACK2));
            this.cardholderName = ByteString.byteArrayToUTF8String(parsedResponse.get(TAG_CARDHOLDER_NAME));
            this.track1DiscretionaryData = ByteString.byteArrayToUTF8String(parsedResponse.get(TAG_TRACK1));
        }
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public String getTrack1DiscretionaryData() {
        return track1DiscretionaryData;
    }

    public String getTrack2EquivalentData() {
        return track2EquivalentData;
    }

    public String getCardNumber() {
        String[] data = getTrack2EquivalentData().split("[DF]");
        if (data == null || data.length < 2)
            return null;
        return data[0];
    }

    public String getExpirationYear() {
        String[] data = getTrack2EquivalentData().split("[DF]");
        if (data == null || data.length < 2 || data[1].length() < 8)
            return null;
        return data[1].substring(0,2);
    }

    public String getExpirationMonth() {
        String[] data = getTrack2EquivalentData().split("[DF]");
        if (data == null || data.length < 2 || data[1].length() < 8)
            return null;
        return data[1].substring(2,4);
    }

    public String getServiceCode() {
        String[] data = getTrack2EquivalentData().split("[DF]");
        if (data == null || data.length < 2 || data[1].length() < 8)
            return null;
        return data[1].substring(4,7);
    }
}
