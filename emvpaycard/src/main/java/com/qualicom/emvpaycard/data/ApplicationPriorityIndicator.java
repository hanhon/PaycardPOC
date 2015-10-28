package com.qualicom.emvpaycard.data;

import com.google.gson.annotations.Expose;
import com.qualicom.emvpaycard.utils.ByteString;

/**
 * Created by kangelov on 2015-10-28.
 */
public class ApplicationPriorityIndicator implements Comparable<ApplicationPriorityIndicator> {

    @Expose
    private byte applicationPriorityIndicator = 0x00;

    public ApplicationPriorityIndicator(byte[] applicationPriorityIndicator) {
        if (applicationPriorityIndicator != null && applicationPriorityIndicator.length > 0) {
            this.applicationPriorityIndicator = applicationPriorityIndicator[0];
        }
    }

    public boolean hasAssignedPriority() {
        return getApplicationPriority() > 0x00;
    }

    public byte getApplicationPriorityIndicator() {
        return applicationPriorityIndicator;
    }

    public int getApplicationPriority() {
        return this.applicationPriorityIndicator & 0x0f; //take out all but bits 1-4. All others are reserved for future use.
    }

    @Override
    public String toString() {
        return ByteString.byteToHexString(this.applicationPriorityIndicator);
    }

    @Override
    public int compareTo(ApplicationPriorityIndicator another) {
        return Integer.compare(getApplicationPriority(), another.getApplicationPriority());
    }
}
