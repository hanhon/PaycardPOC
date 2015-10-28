package com.qualicom.emvpaycard.terminal.configuration;

/**
 *
 * This must be subclassed by each payment kernel so that the kernel's specific data must be supplied.
 * All they have in common is the the way they are keyed.
 *
 * Created by kangelov on 2015-10-28.
 */
public abstract class KernelConfiguration {

    private final ConfigurationKey key;

    public KernelConfiguration(ConfigurationKey key) {
        this.key = key;
    }

    public ConfigurationKey getKey() {
        return key;
    }
}
