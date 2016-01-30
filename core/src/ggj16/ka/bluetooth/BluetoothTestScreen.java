package ggj16.ka.bluetooth;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ggj16.ka.bluetooth.net.Client;
import ggj16.ka.bluetooth.net.ClientInterface;
import ggj16.ka.bluetooth.net.Message;
import ggj16.ka.bluetooth.net.ServerInterface;

public class BluetoothTestScreen extends MyScreen {

    SpriteBatch batch;
    BitmapFont font;

    String log = "";

    // is a bluetooth thing running?
    boolean started;

    ClientInterface client;
    ServerInterface server;


    public BluetoothTestScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.BLUE, assetManager.get("textures/background.png", Texture.class));
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void show() {
        mMain.network.init();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        font.setColor(Color.WHITE);
        font.draw(batch, mMain.network.getMyAddress(), 10, 100);
        font.draw(batch, "SERVER", Gdx.graphics.getWidth()/5*1, 50);
        font.draw(batch, "CLIENT", Gdx.graphics.getWidth()/5*4, 50);
        font.draw(batch, Gdx.graphics.getFramesPerSecond()+ "fps", 0,20);

        font.draw(batch, log, 0, Gdx.graphics.getHeight()*0.8f);

        batch.end();

        if (Gdx.input.isTouched()) {
            log("touched: " + started);
        }

        if (Gdx.input.isTouched()) {

            if ( Gdx.input.getY() <Gdx.graphics.getHeight()/2)        {
                if (server != null) {
                    server.sendToAllClients(new Message(321, "SERVER HIER"));
                } else if (client != null) {
                    client.sendMessage(new Message(123, "HALLO HIER SPRICHT EIN CLIENT"));
                } else {
                    log ("please connect first");
                }
            } else
            if(!started) {
                started = true;
                if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
                    log("start server");
                    server = new ServerInterface() {

                        @Override
                        public void clientConnected(Client client) {
                            log("client connected");
                            Gdx.app.log("Main", "client connected");
                            super.clientConnected(client);
                            client.sendMessage(new Message(0, "INITIAL"));
                        }

                        @Override
                        public void messageReceived(Client client, Message message) {
                            log("received message: " + message.message + ", " + message.number + " from " + client.getAddress());
                            Gdx.app.log("Main", "received message: " + message.message + ", " + message.number);
                            // client.sendMessage(new Message(message.number + 1, "from server"));
                        }
                    };
                    mMain.network.startServer(server);
                } else {
                    log("start client");

                    client = new ClientInterface() {
                        @Override
                        public void messageReceived(Message message) {
                            log("message received: " + message.message + "," + message.number);
                            Gdx.app.log("Main", "received message: " + message.message + ", " + message.number);

                            sendMessage(new Message(message.number + 1, "from client"));
                        }
                    };

                    mMain.network.startClient(client, "60:D9:A0:56:45:ED");
                }
            }
        }
    }

    private void log(String l) {
        log += l + "\n";
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
