package store.model;

import store.Message.ViewMessage;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private int promotionQuantity;
    private Promotion promotion;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = Promotion.fromString(promotion);
    }

    // getter 및 setter
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public int getTotalQuantity(){
        return quantity + promotionQuantity;
    }

    public void setPromotionQuantity(int promotionQuantity) {
        this.promotionQuantity = promotionQuantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    @Override
    public String toString() {
        if (quantity > 0 && promotion != Promotion.NULL) {
            return ViewMessage.PROMOTION_WITH_STOCK
                    .format(name, price, promotionQuantity, promotion.getName(),
                            name, price, quantity, Promotion.NULL.getName());
        }
        if (promotion == Promotion.NULL) {
            return ViewMessage.NO_PROMOTION
                    .format(name, price, quantity, Promotion.NULL.getName());
        }
        if (quantity == 0 && promotionQuantity == 0) {
            return ViewMessage.OUT_OF_STOCK_WITH_PROMOTION
                    .format(name, price, promotion.getName(),
                            name, price, Promotion.NULL.getName());
        }
        return ViewMessage.PROMOTION_ONLY_OUT_OF_STOCK
                .format(name, price, promotionQuantity, promotion.getName(),
                        name, price, Promotion.NULL.getName());
    }
}
