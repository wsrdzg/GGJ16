package ggj16.ka.bluetooth.net;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class ServerConnection implements Runnable {
    BufferedReader reader;
    BufferedWriter writer;
    ServerInterface server;
    Json json;
    Client client;

    public ServerConnection (final BluetoothSocket socket, ServerInterface server) {
        Log.i("BT", "Create ServerConnection");
        final String address = socket.getRemoteDevice().getAddress();
        final String name    = socket.getRemoteDevice().getName();

        this.server = server;
        json = new Json();
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        client = new Client() {
            @Override
            public void sendMessage(Message message) {
                try {
                    Gdx.app.log("Bluetooth", "send Message to Client: " + message);
                    writer.write(json.toJson(message) + "\n");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String getAddress() {
                return address;
            }

            @Override
            public String getName() {
                return name;
            }
        };
        Log.i("BT", "send Client connected");
        server.clientConnected(client);
    }

    public void start() {
        new Thread(this).start();
    }

    public void run() {
        String line;
        try {
            while((line = reader.readLine()) != null) {
                Message message = json.fromJson(Message.class, line);
                server.messageReceived(client, message);
            }
        } catch (IOException e) {

            e.printStackTrace(); // connection stopped

            Log.e("Bluetooth", "connection of " + client.getAddress() + " CLOSED");
        }
    }

}