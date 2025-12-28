package server;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.net.Socket;

import security.DESUtil;
import security.RSADecrypt;
import security.RSAKeyManager;

public class ServerReceiver extends Thread {

    private Socket socket;
    private JTextField txtDESKey, txtCipher, txtDecrypted;

    private String receivedEncryptedDESKey;
    private String receivedCipher;

    public ServerReceiver(Socket socket, JTextField txtDESKey, JTextField txtCipher, JTextField txtDecrypted) {
        this.socket = socket;
        this.txtDESKey = txtDESKey;
        this.txtCipher = txtCipher;
        this.txtDecrypted = txtDecrypted;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Receive RSA-encrypted DES key
            receivedEncryptedDESKey = in.readUTF();

            // Receive DES-encrypted message
            receivedCipher = in.readUTF();

            // Display cipher text in GUI
            SwingUtilities.invokeLater(() -> txtCipher.setText(receivedCipher));

            in.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Called when "Decrypt" button is pressed
    public void decryptMessage() {
        try {
            if (receivedEncryptedDESKey == null || receivedCipher == null) return;

            String desKey = RSADecrypt.decrypt(receivedEncryptedDESKey, RSAKeyManager.getPrivateKey());
            String decryptedText = DESUtil.decrypt(receivedCipher, desKey);

            SwingUtilities.invokeLater(() -> {
                txtDESKey.setText(desKey);
                txtDecrypted.setText(decryptedText);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
