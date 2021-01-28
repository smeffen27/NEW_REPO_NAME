/*
 * Copyright 2019 (C) Julian.
 * All Rights Reserved.
 */

package utils;

/**
 * Helper class for calculating potency.
 * 
 * @author Julian
 */
public final class PotencyHelper {
    // --------------------------------------------------------------------------------------------

    /**
     * Main method for testing.
     * 
     * @param args The arguments
     */
    public static void main(final String[] args) {
        System.out.println(calculatePotency(-5, 2));
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Calculates and returns the potency for the given inputs.
     * 
     * @param base     The base of the calculation
     * @param exponent The exponent of the calculation
     * @return The potency
     */
    public static int calculatePotency(final int base, final int exponent) {
        int result = 1;
        if (exponent == 0) {
            return result;
        }

        for (int i = 1; i <= exponent; i++) {
            result = result * base;
        }

        return result;
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Hide utility class constructor.
     */
    private PotencyHelper() {
    }

    // --------------------------------------------------------------------------------------------
}