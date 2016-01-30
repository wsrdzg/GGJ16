package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
        super(main, assetManager, new Color(0.3f, 0.3f, 0.6f, 1), assetManager.get("textures/background.png", Texture.class));

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mAssetManager.get("font.ttf", BitmapFont.class);

        questName = new Label("", style);
        questName.setAlignment(Align.center);
        questName.setBounds(0, Gdx.graphics.getHeight() - style.font.getCapHeight() * 1.5f, Gdx.graphics.getWidth(), style.font.getCapHeight());
        mStage.addActor(questName);

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
                        if (!questSolver.next(symbol)) {
                            mMain.setScreen(Main.LOST_SCREEN);
                        } else if (questSolver.solved) {
                            mMain.setScreen(Main.WIN_SCREEN);
                        } else {
                            symbol.addAction(Actions.delay(0.5f, new Action() {
                                @Override
                                public boolean act(float delta) {
                                    ((Symbol) getActor()).spawn(quest.symbols);
                                    return true;
                                }
                            }));
                        }
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

        startQuest(true);
    }

    public void startQuest(boolean learnMode) {
        for (Symbol symbol : symbols)
            symbol.reset();

        quest = QuestFactory.getQuest(0, symbols);

        questName.setText(quest.id);

        for (Symbol symbol : quest.symbols)
            symbol.spawn(symbols);

        questSolver = new QuestSolver();
        questSolver.quest = quest;
        questSolver.setLearMode(learnMode);
    }
}