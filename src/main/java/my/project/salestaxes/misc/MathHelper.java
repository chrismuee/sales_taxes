package my.project.salestaxes.misc;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathHelper {

  public static BigDecimal roundSalesTax(final BigDecimal salesTax) {
    var roundingFactor = new BigDecimal("20");
    var result = salesTax.multiply(roundingFactor).setScale(0, RoundingMode.UP);
    return result.divide(roundingFactor, 2, RoundingMode.DOWN);
  }

  public static BigDecimal truncate(final BigDecimal value) {
    return value.setScale(2, RoundingMode.DOWN);
  }
}
