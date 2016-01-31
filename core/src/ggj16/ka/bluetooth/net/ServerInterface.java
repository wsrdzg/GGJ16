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
        for (int i = 0; i < clients.size; i++) {
            clients.get(i).sendMessage(message);
        }
    }

    public Array<Client> getClients() {
        return clients;
    }

    public abstract void disconnected(String name);
}
