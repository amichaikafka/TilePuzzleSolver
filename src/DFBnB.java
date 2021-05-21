import java.util.*;
/***
 * This class extends FindPath represents implementaion of DFBnB algorithm.
 */
public class DFBnB extends FindPath {
    public DFBnB(String[][] initialstate, String[][] goal, boolean withTime, boolean withOpen) {
        super(initialstate, goal, withTime, withOpen);
    }
    /***
     * The DFBnB algorithm.
     * @return state gaol.
     */
    @Override
    public state findPath() {
        place();
        Hashtable<state, state> open = new Hashtable<state, state>();
        LinkedList<state> stack = new LinkedList<>();
        myComper compare = new myComper();
        state start = new state(initialstate, null, 0, 0, 0, "", "", this.x1_empty, this.y1_empty, this.x2_empty, this.y2_empty);
        stack.push(start);
        open.put(start, start);
        double t = Double.MAX_VALUE;
        state result = null;
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

                Queue<state> opertion = n.order();
                LinkedList<state> remains = new LinkedList<>();
                PriorityQueue<state> N = new PriorityQueue(compare);
                while (!opertion.isEmpty()) {
                    state son = opertion.poll();
                    Heuristic(son);

                    N.add(son);
                }

                while (!N.isEmpty()) {
                    state son = N.poll();

                    if (son.getWight() >= t) {
                        N.clear();
                    } else if (open.get(son) != null && open.get(son).isOut()) {
                        continue;
                    } else if (open.get(son) != null && !open.get(son).isOut()) {
                        if (open.get(son).getWight() <= son.getWight()) {
                            continue;
                        } else {
                            stack.remove(open.get(son));
                            open.remove(open.get(son), open.get(son));
                            remains.addFirst(son);
                        }
                    } else if (Arrays.deepEquals(son.getGreed(), goal)) {
                        t = son.getWight();
                        result = son;
                        N.clear();
                    } else if (open.get(son) == null) {
                        remains.addFirst(son);
                    }
                }

                while (!remains.isEmpty()) {
                    state son = remains.poll();
                    stack.push(son);
                    open.put(son, son);
                }
            }
        }
        if (result != null) {
            String str = result.getPath();
            result.setPath(str.substring(0, str.length() - 1));
        }


        return result;
    }
}
