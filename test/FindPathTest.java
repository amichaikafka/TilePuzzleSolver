import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FindPathTest {

    String[][] gaolTwoEmpty={{"1","2","3"},{"4","5","6"},{"7","_","_"}};
    String[][] gaolOneEmpty={{"1","2","3","4"},{"5","6","7","8"},{"9","10","11","_"}};
    FindPath fp;
    State s;


    @Test
    void heuristic() {
        String[][] gaolTwo={{"1","2","3"},{"4","5","6"},{"7","_","_"}};
        fp=new Astar(gaolTwo, gaolTwo,false);
        s=new PuzzleState(gaolTwo);
        fp.place();
        fp.heuristic(s);
        assertEquals(0,s.getHeuristic());
        String[][] state={{"1","3","2"},{"4","_","_"},{"7","5","6"}};
        fp=new DFBnB(state, gaolTwo,false);
        fp.place();
        s=new PuzzleState(state);
        fp.heuristic(s);
        assertEquals(18,s.getHeuristic());
        String[][]  state2={{"1","3","2"},{"4","_","_"},{"6","5","7"}};
        s=new PuzzleState(state2);
        fp.heuristic(s);
        assertEquals(30,s.getHeuristic());
        String[][]  stateOneEmpty={{"1","3","2","4"},{"6","5","_","8"},{"9","11","10","7"}};
        fp=new IDAstar(state,gaolOneEmpty,false);
        fp.place();
        s=new PuzzleState(stateOneEmpty);
        fp.heuristic(s);
        assertEquals(70,s.getHeuristic());

    }
    @Test
    void findPath() {
        String[][] startOneEmpty={{"1","2","3","4"},{"5","6","11","7"},{"9","10","8","_"}};
        fp=new Astar(startOneEmpty,gaolOneEmpty,false);
        assertEquals(20,fp.findPath().getPrice());
        fp=new IDAstar(startOneEmpty,gaolOneEmpty,false);
        assertEquals(20,fp.findPath().getPrice());
        fp=new DFBnB(startOneEmpty,gaolOneEmpty,false);
        assertEquals(20,fp.findPath().getPrice());
        fp=new BFS(startOneEmpty,gaolOneEmpty,false);
        assertEquals(20,fp.findPath().getPrice());
        fp=new DFID(startOneEmpty,gaolOneEmpty,false);
        assertEquals(20,fp.findPath().getPrice());

        String[][] startTwoEmpty={{"1","_","4"},{"3","5","6"},{"2","_","7"}};
        fp=new Astar(startTwoEmpty,gaolTwoEmpty,false);
        assertEquals(76,fp.findPath().getPrice());
        fp=new IDAstar(startTwoEmpty,gaolTwoEmpty,false);
        assertEquals(76,fp.findPath().getPrice());
        fp=new DFBnB(startTwoEmpty,gaolTwoEmpty,false);
        assertEquals(76,fp.findPath().getPrice());
        fp=new BFS(startTwoEmpty,gaolTwoEmpty,false);
        assertEquals(76,fp.findPath().getPrice());
        fp=new DFID(startTwoEmpty,gaolTwoEmpty,false);
        assertEquals(76,fp.findPath().getPrice());


    }
}