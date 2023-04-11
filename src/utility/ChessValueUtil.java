package utility;

import components.IBoard;
import components.Pos;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Objects;

public class ChessValueUtil {
    public static HashMap<String, Integer> chessLinkToValue = new HashMap<>();

    static {
        int highVallue = 10000;

        chessLinkToValue.put("#####", 100000);
        chessLinkToValue.put("+####+", 5000);
        chessLinkToValue.put("#+###", 700);
        chessLinkToValue.put("###+#", 700);
        chessLinkToValue.put("*####+", 1000);
        chessLinkToValue.put("+####*", 1000);
        chessLinkToValue.put("##+##", 700);
        chessLinkToValue.put("+###+", 500);
        chessLinkToValue.put("#+##", 150);
        chessLinkToValue.put("##+#", 150);
        chessLinkToValue.put("++###*", 100);
        chessLinkToValue.put("*###++", 100);
        chessLinkToValue.put("+#+##*", 80);
        chessLinkToValue.put("*##+#+", 80);
        chessLinkToValue.put("+##+#*", 60);
        chessLinkToValue.put("*#+##+", 60);
        chessLinkToValue.put("#++##", 60);
        chessLinkToValue.put("##++#", 60);
        chessLinkToValue.put("#+#+#", 60);
        chessLinkToValue.put("*+###+*", 60);
        chessLinkToValue.put("++##++", 50);
        chessLinkToValue.put("+#+#+", 20);
        chessLinkToValue.put("#++#", 20);
        chessLinkToValue.put("+++##*", 10);
        chessLinkToValue.put("*##+++", 10);
        chessLinkToValue.put("++#+#*", 10);
        chessLinkToValue.put("*#+#++", 10);
        chessLinkToValue.put("+#++#*", 10);
        chessLinkToValue.put("*#++#+", 10);
        chessLinkToValue.put("#+++#", 10);

        chessLinkToValue.put("*****", 10000);
        chessLinkToValue.put("+****+", 5000);
        chessLinkToValue.put("*+***", 700);
        chessLinkToValue.put("***+*", 700);
        chessLinkToValue.put("+****#", 1000);
        chessLinkToValue.put("#****+", 1000);
        chessLinkToValue.put("**+**", 700);
        chessLinkToValue.put("+***+", 500);
        chessLinkToValue.put("*+**", 150);
        chessLinkToValue.put("**+*", 150);
        chessLinkToValue.put("++***#", 100);
        chessLinkToValue.put("#***++", 100);
        chessLinkToValue.put("+*+**#", 80);
        chessLinkToValue.put("#**+*+", 80);
        chessLinkToValue.put("+**+*#", 60);
        chessLinkToValue.put("#*+**+", 60);
        chessLinkToValue.put("*++**", 60);
        chessLinkToValue.put("**++*", 60);
        chessLinkToValue.put("*+*+*", 60);
        chessLinkToValue.put("#+***+#", 60);
        chessLinkToValue.put("++**++", 50);
        chessLinkToValue.put("+*+*+", 20);
        chessLinkToValue.put("*++*", 20);
        chessLinkToValue.put("+++**#", 10);
        chessLinkToValue.put("#**+++", 10);
        chessLinkToValue.put("++*+*#", 10);
        chessLinkToValue.put("#*+*++", 10);
        chessLinkToValue.put("+*++*#", 10);
        chessLinkToValue.put("#*++*+", 10);
        chessLinkToValue.put("*+++*", 10);
    }

    public static boolean assignPositionValue(Pos chessPosition, List<String> chessLink, String[][] board) {
        int value = 0;

        if (!Objects.equals(board[chessPosition.row][chessPosition.col], "+")) return false;
        for (String s : chessLink) {
            if (chessLinkToValue.containsKey(s)) {
                value += chessLinkToValue.get(s);
            }
        }

        return true;
    }
}
