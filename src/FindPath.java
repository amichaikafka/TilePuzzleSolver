import java.util.Comparator;
import java.util.Hashtable;

/***
 * This abstract class represents a frame for finding the way to solve the puzzle.(strategy design pattern)
 * The class contain all the necessary thing for solving such as:initialstate,goal etc..
 * You need to extend this class and implements findpath() function.
 *
 */
public abstract class FindPath {
    private final String EMPTY = "_";
    private Hashtable<String, int[]> indexGaol = new Hashtable<>();
    private String[][] initialstate;
    private String[][] goal;

    private boolean withOpen;

    /***
     * constructor
     * @param initialstate- the state we start from
     * @param goal- the goal state
     * @param withOpen-if we want to display the open list in each iteration
     */
    public FindPath(String[][] initialstate, String[][] goal,  boolean withOpen) {
        this.initialstate = initialstate;
        this.goal = goal;
        this.withOpen = withOpen;
    }

    protected String[][] getInitialstate() {
        return initialstate;
    }

    protected String[][] getGoal() {
        return goal;
    }

    protected boolean isWithOpen() {
        return withOpen;
    }



    /**
     * return the Manhattan distance between two point.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private double manhattan(int x1, int y1, int x2, int y2) {
        double m = (Math.abs(x1 - x2) + Math.abs(y1 - y2));
        return m ;
    }



    /***
     * Heuristic function for the Informed algorithms.
     * sum(Manhattan(n))+2xlinear conflict(n)
     * @param n
     */
    protected void heuristic(State n) {

        int linar = 0;
        double sum = 0, avg;
        String[][] curr = n.getGreed();
        for (int i = 0; i < curr.length; i++) {
            for (int j = 0; j < curr[0].length; j++) {
                int[] index = indexGaol.get(curr[i][j]);
                double m = manhattan(i, j, index[0], index[1]);
                if (m != 0) {
                    if (!curr[i][j].equals(EMPTY)) {
                        sum += m;

                    }
                }
            }
        }
        linar = findConflict(curr);
        if(n.isTwoEmpty()){
            linar*=3;
            sum*=3;
        }else{
            linar*=5;
            sum*=5;
        }
        n.setHeuristic(n.getPrice() + sum + 2 * linar);
    }

    /***
     * return the amount of linear conflict in a greed
     * @param greed
     * @return
     */
    private int findConflict(String[][] greed) {
        int linar = 0;
        for (int i = 0; i < greed.length; i++) {
            for (int j = 0; j < greed[0].length; j++) {

                if (!greed[i][j].equals(EMPTY)) {
                    int[] index = indexGaol.get(greed[i][j]);
                    int[] conflict;
                    if (i == index[0]) {
                        for (int x = j + 1; x < greed[0].length; x++) {
                            if (!greed[i][x].equals(EMPTY)) {
                                conflict = indexGaol.get(greed[i][x]);
                                if (i == conflict[0] && index[1] > conflict[1]) {
                                    linar++;
                                    j = x - 1;
                                    break;
                                }

                            }
                        }

                    }
                }
            }
        }
        for (int i = 0; i < greed[0].length; i++) {
            for (int j = 0; j < greed.length; j++) {
                if (!greed[j][i].equals(EMPTY)) {
                    int[] index = indexGaol.get(greed[j][i]);
                    int[] conflict;
                    if (i == index[1]) {
                        for (int x = j + 1; x < greed.length; x++) {
                            if (!greed[x][i].equals(EMPTY)) {
                                conflict = indexGaol.get(greed[x][i]);
                                if (i == conflict[1] && index[0] > conflict[0]) {
                                    linar++;
                                    j = x - 1;
                                    break;
                                }

                            }
                        }

                    }
                }
            }
        }

        return  linar;
    }

    /***
     * Make a map that contain for each string in the gaol its location,
     * for support o(1) time when we need it in the Heuristic function.
     */
    protected void place() {
        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j < goal[0].length; j++) {
                indexGaol.put(goal[i][j], new int[]{i, j});
            }
        }
    }



    /***
     * calculate the path from the initial state to the gaol state.
     * @return
     */
    public abstract State findPath();
}
