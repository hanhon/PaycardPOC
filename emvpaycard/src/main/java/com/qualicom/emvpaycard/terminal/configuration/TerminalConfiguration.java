package com.qualicom.emvpaycard.terminal.configuration;

import com.qualicom.emvpaycard.terminal.enums.OperatingModeEnum;
import com.qualicom.emvpaycard.terminal.enums.TransactionTypeEnum;

import java.util.Set;

/**
 * Created by kangelov on 2015-10-28.
 */
public class TerminalConfiguration {

    private String countryCode; //ISO 3166, 2 characters

    private Set<String> currencyCodes; //ISO 4217, 3 digits

    private Boolean isAutorun;

    private Set<OperatingModeEnum> operatingMode;

    private Set<TransactionTypeEnum> supportedTransactionTypes;




}
