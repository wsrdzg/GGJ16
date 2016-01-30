package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends MyScreen {

    //ParticleEffect particleEffect;

    Array<Symbol> symbols = new Array<>();
    Label questName;


    Quest quest;
    QuestSolver questSolver;

    public GameScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, new Color(0.3f, 0.3f, 0.6f, 1));

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mAssetManager.get("font.ttf", BitmapFont.class);

        questName = new Label("", style);
        questName.setAlignment(Align.center);
        questName.setBounds(0, Gdx.graphics.getHeight() - style.font.getCapHeight() * 1.5f, Gdx.graphics.getWidth(), style.font.getCapHeight());
        mStage.addActor(questName);

        for (Color COLOR : Main.COLORS) {
            for (int j = 0; j < 7; j++) {
                Symbol symbol = new Symbol(assetManager.get("textures/t.atlas", TextureAtlas.class).findRegion("shape", j), COLOR, symbols.size);
                symbol.setSize(Gdx.graphics.getWidth() / 5f, Gdx.graphics.getWidth() / 5f);
                symbol.setOrigin(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
                symbol.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //particleEffect.setPosition(x, y);
                        //particleEffect.start();
                        Symbol symbol = (Symbol) event.getListenerActor();
                        symbol.setVisible(false);
                        if (!questSolver.next(symbol)) {
                            mMain.setScreen(Main.LOST_SCREEN);
                        } else if (questSolver.solved) {
                            mMain.setScreen(Main.WIN_SCREEN);
                        } else {
                            symbol.addAction(Actions.delay(1, new Action() {
                                @Override
                                public boolean act(float delta) {
                                    ((Symbol) getActor()).spawn(quest.symbols);
                                    return true;
                                }
                            }));
                        }
                    }
                });
                symbol.addAction(new Action() {
                    @Override
                    public boolean act(float delta) {
                        Symbol symbol = (Symbol) getActor();
                        if (symbol.scaleDirection) {
                            symbol.scale += delta;
                            if (symbol.scale > 1)
                                symbol.scaleDirection = false;
                        } else {
                            symbol.scale -= delta;
                            if (symbol.scale < 0)
                                symbol.scaleDirection = true;
                        }
                        symbol.setScale(symbol.scale * 0.1f + 0.9f);
                        return false;
                    }
                });
                symbols.add(symbol);
                mStage.addActor(symbol);
            }
        }
    }

    @Override
    public void show() {
        //particleEffect = new ParticleEffect();
        //particleEffect.load(Gdx.files.internal("particle"), Gdx.files.internal(""));

        Gdx.input.setInputProcessor(mStage);

        startQuest(true);
    }

    public void startQuest(boolean learnMode) {
        for (Symbol symbol : symbols)
            symbol.reset();

        // TODO: load quest
        quest = QuestFactory.getQuest(0, symbols);

        Gdx.app.error("s", "a1");



        Gdx.app.error("s", "a2");

        questName.setText(quest.id);

        Gdx.app.error("s", "a3");

        for (Symbol symbol : quest.symbols)
            symbol.spawn(symbols);

        Gdx.app.error("s", "a4");

        questSolver = new QuestSolver();
        questSolver.quest = quest;
        questSolver.setLearMode(learnMode);
    }
}