/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Realization of my own set-implementation of a <code>HashSet</code>, which resolve collisions
 * by linking.
 * 
 * @author julian
 * 
 * @param <E> The type of the data
 */
public final class LinkedOverflowHashSet<E> implements Set<E> {
    //---------------------------------------------------------------------------------------------
    
    /** Specifies the factor by which the table is enlarged. */
    private static final int REHASH_MULTIPLIER = 2;

    /** The maximum load factor of the hash table. */
    private static final double DEFAULT_LOAD_FACTOR = 0.8;
    
    //---------------------------------------------------------------------------------------------

    /**
     * For testing purposes only.
     * 
     * @param args test
     */
    public static void main(final String[] args) {
        LinkedOverflowHashSet<String> numbers = new LinkedOverflowHashSet<>(53);

//        // Test adding and removing
//        for (Integer i = 0; i < 40; i++) {
//            numbers.add(i.toString());
//            System.out.println(numbers.size);
//        }
//        for (Integer i = 0; i < 40; i++) {
//            numbers.remove(i.toString());
//            System.out.println(numbers.size);
//        }      

//        // Test rehashing
        for (Integer i = 0; i < 90; i++) {
            numbers.add(i.toString());
            System.out.println(numbers.size);
        }
    }

    //---------------------------------------------------------------------------------------------

    /**
     * Get a capacity which is a prime and has a certain distance to the nearest power of two. Note
     * that the real capacity can vary and may be greater or smaller than the given amount.
     * 
     * @param initialCapacity The stated capacity
     * @return A capacity which is a prime that is not near to the surrounding powers of two
     */
    private static Integer getCapacity(final Integer initialCapacity) {
        if (initialCapacity == null || initialCapacity == 0) { return 0; }
        
        Integer adaptedInitialCapacity = (int) (initialCapacity * (1.0 / DEFAULT_LOAD_FACTOR));
                
        Integer capacity = 0;
        if (adaptedInitialCapacity < 52) {
            capacity = 53;
        } else {
            Integer lowerPowerOfTwo = Integer.highestOneBit(adaptedInitialCapacity);
            Integer upperPowerOfTwo = lowerPowerOfTwo << 1;
                    
            List<Integer> primes = calcPrimeNumbers(lowerPowerOfTwo, upperPowerOfTwo);        
            capacity = primes.get(Math.round(primes.size() / 2));
        }
                
        return capacity;
    }

    //---------------------------------------------------------------------------------------------

    /**
     * Calculate all prime numbers till the upper bound, removes the ones that are below the lower
     * bound and returns the result as a list. The method is an implementation of the sieve of
     * eratosthenes algorithm and it should has a time complexity of O(n^2).
     * 
     * @param lowerBound The number to calculate
     * @param upperBound The number to calculate
     * @return A list of prime numbers
     */
    private static List<Integer> calcPrimeNumbers(final int lowerBound, final int upperBound) {
        boolean[] isPrimeNumber = new boolean[upperBound + 1];
        Arrays.fill(isPrimeNumber, true);

        List<Integer> primes = new ArrayList<Integer>();
        for (int i = 2; i < upperBound; i++) { 
            if (isPrimeNumber[i]) {
                primes.add(i);
                
                // marks the multiples of i as non-prime number
                for (int j = i; j * i <= upperBound; j++) {
                    isPrimeNumber[i * j] = false;
                }
            }
        }
        primes.removeIf((prime) -> prime < lowerBound);
        
        return primes;
    }

    //=============================================================================================

    /** The current number of elements in this set. */
    private int size;
    
    /** The capacity of this set. */
    private int capacity;
    
    /** The buckets of the set. */
    private LinkedList<E>[] buckets; 
    
    //---------------------------------------------------------------------------------------------

    /**
     * Create a new <code>LinkedOverflowHashset</code> with the given initialCapacity. Note that
     * the real capacity might be slightly differ for performance- or memory optimization reasons.
     * 
     * @param initialCapacity
     */
    public LinkedOverflowHashSet(final int initialCapacity) {
        capacity = getCapacity(initialCapacity);

        @SuppressWarnings({"unchecked"})
        LinkedList<E>[] newBuckets = (LinkedList<E>[]) new LinkedList[capacity];
        Arrays.fill(newBuckets, new LinkedList<E>());
        
        buckets = newBuckets;
    }
    
    //---------------------------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E data) {        
        if (data == null) { return false; }

        int key = data.hashCode();
        List<E> list = buckets[getPosition(key, capacity)];
        
        boolean isElementInList = list.contains(data); 
        if (isElementInList) { return false; }
                
        list.add(data);
        size++;

        if (isLoadFactorExceeded()) { rehash(); }
        
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(E data) {
        if (data == null) { return false; }

        int key = data.hashCode();
        List<E> list = buckets[getPosition(key, capacity)];

        return list.contains(data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(E data) {
        if (data == null) { return false; }

        int key = data.hashCode();
        List<E> list = buckets[getPosition(key, capacity)];
                        
        boolean isElementRemoved = list.remove(data);
        if (isElementRemoved) { size--; }        

        return isElementRemoved;    
    }

    //---------------------------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() { return size; }
    
    //---------------------------------------------------------------------------------------------
    
    /**
     * Get the position for the given key.
     * 
     * @param key The stated key
     * @return The position
     */
    private int getPosition(final int key, final int capacity) { return key % capacity; }

    //---------------------------------------------------------------------------------------------
    
    /**
     * Returns whether the load factor is exceeded.
     * 
     * @return Whether the load factor is exceeded
     */
    private boolean isLoadFactorExceeded() {
        double usedSpace = (size / (double) capacity);

        System.out.println("Capacity is: " + capacity);
        System.out.println("Size is: " + size);
        System.out.println("Checked used space, it is: " + usedSpace);
        
        if (DEFAULT_LOAD_FACTOR <= usedSpace) { 
            System.out.println("The load factor is exceeded");
            
            return true; 
        }
        
        return false;
    }
    
    //---------------------------------------------------------------------------------------------

    /**
     * Doubles the capacity of the set.
     */
    private void rehash() {  
        System.out.println("Rehashing started");
        int newCapacity = REHASH_MULTIPLIER * capacity; 
        // TODO Check whether this is still a prime?
        
        @SuppressWarnings({"unchecked"})
        LinkedList<E>[] newBuckets = 
            (LinkedList<E>[]) new LinkedList[newCapacity];
        Arrays.fill(newBuckets, new LinkedList<E>());

        LinkedList<E>[] oldBuckets = buckets;
        int key, position; List<E> newBucket;
        for (List<E> bucket : oldBuckets) {
            for (E element : bucket) {
                key = element.hashCode();
                position = getPosition(key, newCapacity);
                
                System.out.println("Element: " + element);
                System.out.println("Elements key: " + key);
                System.out.println("Old position: " + getPosition(key, capacity));
                System.out.println("New position: " + getPosition(key, newCapacity));
                
                newBucket = newBuckets[position];
                newBucket.add(element);
            }
        }
                
        capacity = newCapacity;
        buckets = newBuckets;
        
        System.out.println("Rehashing done, new capacity is: " + buckets.length);
    }

    //---------------------------------------------------------------------------------------------
}
