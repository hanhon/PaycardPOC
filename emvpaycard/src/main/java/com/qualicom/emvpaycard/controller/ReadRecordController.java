package com.qualicom.emvpaycard.controller;

import com.qualicom.emvpaycard.EmvPayCardException;
import com.qualicom.emvpaycard.enums.CommandEnum;
import com.qualicom.emvpaycard.data.ReadResponse;
import com.qualicom.emvpaycard.utils.Command;

/**
 *
 * Created by kangelov on 2015-10-23.
 */
public class ReadRecordController extends CommandController {

    private static final String WRONG_LENGTH_STATUS_CODE = "6C";

    public ReadRecordController(PayCardController payCardController) throws EmvPayCardException {
        super(payCardController);
    }

    public ReadResponse readRecord(byte recordNum, byte sfi, byte len) throws EmvPayCardException {
        Command readRecordCommand = new Command(CommandEnum.READ_RECORD, recordNum, sfi << 3 | 0x04, len);
        byte[] response = getPayCardController().transcieve(readRecordCommand);
        return new ReadResponse(response);
    }

}
