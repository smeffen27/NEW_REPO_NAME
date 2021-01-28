/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package lists;

import java.util.ListIterator;

/**
 * Class for testing my list implementations.
 * 
 * @author julian
 */
public final class ListTester {
    //----------------------------------------------------------------------------------------------

    /**
     * Test the lists.
     * 
     * @param args The arguments
     */
    public static void main(final String[] args) {
        List<Integer> list = new DoublyLinkedList<>();
        //List<Integer> list = new SinglyLinkedList<>();

        list.add(0, 2_000_000);
        list.remove(0);
        
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        
        System.out.println(list.toString());
        System.out.println("The size is: " + list.size());
        
        list.add(0, 2_000_000);

        System.out.println(list.toString());
        System.out.println("The size is: " + list.size());

        list.add(5, 4_000_000);

        System.out.println(list.toString());
        System.out.println("The size is: " + list.size());
   
        System.out.println("The element is: " + list.get(5));
        
        list.remove(0);
        System.out.println(list.toString());
        System.out.println("The size is: " + list.size());

        list.remove(4);
        System.out.println(list.toString());
        System.out.println("The size is: " + list.size());

        list.remove(9);
        System.out.println(list.toString());
        System.out.println("The size is: " + list.size());

        list.remove(3);
        System.out.println(list.toString());
        System.out.println("The size is: " + list.size());
        
        list.add(7, 4_000_000);
        System.out.println(list.toString());
        System.out.println("The size is: " + list.size());
        
        ListIterator<Integer> it = list.listIterator();
        
        System.out.println("ascending iteration");
        System.out.println();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        
        System.out.println("descending iteration");
        System.out.println();
        while (it.hasPrevious()) {
            System.out.println(it.previous());
            
        }
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Hide constructor of utility class.
     */
    private ListTester() { }
    
    //----------------------------------------------------------------------------------------------
}
