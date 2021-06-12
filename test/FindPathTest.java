import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FindPathTest {
    String[][] gaol={{"1","2","3"},{"4","5","6"},{"7","_","_"}};
    Astar f=new Astar(gaol,gaol,false);


    @Test
    void heuristic() {
        State s=new PuzzleState(gaol);
        f.place();
        f.heuristic(s);
        assertEquals(0,s.getHeuristic());


    }

    @Test
    void place() {
        f.place();

    }

    @Test
    void findPath() {
    }
}