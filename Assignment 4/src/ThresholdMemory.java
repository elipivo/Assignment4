import java.util.ArrayList;

/**
 * Interface Memory.java. CS 600.226 Data Structures Fall 2015 Assignment 4 Eli
 * Pivo - epivo1 Raphael Norman-Tenazas - rtenaza1 William Watson - wwatso13
 */
public class ThresholdMemory implements Memory {

    /**
     * Tree holding memory.
     */
    AVLtree<Block> emptyMemory;
    /**
     * Array holding filled memory.
     */
    ArrayList<Block> filledMemory;
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
     * Total size of the failed allocations. Avg is calculated by dividing this
     * with the number of failed ones.
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
     * 
     * @param initalSize
     *            Initial size
     * @param initialThreshold
     *            Initial threshold
     */
    public ThresholdMemory(int initalSize, int initialThreshold) {
        this.size = initalSize;
        this.emptyMemory = new AVLtree<Block>();
        this.filledMemory = new ArrayList<Block>();
        Block initialBlock = new Block(0, 0, this.size);
        this.emptyMemory.add(initialBlock);
        this.metrics = new ArrayList<Metric>();
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
        final long startTime = System.nanoTime();

        Metric metric = new Metric();
        metric.setAlloc(true);
        metric.setId(allocNum);
        metric.setSizeReq(aSize);
        this.numAllocs++;

        // get as small a block as possible
        Block bestFit = this.emptyMemory.ceiling(new Block(-1, -1, aSize));

        if (bestFit != null) {
            // cut block into filled part and empty part
            // update metrics
            int add = this.alloc(bestFit, aSize, allocNum);
            metric.setSuccess(true);
            metric.setDefrag(false);
            metric.setAddress(add);

        } else {
            // didn't work, defrag and try again
            
            this.defrag();

            // try again
            bestFit = this.emptyMemory.ceiling(new Block(-1, -1, aSize));

            if (bestFit != null) {
                int add = this.alloc(bestFit, aSize, allocNum);

                // update metrics
                metric.setSuccess(true);
                metric.setDefrag(true);
                metric.setAddress(add);

            } else {
                // alloc failed

                // update metrics
                metric.setSuccess(false);
                metric.setDefrag(true);
                metric.setAddress(-1);
                this.numFailedAllocs++;
                this.sizeFailedAllocs += aSize;
            }

        }

        this.metrics.add(metric);
        final long endTime = System.nanoTime();
        this.allocTime += endTime - startTime;
        return metric.getAddress();
    }
    /**
     * Helper method so that allocate didn't do things twice.
     * @param bestFit
     * Best fit block
     * @param aSize
     * Allocation size
     * @param allocNum
     * Allocation number
     * @return
     * Memory address
     */
    private int alloc(Block bestFit, int aSize, int allocNum) {
        int thresh = bestFit.getSize() - aSize;
        
        while (thresh < this.threshold && thresh != 0) {
            bestFit = this.emptyMemory.ceiling(bestFit);
            if (bestFit == null || bestFit.getSize() == thresh + aSize) {
                // If it can't find something bigger than the block
                // that causes a less than threshold split
                // Just use the original block
                
                bestFit = this.emptyMemory.ceiling(new Block(-1, -1, aSize));
                break;
            }
            thresh = bestFit.getSize() - aSize;
        }
        Block filledPart = new Block(allocNum, bestFit.getMemAddress(), aSize);
        filledPart.setFilled(true);
        bestFit.setSize(bestFit.getSize() - aSize);
        bestFit.setMemAddress(bestFit.getMemAddress() + aSize);
        filledPart.setFilled(false);

        // add them to corresponding data structures
        
        if (bestFit.getSize() == 0) {
            this.emptyMemory.remove(bestFit);
        }
        this.filledMemory.add(filledPart);
        return filledPart.getMemAddress();

    }

    @Override
    public boolean deallocate(int allocNum) {

        Metric stat = new Metric();
        stat.setAlloc(false);

        Block dealloc = null;
        for (Block b : this.filledMemory) {
            if (b.getAllocNum() == allocNum) {
                dealloc = b;
                this.filledMemory.remove(b);
                break;
            }
        }
        if (dealloc == null) {
            stat.setAddress(-1);
            stat.setDefrag(false);
            stat.setId(allocNum);
            stat.setSizeReq(-1);
            stat.setSuccess(false);
            this.metrics.add(stat);
            return false;
        }
        // frees it
        dealloc.setFilled(false);
        this.emptyMemory.add(dealloc);
        stat.setAddress(dealloc.getMemAddress());
        stat.setDefrag(false);
        stat.setId(dealloc.getAllocNum());
        stat.setSizeReq(dealloc.getSize());
        stat.setSuccess(true);
        this.metrics.add(stat);
        return true;

    }

