package components;

import java.util.List;
import java.util.Stack;

public interface IBoard {
    IBoard getDeepCopy();

    int getSize();

    void placeChess(Pos chessPos, BlackOrWhite color);

    void initBoard();

    void printBoard();

    boolean isGameOver();

    Stack<Pos> getValidPos();
}
