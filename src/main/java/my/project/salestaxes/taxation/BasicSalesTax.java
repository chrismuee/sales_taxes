package my.project.salestaxes.taxation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import my.project.salestaxes.input.ShoppingBasketItem;
import my.project.salestaxes.misc.MathHelper;
import org.apache.commons.lang3.StringUtils;

public class BasicSalesTax implements SalesTax {

  private static final double BASIC_SALES_TAX = 0.10;
  private static final List<String> TAX_FREE_PRODUCTS = List.of("chocolate", "book", "pill");


  @Override
  public boolean applicable(final ShoppingBasketItem shoppingBasketItem) {

    var descriptionTokens = shoppingBasketItem.getDescription().split(" ");
    return Arrays.stream(descriptionTokens).noneMatch(this::isTaxFree);
  }

  @Override
  public BigDecimal calculate(final BigDecimal netPrice) {
    return MathHelper.truncate(netPrice.multiply(BigDecimal.valueOf(BASIC_SALES_TAX)));
  }

  private boolean isTaxFree(final String token) {
    return TAX_FREE_PRODUCTS.stream().anyMatch(taxFreeProduct -> StringUtils.containsIgnoreCase(token, taxFreeProduct));
  }
}
