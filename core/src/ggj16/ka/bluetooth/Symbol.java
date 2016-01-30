package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by sebastian on 29.01.16.
 */
public class Symbol extends Sprite {

    public int id;
    public float scale, timeUntilSpawn;
    public boolean scaleDirection;

    public Symbol(TextureRegion region, Color color, int id) {
        super(region);
        setColor(color);
        this.id = id;
    }

    public boolean isTouched(float x, float y) {
        return Math.hypot(getX() + getOriginX() - x, getY() + getOriginY() - y) < getWidth() / 2f;
    }

    public void spawn(Array<Symbol> symbols) {
        boolean toNear;
        do {
            setPosition(MathUtils.random(getWidth(), Gdx.graphics.getWidth() - getWidth()),
                        MathUtils.random(getHeight(), Gdx.graphics.getHeight() - getHeight()));
            toNear = false;
            for (int i = 0; i < symbols.size; i++) {
                if (!symbols.get(i).equals(this) && Math.hypot(getX() - symbols.get(i).getX(), getY() - symbols.get(i).getY()) < getWidth() * 2) {
                    toNear = true;
                    break;
                }
            }
        } while (toNear);
        setRotation(MathUtils.random(-10, 10));
        scale = MathUtils.random();
    }

    public boolean equals(Symbol symbol) {
        return id == symbol.id;
    }
}