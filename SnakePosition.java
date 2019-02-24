//represent the single unit of the snake body
public class SnakePosition {

    public int col;
    public int row;
//constructor
    SnakePosition(int row, int col) {
        this.col = col;
        this.row = row;
    }
//constructor
    SnakePosition() {
        col = 0;
        row = 0;
    }

}
