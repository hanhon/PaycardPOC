package com.qualicom.emvpaycard.enums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONStringer;

/**
 * Created by kangelov on 2015-10-28.
 */
public enum StatusWordEnum {

    RC_61XX("61[0-9A-F]{2}", "Command successfully executed. %d bytes of data are available and can be requested using GET RESPONSE.", StatusClassifierEnum.PROCESS_COMPLETED),
    RC_6281("6281", "The returned data may be errorneous.", StatusClassifierEnum.PROCESS_WARNING),
    RC_6282("6282", "Fewer bytes than specified by the Le parameter could be read, since the EOF was encountered first.", StatusClassifierEnum.PROCESS_WARNING),
    RC_6283("6283", "The selected file is reversibly blocked or invalidated.", StatusClassifierEnum.PROCESS_WARNING),
    RC_6284("6284", "The FCI data is not structured in accordance with ISO/IEC7816-4.", StatusClassifierEnum.PROCESS_WARNING),
    RC_62XX("62[0-9A-F]{2}","Warning, state of non-volatile memory not changed.", StatusClassifierEnum.PROCESS_WARNING),
    RC_63CX("63C[0-9A-F]", "The counter has reached the value %d.", StatusClassifierEnum.PROCESS_WARNING),
    RC_63XX("63[0-9A-F]{2}", "Warning, state of non-volatile memory changed.", StatusClassifierEnum.PROCESS_WARNING),
    RC_64XX("64[0-9A-F]{2}", "Execution error, state of non-volatile memory not changed.", StatusClassifierEnum.EXECUTION_ERROR),
    RC_6581("6581", "Memory error (e.g. during write operation).", StatusClassifierEnum.EXECUTION_ERROR),
    RC_65XX("65[0-9A-F]{2}", "Execution error, state of non-volatile memory changed.", StatusClassifierEnum.EXECUTION_ERROR),
    RC_67XX("67[0-9A-F]{2}", "Length incorrect.", StatusClassifierEnum.CHECK_ERROR),
    RC_6800("6800", "Functions in the class byte not supported.", StatusClassifierEnum.CHECK_ERROR),
    RC_6881("6881", "Logical channels not supported.", StatusClassifierEnum.CHECK_ERROR),
    RC_6882("6882", "Secure messaging not supported.", StatusClassifierEnum.CHECK_ERROR),
    RC_6900("6900", "Command not allowed (general).", StatusClassifierEnum.CHECK_ERROR),
    RC_6981("6981", "Command incompatible with file structure.", StatusClassifierEnum.CHECK_ERROR),
    RC_6982("6982", "Security state not satisfied.", StatusClassifierEnum.CHECK_ERROR),
    RC_6983("6983", "Authentication method blocked.", StatusClassifierEnum.CHECK_ERROR),
    RC_6984("6984", "Referenced data reversibly blocked.", StatusClassifierEnum.CHECK_ERROR),
    RC_6985("6985", "Usage conditions not satisfied.", StatusClassifierEnum.CHECK_ERROR),
    RC_6986("6986", "Command not allowed. No EF selected.", StatusClassifierEnum.CHECK_ERROR),
    RC_6987("6987", "Expected secure messaging data objects missing.", StatusClassifierEnum.CHECK_ERROR),
    RC_6988("6988", "Secure messaging data objects incorrect.", StatusClassifierEnum.CHECK_ERROR),
    RC_6A00("6A00", "Incorrect P1 or P2 parameters (general).", StatusClassifierEnum.CHECK_ERROR),
    RC_6A80("6A80", "Parameters in the data portion are incorrect.", StatusClassifierEnum.CHECK_ERROR),
    RC_6A81("6A81", "Function not supported.", StatusClassifierEnum.CHECK_ERROR),
    RC_6A82("6A82", "File not found.", StatusClassifierEnum.CHECK_ERROR),
    RC_6A83("6A83", "Record not found.", StatusClassifierEnum.CHECK_ERROR),
    RC_6A84("6A84", "Insufficient memory.", StatusClassifierEnum.CHECK_ERROR),
    RC_6A85("6A85", "Lc inconsistent with TLV structure.", StatusClassifierEnum.CHECK_ERROR),
    RC_6A86("6A86", "Incorrect P1 or P2 parameter.", StatusClassifierEnum.CHECK_ERROR),
    RC_6A87("6A87", "Lc inconsistent with P1 or P2 parameter.", StatusClassifierEnum.CHECK_ERROR),
    RC_6A88("6A88", "Referenced data not found.", StatusClassifierEnum.CHECK_ERROR),
    RC_6B00("6B00", "Parameter 1 or 2 incorrect.", StatusClassifierEnum.CHECK_ERROR),
    RC_6CXX("6C[0-9A-F]{2}", "Bad length value in Le; %d is the correct length.", StatusClassifierEnum.CHECK_ERROR),
    RC_6D00("6D00", "Command (instruction) not supported.", StatusClassifierEnum.CHECK_ERROR),
    RC_6E00("6E00", "Class not supported.", StatusClassifierEnum.CHECK_ERROR),
    RC_6F00("6F00", "Command aborted - more exact diagnosis not possible (e.g. OS error).", StatusClassifierEnum.CHECK_ERROR),
    RC_67_6F_XX("6[7-9A-F][0-9A-F]{2}", "Checked error.", StatusClassifierEnum.CHECK_ERROR),
    RC_9000("9000", "Command successfully executed.", StatusClassifierEnum.PROCESS_COMPLETED),
    UNKNOWN("*", "Unknown status word value. No troubleshooting information available.", null);

    private final String regex;

    @Expose
    private final String message;

    @Expose
    private final StatusClassifierEnum statusClassifier;

    StatusWordEnum(String regex, String message, StatusClassifierEnum statusClassifier) {
        this.regex = regex;
        this.message = message;
        this.statusClassifier = statusClassifier;
    }

    public static StatusWordEnum getStatusWordEnum(final String processingStatus, final String processingQualifier) {
        String statusWord = processingStatus + processingQualifier;
        for (StatusWordEnum status : StatusWordEnum.values()) {
            if (statusWord.matches(status.regex))
                return status;
        }
        return UNKNOWN;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(final String processingStatus, final String processingQualifier) {
        return message.replaceFirst("%d", Integer.valueOf(processingQualifier, 16).toString());
    }

    public StatusClassifierEnum getStatusClassifier() {
        return statusClassifier;
    }

}
