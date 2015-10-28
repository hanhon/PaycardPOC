package com.qualicom.emvpaycard.controller;

import com.qualicom.emvpaycard.EmvPayCardException;
import com.qualicom.emvpaycard.enums.CommandEnum;
import com.qualicom.emvpaycard.data.GetProcessingOptionsResponse;
import com.qualicom.emvpaycard.utils.ByteString;
import com.qualicom.emvpaycard.utils.Command;

/**
 * Created by kangelov on 2015-10-27.
 */
public class GetProcessingOptionsController extends CommandController {


    public GetProcessingOptionsController(PayCardController payCardController) throws EmvPayCardException {
        super(payCardController);
    }

    public GetProcessingOptionsResponse getApplicationProfile(String pdol) throws EmvPayCardException {
        byte[] pdolArray = ByteString.hexStringToByteArray(pdol);
        Command gpoCommand = new Command(CommandEnum.GPO, pdolArray, 0x0);
        byte[] response = getPayCardController().transcieve(gpoCommand);
        return new GetProcessingOptionsResponse(response);
    }

}
