import java.util.LinkedList;

public class Snake {
    private Direction snake_Direction;
    // the current direction of the snake
    private Direction move_Direction;
    // the direction that the player give

    private Food food;
    //food object in the game, refresh a new one after it is eaten

    private LinkedList<SnakePosition> snakeBody;
    // the body of the snake. every unit of the snake is represented by SnakePosition unit
    // data structure is linked list because adding elements into it is usual

    public static final int Row = Configure.ROW;
    public static final int Column = Configure.COL;
    // get the row and column data from the configuration file

    public Snake() {
        snakeBody = new LinkedList<SnakePosition>();
        reset();
        // reset the snake
    }

    public Direction getSnake_Direction() {
        return snake_Direction;
    }

    public void setSnake_Direction(Direction snake_Direction) {
        this.snake_Direction = snake_Direction;
    }

    public LinkedList<SnakePosition> getSnakeBody() {
        return snakeBody;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public void setMove_Direction(Direction dir) {
        this.move_Direction = dir;
    }

    /**
     *
     */
    public void reset() {
        snakeBody.clear();
        // clear the linkedlist(dead snake body)
        SnakePosition begin_SnakePosition = null;
        // reset the begin position of the new snake
        setMove_Direction(null);
        do {
            begin_SnakePosition = this.RandomPos();
            // use random method to locate the position of the new snake randomly
        } while (begin_SnakePosition.row + 3 > Row);
        // if the distance between the begin position and the limitation of the map is larger than 3, the begin position is accepted(because the initial length of the snake is 3)

        snakeBody.add(begin_SnakePosition);
        snakeBody.add(new SnakePosition(begin_SnakePosition.row + 1, begin_SnakePosition.col));
        snakeBody.add(new SnakePosition(begin_SnakePosition.row + 2, begin_SnakePosition.col));
        // create the initial snake body and the length of it is 3 units
        setSnake_Direction(Direction.UP);
        // set the initial direction of the snake
    }

    //create a snake body object(SnakePosition class) and return it
    private SnakePosition RandomPos() {

        int randomRow = (int) (Math.random() * Row);
        int randomCol = (int) (Math.random() * Column);

        return new SnakePosition(randomRow, randomCol);
    }

    // control the movement of the snake
    public void snakeMove() {

        int addRow = snakeBody.getFirst().row;
        int addCol = snakeBody.getFirst().col;
        // the new location of the snake head should be adjacent to the original snake head
        // firstly set the new snake head in the same location of the original snake head

        if ((move_Direction != null)
            && !((snake_Direction == Direction.UP && move_Direction == Direction.DOWN)
            || (snake_Direction == Direction.DOWN && move_Direction == Direction.UP)
            || (snake_Direction == Direction.LEFT && move_Direction == Direction.RIGHT)
            || (snake_Direction == Direction.RIGHT && move_Direction == Direction.LEFT)))
            snake_Direction = move_Direction;
        // if it match the condition, snake_Direction will be covered by move_Direction

        switch (snake_Direction) {
        case UP:
            addRow = addRow - 1;
            break;
        case DOWN:
            addRow = addRow + 1;
            break;
        case LEFT:
            addCol = addCol - 1;
            break;
        case RIGHT:
            addCol = addCol + 1;
            break;
        }
        // estimate the location of the new snake head according to the direction

        SnakePosition addPos = new SnakePosition(addRow, addCol);
        // according to the new location, create a new SnakePosition object addPos( a unit of snakeBody linkedlist)

        if (!detect_Food(addPos)){
            snakeBody.removeLast();
        }
            // if the new snake head does not overlap the food, delete the snake tail unit
        else{
            setFood(new Food().getSnake(snakeBody));
        }
            // if the new snake head overlap the food, randomly set a new food

        if (detect_Obstacles(addPos)){
            reset();
        }
            // if collision happen, start a new game(reset the snake)
        else{
            snakeBody.addFirst(addPos);
        }
            // if collision does not happen, add the new snake head into the snakebody linkedlist
        //
    }

    // detect the food
    private boolean detect_Food(SnakePosition addPos) {
        if (food.row == addPos.row && food.col == addPos.col)
            return true;
        // if the food is in the given location, return true
        return false;
        // if not, return false
    }

    //detect the wall or SnakeBody
    //if the new snake head overlap the snake body or the wall(limitation of the map), collision happens
    private boolean detect_Obstacles(SnakePosition addPos) {
        if (addPos.row < 0 || addPos.row > Row - 1 || addPos.col < 0
                || addPos.col > Column - 1)
            return true;
        // if the new snake head overlap the wall, return true
        for (SnakePosition sp : snakeBody)
            if ((sp.row == addPos.row) && (sp.col == addPos.col))
                return true;
        // if the new snake head overlap the snakeBody, return true
        return false;
        // if no collision happens, return false
    }
}
