package demo.gol;

import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Starts game of life and performs iterations. Takes the input and produces the output.
 */
@Component
public class Game {

    private final int BOARD_HEIGHT = 10;
    private final int BOARD_WIDTH = 10;

    public void notify(Input input) {
        System.out.println("New game of life queued.");
        System.out.println(input.toString());
    }

    public Output play(Input input) {
        double liveCellsPercentFraction = ((double) input.getLiveCellsPercent()) / 100;
        Board board = new Board(BOARD_HEIGHT, BOARD_WIDTH, liveCellsPercentFraction);

        for (int i = 0; i < input.getNumIterations(); i++)
            board.update();

        return createOutput(input, board.countLiveCells());
    }

    private Output createOutput(Input input, Integer numOfLivingCells) {
        Output output = new Output();
        output.setBoardWidth(BOARD_WIDTH);
        output.setBoardHeight(BOARD_HEIGHT);
        output.setNumOfIterations(input.getNumIterations());
        output.setStartPercentageOfLivingCells(input.getLiveCellsPercent());
        output.setNumOfLivingCells(numOfLivingCells);
        return output;
    }
}
