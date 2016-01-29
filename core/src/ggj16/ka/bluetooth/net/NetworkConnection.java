package ggj16.ka.bluetooth.net;

public interface NetworkConnection {
    void init();
    void getData();

    void startServer(ServerInterface server);
    void startClient(ClientInterface client);
}
