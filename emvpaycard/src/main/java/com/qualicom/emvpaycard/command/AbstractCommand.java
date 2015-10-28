package com.qualicom.emvpaycard.command;

import com.qualicom.emvpaycard.EmvPayCardException;

/**
 * Created by kangelov on 2015-10-23.
 */
public abstract class AbstractCommand {

    private final PayCardCommand payCardCommand;


    protected AbstractCommand(PayCardCommand payCardCommand) throws EmvPayCardException {
        if (payCardCommand == null || !payCardCommand.isConnected())
            throw new EmvPayCardException("Invalid or null payCardController object passed.");
        this.payCardCommand = payCardCommand;
    }

    protected PayCardCommand getPayCardCommand() {
        return payCardCommand;
    }
}
