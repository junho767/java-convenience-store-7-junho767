package store.view;

import store.message.ReceiptMessage;
import store.message.ViewMessage;
import store.model.PurchaseItem;
import store.model.Receipt;

import java.text.NumberFormat;

public class OutputView {
    public static void welcomeStore() {
        System.out.println(ViewMessage.WELCOME_STORE.getMessage());
        System.out.println(ViewMessage.PRODUCT_LIST.getMessage());
    }

    public static void printStore() {
        System.out.println(ReceiptMessage.WELCOME_STORE.getMessage());
        System.out.println(ReceiptMessage.PRODUCT_LIST.getMessage());
    }

    public static void printFreeItem() {
        System.out.println(ReceiptMessage.PROMOTION_HEADER.getMessage());
    }

    public static void printLine() {
        System.out.println(ReceiptMessage.LINE.getMessage());
    }

    public static void printProductFormat(PurchaseItem purchaseItem, NumberFormat currencyFormat) {
        System.out.printf(ReceiptMessage.PRODUCT_FORMAT.format(purchaseItem.getName(),
                purchaseItem.getQuantity(),
                currencyFormat.format(purchaseItem.getPrice())));
    }

    public static void printFreeProductFormat(PurchaseItem freeItem) {
        System.out.printf(ReceiptMessage.FREE_ITEM_FORMAT.format(
                freeItem.getName(), freeItem.getQuantity()));
    }

    public static void printTotalAmount(int totalPurchasePrice, NumberFormat currencyFormat) {
        System.out.printf(ReceiptMessage.TOTAL_AMOUNT.format(currencyFormat.format(totalPurchasePrice)));
    }

    public static void printPromotionDiscount(int totalPromotionDiscount, NumberFormat currencyFormat) {
        System.out.printf(ReceiptMessage.PROMOTION_DISCOUNT.format(currencyFormat.format(totalPromotionDiscount)));
    }

    public static void printMembershipDiscount(int membershipDiscountPrice, NumberFormat currencyFormat) {
        System.out.printf(ReceiptMessage.MEMBERSHIP_DISCOUNT.format(currencyFormat.format(membershipDiscountPrice)));
    }

    public static void printFinalAmount(int totalAmount, NumberFormat currencyFormat) {
        System.out.printf(ReceiptMessage.FINAL_AMOUNT.format(currencyFormat.format(totalAmount)));
    }
}
