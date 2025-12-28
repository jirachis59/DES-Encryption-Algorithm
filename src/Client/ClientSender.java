package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PublicKey;
import security.DESUtil;
import security.RSAEncrypt;

public class ClientSender {

    public static void send(String cipherText, String desKey) throws Exception {
        // Connect to server on localhost port 5000
        try (Socket socket = new Socket("localhost", 5000)) {

            // Create streams
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Receive RSA public key from server
            PublicKey pubKey = (PublicKey) in.readObject();

            // Encrypt DES key with RSA public key
            String encryptedDESKey = RSAEncrypt.encrypt(desKey, pubKey);

            // Send encrypted DES key and cipher text to server
            out.writeUTF(encryptedDESKey);
            out.writeUTF(cipherText);
            out.flush();

            // Streams and socket auto-closed by try-with-resources
        }
    }
}
