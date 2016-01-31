package ggj16.ka.bluetooth;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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

public class Main extends Game implements InputProcessor {

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

    public static NetworkConnection network;
    public boolean isHost;
    public RitualServer ritualServer = new RitualServer();
    public RitualClient ritualClient = new RitualClient();

    public Main(NetworkConnection network) {
        this.network = network;
    }

    @Override
    public void create() {
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);

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
        Gdx.input.setInputProcessor(new InputMultiplexer(this, screens.get(screen).getStage()));
    }

    @Override
    public void pause() {
        super.pause();
        Storage.saveRituals(QuestFactory.myRituals);

        // disconnect network when in game screen
        if (getScreen() == screens.get(GAME_SCREEN) && !QuestFactory.learMode) {
            disconnected(null);
        }
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.BACK:
                ((MyScreen) screen).backPressed();
                break;
        }
        return false;
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




    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void disconnected(String name) {
        // kill the network
        network.teardown();
        if(ritualClient.ping!=null) {
            ritualClient.ping.interrupt();
        }
        if (ritualServer != null && ritualServer.ping != null) {
            ritualServer.ping.interrupt();
        }

        // switch to menu screen
        setScreen(MAIN_SCREEN);
    }

}