/**
 * BestFitMemory.
 * BesrtFitMemory.java
 * CS 600.226 Data Structures Fall 2015
 * Assignment 4
 * Eli Pivo - epivo1
 * Raphael Norman-Tenazas - rtenaza1
 * William Watson - wwatso13
 */

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
    public int allocate(int aSize, int allocNum) {
        final long startTime = System.nanoTime();

        Metric metric = new Metric();
        metric.setAlloc(true);
        metric.setId(allocNum);
        metric.setSizeReq(aSize);
        this.numAllocs++;

        //ensure the allocation attempt is smaller than our memory size
        if (aSize > this.memSize || aSize == 0) {
            
            // update metrics
            metric.setSuccess(false);
            metric.setDefrag(false);
            metric.setAddress(-1);
            this.numFailedAllocs++;
            this.sizeFailedAllocs += aSize;
            
            this.metrics.add(metric);
            final long endTime = System.nanoTime();
            this.allocTime += endTime - startTime;
            return metric.getAddress();
            
        }
        
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
        Block filledPart = new Block(allocNum, bestFit.getMemAddress(), aSize);
        filledPart.setFilled(true);
        filledPart.setFilled(true);
        
        Block emptyPart = new Block(-1, bestFit.getMemAddress()
                + aSize, bestFit.getSize() - aSize);
        emptyPart.setFilled(false);
        
        this.emptyMemory.remove(bestFit);
        if (emptyPart.getSize() != 0) {
            this.emptyMemory.add(emptyPart);
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
        //frees it
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
        if (this.emptyMemory.size() == 0) {
            return;
        }
        //timing sorts, only use one later.
        ArrayList<Block> sorted = this.quickSort();
        ArrayList<Block> bucketSort = this.bucketSort();
        bucketSort.clear();
        
        //fix address calc.
        for (int i = 0; i < sorted.size();) {
            Block b = sorted.get(i);
            int diff = -1;
            if (i + 1 < sorted.size()) {
                diff = b.getMemAddress() + b.getSize() 
                    - sorted.get(i + 1).getMemAddress();
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

    @Override
    public ArrayList<Block> bucketSort() {
        
        final long startTime = System.nanoTime();
        
        ArrayList<Block> sortedMemory = new ArrayList<Block>();
        
        Block[] memoryAddress = new Block[this.memSize];
        
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
        this.timeBucketsort += endTime - startTime;
        
        return sortedMemory;
    }

    @Override
    public ArrayList<Block> quickSort() {
        final long startTime = System.nanoTime();
        
        //avoids refrence to tree and thus sorting actual tree.
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
     * @param tmp array list
     * @param i first index
     * @param j second index
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
    public float getBSTime() {
        if (this.totalSizeBucketsort == 0) {
            return -1;
        }
        final int convert = 1000;
        return  ((float) this.timeBucketsort 
                / this.totalSizeBucketsort) / convert;
    }

    /**
     * Average time/size quicksort.
     * 
     * @return avg time QS.
     */
    public float getQSTime() {
        if (this.totalSizeQuickSort == 0) {
            return -1;
        }
        final int convert = 1000;
        return ((float) this.timeQuickSort / this.totalSizeQuickSort) / convert;
    }

    /**
     * Average time to process alloc.
     * 
     * @return Average time.
     */
    public float getAvgTime() {
        if (this.numAllocs == 0) {
            return -1;
        }
        
        final int convert = 1000;
        return (((float) this.allocTime) / this.numAllocs) / convert;
    }

    /**
     * Size of failed allocation attempts.
     * 
     * @return sizeFailedAllocs.
     */
    public long getFailedSize() {
        if (this.numFailedAllocs == 0) {
            return -1;
        }
        return ((long) this.sizeFailedAllocs) / this.numFailedAllocs;
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
     * Returns empty memory.
     * 
     * @return AVL of empty mem.
     */
    public AVLtree<Block> getEmptyMem() {
        return this.emptyMemory;
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