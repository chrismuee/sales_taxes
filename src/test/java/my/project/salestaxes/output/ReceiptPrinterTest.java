package my.project.salestaxes.output;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import my.project.salestaxes.ouput.Receipt;
import my.project.salestaxes.ouput.ReceiptItem;
import my.project.salestaxes.ouput.ReceiptPrinter;
import org.junit.jupiter.api.Test;

class ReceiptPrinterTest {

  @Test
  void print() {
    var givenReceiptItems = List.of(
        ReceiptItem.builder().quantity(1).description("ps5").salesTax(new BigDecimal("40.00"))
            .grossPrice(new BigDecimal("440.00")).build(),
        ReceiptItem.builder().quantity(1).description("chocolate").salesTax(new BigDecimal("0.10"))
            .grossPrice(new BigDecimal("1.10")).build()
    );
    var givenReceipt =
        Receipt.builder().receiptItems(givenReceiptItems).totalSalesTax(new BigDecimal("40.10"))
            .totalCost(new BigDecimal("441" +
                ".10")).build();

    var actual = (new ReceiptPrinter()).print(givenReceipt);
    var expected = List.of(
        "1 ps5: 440.00", "1 chocolate: 1.10", "Sales Taxes: 40.10", "Total: 441.10"
    );

    assertEquals(expected, actual);
  }
}
