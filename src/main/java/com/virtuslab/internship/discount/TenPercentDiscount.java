package com.virtuslab.internship.discount;
import com.virtuslab.internship.product.Product.Type;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

import java.math.BigDecimal;
import java.util.List;

public class TenPercentDiscount {

    public String NAME = "";
    public String NAME2 = "";
    private int count = 0;

    public TenPercentDiscount(){
        NAME = null;
    }

    public Receipt apply(Receipt receipt) {
        shouldApplyMore(receipt);
        // 10 and 15 % disccount
        if (shouldApply(receipt) && shouldApplyMore(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.9)).multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            NAME = "TenPercentDiscount";
            NAME2 = "FftnPercentDiscount";
            discounts.add(NAME);
            discounts.add(NAME2);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        } // 10 % discount only  
        else if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.9));
            var discounts = receipt.discounts();
            NAME = "TenPercentDiscount";
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        } // 15 % discount only
        else if (shouldApplyMore(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            NAME2 = "FftnPercentDiscount";
            discounts.add(NAME2);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt) {
        return receipt.totalPrice().compareTo(BigDecimal.valueOf(50)) >= 0;
    }

    private boolean shouldApplyMore(Receipt receipt) {
        List<ReceiptEntry> receiptEntries = receipt.getReceiptEntries();
        receiptEntries.stream().forEach(x -> {
            if (x.getProduct().type()==Type.GRAINS) {
                count = count + x.quantity();
            }
        });
        return count >= 3;
    }         
}

