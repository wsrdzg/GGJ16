package ggj16.ka.bluetooth.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by sebastian on 29.01.16.
 */
public class Symbol extends Sprite {

    public int id;
    public float scale;
    public boolean scaleDirection, grow;

    public Symbol(TextureRegion region, Color color) {
        super(region);
        setColor(color);
    }

    public boolean isTouched(float x, float y) {
        return Math.hypot(getX() - x, getY() - y) < getWidth() / 2f;
    }

    public boolean equals(Symbol symbol) {
        return id == symbol.id;
    }
}