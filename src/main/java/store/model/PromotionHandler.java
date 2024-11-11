package store.model;

import store.view.InputView;

import java.util.LinkedHashMap;
import java.util.Map;

public class PromotionHandler {
    private final Product product;
    private final PurchaseItem purchaseItem;
    private int totalAmount;
    private final Map<String, PurchaseItem> freeItems;

    public PromotionHandler(Product product, PurchaseItem purchaseItem) {
        this.product = product;
        this.purchaseItem = purchaseItem;
        this.totalAmount = 0;
        this.freeItems = new LinkedHashMap<>();
    }

    public int applyPromotion() {
        int promoApplicableCount = getPromoApplicableCount();
        int remainingRequest = getRemainingRequest(promoApplicableCount);
        int freeItemCount = calculateFreeItemCount(promoApplicableCount);
        int nowPromotionImpossible = getNowPromotionImpossible();

        if (purchaseItem.getQuantity() < product.getPromotionQuantity()) {
            freeItemCount = adjustForAdditionalItem(freeItemCount);
            promoApplicableCount = purchaseItem.getQuantity();
        }

        if (product.getPromotionQuantity() <= purchaseItem.getQuantity()) {
            checkPromotionStock(nowPromotionImpossible);
        }

        updateStock(promoApplicableCount, remainingRequest);
        calculateTotalPrice(promoApplicableCount, remainingRequest);

        freeItems.put(product.getName(), new PurchaseItem(product.getName(), freeItemCount, product.getPrice()));

        return totalAmount;
    }

    private int getPromoApplicableCount() {
        return Math.min(purchaseItem.getQuantity(), product.getPromotionQuantity());
    }

    private int getRemainingRequest(int promoApplicableCount) {
        return purchaseItem.getQuantity() - promoApplicableCount;
    }

    private int calculateFreeItemCount(int promoApplicableCount) {
        int freeQuantity = product.getPromotion().getBuy() + product.getPromotion().getGet();
        return promoApplicableCount / freeQuantity;
    }

    private int getNowPromotionImpossible() {
        int freeQuantity = product.getPromotion().getBuy() + product.getPromotion().getGet();
        return (product.getPromotionQuantity() / freeQuantity) * freeQuantity;
    }

    private int adjustForAdditionalItem(int freeItemCount) {
        int additionalNeeded = purchaseItem.getQuantity() % (product.getPromotion().getBuy() + product.getPromotion().getGet());
        if (shouldAskForAdditionalItem(additionalNeeded)) {
            purchaseItem.setQuantity(purchaseItem.getQuantity() + 1);
            return askForAdditionalItem() / (product.getPromotion().getBuy() + product.getPromotion().getGet());
        }
        return freeItemCount;
    }

    private boolean shouldAskForAdditionalItem(int additionalNeeded) {
        return additionalNeeded == product.getPromotion().getBuy();
    }

    private int askForAdditionalItem() {
        boolean wantsAdditional = InputView.askAdditionalItem(product.getName());
        if (wantsAdditional) {
            return purchaseItem.getQuantity() + 1;
        }
        return purchaseItem.getQuantity();
    }

    private void checkPromotionStock(int nowPromotionImpossible) {
        int remainingQuantity = purchaseItem.getQuantity() - nowPromotionImpossible;
        if (!InputView.NON_DISCOUNTED_ITEM(product.getName(), remainingQuantity)) {
            throw new IllegalArgumentException();
        }
    }

    private void updateStock(int promoApplicableCount, int remainingRequest) {
        product.setPromotionQuantity(product.getPromotionQuantity() - promoApplicableCount);
        product.setQuantity(product.getQuantity() - remainingRequest);
    }

    private void calculateTotalPrice(int promoApplicableCount, int remainingRequest) {
        totalAmount += promoApplicableCount * product.getPrice();
        totalAmount += remainingRequest * product.getPrice();
    }

    public Map<String, PurchaseItem> getFreeItems() {
        return freeItems;
    }
}
