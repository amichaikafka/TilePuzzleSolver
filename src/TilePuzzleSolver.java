import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/***
 * This class is in charge of reading from the input file then to activate the right algorithm
 * and to write the result to the output file.
 */
public class TilePuzzleSolver {

    private FindPath solver;
    private State res;//contain the node with the solution
    private double time;


    public TilePuzzleSolver(String puzzleFile) {
        this.solver = readOutput(puzzleFile);
        double start=(double)(System.currentTimeMillis())/1000;
        this.res=solver.findPath();
        double end=(double)(System.currentTimeMillis())/1000;
        this.time=end-start;
        writeRes("output.txt");


    }

    /***
     * This function reads the input from the file and return the right solver(BFS,A*..etc...)
     * @param puzzleFile-file name
     * @return
     */
    private FindPath readOutput(String puzzleFile) {
        try {

            File myObj = new File(puzzleFile);
            Scanner myReader = new Scanner(myObj);
            int count=1;
            String algo="";
            String[][] start,goal;
            boolean time=false;
            boolean open=false;
            int n,m;
            String data="";
            while (count<=4) {
                data = myReader.nextLine();
                if(count==1){
                    algo=data;
                }else if(count==2){
                    time=data.equals("with time");
                }else if(count==3){
                    open=data.equals("with open");
                }else if (count==4){
                    String[] NxM = data.split("x", 2);
                    n=Integer.parseInt(NxM[0]);
                    m=Integer.parseInt(NxM[1]);
                    start=new String[n][m];
                    goal=new String[n][m];
                    int i=0;
                    data = myReader.nextLine();
                    while (!data.equals("Goal state:")){

                        String[] a = data.split(",");
                        start[i]=a;
                        i++;
                        data = myReader.nextLine();
                    }
                    i=0;
                    while (myReader.hasNextLine()){
                        data = myReader.nextLine();
                        String[] a = data.split(",");
                        goal[i]=a;
                        i++;
                    }
                    myReader.close();
                    switch (algo){
                        case "BFS":
                            return new BFS(start,goal,time,open);
                        case "DFID":
                            return new DFID(start,goal,time,open);
                        case "A*":
                            return new Astar(start,goal,time,open);
                        case "IDA*":
                            return new IDAstar(start,goal,time,open);
                        case "DFBnB":
                            return new DFBnB(start,goal,time,open);
                    }

                }
                count++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }
    /***
     * This function writes the output of the solution to file.
     * @param file-file name
     * @return
     */
    private   void writeRes(String file){
        try {

            FileWriter myWriter = new FileWriter(file);

            if(res==null){
                myWriter.append("no path\n");
                myWriter.append("Num: ").append(String.valueOf(PuzzleState.NUM_OF_STATES)).append("\n");
                if(solver.isWithTime()){
                    myWriter.append("").append(String.valueOf(time)).append(" seconds\n");
                }
                myWriter.close();
                return;
            }
            myWriter.append(res.getPath());
            myWriter.append("\n");
            myWriter.append("Num: ").append(String.valueOf(res.getId())).append("\n");
            myWriter.append("Cost: ").append(String.valueOf(res.getPrice())).append("\n");
            if(solver.isWithTime()){
                myWriter.append("").append(String.valueOf(time)).append(" seconds\n");
            }
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
