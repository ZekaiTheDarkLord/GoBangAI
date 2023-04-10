package components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class SimpleBoard implements IBoard {
    private String[][] board;
    private final int size;
    private boolean hasInit;

    public SimpleBoard(int inputSize) {
        this.size = inputSize;
        this.hasInit = false;
    }

    public SimpleBoard(String[][] inputBoard, int inputSize, boolean inputInit) {
        this.board = inputBoard;
        this.size = inputSize;
        this.hasInit = inputInit;
    }

    @Override
    public IBoard getDeepCopy() {
        return new SimpleBoard(board.clone(), size, hasInit);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void placeChess(Pos chessPos, BlackOrWhite blackOrWhite) {
        initOrThrowException();
        int row = chessPos.row;
        int col = chessPos.col;

        String chess;

        if (blackOrWhite == BlackOrWhite.BLACK) {
            chess = "*";
        } else {
            chess = "#";
        }

        if (row >= 0 && row <= size && col >= 0 && col <= size) {
            if (Objects.equals(board[row - 1][col - 1], "*") || Objects.equals(board[row - 1][col - 1], "#")) {
                throw new IllegalArgumentException("Error: there is a chess at that position, please re-enter a position");
            } else {
                board[row - 1][col - 1] = chess;
                System.out.printf("Player has place a " + chess + " at position (%d, %d)\n", row, col);
                System.out.println();
            }
        } else {
            throw new IllegalArgumentException("Error: there is a chess at given position, please re-enter a position");
        }
    }

    @Override
    public void initBoard() {
        hasInit = true;

        board = new String[size][size];

        for (int i = 0; i <= size - 1; i++) {
            for (int j = 0; j <= size - 1; j++)
                board[i][j] = "+";
        }
    }

    @Override
    public void printBoard() {
        System.out.println("------------------------------");

        for (int i = 0; i <= size; i++) {
            System.out.print(i);
        }

        System.out.print("\n");

        for (int i = 0; i < size; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j]);
            }
            System.out.print("\n");
        }

        System.out.println("------------------------------");
    }

    public boolean isGameOver() {
        initOrThrowException();

        // save consecutive five chess
        String[] consecutiveFivePos = new String[5];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 4; j++) {
                System.arraycopy(board[i], j, consecutiveFivePos, 0, 5);
                if (end(consecutiveFivePos)) return true;
            }
        }

        for (int i = 0; i < size - 5; i++) {
            for (int j = 0; j < size - 1; j++) {
                for (int k = 0; k < 5; k++) {
                    consecutiveFivePos[k] = board[i + k][j];
                }
                if (end(consecutiveFivePos)) return true;
            }
        }

        for (int i = 0; i < size - 5; i++) {
            for (int j = 0; j < size - 5; j++) {
                for (int k = 0; k < 5; k++) {
                    consecutiveFivePos[k] = board[i + k][j + k];
                }
                if (end(consecutiveFivePos)) return true;
            }
        }

        for (int i = 4; i < size - 1; i++) {
            for (int j = 0; j < size - 5; j++) {
                for (int k = 0; k < 5; k++) {
                    consecutiveFivePos[k] = board[i - k][j + k];
                }
                if (end(consecutiveFivePos)) return true;
            }
        }

        return false;
    }

    @Override
    public Stack<Pos> getValidPos() {
        Stack<Pos> validPos = new Stack<>();

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == "+") {
                    validPos.add(new Pos(row, column));
                }
            }
        }

        return validPos;
    }

    private boolean end(String[] consecutiveFivePos) {
        boolean flag = false;
        String color = consecutiveFivePos[0];

        for (int i = 1; i < 5; i++) {
            if (!Objects.equals(color, consecutiveFivePos[i])) {
                flag = false;
                break;
            } else
                flag = true;
        }
        if (flag && Objects.equals(consecutiveFivePos[0], "*")) {
            return true;
        } else if (flag && Objects.equals(consecutiveFivePos[0], "#")) {
            return true;
        }
        return false;
    }

    private void initOrThrowException() {
        if (!hasInit) {
            throw new IllegalStateException("The board is not init yet!");
        }
    }
}
