import java.util.Queue;

/***
 * This interface represent State.
 * State- part of the search space we are in.
 */
public interface State {
    /***
     * compute all the successors of this state according to the right order.
     * @return a Queue with those successors in the right order
     */
    public Queue<State> getSuccessors();

    /***
     * @return the father state of to this state
     */
    public State getFather();
    /***
     * @return the locations of all the empty spots of this state.
     */
    public int[] getEmpty();
    /***
     * @return the id of this state(which is the amount of stats).
     */
    public int getId();
    /***
     * @return the path up to this state
     */
    public String getPath();
    /***
     * set the path up to this state
     * @param path path to set
     */
    public void setPath(String path);
    /***
     * check if this state is "out" for IDA* and DFBnB.
     * @return
     */
    public boolean isOut();
    /***
     * Set this state to be out.
     * @param out
     */
    public void setOut(boolean out);
    /***
     * @return the greed of this state
     */
    public String[][] getGreed();
    /***
     * @return the price up to this state.
     */
    public int getPrice();
    /***
     * set the ptice of this state
     * @param price price
     */
    public void setPrice(int price);
    /***
     * @return the heuristic value of this state(only for informed algorithms).
     */
    public double getHeuristic();
    /***
     * set he heuristic value of this state(only for informed algorithms).
     * @param heuristic heuristic value
     */
    public void setHeuristic(double heuristic);


}
