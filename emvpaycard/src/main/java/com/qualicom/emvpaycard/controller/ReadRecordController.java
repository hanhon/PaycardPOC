package com.qualicom.emvpaycard.controller;

import android.util.Log;

import com.qualicom.emvpaycard.EmvPayCardException;
import com.qualicom.emvpaycard.enums.EmvCommandEnum;
import com.qualicom.emvpaycard.model.EmvResponse;
import com.qualicom.emvpaycard.utils.ByteString;
import com.qualicom.emvpaycard.utils.EmvCommand;

/**
 * Created by kangelov on 2015-10-23.
 */
public class ReadRecordController extends CommandController {

    private static final String WRONG_LENGTH_CODE = "6C";

    public ReadRecordController(PayCardController payCardController) throws EmvPayCardException {
        super(payCardController);
    }

    public void readPSERecord() throws EmvPayCardException {
        //Read record 0. This will fail, but the error will tell us where to read.
        EmvCommand readRecordCommand = new EmvCommand(EmvCommandEnum.READ_RECORD, 0x01, 0x0C);
        byte[] response = getPayCardController().transcieve(readRecordCommand);
        EmvResponse emvResponse = new EmvResponse(response);
        if (!WRONG_LENGTH_CODE.equals(emvResponse.getProcessingStatus()))
            throw new EmvPayCardException("Cannot determine the length of the PSE record. Unexpected response from chip.");
        Log.i("READRECORD","PSE is located at " + emvResponse.getProcessingQualifier());
        readRecordCommand = new EmvCommand(EmvCommandEnum.READ_RECORD, ByteString.hexStringToByte(emvResponse.getProcessingQualifier()), 0x0C);
    }


    public void readRecord(String recordNum) throws EmvPayCardException {
        EmvCommand readRecordCommand = new EmvCommand(EmvCommandEnum.READ_RECORD, ByteString.hexStringToByte(recordNum), 0);
        byte[] response = getPayCardController().transcieve(readRecordCommand);
    }

}
