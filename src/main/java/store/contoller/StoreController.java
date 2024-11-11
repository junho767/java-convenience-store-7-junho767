package store.contoller;

import store.Utils;
import store.model.*;
import store.view.InputView;
import store.view.OutputView;

import java.time.LocalDate;
import java.util.*;

import camp.nextstep.edu.missionutils.DateTimes;

public class StoreController {
    private static final Map<String, Product> productMap = new LinkedHashMap<>();
    private static final StockManager stockManager = new StockManager();
    private static final MembershipDiscount membershipDiscount = new MembershipDiscount();
    private static final Receipt receipt = new Receipt();

    public StoreController() {
        ProductLoader.initProducts(productMap);
    }

    public void run() {
        while (true) {
            try {
                handleCustomerInteraction();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
            if (!InputView.askRetry()) {
                break;
            }
        }
    }

    private void handleCustomerInteraction() {
        OutputView.welcomeStore();
        printProducts();
        parseItems();
    }

    public void printProducts() {
        productMap.values().forEach(System.out::println);
    }

    public static void parseItems() {
        String item = readItemInput();
        List<PurchaseItem> purchaseItems = processItemInput(item);
        processItems(purchaseItems);
    }

    private static String readItemInput() {
        return InputView.readItem();
    }

    private static List<PurchaseItem> processItemInput(String item) {
        String cleanedItem = Utils.removeBrackets(item);
        return Utils.parseItems(cleanedItem);
    }

    public static void processItems(List<PurchaseItem> purchaseItems) {
        LocalDate today = LocalDate.from(DateTimes.now());
        Map<String, PurchaseItem> purchase = new HashMap<>();
        Map<String, PurchaseItem> freeItems = new HashMap<>();
        int totalPrice = 0;
        int nonPromotionTotalPrice = 0;

        for (PurchaseItem purchaseItem : purchaseItems) {
            Product product = productMap.get(purchaseItem.getName());
            int purchaseTotalPrice = processItem(purchaseItem, product, today, freeItems);
            totalPrice += purchaseTotalPrice;
            purchase.put(purchaseItem.getName(), new PurchaseItem(purchaseItem.getName(), purchaseItem.getQuantity(), purchaseTotalPrice));
        }

        int membershipDiscountPrice = membershipDiscount.applyMembershipDiscount(nonPromotionTotalPrice);
        receipt.printReceipt(purchase, freeItems, membershipDiscountPrice, totalPrice);
    }

    private static int processItem(PurchaseItem purchaseItem, Product product, LocalDate today, Map<String, PurchaseItem> freeItems) {
        int purchaseTotalPrice;
        Promotion promotion = product.getPromotion();

        if (promotion != Promotion.NULL && promotion.isAvailable(today) && product.getPromotionQuantity() > 0) {
            PromotionHandler promotionHandler = new PromotionHandler(product, purchaseItem);
            purchaseTotalPrice = promotionHandler.applyPromotion();
            freeItems.putAll(promotionHandler.getFreeItems());
            return purchaseTotalPrice;
        }

        NonPromotionHandler nonPromotionHandler = new NonPromotionHandler(product, purchaseItem);
        purchaseTotalPrice = nonPromotionHandler.handleNonPromotion();
        return purchaseTotalPrice;
    }
}
