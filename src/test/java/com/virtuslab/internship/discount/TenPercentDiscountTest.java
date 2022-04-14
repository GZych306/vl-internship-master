package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TenPercentDiscountTest {

    @Test
    void shouldApply10PercentDiscountWhenPriceIsAbove50() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        // Create receip
        var discount = new TenPercentDiscount();
        List<String> discList = new ArrayList<String>();
        var receipt = new Receipt(receiptEntries, discList);

        // Expected receip
        var expectedTotalPrice = cheese.price().add(steak.price()).multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApply10PercentDiscountWhenPriceIsBelow50() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 2));

        //Create receipt
        var discount = new TenPercentDiscount();
        List<String> discList = new ArrayList<String>();
        var receipt = new Receipt(receiptEntries, discList);

        // Expected
        var expectedTotalPrice = cheese.price().multiply(BigDecimal.valueOf(2));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApply15And10Discount() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 10));

        // Create receipt
        var discount = new TenPercentDiscount();
        List<String> discList = new ArrayList<String>();
        var receipt = new Receipt(receiptEntries, discList);

        // Expected receipt
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(0.85)).multiply(BigDecimal.valueOf(0.9)));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(2, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApply15AndNotApply10Discount() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));

        // Create receipt
        var discount = new TenPercentDiscount();
        List<String> discList = new ArrayList<String>();
        var receipt = new Receipt(receiptEntries, discList);

        // Expected receipt
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(1).multiply(BigDecimal.valueOf(0.85)));
        expectedTotalPrice = expectedTotalPrice.add(cereals.price().multiply(BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(0.85))));
        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice()); 
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }
}
