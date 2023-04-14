import components.BlackOrWhite;
import game.IGoBangController;
import game.SimpleGoBangController;
import player.HumanPlayer;
import player.IPlayer;
import player.MCTSPlayer;
import player.UtilityPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        IPlayer blackPlayer = null;
        IPlayer whitePlayer = null;


        boolean canContinue = false;

        while (!canContinue) {
            System.out.println("Please decide the player on black side (H: Human; U: Utility AI; M: MTCS AI): ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String inputPlayerType = br.readLine();

            switch (inputPlayerType) {
                case "H":
                case "h":
                    blackPlayer = new HumanPlayer();
                    canContinue = true;
                    break;
                case "U":
                case "u":
                    blackPlayer = new UtilityPlayer(BlackOrWhite.BLACK);
                    canContinue = true;
                    break;
                case "M":
                case "m":
                    blackPlayer = new MCTSPlayer(BlackOrWhite.BLACK);
                    canContinue = true;
                    break;
                default:
                    System.out.println("Invalid input type for black side player! Please input the player type again.");
            }
        }

        canContinue = false;

        while (!canContinue) {
            System.out.println("Please decide the player on white side(H: Human; U: Utility AI; M: MTCS AI): ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String inputPlayerType = br.readLine();

            switch (inputPlayerType) {
                case "H":
                case "h":
                    whitePlayer = new HumanPlayer();
                    canContinue = true;
                    break;
                case "U":
                case "u":
                    whitePlayer = new UtilityPlayer(BlackOrWhite.WHITE);
                    canContinue = true;
                    break;
                case "M":
                case "m":
                    whitePlayer = new MCTSPlayer(BlackOrWhite.WHITE);
                    canContinue = true;
                    break;
                default:
                    System.out.println("Invalid input type for white side player! Please input the player type again.");
            }
        }

        IGoBangController controller = new SimpleGoBangController(7);
        controller.setPlayers(blackPlayer, whitePlayer);
        controller.playGame();
    }
}
