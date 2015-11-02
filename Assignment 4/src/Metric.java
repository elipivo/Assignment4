/**
 * Data object for Metrics.
 * Metric.java
 * CS 600.226 Data Structures Fall 2015
 * Assignemnt 4
 * Eli Pivo - epivo1
 * Raphael Norman-Tenazas - rtenaza1
 * William Watson - wwatso13
 */
public class Metric {
    /** Was this an allocation. */
    private boolean alloc;
    /** id number. */
    private int id;
    /** Was this a defrag. */
    private boolean defrag;
    /** Was this successful. */
    private boolean success;
    /** Address working on. */
    private int address;
    /** Size requested by user for operation. */
    private int sizeReg;
    
    /**
     * Getter for Alloc.
     * @return true is alloc.
     */
    public boolean isAlloc() {
        return this.alloc;
    }
    /**
     * Sets allocation.
     * @param allocVal allocation true.
     */
    public void setAlloc(boolean allocVal) {
        this.alloc = allocVal;
    }
    /**
     * gets ID number.
     * @return the id
     */
    public int getId() {
        return this.id;
    }
    /**
     * Sets the id number.
     * @param idNum the id to set
     */
    public void setId(int idNum) {
        this.id = idNum;
    }
    /**
     * @return the defrag
     */
    public boolean isDefrag() {
        return this.defrag;
    }
    /**
     * Sets the defrag value.
     * @param defragVal the defrag to set
     */
    public void setDefrag(boolean defragVal) {
        this.defrag = defragVal;
    }
    /**
     * Returns if this was success.
     * @return the success true if good.
     */
    public boolean isSuccess() {
        return this.success;
    }
    /**
     * Set success val.
     * @param successVal the success to set
     */
    public void setSuccess(boolean successVal) {
        this.success = successVal;
    }
    /**
     * Gets the address.
     * @return the address
     */
    public int getAddress() {
        return this.address;
    }
    /**
     * Sets address number.
     * @param addressNum the address to set
     */
    public void setAddress(int addressNum) {
        this.address = addressNum;
    }
    /**
     * Size request.
     * @return the sizeReg
     */
    public int getSizeReg() {
        return this.sizeReg;
    }
    /**
     * Set size req.
     * @param sizeRegNum the sizeReg to set
     */
    public void setSizeReg(int sizeRegNum) {
        this.sizeReg = sizeRegNum;
    }
    
    
    
    
}
