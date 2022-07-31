package com.alesharik.common.oauth;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * This class initializes BouncyCastle security provider
 */
public class BouncyCastleInitializer {
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null)
            Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * No-op
     */
    public static void stub() {}
}
