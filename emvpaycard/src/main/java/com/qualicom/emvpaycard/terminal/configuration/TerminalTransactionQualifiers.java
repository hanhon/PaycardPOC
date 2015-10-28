package com.qualicom.emvpaycard.terminal.configuration;

import com.google.gson.annotations.Expose;

/**
 * Created by kangelov on 2015-10-28.
 */
public class TerminalTransactionQualifiers {

    @Expose
    private boolean isMSRModeSupported; //byte 1, bit 8 = 0x80

    @Expose
    private boolean isEMVModeSupported; //byte 1, bit 6 = 0x20

    @Expose
    private boolean isEMVContactICCSupported; //byte 1, bit 5 = 0x10

    @Expose
    private boolean isOnlineCapable; //byte 1, bit 4 = 0x08

    @Expose
    private boolean isOnlinePINSupported; //byte 1, bit 3 = 0x04

    @Expose
    private boolean isSignatureSupported; //byte 1, bit 2 = 0x02

    @Expose
    private boolean isOfflineDataAuthenticationForOnlineAuthorizationsSupported; //byte 1, bit 1 = 0x01

    @Expose
    private boolean isOnlineCryptogramRequired; //byte 2, bit 8

    @Expose
    private boolean isCVMRequired; //byte 2, bit 7

    @Expose
    private boolean isEMVContactICCOfflinePINSupported; //byte 2, bit 6

    @Expose
    private boolean isIssuerUpdateProcessingSupported; //byte 3, bit 8

    @Expose
    private boolean isConsumerDeviceCVMSupported; //byte 3, bit 7

    public boolean isConsumerDeviceCVMSupported() {
        return isConsumerDeviceCVMSupported;
    }

    public void setIsConsumerDeviceCVMSupported(boolean isConsumerDeviceCVMSupported) {
        this.isConsumerDeviceCVMSupported = isConsumerDeviceCVMSupported;
    }

    public boolean isCVMRequired() {
        return isCVMRequired;
    }

    public void setIsCVMRequired(boolean isCVMRequired) {
        this.isCVMRequired = isCVMRequired;
    }

    public boolean isEMVContactICCOfflinePINSupported() {
        return isEMVContactICCOfflinePINSupported;
    }

    public void setIsEMVContactICCOfflinePINSupported(boolean isEMVContactICCOfflinePINSupported) {
        this.isEMVContactICCOfflinePINSupported = isEMVContactICCOfflinePINSupported;
    }

    public boolean isEMVContactICCSupported() {
        return isEMVContactICCSupported;
    }

    public void setIsEMVContactICCSupported(boolean isEMVContactICCSupported) {
        this.isEMVContactICCSupported = isEMVContactICCSupported;
    }

    public boolean isEMVModeSupported() {
        return isEMVModeSupported;
    }

    public void setIsEMVModeSupported(boolean isEMVModeSupported) {
        this.isEMVModeSupported = isEMVModeSupported;
    }

    public boolean isIssuerUpdateProcessingSupported() {
        return isIssuerUpdateProcessingSupported;
    }

    public void setIsIssuerUpdateProcessingSupported(boolean isIssuerUpdateProcessingSupported) {
        this.isIssuerUpdateProcessingSupported = isIssuerUpdateProcessingSupported;
    }

    public boolean isMSRModeSupported() {
        return isMSRModeSupported;
    }

    public void setIsMSRModeSupported(boolean isMSRModeSupported) {
        this.isMSRModeSupported = isMSRModeSupported;
    }

    public boolean isOfflineDataAuthenticationForOnlineAuthorizationsSupported() {
        return isOfflineDataAuthenticationForOnlineAuthorizationsSupported;
    }

    public void setIsOfflineDataAuthenticationForOnlineAuthorizationsSupported(boolean isOfflineDataAuthenticationForOnlineAuthorizationsSupported) {
        this.isOfflineDataAuthenticationForOnlineAuthorizationsSupported = isOfflineDataAuthenticationForOnlineAuthorizationsSupported;
    }

    public boolean isOnlineCapable() {
        return isOnlineCapable;
    }

    public void setIsOnlineCapable(boolean isOnlineCapable) {
        this.isOnlineCapable = isOnlineCapable;
    }

    public boolean isOnlineCryptogramRequired() {
        return isOnlineCryptogramRequired;
    }

    public void setIsOnlineCryptogramRequired(boolean isOnlineCryptogramRequired) {
        this.isOnlineCryptogramRequired = isOnlineCryptogramRequired;
    }

    public boolean isOnlinePINSupported() {
        return isOnlinePINSupported;
    }

    public void setIsOnlinePINSupported(boolean isOnlinePINSupported) {
        this.isOnlinePINSupported = isOnlinePINSupported;
    }

    public boolean isSignatureSupported() {
        return isSignatureSupported;
    }

    public void setIsSignatureSupported(boolean isSignatureSupported) {
        this.isSignatureSupported = isSignatureSupported;
    }

    public byte[] calculateTTQ() {
        byte[] ttq = new byte[4];
        ttq[0] = ttq[1] = ttq[2] = ttq[3] = 0;

        //Byte 1
        ttq[0] += isMSRModeSupported() ? 0x80 : 0;
        ttq[0] += isEMVModeSupported() ? 0x20 : 0;
        ttq[0] += isEMVContactICCSupported() ? 0x10 : 0;
        ttq[0] += isOnlineCapable() ? 0x08 : 0;
        ttq[0] += isOnlinePINSupported() ? 0x04 : 0;
        ttq[0] += isSignatureSupported() ? 0x02 : 0;
        ttq[0] += isOfflineDataAuthenticationForOnlineAuthorizationsSupported() ? 0x01 : 0;

        //Byte 2
        ttq[1] += isOnlineCryptogramRequired() ? 0x80 : 0;
        ttq[1] += isCVMRequired() ? 0x40 : 0;
        ttq[1] += isEMVContactICCOfflinePINSupported() ? 0x20 : 0;

        //Byte 3
        ttq[2] += isIssuerUpdateProcessingSupported() ? 0x80 : 0;
        ttq[2] += isConsumerDeviceCVMSupported() ? 0x40 : 0;

        //Byte 4 is unused.

        return ttq;
    }
}
