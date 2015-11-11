/**
 * 600.226, Fall 2015
 * Starter code for AVLtree implementation
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * AVL Tree - based on Weiss.
 * 
 * @param <T>
 *            the base type of data in a node
 *
 */
public class AVLtree<T extends Comparable<? super T>> {

    /**
     * Inner node class. Do not make this static because you want the T to be
     * the same T as in the BST header.
     */
    public class BNode {

        /** Variable data of type T. */
        protected T data;
        /** Variable left of type BNode. */
        protected BNode left;
        /** Variable right of type BNode. */
        protected BNode right;
        /** Variable height of the node. */
        private int height;

        /**
         * Constructor for BNode.
         * 
         * @param val
         *            to insert the given node.
         */
        public BNode(T val) {
            this.data = val;
            this.height = 0;
        }

        /**
         * Returns whether node is a leaf or not.
         * 
         * @return true is node is leaf, false if not
         */
        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        /**
         * Clone.
         * 
         * @return Clone
         */
        public BNode clone() {
            BNode temp = new BNode(this.data);
            temp.height = this.height;
            temp.right = this.right;
            temp.left = this.left;
            return temp;
        }
    }

    /** The root of the tree. */
    private BNode root;
    /** The size of the tree. */
    private int size;

    /**
     * Constructs a Binary Search Tree.
     */
    public AVLtree() {
        this.root = null;
        this.size = 0;
    }
    
    /**
     * Constructs a Binary Search Tree from an ArrayList.
     * @param list The ArrayList to construct the AVLTree from
     */
    public AVLtree(ArrayList<T> list) {
        this.root = null;
        this.size = 0;
        
        for (T entry : list) {
            this.add(entry);
        }
    }
    

    /**
     * Find out how many elements are in the Tree.
     * 
     * @return the number
     */
    public int size() {
        return this.size;
    }

    /**
     * See if the Tree is empty.
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Get the value of the root.
     * 
     * @return value of the root
     */
    public T root() {
        if (this.root == null) {
            return null;
        }
        return this.root.data;
    }

    /**
     * Search for an item in the tree.
     * 
     * @param val
     *            the item to search for
     * @return true if found, false otherwise
     */
    public boolean contains(T val) {
        return this.contains(val, this.root) != null;
    }

    /**
     * Checks if a tree contains a certain value.
     * 
     * @param val
     *            the value you're looking for
     * @param curr
     *            the root of the tree you're searching
     * @return the node that contains that value
     */
    public BNode contains(T val, BNode curr) {
        if (curr == null || this.isEmpty()) {
            return null;
        }
        if (val.equals(curr.data)) {
            return curr;
        }
        if (val.compareTo(curr.data) < 0) {
            return this.contains(val, curr.left);
        }
        return this.contains(val, curr.right);
    }
    
    /** Return the smallest value present that is greater than val.
     * 
     * @param val the value you want to ceiling
     * @return the smallest value greater than val present in the tree
     */
    public T ceiling(T val) {

        BNode ret = this.ceiling(val, this.root);
        if (ret == null) {
            return null;
        } else {
            return ret.data;
        }
        
    }
    
    /** Returns the BNode containing the ceiling of val.
     *  This is the smallest value present greater than val.
     * 
     * @param val the value to ceiling
     * @param curr the current node
     * @return the node containing the ceiling
     */
    private BNode ceiling(T val, BNode curr) {
        
        if (val == null || curr == null || this.isEmpty()) {
            return null;
        }
        
        if (val.equals(curr.data)) {
            return curr;
        } else if (val.compareTo(curr.data) > 0) {
            
            if (val.compareTo(curr.left.data) > 0) {
                //the next one is closer!
                return this.ceiling(val, curr.left);
            } else {
                //the next one would be too far
                return curr;
            }
            
        } else {
            return null;
        }
        
    }

    /**
     * Add an item to the Tree.
     * 
     * @param val
     *            the item to add
     * @return true if added, false if val is null
     */
    public boolean add(T val) {
        if (val != null) {
            this.root = this.insert(val, this.root);
            this.size++;
            // how you can check whether the root is balanced:
            // System.out.println(Math.abs(balanceFactor(this.root)));
            return true;
        }
        return false;
    }

