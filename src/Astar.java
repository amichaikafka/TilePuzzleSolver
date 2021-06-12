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
    public State findPath() {
        place();
        Hashtable<State, State> open = new Hashtable<>();
        Hashtable<State, State> close = new Hashtable<>();
        State start = new PuzzleState(initialstate);


        PriorityQueue<State> pq = new PriorityQueue();

        pq.add(start);
        open.put(start, start);
        while (!pq.isEmpty()) {
            if (withOpen) {
                System.out.println("open\n" + pq);
            }

            State n = pq.poll();
            if (Arrays.deepEquals(goal, n.getGreed())) {
                return n;
            }
            close.put(n, n);
            Queue<State> opertion = n.getSuccessors();
            while (!opertion.isEmpty()) {
                State son = opertion.poll();
                heuristic(son);


                if (close.get(son) == null && open.get(son) == null) {
                    open.put(son, son);
                    pq.add(son);
                } else if (open.get(son).getHeuristic() > son.getHeuristic()) {
                    open.get(son).setPrice(son.getPrice());
                    open.get(son).setPath(son.getPath());
                }

            }
        }
        return null;
    }
}
