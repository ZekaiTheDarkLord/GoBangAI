package player;

import java.io.IOException;

import components.IBoard;
import components.Pos;


public interface IPlayer {
    /**
     * Making a decision depends on the current input board.
     * @param gameBoard the current game board.
     * @return the position that player want to place chess on.
     * @throws IOException Human player may have input error.
     */
    Pos makingDecision(IBoard gameBoard) throws IOException;
}
