package patterns;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Enum that represents different types of coins.
 * 
 * @author julian
 *
 */
public enum Coins {
    //---------------------------------------------------------------------------------------------

//    /** The one cent coin. */
//    ONE_CENT(1),
//    
//    /** The two cents coin. */
//    TWO_CENTS(2),
//    
//    /** The five cents coin. */
//    FIVE_CENTS(5),
//    
//    /** The ten cents coin. */
//    TEN_CENTS(10),
//
//    /** The twenty cents coin. */
//    TWENTY_CENTS(20),
//    
//    /** The fifth cents coin. */
//    FIFTHY_CENTS(50);

    /** The one cent coin. */
    ONE_CENT(1),

    /** The five cents coin. */
    FIVE_CENTS(5),
    
    /** The eleven cents coin. */
    ELEVEN_CENTS(11);

    //---------------------------------------------------------------------------------------------

    /** The value of the coin. */
    private final Integer coinValue;
    
    //---------------------------------------------------------------------------------------------

    /**
     * Gets the coin value of the respective coin.
     * 
     * @return The stated value
     */
    public Integer getCoinValue() {
        return coinValue;
    }
    
    //---------------------------------------------------------------------------------------------

    /**
     * Get the values of the coins as a list. The list is ordered descending.
     * 
     * @return The stated list
     */
    public static Set<Integer> getCoinValues() {
        Set<Integer> coinValues = new TreeSet<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(final Integer value1, final Integer value2) {
                if (value1.equals(value2)) {
                    return 0;
                } else if (value1 < value2) {
                    return 1;
                }

                return -1;
            };
        });
        for (Coins coin : values()) {
            coinValues.add(coin.getCoinValue());
        }
        
        return coinValues;
    }
    
    //---------------------------------------------------------------------------------------------

    /**
     * Constructor that sets a value to the respected coin.
     * 
     * @param value The stated value
     */
    Coins(final int value) {
        coinValue = value;
    }

    //---------------------------------------------------------------------------------------------
}
