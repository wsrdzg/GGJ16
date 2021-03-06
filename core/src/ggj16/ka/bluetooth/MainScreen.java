package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import ggj16.ka.bluetooth.net.RitualServer;

public class MainScreen extends MyScreen {

    public MainScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.BLUE, assetManager.get("textures/triangle_main.png", Texture.class));

        Table table = new Table();
        table.setFillParent(true);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("font.ttf", BitmapFont.class);

        Label label = new Label("ANGRY ACTEC", labelStyle); // TODO: change name
        label.setAlignment(Align.center);
        table.add(label).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7).row();

        label = new Label("Ritual Book", labelStyle);
        label.setAlignment(Align.center);
        label.setFontScale(0.7f);
        label.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mMain.setScreen(Main.RITUAL_BOOK_SCREEN);
            }
        });
        table.add(label).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7).row();

        label = new Label("Host Game", labelStyle);
        label.setAlignment(Align.center);
        label.setFontScale(0.7f);
        label.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mMain.isHost = true;
                mMain.setScreen(Main.CONNECTION_SCREEN);
            }
        });
        table.add(label).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7).row();

        label = new Label("Join Game", labelStyle);
        label.setAlignment(Align.center);
        label.setFontScale(0.7f);
        label.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mMain.isHost = false;
                mMain.setScreen(Main.CONNECTION_SCREEN);
            }
        });
        table.add(label).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7).row();

        mStage.addActor(table);
    }
}