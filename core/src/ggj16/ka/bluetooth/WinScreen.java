package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by floatec on 29/01/16.
 */
public class WinScreen implements Screen {
    TextureAtlas atlas;
    SpriteBatch batch;
    Main game;
    private float delay = 2; //In seconds this time

    public WinScreen(Main game){
        super();
        this.game=game;
    }
    @Override
    public void show() {

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(game.screens.get(game.GAME_SCREEN));
            }
        }, delay);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 255f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
}
