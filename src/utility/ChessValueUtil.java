package utility;

import components.IBoard;
import components.Pos;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Objects;

public class ChessValueUtil {
    private static final HashMap<String, Integer> blackChessLinkToValue = new HashMap<>();
    private static final HashMap<String, Integer> whiteChessLinkToValue = new HashMap<>();

    static {
        blackChessLinkToValue.put("#####", 100000);
        blackChessLinkToValue.put("+####+", 5000);
        blackChessLinkToValue.put("#+###", 700);
        blackChessLinkToValue.put("###+#", 700);
        blackChessLinkToValue.put("*####+", 1000);
        blackChessLinkToValue.put("+####*", 1000);
        blackChessLinkToValue.put("##+##", 700);
        blackChessLinkToValue.put("+###+", 500);
        blackChessLinkToValue.put("#+##", 150);
        blackChessLinkToValue.put("##+#", 150);
        blackChessLinkToValue.put("++###*", 100);
        blackChessLinkToValue.put("*###++", 100);
        blackChessLinkToValue.put("+#+##*", 80);
        blackChessLinkToValue.put("*##+#+", 80);
        blackChessLinkToValue.put("+##+#*", 60);
        blackChessLinkToValue.put("*#+##+", 60);
        blackChessLinkToValue.put("#++##", 60);
        blackChessLinkToValue.put("##++#", 60);
        blackChessLinkToValue.put("#+#+#", 60);
        blackChessLinkToValue.put("*+###+*", 60);
        blackChessLinkToValue.put("++##++", 50);
        blackChessLinkToValue.put("+#+#+", 20);
        blackChessLinkToValue.put("#++#", 20);
        blackChessLinkToValue.put("+++##*", 10);
        blackChessLinkToValue.put("*##+++", 10);
        blackChessLinkToValue.put("++#+#*", 10);
        blackChessLinkToValue.put("*#+#++", 10);
        blackChessLinkToValue.put("+#++#*", 10);
        blackChessLinkToValue.put("*#++#+", 10);
        blackChessLinkToValue.put("#+++#", 10);

        whiteChessLinkToValue.put("*****", 10000);
        whiteChessLinkToValue.put("+****+", 5000);
        whiteChessLinkToValue.put("*+***", 700);
        whiteChessLinkToValue.put("***+*", 700);
        whiteChessLinkToValue.put("+****#", 1000);
        whiteChessLinkToValue.put("#****+", 1000);
        whiteChessLinkToValue.put("**+**", 700);
        whiteChessLinkToValue.put("+***+", 500);
        whiteChessLinkToValue.put("*+**", 150);
        whiteChessLinkToValue.put("**+*", 150);
        whiteChessLinkToValue.put("++***#", 100);
        whiteChessLinkToValue.put("#***++", 100);
        whiteChessLinkToValue.put("+*+**#", 80);
        whiteChessLinkToValue.put("#**+*+", 80);
        whiteChessLinkToValue.put("+**+*#", 60);
        whiteChessLinkToValue.put("#*+**+", 60);
        whiteChessLinkToValue.put("*++**", 60);
        whiteChessLinkToValue.put("**++*", 60);
        whiteChessLinkToValue.put("*+*+*", 60);
        whiteChessLinkToValue.put("#+***+#", 60);
        whiteChessLinkToValue.put("++**++", 50);
        whiteChessLinkToValue.put("+*+*+", 20);
        whiteChessLinkToValue.put("*++*", 20);
        whiteChessLinkToValue.put("+++**#", 10);
        whiteChessLinkToValue.put("#**+++", 10);
        whiteChessLinkToValue.put("++*+*#", 10);
        whiteChessLinkToValue.put("#*+*++", 10);
        whiteChessLinkToValue.put("+*++*#", 10);
        whiteChessLinkToValue.put("#*++*+", 10);
        whiteChessLinkToValue.put("*+++*", 10);
    }



    private static boolean assignPositionValue(Pos chessPosition, List<String> chessLink, String[][] board) {
        int value = 0;

        if (!Objects.equals(board[chessPosition.row][chessPosition.col], "+")) return false;
        for (String s : chessLink) {
            if (blackChessLinkToValue.containsKey(s)) {
                value += blackChessLinkToValue.get(s);
            }
        }

        return true;
    }
}
