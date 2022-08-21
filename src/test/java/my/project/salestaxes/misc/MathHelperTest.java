package my.project.salestaxes.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class MathHelperTest {

  @Test
  void roundSalesTax() {
    var given = new BigDecimal("1.1235");
    var actual = MathHelper.roundSalesTax(given);
    var expected = new BigDecimal("1.15");
    assertEquals(expected, actual);
  }

  @Test
  void truncate() {
    var given = new BigDecimal("1.1235");
    var actual = MathHelper.truncate(given);
    var expected = new BigDecimal("1.12");
    assertEquals(expected, actual);
  }
}
