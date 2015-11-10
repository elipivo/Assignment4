/**
 * WorstFit Tests.
 * WOrstFitTest.java
 * CS 600.226 Data Structures Fall 2015
 * Assignment 4
 * Eli Pivo - epivo1
 * Raphael Norman-Tenazas - rtenaza1
 * William Watson - wwatso13
 */


/** TESTS for 600.226 Fall 2015 Project 4 - WorstFitMemory implementation
 *  Test Explicitly for Initiation and Constructors.
 *  Test for Allocate
 *  Test for Deallocate
 *  Test for Defrag
 *  Test for QuickSort
 *  Test for BucketSort
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
import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This will test WorstFit by using first input file.
 * @author NextBillyonair
 *
 */
public class WorstFitTest {
    
    static WorstFitMemory mem;
    
    @Before
    public void init() {
        //make sure constructors don't mess up
        mem = new WorstFitMemory(200);
        mem = new WorstFitMemory(500);
        mem = new WorstFitMemory(200000);
        mem = new WorstFitMemory(200);
    }
    
    @Test
    public void testConstructors() {
        mem = new WorstFitMemory(200);
        //all should be zero.
        //excpet these which are / 0;
        boolean good = false;
        try {
            mem.getAvgTime();
            mem.getQSTime();
            mem.getBSTime();
        } catch (ArithmeticException e) {
            good = true;
        }
        //== 0
        assertTrue(good);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == 0);
                
        
    }
    
    @Test
    public void testAlloc() {
        //memory of size 200
        // Memory : |200|
        mem = new WorstFitMemory(200);
        int allocNum = 0;
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == 0);
        assertTrue(mem.getEmptyMem().size() == 1);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 200);
        assertTrue(mem.getFilledMem().size() == 0);
        
        // |20*|180|   * = filled
        mem.allocate(20, ++allocNum);
        assertTrue(allocNum == 1);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == 0);
        assertTrue(mem.getEmptyMem().size() == 1);
        assertTrue(mem.getFilledMem().size() == 1);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 180);
        
        //|20*|30*|150| 
        mem.allocate(30, ++allocNum);
        assertTrue(allocNum == 2);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == 0);
        assertTrue(mem.getEmptyMem().size() == 1);
        assertTrue(mem.getFilledMem().size() == 2);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 150);
        
        //|20*|30*|30*|120| 
        mem.allocate(30, ++allocNum);
        assertTrue(allocNum == 3);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == 0);
        assertTrue(mem.getEmptyMem().size() == 1);
        assertTrue(mem.getFilledMem().size() == 3);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 120);
        
        //|20*|30*|30*|50*|70| 
        mem.allocate(50, ++allocNum);
        assertTrue(allocNum == 4);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == 0);
        assertTrue(mem.getEmptyMem().size() == 1);
        assertTrue(mem.getFilledMem().size() == 4);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 70);
        
        
        //|20*|30*|30*|50*|20*|50|
        mem.allocate(20, ++allocNum);
        assertTrue(allocNum == 5);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == 0);
        assertTrue(mem.getEmptyMem().size() == 1);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 20);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 50);
        
        //|20*|30*|30*|50*|20*|20*|30|
        mem.allocate(20, ++allocNum);
        assertTrue(allocNum == 6);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == 0);
        assertTrue(mem.getEmptyMem().size() == 1);
        assertTrue(mem.getFilledMem().size() == 6);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 20);
        assertTrue(mem.getFilledMem().get(5).getSize() == 20);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 30);
        
        //|20*|30*|30*|50*|20*|20*|30*|  All filled
        mem.allocate(30, ++allocNum);
        assertTrue(allocNum == 7);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == 0);
        assertTrue(mem.getEmptyMem().size() == 0);
        assertTrue(mem.getFilledMem().size() == 7);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 20);
        assertTrue(mem.getFilledMem().get(5).getSize() == 20);
        assertTrue(mem.getFilledMem().get(6).getSize() == 30);
        
        //first failed alloc
        //|20*|30*|30*|50*|20*|20*|30*|
        mem.allocate(50, ++allocNum);
        assertTrue(allocNum == 8);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 1);
        assertTrue(mem.getFailedSize() == 50);
        assertTrue(mem.getEmptyMem().size() == 0);
        assertTrue(mem.getFilledMem().size() == 7);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 20);
        assertTrue(mem.getFilledMem().get(5).getSize() == 20);
        assertTrue(mem.getFilledMem().get(6).getSize() == 30);
        
        //dealloc
        //|20*|30*|30|50*|20*|20*|30*|
        assertTrue(mem.deallocate(3));
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 1);
        assertTrue(mem.getFailedSize() == 50);
        assertTrue(mem.getEmptyMem().size() == 1);
        assertTrue(mem.getFilledMem().size() == 6);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 50);
        assertTrue(mem.getFilledMem().get(3).getSize() == 20);
        assertTrue(mem.getFilledMem().get(4).getSize() == 20);
        assertTrue(mem.getFilledMem().get(5).getSize() == 30);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 30);
        
        //dealloc
        //|20*|30*|30|50*|20*|20|30*|
        assertTrue(mem.deallocate(6));
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 1);
        assertTrue(mem.getFailedSize() == 50);
        assertTrue(mem.getEmptyMem().size() == 2);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 50);
        assertTrue(mem.getFilledMem().get(3).getSize() == 20);
        assertTrue(mem.getFilledMem().get(4).getSize() == 30);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 30);
        assertTrue(mem.getEmptyMem().get(1).getSize() == 20);
        
        //|20*|30*|30|50*|20*|20|30*|
        //force defrag fail
        mem.allocate(50, ++allocNum);
        assertTrue(allocNum == 9);
        assertTrue(mem.getDefrag() == 1);
        assertTrue(mem.getFailedAllocs() == 2);
        assertTrue(mem.getEmptyMem().size() == 2);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 50);
        assertTrue(mem.getFilledMem().get(3).getSize() == 20);
        assertTrue(mem.getFilledMem().get(4).getSize() == 30);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 30);
        assertTrue(mem.getEmptyMem().get(1).getSize() == 20);
        
        //|20*|30|30|50*|20*|20|30*|
        //dealloc to have 30 and 30 open.
        assertTrue(mem.deallocate(2));
        assertTrue(mem.getDefrag() == 1);
        assertTrue(mem.getFailedAllocs() == 2);
        assertTrue(mem.getEmptyMem().size() == 3);
        assertTrue(mem.getFilledMem().size() == 4);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 50);
        assertTrue(mem.getFilledMem().get(2).getSize() == 20);
        assertTrue(mem.getFilledMem().get(3).getSize() == 30);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 30);
        assertTrue(mem.getEmptyMem().get(1).getSize() == 20);
        assertTrue(mem.getEmptyMem().get(2).getSize() == 30);
        
        //another alloc
        //|20*|50*|10|50*|20*|20|30*|
        //50 added to end of arraylist.
        mem.allocate(50, ++allocNum);
        assertTrue(allocNum == 10);
        assertTrue(mem.getDefrag() == 2);
        assertTrue(mem.getFailedAllocs() == 2);
        assertEquals(mem.getEmptyMem().size(), 2);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 50);
        assertTrue(mem.getFilledMem().get(2).getSize() == 20);
        assertTrue(mem.getFilledMem().get(3).getSize() == 30);
        assertTrue(mem.getFilledMem().get(4).getSize() == 50);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 20);
        assertTrue(mem.getEmptyMem().get(1).getSize() == 10);
        
        //dealloc again
        //|20*|50*|10|50|20*|20|30*|
        assertTrue(mem.deallocate(4));
        assertTrue(allocNum == 10);
        assertTrue(mem.getDefrag() == 2);
        assertTrue(mem.getFailedAllocs() == 2);
        assertEquals(mem.getEmptyMem().size(), 3);
        assertTrue(mem.getFilledMem().size() == 4);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 20);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 50);
        assertTrue(mem.getEmptyMem().get(1).getSize() == 10);
        assertTrue(mem.getEmptyMem().get(2).getSize() == 20);
        
        
        //|20*|50*|10|8*|42|20*|20|30*|
        //alloc 8 THIS IS WHERE WORST FIT COMES IN.
        mem.allocate(8, ++allocNum);
        assertTrue(allocNum == 11);
        assertTrue(mem.getDefrag() == 2);
        assertTrue(mem.getFailedAllocs() == 2);
        assertEquals(mem.getEmptyMem().size(), 3);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 20);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 8);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 42);
        assertTrue(mem.getEmptyMem().get(1).getSize() == 10);
        assertTrue(mem.getEmptyMem().get(2).getSize() == 20);
        
        //final memory for test described in memSim.pdf
        //| 20* | 50* | 10 | 8* | 42 | 20* | 20 | 30* |
        assertEquals(mem.getEmptyMem().size(), 3);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 20);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 8);
        assertTrue(mem.getEmptyMem().get(0).getSize() == 42);
        assertTrue(mem.getEmptyMem().get(1).getSize() == 10);
        assertTrue(mem.getEmptyMem().get(2).getSize() == 20);
        
    }
    
    
    @Test
    public void testOverflow() {
        mem = new WorstFitMemory(200);
        int blockSize = 1;
        
        //first test too big of size
        for (int i = 0; i < 10; i++) {
            mem.allocate(201, i + 1);
            assertEquals(mem.getFilledMem().size(), 0);
            assertEquals(mem.getEmptyMem().size(), 1);
        }
        
        //reset for future tests failed allocs
        mem = new WorstFitMemory(200);
        
        //fill mem up
        for (int i = 0; i < 200; i++) {
            mem.allocate(blockSize, i + 1);
        }
        assertEquals(mem.getFilledMem().size(), 200);
        assertEquals(mem.getEmptyMem().size(), 0);
        
        //try overflowwing allocs
        int fail = 0;
        for (int i = 200; i < 500; i++) {
            mem.allocate(blockSize, i + 1);
            fail++;
            assertEquals(mem.getFilledMem().size(), 200);
            assertEquals(mem.getEmptyMem().size(), 0);
            assertEquals(mem.getFailedAllocs(), fail);
        }
        
        //free all memory
        int size = 200;
        for (int i = 0; i < 200; i++) {
            assertTrue(mem.deallocate(i + 1));
            size--;
            assertEquals(mem.getFilledMem().size(), size);
            assertEquals(mem.getEmptyMem().size(), i + 1);
        }
        
        //200 blocks of size 1
        assertEquals(mem.getFilledMem().size(), 0);
        assertEquals(mem.getEmptyMem().size(), 200);
        
        //try dealloc on empty
        for (int i = 0; i < 200; i++) {
            assertFalse(mem.deallocate(i + 1));
            assertEquals(mem.getFilledMem().size(), 0);
            assertEquals(mem.getEmptyMem().size(), 200);
        }
        
        
        
        //force defrag with/in allocate
        //was : |1|1|1|...|1|1|1| 200 times
        assertEquals(mem.getEmptyMem().size(), 200);
        assertEquals(mem.getFilledMem().size(), 0);
        //|10*||190|
        mem.allocate(10, 1);
        assertEquals(mem.getFilledMem().size(), 1);
        assertEquals(mem.getEmptyMem().size(), 1);
        
        //do defrag again by free 10, adding 200
        //will condense 2 blocks into one the size of mem.
        //|10*|190|->|10|190|->|200*|
        assertTrue(mem.deallocate(1));
        assertEquals(mem.getFilledMem().size(), 0);
        assertEquals(mem.getEmptyMem().size(), 2);
        mem.allocate(200, 1);
        assertEquals(mem.getFilledMem().size(), 1);
        assertEquals(mem.getEmptyMem().size(), 0);
        
        //now dealloc
        // |200|
        assertTrue(mem.deallocate(1));
        assertTrue(mem.getFilledMem().size() == 0);
        assertEquals(mem.getEmptyMem().size(), 1);
    }
    
    @Test
    public void testSort() {
        mem = new WorstFitMemory(55);
        assertTrue(mem.getEmptyMem().size() == 1);
        assertTrue(mem.getFilledMem().size() == 0);
        
        //allocate like this:
        //|1*|2*|3*|4*|5*|6*|7*|8*|9*|10*|
        for (int i = 0; i < 10; i++) {
            mem.allocate(i + 1, i);
            assertTrue(mem.getFilledMem().size() == (i + 1));
        }
        
        //|1|2|3|4|5|6|7|8|9|10|
        for (int i = 0; i < 10; i++) {
            assertTrue(mem.deallocate(i));
            assertTrue(mem.getFilledMem().size() == 9 - i);
            assertTrue(mem.getEmptyMem().size() == i + 1);
        }
        
        //now everything is free, but due to heap, in order of size, not address.
        assertTrue(!mem.getEmptyMem().toString().equals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]"));
        
        //now sort
        ArrayList<Block> tmp = mem.quickSort();
        ArrayList<Block> tmp1 = mem.bucketSort();
        //equal sorting?
        assertEquals(tmp, tmp1);
        
        //check if in order
        for (int i = 0; i < 10; i++) {
           assertEquals(tmp.get(i).getMemAddress(), tmp1.get(i).getMemAddress());
        }
        
        //this is what mem address are.
        int[] memAddress = new int[10];
        for (int i = 0; i < 10; i++) {
            memAddress[i] = mem.getEmptyMem().get(i).getMemAddress();
        }
        
        //sort mem address.
        Arrays.sort(memAddress);
        
        //check both lists from sorts to see if sorted still.
        for (int i = 0; i < 10; i++) {
            assertEquals(tmp.get(i).getMemAddress(), memAddress[i]);
            assertEquals(tmp1.get(i).getMemAddress(), memAddress[i]);
        }
        
        //sorting works 

    }
    
    
    
}
