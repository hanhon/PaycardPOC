package com.qualicom.emvpaycard.command;

import com.qualicom.emvpaycard.EmvPayCardException;
import com.qualicom.emvpaycard.enums.CommandEnum;
import com.qualicom.emvpaycard.data.GetProcessingOptionsResponse;
import com.qualicom.emvpaycard.utils.ByteString;
import com.qualicom.emvpaycard.utils.Command;

/**
 * Created by kangelov on 2015-10-27.
 */
public class GetProcessingOptionsCommand extends AbstractCommand {


    public GetProcessingOptionsCommand(PayCardCommand payCardCommand) throws EmvPayCardException {
        super(payCardCommand);
    }

    public GetProcessingOptionsResponse getApplicationProfile(String pdol) throws EmvPayCardException {
        byte[] pdolArray = ByteString.hexStringToByteArray(pdol);
        Command gpoCommand = new Command(CommandEnum.GPO, pdolArray, 0x0);
        byte[] response = getPayCardCommand().transcieve(gpoCommand);
        return new GetProcessingOptionsResponse(response);
    }

}
