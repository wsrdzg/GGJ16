package ggj16.ka.bluetooth.net;

import com.badlogic.gdx.Gdx;

import ggj16.ka.bluetooth.Main;
import ggj16.ka.bluetooth.QuestFactory;

public class RitualClient extends ClientInterface {
    @Override
    public void connected() {
        sendMessage(new Message(Message.Type.I_KNOW_RITUALS, QuestFactory.myRituals));
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
        }
    }
}
