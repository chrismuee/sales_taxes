package my.project.salestaxes.output;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import my.project.salestaxes.ouput.Receipt;
import my.project.salestaxes.ouput.ReceiptItem;
import org.junit.jupiter.api.Test;

class ReceiptTest {

  @Test
  void createReceipt() {
    var given = List.of(
        ReceiptItem.builder().quantity(1).description("ps5").salesTax(new BigDecimal("40.00"))
            .grossPrice(new BigDecimal("440.00")).build(),
        ReceiptItem.builder().quantity(1).description("chocolate").salesTax(new BigDecimal("0.10"))
            .grossPrice(new BigDecimal("1.10")).build()
    );
    var actual = Receipt.createReceipt(given);
    var expected =
        Receipt.builder().receiptItems(given).totalSalesTax(new BigDecimal("40.10")).totalCost(new BigDecimal("441" +
            ".10")).build();

    assertEquals(expected, actual);
  }
}
