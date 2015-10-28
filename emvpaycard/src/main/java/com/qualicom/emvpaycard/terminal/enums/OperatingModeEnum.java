package com.qualicom.emvpaycard.terminal.enums;

/**
 * Created by kangelov on 2015-10-28.
 */
public enum OperatingModeEnum {

    EMV("EMV"),
    MSR("MSR");

    private final String operatingMode;

    private OperatingModeEnum(String operatingMode) {
        this.operatingMode = operatingMode;
    }
}
