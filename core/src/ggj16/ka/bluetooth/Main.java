package ggj16.ka.bluetooth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import ggj16.ka.bluetooth.data.Quest;
import ggj16.ka.bluetooth.data.Symbol;

public class Main extends ApplicationAdapter implements GestureDetector.GestureListener {

    static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

    TextureAtlas atlas;
	SpriteBatch batch;
    Array<Symbol> symbols = new Array<>();

    Quest quest;
	
	@Override
	public void create() {
        atlas = new TextureAtlas(Gdx.files.internal("textures/t.atlas"));
		batch = new SpriteBatch();

        for (Color COLOR : COLORS) {
            for (int j = 0; j < 2; j++) {
                Symbol symbol = new Symbol(atlas.findRegion("shape", j), COLOR);
                symbol.setSize(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
                symbol.setOrigin(Gdx.graphics.getWidth() / 20f, Gdx.graphics.getWidth() / 20f);
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
	}

    public void setSymbols() {
        for (int i = 0; i < quest.symbols.size; i++) {
            Symbol symbol = quest.symbols.get(i);
            boolean toNear;
            do {
                symbol.setPosition(MathUtils.random(symbol.getWidth(), Gdx.graphics.getWidth() - symbol.getWidth()),
                                   MathUtils.random(symbol.getWidth(), Gdx.graphics.getHeight() - symbol.getHeight()));
                toNear = false;
                for (int j = 0; j < i; j++) {
                    if (Math.hypot(quest.symbols.get(i).getX() - quest.symbols.get(j).getX(), quest.symbols.get(i).getY() - quest.symbols.get(j).getY()) < symbol.getWidth() * 3) {
                        toNear = true;
                        break;
                    }
                }
            } while (toNear);
            symbol.setRotation(MathUtils.random(-10, 10));
            symbol.scale = MathUtils.random();
            symbol.grow = false;
        }
    }

	@Override
	public void render() {
        float delta = Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(1, 1, 1, 1);
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
            symbol.draw(batch);
        }
		batch.end();
	}

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        for (Symbol symbol : quest.symbols) {
            if (symbol.isTouched(x, y)) {
                symbol.grow = true;
                // TODO: do stuff
            }
        }
        return true;
    }


    // NOT USED
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {return false;}
    @Override
    public boolean longPress(float x, float y) {return false;}
    @Override
    public boolean fling(float velocityX, float velocityY, int button) {return false;}
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {return false;}
    @Override
    public boolean panStop(float x, float y, int pointer, int button) {return false;}
    @Override
    public boolean zoom(float initialDistance, float distance) {return false;}
    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {return false;}
}