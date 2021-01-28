/*
 * Copyright 2019 (C) Julian.
 * All Rights Reserved.
 */

package patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Sample class for a greedy algorithm.
 * 
 * @author julian
 *
 */
public final class Greedy {
    //---------------------------------------------------------------------------------------------
    
    /**
     * Run a greedy algorithm.
     * 
     * @param args The arguments
     */
    public static void main(final String[] args) {
        List<Integer> coins = getCoins(15);
        System.out.print(getFormattedCoinSet(coins));
    }
    
    //---------------------------------------------------------------------------------------------

    /**
     * Gets a list of coin values for the given value. The algorithm works with
     * greedy style means that it continuously withdraw the highest possible amount
     * from the given value till it is 0. Won't return optimal results for some coin sets.
     * 
     * @param value The value to get the coins for
     * @return The list of the stated coins
     */
    public static List<Integer> getCoins(final int value) {
        List<Integer> coins = new ArrayList<Integer>();
        
        int amount = value;
        Set<Integer> coinValues = Coins.getCoinValues();
        
        while (amount > 0) {
            for (Integer coinValue : coinValues) {
                if (amount >= coinValue) {
                    amount -= coinValue;
                    coins.add(coinValue);
                    
                    break;
                }
            }
        }
        
        return coins;
    }
    
    //---------------------------------------------------------------------------------------------

    /**
     * Get a string for the given list of coins formatted as a mathematical set.
     * 
     * @param coins The stated coins
     * @return The coins as a set
     */
    private static String getFormattedCoinSet(final List<Integer> coins) {
        StringBuilder setBuilder = new StringBuilder("{ ");
        for (Integer coin : coins) {
            setBuilder.append(coin);
            setBuilder.append(", ");
        }
        setBuilder.setLength(setBuilder.length() - 2);
        setBuilder.append(" }");
        
        return setBuilder.toString();
    }

    //---------------------------------------------------------------------------------------------

    /**
     * Hide constructor of utility class.
     */
    private Greedy() { }
    
    //---------------------------------------------------------------------------------------------
}
