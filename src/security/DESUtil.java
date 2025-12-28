package security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DESUtil {

    public static String encrypt(String text, String key) throws Exception {
        if (key.length() != 8) throw new IllegalArgumentException("DES key must be 8 chars");
        SecretKeySpec sk = new SecretKeySpec(key.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, sk);
        return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes()));
    }

    public static String decrypt(String text, String key) throws Exception {
        if (key.length() != 8) throw new IllegalArgumentException("DES key must be 8 chars");
        SecretKeySpec sk = new SecretKeySpec(key.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, sk);
        return new String(cipher.doFinal(Base64.getDecoder().decode(text)));
    }
}
