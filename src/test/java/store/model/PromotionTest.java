package store.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PromotionTest {

    @Test
    void 정상적인_프로모션을_반환한다() {
        Promotion promotion = Promotion.fromString("탄산2+1");
        assertEquals(Promotion.CARBONATED_TWO_PLUS_ONE, promotion);
    }

    @Test
    void 존재하지_않는_프로모션을_요청할_경우_NULL_반환() {
        Promotion promotion = Promotion.fromString("존재하지않는상품");
        assertEquals(Promotion.NULL, promotion);
    }

    @Test
    void 프로모션_기간_내에서_참이면_참() {
        LocalDate validDate = LocalDate.parse("2024-11-15");
        Promotion promotion = Promotion.FLASH_SALE;
        assertTrue(promotion.isAvailable(validDate));
    }

    @Test
    void 프로모션_기간_외에서_거짓() {
        LocalDate invalidDate = LocalDate.parse("2024-12-01");
        Promotion promotion = Promotion.FLASH_SALE;
        assertFalse(promotion.isAvailable(invalidDate));
    }

    @Test
    void 프로모션_시작일이_없는_경우_참() {
        LocalDate anyDate = LocalDate.parse("2024-06-01");
        Promotion promotion = Promotion.MD_RECOMMENDED;
        assertTrue(promotion.isAvailable(anyDate));
    }

    @Test
    void 프로모션_종료일이_없는_경우_참() {
        LocalDate anyDate = LocalDate.parse("2024-06-01");
        Promotion promotion = Promotion.CARBONATED_TWO_PLUS_ONE;
        assertTrue(promotion.isAvailable(anyDate));
    }

    @Test
    void 끝날_프로모션_일자_앞에서_참() {
        LocalDate validDate = LocalDate.parse("2024-12-31");
        Promotion promotion = Promotion.CARBONATED_TWO_PLUS_ONE;
        assertTrue(promotion.isAvailable(validDate));
    }

    @Test
    void 끝날_프로모션_일자_뒤에서_거짓() {
        LocalDate invalidDate = LocalDate.parse("2025-01-01");
        Promotion promotion = Promotion.CARBONATED_TWO_PLUS_ONE;
        assertFalse(promotion.isAvailable(invalidDate));
    }
}
