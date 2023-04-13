package utility;

import components.BlackOrWhite;
import components.IBoard;
import components.Pos;

import java.util.*;

public class ChessValueUtil {
    private static final HashMap<String, Integer> blackChessChainToValue = new HashMap<>();
    private static final HashMap<String, Integer> whiteChessChainToValue = new HashMap<>();

    static {
        blackChessChainToValue.put("#####", 100000);
        blackChessChainToValue.put("+####+", 5000);
        blackChessChainToValue.put("#+###", 700);
        blackChessChainToValue.put("###+#", 700);
        blackChessChainToValue.put("*####+", 1000);
        blackChessChainToValue.put("+####*", 1000);
        blackChessChainToValue.put("##+##", 700);
        blackChessChainToValue.put("+###+", 500);
        blackChessChainToValue.put("#+##", 150);
        blackChessChainToValue.put("##+#", 150);
        blackChessChainToValue.put("++###*", 100);
        blackChessChainToValue.put("*###++", 100);
        blackChessChainToValue.put("+#+##*", 80);
        blackChessChainToValue.put("*##+#+", 80);
        blackChessChainToValue.put("+##+#*", 60);
        blackChessChainToValue.put("*#+##+", 60);
        blackChessChainToValue.put("#++##", 60);
        blackChessChainToValue.put("##++#", 60);
        blackChessChainToValue.put("#+#+#", 60);
        blackChessChainToValue.put("*+###+*", 60);
        blackChessChainToValue.put("++##++", 50);
        blackChessChainToValue.put("+#+#+", 20);
        blackChessChainToValue.put("#++#", 20);
        blackChessChainToValue.put("+++##*", 10);
        blackChessChainToValue.put("*##+++", 10);
        blackChessChainToValue.put("++#+#*", 10);
        blackChessChainToValue.put("*#+#++", 10);
        blackChessChainToValue.put("+#++#*", 10);
        blackChessChainToValue.put("*#++#+", 10);
        blackChessChainToValue.put("#+++#", 10);

        whiteChessChainToValue.put("*****", 10000);
        whiteChessChainToValue.put("+****+", 5000);
        whiteChessChainToValue.put("*+***", 700);
        whiteChessChainToValue.put("***+*", 700);
        whiteChessChainToValue.put("+****#", 1000);
        whiteChessChainToValue.put("#****+", 1000);
        whiteChessChainToValue.put("**+**", 700);
        whiteChessChainToValue.put("+***+", 500);
        whiteChessChainToValue.put("*+**", 150);
        whiteChessChainToValue.put("**+*", 150);
        whiteChessChainToValue.put("++***#", 100);
        whiteChessChainToValue.put("#***++", 100);
        whiteChessChainToValue.put("+*+**#", 80);
        whiteChessChainToValue.put("#**+*+", 80);
        whiteChessChainToValue.put("+**+*#", 60);
        whiteChessChainToValue.put("#*+**+", 60);
        whiteChessChainToValue.put("*++**", 60);
        whiteChessChainToValue.put("**++*", 60);
        whiteChessChainToValue.put("*+*+*", 60);
        whiteChessChainToValue.put("#+***+#", 60);
        whiteChessChainToValue.put("++**++", 50);
        whiteChessChainToValue.put("+*+*+", 20);
        whiteChessChainToValue.put("*++*", 20);
        whiteChessChainToValue.put("+++**#", 10);
        whiteChessChainToValue.put("#**+++", 10);
        whiteChessChainToValue.put("++*+*#", 10);
        whiteChessChainToValue.put("#*+*++", 10);
        whiteChessChainToValue.put("+*++*#", 10);
        whiteChessChainToValue.put("#*++*+", 10);
        whiteChessChainToValue.put("*+++*", 10);
    }

    public static Double[][] getChessValueBoard(IBoard gameBoard, BlackOrWhite currentPlayer) {
        String[][] board = gameBoard.getBoard();
        Double[][] chessValueBoard = new Double[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                chessValueBoard[row][col] = getPosValue(board, currentPlayer, new Pos(row, col));
            }
        }

        return chessValueBoard;
    }

    private static double getPosValue(String[][] board, BlackOrWhite currentPlayer, Pos pos) {
        if (!Objects.equals(board[pos.row][pos.col], "+")) {
            return -1;
        } else {
            double posValue = 0.0;

            List<String> stringChessChain = getStringChessChain(board, currentPlayer, pos);

            for (String chain : stringChessChain) {
                int attackScore = currentPlayer == BlackOrWhite.BLACK ?
                        blackChessChainToValue.getOrDefault(chain, 0) :
                        whiteChessChainToValue.getOrDefault(chain, 0);
                int defenseScore = currentPlayer == BlackOrWhite.BLACK ?
                        whiteChessChainToValue.getOrDefault(chain, 0) :
                        blackChessChainToValue.getOrDefault(chain, 0);
                posValue+=attackScore * 0.7 + defenseScore * 0.3;
            }

            return posValue;
        }
    }

    private static List<String> getStringChessChain(String[][] board, BlackOrWhite currentPlayer, Pos pos) {
        List<String> stringChessChains = new ArrayList<>();
        List<List<String>> allChessChains = getAllChessChains(board, currentPlayer, pos);

        for (List<String> chain : allChessChains) {
            stringChessChains.add(chessChainToString(chain));
        }

        return stringChessChains;
    }

    private static String chessChainToString(List<String> list) {
        StringBuilder pool = new StringBuilder();
        for (String chessColor : list) {
            pool.append(chessColor);
        }
        return pool.toString();
    }

    private static List<List<String>> getAllChessChains(String[][] board, BlackOrWhite currentPlayer, Pos pos) {
        List<List<String>> chessChain = new ArrayList<>();

        for (int i = 4; i <= 7; i++) {
            for (int j = 1; j <= i; j++) {
                List<List<String>> singleChessChainList = getLocationList(board, currentPlayer, pos, i, j);
                if (!singleChessChainList.isEmpty()) chessChain.addAll(singleChessChainList);
            }
        }

        return chessChain;
    }

    private static List<List<String>> getLocationList(String[][] board, BlackOrWhite currentPlayer,
                                                      Pos pos, int chainLength, int loc) {
        int row = pos.row;
        int col = pos.col;

        List<List<String>> result = new ArrayList<>();

        String currentPlayerChess = currentPlayer == BlackOrWhite.BLACK ? "#" : "*";
        String opponentPlayerChess = currentPlayer == BlackOrWhite.BLACK ? "*" : "#";

        Pos layStart = new Pos(row, col - loc + 1);
        Pos layEnd = new Pos(row, col + chainLength - loc);
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

            result.add(attackList);
            result.add(defendList);
        }

        Pos standStart = new Pos(row - loc + 1, col);
        Pos standEnd = new Pos(row + chainLength - loc, col);
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
            result.add(attackList);
            result.add(defendList);
        }

        Pos MainStart = new Pos(row - loc + 1, col - loc + 1);
        Pos MainEnd = new Pos(row + chainLength - loc, col + chainLength - loc);

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
            result.add(attackList);
            result.add(defendList);
        }

        Pos ViceStart = new Pos(row + loc - 1, col - loc + 1);
        Pos ViceEnd = new Pos(row - chainLength + loc, col + chainLength - loc);

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

            result.add(attackList);
            result.add(defendList);
        }

        return result;
    }

    private static boolean inBoard(Pos p) {
        return p.row >= 0 && p.row < 7 && p.col >= 0 && p.col < 7;
    }
}
