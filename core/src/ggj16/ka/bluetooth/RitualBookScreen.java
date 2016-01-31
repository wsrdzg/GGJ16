package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class RitualBookScreen extends MyScreen {

    private final Table rituals = new Table();

    public RitualBookScreen(Main main, AssetManager assetManager) {
        super(main, assetManager, Color.BLUE, assetManager.get("textures/triangle_main.png", Texture.class));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get("font.ttf", BitmapFont.class);

        Label label = new Label("Your Rituals", labelStyle);
        label.setAlignment(Align.center);
        label.setBounds(0, Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 7f, Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f);
        mStage.addActor(label);

        rituals.setFillParent(true);

        ScrollPane scrollPane = new ScrollPane(rituals);
        scrollPane.setBounds(0, Gdx.graphics.getWidth() / 7f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 7f);
        mStage.addActor(scrollPane);
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
            label.setAlignment(Align.center);
            label.setFontScale(0.7f);
            table.add(label).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 7f).row();

            style = new Label.LabelStyle();
            style.font = mAssetManager.get("font.ttf", BitmapFont.class);
            label = new Label("Helps against " + QuestFactory.god.name, style);
            label.setFontScale(0.5f);
            label.setAlignment(Align.center);
            table.add(label).size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 14f).row();

            Table symboleTable = new Table();
            for (Symbol symbol : QuestFactory.symbols) {
                Image image = new Image(new TextureRegionDrawable((TextureRegionDrawable) symbol.getDrawable()));
                image.setColor(new Color(symbol.getColor()));
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

    public void backPressed() {
        mMain.setScreen(Main.MAIN_SCREEN);
    }
}