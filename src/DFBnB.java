import java.util.*;
/***
 * This class extends FindPath represents implementaion of DFBnB algorithm.
 */
public class DFBnB extends FindPath {
    public DFBnB(String[][] initialstate, String[][] goal, boolean withOpen) {
        super(initialstate, goal, withOpen);
    }
    /***
     * The DFBnB algorithm.
     * @return state gaol.
     */
    @Override
    public State findPath() {
        makeGoalMap();
        Hashtable<State, State> open = new Hashtable<>();
        LinkedList<State> stack = new LinkedList<>();
        State start = new PuzzleState(getInitialstate());
        stack.push(start);
        open.put(start, start);
        double t = Double.MAX_VALUE;
        State result = null;
        while (!stack.isEmpty()) {
            if (isWithOpen()) {
                System.out.println("open\n" + stack);
            }
            State n = stack.pop();
            if (n.isOut()) {
                open.remove(n, n);
            } else {
                n.setOut(true);
                stack.push(n);

                Queue<State> opertion = n.getSuccessors();
                LinkedList<State> remains = new LinkedList<>();
                PriorityQueue<State> N = new PriorityQueue();
                while (!opertion.isEmpty()) {
                    State son = opertion.poll();
                    heuristic(son);

                    N.add(son);
                }

                while (!N.isEmpty()) {
                    State son = N.poll();

                    if (son.getHeuristic() >= t) {
                        N.clear();
                    } else if (open.get(son) != null && open.get(son).isOut()) {
                        continue;
                    } else if (open.get(son) != null && !open.get(son).isOut()) {
                        if (open.get(son).getHeuristic() <= son.getHeuristic()) {
                            continue;
                        } else {
                            stack.remove(open.get(son));
                            open.remove(open.get(son), open.get(son));
                            remains.addFirst(son);
                        }
                    } else if (Arrays.deepEquals(son.getGreed(), getGoal())) {
                        t = son.getHeuristic();
                        result = son;
                        N.clear();
                    } else if (open.get(son) == null) {
                        remains.addFirst(son);
                    }
                }

                while (!remains.isEmpty()) {
                    State son = remains.poll();
                    stack.push(son);
                    open.put(son, son);
                }
            }
        }

        return result;
    }
}
