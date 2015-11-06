import java.util.ArrayList;

/**
 * Worst Fit Memory Implementation.
 * CS 600.226 Data Structures Fall 2015
 * Assignment 4
 * Eli Pivo - epivo1
 * Raphael Norman-Tenazas - rtenaza1
 * William Watson - wwatso13
 */
public class WorstFitMemory implements Memory {
    
    /** Max Heap holds Memory Blocks. */
    private MaxHeap<Block> emptyMemory;
    /** List of filled Memory. */
    private ArrayList<Block> filledMemory;
    /** Size of total memory blocks. */
    private int memSize;
    /** Stores the metrics. */
    private ArrayList<Metric> metrics;
    /** Number of defrags that have occurred. */
    private int numDefrag;
    /** Number of failed alloc attempts. */
    private int numFailedAllocs;
    /** Total size of all failed allocs. */
    private int sizeFailedAllocs;
    /** Length of time for all allocs so far. */
    private long allocTime;
    /** Num of alloc occurred so far. */
    private int numAllocs;
    /** Cumulative amt of time for QS. */
    private long timeQucksort;
    /** Cumulative size of QS. */
    private long totalSizeQuicksort;
    /** Cumulative time taken by BS. */
    private long timeBucketsort;
    /** Cumulative time by BS. */
    private long totalSizeBucketsort;
    
    /**
     * Default Constructor.
     * @param size size request by user.
     */
    public WorstFitMemory(int size) {
        this.emptyMemory = new MaxHeap<Block>();
        Block tmp = new Block(0, 0, size);
        this.emptyMemory.add(tmp);
        this.filledMemory = new ArrayList<Block>();
        this.memSize = 0;
        this.metrics = new ArrayList<Metric>();
        this.numAllocs = 0;
        this.numDefrag = 0;
        this.numFailedAllocs = 0;
        this.allocTime = 0;
        this.sizeFailedAllocs = 0;
        this.timeBucketsort = 0;
        this.timeQucksort = 0;
        this.totalSizeBucketsort = 0;
        this.totalSizeQuicksort = 0;
    }
    
    @Override
    public int allocate(int size, int allocNumTmp) {
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
        ArrayList<Block> sortedMemory = new ArrayList<Block>();
        
        Block[] memoryAddress = new Block[this.memSize];
        
        for (Block b : this.emptyMemory.toArrayList()) {
            memoryAddress[b.getMemAddress()] = b;
        }
        
        for (int i = 0; i < memoryAddress.length; i++) {
            sortedMemory.add(memoryAddress[i]);
        }
        
        return sortedMemory;
    }

    @Override
    public ArrayList<Block> quickSort() {
        
        return null;
    }
    
}
