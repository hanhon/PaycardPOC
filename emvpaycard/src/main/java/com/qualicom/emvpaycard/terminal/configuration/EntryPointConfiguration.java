package com.qualicom.emvpaycard.terminal.configuration;

/**
 * Created by kangelov on 2015-10-28.
 */
public class EntryPointConfiguration {

    private final ConfigurationKey key;

    private Boolean isStatusCheckSupported;

    private Boolean isZeroAmountAllowed;

    private Long readerContactlessTransactionLimit; //decimal format

    private Long readerContactlessFloorLimit; //decimal format

    private Long terminalFloorLimit; //decimal format

    private Long cvmRequiredLimit; //decimal format

    private TerminalTransactionQualifiers ttqs;

    private Boolean hasExtendedSelectionSupport;



    public EntryPointConfiguration(ConfigurationKey key) {
        this.key = key;
    }

    public ConfigurationKey getKey() {
        return key;
    }

}
