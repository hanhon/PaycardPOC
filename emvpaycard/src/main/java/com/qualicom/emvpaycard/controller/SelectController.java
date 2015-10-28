package com.qualicom.emvpaycard.controller;

import com.qualicom.emvpaycard.EmvPayCardException;
import com.qualicom.emvpaycard.enums.CommandEnum;
import com.qualicom.emvpaycard.data.SelectResponse;
import com.qualicom.emvpaycard.utils.Command;

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
        Command selectCommand = new Command(CommandEnum.SELECT, null, 0);
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
        Command selectCommand = new Command(CommandEnum.SELECT, dfName, 0);
        byte[] response = getPayCardController().transcieve(selectCommand);
        return new SelectResponse(response);
    }

    public SelectResponse selectADF(byte[] application) throws EmvPayCardException {
        Command selectCommand = new Command(CommandEnum.SELECT, application, 0);
        byte[] response = getPayCardController().transcieve(selectCommand);
        return new SelectResponse(response);
    }

}
