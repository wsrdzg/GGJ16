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

public class LearnScreen extends MyScreen {

    private final static String[] text = {"Wisdom is power!", "Learn new rituals\nto defeat\nyour enemies.", "Tap the big runes\nand remember\nthere order."};
    private int step;

    private final Label label;

    public LearnScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.BLUE, assetManager.get("textures/background.png", Texture.class));

        QuestFactory.createQuest(MathUtils.random(QuestFactory.GODS.size - 1), true);


        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("font.ttf", BitmapFont.class);

        label = new Label("", labelStyle);
        label.setAlignment(Align.center);
        label.setFillParent(true);
        label.setText(text[step] + "\n\n\n\n\n\n\n");
        mStage.addActor(label);

        mStage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                step++;
                if (step == text.length) {
                    label.setFontScale(0.7f);
                    label.setText("Your first spell is\n" + QuestFactory.god.spell + "\n" + QuestFactory.god.spellDescription + "\n\n\n\n\n\n");
                } else if (step == text.length + 1) {
                    mMain.setScreen(Main.GAME_SCREEN);
                } else {
                    label.setText(text[step] + "\n\n\n\n\n\n\n");
                }
            }
        });
    }
}