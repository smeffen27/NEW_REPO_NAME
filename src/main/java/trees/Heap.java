package trees;

import java.util.NoSuchElementException;

/**
 * An implementation of an min heap.
 * 
 * @author Julian
 *
 * @param <T> The type of elements the heap contains
 */
public class Heap<T extends Comparable<? super T>> { 
    //---------------------------------------------------------------------------------------------

    /** The position of the root element in the array. */
    private static final int ROOT = 0; 

    //=============================================================================================

    /** The array that contains the nodes of the heap. */
    private T[] heap;
    
    /** The current amount of elements in the heap. */
    private int size; 

    //---------------------------------------------------------------------------------------------

    /**
     * Creates a heap with the given elements.
     * 
     * @param elements The elements to add to the heap.
     */
    @SuppressWarnings("unchecked")
    public Heap(final T ... elements) { 
        size = elements.length;
        
        heap = (T[]) new Comparable<?>[size + 1];
        System.arraycopy(elements, 0, heap, 0, size);
        
        buildHeap();
    } 

    //---------------------------------------------------------------------------------------------

    /**
     * Sort the internal structure into a min heap structure.
     */
    public void buildHeap() {
        int startPosition = ((size / 2) - 1);
        for (int position = startPosition; position >= 0; position--) {
            minHeapify(position);
        }
    }

    //---------------------------------------------------------------------------------------------

    /**
     * Removes the minimum element from the heap, that means the element which is the current root.
     * 
     * @return The removed element
     */
    public T remove() { 
        if (size == 0) throw new NoSuchElementException("Heap is empty!");
        
        T minElement = heap[ROOT]; 
        heap[ROOT] = heap[size - 1];
        heap[size - 1] = null;
        
        minHeapify(ROOT); 
        size--;
        
        return minElement; 
    }  
    
    //---------------------------------------------------------------------------------------------

    /**
     * Print the content of the heap to the console.
     */
    public void print() {
        if (size > 1) {
            for (int i = 0; i < size / 2; i++) {
                System.out.println(" PARENT : " + heap[i] 
                        + " LEFT CHILD : " + heap[getLeftChildPos(i)]
                        + " RIGHT CHILD :" + heap[getRightChildPos(i)]);
            }
        } 
        else if (size == 1) { System.out.println(" PARENT : " + heap[0]); } 
        else { System.out.println(" Tree empty "); }
        System.out.println();
    }

    //---------------------------------------------------------------------------------------------

    /** 
     * Heapify the node at the given position.
     * 
     * @param position The stated position
     */
    private void minHeapify(final int position) { 
        if (!isLeaf(position)) {
            int leftChildPos = getLeftChildPos(position);
            int rightChildPos = getRightChildPos(position);
            
            T heapifiedNode = heap[position];
            T leftChild = heap[leftChildPos];
            boolean isThereARightChild = rightChildPos < size;
            T rightChild = (isThereARightChild ? heap[rightChildPos] : null);

            boolean isLeftChildNotNullAndSmaller = (leftChild != null) && 
                    (heapifiedNode.compareTo(leftChild) > 0);
            boolean isRightChildNotNullAndSmaller = (rightChild != null) && 
                    (heapifiedNode.compareTo(rightChild) > 0);
                    
            if (isLeftChildNotNullAndSmaller || isRightChildNotNullAndSmaller) {
                int childPosition = ((rightChild == null) || (leftChild.compareTo(rightChild) < 0)) 
                        ? leftChildPos : rightChildPos;

                swap(position, childPosition); 
                minHeapify(childPosition); // We continue to leak the old root element
            } 
        } 
    } 
    
    //---------------------------------------------------------------------------------------------
    
    /**
     * Returns the position of the parent for the node currently at position.
     * 
     * @param position The stated child node position
     * @return The stated parent node position
     */
    @SuppressWarnings("unused")
    private int parent(final int position) { return (position - 1) / 2; } 

    /**
     * Returns the position of the left child of the node currently at the given position.
     * 
     * @param position The parent node position
     * @return The stated left child node position
     */
    private int getLeftChildPos(final int position) { return (2 * position) + 1 ; } 
    
    /**
     * Returns the position of the right child of the node currently at the given position.
     * 
     * @param position The parent node position
     * @return The stated right child node position
     */
    private int getRightChildPos(final int position) { return (2 * position) + 2; } 

    /**
     * Get whether the node at the given position is a leaf.
     * 
     * @param The stated position of the node
     * @return Whether this is a leaf or not
     */
    private boolean isLeaf(final int position) { 
        if (position >= (size / 2) && position <= size) { return true; } 
        
        return false; 
    }

    /**
     * Swap the two nodes at the given positions.
     * 
     * @param firstPosition The position of the first node
     * @param secondPosition The position of the second node
     */
    private void swap(final int firstPosition,final int secondPosition) { 
        T tmp = heap[firstPosition]; 
        
        heap[firstPosition] = heap[secondPosition]; 
        heap[secondPosition] = tmp; 
    } 

    //---------------------------------------------------------------------------------------------
} 