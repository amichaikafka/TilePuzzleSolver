import java.util.Arrays;
import java.util.Hashtable;
import java.util.Queue;
/***
 * This class extends FindPath represents implementaion of DFID algorithm.
 */
public class DFID extends FindPath {
    private final PuzzleState cutOff = new PuzzleState();//cutoff state

    public DFID(String[][] initialstate, String[][] goal, boolean withTime, boolean withOpen) {
        super(initialstate, goal, withTime, withOpen);
    }
    /***
     * The DFID algorithm.
     * @return state gaol.
     */
    @Override
    public State findPath() {
        State start = new PuzzleState(initialstate, this.x1_empty, this.y1_empty, this.x2_empty, this.y2_empty);
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            Hashtable<State, State> open = new Hashtable<>();

            State res = Limited_DFS(start, i, open);

            if (res != cutOff) {
                if (res != null && res.getPath().length() > 0) {
                    String str = res.getPath();
                    res.setPath(str.substring(0, str.length() - 1));
                }
                return res;
            }
        }
        return null;
    }

    private State Limited_DFS(State n, int limit, Hashtable<State, State> open) {
        if (Arrays.deepEquals(n.getGreed(), goal)) {
            return n;
        } else if (limit ==0) {
            return cutOff;
        } else {
            if (withOpen) {
                System.out.println("open\n" + open.values());
            }
            updateEmpty(n.getEmpty());
            open.put(n, n);
            Queue<State> opertion = n.getSuccessors();

            boolean isCutOff = false;

            while (!opertion.isEmpty()) {
                State son = opertion.poll();
                if (open.get(son) == null) {

                    State res = Limited_DFS(son, limit-1, open);
                    if (res == cutOff) {
                        isCutOff = true;
                    } else if (res != null) {


                        return res;
                    }
                }
            }

            open.remove(n, n);
            if (isCutOff) {
                return cutOff;
            }
            return null;
        }

    }

}
