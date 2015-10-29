package com.qualicom.emvpaycard.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.EmvPayCardException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kangelov on 2015-10-27.
 */
public class ApplicationFileLocator extends EmvData {

    @Expose
    private final List<ApplicationFileLocatorRange> recordRanges;

    public ApplicationFileLocator(byte[] response) throws EmvPayCardException {
        super(response);
        if (response != null && response.length > 0 && response.length % 4 != 0) //AFL is always non-empty and a multiple of 4 bytes.
            throw new EmvPayCardException("Invalid Application File Locator passed.");
        this.recordRanges = new ArrayList<ApplicationFileLocatorRange>();
        for(int i=0; i<response.length / 4; i++)
            this.recordRanges.add(new ApplicationFileLocatorRange(Arrays.copyOfRange(response, i * 4, i * 4 + 4)));
    }

    public List<ApplicationFileLocatorRange> getRecordRanges() {
        return recordRanges;
    }

}
