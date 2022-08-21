package my.project.salestaxes.output;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import my.project.salestaxes.input.ShoppingBasketItem;
import my.project.salestaxes.ouput.ReceiptItem;
import org.junit.jupiter.api.Test;

class ReceiptItemTest {

  @Test
  void createReceiptItemTest_NoTaxes() {
    var given =
        ShoppingBasketItem.builder().quantity(1).description("chocolate").netPrice(new BigDecimal("1.5")).build();
    var actual = ReceiptItem.createReceiptItem(given);
    var expected =
        ReceiptItem.builder().quantity(1).description("chocolate").salesTax(new BigDecimal("0.00"))
            .grossPrice(new BigDecimal("1.50")).build();
    assertEquals(expected, actual);
  }

  @Test
  void createReceiptItemTest_OnlyBasicSalesTax() {
    var given =
        ShoppingBasketItem.builder().quantity(1).description("ps5").netPrice(new BigDecimal("400.00")).build();
    var actual = ReceiptItem.createReceiptItem(given);
    var expected =
        ReceiptItem.builder().quantity(1).description("ps5").salesTax(new BigDecimal("40.00"))
            .grossPrice(new BigDecimal("440.00")).build();
    assertEquals(expected, actual);
  }

  @Test
  void createReceiptItemTest_OnlyImportDutyTax() {
    var given =
        ShoppingBasketItem.builder().quantity(1).description("imported chocolate").netPrice(new BigDecimal("1.00"))
            .build();
    var actual = ReceiptItem.createReceiptItem(given);
    var expected =
        ReceiptItem.builder().quantity(1).description("imported chocolate").salesTax(new BigDecimal("0.05"))
            .grossPrice(new BigDecimal("1.05")).build();
    assertEquals(expected, actual);
  }

  @Test
  void createReceiptItemTest_BasicAndImportTax() {
    var given =
        ShoppingBasketItem.builder().quantity(1).description("imported ps5").netPrice(new BigDecimal("400.00")).build();
    var actual = ReceiptItem.createReceiptItem(given);
    var expected =
        ReceiptItem.builder().quantity(1).description("imported ps5").salesTax(new BigDecimal("60.00"))
            .grossPrice(new BigDecimal("460.00")).build();
    assertEquals(expected, actual);
  }

  @Test
  void createReceiptItemTest_CuratedDescription() {
    var given =
        ShoppingBasketItem.builder().quantity(1).description("ps5 imported with controller").netPrice(new BigDecimal(
            "500.00")).build();
    var actual = ReceiptItem.createReceiptItem(given);
    var expected =
        ReceiptItem.builder().quantity(1).description("imported ps5 with controller").salesTax(new BigDecimal("75.00"))
            .grossPrice(new BigDecimal("575.00")).build();
    assertEquals(expected, actual);
  }
}
