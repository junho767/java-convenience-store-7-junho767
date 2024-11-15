package store.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void 상품_생성_정상적인_값_입력() {
        Product product = new Product("콜라", 1500, 10, "탄산2+1");

        assertEquals("콜라", product.getName());
        assertEquals(1500, product.getPrice());
        assertEquals(10, product.getQuantity());
        assertEquals(Promotion.CARBONATED_TWO_PLUS_ONE, product.getPromotion());
    }

    @Test
    void 가격_포맷_정상적으로_출력() {
        Product product = new Product("콜라", 1500, 10, "탄산2+1");

        assertEquals("1,500", product.formatPrice());
    }

    @Test
    void 총_수량_재고와_프로모션_수량_합산() {
        Product product = new Product("콜라", 1500, 10, "탄산2+1");
        product.setPromotionQuantity(5);

        assertEquals(15, product.getTotalQuantity());
    }

    @Test
    void 총_수량_프로모션_수량_없을_경우() {
        Product product = new Product("콜라", 1500, 10, "탄산2+1");
        product.setPromotionQuantity(0);

        assertEquals(10, product.getTotalQuantity());
    }
}
