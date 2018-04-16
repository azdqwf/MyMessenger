package pomoyka.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class NetConnection {
    private ConnectionThread connectionThread = new ConnectionThread();
    private Consumer<String> onReceiveCallback;


    protected abstract boolean isServer();

    protected abstract String getIp();

    protected abstract int getPort();

    public NetConnection(Consumer<String> onReceiveCallback) {
        this.onReceiveCallback = this.onReceiveCallback;
        connectionThread.setDaemon(true);
    }

    public void startConnection() throws Exception {
        connectionThread.start();
    }

    public void send(String data) throws Exception {
        connectionThread.out.write(data);
    }

    public void closeConnetion() throws Exception {
        connectionThread.socket.close();
    }

    private class ConnectionThread extends Thread {
        private Socket socket;
        private OutputStreamWriter out;


        @Override
        public void run() {
            try (ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                 Socket socket = isServer() ? server.accept() : new Socket(getIp(), getPort());
                 OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                this.socket = socket;
                this.out = out;
                socket.setTcpNoDelay(true);

                while (true) {
                    String data = (String) in.readObject();
                    onReceiveCallback.accept(data);
                }

            } catch (Exception e) {
                onReceiveCallback.accept("NetConnection closed");
            }
        }
    }


}
