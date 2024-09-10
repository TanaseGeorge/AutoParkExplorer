package utils;


import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {


    private static volatile String secretKey;

    private KeyGenerator() {
    }

    public String getSecretKey () {
        if (secretKey == null) {
            synchronized (KeyGenerator.class) {
                if (secretKey == null) {
                    SecureRandom secureRandom = new SecureRandom();
                    byte[] keyBytes = new byte[32];
                    secureRandom.nextBytes(keyBytes);
                    secretKey =  Base64.getEncoder().encodeToString(keyBytes);
                }
            }
        }
        return secretKey;
    }

    private static final class InstanceHolder {
        private static final KeyGenerator instance = new KeyGenerator();
    }

    public static KeyGenerator getInstance() {
        return InstanceHolder.instance;
    }
}
