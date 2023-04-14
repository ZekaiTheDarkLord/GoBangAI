package game;

import player.IPlayer;

import java.io.IOException;

public interface IGoBangController {
    /**
     * Initialize the controller with player AIs.
     * @param playerBlack the AI player of black side .
     * @param playerWhite the AI player of white side.
     * */
    void setPlayers(IPlayer playerBlack, IPlayer playerWhite);

    /**
     * Play the game with settled rules.
     * @throws IOException If input stream error occurs.
     */
    void playGame() throws IOException;
}
