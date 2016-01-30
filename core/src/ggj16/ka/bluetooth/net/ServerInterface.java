package ggj16.ka.bluetooth.net;

import com.badlogic.gdx.utils.Array;

public abstract class ServerInterface {

    Array<Client> clients;

    public ServerInterface() {
        clients = new Array<Client>();
    }


    public void clientConnected(Client client) {
        clients.add(client);
    }
    public abstract void messageReceived(Client client, Message message);
    public void sendToAllClients(Message message) {
        for (Client client: clients) {
            client.sendMessage(message);
        }
    }

    public Array<Client> getClients() {
        return clients;
    }
}
