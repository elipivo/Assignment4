import java.util.ArrayList;

/**
 * Interface Memory.java.
 * CS 600.226 Data Structures Fall 2015
 * Assignment 4
 * Eli Pivo - epivo1
 * Raphael Norman-Tenazas - rtenaza1
 * William Watson - wwatso13
 */
public interface Memory {
    
    /**This method creates a block of memory 
     * with the corresponding size and Allocation ID number. 
     * The size is determined by the user while the allocation number 
     * is determined by the CPU (Main) based upon 
     * previous user requests. 
     * @param size Size of the block to be allocated.
     * @param allocNum The Allocation Number.
     * @return address of the block of memory, or -1 if failed.
     */
    int allocate(int size, int allocNum);
    
    /**
     * deallocate a block of memory (free it) based upon 
     * an allocation number provided by the user.
     * @param allocNum The allocation number to be freed.
     * @return true if attempt was successful, otherwise false.
     */
    boolean deallocate(int allocNum);
    
    /**
     * Defragments the memory.
     */
    void defrag();
    
    /**
     * Implements BucketSort.
     * @return sorted list of memory blocks via ArrayList.
     */
    ArrayList<Block> bucketSort();
    
    /**
     * Implements QuickSort.
     * @return sorted list of memory blocks via ArrayList.
     */
    ArrayList<Block> quickSort();
    
}
