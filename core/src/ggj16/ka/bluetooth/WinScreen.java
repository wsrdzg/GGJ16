package ggj16.ka.bluetooth;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;

public class WinScreen extends MyScreen {

    private final static float DELAY = 2; // In seconds this time

    public WinScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.GREEN);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("font.ttf", BitmapFont.class);
    }

    @Override
    public void show() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                mMain.setScreen(Main.GAME_SCREEN);
            }
        }, DELAY);
    }
}