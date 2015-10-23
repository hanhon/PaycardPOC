package com.qualicom.emvpaycard.controller;

import com.qualicom.emvpaycard.EmvPayCardException;

/**
 * Created by kangelov on 2015-10-23.
 */
public abstract class CommandController {

    private final PayCardController payCardController;


    protected CommandController(PayCardController payCardController) throws EmvPayCardException {
        if (payCardController == null || !payCardController.isConnected())
            throw new EmvPayCardException("Invalid or null payCardController object passed.");
        this.payCardController = payCardController;
    }

    protected PayCardController getPayCardController() {
        return payCardController;
    }
}
