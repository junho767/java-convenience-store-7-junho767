package store.model;

public class NonPromotionHandler {
    private final Product product;
    private final PurchaseItem purchaseItem;
    private int totalAmount;

    public NonPromotionHandler(Product product, PurchaseItem purchaseItem) {
        this.product = product;
        this.purchaseItem = purchaseItem;
        this.totalAmount = 0;
    }

    public int handleNonPromotion() {
        int purchaseQuantity = purchaseItem.getQuantity();
        int productQuantity = product.getQuantity();

        product.setQuantity(productQuantity - purchaseQuantity);

        int purchaseTotalPrice = purchaseQuantity * product.getPrice();
        totalAmount += purchaseTotalPrice;

        return totalAmount;
    }
}
