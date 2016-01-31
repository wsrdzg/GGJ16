package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class WinScreen extends MyScreen {

    private final Label god, ritual;
    private final Table symboleTable;
    private  final Label startNew;

    private boolean animationRuns;

    public WinScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.GREEN, assetManager.get("textures/triangle_success.png", Texture.class));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("font.ttf", BitmapFont.class);

        god = new Label("God defeated!", labelStyle);
        god.setAlignment(Align.center);
        god.setBounds(0, Gdx.graphics.getHeight() / 4 * 3f, Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f);
        god.setOrigin(god.getWidth() / 2f, god.getHeight() / 2f);
        god.setFontScale(0.01f);
        god.setScale(0.01f);
        mStage.addActor(god);

        ritual = new Label("", labelStyle);
        ritual.setAlignment(Align.center);
        ritual.setBounds(0, Gdx.graphics.getHeight() / 4 * 2.5f, Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f);
        ritual.setOrigin(ritual.getWidth() / 2f, ritual.getHeight() / 2f);
        ritual.setFontScale(0.01f);
        ritual.setScale(0.01f);
        mStage.addActor(ritual);

        symboleTable = new Table();
        symboleTable.setBounds(0, Gdx.graphics.getHeight() / 4 * 2f, Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f);
        symboleTable.setOrigin(symboleTable.getWidth() / 2f, symboleTable.getHeight() / 2f);
        symboleTable.setScale(0.01f);
        mStage.addActor(symboleTable);

            startNew = new Label("Start new game", labelStyle);
            startNew.setAlignment(Align.center);
            startNew.setBounds(0, 0, Gdx.graphics.getWidth() / 8 * 7f, Gdx.graphics.getWidth() / 7f);
        startNew.setOrigin(startNew.getWidth() / 2f, startNew.getHeight() / 2f);
        startNew.setFontScale(0.01f);
        startNew.setScale(0.01f);
            mStage.addActor(startNew);

        mStage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (QuestFactory.learMode) {
                    out();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            Storage.saveFirst();
                            mMain.setScreen(Main.MAIN_SCREEN);
                            QuestFactory.myRituals.add(QuestFactory.god.id);
                        }
                    }, 0.99f / 2f);

                } else {
                    if (mMain.isHost) {
                        mMain.ritualServer.startGame();
                    }
                }
            }
        });
    }

    public void show() {
        super.show();

        god.setText(QuestFactory.god.name + " defeated!");


        startNew.setVisible(!QuestFactory.learMode);
        startNew.setText(mMain.isHost ? "start a new game" : "wait for host");

        Array<Symbol> a = QuestFactory.learMode ? QuestFactory.symbols : QuestFactory.getSymbolsForGod();
        if (a == null) {
            symboleTable.setVisible(false);
            ritual.setVisible(false);
        } else {
            ritual.setText("You learned\n" + (QuestFactory.learMode ? QuestFactory.god.spell : QuestFactory.GODS.get(QuestFactory.newRitual).name));

            symboleTable.clearChildren();
            int i = 1;
            for (Symbol symbol : a) {
                Image image = new Image(new TextureRegionDrawable((TextureRegionDrawable) symbol.getDrawable()));
                image.setColor(new Color(symbol.getColor()));
                image.getColor().a = 0;
                image.addAction(Actions.delay(i++ * 0.5f, Actions.fadeIn(0.1f)));
                symboleTable.add(image).size(Gdx.graphics.getWidth() / 7f);
            }
            symboleTable.setVisible(true);
            ritual.setVisible(true);
        }

        in();
    }

    public void backPressed() {
        out();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (QuestFactory.learMode) {
                    Storage.saveFirst();
                    QuestFactory.myRituals.add(QuestFactory.god.id);
                }
                mMain.setScreen(Main.MAIN_SCREEN);
            }
        }, 0.99f / 2f);
    }

    private void in() {
        animationRuns = true;
        god.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                float scale = Math.min(1, god.getFontScaleX() + delta * 2f);
                god.setFontScale(scale);
                god.setScale(scale);
                ritual.setFontScale(scale * 0.7f);
                ritual.setScale(scale * 0.7f);
                symboleTable.setScale(scale);
                startNew.setFontScale(scale);
                startNew.setScale(scale);
                if (scale == 1) {
                    animationRuns = false;
                    return true;
                }
                return false;
            }
        });
        setTriangleScale(0.99f / 2f, true);
    }

    private void out() {
        animationRuns = true;
        god.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                float scale = Math.max(0.01f, god.getFontScaleX() - delta * 2f);
                god.setFontScale(scale);
                god.setScale(scale);
                ritual.setFontScale(scale * 0.7f);
                ritual.setScale(scale * 0.7f);
                symboleTable.setScale(scale);
                startNew.setFontScale(scale);
                startNew.setScale(scale);
                return scale == 0.01f;
            }
        });
        setTriangleScale(0.99f / 2f, false);
    }

    public void clientGoToGameScreen(final int god) {
        out();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                QuestFactory.createQuest(god, false);
                mMain.setScreen(Main.GAME_SCREEN);
            }
        }, 0.99f / 2f);
    }
}