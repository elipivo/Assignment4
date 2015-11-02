
public class Block {
    private int memAddress;
    private int size;
    private boolean filled;
    private int allocNum;
    
    /**
     * @return the memAddress
     */
    public int getMemAddress() {
        return memAddress;
    }


    /**
     * @param memAddress the memAddress to set
     */
    public void setMemAddress(int memAddress) {
        this.memAddress = memAddress;
    }


    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }


    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }


    /**
     * @return the filled
     */
    public boolean isFilled() {
        return filled;
    }


    /**
     * @param filled the filled to set
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }


    /**
     * @return the allocNum
     */
    public int getAllocNum() {
        return allocNum;
    }


    /**
     * @param allocNum the allocNum to set
     */
    public void setAllocNum(int allocNum) {
        this.allocNum = allocNum;
    }


    
    
    
    private int compareTo(Block b){
        return this.size - b.getSize();
    }
}
