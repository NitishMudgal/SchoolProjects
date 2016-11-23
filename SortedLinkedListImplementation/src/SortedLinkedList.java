/**
 * SortedLinked List implements the funcitonality of linked list which contains String value
 * in a sorted fashion. The class implements basic methods of LinkedList.
 * @author Nitish
 *
 */
public class SortedLinkedList implements MyListInterface {
    /**
     * private Node field to store the linked list data and references.
     */
    private Node head;
    /**
     * Default constructor for SortedLinkedList.
     */
    SortedLinkedList() {
        head = null;
    }
    /**
     * Constructor for SortedLinkedList, takes unsorted array of strings as input.
     * @param unsorted String array
     */
    SortedLinkedList(String[] unsorted) {
        shiftValue(unsorted, unsorted.length);
    }
    /**
     * Shiftvalue takes the unsorted array and using recursion calls the add method implemenation
     * for MyListInterface interface.
     * @param unsorted String array
     * @param length length of unsorted array
     */
    private void shiftValue(String[] unsorted, int length) {
        if (length == 0) {
            return;
        }
        add(unsorted[length - 1]);
        shiftValue(unsorted, length - 1);
    }
    /**
     * Add is the overridden implementation of add method in the interface.
     * @param value String value taken as input from the unsorted array.
     */
    @Override
    public void add(String value) {
        if (value == "" || value == null) { // Check for empty or null value.
            return;
        }
        if (!contains(value)) { // contains method checks if the value is present in the linked list.
            head = add(value, head); // calls the add method which is helper method.
        }
    }
    /**
     * Add method is the helper method for the overridden implementation of add method.
     * @param value String value.
     * @param n Node.
     * @return Node.
     */
    private Node add(String value, Node n) {
        // If the data value is less than the value provided, then a new node is created and set.
        if (n == null || n.data.compareTo(value) > 0) {
            return new Node(value, n);
        } else {
            n.next = add(value, n.next); // recursive call to the same function.
            return n;
        }
    }
    /**
     * gives the size of the linked list, overridden method of the implementation.
     * @return int Size.
     */
    @Override
    public int size() {
        return size(head);
    }
    /**
     * Helper method for the overidden method, recursively gives the size.
     * @param n Node.
     * @return int Size.
     */
    private int size(Node n) {
        if (n == null) {
            return 0;
        }
        return 1 + size(n.next); //recursively counting the number of elements.
    }
    /**
     * display method is used to display the linked list, overidden method.
     * @return void.
     */
    @Override
    public void display() {
        System.out.print("[");
        display(head); // helper method calling.
        System.out.println("]");
    }
    /**
     * display helper method called by the overidden method.
     * @param n Node.
     * @return Node.
     */
    private Node display(Node n) {
        if (n == null) { // base condition.
            return null;
        }
        System.out.print(n.data); // prints the data value.
        if (n.next != null) {
            System.out.print(", ");
        }
        return display(n.next);
    }
    /**
     * contains is the overidden method which checks if the value is present in the list.
     * @param key String.
     * @return boolean.
     */
    @Override
    public boolean contains(String key) {
        return contains(key, head);
    }
    /**
     * helper method for the overridden method.
     * @param key String.
     * @param n Node.
     * @return boolean.
     */
    private boolean contains(String key, Node n) {
        if (n == null) { // base condition
            return false;
        } else if (n.data.equals(key)) { // if found return true
            return true;
        } else {
            return contains(key, n.next); // recursive call
        }
    }
    /**
     * isEmpty is the overridden method which tells if the list is empty or not.
     * @return boolean.
     */
    @Override
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }
    /**
     * removeFirst removes the first element of the list.
     * @return String.
     */
    @Override
    public String removeFirst() {
        if (head == null) {
            return null;
        }
        String str = head.data;
        head = head.next; // pushes the head to the next element
        return str;
    }
    /**
     * removeAt takes index value and removes the element present at that position.
     * @param index int.
     * @return String.
     */
    @Override
    public String removeAt(int index) {
        if (index == 0) {
            return removeFirst(); // removeFirst is called if index = 0
        } else {
            return removeAt(index, null, head); // removeAt helper method is called for other cases
        }
    }
    /**
     * helper method for removeAt method.
     * @param index int.
     * @param prev Node.
     * @param curr Node.
     * @return String.
     */
    private String removeAt(int index, Node prev, Node curr) {
        if (index > size() || curr == null) { // check for index greater than size or node == null
            return "";
        }
        if (index == 0) {
            prev.next = curr.next; //switching the nodes and removing curr node
            curr.next = null;
            return curr.data; //returning the data of removed node
        }
        return removeAt(--index, curr, curr.next); //recursive calling
    }
    /**
     * Node class is the private class which has private fields, String data and Node next.
     * @author Nitish.
     */
    private static class Node {
        /**
         * stores the string value.
         */
        private String data;
        /**
         * store the reference to next node.
         */
        private Node next;
        /**
         * Constructor for Node class.
         * @param data String
         * @param next Node
         */
        public Node(String data, Node next) { // constructor for Node
            this.data = data;
            this.next = next;
        }
    }
}