package player;

import components.IBoard;
import components.Pos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HumanPlayer implements IPlayer {
    public HumanPlayer() {

    }

    @Override
    public Pos makingDecision(IBoard gameBoard) throws IOException {
        int row = -1;
        int col = -1;

        String inputString;

        System.out.println("Please input the row index:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        inputString = br.readLine();
        row = Integer.parseInt(inputString);


        System.out.println("Please input the column index:");
        br = new BufferedReader(new InputStreamReader(System.in));
        inputString = br.readLine();
        col = Integer.parseInt(inputString);


        return new Pos(row, col);
    }
}
