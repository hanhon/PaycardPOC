package com.qualicom.emvpaycard.command;

import com.qualicom.emvpaycard.EmvPayCardException;
import com.qualicom.emvpaycard.data.ApplicationFileLocatorRange;
import com.qualicom.emvpaycard.enums.CommandEnum;
import com.qualicom.emvpaycard.data.ReadResponse;
import com.qualicom.emvpaycard.utils.Command;

/**
 *
 * Created by kangelov on 2015-10-23.
 */
public class ReadRecordCommand extends AbstractCommand {

    private static final String WRONG_LENGTH_STATUS_CODE = "6C";

    public ReadRecordCommand(PayCardCommand payCardCommand) throws EmvPayCardException {
        super(payCardCommand);
    }

    public ReadResponse readRecord(byte recordNum, byte sfi, byte len) throws EmvPayCardException {
        Command readRecordCommand = new Command(CommandEnum.READ_RECORD, recordNum, sfi << 3 | 0x04, len);
        byte[] response = getPayCardCommand().transcieve(readRecordCommand);
        return new ReadResponse(response);
    }

    public ReadResponse readRecord(ApplicationFileLocatorRange recordRange) throws EmvPayCardException {
        return readRecord(
                recordRange.getStartRecord(),
                recordRange.getShortFileIdentifier(),
                recordRange.getLength());

    }

}
