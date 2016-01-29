package ggj16.ka.bluetooth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public class Main extends Game {

    public static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

    public  static final int GAME_SCREEN = 0;

    private Array<Screen> screens = new Array<>();

    @Override
    public void create() {
        screens.add(new GameScreen());
        setScreen(GAME_SCREEN);
    }

    public void setScreen(int screen) {
        setScreen(screens.get(screen));
    }
}