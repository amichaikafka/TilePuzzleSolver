import java.util.Arrays;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Queue;

/***
 * This class extends FindPath represents implementaion of A* algorithm.
 */
public class Astar extends FindPath {
    public Astar(String[][] initialstate, String[][] goal, boolean withTime, boolean withOpen) {
        super(initialstate, goal, withTime, withOpen);
    }

    /***
     * The A* algorithm.
     * @return state gaol/
     */
    @Override
    public state findPath() {
        place();
        Hashtable<state, state> open = new Hashtable<state, state>();
        Hashtable<state, state> close = new Hashtable<state, state>();
        state start = new state(initialstate, null, 0, 0, 0, "", "", this.x1_empty, this.y1_empty, this.x2_empty, this.y2_empty);

        myComper compare = new myComper();
        PriorityQueue<state> pq = new PriorityQueue(compare);
        pq.add(start);
        open.put(start, start);
        while (!pq.isEmpty()) {
            if (withOpen) {
                System.out.println("open\n" + pq);
            }

            state n = pq.poll();
            if (Arrays.deepEquals(goal, n.getGreed())) {
                String str = n.getPath();
                n.setPath(str.substring(0, str.length() - 1));
                return n;
            }
            close.put(n, n);
            Queue<state> opertion = n.order();
            while (!opertion.isEmpty()) {
                state son = opertion.poll();
                Heuristic(son);


                if (close.get(son) == null && open.get(son) == null) {
                    open.put(son, son);
                    pq.add(son);
                } else if (open.get(son).getWight() > son.getWight()) {
                    open.get(son).setPrice(son.getPrice());
                    open.get(son).setPath(son.getPath());
                }

            }
        }
        return null;
    }
}
