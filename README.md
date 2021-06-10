# TilePuzzleSolver
* The goal of this program is to solve a Tilepuzzel which is a puzzle n*m with empty spot (one or two) that allwod to move the spot around the empty
* to the empty one until reaching the goal stat.
* The program gets a start state and goal and return a way to solve the puzzle using a variety of algorithms including informed algorithms.

## Program structure
## Interfaces and abstract class
### State
This interface represent State.
State- part of the search space we are in.
### FindPath
This abstract class represents a frame for finding the way to solve the puzzle.(strategy design pattern)
The class contain all the necessary thing for solving such as:initialstate,goal etc..
You need to extend this class and implements findpath() function.
## Classes 
### PuzzleState
This class implements State interface. 
### TilePuzzleSolver
This class is in charge of reading from the input file then to activate the right algorithm
and to write the result to the output file.
### BFS
This class extends FindPath represents implementaion of BFS algorithm.
### Astar
This class extends FindPath represents implementaion of A* algorithm .
### DFID
This class extends FindPath represents implementaion of DFID algorithm.
### IDAstar
This class extends FindPath represents implementaion of IDA* algorithm.
### DFBnB
This class extends FindPath represents implementaion of DFBnB algorithm.


