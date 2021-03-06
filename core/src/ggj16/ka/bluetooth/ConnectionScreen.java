package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import ggj16.ka.bluetooth.net.Client;
import ggj16.ka.bluetooth.net.Device;
import ggj16.ka.bluetooth.net.RitualClient;
import ggj16.ka.bluetooth.net.RitualServer;

public class ConnectionScreen extends MyScreen {

    Table users = new Table();
    Label button;
    Label title;

    public ConnectionScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.GREEN, assetManager.get("textures/triangle_main.png", Texture.class));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("font.ttf", BitmapFont.class);

        title = new Label("Users", labelStyle);
        title.setAlignment(Align.center);
        title.setBounds(0, Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 7f, Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f);
        mStage.addActor(title);


        users.setFillParent(true);

        ScrollPane scrollPane = new ScrollPane(users);
        scrollPane.setBounds(0, Gdx.graphics.getWidth() / 7f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 7f * 2f);
        mStage.addActor(scrollPane);

        button = new Label("Start Game", labelStyle);
        button.setAlignment(Align.center);
        button.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (mMain.isHost)
                    mMain.startClientGames();
            }
        });
        mStage.addActor(button);
    }

    @Override
    public void show() {
        final Label.LabelStyle style = new Label.LabelStyle();
        style.font = mAssetManager.get("font.ttf", BitmapFont.class);

        button.setVisible(mMain.isHost);
        if (mMain.isHost) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mMain.openServer();
                }
            }).start();

            users.addAction(new Action() {

                float time;

                @Override
                public boolean act(float delta) {
                    time += delta;
                    if (time >= 1) {
                        time = 0;
                        users.clearChildren();
                        Array<String> players = new Array<>();
                        mMain.getPlayers(players);
                        Image image = new Image(mAssetManager.get("textures/t.atlas", TextureAtlas.class).findRegion("pixel"));
                        int i = 0;
                        for (String player : players) {
                            Label label = new Label(player, style);
                            users.add(label).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f).row();
                            i++;
                            if (i != players.size - 1)
                                users.add(image).size(Gdx.graphics.getWidth(), 1).row();
                        }
                    }
                    return false;
                }
            });
        } else {

            title.setText("Select host");

            users.clearChildren();
            for (Device d : mMain.getPossibleServers()) {
                final Device device = d;
                Label label = new Label(device.name, style);
                label.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        users.clear();
                        final Label l = new Label("Connecting...", style);
                        users.add(l).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f).row();
                        // register callback
                        mMain.ritualClient.connectedCallback = new Runnable() {
                            @Override
                            public void run() {
                                l.setText("Connected.");
                            }
                        };
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mMain.joinServer(device);
                            }
                        }).start();
                    }
                });

                users.add(label).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f).row();
            }
        }
    }

    public void hide() {
        users.clearActions();
        mMain.ritualClient.connectedCallback = null;
    }

    public void backPressed() {
        mMain.disconnected(null);
        mMain.setScreen(Main.MAIN_SCREEN);
    }
}