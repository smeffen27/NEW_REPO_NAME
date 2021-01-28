package search;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for several runtime tests.
 * 
 * @author julian
 */
public final class RuntimeHelper {
    //----------------------------------------------------------------------------------------------

    /** The number of executions. */
    private static final int NUMBER_OF_EXECUTIONS = 2000;

    /** Two million. */
    private static final int TWO_MILLION = 2_000_000;

    /** four million. */
    private static final int FOUR_MILLION = 4_000_000;

    /** Six million. */
    private static final int SIX_MILLION = 6_000_000;

    /** Eight million. */
    private static final int EIGHT_MILLION = 8_000_000;

    /** Ten million. */
    private static final int TEN_MILLION = 10_000_000;

    //----------------------------------------------------------------------------------------------

    /**
     * Will execute the following operations: insert the elements in the
     * list, perform a binary search and perform a linear search.
     * <p>
     * The tests will be executed for various amounts of elements and the runtime will be written to
     * several files.
     * <p>
     * For both searches the searched element is not in the list, that means we will
     * print the worst case runtime.
     * 
     * @param args The arguments
     */
    public static void main(final String[] args) {
        List<Integer> list = new ArrayList<>();
        printDurationsToFiles(list, "array_list");

        list = new LinkedList<>();
        printDurationsToFiles(list, "linked_list");
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Prints the durations for the given list and various amount of elements to several files.
     * 
     * @param list The list to test
     * @param fileAppender The appender for the name of the files
     */
    private static void printDurationsToFiles(final List<Integer> list, final String fileAppender) {
        insertElements(list, TWO_MILLION);
        printLinearSearchToFile(list, fileAppender, TWO_MILLION);
        printBinarySearchToFile(list, fileAppender, TWO_MILLION);
        list.clear();

        System.out.println("Printed files for " + fileAppender + " with " + TWO_MILLION);
        
        insertElements(list, FOUR_MILLION);
        printLinearSearchToFile(list, fileAppender, FOUR_MILLION);
        printBinarySearchToFile(list, fileAppender, FOUR_MILLION);
        list.clear();

        System.out.println("Printed files for " + fileAppender + " with " + FOUR_MILLION);

        insertElements(list, SIX_MILLION);
        printLinearSearchToFile(list, fileAppender, SIX_MILLION);
        printBinarySearchToFile(list, fileAppender, SIX_MILLION);
        list.clear();

        System.out.println("Printed files for " + fileAppender + " with " + SIX_MILLION);

        insertElements(list, EIGHT_MILLION);
        printLinearSearchToFile(list, fileAppender, EIGHT_MILLION);
        printBinarySearchToFile(list, fileAppender, EIGHT_MILLION);
        list.clear();

        System.out.println("Printed files for " + fileAppender + " with " + EIGHT_MILLION);

        insertElements(list, TEN_MILLION);
        printLinearSearchToFile(list, fileAppender, TEN_MILLION);
        printBinarySearchToFile(list, fileAppender, TEN_MILLION);
        list.clear();

        System.out.println("Printed files for " + fileAppender + " with " + TEN_MILLION);
}

    //----------------------------------------------------------------------------------------------

    /**
     * Adds the in the LIST_SIZE constant defined amount of elements to the list and
     * returns the duration it took to insert the elements in nanoseconds.
     * 
     * @param list The list to insert
     * @param listSize The amount of elements to insert
     * @return The duration it took to insert the elements in the list in nanoseconds
     */
    private static long insertElements(final List<Integer> list, final int listSize) {
        long start, end, duration;

        start = System.nanoTime();
        for (int i = 0; i < listSize; i++) {
            list.add(i);
        }
        end = System.nanoTime();

        duration = (end - start);

        return duration;
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Print the durations to a file for doing a linear search on the given list.
     * <p>
     * The appender is used to create a unique file name, should be the type of the list, e.g.
     * array_list.
     * <p>
     * The list size is used to simulate a worst case search (because there can't be an element with
     * the size of the list).
     * 
     * @param list The list on which the linear search should be executed
     * @param fileAppender The appender to create a unique file name
     * @param listSize The stated size of the list
     */
    private static void printLinearSearchToFile(final List<Integer> list, final String fileAppender,
            final int listSize) {
        List<Long> durations = new LinkedList<Long>();
        for (int i = 0; i < NUMBER_OF_EXECUTIONS; i++) {
            durations.add(doLinearSearch(list, listSize));
        }

        String fileName = "linear_search_" + fileAppender + "_" + listSize + ".txt";
        printDurationsToFile(fileName, durations);
    }

    /**
     * Print the durations to a file for doing a binary search on the given list.      
     * <p>
     * The appender is used to create a unique file name, should be the type of the list, e.g.
     * array_list.
     * <p>
     * The list size is used to simulate a worst case search (because there can't be an element with
     * the size of the list).
     * 
     * @param list The list on which the binary search should be executed
     * @param fileAppender The appender to create a unique file name
     * @param listSize The stated size of the list
     */
    private static void printBinarySearchToFile(final List<Integer> list, final String fileAppender,
            final int listSize) {
        List<Long> durations = new LinkedList<Long>();
        for (int i = 0; i < NUMBER_OF_EXECUTIONS; i++) {
            durations.add(doBinarySearch(list, listSize));
        }

        String fileName = "binary_search_" + fileAppender + "_" + listSize + ".txt";
        printDurationsToFile(fileName, durations);
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Executes the binary search on the given list.
     * 
     * @param list The list to execute the binary search
     * @param searchableElement The element to search in the list
     * @return The duration of the binary search in nanoseconds.
     */
    private static long doBinarySearch(final List<Integer> list, final int searchableElement) {
        long start, end, duration;

        start = System.nanoTime();
        Collections.binarySearch(list, searchableElement);
        end = System.nanoTime();

        duration = (end - start);

        return duration;
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Executes a linear search on the given list.
     * 
     * @param list The list to execute the linear search
     * @param searchableElement The element to search in the list
     * @return The duration of the linear search in nanoseconds.
     */
    private static long doLinearSearch(final List<Integer> list, final int searchableElement) {
        long start, end, duration;

        start = System.nanoTime();
        for (Integer integer : list) {
            if (integer == Integer.valueOf(searchableElement)) {
                break;
            }
        }
        end = System.nanoTime();

        duration = (end - start);

        return duration;
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Prints the given list of durations to the file with the given name. If the file doesn't exist
     * it'll be created.
     * 
     * @param fileName The name of the file
     * @param durations The durations to print to the file
     */
    private static void printDurationsToFile(final String fileName, final List<Long> durations) {
        try (PrintWriter out = new PrintWriter("src\\main\\resources\\search\\" + fileName)) {
            for (Long duration : durations) {
                out.println(duration);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Hide utility class constructor.
     */
    private RuntimeHelper() {
    }

    //----------------------------------------------------------------------------------------------
}
