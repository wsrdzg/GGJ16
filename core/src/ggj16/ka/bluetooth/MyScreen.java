package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MyScreen implements Screen {

    protected Main mMain;
    protected AssetManager mAssetManager;
    protected Stage mStage;
    protected Color mBackgroundColor;

    public MyScreen(Main main, AssetManager assetManager, Color backgroundColor, Texture backgroud) {
        this.mMain = main;
        this.mAssetManager = assetManager;
        this.mStage = new Stage();
        this.mBackgroundColor = backgroundColor;

        Image image = new Image(backgroud);
        image.setFillParent(true);
        mStage.addActor(image);

        mStage.setDebugAll(true); // TODO: remove
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(mBackgroundColor.r, mBackgroundColor.g, mBackgroundColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mStage.act(delta);
        mStage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    public Stage getStage() {
        return mStage;
    }

    @Override
    public void dispose() {
        mStage.dispose();
    }
}