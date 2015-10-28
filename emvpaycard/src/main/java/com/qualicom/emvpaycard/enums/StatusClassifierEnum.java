package com.qualicom.emvpaycard.enums;

import com.google.gson.annotations.Expose;

/**
 * Created by kangelov on 2015-10-28.
 */
public enum StatusClassifierEnum {

    PROCESS_COMPLETED(true,false,false, "Process completed successfully, normal processing."),
    EXECUTION_ERROR(false,false,true, "Process aborted, execution error."),
    PROCESS_WARNING(true,true,false, "Process completed, warning processing."),
    CHECK_ERROR(false,false,true,"Process aborted, checked error.");

    @Expose
    private final boolean hasSuccess;

    @Expose
    private final boolean hasWarning;

    @Expose
    private final boolean hasError;

    @Expose
    private final String message;

    StatusClassifierEnum(boolean hasSuccess, boolean hasWarning, boolean hasError, String message) {
        this.hasSuccess = hasSuccess;
        this.hasWarning = hasWarning;
        this.hasError = hasError;
        this.message = message;
    }

    public boolean hasError() {
        return hasError;
    }

    public boolean hasWarning() {
        return hasWarning;
    }

    public boolean hasSuccess() {
        return hasSuccess;
    }

    public String getMessage() {
        return message;
    }

}
