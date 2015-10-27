package com.qualicom.emvpaycard.controller;

import android.util.Log;

import com.qualicom.emvpaycard.EmvPayCardException;
import com.qualicom.emvpaycard.enums.CommandEnum;
import com.qualicom.emvpaycard.model.EmvResponse;
import com.qualicom.emvpaycard.model.ReadResponse;
import com.qualicom.emvpaycard.utils.ByteString;
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

    /**
     * The following extracts the application data from a short file identifier (SFI). This is extremely
     * unreliable over an NFC interface as the SFIs are not returned as part of the SELECT PPSE
     * response. Clearly this interface isn't supposed to be dumping applications, at least not this
     * way. Anything can happen.
     *
     * This works great for my VISA, but not for my Mastercard. I suspect the Mastercard response is
     * actually not even a valid EMV response as the length byte is wrong and the tags being returned
     * are all unknown and/or proprietary.
     *
     * @deprecated not defined over NFC interface.
     * @param sfi
     * @return
     * @throws EmvPayCardException
     */
    public ReadResponse readSFI(String sfi) throws EmvPayCardException {
        //Read SFI 1 with 0 length.
        //This may or may not fail depending on the chip, but in case of an error it will tell us how many bytes to read.
        ReadResponse response = readRecord("01",sfi,"00");
        if (WRONG_LENGTH_STATUS_CODE.equals(response.getProcessingStatus())) {
            Log.i("READRECORD", "PSE is " + response.getProcessingQualifier() + " bytes long");
            response = readRecord("01", sfi, response.getProcessingQualifier());
        }
        return response;
    }


    public ReadResponse readRecord(String recordNum, String sfi, String len) throws EmvPayCardException {
        Command readRecordCommand = new Command(CommandEnum.READ_RECORD, ByteString.hexStringToByte(recordNum), ByteString.hexStringToByte(sfi) << 3 | 0x04, ByteString.hexStringToByte(len));
        byte[] response = getPayCardController().transcieve(readRecordCommand);
        return new ReadResponse(response);
    }

}
