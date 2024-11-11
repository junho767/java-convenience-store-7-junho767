package store.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PromotionHandlerTest {

    private Product product;
    private PurchaseItem purchaseItem;
    private PromotionHandler promotionHandler;

    @BeforeEach
    void setUp() {
        String promotion = Promotion.CARBONATED_TWO_PLUS_ONE.getName();
        product = new Product("콜라", 1000, 10, promotion);
        purchaseItem = new PurchaseItem("콜라", 5, 1000);


        promotionHandler = new PromotionHandler(product, purchaseItem);
    }

    @Test
    void applyPromotion_WhenPromotionIsApplied_ReturnCorrectTotalAmount() {
        int totalAmount = promotionHandler.applyPromotion();

        assertEquals(7500, totalAmount);
    }

    @Test
    void applyPromotion_WhenNotEnoughStock_ReturnsCorrectTotalAmount() {
        purchaseItem.setQuantity(2);
        promotionHandler = new PromotionHandler(product, purchaseItem);

        int totalAmount = promotionHandler.applyPromotion();

        assertEquals(3000, totalAmount);
    }

    @Test
    void applyPromotion_WhenPromotionStockIsInsufficient_ThrowsException() {
        product.setPromotionQuantity(1);

        assertThrows(IllegalArgumentException.class, () -> promotionHandler.applyPromotion());
    }

    @Test
    void getFreeItems_ReturnsCorrectFreeItems() {
        promotionHandler.applyPromotion();

        Map<String, PurchaseItem> freeItems = promotionHandler.getFreeItems();

        assertNotNull(freeItems);
        assertTrue(freeItems.containsKey("콜라"));
        assertEquals(2, freeItems.get("콜라").getQuantity());
    }
}
