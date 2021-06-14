import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class FindPathTest {

    String[][] goalTwoEmpty = {{"1", "2", "3"}, {"4", "5", "6"}, {"7", "_", "_"}};
    String[][] goalOneEmpty = {{"1", "2", "3", "4"}, {"5", "6", "7", "8"}, {"9", "10", "11", "_"}};
    FindPath fp;
    State s;


    @Test
    void heuristic() {
        String[][] gaolTwo = {{"1", "2", "3"}, {"4", "5", "6"}, {"7", "_", "_"}};
        fp = new Astar(gaolTwo, gaolTwo, false);
        s = new PuzzleState(gaolTwo);
        fp.makeGoalMap();
        fp.heuristic(s);
        assertEquals(0, s.getHeuristic());
        String[][] state = {{"1", "3", "2"}, {"4", "_", "_"}, {"7", "5", "6"}};
        fp = new DFBnB(state, gaolTwo, false);
        fp.makeGoalMap();
        s = new PuzzleState(state);
        fp.heuristic(s);
        assertEquals(18, s.getHeuristic());
        String[][] state2 = {{"1", "3", "2"}, {"4", "_", "_"}, {"6", "5", "7"}};
        s = new PuzzleState(state2);
        fp.heuristic(s);
        assertEquals(30, s.getHeuristic());
        String[][] stateOneEmpty = {{"1", "3", "2", "4"}, {"6", "5", "_", "8"}, {"9", "11", "10", "7"}};
        fp = new IDAstar(state, goalOneEmpty, false);
        fp.makeGoalMap();
        s = new PuzzleState(stateOneEmpty);
        fp.heuristic(s);
        assertEquals(70, s.getHeuristic());

    }

    @Test
    void findPath() {
        String[][] startOneEmpty = {{"1", "2", "3", "4"}, {"5", "6", "11", "7"}, {"9", "10", "8", "_"}};
        fp = new Astar(startOneEmpty, goalOneEmpty, false);
        assertEquals(20, fp.findPath().getPrice());
        fp = new IDAstar(startOneEmpty, goalOneEmpty, false);
        assertEquals(20, fp.findPath().getPrice());
        fp = new DFBnB(startOneEmpty, goalOneEmpty, false);
        assertEquals(20, fp.findPath().getPrice());
        fp = new BFS(startOneEmpty, goalOneEmpty, false);
        assertEquals(20, fp.findPath().getPrice());
        fp = new DFID(startOneEmpty, goalOneEmpty, false);
        assertEquals(20, fp.findPath().getPrice());

        String[][] startTwoEmpty = {{"1", "2", "4"}, {"5", "6","3"}, {"7", "_", "_"}};
        fp = new Astar(startTwoEmpty, goalTwoEmpty, false);
        assertEquals(60, fp.findPath().getPrice());
        fp = new IDAstar(startTwoEmpty, goalTwoEmpty, false);
        assertEquals(60, fp.findPath().getPrice());
        fp = new DFBnB(startTwoEmpty, goalTwoEmpty, false);
        assertEquals(60, fp.findPath().getPrice());
        fp = new BFS(startTwoEmpty, goalTwoEmpty, false);
        assertEquals(60, fp.findPath().getPrice());
        fp = new DFID(startTwoEmpty, goalTwoEmpty, false);
        assertEquals(60, fp.findPath().getPrice());

    }

    @Test
    void getSuccessors() {
        String[][] state = {{"1", "3", "2"}, {"4", "_", "_"}, {"7", "5", "6"}};
        s = new PuzzleState(state);
        Queue<State> q = s.getSuccessors();
        assertEquals(7, q.size());
        String[][] state2={{"1", "2", "3", "4"}, {"5", "6", "11", "7"}, {"9", "10", "8", "_"}};
        s=new PuzzleState(state2);
        q=s.getSuccessors();
        assertEquals(2,q.size());
    }
}