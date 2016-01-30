package ggj16.ka.bluetooth.net;

public class Message {
    public enum Type {
        I_KNOW_RITUALS,
        START_GAME,
        STEP,
        LOST,
        WIN
    }

    public Type t;
    public int i;
    public boolean b;
    public int[] ia;

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
    public Message(Type type, int[] array) {
        t = type;
        ia = array;
    }
    public Message(Type type, int integer, boolean bool) {
        t = type;
        i = integer;
        b = bool;
    }

}
