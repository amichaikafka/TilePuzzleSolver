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
    public state findPath() {
        place();
        Hashtable<state, state> open = new Hashtable<state, state>();
        LinkedList<state> stack = new LinkedList<>();

        state start = new state(initialstate, null, 0, 0, 0, "", "", this.x1_empty, this.y1_empty, this.x2_empty, this.y2_empty);
        Heuristic(start);
        double t = start.getWight();
        while (t != Double.MAX_VALUE) {

            double minf = Double.MAX_VALUE;
            start.setOut(false);
            //start.TIME_OF_CREATION = 1;
            stack.push(start);
            open.put(start, start);
            System.out.println("s=" + stack.size() + "  o=" + open.size());
            int c = 1;
            while (!stack.isEmpty()) {
                if (withOpen) {
                    System.out.println("open\n" + stack);
                }
                state n = stack.pop();


                if (n.isOut()) {
                    open.remove(n, n);
                } else {

                    n.setOut(true);
                    stack.push(n);
                    if (withOpen) {
                        System.out.println("open\n" + stack);
                    }
                    Queue<state> opertion = n.order();


                    while (!opertion.isEmpty()) {

                        c++;
                        state son = opertion.poll();

                        Heuristic(son);

                        if (son.getWight() > t) {

                            minf = Math.min(son.getWight(), minf);

                            continue;
                        } else if (open.get(son) != null && open.get(son).isOut()) {
                            continue;
                        } else if (open.get(son) != null && !open.get(son).isOut()) {
                            if (open.get(son).getWight() > son.getWight()) {
                                stack.remove(open.get(son));
                                open.remove(open.get(son), open.get(son));
                            } else {
                                continue;
                            }
                        }
                        if (Arrays.deepEquals(son.getGreed(), goal)) {
                            String str = son.getPath();
                            son.setPath(str.substring(0, str.length() - 1));
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
