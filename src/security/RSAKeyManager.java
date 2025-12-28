package security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAKeyManager {
    private static KeyPair keyPair;

    public static void generateKeys() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        keyPair = keyGen.generateKeyPair();
    }

    public static PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public static PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }
}
