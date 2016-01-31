package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MyScreen implements Screen {

    protected Main mMain;
    protected AssetManager mAssetManager;
    protected Stage mStage;
    protected Color mBackgroundColor;

    protected Image mTriangle;

    public MyScreen(Main main, AssetManager assetManager, Color backgroundColor, Texture triangle) {
        this.mMain = main;
        this.mAssetManager = assetManager;
        this.mStage = new Stage();
        this.mBackgroundColor = backgroundColor;

        Image image = new Image(assetManager.get("textures/background.png", Texture.class));
        image.setFillParent(true);
        mStage.addActor(image);

        mTriangle = new Image(triangle);
        mTriangle.setBounds(-Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() * 0.23555f, Gdx.graphics.getWidth() * 2f, Gdx.graphics.getHeight() * (1 - 0.23555f));
        mTriangle.setOrigin(mTriangle.getWidth() / 2f, mTriangle.getHeight() / 2f);
        mStage.addActor(mTriangle);

        //mStage.setDebugAll(true); // TODO: remove
    }

    public void setTriangleScale(float scale) {
        mTriangle.setScaleX(scale);
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

    public void backPressed() {}
}