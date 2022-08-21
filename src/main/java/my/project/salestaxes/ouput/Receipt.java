package my.project.salestaxes.ouput;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import my.project.salestaxes.misc.MathHelper;

@Builder
@Getter
@EqualsAndHashCode
public class Receipt {

  private final List<ReceiptItem> receiptItems;
  private final BigDecimal totalSalesTax;
  private final BigDecimal totalCost;

  public static Receipt createReceipt(final List<ReceiptItem> receiptItems) {
    var totalSalesCost = calculateTotalSalesTax(receiptItems);
    var totalCost = calculateTotalCost(receiptItems);
    return Receipt.builder().receiptItems(receiptItems).totalSalesTax(totalSalesCost).totalCost(totalCost).build();
  }

  private static BigDecimal calculateTotalSalesTax(final List<ReceiptItem> receiptItems) {
    BigDecimal totalSalesTax =
        receiptItems.stream().map(ReceiptItem::getSalesTax).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    return MathHelper.truncate(totalSalesTax);
  }

  private static BigDecimal calculateTotalCost(final List<ReceiptItem> receiptItems) {
    BigDecimal totalCost =
        receiptItems.stream().map(ReceiptItem::getGrossPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    return MathHelper.truncate(totalCost);
  }
}
