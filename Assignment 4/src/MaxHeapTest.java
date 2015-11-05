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
 *  Test for Initiation and Constructors.
 *  Test for Adding
 *  Test for Contains
 *  Test for Dequeue (remove max)
 *  Test isEmpty
 *  Test Max
 *  Test size
 *  Test toString();
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
    }
    
    
    @Test
    public void testDequeue() {
        
    }
    
    @Test
    public void testMaxAndSize() {
        
    }
    
    
    @Test
    public void testToString() {
        
    }
    
    
    
    
    
    
    
    
    
}
