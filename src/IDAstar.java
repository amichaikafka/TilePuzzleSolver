import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
/***
 * This class extends FindPath represents implementaion of IDA* algorithm.
 */
public class IDAstar extends FindPath {
    public IDAstar(String[][] initialstate, String[][] goal, boolean withTime, boolean withOpen) {
        super(initialstate, goal, withTime, withOpen);
    }
    /***
     * The IDA* algorithm.
     * @return state gaol.
     */
    @Override
    public State findPath() {
        place();
        Hashtable<State, State> open = new Hashtable<>();
        LinkedList<State> stack = new LinkedList<>();
        State start = new PuzzleState(initialstate);
        heuristic(start);
        double t = start.getHeuristic();
        while (t != Double.MAX_VALUE) {

            double minf = Double.MAX_VALUE;
            start.setOut(false);

            stack.push(start);
            open.put(start, start);


            while (!stack.isEmpty()) {
                if (withOpen) {
                    System.out.println("open\n" + stack);
                }
                State n = stack.pop();


                if (n.isOut()) {
                    open.remove(n, n);
                } else {

                    n.setOut(true);
                    stack.push(n);
                    if (withOpen) {
                        System.out.println("open\n" + stack);
                    }
                    Queue<State> opertion = n.getSuccessors();


                    while (!opertion.isEmpty()) {


                        State son = opertion.poll();

                        heuristic(son);

                        if (son.getHeuristic() > t) {

                            minf = Math.min(son.getHeuristic(), minf);

                            continue;
                        } else if (open.get(son) != null && open.get(son).isOut()) {
                            continue;
                        } else if (open.get(son) != null && !open.get(son).isOut()) {
                            if (open.get(son).getHeuristic() > son.getHeuristic()) {
                                stack.remove(open.get(son));
                                open.remove(open.get(son), open.get(son));
                            } else {
                                continue;
                            }
                        }
                        if (Arrays.deepEquals(son.getGreed(), goal)) {

                            return son;
                        }
                        stack.push(son);
                        open.put(son, son);
                    }
                }
            }
            t = minf;

        }
        return null;
    }
}
