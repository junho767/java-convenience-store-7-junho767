package store.model;

import store.message.ReceiptMessage;
import store.view.OutputView;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class Receipt {
    private final Map<String, PurchaseItem> purchase;
    private final Map<String, PurchaseItem> freeItems;
    private final int membershipDiscountPrice;
    private int totalAmount;
    private int totalPromotionDiscount;
    private int totalPurchasePrice;

    public Receipt(Map<String, PurchaseItem> purchase, Map<String, PurchaseItem> freeItems,
                   int membershipDiscountPrice, int totalAmount) {
        this.purchase = purchase;
        this.freeItems = freeItems;
        this.membershipDiscountPrice = membershipDiscountPrice;
        this.totalAmount = totalAmount;
        this.totalPromotionDiscount = 0;
        this.totalPurchasePrice = 0;

    }

    public void print() {
        NumberFormat currencyFormat = NumberFormat.getInstance(Locale.KOREA);

        OutputView.printStore();

        totalPurchasePrice = purchaseItems(currencyFormat, totalPurchasePrice);
        totalPromotionDiscount = freeItems(totalPromotionDiscount);

        OutputView.printLine();

        printPriceDetails(currencyFormat);
    }

    private int purchaseItems(NumberFormat currencyFormat, int totalPurchasePrice) {
        for (PurchaseItem purchaseItem : purchase.values()) {
            totalPurchasePrice += purchaseItem.getPrice();
            OutputView.printProductFormat(purchaseItem, currencyFormat);
        }
        return totalPurchasePrice;
    }

    private int freeItems(int totalPromotionDiscount) {
        OutputView.printFreeItem();
        for (PurchaseItem freeItem : freeItems.values()) {
            totalPromotionDiscount += freeItem.getQuantity() * freeItem.getPrice();
            if (freeItem.getQuantity() > 0) {
                OutputView.printFreeProductFormat(freeItem);
            }
        }
        return totalPromotionDiscount;
    }

    private void printPriceDetails(NumberFormat currencyFormat) {
        totalAmount -= totalPromotionDiscount;
        totalAmount -= membershipDiscountPrice;
        OutputView.printTotalAmount(totalPurchasePrice,currencyFormat);
        OutputView.printPromotionDiscount(totalPromotionDiscount,currencyFormat);
        OutputView.printMembershipDiscount(membershipDiscountPrice,currencyFormat);
        OutputView.printFinalAmount(totalAmount,currencyFormat);
    }
}
