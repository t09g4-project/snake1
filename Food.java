import java.util.LinkedList;

public class Food extends SnakePosition {

    public int row;
    public int col;
    // row and column(represent the location of the food)

    public static final int Row = Configure.ROW;
    public static final int Column = Configure.COL;
    // get the game data from the configuration file

    Food() {
        randomPos();
        // randomly set the location of the food
    }

    // method to check does the food overlap the snake body
    private boolean checkSame(LinkedList<SnakePosition> snakeBody) {
        for (SnakePosition sp : snakeBody)
            if (sp.row == this.row && sp.col == this.col)
                return true;
        // return true if it does
        return false;
    }

    // get the data of snake body to confirm food does not overlap the snake body
    public Food getSnake(LinkedList<SnakePosition> snakeBody) {
        while (checkSame(snakeBody))
            randomPos();
        //if it does, relocate the food randomly
        return this;
        // return the object itself
    }


    // randomly set the location(row and column)
    private void randomPos() {
        this.row = (int) (Math.random() * Row);
        this.col = (int) (Math.random() * Column);
    }
}
