package demo.gol;

/**
 * Represents a cell on the board of game of life.
 */
public class Cell {
    private boolean state = false;
    private boolean newState;

    public Cell() {

    }

    public Cell(boolean state) {
        this.state = state;
    }

    public void setNewState(boolean state) {
        newState = state;
    }

    public void updateState() {
        state = newState;
    }

    public boolean getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "state=" + state +
                ", newState=" + newState +
                '}';
    }
}
