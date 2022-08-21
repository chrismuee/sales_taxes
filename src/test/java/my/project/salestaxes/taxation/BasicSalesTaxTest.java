package my.project.salestaxes.taxation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import my.project.salestaxes.input.ShoppingBasketItem;
import org.junit.jupiter.api.Test;

class BasicSalesTaxTest {

  @Test
  void applicable_False() {
    var given =
        ShoppingBasketItem.builder().quantity(1).description("chocolate bar").netPrice(new BigDecimal("0.85")).build();
    var actual = TaxFactory.BASIC_SALES_TAX.createInstance().applicable(given);
    var expected = false;
    assertEquals(expected, actual);
  }

  @Test
  void applicable_True() {
    var given =
        ShoppingBasketItem.builder().quantity(1).description("something").netPrice(new BigDecimal("0.85")).build();
    var actual = TaxFactory.BASIC_SALES_TAX.createInstance().applicable(given);
    var expected = true;
    assertEquals(expected, actual);
  }

  @Test
  void calculate() {
    var given = new BigDecimal("1.00");
    var actual = TaxFactory.BASIC_SALES_TAX.createInstance().calculate(given);
    var expected = new BigDecimal("0.10");
    assertEquals(expected, actual);
  }
}
