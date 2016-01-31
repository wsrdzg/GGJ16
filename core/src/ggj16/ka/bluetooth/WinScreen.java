package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

public class WinScreen extends MyScreen {

    private final Label god, ritual;
    private final Table symboleTable;

    public WinScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.GREEN, assetManager.get("textures/triangle_success.png", Texture.class));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("font.ttf", BitmapFont.class);
        if (mMain.isHost) {
            god = new Label("start a new game", labelStyle);
        }else{
            god = new Label("wait for host restart the game", labelStyle);
        }
        god.setAlignment(Align.center);
        god.setBounds(0, Gdx.graphics.getHeight() / 4 * 3f, Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f);
        mStage.addActor(god);

        ritual = new Label(QuestFactory.god.spell, labelStyle);
        ritual.setFontScale(0.5f);
        ritual.setAlignment(Align.center);
        ritual.setBounds(0, Gdx.graphics.getHeight() / 4 * 2.5f, Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f);
        mStage.addActor(ritual);

        symboleTable = new Table();
        symboleTable.setBounds(0, Gdx.graphics.getHeight() / 4 * 2f, Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f);
        mStage.addActor(symboleTable);

        mStage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (QuestFactory.learMode) {
                    Storage.saveFirst();
                    QuestFactory.myRituals.add(QuestFactory.god.id);
                    mMain.setScreen(Main.MAIN_SCREEN);
                } else {
                    if (mMain.isHost) {
                        mMain.ritualServer.startGame();
                    }
                }
            }
        });
    }

    public void show() {
        god.setText(QuestFactory.god.name + " defeated!");
        ritual.setText(QuestFactory.god.spell);

        symboleTable.clearChildren();
        int i = 1;
        for (Symbol symbol : QuestFactory.symbols) {
            Image image = new Image(new TextureRegionDrawable((TextureRegionDrawable) symbol.getDrawable()));
            image.setColor(new Color(symbol.getColor()));
            image.getColor().a = 0;
            image.addAction(Actions.delay(i++ * 0.5f, Actions.fadeIn(0.1f)));
            symboleTable.add(image).size(Gdx.graphics.getWidth() / 7f);
        }
    }

    public void backPressed() {
        if (QuestFactory.learMode) {
            Storage.saveFirst();
            QuestFactory.myRituals.add(QuestFactory.god.id);
        }
        mMain.setScreen(Main.MAIN_SCREEN);
    }
}