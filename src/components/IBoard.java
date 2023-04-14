package components;

import java.util.Stack;

public interface IBoard {
    /**
     * Get the deep copy of current game board.
     * @return the deep copy of IBoard.
     */
    IBoard getDeepCopy();

    /**
     * Get the deep copy of the board in the current game board class.
     * @return deep copy of a board.
     */
    String[][] getBoard();

    /**
     * Get the size of the current game board.
     * @return the size of the current board.
     */
    int getSize();

    /**
     * Place the chess at the given position with the given color.
     * @param chessPos the position chess want to place
     * @param color the color of the placing chess
     */
    void placeChess(Pos chessPos, BlackOrWhite color);

    /**
     * Initialize the board with all empty space.
     */
    void initBoard();

    /**
     * Visualize the current game board.
     */
    void printBoard();

    /**
     * Determine whether there is a player win the game.
     * @return A boolean represent there is a winner in the game.
     */
    boolean isPlayerWin();

    /**
     * Determine whether the board is full of chess.
     * @return A boolean represents the board is full or not.
     */
    boolean isBoardFull();

    /**
     * Get all empty position in a board.
     * @return All valid positions that players can place chess on.
     */
    Stack<Pos> getValidPos();
}
