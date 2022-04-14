package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        List<Product> distinctProduct = new ArrayList<>(); 
        basket.getProducts().stream().distinct().forEach(x -> {distinctProduct.add(x);});
        for (Product x : distinctProduct) {
              receiptEntries.add(new ReceiptEntry(x, Collections.frequency(basket.getProducts(), x)));
        }
        List<String> discList = new ArrayList<String>();
        return new Receipt(receiptEntries, discList);
    }
}
