/**
 * Main Driver File.
 * MemSimMain.java
 * CS 600.226 Data Structures Fall 2015
 * Assignemnt 4
 * Eli Pivo - epivo1
 * Raphael Norman-Tenazas - rtenaza1
 * William Watson - wwatso13
 */

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Memory Simulator.
 * Driver for MemSimulator project.
 */
public final class MemSimMain {
    
    /**
     * Empty Constructor for Checkstyle.
     */
    private MemSimMain() {
    }
    
    /**
     * Main program.
     * @param args for Checkstyle
     * @throws FileNotFoundException if file not found
     * @throws IOException for bad filename
     */
    public static void main(String[] args) throws FileNotFoundException,
        IOException {
        
        final int numApproaches = 3;
        Memory[] sims = new Memory[numApproaches];
        // sims[0] must be best fit, sims[1] is worst fit, sims[2] is yours
        
        
        // read input filename from keyboard, or get from command-line args
        String filename = "";
        if (args.length == 0) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter filename: ");
            filename = sc.nextLine();
            sc.close();
        } else {
            filename = args[0];
        }
        
        
        Scanner fromFile = new Scanner(new File(filename));
        int memSize = 0;
        
        try {
            memSize = Integer.parseInt(fromFile.nextLine());
        } catch (NumberFormatException e) {
            memSize = -1;
            System.out.println("invalid memory size, exiting");
            System.exit(1);
        }
        
        //make memory sims
        BestFitMemory bfm = new BestFitMemory(memSize);
        WorstFitMemory wfm = new WorstFitMemory(memSize);
        //what is initial threshold?
        final int twenty = 20;
        ThresholdMemory tfm = new ThresholdMemory(memSize, twenty);
        
        sims[0] = bfm;
        sims[1] = wfm;
        sims[2] = tfm;
                
        int allocNum = 1;
        
        while (fromFile.hasNextLine()) {
            String line = fromFile.nextLine();
            Scanner input = new Scanner(line);
            String choice = input.next();
            int num = getNum(input); //input.nextInt();
            
            if (choice.equalsIgnoreCase("A")) {
                //alloc
                for (int i = 0; i < numApproaches; i++) {
                    sims[i].allocate(num, allocNum);
                }
                allocNum++;
            } else if (choice.equalsIgnoreCase("D")) {
                //dealloc
                for (int i = 0; i < numApproaches; i++) {
                    sims[i].deallocate(num);
                }
            } else {
                System.err.println("Invalid Memory Command");
                System.exit(1);
            }
            
            input.close();
        }
        
        //close input
        fromFile.close();
        
        //translog output.
        PrintWriter outPut1 = new PrintWriter("translog.txt");
        outPut1.println("Memory Transaction Log");
        outPut1.println("Memory Size:" + memSize);
        outPut1.println("Input file: " + filename);
        outPut1.println("Memory Address begins at location 0");
        outPut1.println();
        
        outPut1.println("------------------------------------------------------"
            + "----------------------------------------------------------");
        outPut1.println("           Best Fit                                "
            + "Worst Fit                                Your Fit");
        outPut1.println("------------------------------------------------------"
            + "----------------------------------------------------------");
        outPut1.println("   ID  DF? Success  Addr.  Size           ID  DF? "
            + "Success  Addr.  Size           ID  DF? Success  Addr.  Size");
        outPut1.println();
        
        
        ArrayList<Metric> bMetric = ((BestFitMemory) sims[0]).getMetrics();
        ArrayList<Metric> wMetric = ((WorstFitMemory) sims[1]).getMetrics();
        ArrayList<Metric> tMetric = ((ThresholdMemory) sims[2]).getMetrics();
        
        for (int i = 0; i < bMetric.size(); i++) {
            outPut1.print(bMetric.get(i).toString());
            outPut1.print(wMetric.get(i).toString());
            outPut1.println(tMetric.get(i).toString());
        }
        
        outPut1.close();
        
        PrintWriter outPut = new PrintWriter("analysis.txt");
        //add analysis shit
        printOutput(outPut, sims, memSize, filename);
        
        //save files
    }

    /**
     * Gets the number from input.
     * Also checks for InputMismatchExceptions.
     * @param input Scanner with input
     * @return the int we are trying to get.
     */
    private static int getNum(Scanner input) {
        int i = 0;
        try {
            i = input.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Memory Simulator needs a number after "
                    + "Allocation or Deallocation Request.");
            System.exit(1);
        }
        return i;
    }
    
    
    /**
     * Print lines to analysis log.
     * @param outPut PrintWriter for analysis log
     * @param sims Array of MemoryManagers
     * @param memSize Amount of memory in this simulation
     * @param filename Input file name
     */
    public static void printOutput(PrintWriter outPut, Memory[] sims,
        int memSize, String filename) {
        outPut.println("Performance Analysis Chart");
        outPut.println("Memory Size " + memSize);
        outPut.println("Input File Used: " + filename);
        outPut.println("---------------------------------------------------"
            + "-------------------------");
        outPut.println("Statistics:                        Best Fit    "
            + "Worst Fit     Your Fit");
        outPut.println("---------------------------------------------------"
            + "-------------------------");
        outPut.println("");
        //defrags
        outPut.printf("%-35s%8s%13s%13s", "Number of Defragmentations:",
            ((BestFitMemory) sims[0]).getDefrag(), 
            ((WorstFitMemory) sims[1]).getDefrag(),
            ((ThresholdMemory) sims[2]).getDefrag());
        outPut.println("");
        //total fails
        outPut.printf("%-35s%8s%13s%13s", "# of failed allocation requests:",
            ((BestFitMemory) sims[0]).getFailedAllocs(), 
            ((WorstFitMemory) sims[1]).getFailedAllocs(),
            ((ThresholdMemory) sims[2]).getFailedAllocs());
        outPut.println("");
        //average faield size
        outPut.printf("%-35s%8s%13s%13s", "Average size failed allocs:",
            ((BestFitMemory) sims[0]).getFailedSize(), 
            ((WorstFitMemory) sims[1]).getFailedSize(),
            ((ThresholdMemory) sims[2]).getFailedSize());
        outPut.println("");
        //average time
        outPut.printf("%-35s%8.2f%13.2f%13.2f",
                "Average time to process alloc*:",
            ((BestFitMemory) sims[0]).getAvgTime(), 
            ((WorstFitMemory) sims[1]).getAvgTime(),
            ((ThresholdMemory) sims[2]).getAvgTime());
        outPut.println("");
        //average QS sort
        outPut.printf("%-35s%8.2f%13.2f%13.2f",
                "Average time/size quicksort*:",
             ((BestFitMemory) sims[0]).getQSTime(), 
             ((WorstFitMemory) sims[1]).getQSTime(),
             ((ThresholdMemory) sims[2]).getQSTime());
        outPut.println("");
        //average BS sort
        outPut.printf("%-35s%8.2f%13.2f%13.2f",
                "Average time/size bucketsort*:",
              ((BestFitMemory) sims[0]).getBSTime(), 
              ((WorstFitMemory) sims[1]).getBSTime(),
              ((ThresholdMemory) sims[2]).getBSTime());
        
        outPut.println("");
        outPut.println("");
        outPut.println("*All times in microseconds.");
        //save file
        outPut.close();
    }
     
}
