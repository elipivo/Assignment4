import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ThresholdMemoryTest {
    private ThresholdMemory mem;
    
    @Before
    public void init(){
        mem = new ThresholdMemory(1000);
    }
    
    @Test
    public void allocateTest(){
        for (int i = 1; i<5; i++){
            mem.allocate(i*100, i);
            assertEquals(mem.getFilledMemory().get(i-1).getSize(), (i*100));
        }
        
    }
    
    @Test
    public void deallocateTest(){
    }
    
    @Test
    public void defragTest(){
        
    }
   
    
}
