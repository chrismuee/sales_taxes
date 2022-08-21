package my.project.salestaxes.taxation;

import java.math.BigDecimal;
import my.project.salestaxes.input.ShoppingBasketItem;

public interface SalesTax {

  boolean applicable(ShoppingBasketItem shoppingBasketItem);

  BigDecimal calculate(BigDecimal netPrice);
}
