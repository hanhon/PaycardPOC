package com.qualicom.emvpaycard.business;

import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.qualicom.emvpaycard.EmvPayCardException;
import com.qualicom.emvpaycard.data.ApplicationData;
import com.qualicom.emvpaycard.data.FCIApplicationTemplate;
import com.qualicom.emvpaycard.data.FCIProprietaryTemplate;
import com.qualicom.emvpaycard.data.KernelIdentifier;
import com.qualicom.emvpaycard.enums.CardSchemeEnum;
import com.qualicom.emvpaycard.enums.KernelEnum;
import com.qualicom.emvpaycard.enums.StatusClassifierEnum;

import java.lang.reflect.Type;

/**
 * Created by kangelov on 2015-10-28.
 */
public class CardData extends BusinessObject {

    static {
        gsonBuilder = gsonBuilder.registerTypeAdapter(CardData.class, new JsonSerializer<CardData>() {

            @Override
            public JsonElement serialize(CardData src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject obj = new JsonObject();
                obj.add("appName",context.serialize(src.getAppName()));
                obj.add("appLabel",context.serialize(src.getAppLabel()));
                obj.add("appPreferredName", context.serialize(src.getAppPreferredName()));
                obj.add("cardScheme",context.serialize(src.getCardSchemeEnum()));
                obj.add("track1",context.serialize(src.getTrack1()));
                obj.add("track2",context.serialize(src.getTrack2()));
                obj.add("track1DiscretionaryData",context.serialize(src.getTrack1DiscretionaryData()));
                obj.add("track2DiscretionaryData",context.serialize(src.getTrack2DiscretionaryData()));
                obj.add("cardNumber",context.serialize(src.getCardNumber()));
                obj.add("expirationMonth",context.serialize(src.getExpirationMonth()));
                obj.add("expirationYear",context.serialize(src.getExpirationYear()));
                obj.add("serviceCode",context.serialize(src.getServiceCode()));
                obj.add("cardholderName",context.serialize(src.getCardholderName()));
                obj.add("cardLanguage",context.serialize(src.getCardLanguage()));
                obj.add("issuerCountryCode",context.serialize(src.getIssuerCountryCode()));
                obj.add("selectedPaymentKernelId",context.serialize(src.getPaymentKernelEnum()));
                return obj;
            }
        }).registerTypeAdapter(CardSchemeEnum.class, new JsonSerializer<CardSchemeEnum>() {
            @Override
            public JsonElement serialize(CardSchemeEnum src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject object = new JsonObject();
                object.add("cardScheme", context.serialize(src.getName()));
                return object;
            }
        }).registerTypeAdapter(KernelEnum.class, new JsonSerializer<KernelEnum>() {
            @Override
            public JsonElement serialize(KernelEnum src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject object = new JsonObject();
                object.add("paymentKernelId", context.serialize(src.getKernelId()));
                return object;
            }
        });
    }

    private static final String TRACK1_SENTINELS_AND_SEPARATORS = "[%B\\^\\?]";
    private static final String TRACK2_SENTINELS_AND_SEPARATORS = "[;=DF\\?]";

    private final ApplicationData appData;
    private final FCIProprietaryTemplate ddfFCIProprietaryTemplate;
    private final FCIApplicationTemplate appTemplateData;
    private final FCIProprietaryTemplate adfFCIProprietaryTemplate;

    /**
     *
     * @param appData the application data returned by the read record operation
     * @param ddfFCIProprietaryResponse the FCI Proprietary Response from the Select DDF operation.
     * @param selectedApplicationTemplateData The selected application template data from the Select DDF response.
     * @param adfFCIProprietaryTemplate The FCI Proprietary Response from the Select APP operation.
     */
    public CardData(ApplicationData appData, FCIProprietaryTemplate ddfFCIProprietaryResponse, FCIApplicationTemplate selectedApplicationTemplateData, FCIProprietaryTemplate adfFCIProprietaryTemplate) throws EmvPayCardException {
        if (appData == null || ddfFCIProprietaryResponse == null || selectedApplicationTemplateData == null || adfFCIProprietaryTemplate == null)
            throw new EmvPayCardException("Insufficient or invalid data.");
        this.appData = appData;
        this.ddfFCIProprietaryTemplate = ddfFCIProprietaryResponse;
        this.appTemplateData = selectedApplicationTemplateData;
        this.adfFCIProprietaryTemplate = adfFCIProprietaryTemplate;
    }

