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
    private static final LocalDate today = LocalDate.from(DateTimes.now());

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
        Receipt receipt = purchaseItems();
        receipt.print();
    }

    public void printProducts() {
        productMap.values().forEach(System.out::println);
    }

    public static Receipt purchaseItems() {
        String item = readItemInput();
        List<PurchaseItem> purchaseItems = processItemInput(item);
        return processItems(purchaseItems);
    }

    private static String readItemInput() {
        return InputView.readItem();
    }

    private static List<PurchaseItem> processItemInput(String item) {
        String cleanedItem = Utils.removeBrackets(item);
        return Utils.parseItems(cleanedItem);
    }

    public static Receipt processItems(List<PurchaseItem> purchaseItems) {
        Map<String, PurchaseItem> purchase = new LinkedHashMap<>();
        Map<String, PurchaseItem> freeItems = new LinkedHashMap<>();
        int totalPrice = 0;
        int nonPromotionTotalPrice = 0;

        for (PurchaseItem purchaseItem : purchaseItems) {
            Product product = productMap.get(purchaseItem.getName());
            stockManager.validateStock(purchaseItem, product);
            int purchaseTotalPrice = processItem(purchaseItem, product, freeItems);
            nonPromotionTotalPrice += getNonPromotionTotalPrice(product, purchaseTotalPrice);
            totalPrice += purchaseTotalPrice;
            purchase.put(purchaseItem.getName(), new PurchaseItem(purchaseItem.getName(), purchaseItem.getQuantity(), purchaseTotalPrice));
        }

        int membershipDiscountPrice = membershipDiscount.applyMembershipDiscount(nonPromotionTotalPrice);
        return new Receipt(purchase, freeItems, membershipDiscountPrice, totalPrice);
    }

    private static int processItem(PurchaseItem purchaseItem, Product product, Map<String, PurchaseItem> freeItems) {
        int purchaseTotalPrice;
        Promotion promotion = product.getPromotion();

        if (isPromotionAvailable(promotion, product)) {
            PromotionHandler promotionHandler = new PromotionHandler(product, purchaseItem);
            purchaseTotalPrice = promotionHandler.applyPromotion();
            freeItems.putAll(promotionHandler.getFreeItems());
            return purchaseTotalPrice;
        }

        NonPromotionHandler nonPromotionHandler = new NonPromotionHandler(product, purchaseItem);
        purchaseTotalPrice = nonPromotionHandler.handleNonPromotion();
        return purchaseTotalPrice;
    }

    private static boolean isPromotionAvailable(Promotion promotion, Product product) {
        return promotion != Promotion.NULL && promotion.isAvailable(today) && product.getPromotionQuantity() > 0;
    }

    private static int getNonPromotionTotalPrice(Product product, int purchaseTotalPrice) {
        if (isPromotionAvailable(product.getPromotion(), product)) {
            return 0;
        }
        return purchaseTotalPrice;
    }
}
