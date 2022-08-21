package my.project.salestaxes.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import my.project.salestaxes.exceptions.TokenNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ShoppingBasketReaderTest {

  ShoppingBasketExtractor shoppingBasketExtractor;
  @TempDir
  Path tempDir;

  @BeforeEach
  void init() {
    shoppingBasketExtractor = new ShoppingBasketExtractor();
  }

  @Test
  void extractItems() throws IOException {
    var pathToFileInput1 = tempDir.resolve("input1.txt");
    var fileContent = List.of("1 book at 12.49", "1 music CD at 14.99", "1 chocolate bar at 0.85");
    Files.write(pathToFileInput1, fileContent);

    var actual = shoppingBasketExtractor.extractItems(pathToFileInput1);
    var expected = List.of(
        ShoppingBasketItem.builder().quantity(1).description("book").netPrice(new BigDecimal("12.49")).build(),
        ShoppingBasketItem.builder().quantity(1).description("music CD").netPrice(new BigDecimal("14.99")).build(),
        ShoppingBasketItem.builder().quantity(1).description("chocolate bar").netPrice(new BigDecimal("0.85")).build());

    assertEquals(expected, actual);
  }

  @Test
  void extractItems_TokenNotFoundException() throws IOException {
    var pathToFileInput1 = tempDir.resolve("input1.txt");
    var fileContent = List.of("1 music CD 14.99");
    Files.write(pathToFileInput1, fileContent);

    assertThrows(TokenNotFoundException.class, () -> shoppingBasketExtractor.extractItems(pathToFileInput1),
        "Expected to throw Exception as 'at' token is missing.");
  }
}