package server;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import security.DESUtil;
import security.RSADecrypt;
import security.RSAKeyManager;

public class Server extends JFrame {

    private JTextField txtDESKey, txtCipher, txtDecrypted;
    private JButton btnDecrypt, btnClear;

    public Server() {
        initGUI();
        startServer();
    }

    private void initGUI() {
        setTitle("SERVER - ABE");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        Color bgColor = new Color(0, 80, 80);
        Color brightYellow = new Color(255, 255, 0);
        Color labelColor = brightYellow;
        Color textFieldBg = new Color(200, 200, 200);

        getContentPane().setBackground(bgColor);

        JLabel lblDESKey = new JLabel("DES Key:");
        lblDESKey.setForeground(labelColor);
        add(lblDESKey);
        txtDESKey = new JTextField();
        txtDESKey.setEditable(false);
        add(txtDESKey);

        JLabel lblCipher = new JLabel("Cipher Text:");
        lblCipher.setForeground(labelColor);
        add(lblCipher);
        txtCipher = new JTextField();
        txtCipher.setEditable(false);
        add(txtCipher);

        JLabel lblDecrypted = new JLabel("Decrypted Text:");
        lblDecrypted.setForeground(labelColor);
        add(lblDecrypted);
        txtDecrypted = new JTextField();
        txtDecrypted.setEditable(false);
        add(txtDecrypted);

        btnDecrypt = new JButton("Decrypt");
        btnDecrypt.addActionListener(e -> decryptAction());
        add(btnDecrypt);

        btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> clearFields());
        add(btnClear);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startServer() {
        new Thread(() -> {
            try {
                RSAKeyManager.generateKeys();
                ServerSocket serverSocket = new ServerSocket(5000);
                System.out.println("Server started. Waiting for clients...");

                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected.");

                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject(RSAKeyManager.getPublicKey());
                    out.flush();

                    // Read encrypted DES key and ciphertext
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    String encryptedDESKey = in.readUTF();
                    String cipherText = in.readUTF();

                    String desKey = RSADecrypt.decrypt(encryptedDESKey, RSAKeyManager.getPrivateKey());

                    SwingUtilities.invokeLater(() -> {
                        txtDESKey.setText(desKey);
                        txtCipher.setText(cipherText);
                        txtDecrypted.setText("");  // clear decrypted text until decrypt pressed
                    });

                    in.close();
                    out.close();
                    socket.close();
                }

            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(this, "Server error: " + e.getMessage())
                );
                e.printStackTrace();
            }
        }).start();
    }

    private void decryptAction() {
        try {
            String desKey = txtDESKey.getText();
            String cipherText = txtCipher.getText();

            if (desKey.isEmpty() || cipherText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "DES Key and Cipher Text required for decryption!");
                return;
            }

            String decrypted = DESUtil.decrypt(cipherText, desKey);
            txtDecrypted.setText(decrypted);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Decryption error: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtDESKey.setText("");
        txtCipher.setText("");
        txtDecrypted.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Server::new);
    }
}
