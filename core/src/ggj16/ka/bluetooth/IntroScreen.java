package ggj16.ka.bluetooth;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class IntroScreen extends MyScreen {

    public IntroScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.BLUE, assetManager.get("textures/background.png", Texture.class));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("font.ttf", BitmapFont.class);

        Label label = new Label("You are the hero!\nKill everything!\n\n\n\n\n\n\n\n", labelStyle);
        label.setAlignment(Align.center);
        label.setFillParent(true);
        mStage.addActor(label);

        mStage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mMain.setScreen(Storage.loadFirst() ? Main.LEARN_SCREEN : Main.MAIN_SCREEN);
            }
        });
    }
}