package com.qualicom.emvpaycard.terminal.enums;

/**
 * Created by kangelov on 2015-10-28.
 */
public enum TransactionTypeEnum {

    PURCHASE("00"),
    PURCHASE_WITH_CASHBACK("09"),
    CASH_ADVANCE("01"),
    REFUND("20");

    private final String transactionType;

    private TransactionTypeEnum(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }

}
