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
        this.memSize = size;
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
    
    /*----MAIN METHODS----*/
    
    @Override
    public int allocate(int size, int allocNumTmp) {
        final long startTime = System.currentTimeMillis();
        
        Metric stat = new Metric();
        stat.setAlloc(true);
        
        if (this.emptyMemory.size() == 0) {
            this.defrag();
            this.numFailedAllocs++;
            this.sizeFailedAllocs += size;
            this.numDefrag++;
            stat.setSuccess(false);
            stat.setDefrag(true);
            stat.setAddress(-1);
            stat.setId(allocNumTmp);
            stat.setSizeReq(size);
            this.metrics.add(stat);
            final long endTime = System.currentTimeMillis();
            this.allocTime += endTime - startTime;
            return -1;
        }
        
        if (size > this.memSize) {
            this.numFailedAllocs++;
            this.sizeFailedAllocs += size;
            stat.setSuccess(false);
            stat.setAddress(-1);
            stat.setDefrag(false);
            stat.setId(allocNumTmp);
            stat.setSizeReq(size);
            this.metrics.add(stat);
            final long endTime = System.currentTimeMillis();
            this.allocTime += endTime - startTime;
            return -1;
        }
        
        if (size > this.emptyMemory.max().getSize()) {
            this.defrag();
            this.numDefrag++;
            int num = this.retryAlloc(size, allocNumTmp);
            if (num == -1) {
                stat.setSuccess(false);
            } else {
                stat.setSuccess(true);
            }
            stat.setAddress(num);
            stat.setDefrag(true);
            stat.setId(allocNumTmp);
            stat.setSizeReq(size);
            this.metrics.add(stat);
            final long endTime = System.currentTimeMillis();
            this.allocTime += endTime - startTime;
            return num;
        }
        
        Block max = this.emptyMemory.max();
        Block alloc = new Block(allocNumTmp, max.getMemAddress(), size);
        this.emptyMemory.dequeue();
        max.setSize(max.getSize() - size);
        max.setMemAddress(max.getMemAddress() + size);
        if (max.getSize() > 0) {
            this.emptyMemory.add(max);
        }
        
        this.filledMemory.add(alloc);
        
        this.numAllocs++;
        
        stat.setSuccess(true);
        stat.setAddress(alloc.getMemAddress());
        stat.setDefrag(false);
        stat.setId(allocNumTmp);
        stat.setSizeReq(size);
        this.metrics.add(stat);
        
        final long endTime = System.currentTimeMillis();
        this.allocTime += endTime - startTime;
        
        return alloc.getMemAddress();
    }

    /**
     * Private Helper Method for allocation retry after dfrag.
     * @param size size of block.
     * @param allocNumTmp tmp alloc number
     * @return memaddress.
     */
    private int retryAlloc(int size, int allocNumTmp) {
        if (size > this.emptyMemory.max().getSize()) {
            this.numFailedAllocs++;
            this.sizeFailedAllocs += size;
            return -1;
        }
        
        Block max = this.emptyMemory.max();
        Block alloc = new Block(allocNumTmp, max.getMemAddress(), size);
        this.emptyMemory.dequeue();
        max.setSize(max.getSize() - size);
        max.setMemAddress(max.getMemAddress() + size);
        if (max.getSize() > 0) {
            this.emptyMemory.add(max);
        }
        
        this.filledMemory.add(alloc);
        
        this.numAllocs++;
        
        return alloc.getMemAddress();
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
        //this.numDefrag++;
        if (this.emptyMemory.size() != 0) {
            
        
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
        
            this.emptyMemory.clear();
            this.emptyMemory = new MaxHeap<Block>(sorted);
        }
    }

    
    /*----SORTING METHODS----*/
    
    @Override
    public ArrayList<Block> bucketSort() {
        final long startTime = System.currentTimeMillis();
        
        ArrayList<Block> sortedMemory = new ArrayList<Block>();
        
        Block[] memoryAddress = new Block[this.memSize];
        
        for (Block b : this.emptyMemory.toArrayList()) {
            memoryAddress[b.getMemAddress()] = b;
        }
        
        for (int i = 0; i < memoryAddress.length; i++) {
            if (memoryAddress[i] != null) {
                sortedMemory.add(memoryAddress[i]);
            }
        }
        
        final long endTime = System.currentTimeMillis();
        this.totalSizeBucketsort += this.emptyMemory.size();
        this.timeBucketsort += endTime - startTime;
        
        return sortedMemory;
    }

    @Override
    public ArrayList<Block> quickSort() {
        final long startTime = System.currentTimeMillis();
        
        //avoids refrence to heap and thus sorting actual heap.
        ArrayList<Block> tmp = new 
                ArrayList<Block>(this.emptyMemory.toArrayList());
        
        this.qsort(tmp, 0, tmp.size() - 1);

        
        final long endTime = System.currentTimeMillis();
        this.totalSizeBucketsort += this.emptyMemory.size();
        this.timeBucketsort += endTime - startTime;
        
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
     * @return avg time BS.
     */
    public double getBSTime() {
        if (this.totalSizeBucketsort == 0) {
            return -1;
        }
        return ((double) this.timeBucketsort) / this.totalSizeBucketsort;
    }
    
    /**
     * Average time/size quicksort.
     * @return avg time QS.
     */
    public double getQSTime() {
        if (this.totalSizeQuicksort == 0) {
            return -1;
        }
        return ((double) this.timeQucksort) / this.totalSizeQuicksort;
    }
    
    /**
     * Average time to process alloc.
     * @return Average time.
     */
    public double getAvgTime() {
        if (this.numAllocs == 0) {
            return -1;
        }
        return ((double) this.allocTime) / this.numAllocs;
    }
    
    /**
     * Size of failed allocation attempts.
     * @return sizeFailedAllocs.
     */
    public double getFailedSize() {
        if (this.numFailedAllocs == 0) {
            return -1;
        }
        return ((double) this.sizeFailedAllocs) / this.numFailedAllocs;
    }
    
    
    /**
     * Number of failed allocation attempts.
     * @return numFailedAllocs.
     */
    public int getFailedAllocs() {
        return this.numFailedAllocs;
    }
    
    /**
     * Returns the Number of Defragmentations.
     * @return defrag
     */
    public int getDefrag() {
        return this.numDefrag;
    }
    
    /**
     * Returns filled memory.
     * @return AL of filled mem.
     */
    public ArrayList<Block> getFilledMem() {
        return this.filledMemory;
    }
    
    /**
     * Returns empty mem.
     * @return AL of empty mem.
     */
    public ArrayList<Block> getEmptyMem() {
        return this.emptyMemory.toArrayList();
    }
    
    
    /**
     * Gets the metrics so far.
     * @return ArrayList of metrics to be printed.
     */
    public ArrayList<Metric> getMetrics() {
        return this.metrics;
    }
    
}
