package ggj16.ka.bluetooth.net;

public interface Client {
    void sendMessage(Message message);
    String getAddress();
}
