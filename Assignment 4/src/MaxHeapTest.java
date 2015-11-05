/**
 * MaxHeap Tests.
 * MaxHeapTest.java
 * CS 600.226 Data Structures Fall 2015
 * Assignment 4
 * Eli Pivo - epivo1
 * Raphael Norman-Tenazas - rtenaza1
 * William Watson - wwatso13
 */


/** TESTS for 600.226 Fall 2015 Project 4 - MaxHeap implementation
 *  Test Explicitly for Initiation and Constructors.
 *  Test Explicitly for Adding
 *  Test Explicitly for Contains
 *  Test Explicitly for Dequeue (remove max)
 *  Test Explicitly isEmpty
 *  Test Implicitly Max
 *  Test Implicitly size
 *  Test Implicitly toString()
 *  
 *  NEED: heapify
 */


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class MaxHeapTest {
    
    static MaxHeap<Integer> heap; //empty heap
    
    @Before
    public void init() {
        heap = new MaxHeap<Integer>();
    }
    
    @Test
    public void testConstructors() {
        heap = new MaxHeap<Integer>();
        //MaxHeap<Integer> heapList = null;
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }
        
        assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", numbers.toString());
        
        MaxHeap<Integer> heapList = new MaxHeap<Integer>(numbers);
        //add normally to heap.
        for (int i = 0; i < numbers.size(); i++) {
            heap.add(numbers.get(i));
        }
        
        for (int i = 0; i < numbers.size(); i++) {
            assertTrue(heap.contains(numbers.get(i)));
            assertTrue(heapList.contains(i));
        }
        
        //make sure normal add and list add are same.
        assertEquals(heap.toString(), heapList.toString());
        
        heap.clear();
        heapList.clear();
    }
    
    @Test
    public void testEmpty() {
        //test empty edge cases as well.
        assertTrue(heap.isEmpty());
        assertTrue(heap.size() == 0);
        for (int i = 10; i > 0; i--) {
            assertFalse(heap.contains(i));
        }
        assertNull(heap.dequeue());
        assertNull(heap.max());
        assertEquals("[]", heap.toString());
        assertTrue(heap.isEmpty());
        assertTrue(heap.size() == 0);
    }
    
    @Test
    public void testAdd() {
        //test add and percolate up
        int size = 0;
        
        //first see if normal add works
        for (int i = 10; i > 0; i--) {
            heap.add(i);
            assertEquals(heap.size(), ++size);
            assertTrue(heap.contains(i));
            assertEquals(heap.max(), (Integer) 10);
            assertFalse(heap.isEmpty());
        }
        //Heap : 10 9 8 7 6 5 4 3 2 1
        assertTrue(heap.size() == 10);
        assertFalse(heap.isEmpty());
        assertEquals("[10, 9, 8, 7, 6, 5, 4, 3, 2, 1]", heap.toString());
        
        heap.clear();
        
        assertEquals("[]", heap.toString());
        assertTrue(heap.isEmpty());
        assertTrue(heap.size() == 0);
        size = 0;
        //now add low first, see if percolates, same range of numbers.
        for (int i = 1; i < 11; i++) {
            heap.add(i);
            assertEquals(heap.size(), ++size);
            assertTrue(heap.contains(i));
            assertEquals(heap.max(), (Integer) i);
            assertFalse(heap.isEmpty());
        }
        
        //Heap: 10 9 6 7 8 2 5 1 4 3
        assertEquals(heap.toString(), "[10, 9, 6, 7, 8, 2, 5, 1, 4, 3]");
        
        heap.clear();
        
        assertEquals("[]", heap.toString());
        assertTrue(heap.isEmpty());
        assertTrue(heap.size() == 0);
    
    }
    
    
    @Test
    public void testContains() {
        //noting in it.
        for (int i = 0; i < 100; i++) {
            assertFalse(heap.contains(i));
            assertTrue(heap.isEmpty());
            assertTrue(heap.size() == 0);
        }
        
        int size = 0;
        //add 1 to 100, lots of percolating, see if anything lost.
        for (int i = 0; i < 100; i++) {
            heap.add(i);
            assertEquals(heap.size(), ++size);
            assertTrue(heap.contains(i));
            assertEquals(heap.max(), (Integer) i);
            assertFalse(heap.isEmpty());
        }
        
        //now remove one.
        for (int i = 99; i > -1; i--) {
            assertEquals(heap.dequeue(), (Integer) i);
            assertEquals(heap.size(), --size);
            assertFalse(heap.contains(i));
            //test these while heap not empty yet, will be empty at i = 1
            if (i != 0) {
                assertEquals(heap.max(), (Integer) (i - 1));
                assertFalse(heap.isEmpty());
            } else {
                //i = 0, heap empty so...
                assertNull(heap.max());
                assertTrue(heap.isEmpty());
            }
            
        }                
    }
    
    
    @Test
    public void testDequeue() {
        //check dequeue if added in order.
        int size = 0;
        for (int i = 500; i > 0; i--) {
            heap.add(i);
            assertEquals(heap.size(), ++size);
            assertTrue(heap.contains(i));
            assertEquals(heap.max(), (Integer) 500);
            assertFalse(heap.isEmpty());
        }
        for (int i = 500; i > 0; i--) {
            assertEquals(heap.dequeue(), (Integer) i);
            assertEquals(heap.size(), --size);
            assertFalse(heap.contains(i));
            //test these while heap not empty yet, will be emtpy at i = 0
            if (i != 1) {
                assertEquals(heap.max(), (Integer) (i - 1));
                assertFalse(heap.isEmpty());
            } else {
                //i = 0, heap empty so...
                assertNull(heap.max());
                assertTrue(heap.isEmpty());
            }
        }
        
        //make sure not there.
        for (int i = 500; i > 0; i--) {
            assertFalse(heap.contains(i));
            assertTrue(heap.isEmpty());
            assertTrue(heap.size() == 0);
        }
        
        //remove after percolating adding.
        for (int i = 0; i < 500; i++) {
            heap.add(i);
            assertEquals(heap.size(), ++size);
            assertTrue(heap.contains(i));
            assertEquals(heap.max(), (Integer) i);
            assertFalse(heap.isEmpty());
        }
        
        //500 items in heap, 499 is max (b/c of for loop bounds)
        assertTrue(heap.size() == 500);
        assertTrue(heap.max() == 499);
        
        int max = heap.max();
        for (int i = 499; i > -1; i--) {
            assertEquals(heap.dequeue(), (Integer) i);
            assertEquals(heap.size(), --size);
            assertFalse(heap.contains(i));
            //test these while heap not empty yet, will be emtpy at i = 0
            if (i != 0) {
                assertEquals(heap.max(), (Integer) (i - 1));
                assertFalse(heap.isEmpty());
            } else {
                //i = 0, heap empty so...
                assertNull(heap.max());
                assertTrue(heap.isEmpty());
            }
        }
        
        heap.clear();
        
        //ArrayList<Integer> tmp = new ArrayList<Integer>();
        //test duplicate add and remove.
        int dup = 150;
        int sizeTest = 0;
        for (int i = 0; i < 200; i++) {
            heap.add(dup);
            sizeTest++;
            assertTrue(heap.size() == sizeTest);
            assertTrue(heap.contains(dup));
            assertTrue(heap.max().equals(150));
        }
        
        assertTrue(heap.size() == 200);
        assertTrue(heap.max().equals(150));
        //not empty string
        assertTrue(!heap.toString().equals("[]"));
        
        //manually make string to test toString
        String test = "[";
        for (int i = 0; i < 200; i++) {
            if (i == 199) {
                test += "150]";
            } else {
                test += "150, ";
            }
        }
        
        assertEquals(test, heap.toString());
        
        //remove each dup
        for (int i = 0; i < 200; i++) {
            assertTrue(heap.max().equals(150));
            assertTrue(heap.dequeue().equals(150));
            assertTrue(heap.size() == --sizeTest);
        }
        
        assertTrue(heap.isEmpty());
        assertNull(heap.max());
        assertFalse(heap.contains(150));
        assertTrue(heap.size() == 0);
        assertEquals("[]", heap.toString());
        
        heap.clear();
        
    }
    
    @Test
    public void testMaxAndSize() {
        //add random values in.
        //check size and max
        //this will check both duplicates and random adding.
        Random gen = new Random();
        
        assertNull(heap.max());
        int size = 0;
        int max = 0;
        for (int i = 0; i < 5000; i++) {
            int k = gen.nextInt(50);
            heap.add(k);
            size++;
            //update max number so far
            if (k > max) {
                max = k;
            }
            assertTrue(heap.size() == size);
            assertTrue(heap.max().equals(max));
            assertTrue(heap.contains(k));
            
        }
        
        //clear it.
        for (int i = 0; i < 5000; i++) {
            heap.dequeue();
        }
        
        for (int i = 0; i < 50; i++) {
            assertFalse(heap.contains(i));
        }
        
        assertNull(heap.dequeue());
        assertNull(heap.max());
        assertEquals("[]", heap.toString());
        assertTrue(heap.isEmpty());
        assertTrue(heap.size() == 0);
        
        //just make sure
        heap.clear();
        
    }
    
}
