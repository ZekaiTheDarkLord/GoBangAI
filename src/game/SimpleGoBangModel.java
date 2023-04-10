package game;

import components.BlackOrWhite;
import components.IBoard;
import components.Pos;
import components.SimpleBoard;
import player.IPlayer;

import java.io.IOException;

public class SimpleGoBangModel implements IGoBangModel {
    private final IBoard gameBoard;
    private BlackOrWhite currentSide;
    private IPlayer playerBlack;
    private IPlayer playerWhite;

    public SimpleGoBangModel(int inputSize) {
        gameBoard = new SimpleBoard(inputSize);
    }

    @Override
    public IGoBangModel getDeepCopy() {
        return null;
    }

    @Override
    public BlackOrWhite getCurrentPlayer() {
        if (currentSide == BlackOrWhite.BLACK) {
            return BlackOrWhite.BLACK;
        } else if (currentSide == BlackOrWhite.WHITE) {
            return BlackOrWhite.WHITE;
        }

        throw new IllegalArgumentException("Invalid current player!");
    }

    @Override
    public void setupPlayer(IPlayer player, BlackOrWhite side) {
        if (side == BlackOrWhite.BLACK) {
            playerBlack = player;
        } else if (side == BlackOrWhite.WHITE) {
            playerWhite = player;
        }
    }

    @Override
    public void startGame() {
        gameBoard.initBoard();
        currentSide = BlackOrWhite.BLACK;
        System.out.println("The game starts.");
    }

    @Override
    public void currentPlayerTakeTurn() throws IOException {
        Pos placePosition;

        if (currentSide == BlackOrWhite.BLACK) {
            placePosition = playerBlack.makingDecision(gameBoard);
            gameBoard.placeChess(placePosition, currentSide);
        } else if (currentSide == BlackOrWhite.WHITE) {
            placePosition = playerWhite.makingDecision(gameBoard);
            gameBoard.placeChess(placePosition, currentSide);
        }
    }

    @Override
    public void switchPlayerTakeTurn() {
        if (currentSide == BlackOrWhite.BLACK) {
            currentSide = BlackOrWhite.WHITE;
        } else if (currentSide == BlackOrWhite.WHITE) {
            currentSide = BlackOrWhite.BLACK;
        }
    }

    @Override
    public boolean isGameEnd() {
        return gameBoard.isGameOver();
    }

    @Override
    public void visualizeGame() {
        gameBoard.printBoard();
    }
}
