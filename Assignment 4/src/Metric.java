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
    private int sizeReq;
    
    /**
     * Default Constructor.
     */
    public Metric() {
        this.alloc = false;
        this.id = -1;
        this.defrag = false;
        this.success = false;
        this.address = -1;
        this.sizeReq = -1;
    }
    
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
    public int getSizeReq() {
        return this.sizeReq;
    }
    /**
     * Set size req.
     * @param sizeReqNum the sizeReg to set
     */
    public void setSizeReq(int sizeReqNum) {
        this.sizeReq = sizeReqNum;
    }
    
    /**
     * To String Method.
     * @return string of metric data.
     */
    public String toString() {
        String output = "";
        if (this.alloc) {
            String defragStr = "";
            String sucString = "SUCCESS";

            if (this.defrag) {
                defragStr = "DF";
            }
            if (this.address == -1) {
                sucString = "FAILED";
            }
            output += String.format("A%4d%4s  %-7s%6s%7s        ", 
                 this.id, defragStr, sucString, this.address, this.sizeReq);

            return output;
        }
        
        String sucString = "SUCCESS";
        int deallocSize = 0;
        int addressNum = 0;

        if (!this.success) {
            sucString = "FAILED";
            addressNum = -1;
        } else {
            addressNum = this.address;
            deallocSize = this.sizeReq;
        }
        output += String.format("D%4s%4s  %-7s%6s%7s        ",
            this.id, "", sucString, addressNum, deallocSize);
        return output;
    }
    
    
    
}
