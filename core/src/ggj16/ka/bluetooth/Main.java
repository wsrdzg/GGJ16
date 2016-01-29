package ggj16.ka.bluetooth;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ggj16.ka.bluetooth.net.Client;
import ggj16.ka.bluetooth.net.ClientInterface;
import ggj16.ka.bluetooth.net.Message;
import ggj16.ka.bluetooth.net.NetworkConnection;
import ggj16.ka.bluetooth.net.ServerInterface;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

    private NetworkConnection network;

    public Main(NetworkConnection network) {
        this.network = network;
    }


	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

        network.init();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() < Gdx.graphics.getWidth()/2) {
                network.startServer(new ServerInterface() {

                    @Override
                    public void clientConnected(Client client) {
                        Gdx.app.log("Main", "client connected");
                        super.clientConnected(client);
                        client.sendMessage(new Message(0, "INITIAL"));
                    }

                    @Override
                    public void messageReceived(Client client, Message message) {
                        Gdx.app.log("Main", "received message: " + message.message + ", " + message.number);
                        client.sendMessage(new Message(message.number + 1, "from server"));
                    }
                });
            } else {
                network.startClient(new ClientInterface() {
                    @Override
                    public void messageReceived(Message message) {
                        Gdx.app.log("Main", "received message: " + message.message + ", " + message.number);

                        sendMessage(new Message(message.number + 1, "from client"));
                    }
                });
            }
        }
	}
}
