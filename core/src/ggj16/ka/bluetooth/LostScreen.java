package ggj16.ka.bluetooth;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

import ggj16.ka.bluetooth.net.Message;

public class LostScreen extends MyScreen {

    public LostScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.RED, assetManager.get("textures/fail.png", Texture.class));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("font.ttf", BitmapFont.class);

        Label label = new Label("YOU DIED", labelStyle);
        label.setAlignment(Align.center);
        label.setFillParent(true);
        mStage.addActor(label);

        mStage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mMain.setScreen(Main.GAME_SCREEN);
                mMain.ritualClient.sendMessage(new Message(Message.Type.CONTINUE));
            }
        });
    }
}