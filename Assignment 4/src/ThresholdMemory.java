import java.util.ArrayList;

/**
 * Interface Memory.java. CS 600.226 Data Structures Fall 2015 Assignment 4 Eli
 * Pivo - epivo1 Raphael Norman-Tenazas - rtenaza1 William Watson - wwatso13
 */
public class ThresholdMemory implements Memory {
    
    /**
     * Tree holding memory.
     */
    AVLtree<Block> memory;
    /**
     * Size of total memory.
     */
    int size;
    /**
     * The threshold.
     */
    int threshold;
    /**
     * Array holding metrics.
     */
    ArrayList<Metric> metrics;
    /**
     * Number of defragmentations.
     */
    int numDefrag;
    /**
     * Amount of failed allocations.
     */
    int numFailedAllocs;
    /**
     * Total size of the failed allocations. Avg is calculated by dividing this with the number of failed ones.
     */
    int sizeFailedAllocs;
    /**
     * Amount of time it took to allocated something.
     */
    long allocTime;
    /**
     * Amount of allocations.
     */
    int numAllocs;
    /**
     * Amount of time it took to quicksort (an increasing value).
     */
    long timeQuickSort;
    /**
     * Total size of all the quicksorts, used to calculated avg time.
     */
    long totalSizeQuickSort;
    /**
     * Amount of time it took to bucketsort (an increasing value).
     */
    long timeBucketSort;
    /**
     * Total size of all the bucketsorts, used to calculated avg time.
     */
    long totalSizeBucketsort;
    /**
     * Constructor.
     * @param initalSize
     * Initial size
     * @param initialThreshold
     * Initial threshold
     */
    public ThresholdMemory(int initalSize, int initialThreshold) {
        this.size = initalSize;
        this.memory = new AVLtree<Block>();
        Block initialBlock = new Block(0, 0, this.size);
        this.memory.add(initialBlock);
        this.metrics = new ArrayList<Metric>();
        this.size = 0;
        this.numDefrag = 0;
        this.numFailedAllocs = 0;
        this.sizeFailedAllocs = 0;
        this.allocTime = 0;
        this.numAllocs = 0;
        this.timeBucketSort = 0;
        this.timeQuickSort = 0;
        this.totalSizeBucketsort = 0;
        this.totalSizeQuickSort = 0;
        this.threshold = initialThreshold;
    }

    @Override
    public int allocate(int aSize, int allocNum) {
        long startTime = System.nanoTime();
        if (aSize > this.memory.root().getSize()) {
            this.allocTime += (System.nanoTime() - startTime);
            this.numFailedAllocs++;
            this.numAllocs++;
            return -1;
        }
        this.memory.add(new Block(allocNum, ))

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Block> quickSort() {
        // TODO Auto-generated method stub
        return null;
    }

}
