package ggj16.ka.bluetooth.net;

public class RitualClient extends ClientInterface {
    @Override
    public void messageReceived(Message message) {
        switch (message.t) {
            case START_GAME:
                // server started the game
                int quest = message.i;
                // TODO: Main.setScreen(GAME_SCREEN)
                break;
            case LOST:
                // we lost the game
                // TODO: Main.setScreen(LOST_SCREEN)
                break;
            case WIN:
                // we won the game
                // TODO: Main.setScreen(WIN_SCREEN)
                break;
        }
    }
}
