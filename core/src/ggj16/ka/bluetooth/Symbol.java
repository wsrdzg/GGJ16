package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class Symbol extends Image {

    public int id;
    public float scale;
    public boolean scaleDirection, highlight;

    private final Vector2 position = new Vector2();

    public Symbol(TextureRegion region, Color color, int id) {
        super(region);
        setColor(color);
        this.id = id;
        setVisible(false);
    }

    public void spawn(Array<Symbol> symbols) {
        boolean toNear;
        do {
            position.set(MathUtils.random(getWidth(), Gdx.graphics.getWidth() - getWidth()),
                         MathUtils.random(getHeight(), Gdx.graphics.getHeight() - getHeight()));
            toNear = false;
            for (int i = 0; i < symbols.size; i++) {
                if (!symbols.get(i).equals(this) && position.dst(symbols.get(i).position) < getWidth() * 2) {
                    toNear = true;
                    break;
                }
            }
        } while (toNear);
        setPosition(position.x, position.y);
        setRotation(MathUtils.random(-10, 10));
        setHighlight(false);
        scale = MathUtils.random();
        setVisible(true);
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
        if (highlight) {
            setSize(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getWidth() / 3f);
            setOrigin(Gdx.graphics.getWidth() / 6f, Gdx.graphics.getWidth() / 6f);
        } else {
            setSize(Gdx.graphics.getWidth() / 5f, Gdx.graphics.getWidth() / 5f);
            setOrigin(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
        }
    }

    public boolean equals(Symbol symbol) {
        return id == symbol.id;
    }
}