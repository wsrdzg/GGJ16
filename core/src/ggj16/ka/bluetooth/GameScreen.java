package ggj16.ka.bluetooth;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
    public void dispose() {

    }

    /*ParticleEffect particleEffect;
    TextureAtlas atlas;
    SpriteBatch batch;
    Array<Symbol> symbols = new Array<>();

    Quest quest;

    public void setSymbols() {
        for (int i = 0; i < quest.symbols.size; i++) {
            Symbol symbol = quest.symbols.get(i);
            boolean toNear;
            do {
                symbol.setPosition(MathUtils.random(symbol.getWidth(), Gdx.graphics.getWidth() - symbol.getWidth()),
                        MathUtils.random(symbol.getWidth(), Gdx.graphics.getHeight() - symbol.getHeight()));
                toNear = false;
                for (int j = 0; j < i; j++) {
                    if (Math.hypot(quest.symbols.get(i).getX() - quest.symbols.get(j).getX(), quest.symbols.get(i).getY() - quest.symbols.get(j).getY()) < symbol.getWidth() * 2) {
                        toNear = true;
                        break;
                    }
                }
            } while (toNear);
            symbol.setRotation(MathUtils.random(-10, 10));
            symbol.scale = MathUtils.random();
        }
    }

    @Override
    public void show() {
        atlas = new TextureAtlas(Gdx.files.internal("textures/t.atlas"));

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particle"), Gdx.files.internal(""));

        batch = new SpriteBatch();

        for (Color COLOR : COLORS) {
            for (int j = 0; j < 2; j++) {
                Symbol symbol = new Symbol(atlas.findRegion("shape", j), COLOR);
                symbol.setSize(Gdx.graphics.getWidth() / 5f, Gdx.graphics.getWidth() / 5f);
                symbol.setOrigin(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
                symbols.add(symbol);
            }
        }


        // TODO: load quest
        quest = new Quest();
        quest.id = "Kill Xardas";
        quest.maxTime = 5000;
        quest.symbols = new Array<>();
        quest.symbols.add(symbols.get(2));
        quest.symbols.add(symbols.get(0));
        quest.symbols.add(symbols.get(1));

        setSymbols();

        Gdx.input.setInputProcessor(new GestureDetector(this));
    }

    @Override
    public void render(float delta) {
        float delta = Gdx.graphics.getDeltaTime();

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
                if (symbol.timeUntilSpawn > 2) {

                }
            } else {
                symbol.draw(batch);
            }
        }
        particleEffect.update(delta);
        particleEffect.draw(batch);
        batch.end();
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
                // TODO: do stuff
            }
        }
        return true;
    }*/
}