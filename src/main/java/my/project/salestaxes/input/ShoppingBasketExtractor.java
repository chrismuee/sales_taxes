package my.project.salestaxes.input;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import my.project.salestaxes.exceptions.TokenNotFoundException;

public class ShoppingBasketExtractor {

  private static final Pattern PATTERN_AT = Pattern.compile("\\bat\\b");

  public List<ShoppingBasketItem> extractItems(final Path filePath) throws IOException {
    List<String> shoppingBasketLines = readAllLines(filePath);
    List<ShoppingBasketItem> result = new ArrayList<>();

    for (String line : shoppingBasketLines) {
      int quantity = extractQuantity(line);
      String description = extractDescription(line);
      BigDecimal netPrice = extractPrice(line);

      result.add(
          ShoppingBasketItem.builder().quantity(quantity).description(description).netPrice(netPrice).build()
      );
    }
    return result;
  }

  private List<String> readAllLines(final Path filePath) throws IOException {
    List<String> result = new ArrayList<>();

    try (Stream<String> lines = Files.lines(filePath, Charset.defaultCharset())) {
      lines.forEachOrdered(result::add);
    }

    return result;
  }

  private int extractQuantity(final String lineOfInput) {
    String[] tokens = lineOfInput.split(" ");
    return Integer.parseInt(tokens[0]);
  }

  private String extractDescription(final String line) {
    int indexOfStartDesc = line.indexOf(" ") + 1;

    Matcher m = PATTERN_AT.matcher(line);
    if (!m.find()) {
      throw new TokenNotFoundException("Expected Token 'at' not found");
    }
    int indexOfEndDesc = m.start() - 1;

    return line.substring(indexOfStartDesc, indexOfEndDesc);
  }

  private BigDecimal extractPrice(final String line) {
    Matcher m = PATTERN_AT.matcher(line);
    if (!m.find()) {
      throw new TokenNotFoundException("Expected Token 'at' not found");
    }
    int indexStartPrice = m.end() + 1;

    return new BigDecimal(line.substring(indexStartPrice));
  }


}
