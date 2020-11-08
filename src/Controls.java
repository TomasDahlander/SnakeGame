
public class Controls {

    public void move(char heading, Grid position) {
        if (heading == 'N') position.row--;
        if (heading == 'S') position.row++;
        if (heading == 'W') position.col--;
        if (heading == 'E') position.col++;
    }
}
