package store.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NonPromotionHandlerTest {

    private Product product;
    private PurchaseItem purchaseItem;
    private NonPromotionHandler nonPromotionHandler;

    @BeforeEach
    void 설정() {
        product = new Product("콜라", 1000, 10, "NULL");
        purchaseItem = new PurchaseItem("콜라", 5, 1000);
        nonPromotionHandler = new NonPromotionHandler(product, purchaseItem);
    }

    @Test
    void 프로모션_적용안됨_정상금액계산_및_재고변경() {
        int totalPrice = nonPromotionHandler.handleNonPromotion();
        assertEquals(5000, totalPrice, "총 금액이 잘못 계산되었습니다.");
        assertEquals(5, product.getQuantity(), "재고가 제대로 차감되지 않았습니다.");
    }

    @Test
    void 재고부족시_더이상구매불가() {
        nonPromotionHandler.handleNonPromotion();
        int totalPrice = nonPromotionHandler.handleNonPromotion();
        assertEquals(10000, totalPrice, "총 금액이 잘못 계산되었습니다.");
        assertEquals(0, product.getQuantity(), "재고가 제대로 차감되지 않았습니다.");
    }
}
