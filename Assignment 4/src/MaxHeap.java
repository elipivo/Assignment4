
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
 * @param <T>
 *            Generic Type T.
 */
public class MaxHeap<T> {

	/** ArrayList makes the Heap. */
	private ArrayList<T> heap;
	/** Size of Heap. */
	private int size;

	/**
	 * Default Constructor.
	 */
	public MaxHeap() {
		this.heap = new ArrayList<T>();
	}

	/**
	 * Constructor for ArrayList input.
	 * 
	 * @param list
	 *            arrayList.
	 */
	public MaxHeap(ArrayList<T> list) {

	}

	/**
	 * Adds the val to the heap. The Percolates the value up if needed.
	 * 
	 * @param val
	 *            value to be added to heap.
	 */
	public void add(T val) {
		if (this.heap.size() == 0) {
			this.heap.add(val);
		}

		this.heap.add(val);
		percolateUp(heap.size() - 1);

	}

	/**
	 * Percolates the newly added value at index. and moves it into position
	 * within the heap.
	 * 
	 * @param index
	 *            index of the most recently added item.
	 */
	private void percolateUp(int index) {
		T tmp = this.heap.get(index);
		boolean move = true;
		int newIndex = index;
		while (index > 0 && move) {
			int parent = (newIndex - 1) / 2;
			T val = this.heap.get(parent);
			if (val.compareTo(tmp) < 0) {
				this.heap.set(newIndex, this.heap.get(parent));
				newIndex = parent;
			} else {
				move = false;
			}
		}
		this.heap.set(newIndex, tmp);
	}

	/**
	 * Removes the max element.
	 * 
	 * @reutrn the removed value.
	 */
	public T dequeue() {
		if (this.heap.isEmpty()) {
			return null;
		}
		T val = null;
		if (this.heap.size() == 1) {
			return this.heap.remove(0);
		}

		val = this.heap.get(0);
		this.heap.set(0, this.heap.remove(this.heap.size() - 1));
		percolateDown(0);
		return val;
	}

	/**
	 * Percolated Down after removing the max.
	 * 
	 * @param index
	 *            index of removed item (zero here).
	 */
	private void percolateDown(int index) {
		T tmp = this.heap.get(0);
		int newIndex = index;
		boolean move = true;
		while (index < this.heap.size() && move) {
			int left = 2 * newIndex + 1;
			int right = 2 * newIndex + 2;
			T leftVal = null;
			T rightVal = null;
			if (this.heap.size() > left) {
				leftVal = this.heap.get(left);
			}
			if (this.heap.size() > right) {
				rightVal = this.heap.get(right);
			}
			// if (this.heap.get(left).compareTo(this.heap.get(right)) > 0) {
			if (leftVal != null && leftVal.compareTo(tmp) > 0) {
				this.heap.set(newIndex, this.heap.get(left));
				newIndex = left;
			}

			// } else if (this.heap.get(right).compareTo(this.heap.get(left)) >
			// 0) {
			else if (rightVal != null && rightVal.compareTo(tmp) > 0) {
				this.heap.set(newIndex, this.heap.get(right));
				newIndex = right;
				// }
			} else {
				move = false;
			}
		}
		this.heap.set(newIndex, tmp);
	}

	/**
	 * Checks if T value is in the heap.
	 * 
	 * @param val
	 *            Value to check if in heap.
	 * @return true if in heap, false if not.
	 */
	public boolean contains(T val) {
		return this.heap.contains(val);
	}

	/**
	 * Method to see if Heap is empty or not.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return this.heap.isEmpty();
	}

	/**
	 * Gets the data in the max of the tree.
	 * 
	 * @return value of data in max(root).
	 */
	public T max() {
		if (this.heap.isEmpty()) {
			return null;
		}
		return heap.get(0);
	}

	/**
	 * Getter for heap size.
	 * 
	 * @return the size of the heap.
	 */
	public int size() {
		return this.heap.size();
	}

	/**
	 * ToString method for the heap.
	 * 
	 * @return a string representation of the heap.
	 */
	public String toString() {
		return this.heap.toString();
	}

}
