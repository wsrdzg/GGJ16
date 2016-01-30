package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class RitualBookScreen extends MyScreen {

    private final Table rituals = new Table();

    public RitualBookScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.BLUE, assetManager.get("textures/background_empty.png", Texture.class));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("font.ttf", BitmapFont.class);

        Label label = new Label("Your Rituals", labelStyle);
        label.setAlignment(Align.center);
        label.setBounds(0, Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 7f, Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f);
        mStage.addActor(label);

        rituals.setFillParent(true);

        ScrollPane scrollPane = new ScrollPane(rituals);
        scrollPane.setBounds(0, Gdx.graphics.getWidth() / 7f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 7f * 2f);
        mStage.addActor(scrollPane);

        label = new Label("Back", labelStyle);
        label.setAlignment(Align.center);
        label.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f);
        label.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mMain.setScreen(Main.MAIN_SCREEN);
            }
        });
        mStage.addActor(label);
    }

    @Override
    public void show() {
        rituals.clearChildren();
        for (int i = 0; i < QuestFactory.myRituals.size; i++) {
            QuestFactory.createQuest(QuestFactory.GODS.get(QuestFactory.myRituals.get(i)).id, false);
            QuestFactory.startQuest(GameScreen.symbols);

            Table table = new Table();

            Label.LabelStyle style = new Label.LabelStyle();
            style.font = mAssetManager.get("font.ttf", BitmapFont.class);
            Label label = new Label(QuestFactory.god.spell, style);
            label.setFontScale(0.7f);
            table.add(label).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f).row();

            style = new Label.LabelStyle();
            style.font = mAssetManager.get("font.ttf", BitmapFont.class);
            label = new Label("Helps against " + QuestFactory.god.name, style);
            label.setFontScale(0.5f);
            table.add(label).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 14f).row();

            Table symboleTable = new Table();
            for (Symbol symbol : QuestFactory.symbols) {
                Image image = new Image(symbol.getDrawable());
                image.setColor(symbol.getColor());
                symboleTable.add(image).size(Gdx.graphics.getWidth() / 7f);
            }
            table.add(symboleTable).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7).row();

            if (i != QuestFactory.myRituals.size - 1) {
                Image image = new Image(mAssetManager.get("textures/t.atlas", TextureAtlas.class).findRegion("pixel"));
                table.add(image).size(Gdx.graphics.getWidth(), 1);
            }

            rituals.add(table);
        }
    }
}