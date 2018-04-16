package pomoyka.client;

import pomoyka.server.NetConnection;

import java.util.function.Consumer;

public class ChatClient extends NetConnection {
    private String ip;
    private int port;

    public ChatClient(String ip, int port, Consumer<String> onReceiveCallback){
        super(onReceiveCallback);
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return true;
    }

    @Override
    protected String getIp() {
        return ip;
    }

    @Override
    protected int getPort() {
        return port;
    }
}
