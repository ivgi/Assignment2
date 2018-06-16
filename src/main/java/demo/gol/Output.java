package demo.gol;

import javax.persistence.*;

/**
 * Output from the game of life.
 */
@Entity(name = "gol_output")
public class Output {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    private Integer boardHeight;
    @Basic
    private Integer boardWidth;
    @Basic
    private Integer startPercentageOfLivingCells;
    @Basic
    private Integer numOfIterations;
    @Basic
    private Integer numOfLivingCells;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(Integer boardHeight) {
        this.boardHeight = boardHeight;
    }

    public Integer getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(Integer boardWidth) {
        this.boardWidth = boardWidth;
    }

    public Integer getStartPercentageOfLivingCells() {
        return startPercentageOfLivingCells;
    }

    public void setStartPercentageOfLivingCells(Integer startPercentageOfLivingCells) {
        this.startPercentageOfLivingCells = startPercentageOfLivingCells;
    }

    public Integer getNumOfIterations() {
        return numOfIterations;
    }

    public void setNumOfIterations(Integer numOfIterations) {
        this.numOfIterations = numOfIterations;
    }

    public Integer getNumOfLivingCells() {
        return numOfLivingCells;
    }

    public void setNumOfLivingCells(Integer numOfLivingCells) {
        this.numOfLivingCells = numOfLivingCells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Output)) return false;

        Output output = (Output) o;

        if (id != null ? !id.equals(output.id) : output.id != null) return false;
        if (boardHeight != null ? !boardHeight.equals(output.boardHeight) : output.boardHeight != null)
            return false;
        if (boardWidth != null ? !boardWidth.equals(output.boardWidth) : output.boardWidth != null) return false;
        if (startPercentageOfLivingCells != null ? !startPercentageOfLivingCells.equals(output.startPercentageOfLivingCells) : output.startPercentageOfLivingCells != null)
            return false;
        if (numOfIterations != null ? !numOfIterations.equals(output.numOfIterations) : output.numOfIterations != null)
            return false;
        return numOfLivingCells != null ? numOfLivingCells.equals(output.numOfLivingCells) : output.numOfLivingCells == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (boardHeight != null ? boardHeight.hashCode() : 0);
        result = 31 * result + (boardWidth != null ? boardWidth.hashCode() : 0);
        result = 31 * result + (startPercentageOfLivingCells != null ? startPercentageOfLivingCells.hashCode() : 0);
        result = 31 * result + (numOfIterations != null ? numOfIterations.hashCode() : 0);
        result = 31 * result + (numOfLivingCells != null ? numOfLivingCells.hashCode() : 0);
        return result;
    }
}
