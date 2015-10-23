package com.qualicom.emvpaycard.utils;

/**
 * Created by kangelov on 2015-10-20.
 */
public class ByteString {

    public static String printByteStream(byte[] stream) {
        StringBuffer buffer = new StringBuffer();
        if (stream != null) {
            for (byte b : stream) {
                buffer.append(byteToHexString(b) + " ");
            }
        }
        return buffer.toString();
    }

    public static String byteArrayToHexString(byte[] stream) {
        StringBuffer buffer = new StringBuffer();
        if (stream != null) {
            for (byte b : stream) {
                buffer.append(byteToHexString(b));
            }
        } else
            return null;
        return buffer.toString();
    }

    public static String byteArrayToUTF8String(byte[] stream) {
        if (stream != null)
            return new String(stream);
        else
            return null;
    }

    public static String byteToHexString(byte b) {
        String hexString = Integer.toHexString(b & 0xff); //Take care of the sign bit.
        if (hexString.length() == 1) hexString = "0" + hexString;
        return hexString.toUpperCase();
    }
}
