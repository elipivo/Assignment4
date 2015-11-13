import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ThresholdMemoryTest {
    static ThresholdMemory mem;

    

    @Before
    public void init() {
        // make sure constructors don't mess up
        mem = new ThresholdMemory(200);
        mem = new ThresholdMemory(500);
        mem = new ThresholdMemory(200000);
        mem = new ThresholdMemory(200);
    }

    @Test
    public void testConstructors() {
        
        // all should be zero.
        // excpet these which are / 0;

        // -1 means / by 0 to avoid exception.
        assertEquals((int) mem.getAvgTime(), (int) -1);
        assertEquals((int) mem.getQSTime(), (int) -1);
        assertEquals((int) mem.getBSTime(), -1);

        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);

    }

    @Test
    public void testAlloc() {
        // memory of size 200
        // Memory : |200|
        
        int allocNum = 0;
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.emptyMemory.size() == 1);
        assertTrue(mem.emptyMemory.root().getSize() == 200);
        assertTrue(mem.getFilledMem().size() == 0);

        // |20*|180| * = filled
        mem.allocate(20, ++allocNum);
        assertTrue(allocNum == 1);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.emptyMemory.size() == 1);
        assertTrue(mem.getFilledMem().size() == 1);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.emptyMemory.root().getSize() == 180);

        // |20*|30*|150|
        mem.allocate(30, ++allocNum);
        assertTrue(allocNum == 2);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.emptyMemory.size() == 1);
        assertTrue(mem.getFilledMem().size() == 2);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.emptyMemory.root().getSize() == 150);

        // |20*|30*|30*|120|
        mem.allocate(30, ++allocNum);
        assertTrue(allocNum == 3);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.emptyMemory.size() == 1);
        assertTrue(mem.getFilledMem().size() == 3);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.emptyMemory.root().getSize() == 120);

        // |20*|30*|30*|50*|70|
        mem.allocate(50, ++allocNum);
        assertTrue(allocNum == 4);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.emptyMemory.size() == 1);
        assertTrue(mem.getFilledMem().size() == 4);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.emptyMemory.root().getSize() == 70);

        // |20*|30*|30*|50*|20*|50|
        mem.allocate(20, ++allocNum);
        assertTrue(allocNum == 5);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.emptyMemory.size() == 1);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 20);
        assertTrue(mem.emptyMemory.root().getSize() == 50);

        // |20*|30*|30*|50*|20*|20*|30|
        mem.allocate(20, ++allocNum);
        assertTrue(allocNum == 6);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.emptyMemory.size() == 1);
        assertTrue(mem.getFilledMem().size() == 6);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 20);
        assertTrue(mem.getFilledMem().get(5).getSize() == 20);
        assertTrue(mem.emptyMemory.root().getSize() == 30);

        // |20*|30*|30*|50*|20*|20*|30*| All filled
        mem.allocate(30, ++allocNum);
        assertTrue(allocNum == 7);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.emptyMemory.size() == 0);
        assertTrue(mem.getFilledMem().size() == 7);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 20);
        assertTrue(mem.getFilledMem().get(5).getSize() == 20);
        assertTrue(mem.getFilledMem().get(6).getSize() == 30);

        // first failed alloc
        // |20*|30*|30*|50*|20*|20*|30*|
        mem.allocate(50, ++allocNum);
        assertTrue(allocNum == 8);
        assertTrue(mem.getDefrag() == 1);
        assertTrue(mem.getFailedAllocs() == 1);
        assertTrue(mem.getFailedSize() == 50);
        assertTrue(mem.emptyMemory.size() == 0);
        assertTrue(mem.getFilledMem().size() == 7);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 20);
        assertTrue(mem.getFilledMem().get(5).getSize() == 20);
        assertTrue(mem.getFilledMem().get(6).getSize() == 30);

        // dealloc
        // |20*|30*|30|50*|20*|20*|30*|
        assertTrue(mem.deallocate(3));
        assertTrue(mem.getDefrag() == 1);
        assertTrue(mem.getFailedAllocs() == 1);
        assertTrue(mem.getFailedSize() == 50);
        assertTrue(mem.emptyMemory.size() == 1);
        assertTrue(mem.getFilledMem().size() == 6);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 50);
        assertTrue(mem.getFilledMem().get(3).getSize() == 20);
        assertTrue(mem.getFilledMem().get(4).getSize() == 20);
        assertTrue(mem.getFilledMem().get(5).getSize() == 30);
        assertTrue(mem.emptyMemory.root().getSize() == 30);

        // dealloc
        // |20*|30*|30|50*|20*|20|30*|
        assertTrue(mem.deallocate(6));
        assertTrue(mem.getDefrag() == 1);
        assertTrue(mem.getFailedAllocs() == 1);
        assertTrue(mem.getFailedSize() == 50);
        assertTrue(mem.emptyMemory.size() == 2);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 50);
        assertTrue(mem.getFilledMem().get(3).getSize() == 20);
        assertTrue(mem.getFilledMem().get(4).getSize() == 30);
        assertTrue(mem.emptyMemory.root().getSize() == 30);
     

        // |20*|30*|30|50*|20*|20|30*|
        // force defrag fail
        mem.allocate(50, ++allocNum);
        assertTrue(allocNum == 9);
        assertTrue(mem.getDefrag() == 2);
        assertTrue(mem.getFailedAllocs() == 2);
        assertTrue(mem.emptyMemory.size() == 2);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 50);
        assertTrue(mem.getFilledMem().get(3).getSize() == 20);
        assertTrue(mem.getFilledMem().get(4).getSize() == 30);
        assertTrue(mem.emptyMemory.root().getSize() == 30);


        // |20*|30|30|50*|20*|20|30*|
        // dealloc to have 30 and 30 open.
        assertTrue(mem.deallocate(2));
        assertTrue(mem.getDefrag() == 2);
        assertTrue(mem.getFailedAllocs() == 2);
        assertTrue(mem.emptyMemory.size() == 3);
        assertTrue(mem.getFilledMem().size() == 4);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 50);
        assertTrue(mem.getFilledMem().get(2).getSize() == 20);
        assertTrue(mem.getFilledMem().get(3).getSize() == 30);
        assertTrue(mem.emptyMemory.root().getSize() == 30);

        // another alloc
        // |20*|50*|10|50*|20*|20|30*|
        // 50 added to end of arraylist.
        mem.allocate(50, ++allocNum);
        assertTrue(allocNum == 10);
        assertTrue(mem.getDefrag() == 3);
        assertTrue(mem.getFailedAllocs() == 2);
        assertEquals(mem.emptyMemory.size(), 2);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 50);
        assertTrue(mem.getFilledMem().get(2).getSize() == 20);
        assertTrue(mem.getFilledMem().get(3).getSize() == 30);
        assertTrue(mem.getFilledMem().get(4).getSize() == 50);
        assertTrue(mem.emptyMemory.root().getSize() == 20);


        // dealloc again
        // |20*|50*|10|50|20*|20|30*|
        assertTrue(mem.deallocate(4));
        assertTrue(allocNum == 10);
        assertTrue(mem.getDefrag() == 3);
        assertTrue(mem.getFailedAllocs() == 2);
        assertEquals(mem.emptyMemory.size(), 3);
        assertTrue(mem.getFilledMem().size() == 4);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 20);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.emptyMemory.root().getSize() == 50);


        // |20*|50*|10|8*|42|20*|20|30*|
        // alloc 8 THIS IS WHERE WORST FIT COMES IN.
        mem.allocate(8, ++allocNum);
        assertTrue(allocNum == 11);
        assertTrue(mem.getDefrag() == 3);
        assertTrue(mem.getFailedAllocs() == 2);
        assertEquals(mem.emptyMemory.size(), 3);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 20);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 8);
        assertTrue(mem.emptyMemory.root().getSize() == 42);


        // final memory for test described in memSim.pdf
        // | 20* | 50* | 10 | 8* | 42 | 20* | 20 | 30* |
        assertEquals(mem.emptyMemory.size(), 3);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 20);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 8);
        assertTrue(mem.emptyMemory.root().getSize() == 42);
       

    }
}
