package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    Array<Client> clients = new Array<>();

    public ConnectionScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.GREEN, assetManager.get("textures/success.png", Texture.class));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("font.ttf", BitmapFont.class);

        Label label = new Label("Users", labelStyle);
        label.setAlignment(Align.center);
        label.setBounds(0, Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 7f, Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f);
        mStage.addActor(label);


        users.setFillParent(true);

        ScrollPane scrollPane = new ScrollPane(users);
        scrollPane.setBounds(0, Gdx.graphics.getWidth() / 7f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 7f * 2f);
        mStage.addActor(scrollPane);

        label = new Label("Start", labelStyle);
        label.setAlignment(Align.center);
        label.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f);
        label.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO: start game (only server)
                if (mMain.isHost) {
                    mMain.startClientGames();
                }
            }
        });
        mStage.addActor(label);
    }

    @Override
    public void show() {


        final Label.LabelStyle style = new Label.LabelStyle();
        style.font = mAssetManager.get("font.ttf", BitmapFont.class);

        if (mMain.isHost) {
            mMain.openServer();

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
                        for (String player : players) {
                            Label label = new Label(player, style);
                            users.add(label).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f).row();
                        }
                    }
                    return false;
                }
            });
        } else {
            for (Device d : mMain.getPossibleServers()) {
                final Device device = d;
                users.clearChildren();
                Label label = new Label(device.name, style);
                label.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        mMain.joinServer(device);
                    }
                });


                users.add(label).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f).row();
            }
        }
    }

    public void hide() {
        users.clearActions();
    }
}