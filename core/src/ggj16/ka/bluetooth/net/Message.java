package ggj16.ka.bluetooth.net;


import com.badlogic.gdx.utils.IntArray;

public class Message {
    public enum Type {
        I_KNOW_RITUALS,
        START_GAME,
        STEP,
        LOST,
        NEXT,
        WIN
    }

    public Type t;
    public int i;
    public boolean b;
    public IntArray ia;

    // For initialization
    public Message() {

    }

    public Message(Type type) {
        t = type;
    }
    public Message(Type type, int integer) {
        t = type;
        i = integer;
    }
    public Message(Type type, boolean bool) {
        t = type;
        b = bool;
    }
    public Message(Type type, IntArray array) {
        t = type;
        ia = array;
    }
    public Message(Type type, int integer, boolean bool) {
        t = type;
        i = integer;
        b = bool;
    }

    @Override
    public String toString() {
        if (ia == null) {
            return t + "\n" + i + " / " + b + "/0";
        } else {
            return t + "\n" + i + " / " + b + "/" + ia.size;
        }
    }
}
