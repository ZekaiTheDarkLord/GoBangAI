package player;

import java.io.IOException;

import components.IBoard;
import components.Pos;

public interface IPlayer {
    Pos makingDecision(IBoard gameBoard) throws IOException;
}
