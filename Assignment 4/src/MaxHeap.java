/**
 * The data structure Priority Queue.
 * Implemented with a Max Heap.
 * MaxHeap.java
 * CS 600.226 Data Structures Fall 2015
 * Assignment 4
 * Eli Pivo - epivo1
 * Raphael Norman-Tenazas - rtenaza1
 * William Watson - wwatso13
 */

import java.util.ArrayList;
 
/** 
 * @param <T> Generic Type T.
 */
public class MaxHeap<T> {
    
	/** Head of Heap. */
	private TNode head;
	/** Size of Heap. */
	private int size;
    
    /**
     * Default Constructor.
     */
    public MaxHeap() {
        this.head = null;
        this.size = 0;
    }
    
    
    public boolean add(T val) {
    	
    }
    
    public boolean contains(T val) {
    	
    }
    
    public boolean remove(T val) {
    	
    }

    /**
     * Method to see if Heap is empty or not.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
    	return this.size == 0;
    }
    
    /**
     * Gets the data in the root of the tree.
     * @return value of data in root.
     */
    public T root() {
    	if (this.head == null) {
    		return null;
    	}
    	return head.data;
    }
    
    /**
     * Getter for heap size.
     * @return the size of the heap.
     */
    public int size() {
    	return this.size;
    }
    
    /**
     * ToString method for the heap.
     * @return a string representation of the heap.
     */
    public String toString() {
    	return this.inOrder().toString();
    }
    
    /**
     * Makes an array list of elements in order traversal.
     * @return Array list of all elements in heap.
     */
    private ArrayList<T> inOrder() {
    	ArrayList<T> tmp = new ArrayList<T>();
    	
    	if (this.head != null) {
    		this.traverse(head, tmp);
    	}
    	
    	return tmp;
    }
    
    /**
     * Traverses the tree.
     * @param head 	node to traverse.
     * @param tmp	list to add elements to.
     */
    private void traverse(TNode head, ArrayList<T> tmp) {
    	if (head.left != null) {
    		traverse(head.left, tmp);
    	}
    	
    	tmp.add(head.data);
    	
    	if (head.right != null) {
    		traverse(head.right, tmp);
    	}
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
