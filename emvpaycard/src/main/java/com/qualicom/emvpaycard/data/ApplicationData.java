package com.qualicom.emvpaycard.data;

import android.text.TextUtils;

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
    private static final String TAG_TRACK2_EQUIVALENT_DATA = "57";

    @Expose
    private String track2Data;
    private static final String TAG_TRACK2_DATA = "9F6B";

    @Expose
    private String cardholderName;
    private static final String TAG_CARDHOLDER_NAME = "5F20";

    @Expose
    private String track1DiscretionaryData;
    private static final String TAG_TRACK1_DISCRETIONARY_DATA = "9F1F";

    @Expose
    private String track1Data;
    private static final String TAG_TRACK1_DATA = "56";

    public ApplicationData(byte[] response) {
        super(response);
        if (response != null) {
            Map<String, byte[]> parsedResponse = new HashMap<String, byte[]>();
            parseTLVResponse(response, new String[]{
                    TAG_TRACK2_DATA,
                    TAG_TRACK2_EQUIVALENT_DATA,
                    TAG_CARDHOLDER_NAME,
                    TAG_TRACK1_DATA,
                    TAG_TRACK1_DISCRETIONARY_DATA}, parsedResponse);

            this.track2Data = ByteString.byteArrayToHexString(parsedResponse.get(TAG_TRACK2_DATA));
            this.track2EquivalentData = ByteString.byteArrayToHexString(parsedResponse.get(TAG_TRACK2_EQUIVALENT_DATA));
            this.track1Data = ByteString.byteArrayToUTF8String(parsedResponse.get(TAG_TRACK1_DATA));
            this.track1DiscretionaryData = ByteString.byteArrayToUTF8String(parsedResponse.get(TAG_TRACK1_DISCRETIONARY_DATA));
            this.cardholderName = ByteString.byteArrayToUTF8String(parsedResponse.get(TAG_CARDHOLDER_NAME));
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

    public String getTrack1Data() {
        return track1Data;
    }

    public String getTrack2Data() {
        return track2Data;
    }

}