import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/* Elijah Pivo
 * epivo1
 * 600.226.01
 * Project 3
 */

public class AVLtreeTest {
    
    AVLtree<Integer> tree;
    List<Integer> test;
    Iterator<Integer> testIter;
    Iterable<Integer> treeIter;
    
    @Before
    public void setUp() {
        tree = new AVLtree<Integer>();
        test = new LinkedList<Integer>();
    }
    
    @Test
    public void testAdd() {
        
        /*
         *  50
         */
        tree.add(50); 
        test.add(50);
        Collections.sort(test);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        assertTrue(tree.isBalanced()); //test the tree is balanced
        
        /*
         *    50
         *   /
         *  40
         */
        tree.add(40);
        test.add(40);
        Collections.sort(test);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        assertTrue(tree.isBalanced()); //test the tree is balanced
        
        /* Forces Left-Left case (with no subtrees dealt w/)
         *    40
         *   /  \
         *  30  50 
         */
        tree.add(30);
        test.add(30);
        Collections.sort(test);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        assertTrue(tree.isBalanced()); //test the tree is balanced
        
        /*
         *    40
         *   /  \
         *  30  50 
         *   \
         *    35 
         */
        tree.add(35);
        test.add(35);
        Collections.sort(test);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        assertTrue(tree.isBalanced()); //test the tree is balanced
        
        /* Forces Right-Right case (with no subtrees dealt w/)
         *     40
         *    /  \
         *   35  50 
         *  /  \
         * 30  37 
         */
        tree.add(37);
        test.add(37);
        Collections.sort(test);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        assertTrue(tree.isBalanced()); //test the tree is balanced
        
        /* Forces Left-Left case (w/ subtrees dealt w/)
         *      35
         *    /    \
         *   30     40 
         *  /      /  \
         * 20     37  50
         */
        tree.add(20);
        test.add(20);
        Collections.sort(test);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        assertTrue(tree.isBalanced()); //test the tree is balanced
        
        /*
         *      35
         *    /    \
         *   30     40 
         *  /      /  \
         * 20     37  50
         *              \
         *               60
         */
        tree.add(60);
        test.add(60);
        Collections.sort(test);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        assertTrue(tree.isBalanced()); //test the tree is balanced
        
        /*
         *      35
         *    /    \
         *   30     40 
         *  /      /  \
         * 20     37  50
         *           /  \
         *          45   60
         */
        tree.add(45);
        test.add(45);
        Collections.sort(test);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        assertTrue(tree.isBalanced()); //test the tree is balanced
        
        /* Forces Right-Right case w/ subtree dealt w/
         *      35
         *    /    \
         *   30     50 
         *  /      /  \
         * 20     40  60
         *       /  \   \
         *      37  45  70
         */
        tree.add(70);
        test.add(70);
        Collections.sort(test);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        assertTrue(tree.isBalanced()); //test the tree is balanced
        
        /* Forces Right-Left case w/ subtree dealt w/
         *        40
         *     /     \
         *    35      50 
         *   /  \    /  \
         *  30   37 45   60
         * /     /        \
         *20    35        70
         *      
         */
        tree.add(35);
        test.add(35);
        Collections.sort(test);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        assertTrue(tree.isBalanced()); //test the tree is balanced
        
        /* 
         *        40
         *     /     \
         *    35       50 
         *   /  \     /  \
         *  30   37  45   60
         * /    /  \        \
         *20   35   38      70    
         */
        tree.add(38);
        test.add(38);
        Collections.sort(test);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        assertTrue(tree.isBalanced()); //test the tree is balanced
        
        /* Forces Left-Right Case w/ Subtree Handling
         *        37
         *     /     \
         *    35        40 
         *   /  \     /    \
         *  30   35  38     50
         * /           \   / \
         *20           39 45 60
         *                     \
         *                     70    
         */
        tree.add(39);
        test.add(39);
        Collections.sort(test);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        assertTrue(tree.isBalanced()); //test the tree is balanced
        
    }
    
    @Test
    public void testRemove() {
        
        //remove on null
        assertFalse(tree.remove(3));
        
        /* Build Tree
         *            8
         *      /           \
         *     4            12
         *   /   \        /    \
         *  2     5      10     14
         * / \   / \    /  \   /  \
         *1   3 6   7  9   11 13   15                
         */
        for (int i = 1; i <= 15; i++) {
           tree.add(i); 
           test.add(i);
        }
        
        //try to remove something that doesn't exit
        assertFalse(tree.remove(16));
        
        //remove each node from 15 to 1
        for (int i = 14; i > 0; i--) {
            assertTrue(tree.remove(i));
            test.remove(i);
            Collections.sort(test);
            treeIter = tree.inOrder();
            testIter = test.iterator();
            for(Integer k : treeIter) { //test order is correct
                assertEquals(k, testIter.next());
            }
            assertFalse(this.testIter.hasNext());
            assertTrue(this.tree.isBalanced()); //test the tree is balanced
        }
        
        /* Build Tree
         *            8
         *      /           \
         *     4            12
         *   /   \        /    \
         *  2     5      10     14
         * / \   / \    /  \   /  \
         *1   3 6   7  9   11 13   15                
         */
        for (int i = 1; i <= 15; i++) {
            tree.add(i); 
            test.add(i);
        }

        //remove each node from 1 to 15  
        for (int i = 1; i <= 15; i++) {
            assertTrue(tree.remove(i));
            test.remove(i);
            Collections.sort(test);
            treeIter = tree.inOrder();
            testIter = test.iterator();
            for(Integer k : treeIter) { //test order is correct
                assertEquals(k, testIter.next());
            }
            assertFalse(this.testIter.hasNext());
            assertTrue(this.tree.isBalanced()); //test the tree is balanced
        }
        
        //remove each node from 1 to 15 individually  
        for (int i = 1; i <= 15; i++) {
            /* Build Tree
             *            8
             *      /           \
             *     4            12
             *   /   \        /    \
             *  2     5      10     14
             * / \   / \    /  \   /  \
             *1   3 6   7  9   11 13   15                
             */
            
            for (int n = 1; n <= 15; n++) {
                tree.add(n); 
                test.add(n);
            }
            
            assertTrue(tree.remove(i));
            test.remove(i);
            Collections.sort(test);
            treeIter = tree.inOrder();
            testIter = test.iterator();
            for(Integer k : treeIter) { //test order is correct
                assertEquals(k, testIter.next());
            }
            assertFalse(this.testIter.hasNext());
            assertTrue(this.tree.isBalanced()); //test the tree is balanced
            
            //then clear tree
            for (int n = 1; n <= 15; i++) {
                tree.remove(i);
                test.remove(i);
            }
            
        }
        
    }
}
