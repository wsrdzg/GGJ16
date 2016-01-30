package ggj16.ka.bluetooth;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.Array;
import ggj16.ka.bluetooth.net.NetworkConnection;

public class Main extends Game {

    public static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

    public  static final int GAME_SCREEN = 0;
    public  static final int MENU_SCREEN = 1;
    public  static final int LOST_SCREEN = 2;
    public  static final int WIN_SCREEN = 3;

    public Array<Screen> screens = new Array<>();

    private final AssetManager assetManager = new AssetManager();


    private NetworkConnection network;

    public Main(NetworkConnection network) {
        this.network = network;
    }

    @Override
    public void create() {
        assetManager.load("textures/t.atlas", TextureAtlas.class);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter size1Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        size1Params.fontFileName = "font.ttf";
        size1Params.fontParameters.size = 10;
        assetManager.load("font.ttf", BitmapFont.class, size1Params);

        assetManager.finishLoading();


        screens.add(new GameScreen(this, assetManager));
        screens.add(new MenuScreen(this, assetManager));
        screens.add(new LostScreen(this, assetManager));
        screens.add(new WinScreen(this, assetManager));
        setScreen(GAME_SCREEN);
    }

    public void setScreen(int screen) {
        setScreen(screens.get(screen));
    }
}


/*<<<<<<< HEAD

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
=======
*/