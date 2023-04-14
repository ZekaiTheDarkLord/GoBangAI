package game;

import components.BlackOrWhite;
import player.IPlayer;

import java.io.IOException;

public interface IGoBangModel {
    /**
     * Get the deep copy of the model.
     * @return A IGoBangModel that is a deep copy of the current model.
     */
    IGoBangModel getDeepCopy();

    /**
     * Get the player that is currently taking the turn.
     * @return A Enum that represent the side of the current player.
     */
    BlackOrWhite getCurrentPlayer();

    /**
     * Setup the player in the model.
     * @param player the player AI.
     * @param side the side of the AI.
     */
    void setupPlayer(IPlayer player, BlackOrWhite side);

    /**
     * Start the game.
     */
    void startGame();

    /**
     * Let the current player make the decision.
     * @throws IOException Human player may have input error.
     */
    void currentPlayerTakeTurn() throws IOException;

    /**
     * Switch to next player.
     */
    void switchPlayerTakeTurn();

    /**
     * Determine whether the game is end or not.
     * @return A boolean represents whether the game end or not.
     */
    boolean isGameEnd();

    /**
     * Simply visualize the game.
     */
    void visualizeGame();
}
