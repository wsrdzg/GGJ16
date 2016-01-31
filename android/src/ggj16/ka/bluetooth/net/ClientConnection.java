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

class ClientConnection implements Runnable {
    BufferedReader reader;
    BufferedWriter writer;
    ServerInterface server;
    Json json;
    ClientInterface client;

    public ClientConnection (BluetoothSocket socket, final ClientInterface client) {
        this.client = client;

        json = new Json();
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.setServer(new Server() {
            @Override
            public void sendMessage(Message message) {
                try {
                    Gdx.app.log("Bluetooth", "send Message to server: " + message);
                    writer.write(json.toJson(message) + "\n");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    client.disconnected();
                }
            }
        });

        client.connected();

    }

    public Thread start() {
        Thread t = new Thread(this);
        t.start();
        return t;
    }

    public void run() {
        String line;
        try {
            while((line = reader.readLine()) != null) {
                Message message = json.fromJson(Message.class, line);
                client.messageReceived(message);
            }
        } catch (IOException e) {
            Log.e("ClientConnection", "disconnected from server");
            e.printStackTrace();
            client.disconnected();
        }
    }

}

