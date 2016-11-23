/**
 * 08722 Data Structures for Application Programmers.
 *
 * Homework Assignment 4
 * HashTable Implementation with linear probing
 *
 * Andrew ID: nmudgal
 * @author
 */
public class MyHashTable implements MyHTInterface {
    /**
     * Create a DataItem array reference.
     */
    private DataItem[] myHashArray;
    /**
     * This integer stores the number of collisions in the array.
     */
    private int numberOfCollisions;
    /**
     * This integer keeps a tab on the number of values stored in the Hashtable.
     */
    private int size;
    /**
     * Constant to store the default initial capacity.
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    /**
     * Constant to store the DELETED value of DATAITEM.
     */
    private static final DataItem DELETED = new DataItem("#DEL#", 0);
    /**
     * Constant with the DEFAULT Load Factor.
     */
    private static final double DEFAULT_LOAD_FACTOR = 0.5;
    /**
     * Default constructor of MyHashTable wich initializes the array with capacity of 10.
     */
    MyHashTable() {
        myHashArray = new DataItem[DEFAULT_INITIAL_CAPACITY];
    }
    /**
     * Constructor of MyHashTable wich initializes the array with new capacity.
     * It checks the capacity.
     * If its less throws a Runtime Exception.
     * @param initialCapacity capacity provided by user
     */
    MyHashTable(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new RuntimeException("Please provide initial capacity greater than 0");
        }
        myHashArray = new DataItem[initialCapacity];
    }


    /**
     * Instead of using String's hashCode, you are to implement your own here.
     * You need to take the table length into your account in this method.
     *
     * In other words, you are to combine the following two steps into one step.
     * 1. converting Object into integer value
     * 2. compress into the table using modular hashing (division method)
     *
     * Helper method to hash a string for English lowercase alphabet and blank,
     * we have 27 total. But, you can assume that blank will not be added into
     * your table. Refer to the instructions for the definition of words.
     *
     * For example, "cats" : 3*27^3 + 1*27^2 + 20*27^1 + 19*27^0 = 60,337
     *
     * But, to make the hash process faster, Horner's method should be applied as follows;
     *
     * var4*n^4 + var3*n^3 + var2*n^2 + var1*n^1 + var0*n^0 can be rewritten as
     * (((var4*n + var3)*n + var2)*n + var1)*n + var0
     *
     * Note: You must use 27 for this homework.
     *
     * However, if you have time, I would encourage you to try with other
     * constant values than 27 and compare the results but it is not required.
     * @param input input string for which the hash value needs to be calculated
     * @return int hash value of the input string
     */
    private int myHashFunc(String input) {
        // confirm that the input is in lowercase.
        input = input.toLowerCase();
        // get the ascii values of charaters and subtract by 96 to get a = 1.
        int asciiCode = input.charAt(0) - 96;
        for (int i = 1; i < input.length(); i++) {
            asciiCode = (asciiCode * 27 + (input.charAt(i) - 96)) % myHashArray.length;
        }
        int hashVal = asciiCode;
        return hashVal;
    }

    /**
     * doubles array length and rehash items whenever the load factor is reached.
     */
    private void rehash() {
        boolean isCollision = false;
        // resetting the number of collisions.
        numberOfCollisions = 0;
        // storing the array in a temp array.
        DataItem[] tempArray = new DataItem[myHashArray.length];
        // copying old array into temp array.
        System.arraycopy(myHashArray, 0, tempArray, 0, myHashArray.length);
        int newLength = primeLength(myHashArray.length);
        System.out.println("Rehashing " + (size + 1) + " items, new size is " + newLength);
        myHashArray = new DataItem[newLength];
        int hashValue;
        for (DataItem item : tempArray) {
            isCollision = false;
            if (item != null && item != DELETED) {
                hashValue = hashValue(item.value);
                while (myHashArray[hashValue] != null) {
                    isCollision = true;
                    hashValue++;
                    hashValue = hashValue % myHashArray.length;
                }
                myHashArray[hashValue] = item;
                if (isCollision) {
                    numberOfCollisions++;
                }
            }
        }
    }
    /**
     * Doubles the oldlength of the array and finds the nearest prime number to it.
     * @param oldLength initial lenght of hashtable.
     * @return int new Length
     */
    private int primeLength(int oldLength) {
        boolean isPrime = false;
        // setting newLength as twice of oldlength
        int newLength = oldLength * 2;
        while (!isPrime) {
            isPrime = true;
            for (int i = 2; i <= Math.sqrt(newLength); i++) {
                if (newLength % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (!isPrime) {
                newLength++;
            }
        }
        return newLength;
    }
    /**
     * Inserts a new String value (word).
     * Frequency of each word to be stored too.
     * @param value String value to add
     */
    @Override
    public void insert(String value) {
        // check if the value is not null and doesnot include character apart from lowercase alphabets.
        if (value != null && value.matches("[a-z]*")) {
            boolean isCollision = false;
            // check the size of the array, if more rehash the table.
            if (size() + 1 > DEFAULT_LOAD_FACTOR * myHashArray.length) {
                rehash();
            }
            int hashVal = hashValue(value);
            while (myHashArray[hashVal] != null && myHashArray[hashVal] != DELETED) {
                if (myHashArray[hashVal].value.equals(value)) {
                    myHashArray[hashVal].frequency++;
                    return;
                }
                isCollision = true;
                hashVal++;
                hashVal = hashVal % myHashArray.length;
            }
            DataItem item = new DataItem(value, 1);
            // increasing the size after insertion and if ifCollision is true increase number of collisiosn.
            size++;
            if (isCollision) {
                numberOfCollisions++;
            }
            myHashArray[hashVal] = item;
        }
    }
    /**
     * Returns the size, number of items, of the table.
     * @return the number of items in the table
     */
    @Override
    public int size() {
        return size;
    }
    /**
     * Displays the values of the table.
     * If an index is empty, it shows **
     * If previously existed data item got deleted, then it should show #DEL#
     */
    @Override
    public void display() {
        StringBuilder strBuild = new StringBuilder();
        for (int i = 0; i < myHashArray.length; i++) {
            if (myHashArray[i] != null && myHashArray[i] != DELETED) {
                strBuild.append("[");
                strBuild.append(myHashArray[i].value);
                strBuild.append(", ");
                strBuild.append(myHashArray[i].frequency);
                strBuild.append("]");
            } else if (myHashArray[i] != null) {
                strBuild.append("#DEL#");
            } else {
                strBuild.append("**");
            }
            if (i != myHashArray.length - 1) {
                strBuild.append(" ");
            }
        }
        System.out.println(strBuild.toString());
    }
    /**
     * Returns true if value is contained in the table.
     * @param key String key value to search
     * @return true if found, false if not found.
     */
    @Override
    public boolean contains(String key) {
        // generate the key.
        int hashVal = hashValue(key);
        // loop till we find the key or end of array.
        while (myHashArray[hashVal] != null) {
            if (myHashArray[hashVal].value.equals(key)) {
                return true;
            }
            hashVal++;
            hashVal = hashVal % myHashArray.length;
        }
        return false; // cannot find the key
    }
    /**
     * Returns the number of collisions in relation to insert and rehash.
     * When rehashing process happens, the number of collisions should be properly updated.
     *
     * The definition of collision is "two different keys map to the same hash value."
     * @return number of collisions
     */
    @Override
    public int numOfCollisions() {
        return numberOfCollisions;
    }
    /**
     * Returns the hash value of a String.
     * @param value value for which the hash value should be calculated
     * @return int hash value of a String
     */
    @Override
    public int hashValue(String value) {
        return myHashFunc(value);
    }
    /**
     * Returns the frequency of a key String.
     * @param key string value to find its frequency
     * @return frequency value if found. If not found, return 0
     */
    @Override
    public int showFrequency(String key) {
        if (contains(key)) {
            int hashVal = hashValue(key);
            return myHashArray[hashVal].frequency;
        }
        return 0; // cannot find the key
    }
    /**
     * Removes and returns removed value.
     * @param key String to remove
     * @return value that is removed
     */
    @Override
    public String remove(String key) {
        int hashVal = hashValue(key);
        while (myHashArray[hashVal] != null) {
            if (myHashArray[hashVal].value.equals(key)) {
                DataItem item = myHashArray[hashVal];
                size--;
                myHashArray[hashVal] = DELETED;
                return item.value;
            }
            hashVal++;
            hashVal = hashVal % myHashArray.length;
        }
        return "";
    }

    /**
     * private static data item nested class.
     */
    private static class DataItem {
        /**
         * String value.
         */
        private String value;
        /**
         * String value's frequency.
         */
        private int frequency;
        /**
         * Constructor for DataItem.
         * @param value value of the dataitem.
         * @param frequency total frequency of the same value.
         */
        DataItem(String value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }
    }
}
