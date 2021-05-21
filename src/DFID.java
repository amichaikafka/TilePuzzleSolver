import java.util.Arrays;
import java.util.Hashtable;
import java.util.Queue;
/***
 * This class extends FindPath represents implementaion of DFID algorithm.
 */
public class DFID extends FindPath {
    private final state cutOff = new state();//cutoff state

    public DFID(String[][] initialstate, String[][] goal, boolean withTime, boolean withOpen) {
        super(initialstate, goal, withTime, withOpen);
    }
    /***
     * The DFID algorithm.
     * @return state gaol.
     */
    @Override
    public state findPath() {
        state start = new state(initialstate, null, 0, 0, 0, "", "", this.x1_empty, this.y1_empty, this.x2_empty, this.y2_empty);

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            Hashtable<state, state> open = new Hashtable<>();
            state.NUM_OF_STATES = 1;
            state res = Limited_DFS(start, i, open);
            System.out.println(i);
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

    private state Limited_DFS(state n, int limit, Hashtable<state, state> open) {
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
            Queue<state> opertion = n.order();

            boolean isCutOff = false;

            while (!opertion.isEmpty()) {
                state son = opertion.poll();
                if (open.get(son) == null) {

                    state res = Limited_DFS(son, limit-1, open);
                    if (res == cutOff) {
                        isCutOff = true;
                    } else if (res != null) {

                        System.out.println(res);
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
