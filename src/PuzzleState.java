import java.util.*;

public class PuzzleState implements State,Comparable<State>{


    /***
     * Inner class that represents a Position we want to move on the greed and where.
     */
    private class Position {
        private int x1;
        private int y1;
        private int x2;
        private int y2;
        private char dir;

        public Position(int x1, int y1, int x2, int y2, char dir) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.dir = dir;
        }

        public Position(Position other) {
            this.x1 = other.x1;
            this.y1 = other.y1;
            this.x2 = other.x2;
            this.y2 = other.y2;
            this.dir = other.dir;
        }

        public char getDir() {
            return dir;
        }

        public int getX1() {
            return x1;
        }

        public int getY1() {
            return y1;
        }

        public int getX2() {
            return x2;
        }

        public int getY2() {
            return y2;
        }

        public void setX1(int x1) {
            this.x1 = x1;
        }

        public void setY1(int y1) {
            this.y1 = y1;
        }

        public void setX2(int x2) {
            this.x2 = x2;
        }

        public void setY2(int y2) {
            this.y2 = y2;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x1=" + x1 +
                    ", y1=" + y1 +
                    ", x2=" + x2 +
                    ", y2=" + y2 +
                    ", dir=" + dir +
                    '}';
        }
    }

    private final String EMPTY = "_";
    public static int NUM_OF_STATES = 0;
    private String[][] greed;
    private State father=null;
    private int id;
    private int price=0;
    private double heuristic=0;
    private String step="";
    private String path="";
    private int level=0;
    private int x1_empty;
    private int y1_empty;
    private int x2_empty;
    private int y2_empty;
    private boolean out = false;
    private Hashtable<Character, Character> isOpposite = new Hashtable<>();

    public PuzzleState() {

    }

    /***
     * constructor
     * @param greed-the greed of this state
     * @param father-the father of this state
     * @param level-depth of this state
     * @param price-price of this state
     * @param heuristic-heuristic value of this state(use in informed algorithms)
     * @param path-path up to this state
     * @param step-step from the father to this state
     * @param x1_empty
     * @param y1_empty
     * @param x2_empty
     * @param y2_empty
     */
    public PuzzleState(String[][] greed, State father, int level, int price, double heuristic, String path, String step, int x1_empty, int y1_empty, int x2_empty, int y2_empty) {
        this.greed = greed;
        this.father = father;
        this.price = price;
        this.heuristic = heuristic;
        this.path = path;
        this.level = level + 1;
        this.x1_empty = x1_empty;
        this.y1_empty = y1_empty;
        this.x2_empty = x2_empty;
        this.y2_empty = y2_empty;
        this.step = step;
        updateOpposite();
        NUM_OF_STATES++;
        this.id = NUM_OF_STATES;
//        NUM_OF_STATES++;
    }
    public PuzzleState(String[][] greed, int x1_empty, int y1_empty, int x2_empty, int y2_empty) {
        this.greed = greed;
        this.x1_empty = x1_empty;
        this.y1_empty = y1_empty;
        this.x2_empty = x2_empty;
        this.y2_empty = y2_empty;
        updateOpposite();
        NUM_OF_STATES++;
        this.id = NUM_OF_STATES;
    }

    /***
     * Update the opposite map to get the opposite direction
     * using to filter unnecessary state.
     */
    private void updateOpposite(){
        isOpposite.put('L', 'R');
        isOpposite.put('U', 'D');
        isOpposite.put('R', 'L');
        isOpposite.put('D', 'U');
    }

    /***
     * check if this state is "out" for IDA* and DFBnB.
     * @return
     */
    public boolean isOut() {
        return out;
    }

    /***
     * Set this state to be out.
     * @param out
     */
    public void setOut(boolean out) {
        this.out = out;
    }


    /***
     * Check if we are not creat a state which same like the father or unnecessary state(in o(1)).
     * @param p
     * @return
     */
    private boolean isNotFather(Position p) {


        char dir = step.charAt(step.length() - 1);
        String str = step.substring(0, step.length() - 1);

        if (str.contains("&")) {
            String[] arr = str.split("&");
            if (p.getX2() != -1) {

                if (p.getDir() == isOpposite.get(dir) && (((greed[p.getX1()][p.getY1()].equals(arr[0])) && (greed[p.getX2()][p.getY2()].equals(arr[1])))
                        || ((greed[p.getX1()][p.getY1()].equals(arr[1])) && (greed[p.getX2()][p.getY2()].equals(arr[0]))))) {
                    return false;
                }
            } else if ((p.getDir() == isOpposite.get(dir) && ((greed[p.getX1()][p.getY1()].equals(arr[0]))
                    || (greed[p.getX1()][p.getY1()].equals(arr[1])))) || (greed[p.getX1()][p.getY1()].equals(EMPTY))) {
                return false;
            }


        } else {
            if ((greed[p.getX1()][p.getY1()].equals(str) && p.getDir() == isOpposite.get(dir)) || greed[p.getX1()][p.getY1()].equals(EMPTY)) {
                return false;
            }

        }
        return true;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PuzzleState node = (PuzzleState) o;

        return Arrays.deepEquals(greed, node.greed);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(greed);
    }
    /***
     * @return the father state of to this state
     */
    public State getFather() {
        return father;
    }

    /***
     * @return the path up to this state
     */
    public String getPath() {
        return path;
    }

    /***
     * set the path up to this state
     * @param path path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /***
     * @return the greed of this state
     */
    public String[][] getGreed() {
        return greed;
    }

    /***
     * @return the id of this state(which is the amount of stats).
     */
    public int getId() {
        return id;
    }

    /***
     * @return the price up to this state.
     */
    public int getPrice() {
        return price;
    }



    /***
     *
     * @return the heuristic value of this state(only for informed algorithms).
     */
    @Override
    public double getHeuristic() {
        return heuristic;
    }

    /***
     * set the ptice of this state
     * @param price price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /***
     * set he heuristic value of this state(only for informed algorithms).
     * @param heuristic heuristic value
     */
    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    /***
     * @return the locations of all the empty spots of this state.
     */
    @Override
    public int[] getEmpty() {
        return new int[]{x1_empty, y1_empty, x2_empty, y2_empty};

    }
    /***
     * @return deep copy of this greed.
     */
    private String[][] copyGreed() {
        String[][] res = new String[greed.length][greed[0].length];
        for (int i = 0; i < greed.length; i++) {
            for (int j = 0; j < greed[0].length; j++) {
                res[i][j] = greed[i][j];

            }
        }
        return res;
    }



    /***
     * compute the son greed of this state according the position you want to move
     * Store it in the successors queue.
     * @param p position
     * @param successors -successors queue
     * @param isTheSecond -true if the spot we move is next to the second empty spot.
     * @return Son's greed
     */
    private void swapAndStore(Queue<State> successors, Position p, boolean isTheSecond) {
        if (p.getX2() != -1 && !isTwoNaiber(p.getDir())) {
            return;
        } else if (p.getX2() == -1 && !canMoveOne(p.getX1(), p.getY1(), p.getDir())) {
            return;
        }

        if (father != null && !isNotFather(p)) {
            return;
        }
        String[][] next = copyGreed();

        if(isTheSecond){
            next[x2_empty][y2_empty] = next[p.getX1()][p.getY1()];
        }else {
            next[x1_empty][y1_empty] = next[p.getX1()][p.getY1()];
        }
        next[p.getX1()][p.getY1()] = EMPTY;
        if (p.getX2() != -1) {

            next[x2_empty][y2_empty] = next[p.getX2()][p.getY2()];
            next[p.getX2()][p.getY2()] = EMPTY;
        }
        String step = calcPath(greed, p);
        String path = this.path + step + "-";
        int w = calcPriceStep(p);
        int price = w + this.price;
        Position _p = checkEmpty(p, next);
        PuzzleState son = new PuzzleState(next, this, this.level, price, w, path, step, _p.getX1(), _p.getY1(), _p.getX2(), _p.getY2());
        successors.add(son);

    }

    /***
     * Create position to move right and send in to swapAndStore
     * @param successors-successors queue
     * @param isTheSecond-true if the spot we move is next to the second empty spot.
     */
    private void right(Queue<State> successors, boolean isTheSecond) {
        Position p;
        if (!isTheSecond) {
            p = new Position(x1_empty, y1_empty - 1, -1, -1, 'R');
        } else {
            p = new Position(x2_empty, y2_empty - 1, -1, -1, 'R');
        }
        swapAndStore(successors, p,isTheSecond);

    }

    /***
     * Create position of two spot to move right send in to swapAndStore.
     * @param successors-successors queue
     */
    private void twoRight(Queue<State> successors) {
        Position p = new Position(x1_empty, y1_empty - 1, x2_empty, y2_empty - 1, 'R');
        swapAndStore(successors, p,false);
    }
    /***
     * Create position to move left and send in to swapAndStore
     * @param successors-successors queue
     * @param isTheSecond-true if the spot we move is next to the second empty spot.
     */
    private void left(Queue<State> successors, boolean isTheSecond) {
        Position p;
        if (!isTheSecond) {
            p = new Position(x1_empty, y1_empty + 1, -1, -1, 'L');
        } else {
            p = new Position(x2_empty, y2_empty + 1, -1, -1, 'L');
        }
        swapAndStore(successors, p,isTheSecond);
    }
    /***
     * Create position of two spot to move left send in to swapAndStore.
     * @param successors-successors queue
     */
    private void twoLeft(Queue<State> successors) {
        Position p = new Position(x1_empty, y1_empty + 1, x2_empty, y2_empty + 1, 'L');
        swapAndStore(successors, p,false);
    }
    /***
     * Create position to move down and send in to swapAndStore
     * @param successors-successors queue
     * @param isTheSecond-true if the spot we move is next to the second empty spot.
     */
    private void down(Queue<State> successors, boolean isTheSecond) {
        Position p;
        if (!isTheSecond) {
            p = new Position(x1_empty - 1, y1_empty, -1, -1, 'D');
        } else {
            p = new Position(x2_empty - 1, y2_empty, -1, -1, 'D');
        }
        swapAndStore(successors, p,isTheSecond);
    }
    /***
     * Create position of two spot to move down send in to swapAndStore.
     * @param successors-successors queue
     */
    private void twoDown(Queue<State> successors) {
        Position p = new Position(x1_empty - 1, y1_empty, x2_empty - 1, y2_empty, 'D');
        swapAndStore(successors, p,false);
    }
    /***
     * Create position to move up and send in to swapAndStore
     * @param successors-successors queue
     * @param isTheSecond-true if the spot we move is next to the second empty spot.
     */
    private void up(Queue<State> successors, boolean isTheSecond) {
        Position p;
        if (!isTheSecond) {
            p = new Position(x1_empty + 1, y1_empty, -1, -1, 'U');
        } else {
            p = new Position(x2_empty + 1, y2_empty, -1, -1, 'U');
        }
        swapAndStore(successors, p,isTheSecond);
    }
    /***
     * Create position of two spot to move up send in to swapAndStore.
     * @param successors-successors queue
     */
    private void twoUp(Queue<State> successors) {
        Position p = new Position(x1_empty + 1, y1_empty, x2_empty + 1, y2_empty, 'U');
        swapAndStore(successors, p,false);
    }

    /***
     * @param dirction
     * @return true if this direction its possible to move two squares in the same time.
     */
    private boolean isTwoNaiber(char dirction) {
        boolean U_D = x1_empty == x2_empty && Math.abs(y1_empty - y2_empty) == 1;
        boolean R_L = y1_empty == y2_empty && Math.abs(x1_empty - x2_empty) == 1;
        if (U_D && (dirction == 'R' || dirction == 'L')) {
            return false;
        }
        if (R_L && (dirction == 'U' || dirction == 'D')) {
            return false;
        }
        if (R_L) {
            if (y1_empty == greed[0].length - 1 && dirction == 'L') {
                return false;
            }
            if (y1_empty == 0 && dirction == 'R') {
                return false;
            }
        }
        if (U_D) {
            if (x1_empty == greed.length - 1 && dirction == 'U') {
                return false;
            }
            if (x1_empty == 0 && dirction == 'D') {
                return false;
            }
        }
        return U_D || R_L;

    }

    /***
     * @param x
     * @param y
     * @param dirction
     * @return return true if its possible to move this location in this direction.
     */

    private boolean canMoveOne(int x, int y, char dirction) {
        switch (dirction) {
            case 'L':
                if (y == 0||y>=greed[0].length ) {
                    return false;
                }
                break;
            case 'U':
                if (x == 0||x>=greed.length) {
                    return false;
                }
                break;
            case 'R':
                if (y == greed[0].length - 1||y<0) {
                    return false;
                }
                break;
            case 'D':
                if (x == greed.length - 1||x<0) {
                    return false;
                }
                break;
        }
        return true;
    }

    /***
     * This function send the successors queue to the functions that move two spot.
     * @param successors-successors queue
     */
    private void moveTwo(Queue<State> successors) {
        twoLeft(successors);
        twoUp(successors);
        twoRight(successors);
        twoDown(successors);
    }
    /***
     * This function send the successors queue to the functions that move one spot.
     * @param successors-successors queue
     */
    private void moveOne(Queue<State> successors, boolean isTheSecond) {
        left(successors, isTheSecond);
        up(successors, isTheSecond);
        right(successors, isTheSecond);
        down(successors, isTheSecond);
    }

    /***
     * compute all the successors of this state according to the right order.
     * @return a Queue with those successors in the right order
     */
    @Override
    public Queue<State> getSuccessors() {
        Queue<State> successors = new LinkedList<>();
        if (x2_empty == -1) {
            moveOne(successors, false);
        } else {
            moveTwo(successors);
            moveOne(successors, false);
            moveOne(successors, true);
        }

        return successors;
    }

    /***
     * compute of a son state
     * @param greed
     * @param pos
     * @return
     */
    private String calcPath(String[][] greed, PuzzleState.Position pos) {
        String ans = "";
        ans += greed[pos.getX1()][pos.getY1()];
        if (pos.getX2() != -1) {
            ans += "&" + greed[pos.getX2()][pos.getY2()];
        }
        ans += pos.getDir();
        return ans;
    }

    private int calcPriceStep(PuzzleState.Position pos) {
        char dirction = pos.getDir();
        if (pos.getX2() != -1) {
            if (dirction == 'U' || dirction == 'D') {
                return 7;
            }
            if (dirction == 'R' || dirction == 'L') {
                return 6;
            }
        }
        return 5;

    }

    /***
     * compute the emptys spots of a son state
     * @param p
     * @param greed
     * @return
     */
    private PuzzleState.Position checkEmpty(PuzzleState.Position p, String[][] greed) {

        PuzzleState.Position ans = new PuzzleState.Position(p);
        if (ans.getX2() == -1) {
            ans.setX2(x2_empty);
            ans.setY2(y2_empty);
            if (x2_empty != -1 && !greed[ans.getX2()][ans.getY2()].equals(EMPTY)) {
                ans.setX2(x1_empty);
                ans.setY2(y1_empty);
            }
        }
        if (ans.getX2() != -1 && ans.getY2() != -1) {
            if (ans.getX1() < ans.getX2()) {
                return ans;
            }
            if (ans.getX1() == ans.getX2()) {
                if (ans.getY1() < ans.getY2()) {
                    return ans;
                }
            }
            int temp = ans.getX1();
            int temp2 = ans.getY1();
            ans.setX1(ans.getX2());
            ans.setY1(ans.getY2());
            ans.setX2(temp);
            ans.setY2(temp2);
            return ans;
        }
        return ans;

    }
    @Override
    public int compareTo(State o) {
        if (o.getHeuristic() == this.getHeuristic()) {
            return Double.compare(this.getId(), o.getId());
        }
        return Double.compare(this.getHeuristic(), o.getHeuristic());

    }
    /***
     * toString method.
     * @return
     */
    @Override
    public String toString() {
        String res =
                "{greed=\n";
        for (int i = 0; i < greed.length; i++) {
            res += Arrays.toString(greed[i]) + "\n";
        }
        res += "}";
        return res;

    }
}
