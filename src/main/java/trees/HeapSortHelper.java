/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements a heap sort algorithm.
 * 
 * @author Julian
 *
 */
public class HeapSortHelper {
    //---------------------------------------------------------------------------------------------

    /**
     * Only for testing purposes.
     * 
     * @param args The arguments
     */
    @SuppressWarnings("serial")
    public static void main(String[] args) { 
        List<Integer> list = new ArrayList<Integer>() {
            {
                add(1);
                add(88);
                add(99);
                add(54);
                add(24);
                add(200);
            }
        };
        
        StringBuilder sb = new StringBuilder();
        list.forEach((element) -> { sb.append(element + ", "); });
        sb.setLength(sb.length() - 2);
        System.out.println(sb.toString() + "\n");
        
        sort(list);
        
        sb.setLength(0);
        list.forEach((element) -> { sb.append(element + ", "); });
        sb.setLength(sb.length() - 2);
        System.out.println(sb.toString() + "\n");
    }

    //---------------------------------------------------------------------------------------------

    /**
     * Sort the given list via a heap sort algorithm. The elements of the given list need to
     * implement the comparable type.
     * 
     * @param <T> The type that needs to implement the comparable type
     * @param list The list to be sorted
     */
    public static <T extends Comparable<? super T>> void sort(final List<T> list) {       
        int size = list.size();        
        
        @SuppressWarnings("unchecked")
        T[] elements = (T[]) new Comparable[size];
        elements = list.toArray(elements);
        
        Heap<T> heap = new Heap<T>(elements);
//        heap.print();
        
        list.clear();
        while (size > 0) {
            list.add(heap.remove());
            size--;        

//          heap.print();
        }
    }

    //---------------------------------------------------------------------------------------------
}
