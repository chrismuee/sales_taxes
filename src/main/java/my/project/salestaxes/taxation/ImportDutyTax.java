package my.project.salestaxes.taxation;

import java.math.BigDecimal;
import java.util.Arrays;
import my.project.salestaxes.input.ShoppingBasketItem;
import my.project.salestaxes.misc.MathHelper;
import org.apache.commons.lang3.StringUtils;

public class ImportDutyTax implements SalesTax {

  private static final double IMPORT_DUTY_TAX = 0.05;
  private static final String IDENTIFIER = "imported";


  @Override
  public boolean applicable(final ShoppingBasketItem shoppingBasketItem) {
    var descriptionTokens = shoppingBasketItem.getDescription().split(" ");
    return Arrays.stream(descriptionTokens).anyMatch(token -> StringUtils.containsIgnoreCase(token, IDENTIFIER));
  }

  @Override
  public BigDecimal calculate(final BigDecimal netPrice) {
    return MathHelper.truncate(netPrice.multiply(BigDecimal.valueOf(IMPORT_DUTY_TAX)));
  }
}


