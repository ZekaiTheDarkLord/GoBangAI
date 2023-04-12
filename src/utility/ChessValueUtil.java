package utility;

import components.BlackOrWhite;
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

    public static List<List<String>> getSettedLocationList(String[][] board, BlackOrWhite currentPlayer,
                                                           Pos pos, int length, int loc) {
        int row = pos.row;
        int col = pos.col;

        List<List<String>> list = new ArrayList<>();

        String currentPlayerChess = currentPlayer == BlackOrWhite.BLACK ? "#" : "*";
        String opponentPlayerChess = currentPlayer == BlackOrWhite.BLACK ? "*" : "#";

        Pos layStart = new Pos(row, col - loc + 1);
        Pos layEnd = new Pos(row, col + length - loc);
        int LayStartCol = layStart.col;
        int LayEndCol = layEnd.col;

        if (inBoard(layStart) && inBoard(layEnd)) {
            List<String> attackList = new ArrayList<>();
            List<String> defendList = new ArrayList<>();

            for (int i = LayStartCol; i <= LayEndCol; i++) {
                if (i == col) {
                    attackList.add(currentPlayerChess);
                } else {
                    attackList.add(board[row][i]);
                }
            }

            for (int i = LayStartCol; i <= LayEndCol; i++) {
                if (i == col) {
                    defendList.add(opponentPlayerChess);
                } else {
                    defendList.add(board[row][i]);
                }
            }

            list.add(attackList);
            list.add(defendList);
        }

        Pos standStart = new Pos(row - loc + 1, col);
        Pos standEnd = new Pos(row + length - loc, col);
        int StandStartRow = standStart.row;
        int StandEndRow = standEnd.row;

        if (inBoard(standStart) && inBoard(standEnd)) {
            List<String> attackList = new ArrayList<>();
            List<String> defendList = new ArrayList<>();

            for (int i = StandStartRow; i <= StandEndRow; i++) {
                if (i == row) {
                    attackList.add(currentPlayerChess);
                } else {
                    attackList.add(board[i][col]);
                }
            }
            for (int i = StandStartRow; i <= StandEndRow; i++) {
                if (i == row) {
                    defendList.add(opponentPlayerChess);
                } else {
                    defendList.add(board[i][col]);
                }
            }
            list.add(attackList);
            list.add(defendList);
        }

        Pos MainStart = new Pos(row - loc + 1, col - loc + 1);
        Pos MainEnd = new Pos(row + length - loc, col + length - loc);

        int MainStartRow = MainStart.row;
        int MainEndRow = MainEnd.row;
        int MainStartCol = MainStart.col;

        if (inBoard(MainEnd) && inBoard(MainStart)) {
            List<String> attackList = new ArrayList<>();
            List<String> defendList = new ArrayList<>();

            for (int rowi = MainStartRow, coli = MainStartCol; rowi <= MainEndRow; rowi++, coli++) {
                if (rowi == row && coli == col) {
                    attackList.add(currentPlayerChess);
                } else {
                    attackList.add(board[rowi][coli]);
                }
            }
            for (int rowi = MainStartRow, coli = MainStartCol; rowi <= MainEndRow; rowi++, coli++) {
                if (rowi == row && coli == col) {
                    defendList.add(opponentPlayerChess);
                } else {
                    defendList.add(board[rowi][coli]);
                }
            }
            list.add(attackList);
            list.add(defendList);
        }

        Pos ViceStart = new Pos(row + loc - 1, col - loc + 1);
        Pos ViceEnd = new Pos(row - length + loc, col + length - loc);

        int ViceStartRow = ViceStart.row;
        int ViceEndRow = ViceEnd.row;
        int ViceStartCol = ViceStart.col;
        if (inBoard(ViceStart) && inBoard(ViceEnd)) {

            List<String> attackList = new ArrayList<>();
            List<String> defendList = new ArrayList<>();

            for (int rowi = ViceStartRow, coli = ViceStartCol; rowi >= ViceEndRow; rowi--, coli++) {
                if (rowi == row && coli == col) {
                    attackList.add(currentPlayerChess);
                } else {
                    attackList.add(board[rowi][coli]);
                }
            }

            for (int rowi = ViceStartRow, coli = ViceStartCol; rowi >= ViceEndRow; rowi--, coli++) {
                if (rowi == row && coli == col) {
                    defendList.add(opponentPlayerChess);
                } else {
                    defendList.add(board[rowi][coli]);
                }
            }

            list.add(attackList);
            list.add(defendList);
        }

        return list;
    }

    private static boolean inBoard(Pos p) {
        return p.row >= 0 && p.row < 15 && p.col >= 0 && p.col < 15;
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
