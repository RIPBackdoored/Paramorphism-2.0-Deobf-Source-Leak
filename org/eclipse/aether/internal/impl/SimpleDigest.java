package org.eclipse.aether.internal.impl;

import java.security.*;
import java.nio.charset.*;

class SimpleDigest
{
    private MessageDigest digest;
    private long hash;
    
    SimpleDigest() {
        super();
        try {
            this.digest = MessageDigest.getInstance("SHA-1");
        }
        catch (NoSuchAlgorithmException ex) {
            try {
                this.digest = MessageDigest.getInstance("MD5");
            }
            catch (NoSuchAlgorithmException ex2) {
                this.digest = null;
                this.hash = 13L;
            }
        }
    }
    
    public void update(final String s) {
        if (s == null || s.length() <= 0) {
            return;
        }
        if (this.digest != null) {
            this.digest.update(s.getBytes(StandardCharsets.UTF_8));
        }
        else {
            this.hash = this.hash * 31L + s.hashCode();
        }
    }
    
    public String digest() {
        if (this.digest != null) {
            final StringBuilder sb = new StringBuilder(64);
            final byte[] digest = this.digest.digest();
            for (int length = digest.length, i = 0; i < length; ++i) {
                final int n = digest[i] & 0xFF;
                if (n < 16) {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(n));
            }
            return sb.toString();
        }
        return Long.toHexString(this.hash);
    }
}
