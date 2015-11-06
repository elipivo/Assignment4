import java.util.ArrayList;

/**
 * Interface Memory.java.
 * CS 600.226 Data Structures Fall 2015
 * Assignment 4
 * Eli Pivo - epivo1
 * Raphael Norman-Tenazas - rtenaza1
 * William Watson - wwatso13
 */
public class BestFitMemory implements Memory {
    
    /** The empty memory blocks sorted by size. */
    AVLtree<Block> emptyMemory;
    /** The filled memory blocks. */
    ArrayList<Block> filledMemory;
    /** The size of our memory. */
    int memSize;
    /** The metrics for each allocation. */
    ArrayList<Metric> metrics;
    /** The number of defragmentations. */
    int numDefrag;
    /** The number of failed allocations. */
    int numFailedAllocs;
    /** The total size of the failed allocations. */
    int sizeFailedAllocs;
    /** The total time spent allocating memory. */
    long allocTime;
    /** The total number of allocation attempts. */
    int numAllocs;
    /** The total time spent quick sorting. */
    long timeQuickSort;
    /** The total size of lists quicksorted. */
    long totalSizeQuickSort;
    /** The total time spent bucket sorting. */
    long timeBucketsort;
    /** The total time spent bucket sorting. */
    long totalSizeBucketsort;
    
    /**
     * Constructs a new BestFitMemory.
     * @param size the number of bits in memory
     */
    public BestFitMemory(int size) {
        this.emptyMemory = new AVLtree<Block>();
        this.emptyMemory.add(new Block(0, 0, size));
        
        this.filledMemory = new ArrayList<Block>();
        this.memSize = size;
        this.metrics = new ArrayList<Metric>();
        this.numDefrag = 0;
        this.numFailedAllocs = 0;
        this.sizeFailedAllocs = 0;
        this.allocTime = 0;
        this.numAllocs = 0;
        this.timeQuickSort = 0;
        this.totalSizeQuickSort = 0;
        this.timeBucketsort = 0;
        this.totalSizeBucketsort = 0;
    }
    
    @Override
    public int allocate(int size, int allocNum) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean deallocate(int allocNum) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void defrag() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ArrayList<Block> bucketSort() {
        
        final long startTime = System.currentTimeMillis();
        
        ArrayList<Block> sortedMemory = new ArrayList<Block>();
        
        Block[] memoryAddress = new Block[this.memSize];
        
        for (Block b : this.emptyMemory.inOrder()) {
            memoryAddress[b.getMemAddress()] = b;
        }
        
        for (int i = 0; i < memoryAddress.length; i++) {
            sortedMemory.add(memoryAddress[i]);
        }
        
        final long endTime = System.currentTimeMillis();
        
        this.totalSizeBucketsort += this.emptyMemory.size();
        this.timeBucketsort += endTime - startTime;
        
        return sortedMemory;
    }

    @Override
    public ArrayList<Block> quickSort() {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}
