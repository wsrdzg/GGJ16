package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends MyScreen {

    //ParticleEffect particleEffect;

    Array<Symbol> symbols = new Array<>();

    Quest quest;
    QuestSolver questSolver;

    public GameScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, new Color(0.3f, 0.3f, 0.6f, 1));

        for (Color COLOR : Main.COLORS) {
            for (int j = 0; j < 2; j++) {
                Symbol symbol = new Symbol(assetManager.get("textures/t.atlas", TextureAtlas.class).findRegion("shape", j), COLOR, symbols.size);
                symbol.setSize(Gdx.graphics.getWidth() / 5f, Gdx.graphics.getWidth() / 5f);
                symbol.setOrigin(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
                symbol.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Symbol symbol = (Symbol) event.getListenerActor();
                        //particleEffect.setPosition(x, y);
                        //particleEffect.start();
                        symbol.timeUntilSpawn = 0;
                        symbol.setPosition(-100, -100);
                        // TODO: do stuff
                        if (!questSolver.next(symbol)) {
                            mMain.setScreen(Main.LOST_SCREEN);
                        } else if (questSolver.solved) {
                            mMain.setScreen(Main.WIN_SCREEN);
                        } else {
                            symbol.addAction(new Action() {
                                @Override
                                public boolean act(float delta) {
                                    Symbol symbol = (Symbol) getActor();
                                    symbol.spawn(mStage, quest.symbols);
                                    return true;
                                }
                            });
                        }
                    }
                });
                symbols.add(symbol);
            }
        }
    }

    @Override
    public void show() {
        //particleEffect = new ParticleEffect();
        //particleEffect.load(Gdx.files.internal("particle"), Gdx.files.internal(""));

        Gdx.input.setInputProcessor(mStage);

        startQuest();
    }

    /*@Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (Symbol symbol : quest.symbols) {
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
            if (symbol.getX() < 0) {
                symbol.timeUntilSpawn += delta;
                if (symbol.timeUntilSpawn > 2)
                    symbol.spawn(mStage, quest.symbols);
            } else {
                symbol.draw(batch);
            }
        }
        particleEffect.update(delta);
        particleEffect.draw(batch);
        // TODO: render quest name
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, quest.id);
        font.draw(batch, quest.id, (Gdx.graphics.getWidth() - glyphLayout.width) / 2f, Gdx.graphics.getHeight() - font.getCapHeight());
        batch.end();
    }*/

    public void startQuest() {
        // TODO: load quest
        quest = new Quest();
        quest.id = "Kill Xardas";
        quest.maxTime = 5000;
        quest.symbols = new Array<>();
        quest.symbols.add(symbols.get(2));
        quest.symbols.add(symbols.get(0));
        quest.symbols.add(symbols.get(1));
        quest.resetScale();
        questSolver = new QuestSolver();
        questSolver.quest = quest;


        mStage.clear();

        for (Symbol symbol : quest.symbols)
            symbol.spawn(mStage, symbols);
    }
}