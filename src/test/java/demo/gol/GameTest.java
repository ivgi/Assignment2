package demo.gol;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {

    private final Game game = new Game();

    @Test
    public void correctInput() {
        // Arrange
        final Integer liveCellsPercent = 60;
        final Integer numIterations = 5;
        Input input = new Input();
        input.setLiveCellsPercent(liveCellsPercent);
        input.setNumIterations(numIterations);
        // Act
        Output gameResult = game.play(input);
        // Assert
        assertEquals(Integer.valueOf(10), gameResult.getBoardHeight());
        assertEquals(Integer.valueOf(10), gameResult.getBoardWidth());
        assertEquals(liveCellsPercent, gameResult.getStartPercentageOfLivingCells());
        assertEquals(numIterations, gameResult.getNumOfIterations());
    }
}
