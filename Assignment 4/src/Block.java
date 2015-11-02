/**
 * An individual block of memory.
 * Block.java
 * CS 600.226 Data Structures Fall 2015
 * Assignemnt 4
 * Eli Pivo - epivo1
 * Raphael Norman-Tenazas - rtenaza1
 * William Watson - wwatso13
 */
public class Block {
	
	/** The memory address. */
    private int memAddress;
    /** Size of the memory block. */
    private int size;
    /** Is the block filled. True if yes.*/
    private boolean filled;
    /** The allocation number for this block. */
    private int allocNum;
    
    /**
     * Constructor Method. 
     */
    public Block() {
    	//add shit here.
    }
    
    /**
     * Gets the memory address of this block.
     * @return the memAddress
     */
    public int getMemAddress() {
        return memAddress;
    }


    /**
     * Sets the memory address of this block.
     * @param memAddress the memAddress to set
     */
    public void setMemAddress(int memAddress) {
        this.memAddress = memAddress;
    }


    /**
     * Gets the size of the current block.
     * @return the size
     */
    public int getSize() {
        return size;
    }


    /**
     * Sets the size of the block.
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }


    /**
     * Returns whether or not the block is filled or free.
     * @return the filled
     */
    public boolean isFilled() {
        return filled;
    }


    /**
     * Sets whether or not this block is filled.
     * @param filled the filled to set
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }


    /**
     * Gets the Allocation Number.
     * @return the allocNum
     */
    public int getAllocNum() {
        return allocNum;
    }


    /**
     * Sets the Allocation number.
     * @param allocNum the allocNum to set
     */
    public void setAllocNum(int allocNum) {
        this.allocNum = allocNum;
    }


    
    
    /**
     * Compares to blocks by size.
     */
    private int compareTo(Block b){
        return this.size - b.getSize();
    }
}
