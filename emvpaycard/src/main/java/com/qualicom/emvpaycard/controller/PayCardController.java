package com.qualicom.emvpaycard.controller;

import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.util.Log;

import com.qualicom.emvpaycard.EmvPayCardException;
import com.qualicom.emvpaycard.utils.ByteString;
import com.qualicom.emvpaycard.utils.EmvCommand;
import com.qualicom.emvpaycard.utils.EmvPayCardUtils;

import java.io.IOException;

/**
 * A simple class to handle low-level communication with an NFC chipcard.
 *
 * Created by kangelov on 2015-10-18.
 */
public class PayCardController {

    private final Tag tag;
    private final IsoDep isoDepTag;
    private boolean isConnected = false;

    public PayCardController(Tag tag) throws EmvPayCardException {
        this.tag = tag;
        if (!EmvPayCardUtils.isValidEmvPayCard(tag))
            throw new EmvPayCardException("Tag is not a valid payment card.");
        this.isoDepTag = IsoDep.get(tag);
    }

    public void connect() throws EmvPayCardException {
        try {
            isoDepTag.connect();
            isConnected = true;
        } catch (IOException e) {
            throw new EmvPayCardException("Cannot connect to Tag.",e);
        }
    }

    public void disconnect() throws EmvPayCardException {
        try {
            isoDepTag.close();
        } catch (IOException e) {
            throw new EmvPayCardException("Cannot disconnect from Tag.",e);
        } finally {
            isConnected = false;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        disconnect();
    }

    public byte[] transcieve(EmvCommand command) throws EmvPayCardException {
        if (!isConnected) throw new EmvPayCardException("Tag not connected.");
        try {
            Log.i("SEND", ByteString.printByteStream(command.toBytes()));
            byte[] response = isoDepTag.transceive(command.toBytes());
            Log.i("RECV", ByteString.printByteStream(response));
            return response;
        }catch (IOException e) {
            try { disconnect(); } catch (Exception err) { }
            throw new EmvPayCardException("Cannot communicate with Tag. Tag has been disconnected.",e);
        }
    }

    public boolean isConnected() {
        return isConnected;
    }



}
