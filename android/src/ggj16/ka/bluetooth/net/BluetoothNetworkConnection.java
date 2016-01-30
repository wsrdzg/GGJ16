package ggj16.ka.bluetooth.net;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
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
    private Array<Thread> threads;
    private Array<BluetoothSocket> sockets;

    public BluetoothNetworkConnection(Activity activity) {
        this.activity = activity;
        threads = new Array<>();
        sockets = new Array<>();
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
    public void startServer(final ServerInterface server, final ClientInterface client) {
        // Server has himself as a client
        final Client myselfAsAClient = new Client() {
            @Override
            public void sendMessage(Message message) {
                client.messageReceived(message);
            }

            @Override
            public String getAddress() {
                return adapter.getAddress();
            }

            @Override
            public String getName() {
                return adapter.getName();
            }
        };

        client.setServer(new Server() {
            @Override
            public void sendMessage(Message message) {
                server.messageReceived(myselfAsAClient, message);
            }
        });
        server.clientConnected(myselfAsAClient);
        client.connected();


        // start a server for every bounded device
        for (final BluetoothDevice device : adapter.getBondedDevices()) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Log.i("Bluetooth", "try to create " + device.getName());
                        BluetoothServerSocket serverSocket = adapter.listenUsingRfcommWithServiceRecord("ggj16", genUUID(device.getAddress()));
                        Log.i("Bluetooth", "server for " + device.getName()+" ready");
                        // wait for client
                        BluetoothSocket socket = serverSocket.accept();
                        sockets.add(socket);
                        Log.i("Bluetooth", "client connected");

                        BluetoothDevice remote = socket.getRemoteDevice();
                        Log.i("Bluetooth", "name: " + remote.getName());
                        Log.i("Bluetooth", "address: " + remote.getAddress());

                        serverSocket.close();

                        // we are already in our own thread
                        new ServerConnection(socket, server).run();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Bluetooth", "Server for " + device.getName() + " crashed");
                    }
                }
            });
            threads.add(t);
            t.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void startClient(ClientInterface client, String serverAddr) {
        BluetoothDevice server = null;
        for (BluetoothDevice device : adapter.getBondedDevices()) {
            if ( device.getAddress().equals(serverAddr)) {
                server = device;
                break;
            }
        }

        if (server == null) {
            throw new RuntimeException("NO FITTING SERVER FOUND"); // TODO: handle
        }

        Log.i("Bluetooth", "start client ");
        try {
            BluetoothSocket socket = server.createRfcommSocketToServiceRecord(genUUID(adapter.getAddress()));
            Log.i("Bluetooth", "Waiting for server " + server.getName());
            socket.connect();
            Log.i("Bluetooth", "client connected");

            sockets.add(socket);

            threads.add(new ClientConnection(socket, client).start());

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


    /**
     * Generates the acurate UUID for a mac address.
     * @param addr
     * @return
     */
    public UUID genUUID(String addr) {
        return UUID.fromString("b6461a60-c6c6-11e5-a837-" + addr.replace(":", ""));
    }

    @Override
    public String getMyAddress() {
        return adapter.getAddress();
    }

    @Override
    public Array<Device> getPossibleServers() {
        Array<Device> devices = new Array<>();
        for (final BluetoothDevice device : adapter.getBondedDevices()) {
            devices.add(new Device(device.getName(), device.getAddress()));
        }
        return devices;
    }

    @Override
    public void teardown() {
        for (Thread t: threads) {
            t.interrupt();
        }
        threads.clear();
        for (BluetoothSocket socket: sockets) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
