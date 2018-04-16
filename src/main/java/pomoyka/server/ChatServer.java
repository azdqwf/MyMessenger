package pomoyka.server;

import java.util.function.Consumer;

public class ChatServer extends NetConnection {

    private int port;

    public ChatServer(int port, Consumer<String> onReceiveCallback){
        super(onReceiveCallback);
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return true;
    }

    @Override
    protected String getIp() {
        return null;
    }

    @Override
    protected int getPort() {
        return port;
    }
}
