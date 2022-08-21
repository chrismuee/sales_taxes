package my.project.salestaxes.input;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class ShoppingBasketItem {

  private final String description;
  private final int quantity;
  private final BigDecimal netPrice;

}
