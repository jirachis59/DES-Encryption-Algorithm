package server;

import java.net.Socket;
import java.io.ObjectInputStream;

public class ServerReceiverGUI extends Thread {

    private Socket socket;
    private Server server;

    public ServerReceiverGUI(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String encryptedDESKey = in.readUTF();
            String cipher = in.readUTF();

            // Send data to Server GUI
            server.setReceivedData(encryptedDESKey, cipher);

            in.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
