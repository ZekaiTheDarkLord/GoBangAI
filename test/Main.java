import components.BlackOrWhite;
import game.IGoBangController;
import game.SimpleGoBangController;
import player.HumanPlayer;
import player.MCTSPlayer;
import player.UtilityPlayer;

import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        IGoBangController controller = new SimpleGoBangController(7);
        controller.setPlayers(new UtilityPlayer(BlackOrWhite.BLACK), new HumanPlayer());
        controller.playGame();
    }
}
