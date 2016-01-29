package ggj16.ka.bluetooth.net;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class BluetoothNetworkConnection implements NetworkConnection {

    private Activity activity;
    private BluetoothAdapter adapter;
    private UUID uuid;


    public BluetoothNetworkConnection(Activity activity) {
        this.activity = activity;
        uuid = UUID.fromString("b6461a60-c6c6-11e5-a837-0800200c9a66");
    }

    @Override
    public void init() {
        adapter = BluetoothAdapter.getDefaultAdapter();

        // enable bluetooth
        Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(turnOn, 0);

        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();

        System.out.println(pairedDevices.size());
        for (BluetoothDevice device : pairedDevices) {
            System.out.println(device.getAddress());
            System.out.println(device.getName());
        }

    }

    @Override
    public void getData() {

    }

    @Override
    public void startServer(ServerInterface server) {
        try {
            BluetoothServerSocket serverSocket = adapter.listenUsingRfcommWithServiceRecord("ggj16", uuid);
            Log.i("Bluetooth", "server ready");
            // wait for client
            BluetoothSocket socket = serverSocket.accept();
            Log.i("Bluetooth", "client connected");

            BluetoothDevice remote = socket.getRemoteDevice();
            Log.i("Bluetooth", "name: " + remote.getName());
            Log.i("Bluetooth", "address: " + remote.getAddress());

            new ServerConnection(socket, server).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void startClient(ClientInterface client) {
        Iterator<BluetoothDevice> iterator = adapter.getBondedDevices().iterator();
        iterator.next();
        BluetoothDevice server = iterator.next();
        Log.i("Bluetooth", "start client ");
        try {
            BluetoothSocket socket = server.createRfcommSocketToServiceRecord(uuid);
            Log.i("Bluetooth", "Waiting for server " + server.getName());
            socket.connect();
            Log.i("Bluetooth", "client connected");

            new ClientConnection(socket, client).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // if we established a connection to the server
    private void handleClient(BluetoothSocket socket) {
        Log.i("Bluetooth", "handle Client");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                Log.i("BluetoothClient", "received " + line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // if we established a connection to a client
    private void handleServer(BluetoothSocket socket, ServerInterface server) {
        Log.i("Bluetooth", "handle Server");

        BufferedReader reader;
        BufferedWriter writer;

        Client client = new Client() {
            @Override
            public void sendMessage(Message message) {

            }
        };



    }

    class ServerConnection implements Runnable {
        BufferedReader reader;
        BufferedWriter writer;
        ServerInterface server;
        Json json;
        Client client;

        public ServerConnection (BluetoothSocket socket, ServerInterface server) {
            Log.i("BT", "Create ServerConnection");
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
                        Gdx.app.log("Bluetooth", "send Message to Client: " + message.message);
                        writer.write(json.toJson(message) + "\n");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
            }
        }

    }

    class ClientConnection implements Runnable {
        BufferedReader reader;
        BufferedWriter writer;
        ServerInterface server;
        Json json;
        ClientInterface client;

        public ClientConnection (BluetoothSocket socket, ClientInterface client) {
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
                        Gdx.app.log("Bluetooth", "send Message to server: " + message.message);
                        writer.write(json.toJson(message) + "\n");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void start() {
            new Thread(this).start();
        }

        public void run() {
            String line;
            try {
                while((line = reader.readLine()) != null) {
                    Message message = json.fromJson(Message.class, line);
                    client.messageReceived(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
