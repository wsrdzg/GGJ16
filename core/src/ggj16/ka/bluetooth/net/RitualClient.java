package ggj16.ka.bluetooth.net;

import com.badlogic.gdx.Gdx;

import ggj16.ka.bluetooth.Main;

public class RitualClient extends ClientInterface {
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
