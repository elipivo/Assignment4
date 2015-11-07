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
        final long startTime = System.currentTimeMillis();
        
        //avoids refrence to tree and thus sorting actual tree.
        ArrayList<Block> tmp = new ArrayList<Block>();
        
        for (Block b : this.emptyMemory.inOrder()) {
            tmp.add(b);
        }
        
        this.qsort(tmp, 0, tmp.size() - 1);
        
        final long endTime = System.currentTimeMillis();
        this.totalSizeQuickSort += this.emptyMemory.size();
        this.timeQuickSort += endTime - startTime;
        
        return tmp;
    }
    
    /**
     * Private helper method for quicksort.
     * @param tmp ArrayList to sort
     * @param lo lower bound
     * @param hi upper bound
     */
    private void qsort(ArrayList<Block> tmp, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int j = this.partition(tmp, lo, hi); //partition it
        this.qsort(tmp, lo, j - 1); //sort left part
        this.qsort(tmp, j + 1, hi); //sort right part
    }
    /**
     * Partitions the array to sort.
     * @param tmp arraylist to sort
     * @param lo lower bound
     * @param hi upper bound
     * @return j
     */
    private int partition(ArrayList<Block> tmp, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Block v = tmp.get(lo);
        while (true) {
            while (tmp.get(++i).getMemAddress().compareTo(v.getMemAddress()) < 0) {
                if (i == hi) {
                    break;
                }
            }
            while (v.getMemAddress().compareTo(tmp.get(--j).getMemAddress()) < 0) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            this.swap(tmp, i, j);   
        }
        this.swap(tmp, lo, j);
        return j;
    }
    
    /**
     * Swaps the element at i with j in tmp.
     * @param tmp array list
     * @param i first index
     * @param j second index
     */
    private void swap(ArrayList<Block> tmp, int i, int j) {
        Block block = tmp.get(i);
        tmp.set(i, tmp.get(j));
        tmp.set(j, block);
    }
    
}
