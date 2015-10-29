/* Elijah Pivo
 * epivo1
 * 600.226.01
 * Project 4
 */

/*
 * This will generate 100,000 random requests according to the 
 * following principles:
 * 
 * Allocation size is between 1 and 50 so average allocation and deallocation 
 * size is about 25. The threshold is then 40%  of the average allocation size, 
 * which seems reasonable to me.
 * 
 * Allocations are twice as likely as deallocations so  that memory should 
 * fill up. Probabilistically, memory should fill after about 8000 
 * deallocation or allocation requests. Once the memory is filled, 
 * defragmentations will occur frequently. I generated 100,000 different 
 * requests to ensure this happened many times.
 */

import java.io.FileWriter;
import java.io.IOException;

/**
 * Generates random requests for test input.
 * @author Elijah
 *
 */

public final class GenerateRequests {

    /**
     * To satisfy checkstyle.
     */
    private GenerateRequests() {
        
    }
    
    /**
     * The main method.
     * @param args command line input
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        
        FileWriter outputFile = new FileWriter("testInput.txt");
        
        int numDeallocs = 0;
        int numAllocs = 0;
        int totalAllocSize = 0;
        
        final int numRequests = 100000;
        for (int i = 0; i < numRequests; i++) {
            //select deallocation or allocation
            
            String request = "";
            
            final double percentAlloc = .75;
            if (Math.random() <= percentAlloc) {
                //make allocation
                
                final int maxSize = 50;
                int size = (int) (Math.random() * (maxSize - 1)) + 1;
                
                request += "A " + size;
                
                numAllocs++;
                totalAllocSize += size;
                
            } else {
                //make deallocation
                
                //get random alloc num, but ensure its within a valid range
                int allocNum = (int) (Math.random() * (numAllocs - 1)) + 1;
                
                request += "D " + allocNum;
                
                numDeallocs++;
                
            }
            
            outputFile.write(request + "\n");
            
        }
        
        System.out.println("Input generation complete.");
        System.out.println("Allocs made: " + numAllocs);
        System.out.println("Average alloc size: " 
                + ((double) totalAllocSize) / numAllocs);
        System.out.println("Deallocs made: " + numDeallocs);
        
        outputFile.close();

    }

}
