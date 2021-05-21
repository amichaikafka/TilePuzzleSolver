import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
/***
 * This class extends FindPath represents implementaion of BFS algorithm.
 */
public class BFS extends FindPath {

    public BFS(String[][] initialstate, String[][] goal, boolean withTime, boolean withOpen) {
        super(initialstate, goal, withTime, withOpen);
    }
    /***
     * The BFS algorithm.
     * @return state gaol/
     */
    @Override
    public state findPath() {
        Queue<state> q = new LinkedList<state>();
        Hashtable<state, state> open = new Hashtable<state, state>();
        Hashtable<state, state> close = new Hashtable<state, state>();
        state start = new state(initialstate, null, 0, 0, 0, "", "", this.x1_empty, this.y1_empty, this.x2_empty, this.y2_empty);
        q.add(start);
        open.put(start, start);
        while (!q.isEmpty()) {
            if (withOpen) {
                System.out.println("open\n" + q);
            }
            state n = q.poll();
            if (Arrays.deepEquals(n.getGreed(), goal)) {
                return n;
            }
            updateEmpty(n.getEmpty());
            close.put(n, n);
            Queue<state> opertion = n.order();
            while (!opertion.isEmpty()) {
                state son = opertion.poll();
                if (close.get(son) != null || open.get(son) != null) {
                    continue;
                }

                if (Arrays.deepEquals(son.getGreed(), goal)) {
                    String str = son.getPath();
                    son.setPath(str.substring(0, str.length() - 1));
                    return son;
                }
                q.add(son);
                open.put(son, son);
            }
        }
        return null;
    }


}
