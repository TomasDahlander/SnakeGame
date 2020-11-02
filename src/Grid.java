public class Grid {
    int row;
    int col;

    public Grid(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Grid(Grid grid){
        this.row = grid.row;
        this.col = grid.col;
    }

    public void setNewPos(int row, int col){
        this.row = row;
        this.col = col;
    }

    public boolean isEqualsTo(Grid grid){
        return grid.row == row && grid.col == col;
    }
}
