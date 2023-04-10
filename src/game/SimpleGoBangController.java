package game;


import components.BlackOrWhite;
import player.IPlayer;

import java.io.IOException;

public class SimpleGoBangController implements IGoBangController {
    boolean hasSetPlayer;
    IGoBangModel gameModel;

    public SimpleGoBangController(int boardSize) {
        this.gameModel = new SimpleGoBangModel(boardSize);
    }

    @Override
    public void setPlayers(IPlayer playerBlack, IPlayer playerWhite) {
        gameModel.setupPlayer(playerBlack, BlackOrWhite.BLACK);
        gameModel.setupPlayer(playerWhite, BlackOrWhite.WHITE);
    }

    @Override
    public void playGame() throws IOException {
        gameModel.startGame();

        while(true) {
            String currentPlayer = (gameModel.getCurrentPlayer() == BlackOrWhite.BLACK) ? "BLACK(#)" : "WHITE(*)";
            System.out.println("It is " + currentPlayer + "'s term");

            while(true) {
                try {
                    gameModel.visualizeGame();
                    gameModel.currentPlayerTakeTurn();
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (gameModel.isGameEnd()) {
                System.out.println("Game over! " + currentPlayer + "side wins.");
                break;
            }

            gameModel.switchPlayerTakeTurn();
        }
    }
}
