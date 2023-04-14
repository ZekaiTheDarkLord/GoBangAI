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

            double attackRatio = currentPlayer == BlackOrWhite.BLACK ?
                    0.7 : 0.4;
            double defenseRatio = 1.0 - attackRatio;

            List<String> stringChessChain = getAllChessChains(board, currentPlayer, pos);

            for (String chain : stringChessChain) {
                int attackScore = currentPlayer == BlackOrWhite.BLACK ?
                        blackChessChainToValue.getOrDefault(chain, 0) :
                        whiteChessChainToValue.getOrDefault(chain, 0);
                int defenseScore = currentPlayer == BlackOrWhite.BLACK ?
                        whiteChessChainToValue.getOrDefault(chain, 0) :
                        blackChessChainToValue.getOrDefault(chain, 0);
                posValue += attackScore * attackRatio + defenseScore * defenseRatio;
            }

            return posValue;
        }
    }

    private static List<String> getAllChessChains(String[][] board, BlackOrWhite currentPlayer, Pos pos) {
        List<String> chessChain = new ArrayList<>();

        for (int i = 4; i <= 7; i++) {
            for (int j = 1; j <= i; j++) {
                List<String> singleChessChainList = getSingleLengthChessChain(board, currentPlayer, pos, i, j);
                if (!singleChessChainList.isEmpty()) chessChain.addAll(singleChessChainList);
            }
        }

        return chessChain;
    }

    private static List<String> getSingleLengthChessChain(String[][] board, BlackOrWhite currentPlayer,
                                                          Pos pos, int chainLength, int loc) {
        int chessRowIndex = pos.row;
        int chessColIndex = pos.col;

        List<String> result = new ArrayList<>();

        String currentPlayerChess = currentPlayer == BlackOrWhite.BLACK ? "#" : "*";
        String opponentPlayerChess = currentPlayer == BlackOrWhite.BLACK ? "*" : "#";

        Pos horizontalStartPos = new Pos(chessRowIndex, chessColIndex - loc + 1);
        Pos horizontalEndPos = new Pos(chessRowIndex, chessColIndex + chainLength - loc);
        int horizontalStartColIndex = horizontalStartPos.col;
        int horizontalEndColIndex = horizontalEndPos.col;

        iterateHorizontalOrVertical(board, result, horizontalStartPos, horizontalEndPos, horizontalStartColIndex, horizontalEndColIndex,
                currentPlayerChess, opponentPlayerChess, chessRowIndex, true);

        Pos verticalStartPos = new Pos(chessRowIndex - loc + 1, chessColIndex);
        Pos verticalEndPos = new Pos(chessRowIndex + chainLength - loc, chessColIndex);
        int verticalStartRowIndex = verticalStartPos.row;
        int verticalEndRowIndex = verticalEndPos.row;

        iterateHorizontalOrVertical(board, result, verticalStartPos, verticalEndPos, verticalStartRowIndex, verticalEndRowIndex,
                currentPlayerChess, opponentPlayerChess, chessColIndex, false);

        Pos MainStart = new Pos(chessRowIndex - loc + 1, chessColIndex - loc + 1);
        Pos MainEnd = new Pos(chessRowIndex + chainLength - loc, chessColIndex + chainLength - loc);

        int mainStartRowIndex = MainStart.row;
        int mainEndRowIndex = MainEnd.row;
        int mainStartColIndex = MainStart.col;

        if (startEndInBoard(MainStart, MainEnd)) {
            StringBuilder currentPlayerPossibleChain = new StringBuilder();
            StringBuilder opponentPlayerPossibleChain = new StringBuilder();

            for (int row = mainStartRowIndex, col = mainStartColIndex; row <= mainEndRowIndex; row++, col++) {
                if (row == chessRowIndex && col == chessColIndex) {
                    currentPlayerPossibleChain.append(currentPlayerChess);
                } else {
                    currentPlayerPossibleChain.append(board[row][col]);
                }
            }

            for (int row = mainStartRowIndex, col = mainStartColIndex; row <= mainEndRowIndex; row++, col++) {
                if (row == chessRowIndex && col == chessColIndex) {
                    opponentPlayerPossibleChain.append(opponentPlayerChess);
                } else {
                    opponentPlayerPossibleChain.append(board[row][col]);
                }
            }

            result.add(currentPlayerPossibleChain.toString());
            result.add(opponentPlayerPossibleChain.toString());
        }

        Pos viceStartPos = new Pos(chessRowIndex + loc - 1, chessColIndex - loc + 1);
        Pos viceEndPos = new Pos(chessRowIndex - chainLength + loc, chessColIndex + chainLength - loc);

        int viceStartRowIndex = viceStartPos.row;
        int viceEndRowIndex = viceEndPos.row;
        int viceStartColIndex = viceStartPos.col;

        if (startEndInBoard(viceStartPos, viceEndPos)) {

            StringBuilder currentPlayerPossibleChain = new StringBuilder();
            StringBuilder opponentPlayerPossibleChain = new StringBuilder();

            for (int row = viceStartRowIndex, col = viceStartColIndex; row >= viceEndRowIndex; row--, col++) {
                if (row == chessRowIndex && col == chessColIndex) {
                    currentPlayerPossibleChain.append(currentPlayerChess);
                } else {
                    currentPlayerPossibleChain.append(board[row][col]);
                }
            }

            for (int row = viceStartRowIndex, col = viceStartColIndex; row >= viceEndRowIndex; row--, col++) {
                if (row == chessRowIndex && col == chessColIndex) {
                    opponentPlayerPossibleChain.append(opponentPlayerChess);
                } else {
                    opponentPlayerPossibleChain.append(board[row][col]);
                }
            }

            result.add(currentPlayerPossibleChain.toString());
            result.add(opponentPlayerPossibleChain.toString());
        }

        return result;
    }

    private static void iterateHorizontalOrVertical(String[][] board, List<String> result,
                                                    Pos startPos, Pos endPos,
                                                    int startIndex, int endIndex,
                                                    String currentPlayerChess, String opponentPlayerChess,
                                                    int chessIndex, boolean isRowIndex) {
        if (startEndInBoard(startPos, endPos)) {
            StringBuilder currentPlayerPossibleChain = new StringBuilder();
            StringBuilder opponentPlayerPossibleChain = new StringBuilder();

            appendListHelper(board, startIndex, endIndex, currentPlayerChess, chessIndex, isRowIndex, currentPlayerPossibleChain);

            appendListHelper(board, startIndex, endIndex, opponentPlayerChess, chessIndex, isRowIndex, opponentPlayerPossibleChain);

            result.add(currentPlayerPossibleChain.toString());
            result.add(opponentPlayerPossibleChain.toString());
        }
    }

    private static void appendListHelper(String[][] board, int startIndex, int endIndex, String currentPlayerChess,
                                         int chessIndex, boolean isRowIndex, StringBuilder possibleChessChain) {
        for (int i = startIndex; i <= endIndex; i++) {
            if (i == chessIndex) {
                possibleChessChain.append(currentPlayerChess);
            } else {
                if (isRowIndex) {
                    possibleChessChain.append(board[chessIndex][i]);
                } else {
                    possibleChessChain.append(board[i][chessIndex]);
                }
            }
        }
    }

    private static boolean startEndInBoard(Pos start, Pos end) {
        return inBoard(start) && inBoard(end);
    }

    private static boolean inBoard(Pos p) {
        return p.row >= 0 && p.row < 7 && p.col >= 0 && p.col < 7;
    }
}