    /**
     * Helper insert method.
     * 
     * @param val
     *            the value to insert
     * @param curr
     *            the root of the tree
     * @return the node that is inserted
     */
    private BNode insert(T val, BNode curr) {
        BNode temp = curr;
        if (temp == null) { // leaf, make new node
            return new BNode(val);
        }
        if (val.compareTo(temp.data) < 0) {
            temp.left = this.insert(val, temp.left);

        } else { // val >= temp
            temp.right = this.insert(val, temp.right);
        }
        
        //update height for current node
        curr.height = Math.max(this.height(curr.left), this.height(curr.right))
                + 1;
        
        temp = this.balance(temp);
        return temp;
    }

    /**
     * Remove an item from the Tree.
     * 
     * @param val
     *            the item to remove
     * @return true if removed, false if not found
     */
    public boolean remove(T val) {
        if (this.contains(val)) {
            this.root = this.delete(this.root, val);
            this.size--;
            // how you can check whether the root was balanced:
            // System.out.println(Math.abs(balanceFactor(this.root)))
            return true;
        }
        return false;
    }
    
    /**
   * Helper delete method. - This does the real work - IMPLEMENT!
   * 
   * @param value
   *            the value to delete
   * @param curr
   *            the root of the subtree to look in
   * @return the new subtree after rebalancing
   */
    private BNode delete(BNode curr, T value) {
        //go to the node that needs to be deleted

        if (curr == null) { //should I include || value == null?
            //if curr is empty or value is empty just return curr unchanged

            return curr;

        } else if (value.equals(curr.data)) {
            //carry out delete if equal
            
            if (curr.left == null && curr.right == null) {
                //we're deleting a leaf
                return null;
                
            }  else if (curr.left != null && curr.right != null) {
                //we're deleting something with two children
                
                //replace with the in order successor
                curr.data = this.findMin(curr.right).data;
                
                //delete the in order successor
                curr.right = this.delete(curr.right, curr.data);
                
            } else if (curr.left != null) {
                //we're deleting something with only a left child  
                return curr.left;
                
            } else {
                //we're deleting something with only a right child
                return curr.right;   
            }

        } else if (value.compareTo(curr.data) < 0) {
            //go left if the value is smaller than the node we're at

            curr.left = this.delete(curr.left, value);

        } else if (value.compareTo(curr.data) > 0) {
            //go right if the value is larger than the node we're at
            
            curr.right = this.delete(curr.right, value);

        }
        
        //now update the height of the node
        curr.height = Math.max(this.height(curr.left), this.height(curr.right))
                + 1;
        
        //at this point the node should be removed
        //however the node isn't balanced
        
        return this.balance(curr);

    }
    

    /**
     * Performs balancing of the nodes if necessary. IMPLEMENT!
     * 
     * @param curr
     *            the root of the subtree to balance
     * @return the root node of the newly balanced subtree
     */
    private BNode balance(BNode curr) {
        
        int currFactor = this.balanceFactor(curr);
        
        if (currFactor > 1) {
            int lFactor = this.balanceFactor(curr.left);
            if (lFactor >= 0) {
                //Left Left Case
                curr = this.rotateWithLeftChild(curr);
            } else if (lFactor <= -1) {
                //Left Right Case
                curr = this.doubleWithLeftChild(curr);
            }
        } else if (this.balanceFactor(curr) < -1) {
            int rFactor = this.balanceFactor(curr.right);
            if (rFactor <= 0) {
                //Right Right Case
                curr = this.rotateWithRightChild(curr);
            } else if (rFactor >= 1) {
                //Right Left Case
                curr = this.doubleWithRightChild(curr);
            }
        }

        return curr;
    }

    /**
     * Checks balance of nodes.
     * 
     * @param b
     *            node to check balance at
     * @return integer that is balance factor
     */
    private int balanceFactor(BNode b) {
        if (b == null) {
            return -1;
        }

        if (b.isLeaf()) {
            return 0;
        }

        return this.height(b.left) - this.height(b.right);
    }

    /**
     * Search from curr (as root of subtree) and find minimum value.
     * 
     * @param curr
     *            the root of the tree
     * @return the min
     */
    private BNode findMin(BNode curr) {
        BNode temp = curr;
        if (temp == null) {
            return temp;
        }
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp;
    }

    /**
     * Return the height of node t, or -1, if null.
     * 
     * @param t
     *            the node to find the height of
     * @return int height to be returned
     */
    private int height(BNode t) {
        if (t == null) {
            return -1;
        }
        return t.height;
    }

    /**
     * Check whether tree is balanced.
     * 
     * @param curr
     *            the root of the tree
     * @return true if balanced, false if not
     */
    public boolean isBalanced(BNode curr) {
        if (curr == null) {
            return true;
        }
        return this.isBalanced(curr.left) && this.isBalanced(curr.right)
                && Math.abs(this.height(curr.left) - this.height(curr.right))
                < 2;
    }

