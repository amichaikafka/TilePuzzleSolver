import java.util.*;

public class state {
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
    private state father;
    private int id;
    private int price;
    private double wight;
    private String step;
    private String path;
    private int level;
    private int x1_empty;
    private int y1_empty;
    private int x2_empty;
    private int y2_empty;
    private boolean out = false;
    private Hashtable<Character, Character> isOppsite = new Hashtable<>();

    public state() {

    }

    /***
     * constructor
     * @param greed-the greed of this state
     * @param father-the father of this state
     * @param level-depth of this state
     * @param price-price of this state
     * @param wight-wight of this state(use in informed algorithms)
     * @param path-path up to this state
     * @param step-step from the father to this state
     * @param x1_empty
     * @param y1_empty
     * @param x2_empty
     * @param y2_empty
     */
    public state(String[][] greed, state father, int level, int price, double wight, String path, String step, int x1_empty, int y1_empty, int x2_empty, int y2_empty) {
        this.greed = greed;
        this.father = father;
        this.price = price;
        this.wight = wight;
        this.path = path;
        this.level = level + 1;
        this.x1_empty = x1_empty;
        this.y1_empty = y1_empty;
        this.x2_empty = x2_empty;
        this.y2_empty = y2_empty;
        this.step = step;
        isOppsite.put('L', 'R');
        isOppsite.put('U', 'D');
        isOppsite.put('R', 'L');
        isOppsite.put('D', 'U');
        NUM_OF_STATES++;
        this.id = NUM_OF_STATES;
//        NUM_OF_STATES++;
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


    public int getLevel() {
        return level;
    }

    public String getStep() {
        return step;
    }

    /***
     * Check if we are not creat a state which same like the father or unnecessary state(in o(1)).
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param dirction
     * @return
     */
    private boolean isNotFather(int x1, int y1, int x2, int y2, char dirction) {
        char dir = step.charAt(step.length() - 1);
        String str = step.substring(0, step.length() - 1);
        if (str.contains("&")) {
            String[] arr = str.split("&");
            if (x2 != -1) {

                if (dirction == isOppsite.get(dir) && (((greed[x1][y1].equals(arr[0])) && (greed[x2][y2].equals(arr[1])))||((greed[x1][y1].equals(arr[1])) && (greed[x2][y2].equals(arr[0]))))) {
                    return false;
                }
            }else if (dirction == isOppsite.get(dir) && ((greed[x1][y1].equals(arr[0])) || (greed[x1][y1].equals(arr[1])))) {
                return false;
            }


        } else {
            if (greed[x1][y1].equals(str) && dirction == isOppsite.get(dir)) {
                return false;
            }

        }
        return true;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        state node = (state) o;

        return Arrays.deepEquals(greed, node.greed);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(greed);
    }

    /***
     * @return the path up to this state
     */
    public String getPath() {
        return path;
    }

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
     * @return the wight of this state(only for informed algorithms).
     */
    public double getWight() {
        return wight;
    }


    public void setPrice(int price) {
        this.price = price;
    }

    public void setWight(double wight) {
        this.wight = wight;
    }

    /***
     * @return the locations of all the empty spots of this state.
     */
    public int[] getEmpty() {
        return new int[]{x1_empty, y1_empty, x2_empty, y2_empty};

    }

    /***
     * compute the son greed of this state according the position you want to move.
     * @param p
     * @return Son's greed
     */
    public String[][] getSun(Position p) {
        if (p.getX1() >= greed.length || p.getY1() >= greed[0].length || p.getX2() >= greed.length || p.getY2() >= greed[0].length) {
            return null;
        }

        String[][] res = new String[greed.length][greed[0].length];
        for (int i = 0; i < greed.length; i++) {
            for (int j = 0; j < greed[0].length; j++) {
                res[i][j] = greed[i][j];

            }
        }
        switch (p.getDir()) {
            case 'R':

                res[p.getX1()][p.getY1() + 1] = res[p.getX1()][p.getY1()];
                res[p.getX1()][p.getY1()] = EMPTY;
                if (p.getX2() != -1) {
                    res[p.getX2()][p.getY2() + 1] = res[p.getX2()][p.getY2()];
                    res[p.getX2()][p.getY2()] = EMPTY;
                }
                break;
            case 'L':

                res[p.getX1()][p.getY1() - 1] = res[p.getX1()][p.getY1()];
                res[p.getX1()][p.getY1()] = EMPTY;
                if (p.getX2() != -1) {
                    res[p.getX2()][p.getY2() - 1] = res[p.getX2()][p.getY2()];
                    res[p.getX2()][p.getY2()] = EMPTY;
                }
                break;
            case 'U':

                res[p.getX1() - 1][p.getY1()] = res[p.getX1()][p.getY1()];
                res[p.getX1()][p.getY1()] = EMPTY;
                if (p.getX2() != -1) {
                    res[p.getX2() - 1][p.getY2()] = res[p.getX2()][p.getY2()];
                    res[p.getX2()][p.getY2()] = EMPTY;
                }

                break;
            case 'D':

                res[p.getX1() + 1][p.getY1()] = res[p.getX1()][p.getY1()];
                res[p.getX1()][p.getY1()] = EMPTY;
                if (p.getX2() != -1) {
                    res[p.getX2() + 1][p.getY2()] = res[p.getX2()][p.getY2()];
                    res[p.getX2()][p.getY2()] = EMPTY;
                }
                break;
        }
        return res;
    }


    /***
     * @param dirction
     * @return true if this direction its possible to move two squares in the same time.
     */
    public boolean isTwoNaiber(char dirction) {
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
    private boolean canMove(int x, int y, char dirction) {
        switch (dirction) {
            case 'L':
                if (y == greed[0].length - 1) {
                    return false;
                }
                break;
            case 'U':
                if (x == greed.length - 1) {
                    return false;
                }
                break;
            case 'R':
                if (y == 0) {
                    return false;
                }
                break;
            case 'D':
                if (x == 0) {
                    return false;
                }
                break;
        }
        return true;
    }

    /***
     * check that we are not moving an empty spot.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private boolean isNotEmpty(int x1, int y1, int x2, int y2) {
        if (x2 != -1) {
            return !((greed[x1][y1].equals(EMPTY)) || (greed[x2][y2].equals(EMPTY)));
        }
        return !(greed[x1][y1].equals(EMPTY));
    }

    /***
     * compute all the sons of this state according to the right order.
     * @return a Queue with those sons in the right order
     */
    public Queue<state> order() {

        boolean flag = true;
        Queue<Position> ans = new LinkedList<Position>() {
        };
        if (x2_empty != -1) {

            if (isTwoNaiber('L')) {
                if (father != null) {
                    flag = isNotFather(x1_empty, y1_empty + 1, x2_empty, y2_empty + 1, 'L');
                }

                if (flag && isNotEmpty(x1_empty, y1_empty + 1, x2_empty, y2_empty + 1)) {
                    Position t = new Position(x1_empty, y1_empty + 1, x2_empty, y2_empty + 1, 'L');
                    ans.add(t);
                }

            }
            if (isTwoNaiber('U')) {
                if (father != null) {
                    flag = isNotFather(x1_empty + 1, y1_empty, x2_empty + 1, y2_empty, 'U');
                }
                if (flag && isNotEmpty(x1_empty + 1, y1_empty, x2_empty + 1, y2_empty)) {
                    Position t = new Position(x1_empty + 1, y1_empty, x2_empty + 1, y2_empty, 'U');
                    ans.add(t);
                }
            }
            if (isTwoNaiber('R')) {
                if (father != null) {
                    flag = isNotFather(x1_empty, y1_empty - 1, x2_empty, y2_empty - 1, 'R');
                }
                if (flag && isNotEmpty(x1_empty, y1_empty - 1, x2_empty, y2_empty - 1)) {
                    Position t = new Position(x1_empty, y1_empty - 1, x2_empty, y2_empty - 1, 'R');
                    ans.add(t);
                }
            }
            if (isTwoNaiber('D')) {
                if (father != null) {
                    flag = isNotFather(x1_empty - 1, y1_empty, x2_empty - 1, y2_empty, 'D');
                }
                if (flag && isNotEmpty(x1_empty - 1, y1_empty, x2_empty - 1, y2_empty)) {

                    Position t = new Position(x1_empty - 1, y1_empty, x2_empty - 1, y2_empty, 'D');
                    ans.add(t);
                }
            }
        }


        if (canMove(x1_empty, y1_empty, 'L')) {

            if (father != null) {
                flag = isNotFather(x1_empty, y1_empty + 1, -1, -1, 'L');
            }
            if (flag && isNotEmpty(x1_empty, y1_empty + 1, -1, -1)) {

                Position t = new Position(x1_empty, y1_empty + 1, -1, -1, 'L');
                ans.add(t);
            }

        }
        if (canMove(x1_empty, y1_empty, 'U')) {
            if (father != null) {
                flag = isNotFather(x1_empty + 1, y1_empty, -1, -1, 'U');
            }
            if (flag && isNotEmpty(x1_empty + 1, y1_empty, -1, -1)) {
                Position t = new Position(x1_empty + 1, y1_empty, -1, -1, 'U');
                ans.add(t);
            }
        }
        if (canMove(x1_empty, y1_empty, 'R')) {
            if (father != null) {
                flag = isNotFather(x1_empty, y1_empty - 1, -1, -1, 'R');
            }
            if (flag && isNotEmpty(x1_empty, y1_empty - 1, -1, -1)) {
                Position t = new Position(x1_empty, y1_empty - 1, -1, -1, 'R');
                ans.add(t);
            }
        }
        if (canMove(x1_empty, y1_empty, 'D')) {
            if (father != null) {
                flag = isNotFather(x1_empty - 1, y1_empty, -1, -1, 'D');
            }
            if (flag && isNotEmpty(x1_empty - 1, y1_empty, -1, -1)) {
                Position t = new Position(x1_empty - 1, y1_empty, -1, -1, 'D');
                ans.add(t);
            }
        }

        if (x2_empty != -1) {

            if (canMove(x2_empty, y2_empty, 'L')) {
                if (father != null) {
                    flag = isNotFather(x2_empty, y2_empty + 1, -1, -1, 'L');
                }
                if (flag && isNotEmpty(x2_empty, y2_empty + 1, -1, -1)) {
                    Position t = new Position(x2_empty, y2_empty + 1, -1, -1, 'L');
                    ans.add(t);
                }
            }
            if (canMove(x2_empty, y2_empty, 'U')) {

                if (father != null) {
                    flag = isNotFather(x2_empty + 1, y2_empty, -1, -1, 'U');
                }
                if (flag && isNotEmpty(x2_empty + 1, y2_empty, -1, -1)) {
                    Position t = new Position(x2_empty + 1, y2_empty, -1, -1, 'U');
                    ans.add(t);
                }
            }
            if (canMove(x2_empty, y2_empty, 'R')) {
                if (father != null) {
                    flag = isNotFather(x2_empty, y2_empty - 1, -1, -1, 'R');
                }
                if (flag && isNotEmpty(x2_empty, y2_empty - 1, -1, -1)) {
                    Position t = new Position(x2_empty, y2_empty - 1, -1, -1, 'R');
                    ans.add(t);
                }
            }
            if (canMove(x2_empty, y2_empty, 'D')) {
                if (father != null) {
                    flag = isNotFather(x2_empty - 1, y2_empty, -1, -1, 'D');
                }
                if (flag && isNotEmpty(x2_empty - 1, y2_empty, -1, -1)) {

                    Position t = new Position(x2_empty - 1, y2_empty, -1, -1, 'D');
                    ans.add(t);
                }
            }
        }
        Queue<state> res = new LinkedList<>();

        while (!ans.isEmpty()) {
            Position p = ans.poll();

            String[][] next = getSun(p);
            if (next != null) {
                String step = calcPath(greed, p);
                String path = this.path + step + "-";
                int w = calcWeight(p);
                int price = w + this.price;
                state.Position _p = checkEmpty(p, next);
                state son = new state(next, this, this.level, price, w, path, step, _p.getX1(), _p.getY1(), _p.getX2(), _p.getY2());
                res.add(son);
            }
        }

        return res;

    }

    /***
     * compute of a son state
     * @param greed
     * @param pos
     * @return
     */
    private String calcPath(String[][] greed, state.Position pos) {
        String ans = "";
        ans += greed[pos.getX1()][pos.getY1()];
        if (pos.getX2() != -1) {
            ans += "&" + greed[pos.getX2()][pos.getY2()];
        }
        ans += pos.getDir();
        return ans;
    }

    private int calcWeight(state.Position pos) {
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
    private state.Position checkEmpty(state.Position p, String[][] greed) {

        state.Position ans = new state.Position(p);
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
