package ggj16.ka.bluetooth;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;

import ggj16.ka.bluetooth.net.Client;
import ggj16.ka.bluetooth.net.Device;
import ggj16.ka.bluetooth.net.Message;
import ggj16.ka.bluetooth.net.NetworkConnection;
import ggj16.ka.bluetooth.net.RitualClient;
import ggj16.ka.bluetooth.net.RitualServer;

public class Main extends Game {

    public static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

    public static final int INTRO_SCREEN = 0;
    public static final int LEARN_SCREEN = 1;
    public static final int GAME_SCREEN = 2;
    public static final int LOST_SCREEN = 3;
    public static final int WIN_SCREEN = 4;
    public static final int MAIN_SCREEN = 5;
    public static final int RITUAL_BOOK_SCREEN = 6;
    public static final int CONNECTION_SCREEN = 7;

    private final Array<MyScreen> screens = new Array<>();

    private final AssetManager assetManager = new AssetManager();

    public NetworkConnection network;
    public boolean isHost;
    public RitualServer ritualServer = new RitualServer();
    public RitualClient ritualClient = new RitualClient();

    public Main(NetworkConnection network) {
        this.network = network;
    }

    @Override
    public void create() {
        assetManager.load("textures/t.atlas", TextureAtlas.class);
        assetManager.load("textures/background.png", Texture.class);
        assetManager.load("textures/fail.png", Texture.class);
        assetManager.load("textures/success.png", Texture.class);
        assetManager.load("textures/background_empty.png", Texture.class);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter fontParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        fontParams.fontFileName = "font.ttf";
        fontParams.fontParameters.size = Gdx.graphics.getWidth() / 10;
        assetManager.load("font.ttf", BitmapFont.class, fontParams);

        assetManager.finishLoading();

        Storage.loadRituals(QuestFactory.myRituals);

        screens.add(new IntroScreen(this, assetManager));
        screens.add(new LearnScreen(this, assetManager));
        screens.add(new GameScreen(this, assetManager));
        screens.add(new LostScreen(this, assetManager));
        screens.add(new WinScreen(this, assetManager));
        screens.add(new MainScreen(this, assetManager));
        screens.add(new RitualBookScreen(this, assetManager));
        screens.add(new ConnectionScreen(this, assetManager));
        setScreen(INTRO_SCREEN);
    }

    public void setScreen(int screen) {
        super.setScreen(screens.get(screen));
        Gdx.input.setInputProcessor(screens.get(screen).getStage());
    }

    @Override
    public void pause() {
        super.pause();
        Storage.saveRituals(QuestFactory.myRituals);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }



    public void openServer() {
        network.startServer(ritualServer, ritualClient);
    }

    public void getPlayers(Array<String> players) {
        players.clear();
        for (Client client : ritualServer.getClients()) {
            players.add(client.getName());
        }
    }

    public void startClientGames() {
        ritualServer.startGame();
    }


    /**
     * called by server
     */
    public void startGame(int god) {
        QuestFactory.createQuest(god, false);
        setScreen(GAME_SCREEN);
    }

    public Array<Device> getPossibleServers() {
        return network.getPossibleServers();
    }

    public void joinServer(Device device) {
        network.startClient(ritualClient, device.address);

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