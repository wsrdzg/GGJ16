package ggj16.ka.bluetooth.net;

import com.badlogic.gdx.utils.Array;

public interface NetworkConnection {
    void init();

    void startServer(ServerInterface server, ClientInterface client);

    /**
     * List of devices that this device is paired to and that could possibly be the master.
     * @return
     */
    Array<Device> getPossibleServers();

    /**
     * Start a client.
     * @param client
     * @param serverAddr the address of the server.
     */
    void startClient(ClientInterface client, String serverAddr);

    String getMyAddress();

    void teardown();
}
