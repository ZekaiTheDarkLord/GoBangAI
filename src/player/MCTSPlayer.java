package player;

import components.BlackOrWhite;
import components.IBoard;
import components.Pos;
import mcts.MCTSNode;

import java.io.IOException;

public class MCTSPlayer implements IPlayer {
    private final BlackOrWhite side;

    public MCTSPlayer(BlackOrWhite inputSide) {
        this.side = inputSide;
    }
    @Override
    public Pos makingDecision(IBoard gameBoard) throws IOException {
        MCTSNode node = new MCTSNode(gameBoard.getDeepCopy(), this.side);
        Pos decision = node.mctsSearch(50000);

        System.out.println("MCTS player place the chess at: " + "(" + decision.row + ", " + decision.col + ").");

        return decision;
    }
}
