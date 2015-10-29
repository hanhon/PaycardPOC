package com.qualicom.emvpaycard.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.EmvPayCardException;

import java.lang.reflect.Type;

/**
 * A single range of records to read for a given application as returned by the Get Processing Options operation as part
 * of the ApplicationFileLocator. The AFL will in general have more than one.
 *
 * Created by kangelov on 2015-10-29.
 */
public class ApplicationFileLocatorRange extends EmvData {

    @Expose
    private final byte shortFileIdentifier;

    @Expose
    private final byte startRecord;

    @Expose
    private final byte endRecord;

    @Expose
    private final int numRecordsForOfflineDataAuthentication;

    public ApplicationFileLocatorRange(byte[] raw) throws EmvPayCardException {
        super(raw);
        if (getRaw().length != 4)
            throw new EmvPayCardException("Invalid Application File Locator range passed.");

        //SFL
        this.shortFileIdentifier = (byte)(getMaskedValue(0, 0xF8) >> 3);
        if (this.shortFileIdentifier < 1)
            throw new EmvPayCardException("Short File Identifier within the Application File Locator is missing or invalid.");
        this.startRecord = raw[1];
        this.endRecord = raw[2];
        this.numRecordsForOfflineDataAuthentication = raw[3];
    }

    public byte getEndRecord() {
        return endRecord;
    }

    public byte getStartRecord() {
        return startRecord;
    }

    public byte getLength() {
        return (byte)(getEndRecord() - getStartRecord()); //possible off by one here, but can't really tell.
    }

    public int getNumRecordsForOfflineDataAuthentication() {
        return numRecordsForOfflineDataAuthentication;
    }

    public byte getShortFileIdentifier() {
        return shortFileIdentifier;
    }
}

