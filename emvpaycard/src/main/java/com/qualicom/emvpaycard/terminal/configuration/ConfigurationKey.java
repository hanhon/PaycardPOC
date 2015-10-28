package com.qualicom.emvpaycard.terminal.configuration;

import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.enums.KernelEnum;
import com.qualicom.emvpaycard.terminal.enums.TransactionTypeEnum;

/**
 * Created by kangelov on 2015-10-28.
 */
public class ConfigurationKey {

    @Expose
    private final String aid;

    @Expose
    private final KernelEnum kernelId;

    @Expose
    private final TransactionTypeEnum transactionType;

    private ConfigurationKey(String aid, KernelEnum kernelId, TransactionTypeEnum transactionType) {
        this.aid = aid;
        this.kernelId = kernelId;
        this.transactionType = transactionType;
    }

    @Override
    public int hashCode() {
        return aid.hashCode() + 5 * kernelId.hashCode() + 7 * transactionType.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof ConfigurationKey)) return false;
        ConfigurationKey other = (ConfigurationKey)o;
        return aid.equals(other.aid) && kernelId.equals(other.kernelId) && transactionType.equals(other.transactionType);
    }
}
