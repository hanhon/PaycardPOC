package com.qualicom.emvpaycard.data;

import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.EmvPayCardException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kangelov on 2015-10-27.
 */
public class GetProcessingOptionsResponse extends EmvResponse {

    @Expose
    private ApplicationInterchangeProfile applicationInterchangeProfile;
    private static final String TAG_AIP="82";

    @Expose
    private ApplicationFileLocator applicationFileLocator;
    private static final String TAG_AFL="94";

    private static final String TAG_80="80";
    private static final String TAG_77="77";


    public GetProcessingOptionsResponse(byte[] response) throws EmvPayCardException {
        super(response);
        if (getData() != null && getData().length > 0 && isSuccessfulResponse()) {
            Map<String, byte[]> parsedResponse = new HashMap<String, byte[]>();
            parseTLVResponse(getData(), new String[]{
                    TAG_80,
                    TAG_77
            }, parsedResponse);
            //The response can come in two formats: a TAG 80 containing the AFP and AIP concatenated, or
            //as a TAG 77 containing TAGS 82 and 94 among possibly other things. Either way, we only care
            //for the AIP and AFL as this is the output of this command.
            if (parsedResponse.containsKey(TAG_80)) {
                byte[] tag80 = parsedResponse.get(TAG_80);
                this.applicationInterchangeProfile = new ApplicationInterchangeProfile(Arrays.copyOfRange(tag80,0,2));
                this.applicationFileLocator = new ApplicationFileLocator(Arrays.copyOfRange(tag80,2,tag80.length));
            } else if (parsedResponse.containsKey(TAG_77)) {
                //Now do it again on TAG 77.
                HashMap<String, byte[]> tag77ParsedResponse = new HashMap<String, byte[]>();
                //Don't trust the top-level tag.
                parseTLVResponse(getData(), new String[]{
                        TAG_AIP,
                        TAG_AFL
                }, tag77ParsedResponse);
                this.applicationInterchangeProfile = new ApplicationInterchangeProfile(tag77ParsedResponse.get(TAG_AIP));
                this.applicationFileLocator = new ApplicationFileLocator(tag77ParsedResponse.get(TAG_AFL));
            }
        }
    }

    public ApplicationFileLocator getApplicationFileLocator() {
        return applicationFileLocator;
    }

    public ApplicationInterchangeProfile getApplicationInterchangeProfile() {
        return applicationInterchangeProfile;
    }


}
