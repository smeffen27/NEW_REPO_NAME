/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package hash;

/**
 * A collection that contains no duplicate elements.
 * 
 * @author julian
 *
 * @param <E> The type of elements that the set contains
 */
public interface Set<E> {
    //----------------------------------------------------------------------------------------------

    /**
     * The add method adds an element to the set. If the element is already stored in the set, the
     * element is not saved again and returns false. Otherwise the element will be saved and
     * returned true
     * 
     * @param data The data to add
     * @return Whether the operation was successful
     */
    boolean add(E data);

    //----------------------------------------------------------------------------------------------

    /**
     * The contains method checks whether the transferred element exists in the set. If so, true is
     * returned, otherwise false.
     * 
     * @param data The data to check
     * @return Whether the operation was successful
     */
    boolean contains(E data);

    //----------------------------------------------------------------------------------------------

    /**
     * The remove method removes the item from the set. If the element could be deleted, true
     * otherwise false is returned.
     * 
     * @param data The data to remove
     * @return Whether the operation was successful
     */
    boolean remove(E data);

    //----------------------------------------------------------------------------------------------

    /**
     * The current number of elements in this set.
     * 
     * @return The size of the set
     */
    int size();
    
    //----------------------------------------------------------------------------------------------
}
