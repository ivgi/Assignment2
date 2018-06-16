package demo.gol;

/**
 * Input parameters for the game of life.
 */
public class Input {
    private Integer numIterations;
    private Integer liveCellsPercent;

    public Integer getNumIterations() {
        return numIterations;
    }

    public void setNumIterations(Integer numIterations) {
        this.numIterations = numIterations;
    }

    public Integer getLiveCellsPercent() {
        return liveCellsPercent;
    }

    public void setLiveCellsPercent(Integer liveCellsPercent) {
        this.liveCellsPercent = liveCellsPercent;
    }

    @Override
    public String toString() {
        return "Input{" +
                "numIterations=" + numIterations +
                ", liveCellsPercent=" + liveCellsPercent +
                '}';
    }
}
