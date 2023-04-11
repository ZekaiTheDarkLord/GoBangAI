import components.BlackOrWhite;
import game.IGoBangController;
import game.SimpleGoBangController;
import player.HumanPlayer;
import player.MCTSPlayer;

import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        IGoBangController controller = new SimpleGoBangController(7);
        controller.setPlayers(new HumanPlayer(), new MCTSPlayer(BlackOrWhite.WHITE));
        controller.playGame();
    }
}
