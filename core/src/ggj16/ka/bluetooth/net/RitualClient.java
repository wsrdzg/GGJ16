package ggj16.ka.bluetooth.net;

import com.badlogic.gdx.Gdx;

import ggj16.ka.bluetooth.Main;
import ggj16.ka.bluetooth.QuestFactory;

public class RitualClient extends ClientInterface {
    public Thread ping;
    @Override
    public void connected() {
        Gdx.app.log("RitualClient", "rituals: " + QuestFactory.myRituals + " ("+QuestFactory.myRituals.size);
        sendMessage(new Message(Message.Type.I_KNOW_RITUALS, QuestFactory.myRituals));


        // keep the connection alive
        ping = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    sendMessage(new Message(Message.Type.PING));
                }
            }
        });
        ping.start();
    }

    @Override
    public void messageReceived(Message message) {
        switch (message.t) {
            case START_GAME:
                // server started the game
                int quest = message.i;
                ((Main)Gdx.app.getApplicationListener()).startGame(quest);
                break;
            case LOST:
                // we lost the game
                ((Main)Gdx.app.getApplicationListener()).setScreen(Main.LOST_SCREEN);
                break;
            case WIN:
                // we won the game
                ((Main)Gdx.app.getApplicationListener()).setScreen(Main.WIN_SCREEN);
                break;
            case PONG:
                Gdx.app.log("RitualClient", "PONG from server");
                break;
        }
    }

    @Override
    public void disconnected() {
        ((Main)Gdx.app.getApplicationListener()).disconnected();
    }
}
