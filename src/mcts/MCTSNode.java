package mcts;

import components.BlackOrWhite;
import components.IBoard;
import components.Pos;

import java.util.*;

// class that implements monte carlo tree search
public class MCTSNode {
    private final IBoard gameBoard;
    private final MCTSNode parent;
    private final Pos parentAction;
    private int numberOfVisits;
    private int wins;
    private int loses;
    private final BlackOrWhite currentPlayer;
    private final BlackOrWhite aiSide;
    private final List<MCTSNode> children;
    private final Stack<Pos> untriedActions;

    public MCTSNode(IBoard board, BlackOrWhite inputColor) {
        this.gameBoard = board;
        this.currentPlayer = inputColor;
        this.aiSide = inputColor;
        this.parent = null;
        this.parentAction = null;
        this.children = new ArrayList<>();
        this.untriedActions = board.getValidPos();
    }

    public MCTSNode(IBoard board, MCTSNode inputParent, Pos inputAction, BlackOrWhite color, BlackOrWhite inputSide) {
        this.gameBoard = board;
        this.parent = inputParent;
        this.parentAction = inputAction;
        this.untriedActions = board.getValidPos();
        this.currentPlayer = switchColor(color);
        this.aiSide = inputSide;
        this.children = new ArrayList<>();
    }

    // apply the Monte Carlo Tree search to get the best node to use
    public Pos mctsSearch(int simulationEpisodes) {
        for (int i = 0; i < simulationEpisodes; i++) {
            System.out.println("Process: " + (double) i / (double) simulationEpisodes);
            MCTSNode expandNode = exploreTree();
            BlackOrWhite winner = simulate();
            if (winner != null) {
                expandNode.backPropagate(winner);
            }
        }

        MCTSNode bestChild = getBestChild();
        return bestChild.parentAction;
    }

    // go through the tree by expanding its children
    public MCTSNode exploreTree() {
        MCTSNode currentNode = this;

        while (!currentNode.isFinalState()) {
            if (!currentNode.isFullyExpand()) {
                return currentNode.expandNode();
            } else {
                currentNode = currentNode.getBestChild();
            }
        }

        return currentNode;
    }

    // simulate the game by randomly place chess on the game board
    public BlackOrWhite simulate() {
        IBoard currentState = this.gameBoard.getDeepCopy();
        BlackOrWhite currentStateColor = this.currentPlayer;

        while (!currentState.isPlayerWin() && !currentState.isBoardFull()) {
            List<Pos> possibleMoves = currentState.getValidPos();
            Pos nextAction = simulateHelper(possibleMoves);
            currentState.placeChess(nextAction, currentStateColor);
            currentStateColor = switchColor(currentStateColor);
        }

        if (currentState.isBoardFull()) {
            if (currentState.isPlayerWin()) {
                return switchColor(currentStateColor);
            } else {
                return null;
            }
        } else {
            return switchColor(currentStateColor);
        }
    }

    // backpropagation process
    public void backPropagate(BlackOrWhite result) {
        numberOfVisits++;
        calculateWinning(result);

        if (this.parent != null) {
            this.parent.backPropagate(result);
        }
    }

    // expand the node
    private MCTSNode expandNode() {
        Pos action = untriedActions.pop();
        IBoard nextState = gameBoard.getDeepCopy();
        nextState.placeChess(action, currentPlayer);
        MCTSNode newChildNode = new MCTSNode(nextState, this, action, currentPlayer, aiSide);
        children.add(newChildNode);
        return newChildNode;
    }

    // get the best uct value child from children list
    private MCTSNode getBestChild() {
        List<Double> uctValues = new ArrayList<>();
        Map<Double, MCTSNode> scoreToNode = new HashMap<>();

        for (MCTSNode n : children) {
            double uctValue = getUCT(n, 2);

            uctValues.add(uctValue);
            scoreToNode.put(uctValue, n);
        }

        // sort in ascending order
        Collections.sort(uctValues);

        // get the best score
        double bestScore = currentPlayer == aiSide ? uctValues.get(uctValues.size() - 1) : uctValues.get(0);
        return scoreToNode.get(bestScore);
    }

    // pick a random action
    private Pos simulateHelper(List<Pos> possibleMoves) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(possibleMoves.size());

        return possibleMoves.get(randomIndex);
    }

    // calculate the UCT value of the given node
    private double getUCT(MCTSNode child, double cParam) {
        return ((double) child.getWinningScore() / (double) child.getNumberOfVisits()) +
                cParam * Math.sqrt((2 * Math.log(this.getNumberOfVisits()) / child.getNumberOfVisits()));
    }

    // count the wins and loses depends on the AI side
    private void calculateWinning(BlackOrWhite result) {
        if (aiSide == BlackOrWhite.BLACK) {
            if (result == BlackOrWhite.BLACK) {
                wins++;
            } else if (result == BlackOrWhite.WHITE) {
                loses++;
            } else {
                throw new IllegalArgumentException("Illegal input for result!");
            }
        } else if (aiSide == BlackOrWhite.WHITE) {
            if (result == BlackOrWhite.BLACK) {
                loses++;
            } else if (result == BlackOrWhite.WHITE) {
                wins++;
            } else {
                throw new IllegalArgumentException("Illegal input for result!");
            }
        } else {
            throw new IllegalArgumentException("Illegal input for AI side!");
        }
    }

    private BlackOrWhite switchColor(BlackOrWhite color) {
        if (color == BlackOrWhite.BLACK) {
            return BlackOrWhite.WHITE;
        } else if (color == BlackOrWhite.WHITE) {
            return BlackOrWhite.BLACK;
        } else {
            throw new IllegalArgumentException("Invalid color!");
        }
    }

    // get the value which number of wins minus number of loses
    private int getWinningScore() {
        return wins - loses;
    }

    // get the number of visits of that node
    private int getNumberOfVisits() {
        return numberOfVisits;
    }

    // decide whether the current node is the leaf node(whether the game reach the final state)
    private boolean isFinalState() {
        return gameBoard.isPlayerWin() || gameBoard.isBoardFull();
    }

    // decide whether the game is fully expand
    private boolean isFullyExpand() {
        return untriedActions.isEmpty();
    }
}
