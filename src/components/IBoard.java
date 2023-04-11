package components;

import java.util.Stack;

public interface IBoard {
    IBoard getDeepCopy();

    int getSize();

    void placeChess(Pos chessPos, BlackOrWhite color);

    void initBoard();

    void printBoard();

    boolean isPlayerWin();

    boolean isBoardFull();

    Stack<Pos> getValidPos();
}
