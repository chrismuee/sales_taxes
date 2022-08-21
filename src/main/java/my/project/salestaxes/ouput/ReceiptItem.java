package my.project.salestaxes.ouput;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import my.project.salestaxes.input.ShoppingBasketItem;
import my.project.salestaxes.misc.MathHelper;
import my.project.salestaxes.taxation.SalesTax;
import my.project.salestaxes.taxation.TaxFactory;
import org.apache.commons.lang3.StringUtils;

@Builder
@Getter
@EqualsAndHashCode
public class ReceiptItem {

  private static final SalesTax BASIC_SALES_SALES_TAX = TaxFactory.BASIC_SALES_TAX.createInstance();
  private static final SalesTax IMPORT_DUTY_SALES_TAX = TaxFactory.IMPORT_DUTY_TAX.createInstance();

  private static final String IMPORTED = "imported";

  private final String description;
  private final int quantity;
  private final BigDecimal salesTax;
  private final BigDecimal grossPrice;


  public static ReceiptItem createReceiptItem(final ShoppingBasketItem shoppingBasketItem) {
    var salesTax = calculateSalesTax(shoppingBasketItem);
    var grossPrice = calculateGrossPrice(shoppingBasketItem.getNetPrice(), salesTax);
    var description = curateDescription(shoppingBasketItem);
    var quantity = shoppingBasketItem.getQuantity();

    return ReceiptItem.builder().description(description).quantity(quantity).salesTax(salesTax).grossPrice(grossPrice)
        .build();
  }

  private static BigDecimal calculateSalesTax(final ShoppingBasketItem shoppingBasketItem) {
    BigDecimal tax = BigDecimal.ZERO;

    if (BASIC_SALES_SALES_TAX.applicable(shoppingBasketItem)) {
      tax = tax.add(BASIC_SALES_SALES_TAX.calculate(shoppingBasketItem.getNetPrice()));
    }

    if (IMPORT_DUTY_SALES_TAX.applicable(shoppingBasketItem)) {
      tax = tax.add(IMPORT_DUTY_SALES_TAX.calculate(shoppingBasketItem.getNetPrice()));
    }

    return MathHelper.roundSalesTax(tax);
  }

  private static BigDecimal calculateGrossPrice(final BigDecimal netPrice, final BigDecimal salesTax) {
    return netPrice.add(salesTax);
  }

  private static String curateDescription(final ShoppingBasketItem shoppingBasketItem) {
    if (IMPORT_DUTY_SALES_TAX.applicable(shoppingBasketItem)) {
      var descriptionTokens = shoppingBasketItem.getDescription().split(" ");
      var result =
          Arrays.stream(descriptionTokens).filter(token -> !StringUtils.containsIgnoreCase(token,
              IMPORTED)).collect(Collectors.toList());
      result.add(0, IMPORTED);
      return String.join(" ", result);
    }

    return shoppingBasketItem.getDescription();

  }
}
