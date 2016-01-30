package ggj16.ka.bluetooth;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

public class WinScreen extends MyScreen {

    public WinScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.GREEN, assetManager.get("textures/success.png", Texture.class));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("title.ttf", BitmapFont.class);

        Label label = new Label("ENEMY DEFEATED\n\n", labelStyle);
        label.setAlignment(Align.center);
        label.setFillParent(true);
        mStage.addActor(label);

        mStage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (QuestFactory.learMode) {
                    Storage.saveFirst();
                    QuestFactory.myRituals.add(QuestFactory.god.id);
                    mMain.setScreen(Main.MAIN_SCREEN);
                } else {
                    QuestFactory.createQuest(MathUtils.random(QuestFactory.GODS.size - 1), true); // TODO: server must decide level
                    mMain.setScreen(Main.GAME_SCREEN);
                }
            }
        });
    }
}