package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

public class LearnScreen extends MyScreen {

    private final static String[] text = {"You are the hero!\nKill everything!", "Learn new rituals\nto defeat\nyour enemies.", "Tap the big runes\nand remember\nthere order."};
    private int step;

    private final Label label;

    private boolean animationRuns;

    public LearnScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.BLUE, assetManager.get("textures/triangle_main.png", Texture.class));

        QuestFactory.createQuest(MathUtils.random(4), true);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("font.ttf", BitmapFont.class);

        label = new Label("", labelStyle);
        label.setAlignment(Align.center);
        label.setText(text[step]);
        label.setBounds(Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 2f, Gdx.graphics.getWidth() / 20f * 18f, Gdx.graphics.getHeight() / 2f);
        label.setOrigin(label.getWidth() / 2f, label.getHeight() / 2f);
        label.setFontScale(0.01f);
        label.setScaleX(0.01f);
        mStage.addActor(label);

        mStage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!animationRuns) {
                    out();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            step++;
                            if (step == text.length) {
                                label.setText("Your first ritual is\n\n" + QuestFactory.god.spell + "\n\nHelps against\n" + QuestFactory.god.name);
                            } else if (step == text.length + 1) {
                                mMain.setScreen(Main.GAME_SCREEN);
                            } else {
                                label.setText(text[step]);
                            }
                            in();
                        }
                    }, 0.99f / 2f);
                }
            }
        });
    }

    @Override
    public void show() {
        super.show();
        in();
    }

    private void in() {
        animationRuns = true;
        label.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                float scale = Math.min(1, label.getFontScaleX() + delta * 2f);
                label.setFontScale(scale);
                label.setScaleX(scale);
                if (scale == 1) {
                    animationRuns = false;
                    return true;
                }
                return false;
            }
        });
        setTriangleScale(0.99f / 2f, true);
    }

    private void out() {
        animationRuns = true;
        label.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                float scale = Math.max(0.01f, label.getFontScaleX() - delta * 2f);
                label.setFontScale(scale);
                label.setScaleX(scale);
                return scale == 0.01f;
            }
        });
        setTriangleScale(0.99f / 2f, false);
    }
}