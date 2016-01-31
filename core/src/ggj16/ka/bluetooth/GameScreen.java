package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import ggj16.ka.bluetooth.net.Message;

public class GameScreen extends MyScreen {

    //ParticleEffect particleEffect;

    public static final Array<Symbol> symbols = new Array<>();
    private Label questName;
    private Image god;

    private boolean animationRuns;

    public GameScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, new Color(0.3f, 0.3f, 0.6f, 1), assetManager.get("textures/triangle_main.png", Texture.class));

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mAssetManager.get("font.ttf", BitmapFont.class);

        questName = new Label("", style);
        questName.setAlignment(Align.top, Align.center);
        questName.setBounds(0, Gdx.graphics.getHeight() - style.font.getCapHeight() * 1.5f, Gdx.graphics.getWidth(), style.font.getCapHeight());
        questName.setOrigin(questName.getWidth() / 2f, questName.getHeight() / 2f);
        questName.setFontScale(0.01f);
        questName.setScaleX(0.01f);
        mStage.addActor(questName);

        god = new Image();
        god.setBounds(Gdx.graphics.getWidth() * 0.15f, Gdx.graphics.getHeight() / 2f - Gdx.graphics.getWidth() * 0.35f, Gdx.graphics.getWidth() * 0.7f, Gdx.graphics.getWidth() * 0.7f);
        god.setOrigin(god.getWidth() / 2f, god.getHeight() / 2f);
        god.setScale(0.01f);
        mStage.addActor(god);

        for (Color COLOR : Main.COLORS) {
            for (int j = 0; j < 7; j++) {
                Symbol symbol = new Symbol(assetManager.get("textures/t.atlas", TextureAtlas.class).findRegion("shape", j), COLOR, symbols.size);
                symbol.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //particleEffect.setPosition(x, y);
                        //particleEffect.start();
                        Symbol symbol = (Symbol) event.getListenerActor();
                        symbol.reset();
                        if (QuestFactory.learMode) {
                            if (!QuestFactory.next(symbol)) {
                                out();
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        mMain.setScreen(Main.LOST_SCREEN);
                                    }
                                }, (0.99f / 2f));
                            } else if (QuestFactory.solved) { // TODO: symbols nicht mehr klickbar machen
                                for (Symbol s : QuestFactory.symbols)
                                    s.reset();
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        mMain.setScreen(Main.WIN_SCREEN);
                                    }
                                }, 1);
                            }
                        } else {
                            mMain.ritualClient.sendMessage(new Message(Message.Type.STEP, QuestFactory.position, QuestFactory.next(symbol)));
                        }
                        if (!QuestFactory.solved) {
                            symbol.addAction(Actions.delay(0.3f, new Action() {
                                @Override
                                public boolean act(float delta) {
                                    ((Symbol) getActor()).spawn(QuestFactory.symbols);
                                    return true;
                                }
                            }));
                        }

                        float scale = Math.max(0.01f, 1 - (QuestFactory.position / (float) QuestFactory.symbols.size));
                        out(scale, 0.2f);
                    }
                });
                symbols.add(symbol);
                mStage.addActor(symbol);
            }
        }
    }

    @Override
    public void show() {
        super.show();
        in();

        //particleEffect = new ParticleEffect();
        //particleEffect.load(Gdx.files.internal("particle"), Gdx.files.internal(""));

        startQuest();
    }

    public void startQuest() {
        QuestFactory.startQuest(symbols);

        questName.setText(QuestFactory.learMode ? QuestFactory.god.spell : QuestFactory.god.name);
        questName.setFontScale(0.01f);
        questName.setScaleX(0.01f);

        god.setDrawable(new TextureRegionDrawable(mAssetManager.get("textures/t.atlas", TextureAtlas.class).findRegion("god", QuestFactory.god.male ? 0 : 1)));
        god.setScale(1);
        god.setColor(Color.WHITE);
        god.clearActions();
        //god.setVisible(!QuestFactory.learMode); // TODO
    }

    private void in() {
        questName.clearActions();
        god.clearActions();
        mTriangle.clearActions();

        questName.setFontScale(0.01f);
        questName.setScaleX(0.01f);
        god.setScale(0.01f);
        mTriangle.setScaleX(0.01f);

        animationRuns = true;
        questName.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                float scale = Math.min(1, questName.getFontScaleX() + delta * 2f);
                questName.setFontScale(scale);
                questName.setScaleX(scale);
                god.setScale(scale);
                if (scale == 1) {
                    animationRuns = false;
                    return true;
                }
                return false;
            }
        });
        setTriangleScale(0.99f / 2f, true);
    }

    private void out(final float to, final float duration) {
        animationRuns = true;
        questName.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                float scale = Math.max(to, god.getScaleX() - delta);
                god.setScale(scale);
                return scale == to;
            }
        });
        mTriangle.addAction(Actions.scaleTo(to, 1, duration));
    }

    private void out() {
        animationRuns = true;
        questName.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                float scale = Math.max(0.01f, god.getScaleX() - delta * 2f);
                questName.setFontScale(scale);
                questName.setScaleX(scale);
                god.setScale(scale);
                return scale == 0.01f;
            }
        });
        setTriangleScale(0.99f / 2f, false);
    }
}