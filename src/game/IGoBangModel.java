package game;

import components.BlackOrWhite;
import player.IPlayer;

import java.io.IOException;

public interface IGoBangModel {
    IGoBangModel getDeepCopy();

    BlackOrWhite getCurrentPlayer();

    void setupPlayer(IPlayer player, BlackOrWhite side);

    void startGame();

    void currentPlayerTakeTurn() throws IOException;

    void switchPlayerTakeTurn();

    boolean isGameEnd();

    void visualizeGame();
}
