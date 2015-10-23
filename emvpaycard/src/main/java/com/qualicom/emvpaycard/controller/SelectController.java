package com.qualicom.emvpaycard.controller;

import android.nfc.Tag;
import android.nfc.tech.IsoDep;

import com.qualicom.emvpaycard.EmvPayCardException;
import com.qualicom.emvpaycard.enums.EmvCommandEnum;
import com.qualicom.emvpaycard.model.SelectResponse;
import com.qualicom.emvpaycard.utils.EmvCommand;

/**
 * Created by kangelov on 2015-10-18.
 */
public class SelectController extends CommandController {

    public static final byte[] PPSE = "2PAY.SYS.DDF01".getBytes(); //used for Proximity interfaces.
    public static final byte[] PSE = "1PAY.SYS.DDF01".getBytes(); //used for EMV interfaces. This isn't used right now, no Android phone has a chipcard reader.


    public SelectController(PayCardController payCardController) throws EmvPayCardException {
        super(payCardController);
    }

    /**
     * Select the Payment System's Environment and return it parsed out.
     *
     * @return
     * @throws EmvPayCardException
     */
    public SelectResponse selectPSE() throws EmvPayCardException {
        EmvCommand selectCommand = new EmvCommand(EmvCommandEnum.SELECT, null, 0);
        byte[] response = getPayCardController().transcieve(selectCommand);
        return new SelectResponse(response);
    }

    /**
     * Select the Directory Definition File and return it parsed out.
     * Since this only deals with contactless interfaces, the only meaningful value (for now) is the PPSE application
     * @return
     * @throws EmvPayCardException
     */
    public SelectResponse selectDDF(byte[] dfName) throws EmvPayCardException {
        EmvCommand selectCommand = new EmvCommand(EmvCommandEnum.SELECT, dfName, 0);
        byte[] response = getPayCardController().transcieve(selectCommand);
        return new SelectResponse(response);
    }

    public SelectResponse selectADF(byte[] application) throws EmvPayCardException {
        EmvCommand selectCommand = new EmvCommand(EmvCommandEnum.SELECT, application, 0);
        byte[] response = getPayCardController().transcieve(selectCommand);
        return new SelectResponse(response);
    }

}
