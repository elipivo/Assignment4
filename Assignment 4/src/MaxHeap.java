/**
 * The data structure Priority Queue.
 * Implemented with a Max Heap.
 * MaxHeap.java
 * CS 600.226 Data Structures Fall 2015
 * Assignment 4
 * Eli Pivo - epivo1
 * Raphael Norman-Tenazas - rtenaza1
 * William Watson - wwatso13
 * 
 * @param <T> Generic Type T.
 */
public class MaxHeap<T> {
    
    
    /**
     * Default Constructor.
     */
    public MaxHeap() {
        
    }
    
    
    
    
    
    
    
    /**
     * Inner Helper Node Class for Organizing Nodes.
     * @param <T> Generic Type T.
     */
    public class TNode<T> {
        /** Data. */
        protected T data;
        /** Left Child. */
        protected TNode<T> left;
        /** Right Child. */
        protected TNode<T> right;
        /** Parent. */
        protected TNode<T> parent;
        /** Height. */
        private int height;
        
        /**
         * Constructor for TNode.
         * @param val value for data.
         */
        public TNode(T val) {
            this.data = val;
            this.setHeight(0);
        }
        
        /**
         * Returns whether node is a leaf or not.
         * @return true is node is leaf, false if not
         */
        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        /**
         * Gets the height of node.
         * @return the height
         */
        public int getHeight() {
            return this.height;
        }

        /**
         * Reset height of node.
         * @param heightNum the height to set
         */
        public void setHeight(int heightNum) {
            this.height = heightNum;
        }
        
    } //end inner class
}
