package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class LearnScreen extends MyScreen {

    private final static String[] text = {"You are the hero!\nKill everything!", "Knowledge is power!", "Learn new rituals\nto defeat\nyour enemies.", "Tap the big runes\nand remember\nthere order."};
    private int step;

    private final Label label;

    public LearnScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.BLUE, assetManager.get("textures/triangle_main.png", Texture.class));

        QuestFactory.createQuest(MathUtils.random(QuestFactory.GODS.size - 1), true);




        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("font.ttf", BitmapFont.class);

        label = new Label("", labelStyle);
        label.setAlignment(Align.center);
        label.setText(text[step]);
        label.setWrap(true);
        label.setFontScale(0.8f);
        label.setBounds(Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 2f, Gdx.graphics.getWidth() / 20f * 18f, Gdx.graphics.getHeight() / 2f);

        label.setFontScale(0.01f);
        label.addAction(new Action() {
            float time = 0.01f;

            @Override
            public boolean act(float delta) {
                time = Math.min(2, delta + time);
                label.setFontScale(time / 2f);
                return time == 2;
            }
        });
        //label.addAction(Actions.scaleTo(1, 1, 2));
        mTriangle.setScale(0.01f, 1);
        mTriangle.addAction(Actions.scaleTo(1, 1, 2));


        mStage.addActor(label);

        mStage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                step++;
                if (step == text.length) {
                    label.setText("Your first ritual is\n\n" + QuestFactory.god.spell + "\n\nHelps against " + QuestFactory.god.name);
                } else if (step == text.length + 1) {
                    mMain.setScreen(Main.GAME_SCREEN);
                } else {
                    label.setText(text[step]);
                }
            }
        });
    }
}