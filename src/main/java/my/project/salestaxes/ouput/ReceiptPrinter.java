package my.project.salestaxes.ouput;

import java.util.List;
import java.util.stream.Collectors;

public class ReceiptPrinter {

  public List<String> print(final Receipt receipt) {
    var result = receipt.getReceiptItems().stream().map(this::receiptItemLine).collect(Collectors.toList());
    result.add("Sales Taxes: " + receipt.getTotalSalesTax());
    result.add("Total: " + receipt.getTotalCost());
    return result;
  }

  private String receiptItemLine(final ReceiptItem receiptItem) {
    return receiptItem.getQuantity() + " " + receiptItem.getDescription() + ": " + receiptItem.getGrossPrice();
  }
}
