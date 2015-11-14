import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class ThresholdMemoryTest {
    private ThresholdMemory mem;

    @Before
    public void init() {
        mem = new ThresholdMemory(1000);
    }
    @Test
    public void threshTest(){
        mem = new ThresholdMemory(1000,200);
        mem.allocate(300,1);
        mem.allocate(200, 2);
        mem.allocate(500, 3);
        // |300*|200*|500*|
        // Threshold should be 300 (avg allocation size)
        //Make sure threshold doesn't change
        assertEquals(mem.getThreshold(), 200);
        for (int i = 1; i < 4; i++) {
            mem.deallocate(i);
        }
        //|300|200|500|
        mem.allocate(100, 4);

        //|100*|200|200|500|
        mem.allocate(200, 5);

        //|100*|200*|200|500|
        mem.allocate(500, 6);
        //|100*|200*|200|500*|
        mem.allocate(200, 6);
  
        //Best case would have defragged.
        assertEquals(mem.getNumDefrag(),0);
    }
    @Test
    public void allocateTest() {
        //
        
        // memory of size 200
        // Memory : |200|
        mem = new ThresholdMemory(200);
        int allocNum = 0;
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.getEmptyMemory().size() == 1);
        Iterator<Block> memIter = mem.getEmptyMemory().inOrder().iterator();
        assertTrue(memIter.next().getSize() == 200);
        assertTrue(mem.getFilledMem().size() == 0);

        // |20*|180| * = filled
        mem.allocate(20, ++allocNum);
        assertTrue(allocNum == 1);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.getEmptyMemory().size() == 1);
        assertTrue(mem.getFilledMem().size() == 1);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        memIter = mem.getEmptyMemory().inOrder().iterator();
        assertTrue(memIter.next().getSize() == 180);

        // |20*|30*|150|
        mem.allocate(30, ++allocNum);
        assertTrue(allocNum == 2);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.getEmptyMemory().size() == 1);
        assertTrue(mem.getFilledMem().size() == 2);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        memIter = mem.getEmptyMemory().inOrder().iterator();
        assertTrue(memIter.next().getSize() == 150);

        // |20*|30*|30*|120|
        mem.allocate(30, ++allocNum);
        assertTrue(allocNum == 3);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.getEmptyMemory().size() == 1);
        assertTrue(mem.getFilledMem().size() == 3);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        memIter = mem.getEmptyMemory().inOrder().iterator();
        assertTrue(memIter.next().getSize() == 120);

        // |20*|30*|30*|50*|70|
        mem.allocate(50, ++allocNum);
        assertTrue(allocNum == 4);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.getEmptyMemory().size() == 1);
        assertTrue(mem.getFilledMem().size() == 4);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        memIter = mem.getEmptyMemory().inOrder().iterator();
        assertTrue(memIter.next().getSize() == 70);

        // |20*|30*|30*|50*|20*|50|
        mem.allocate(20, ++allocNum);
        assertTrue(allocNum == 5);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.getEmptyMemory().size() == 1);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 20);
        memIter = mem.getEmptyMemory().inOrder().iterator();
        assertTrue(memIter.next().getSize() == 50);

        // |20*|30*|30*|50*|20*|20*|30|
        mem.allocate(20, ++allocNum);
        assertTrue(allocNum == 6);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.getEmptyMemory().size() == 1);
        assertTrue(mem.getFilledMem().size() == 6);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        assertTrue(mem.getFilledMem().get(4).getSize() == 20);
        assertTrue(mem.getFilledMem().get(5).getSize() == 20);
        memIter = mem.getEmptyMemory().inOrder().iterator();
        assertTrue(memIter.next().getSize() == 30);

        // |20*|30*|30*|50*|20*|20*|30*| All filled
        mem.allocate(30, ++allocNum);
        assertTrue(allocNum == 7);
        assertTrue(mem.getDefrag() == 0);
        assertTrue(mem.getFailedAllocs() == 0);
        assertTrue(mem.getFailedSize() == -1);
        assertTrue(mem.getEmptyMemory().size() == 0);
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
        assertTrue(mem.getEmptyMemory().size() == 0);
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
        assertTrue(mem.getEmptyMemory().size() == 1);
        assertTrue(mem.getFilledMem().size() == 6);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 50);
        assertTrue(mem.getFilledMem().get(3).getSize() == 20);
        assertTrue(mem.getFilledMem().get(4).getSize() == 20);
        assertTrue(mem.getFilledMem().get(5).getSize() == 30);
        memIter = mem.getEmptyMemory().inOrder().iterator();
        assertTrue(memIter.next().getSize() == 30);

        // dealloc
        // |20*|30*|30|50*|20*|20|30*|
        assertTrue(mem.deallocate(6));
        assertTrue(mem.getDefrag() == 1);
        assertTrue(mem.getFailedAllocs() == 1);
        assertTrue(mem.getFailedSize() == 50);
        assertTrue(mem.getEmptyMemory().size() == 2);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 50);
        assertTrue(mem.getFilledMem().get(3).getSize() == 20);
        assertTrue(mem.getFilledMem().get(4).getSize() == 30);
        memIter = mem.getEmptyMemory().inOrder().iterator();
        assertTrue(memIter.next().getSize() == 20);
        assertTrue(memIter.next().getSize() == 30);

        // |20*|30*|30|50*|20*|20|30*|
        // force defrag fail
        mem.allocate(50, ++allocNum);
        assertTrue(allocNum == 9);
        assertTrue(mem.getDefrag() == 2);
        assertTrue(mem.getFailedAllocs() == 2);
        assertTrue(mem.getEmptyMemory().size() == 2);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 30);
        assertTrue(mem.getFilledMem().get(2).getSize() == 50);
        assertTrue(mem.getFilledMem().get(3).getSize() == 20);
        assertTrue(mem.getFilledMem().get(4).getSize() == 30);
        memIter = mem.getEmptyMemory().inOrder().iterator();
        assertTrue(memIter.next().getSize() == 20);
        assertTrue(memIter.next().getSize() == 30);

        // |20*|30|30|50*|20*|20|30*|
        // dealloc to have 30 and 30 open.
        assertTrue(mem.deallocate(2));
        assertTrue(mem.getDefrag() == 2);
        assertTrue(mem.getFailedAllocs() == 2);
        assertTrue(mem.getEmptyMemory().size() == 3);
        assertTrue(mem.getFilledMem().size() == 4);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 50);
        assertTrue(mem.getFilledMem().get(2).getSize() == 20);
        assertTrue(mem.getFilledMem().get(3).getSize() == 30);
        memIter = mem.getEmptyMemory().inOrder().iterator();
        assertTrue(memIter.next().getSize() == 20);
        assertTrue(memIter.next().getSize() == 30);
        assertTrue(memIter.next().getSize() == 30);

        // another alloc
        // |20*|50*|10|50*|20*|20|30*|
        // 50 added to end of arraylist.
        mem.allocate(50, ++allocNum);
        assertTrue(allocNum == 10);
        assertTrue(mem.getDefrag() == 3);
        assertTrue(mem.getFailedAllocs() == 2);
        assertEquals(mem.getEmptyMemory().size(), 2);
        assertTrue(mem.getFilledMem().size() == 5);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 50);
        assertTrue(mem.getFilledMem().get(2).getSize() == 20);
        assertTrue(mem.getFilledMem().get(3).getSize() == 30);
        assertTrue(mem.getFilledMem().get(4).getSize() == 50);
        memIter = mem.getEmptyMemory().inOrder().iterator();
        assertTrue(memIter.next().getSize() == 10);
        assertTrue(memIter.next().getSize() == 20);

        // dealloc again
        // |20*|50*|10|50|20*|20|30*|
        assertTrue(mem.deallocate(4));
        assertTrue(allocNum == 10);
        assertTrue(mem.getDefrag() == 3);
        assertTrue(mem.getFailedAllocs() == 2);
        assertEquals(mem.getEmptyMemory().size(), 3);
        assertTrue(mem.getFilledMem().size() == 4);
        assertTrue(mem.getFilledMem().get(0).getSize() == 20);
        assertTrue(mem.getFilledMem().get(1).getSize() == 20);
        assertTrue(mem.getFilledMem().get(2).getSize() == 30);
        assertTrue(mem.getFilledMem().get(3).getSize() == 50);
        memIter = mem.getEmptyMemory().inOrder().iterator();
        assertTrue(memIter.next().getSize() == 10);
        assertTrue(memIter.next().getSize() == 20);
        assertTrue(memIter.next().getSize() == 50);



    }

    @Test
    public void deallocateTest() {
    }

    @Test
    public void defragTest() {

    }

}
