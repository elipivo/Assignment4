/**
 * An individual block of memory. Block.java CS 600.226 Data Structures Fall
 * 2015 Assignemnt 4 Eli Pivo - epivo1 Raphael Norman-Tenazas - rtenaza1 William
 * Watson - wwatso13
 */
public class Block implements Comparable<Block> {

    /** The memory address. */
    private int memAddress;
    /** Size of the memory block. */
    private int size;
    /** Is the block filled. True if yes. */
    private boolean filled;
    /** The allocation number for this block. */
    private int allocNum;

    /**
     * Default Constructor Method.
     */
    public Block() {
        this.memAddress = -1;
        this.size = -1;
        this.filled = false;
        this.allocNum = -1;
    }

    /**
     * Constructor for Memaddress, Size, allocNum.
     * 
     * @param allocNumTmp
     *            allocation number.
     * @param address
     *            address of memory.
     * @param sizeTmp
     *            size of block wanted.
     */
    public Block(int allocNumTmp, int address, int sizeTmp) {
        this.memAddress = address;
        this.size = sizeTmp;
        this.filled = false;
        this.allocNum = allocNumTmp;
    }

    /**
     * Gets the memory address of this block.
     * 
     * @return the memAddress
     */
    public int getMemAddress() {
        return this.memAddress;
    }

    /**
     * Sets the memory address of this block.
     * 
     * @param memAddressNum
     *            the memAddress to set
     */
    public void setMemAddress(int memAddressNum) {
        this.memAddress = memAddressNum;
    }

    /**
     * Gets the size of the current block.
     * 
     * @return the size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Sets the size of the block.
     * 
     * @param sizeNum
     *            the size to set
     */
    public void setSize(int sizeNum) {
        this.size = sizeNum;
    }

    /**
     * Returns whether or not the block is filled or free.
     * 
     * @return the filled
     */
    public boolean isFilled() {
        return this.filled;
    }

    /**
     * Sets whether or not this block is filled.
     * 
     * @param isFilled
     *            the filled to set
     */
    public void setFilled(boolean isFilled) {
        this.filled = isFilled;
    }

    /**
     * Gets the Allocation Number.
     * 
     * @return the allocNum
     */
    public int getAllocNum() {
        return this.allocNum;
    }

    /**
     * Sets the Allocation number.
     * 
     * @param allocNumber
     *            the allocNum to set
     */
    public void setAllocNum(int allocNumber) {
        this.allocNum = allocNumber;
    }

    /**
     * Compares to blocks by size.
     * 
     * @param b
     *            Block b to be compared with this.
     * @return difference in size.
     */
    public int compareTo(Block b) {
        return this.size - b.getSize();
    }

    /**
     * To String method for Block.java.
     * 
     * @return string representation of a block.
     */
    public String toString() {
        return "Block: " + this.memAddress + " Size: " 
                + this.size + " Filled: " + this.filled + " Allocation#: "
                + this.allocNum;
    }
}
