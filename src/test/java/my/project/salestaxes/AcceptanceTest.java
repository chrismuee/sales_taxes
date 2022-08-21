package my.project.salestaxes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import my.project.salestaxes.input.ShoppingBasketExtractor;
import my.project.salestaxes.ouput.Receipt;
import my.project.salestaxes.ouput.ReceiptItem;
import my.project.salestaxes.ouput.ReceiptPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AcceptanceTest {

  ShoppingBasketExtractor shoppingBasketExtractor;

  @TempDir
  Path tempDir;

  @BeforeEach
  void init() {
    shoppingBasketExtractor = new ShoppingBasketExtractor();
  }

  @Test
  void Input1Test() throws IOException {
    Path pathToFileInput1 = tempDir.resolve("input1.txt");
    List<String> fileContent = List.of("1 book at 12.49", "1 music CD at 14.99", "1 chocolate bar at 0.85");
    Files.write(pathToFileInput1, fileContent);

    var shoppingBasketItems = shoppingBasketExtractor.extractItems(pathToFileInput1);
    var receiptItems = shoppingBasketItems.stream().map(
        ReceiptItem::createReceiptItem).collect(Collectors.toList());
    var receipt = Receipt.createReceipt(receiptItems);
    var printedReceipt = (new ReceiptPrinter()).print(receipt);

    assertEquals("1 book: 12.49", printedReceipt.get(0));
    assertEquals("1 music CD: 16.49", printedReceipt.get(1));
    assertEquals("1 chocolate bar: 0.85", printedReceipt.get(2));
    assertEquals("Sales Taxes: 1.50", printedReceipt.get(3));
    assertEquals("Total: 29.83", printedReceipt.get(4));

    System.out.println("Output 1:");
    printedReceipt.forEach(System.out::println);
    System.out.println();
  }

  @Test
  void Input2Test() throws IOException {
    Path pathToFileInput1 = tempDir.resolve("input2.txt");
    List<String> fileContent =
        List.of("1 imported box of chocolates at 10.00", "1 imported bottle of perfume at 47.50");
    Files.write(pathToFileInput1, fileContent);

    var shoppingBasketItems = shoppingBasketExtractor.extractItems(pathToFileInput1);
    var receiptItems = shoppingBasketItems.stream().map(
        ReceiptItem::createReceiptItem).collect(Collectors.toList());
    var receipt = Receipt.createReceipt(receiptItems);
    var printedReceipt = (new ReceiptPrinter()).print(receipt);

    assertEquals("1 imported box of chocolates: 10.50", printedReceipt.get(0));
    assertEquals("1 imported bottle of perfume: 54.65", printedReceipt.get(1));
    assertEquals("Sales Taxes: 7.65", printedReceipt.get(2));
    assertEquals("Total: 65.15", printedReceipt.get(3));

    System.out.println("Output 2:");
    printedReceipt.forEach(System.out::println);
    System.out.println();
  }

  @Test
  void Input3Test() throws IOException {
    Path pathToFileInput1 = tempDir.resolve("input3.txt");
    List<String> fileContent = List.of("1 imported bottle of perfume at 27.99", "1 bottle of perfume at 18.99",
        "1 packet of headache pills at 9.75", "1 box of imported chocolates at 11.25");
    Files.write(pathToFileInput1, fileContent);

    var shoppingBasketItems = shoppingBasketExtractor.extractItems(pathToFileInput1);
    var receiptItems = shoppingBasketItems.stream().map(
        ReceiptItem::createReceiptItem).collect(Collectors.toList());
    var receipt = Receipt.createReceipt(receiptItems);
    var printedReceipt = (new ReceiptPrinter()).print(receipt);

    assertEquals("1 imported bottle of perfume: 32.19", printedReceipt.get(0));
    assertEquals("1 bottle of perfume: 20.89", printedReceipt.get(1));
    assertEquals("1 packet of headache pills: 9.75", printedReceipt.get(2));
    assertEquals("1 imported box of chocolates: 11.85", printedReceipt.get(3));
    assertEquals("Sales Taxes: 6.70", printedReceipt.get(4));
    assertEquals("Total: 74.68", printedReceipt.get(5));

    System.out.println("Output 3:");
    printedReceipt.forEach(System.out::println);
  }


}