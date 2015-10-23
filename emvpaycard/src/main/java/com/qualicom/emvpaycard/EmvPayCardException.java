package com.qualicom.emvpaycard;

/**
 * Created by kangelov on 2015-10-18.
 */
public class EmvPayCardException extends Exception {

    public EmvPayCardException(String message) {
        super(message);
    }

    public EmvPayCardException(String message, Throwable e) {
        super(message,e);
    }

}
