package Client;

import security.DESUtil;
import javax.swing.*;
import java.awt.*;
import java.security.SecureRandom;

public class Client extends JFrame {

    private JTextField txtPlaintext, txtKey, txtCipher;
    private JButton btnEncrypt, btnSend, btnClear;

    public Client() {
        initComponents();
    }

    private void initComponents() {
        setTitle("       CLIENT - KEBE");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        Color bgColor = new Color(5, 5, 48);
        Color brightGreen = new Color(0, 255, 0);
        Color buttonPurple = new Color(102, 0, 255);
        Color textFieldBg = new Color(90, 90, 90);
        Color textFieldFg = brightGreen;

        getContentPane().setBackground(bgColor);

        add(new JLabel("   Plaintext:")).setForeground(brightGreen);
        txtPlaintext = new JTextField();
        txtPlaintext.setBackground(textFieldBg);
        txtPlaintext.setForeground(textFieldFg);
        add(txtPlaintext);

        add(new JLabel("   Key (8 chars):")).setForeground(brightGreen);
        txtKey = new JTextField();
        txtKey.setBackground(textFieldBg);
        txtKey.setForeground(textFieldFg);
        txtKey.setEditable(false);  // read-only now
        add(txtKey);

        add(new JLabel("    Encrypted Text:")).setForeground(brightGreen);
        txtCipher = new JTextField();
        txtCipher.setBackground(textFieldBg);
        txtCipher.setForeground(textFieldFg);
        txtCipher.setEditable(false);
        add(txtCipher);

        btnEncrypt = new JButton("Encrypt");
        btnEncrypt.setBackground(buttonPurple);
        btnEncrypt.setForeground(brightGreen);
        btnEncrypt.setFocusPainted(false);
        btnEncrypt.addActionListener(e -> encryptAction());
        add(btnEncrypt);

        btnSend = new JButton("Send to Server");
        btnSend.setEnabled(false);
        btnSend.addActionListener(e -> sendAction());
        add(btnSend);

        btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> clearFields());
        add(btnClear);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String generateRandomDESKey() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            key.append(chars.charAt(random.nextInt(chars.length())));
        }
        return key.toString();
    }

    private void encryptAction() {
        try {
            String plaintext = txtPlaintext.getText().trim();
            if (plaintext.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter plaintext!");
                return;
            }

            String key = generateRandomDESKey();
            txtKey.setText(key);

            String cipher = DESUtil.encrypt(plaintext, key);
            txtCipher.setText(cipher);

            btnSend.setEnabled(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Encryption error: " + e.getMessage());
        }
    }

    private void sendAction() {
        try {
            String cipher = txtCipher.getText();
            String key = txtKey.getText();

            if (cipher.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Encrypt first!");
                return;
            }

            ClientSender.send(cipher, key);
            JOptionPane.showMessageDialog(this, "Data sent to server!");

            btnSend.setEnabled(false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Send error: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtPlaintext.setText("");
        txtKey.setText("");
        txtCipher.setText("");
        btnSend.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }
}
