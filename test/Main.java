import game.IGoBangController;
import game.SimpleGoBangController;
import player.HumanPlayer;

import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        IGoBangController controller = new SimpleGoBangController(7);
        controller.setPlayers(new HumanPlayer(), new HumanPlayer());
        controller.playGame();
    }
}