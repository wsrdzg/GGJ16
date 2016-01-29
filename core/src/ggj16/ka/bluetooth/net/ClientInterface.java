package ggj16.ka.bluetooth.net;

public abstract class ClientInterface {
    private Server server;
    public void setServer(Server server) {
        this.server = server;
    }
    public void sendMessage(Message message) {
        server.sendMessage(message);
    }
    public abstract void messageReceived(Message message);
}