    @Override
    public void defrag() {
        this.numDefrag++;
        if (this.emptyMemory.size() != 0) {

            // timing sorts, only use one later.
            ArrayList<Block> sorted = this.quickSort();
            ArrayList<Block> bucketSort = this.bucketSort();
            bucketSort.clear();

            // fix address calc.
            for (int i = 0; i < sorted.size();) {
                Block b = sorted.get(i);
                int diff = -1;
                if (i + 1 < sorted.size()) {
                    diff = b.getMemAddress() + b.getSize() - sorted.get(i + 1).getMemAddress();
                }
                if (diff == 0) {
                    Block tmp = sorted.remove(i + 1);
                    Block good = sorted.get(i);
                    int newSize = tmp.getSize() + good.getSize();
                    int address = good.getMemAddress();
                    Block putIn = new Block(-1, address, newSize);
                    sorted.set(i, putIn);
                    i--;
                }
                i++;
            }

            this.emptyMemory = new AVLtree<Block>(sorted);
        }
    }

    @Override
    public ArrayList<Block> bucketSort() {

        final long startTime = System.nanoTime();

        ArrayList<Block> sortedMemory = new ArrayList<Block>();

        Block[] memoryAddress = new Block[this.size];

        for (Block b : this.emptyMemory.inOrder()) {
            memoryAddress[b.getMemAddress()] = b;
        }

        for (int i = 0; i < memoryAddress.length; i++) {
            if (memoryAddress[i] != null) {
                sortedMemory.add(memoryAddress[i]);
            }
        }

        final long endTime = System.nanoTime();
        this.totalSizeBucketsort += this.emptyMemory.size();
        this.timeBucketSort += endTime - startTime;

        return sortedMemory;
    }

    @Override
    public ArrayList<Block> quickSort() {
        final long startTime = System.nanoTime();

        // avoids refrence to tree and thus sorting actual tree.
        ArrayList<Block> tmp = new ArrayList<Block>();

        for (Block b : this.emptyMemory.inOrder()) {
            tmp.add(b);
        }

        this.qsort(tmp, 0, tmp.size() - 1);

        final long endTime = System.nanoTime();
        this.totalSizeQuickSort += this.emptyMemory.size();
        this.timeQuickSort += endTime - startTime;

        return tmp;
    }

    /**
     * Private helper method for quicksort.
     * 
     * @param tmp
     *            ArrayList to sort
     * @param lo
     *            lower bound
     * @param hi
     *            upper bound
     */
    private void qsort(ArrayList<Block> tmp, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int j = this.partition(tmp, lo, hi); // partition it
        this.qsort(tmp, lo, j - 1); // sort left part
        this.qsort(tmp, j + 1, hi); // sort right part
    }

    /**
     * Partitions the array to sort.
     * 
     * @param tmp
     *            arraylist to sort
     * @param lo
     *            lower bound
     * @param hi
     *            upper bound
     * @return j
     */
    private int partition(ArrayList<Block> tmp, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Block v = tmp.get(lo);
        while (true) {
            while ((tmp.get(++i).getMemAddress() - v.getMemAddress()) < 0) {
                if (i == hi) {
                    break;
                }
            }
            while ((v.getMemAddress() - tmp.get(--j).getMemAddress()) < 0) {
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
     * 
     * @param tmp
     *            array list
     * @param i
     *            first index
     * @param j
     *            second index
     */
    private void swap(ArrayList<Block> tmp, int i, int j) {
        Block block = tmp.get(i);
        tmp.set(i, tmp.get(j));
        tmp.set(j, block);
    }

    /*----ANALYSIS METHODS----*/

    /**
     * Average time/size bucketsort.
     * 
     * @return avg time BS.
     */
    public double getBSTime() {
        if (this.totalSizeBucketsort == 0) {
            return -1;
        }
        return  ((double) this.timeBucketSort / this.totalSizeBucketsort)/ 1000;
    }

    /**
     * Average time/size quicksort.
     * 
     * @return avg time QS.
     */
    public double getQSTime() {
        if (this.totalSizeQuickSort == 0) {
            return -1;
        }
        return ((double) this.timeQuickSort / this.totalSizeQuickSort) / 1000;
    }

    /**
     * Average time to process alloc.
     * 
     * @return Average time.
     */
    public double getAvgTime() {
        if (this.numAllocs == 0) {
            return -1;
        }
        return (((double) this.allocTime) / this.numAllocs) / 1000;
    }

    /**
     * Size of failed allocation attempts.
     * 
     * @return sizeFailedAllocs.
     */
    public double getFailedSize() {
        return (double) this.sizeFailedAllocs / this.numFailedAllocs;
    }

    /**
     * Number of failed allocation attempts.
     * 
     * @return numFailedAllocs.
     */
    public int getFailedAllocs() {
        return this.numFailedAllocs;
    }

    /**
     * Returns the Number of Defragmentations.
     * 
     * @return defrag
     */
    public int getDefrag() {
        return this.numDefrag;
    }

    /**
     * Returns filled memory.
     * 
     * @return AL of filled mem.
     */
    public ArrayList<Block> getFilledMem() {
        return this.filledMemory;
    }

    /**
     * Gets the metrics so far.
     * 
     * @return ArrayList of metrics to be printed.
     */
    public ArrayList<Metric> getMetrics() {
        return this.metrics;
    }

}
