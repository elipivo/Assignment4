import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

/* Elijah Pivo
 * epivo1
 * 600.226.01
 * Project 3
 */

public class AVLtreeTest {
    
    AVLtree<Integer> tree;
    SortedSet<Integer> test;
    Iterator<Integer> testIter;
    Iterable<Integer> treeIter;
    
    @Before
    public void setUp() {
        tree = new AVLtree<Integer>();
        test = new TreeSet<Integer>();
    }
    
    @Test
    public void testAdd() {
        
        /*
         *  50
         */
        tree.add(50);
        test.add(50);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        tree.isBalanced(); //test the tree is balanced
        
        /*
         *    50
         *   /
         *  40
         */
        tree.add(40);
        test.add(40);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        tree.isBalanced(); //test the tree is balanced
        
        /* Forces Left-Left case (with no subtrees dealt w/)
         *    40
         *   /  \
         *  30  50 
         */
        tree.add(30);
        test.add(30);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        tree.isBalanced(); //test the tree is balanced
        
        /*
         *    40
         *   /  \
         *  30  50 
         *   \
         *    35 
         */
        tree.add(35);
        test.add(35);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        tree.isBalanced(); //test the tree is balanced
        
        /* Forces Right-Right case (with no subtrees dealt w/)
         *     40
         *    /  \
         *   35  50 
         *  /  \
         * 30  37 
         */
        tree.add(37);
        test.add(37);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        tree.isBalanced(); //test the tree is balanced
        
        /* Forces Left-Left case (w/ subtrees dealt w/)
         *      35
         *    /    \
         *   30     40 
         *  /      /  \
         * 20     37  50
         */
        tree.add(20);
        test.add(20);
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        tree.isBalanced(); //test the tree is balanced
        
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
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        tree.isBalanced(); //test the tree is balanced
        
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
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        tree.isBalanced(); //test the tree is balanced
        
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
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        tree.isBalanced(); //test the tree is balanced
        
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
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        tree.isBalanced(); //test the tree is balanced
        
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
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        tree.isBalanced(); //test the tree is balanced
        
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
        treeIter = tree.inOrder();
        testIter = test.iterator();
        for(Integer i : treeIter) { //test order is correct
            assertEquals(i, testIter.next());
        }
        assertFalse(testIter.hasNext());
        tree.isBalanced(); //test the tree is balanced
        
    }

}
