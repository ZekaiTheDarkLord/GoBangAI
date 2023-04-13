package player;

import components.BlackOrWhite;
import components.IBoard;
import components.Pos;
import utility.ChessValueUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UtilityPlayer implements IPlayer {
    private final BlackOrWhite side;

    public UtilityPlayer(BlackOrWhite inputSide) {
        this.side = inputSide;
    }

    @Override
    public Pos makingDecision(IBoard gameBoard) throws IOException {
        Double[][] valueBoard = ChessValueUtil.getChessValueBoard(gameBoard, side);
        return getHighestValuePos(valueBoard);
    }

    private Pos getHighestValuePos(Double[][] valueBoard) {
        List<Pos> highestValuePos = new ArrayList<>();
        double highest = -1;

        for (int row = 0; row < valueBoard.length; row++) {
            for (int col = 0; col < valueBoard[0].length; col++) {
                if (valueBoard[row][col] - highest > 0.0001) {
                    highest = valueBoard[row][col];
                    highestValuePos = new ArrayList<>();
                    highestValuePos.add(new Pos(row, col));
                } else if (valueBoard[row][col] - highest < 0.0001 && valueBoard[row][col] - highest > 0) {
                    highestValuePos.add(new Pos(row, col));
                }
            }
        }

        int randomIndex = new Random().nextInt(highestValuePos.size());

        return highestValuePos.get(randomIndex);
    }
}
