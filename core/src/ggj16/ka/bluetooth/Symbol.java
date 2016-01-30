package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class Symbol extends Image {

    public final int id;
    private float scale;
    private boolean scaleDirection;

    private final Vector2 position = new Vector2();

    public Symbol(TextureRegion region, Color color, int id) {
        super(region);
        setColor(color);
        this.id = id;
        scale = MathUtils.random();
        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Symbol symbol = (Symbol) getActor();
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
                return false;
            }
        });
    }

    public void spawn(Array<Symbol> symbols) {
        float size = Gdx.graphics.getWidth() / 5f * 1.5f;
        boolean toNear;
        do {
            position.set(MathUtils.random(0, Gdx.graphics.getWidth() - size),
                         MathUtils.random(0, Gdx.graphics.getHeight() - size - (Gdx.graphics.getWidth() / 10 * 1.5f)));
            toNear = false;
            for (int i = 0; i < symbols.size; i++) {
                if (!symbols.get(i).equals(this) && position.dst(symbols.get(i).position) < size) {
                    toNear = true;
                    break;
                }
            }
        } while (toNear);
        setPosition(position.x, position.y);
        setRotation(MathUtils.random(-10, 10));
        setVisible(true);
    }

    public void setHighlight(boolean highlight) {
        float size = Gdx.graphics.getWidth() / 5f;
        if (highlight) {
            setSize(size * 1.5f, size * 1.5f);
            setOrigin(size / 2f * 1.5f, size / 2f * 1.5f);
        } else {
            setSize(size, size);
            setOrigin(size / 2f, size / 2f);
        }
    }

    public void reset() {
        Action action = getActions().first();
        getActions().clear();
        getActions().add(action);
        setVisible(false);
        setPosition(-1000, -1000);
        setHighlight(false);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        position.set(x, y);
    }

    public boolean equals(Symbol symbol) {
        return id == symbol.id;
    }
}