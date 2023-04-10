package mcts;

import components.BlackOrWhite;
import components.IBoard;
import components.Pos;

import java.util.*;

// class that implements monte carlo tree search
public class MCTSNode {
    IBoard gameBoard;
    MCTSNode parent;
    Pos parentAction;
    int numberOfVisits;
    int wins;
    int loses;
    BlackOrWhite currentPlayer;

    List<MCTSNode> children;
    Stack<Pos> untriedActions;

    public MCTSNode(IBoard board, BlackOrWhite inputColor) {
        this.gameBoard = board;
        this.currentPlayer = inputColor;
        this.parent = null;
        this.parentAction = null;
        this.untriedActions = board.getValidPos();
    }

    public MCTSNode(IBoard board, MCTSNode inputParent, Pos inputAction, BlackOrWhite color) {
        this.gameBoard = board;
        this.parent = inputParent;
        this.parentAction = inputAction;
        this.untriedActions = board.getValidPos();
        this.currentPlayer = switchColor(color);
    }

    // apply the Monte Carlo Tree search to get the best node to use
    public Pos mctsSearch(int simulationEpisodes) {
        for (int i = 0; i < simulationEpisodes; i++) {
            MCTSNode expandNode = exploreTree();
            BlackOrWhite winner = simulate();
            expandNode.backPropagate(winner);
        }

        return getBestChild().parentAction;
    }

    // TODO: will this need a deep copy?
    // go through the tree by expanding its children
    public MCTSNode exploreTree() {
        MCTSNode currentNode = this;

        while (!currentNode.isFinalState()) {
            if (!currentNode.isFullyExpand()) {
                return currentNode.expandNode();
            } else {
                // TODO: get best performance child
                // currentNode = chooseRandomChild();
                currentNode = getBestChild();
            }
        }

        return currentNode;
    }

    // simulate the game by randomly place chess on the game board
    public BlackOrWhite simulate() {
        IBoard currentState = this.gameBoard.getDeepCopy();
        BlackOrWhite currentStateColor = this.currentPlayer;

        while (!currentState.isGameOver()) {
            List<Pos> possibleMoves = currentState.getValidPos();
            Pos nextAction = simulateHelper(possibleMoves);
            currentState.placeChess(nextAction, currentStateColor);
            currentStateColor = switchColor(currentStateColor);
        }

        return switchColor(currentStateColor);
    }

    // backpropagation process
    public void backPropagate(BlackOrWhite result) {
        numberOfVisits++;
        if (result == BlackOrWhite.BLACK) {
            wins++;
        } else if (result == BlackOrWhite.WHITE) {
            loses++;
        } else {
            throw new IllegalArgumentException("Illegal input for backpropagation!");
        }

        if (this.parent != null) {
            this.parent.backPropagate(result);
        }
    }

    // expand the node
    private MCTSNode expandNode() {
        Pos action = untriedActions.pop();
        IBoard nextState = gameBoard.getDeepCopy();
        nextState.placeChess(action, currentPlayer);
        MCTSNode newChildNode = new MCTSNode(nextState, this, action, currentPlayer);
        children.add(newChildNode);
        return newChildNode;
    }

    // get the best uct value child from children list
    private MCTSNode getBestChild() {
        List<Double> uctValues = new ArrayList<>();
        Map<Double, MCTSNode> scoreToNode = new HashMap<>();

        for (MCTSNode n : children) {
            double uctValue = getUCT(n, 0.1);

            uctValues.add(uctValue);
            scoreToNode.put(uctValue, n);
        }

        // sort in ascending order
        Collections.sort(uctValues);

        // if the current player is black, use the highest UCT score, otherwise the lowest UCT score.
        double bestScore = currentPlayer == BlackOrWhite.BLACK ? uctValues.get(uctValues.size() - 1) : uctValues.get(0);
        return scoreToNode.get(bestScore);
    }

    // randomly choose a child from children list
    private MCTSNode chooseRandomChild() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(children.size());

        return children.get(randomIndex);
    }

    // pick a random action
    private Pos simulateHelper(List<Pos> possibleMoves) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(possibleMoves.size());

        return possibleMoves.get(randomIndex);
    }

    //------------------------------------------------------------------------------------------------------------------

    // calculate the UCT value of the given node
    private double getUCT(MCTSNode child, double cParam) {
        return ((float) child.getWinningScore() / (float) child.getNumberOfVisits()) +
                cParam * Math.sqrt((2 * Math.log(this.getNumberOfVisits()) / child.getNumberOfVisits()));
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
        return gameBoard.isGameOver();
    }

    // decide whether the game is fully expand
    private boolean isFullyExpand() {
        return untriedActions.isEmpty();
    }
}
