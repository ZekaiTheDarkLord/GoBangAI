package game;

import player.IPlayer;

import java.io.IOException;

public interface IGoBangController {
    void setPlayers(IPlayer playerBlack, IPlayer playerWhite);

    void playGame() throws IOException;
}
