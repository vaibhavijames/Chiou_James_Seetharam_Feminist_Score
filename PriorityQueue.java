package javafoundations;
import javafoundations.exceptions.*;
/**
 * A data structure that works like a queue, but instead of
 * FIFO, always dequeues the item with the highest priority.
 * Uses a maxheap to store and sort items.
 *
 * @author Akshaya Seetharam, Vaibhavi James, Hannah Chiou
 * @version 15 Dec 2022
 */
public class PriorityQueue<T extends Comparable<T>> implements Queue<T>

{
    // instance variables
    private LinkedMaxHeap<T> heap;

    /**
     * Constructor for objects of class PriorityQueue
     */
    public PriorityQueue()
    {
    heap = new LinkedMaxHeap<T>();
    }
    
    /**
     * Returns the element at the front of the queue 
     * 
     * @return the firt element in the queue
     */
    public T first(){
    return heap.getMax();
    }
    
    /**
     * Returns true if the queue contains no elements and false otherwise.
     * 
     * @return true, false depending on if the queue isEmpty
     */
    public boolean isEmpty()
    {
    return heap.isEmpty();
    }
    
    /**
     * Returns the size of the queue
     * 
     * @return the size of the queue
     */
    public int size(){
    return heap.size();
    }
    
    /**
     * Returns a string representation of the queue
     * 
     * @return A string representation of the queue
     */
    public String toString() {
    return heap.toString();
    }
    
    /**
     * Add the element to the queue
     * 
     * @param element The element to be added to the queue
     */
    public void enqueue(T element){
    heap.add(element);
    }
    
    /**
     * Returns the element at the front of the queue
     * 
     * @return element at the front of the queue
     */
    public T dequeue(){
    try {
    T temp = heap.removeMax();
    return temp;
    } catch(EmptyCollectionException ece) {
    System.out.println(ece);
    }
    return null;
    }
}