    public String getAppName() {
        return appTemplateData.getAdfName();
    }

    public String getAppLabel() {
        return appTemplateData.getApplicationLabel();
    }

    public String getAppPreferredName() {
        return adfFCIProprietaryTemplate.getApplicationPreferredName();
    }

    public CardSchemeEnum getCardSchemeEnum() {
        return CardSchemeEnum.getCardTypeByAid(getAppName());
    }

    public String getTrack2() {
        String track2 = appData.getTrack2Data();
        if (TextUtils.isEmpty(track2))
            track2 = appData.getTrack2EquivalentData();
        return track2;
    }

    public String getTrack1() {
        return appData.getTrack1Data();
    }

    public String getTrack2DiscretionaryData() {
        String track2 = getTrack2();
        if (!TextUtils.isEmpty(track2)) {
            String[] data = track2.split(TRACK2_SENTINELS_AND_SEPARATORS);
            if (data == null || data.length < 2 || data[1].length() < 8)
                return null;
            return data[1].substring(7);
        }
        return null;
    }

    public String getTrack1DiscretionaryData() {
        String track1DiscretionaryData = appData.getTrack1DiscretionaryData();
        if (TextUtils.isEmpty(track1DiscretionaryData) && !TextUtils.isEmpty(getTrack1())) {
            String track1 = getTrack1();
            String[] data = track1.split(TRACK1_SENTINELS_AND_SEPARATORS);
            if (data != null && data.length >= 5) {
                track1DiscretionaryData = data[4];
            }
        }
        return track1DiscretionaryData;
    }

    public String getCardNumber() {
        String track2 = getTrack2();
        if (!TextUtils.isEmpty(track2)) {
            String[] data = track2.split(TRACK2_SENTINELS_AND_SEPARATORS);
            if (data == null || data.length < 2)
                return null;
            return data[0];
        }
        return null;
    }

    public String getExpirationYear() {
        String track2 = getTrack2();
        if (!TextUtils.isEmpty(track2)) {
            String[] data = track2.split(TRACK2_SENTINELS_AND_SEPARATORS);
            if (data == null || data.length < 2 || data[1].length() < 8)
                return null;
            return data[1].substring(0, 2);
        }
        return null;
    }

    public String getExpirationMonth() {
        String track2 = getTrack2();
        if (!TextUtils.isEmpty(track2)) {
            String[] data = track2.split(TRACK2_SENTINELS_AND_SEPARATORS);
            if (data == null || data.length < 2 || data[1].length() < 8)
                return null;
            return data[1].substring(2, 4);
        }
        return null;
    }

    public String getServiceCode() {
        String track2 = getTrack2();
        if (!TextUtils.isEmpty(track2)) {
            String[] data = track2.split(TRACK2_SENTINELS_AND_SEPARATORS);
            if (data == null || data.length < 2 || data[1].length() < 8)
                return null;
            return data[1].substring(4, 7);
        }
        return null;
    }

    public String getCardholderName() {
        String cardholderName = appData.getCardholderName();
        if (TextUtils.isEmpty(cardholderName) && !TextUtils.isEmpty(appData.getTrack1Data())) {
            String track1 = getTrack1();
            String[] data = track1.split(TRACK1_SENTINELS_AND_SEPARATORS);
            if (data != null && data.length > 1) {
                cardholderName = data[2];
            }
        }
        return cardholderName;
    }

    public KernelEnum getPaymentKernelEnum() {
        KernelIdentifier kernelIdentifier = appTemplateData.getKernelIdentifier();
        if (kernelIdentifier.isDefaultKernelForADF())
            return getCardSchemeEnum().getDefaultKernelId();
        else
            return kernelIdentifier.getKernel();
    }

    public String getIssuerCountryCode() {
        if (adfFCIProprietaryTemplate.getIssuerDiscretionaryData() != null)
            return adfFCIProprietaryTemplate.getIssuerDiscretionaryData().getIssuerCountryCode();
        return null;
    }

    public String getCardLanguage() {
        String lang = ddfFCIProprietaryTemplate.getLanguagePreference();
        if (!TextUtils.isEmpty(adfFCIProprietaryTemplate.getLanguagePreference()))
            lang = adfFCIProprietaryTemplate.getLanguagePreference();
        return lang;
    }

}
