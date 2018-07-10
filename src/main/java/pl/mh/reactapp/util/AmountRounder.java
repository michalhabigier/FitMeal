package pl.mh.reactapp.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountRounder {
    public static double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(Constants.ROUND_PLACES_AMOUNT, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
