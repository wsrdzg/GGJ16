package ggj16.ka.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.IntArray;

/**
 * Created by sebastian on 30.01.16.
 */
public class Storage {

    private final static String NAME = "ggj";

    public static void saveFirst() {
        Preferences prefs = Gdx.app.getPreferences(NAME);
        prefs.putBoolean("first", false);
        prefs.flush();
    }

    public static boolean loadFirst() {
        return Gdx.app.getPreferences(NAME).getBoolean("first", true);
    }

    public static void saveRituals(IntArray rituals) {
        Preferences prefs = Gdx.app.getPreferences(NAME);
        for (int i = 0; i < rituals.size; i++)
            prefs.putInteger("ritual_" + i, rituals.get(i));
        prefs.flush();
    }

    public static void loadRituals(IntArray rituals) {
        Preferences prefs = Gdx.app.getPreferences(NAME);
        for (int i = 0; i < rituals.size; i++) {
            int result = prefs.getInteger("ritual_" + i, -1);
            if (result == -1)
                return;
            rituals.add(result);
        }
    }
}