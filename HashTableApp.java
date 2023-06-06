// implements hash table with linear probing

import java.io.*;
import java.util.Scanner;

//DataItem Class
class DataItem{
    private int iData; // data item (key)

    public DataItem(int ii){
    //constructor
        this.iData = ii;
    }

    public int getKey() // getter
    {
        return this.iData;
    }

} // end class DataItem

//HashTable class
class HashTable{

    private DataItem[] hashArray; // array holds hash table
    private int arraySize;
    private int size;
    private double loadFactor;

    //Hashtable construction
    public HashTable(int size){
        //constructor
        this.arraySize = size;
        this.hashArray = new DataItem[this.arraySize];
        this.size = 0;
        this.loadFactor = 0;
    }

    //copy DataItem method
    private DataItem[] copy(){
        DataItem[] copied = new DataItem[arraySize];
        for (int i = 0; i < arraySize; i++){
            copied[i] = hashArray[i];
        }
        return copied;
    }

    //Load factor checker
    public void refreshLF(){
        loadFactor = (double) size / arraySize;
        if (loadFactor >= 0.75) {
            System.out.printf("Load Factor limit exceeded | LF = %.2f%n", loadFactor);
            rehash();
        }
    }

    //Rehash method
    private void rehash(){
        System.out.println("Rehashing");
        DataItem[] temp = copy();
        arraySize *= 2;
        hashArray = new DataItem[arraySize];
        size = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null) {
                insert(temp[i]);
            }
        }
        System.out.printf("Rehashed | new capacity: %d%n", arraySize);
    }

    //displayTable method
    public void displayTable(){
        // displays hash table
        System.out.print("Table: ");
        for (int i = 0; i < arraySize; i++) {
            if (hashArray[i] != null) {
                System.out.print(hashArray[i].getKey() + " ");
            } else {
                System.out.print("** ");
            }
        }
        System.out.println();
    }

    //hashfunc method using key
    public int hashFunc(int key, int i){
    // hash function

        // to complete with a hash function of your choice
        return (key + i) % this.arraySize;
    }

    //insert method
    public void insert(DataItem item){
    // insert a DataItem
        int i = 0;
        int j;
        do {
            j = hashFunc(item.getKey(), i);
            if (hashArray[j] == null) {
                hashArray[j] = item;
                size++;
                break;
            } else {
                i = i + 1;
            }
        } while (i != arraySize);
    } // end insert()

    //DataItem delete class
    public DataItem delete(int key){

    // delete a DataItem
    
        int i = 0;
        int j;
        do {
            j = hashFunc(key, i);
            if (hashArray[j] != null && hashArray[j].getKey() == key) {
                hashArray[j] = null;
                size--;
                break;
            }
            i = i + 1;
        } while (hashArray[j] == null && i != arraySize);
        return null;
    } // end delete()

    //find method 
    public DataItem find(int key) // find item with key
    {
        int i = 0;
        int j;
        do {
            j = hashFunc(key, i);
            if (hashArray[j] != null && hashArray[j].getKey() == key) {
                return hashArray[j];
            }
            i = i + 1;
        } while (hashArray[j] != null && i != arraySize);
        return null;
    }

} // end class HashTable

class HashTableApp{
    static Scanner s;
    static char choice;
    static HashTable ht;
    static int key;
    static final int MIN_ARRAY_SIZE = 17;

    //main method
    public static void main(String[] args) throws IOException {
        s = new Scanner(System.in);
        choice = Character.MIN_VALUE;
        key = 0;
        ht = new HashTable(MIN_ARRAY_SIZE);
        DataItem aDataItem;
        while (true) // interact with user
        {
            ht.refreshLF();
            System.out.print("Enter first letter of ");
            System.out.print("show, insert, delete, or find: ");

            choice = s.next().charAt(0);

            switch (choice) {
                case 's':
                    ht.displayTable();
                    break;

                //insert method case
                case 'i':
                    System.out.print("Enter key value to insert: ");
                    key = s.nextInt();
                    aDataItem = new DataItem(key);
                    ht.insert(aDataItem);
                    break;
                //delete method case
                case 'd':
                    System.out.print("Enter key value to delete: ");
                    key = s.nextInt();
                    ht.delete(key);
                    break;

                //find case
                case 'f':
                    System.out.print("Enter key value to find: ");
                    key = s.nextInt();
                    aDataItem = ht.find(key);
                    if (aDataItem != null) {
                        System.out.printf("Found %d%n", aDataItem.getKey());
                    } else {
                        System.out.printf("Could not find %d%n", key);
                    }
                    break;

                //invalid case 
                default:
                    System.out.print("Invalid entry\n");

            } // end switch

        } // end while

    } // end main()

} // end class HashTableApp