    /**
     * Check whether THIS tree is balanced.
     * 
     * @return true if balanced, false if not
     */
    public boolean isBalanced() {
        return this.isBalanced(this.root);
    }

    /**
     * Rotate binary tree node with left child. Update heights, then return new
     * root.
     * 
     * @param k2
     *            node to rotate
     * @return updated node
     */
    private BNode rotateWithLeftChild(BNode k2) {
        if (k2 == null) {
            return null;
        }
        BNode k1 = k2.left;
        if (k1 != null) {
            
            //rotate
            k2.left = k1.right;
            k1.right = k2;
            
            //update heights
            k1.height = Math.max(this.height(k1.left), this.height(k1.right))
                    + 1;
            k2.height = Math.max(this.height(k2.left), this.height(k2.right))
                    + 1;
        }
        return k1;
    }

    /**
     * Rotate binary tree node with right child. Update heights, then return new
     * root.
     * 
     * @param k1
     *            node to rotate
     * @return updated node
     */
    private BNode rotateWithRightChild(BNode k1) {
        if (k1 == null) {
            return null;
        }
        BNode k2 = k1.right;
        if (k2 != null) {
            k1.right = k2.left;
            k2.left = k1;
            
            //update heights
            k1.height = Math.max(this.height(k1.left), this.height(k1.right))
                    + 1;
            k2.height = Math.max(this.height(k2.left), this.height(k2.right))
                    + 1;
        }
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child with its right child;
     * then node k3 with new left child. Update heights, then return new root.
     * 
     * @param k3
     *            node to rotate
     * @return update node
     */
    private BNode doubleWithLeftChild(BNode k3) {
        if (k3 != null) {
            k3.left = this.rotateWithRightChild(k3.left);
            return this.rotateWithLeftChild(k3);
        }
        return k3;
    }

    /**
     * Double rotate binary tree node: first right child with its left child;
     * then node k1 with new right child. Update heights, then return new root.
     * 
     * @param k1
     *            node to rotate
     * @return updated node
     */
    private BNode doubleWithRightChild(BNode k1) {
        if (k1 != null) {
            k1.right = this.rotateWithLeftChild(k1.right);
            return this.rotateWithRightChild(k1);
        }
        return k1;
    }

    /**
     * String representation of the Tree with elements in order.
     * 
     * @return a string containing the Tree contents in the format "[1, 5, 6]".
     */
    public String toString() {
        return this.inOrder().toString();
    }

    /**
     * Inorder traversal.
     * 
     * @return a Collection of the Tree elements in order
     */
    public Iterable<T> inOrder() {
        return this.inOrder(this.root);
    }

    /**
     * Preorder traversal.
     * 
     * @return a Collection of the Tree elements in preorder
     */
    public Iterable<T> preOrder() {
        return this.preOrder(this.root);
    }

    /**
     * Postorder traversal.
     * 
     * @return a Collection of the Tree elements in postorder
     */
    public Iterable<T> postOrder() {
        return this.postOrder(this.root);
    }

    /**
     * Generates an in-order list of items.
     * 
     * @param curr
     *            the root of the tree
     * @return collection of items in order
     */
    private Collection<T> inOrder(BNode curr) {
        LinkedList<T> iter = new LinkedList<T>();
        if (curr == null) {
            return iter;
        }
        iter.addAll(this.inOrder(curr.left));
        iter.addLast(curr.data);
        iter.addAll(this.inOrder(curr.right));
        return iter;
    }

    /**
     * Generates a pre-order list of items.
     * 
     * @param curr
     *            the root of the tree
     * @return collection of items in preorder
     */
    private Collection<T> preOrder(BNode curr) {
        LinkedList<T> iter = new LinkedList<T>();
        if (curr == null) {
            return iter;
        }
        iter.addLast(curr.data);
        iter.addAll(this.preOrder(curr.left));
        iter.addAll(this.preOrder(curr.right));
        return iter;
    }

    /**
     * Generates a post-order list of items.
     * 
     * @param curr
     *            the root of the tree
     * @return collection of items in postorder
     */
    private Collection<T> postOrder(BNode curr) {
        LinkedList<T> iter = new LinkedList<T>();
        if (curr == null) {
            return iter;
        }
        iter.addAll(this.postOrder(curr.left));
        iter.addAll(this.postOrder(curr.right));
        iter.addLast(curr.data);
        return iter;
    }
}
