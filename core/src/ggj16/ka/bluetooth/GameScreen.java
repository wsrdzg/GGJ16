package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen, GestureDetector.GestureListener {

    ParticleEffect particleEffect;
    TextureAtlas atlas;
    SpriteBatch batch;
    Array<Symbol> symbols = new Array<>();

    Quest quest;
    QuestSolver questSolver;
    Main main;

    public GameScreen(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        atlas = new TextureAtlas(Gdx.files.internal("textures/t.atlas"));

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particle"), Gdx.files.internal(""));

        batch = new SpriteBatch();

        for (Color COLOR : Main.COLORS) {
            for (int j = 0; j < 2; j++) {
                Symbol symbol = new Symbol(atlas.findRegion("shape", j), COLOR, symbols.size);
                symbol.setSize(Gdx.graphics.getWidth() / 5f, Gdx.graphics.getWidth() / 5f);
                symbol.setOrigin(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
                symbols.add(symbol);
            }
        }

        Gdx.input.setInputProcessor(new GestureDetector(this));

        startQuest();
    }

    @Override
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
                    symbol.spawn(quest.symbols);
            } else {
                symbol.draw(batch);
            }
        }
        particleEffect.update(delta);
        particleEffect.draw(batch);
        // TODO: render quest name
        batch.end();
    }

    @Override
    public void dispose() {
        particleEffect.dispose();
        batch.dispose();
        atlas.dispose();
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        y = Gdx.graphics.getHeight() - y;
        for (Symbol symbol : quest.symbols) {
            if (symbol.isTouched(x, y)) {
                particleEffect.setPosition(x, y);
                particleEffect.start();
                symbol.timeUntilSpawn = 0;
                symbol.setPosition(-100, -100);
                // TODO: do stuff
                if (!questSolver.next(symbol)) {
                    main.setScreen(main.screens.get(main.LOST_SCREEN));
                } else if (questSolver.solved) {
                    main.setScreen(main.screens.get(main.WIN_SCREEN));
                }
            }
        }
        return true;
    }

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

        for (Symbol symbol : quest.symbols)
            symbol.spawn(symbols);
    }







    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}