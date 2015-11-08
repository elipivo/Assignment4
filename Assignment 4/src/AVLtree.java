
/**
 * 600.226, Fall 2015
 * Starter code for AVLtree implementation
 */
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
            temp = this.balance(temp);

        } else { // val >= temp
            temp.right = this.insert(val, temp.right);
            temp = this.balance(temp);

        }
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
     * Deletes from the left.
     * 
     * @param curr
     *            Current node
     */
    private void deleteLeft(BNode curr) {
        BNode left = curr.left; // To simplify typing, might inline
        // later
        if (left.left != null && left.right != null) { // Two children
            BNode min = this.findMin(left.right);
            curr.left = min;
            this.delete(left.right, min.data);
        }
        if (left.left != null && left.right == null) { // One left child
            curr.left = left.left;
        }
        if (left.left == null && left.right != null) { // One right
            // child
            curr.left = left.right;
        }
        //no children
        if (left.left == null && left.right == null){
            curr.left = null;
        }
    }

    /**
     * Deletes from the right.
     * 
     * @param curr
     *            Current node
     */
    private void deleteRight(BNode curr) {

        BNode right = curr.right; // To simplify typing, might inline
                                  // later
        if (right.left != null && right.right != null) { // Two children
            BNode min = this.findMin(right.right);
            curr.right = min;
            this.delete(right.right, min.data);
        }
        if (right.left != null && right.right == null) { // One left
                                                         // child
            curr.left = right.left;
        }
        if (right.left == null && right.right != null) { // One right
                                                         // child
            curr.left = right.right;
        }
        if (right.left == null && right.right == null) {
            curr.right = null;
        }

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
 
        if (curr == null) {
            return null;
        }
        if (value == this.root()){
            BNode min = this.findMin(this.root.right);
            this.root = min;
            //this.delete(right.right, min.data);
        }
        if (curr.left != null && curr.left.data.compareTo(value) == 0) {
            this.deleteLeft(curr);
            curr = this.balance(curr);
        }

        if (curr.right != null && curr.right.data.compareTo(value) == 0) {
            this.deleteRight(curr);
            curr = this.balance(curr);

        } else if (value.compareTo(curr.data) < 0) {

            curr.left = this.delete(curr.left, value);
            curr = this.balance(curr);

        } else { // val >= temp
            curr.right = this.delete(curr.right, value);
            curr = this.balance(curr);
        }
        return curr;

    }

    /**
     * Performs balancing of the nodes if necessary. IMPLEMENT!
     * 
     * @param curr
     *            the root of the subtree to balance
     * @return the root node of the newly balanced subtree
     */
    private BNode balance(BNode curr) {
        if (this.balanceFactor(curr) > 1) {
            if (this.balanceFactor(curr.left) >= 0) {
                curr = this.rotateWithLeftChild(curr);
            }
            if (this.balanceFactor(curr.left) < -1) {
                curr = this.doubleWithLeftChild(curr);
            }
        }
        if (this.balanceFactor(curr) < -1) {
            if (this.balanceFactor(curr.right) <= 0) {
                curr = this.rotateWithRightChild(curr);
            }
            if (this.balanceFactor(curr.left) > 1) {
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
                && Math.abs(this.height(curr.left)
                        - this.height(curr.right)) < 2;
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
     * Return maximum of lhs and rhs.
     * 
     * @param lhs
     *            height of lhs
     * @param rhs
     *            height of rhs
     * @return the int that's larger
     */
    private static int max(int lhs, int rhs) {
        if (lhs > rhs) {
            return lhs;
        }
        return rhs;
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
            k2.left = k1.right;
            k1.right = k2;
            k2.height = this.max(this.height(k2.left), this.height(k2.right)) + 1;
            k1.height = this.max(this.height(k1.left), k2.height) + 1;
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
            k1.height = max(this.height(k1.left), this.height(k1.right)) + 1;
            k2.height = max(this.height(k2.right), k1.height) + 1;
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
