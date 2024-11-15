package store.validator;

import org.junit.jupiter.api.Test;
import store.model.Product;

import static org.junit.jupiter.api.Assertions.*;

class InputTest {

    @Test
    void 상품이_없을_경우_예외가_발생한다() {
        assertThrows(IllegalArgumentException.class, () -> {
            InputValidator.validateProductExists(null);
        });
    }

    @Test
    void 상품이_있을_경우_예외가_발생하지_않는다() {
        Product product = new Product("Test Product", 1000, 10, null);  // 예시로 Product 객체 생성
        assertDoesNotThrow(() -> {
            InputValidator.validateProductExists(product);
        });
    }

    @Test
    void 수량이_재고를_초과할_경우_예외가_발생한다() {
        assertThrows(IllegalArgumentException.class, () -> {
            InputValidator.validateStockQuantity(5, 3);
        });
    }

    @Test
    void 수량이_재고를_초과하지_않을_경우_예외가_발생하지_않는다() {
        assertDoesNotThrow(() -> {
            InputValidator.validateStockQuantity(3, 5);
        });
    }

    @Test
    void 예외_입력값이_YN_형식이_아닐_경우_예외가_발생한다() {
        assertThrows(IllegalArgumentException.class, () -> {
            InputValidator.validateYesOrNo("A");
        });
    }

    @Test
    void 예외_입력값이_YN_형식일_경우_예외가_발생하지_않는다() {
        assertDoesNotThrow(() -> {
            InputValidator.validateYesOrNo("Y");
            InputValidator.validateYesOrNo("N");
        });
    }

    @Test
    void 아이템_형식이_잘못된_경우_예외가_발생한다() {
        assertThrows(IllegalArgumentException.class, () -> {
            InputValidator.validateItemFormat(new String[] {"itemName"});
        });
    }

    @Test
    void 아이템_형식이_정상일_경우_예외가_발생하지_않는다() {
        assertDoesNotThrow(() -> {
            InputValidator.validateItemFormat(new String[] {"itemName", "3"});
        });
    }

    @Test
    void 아이템_이름이_빈_값일_경우_예외가_발생한다() {
        assertThrows(IllegalArgumentException.class, () -> {
            InputValidator.validateItemName("");
        });
    }

    @Test
    void 아이템_이름이_정상일_경우_예외가_발생하지_않는다() {
        assertDoesNotThrow(() -> {
            InputValidator.validateItemName("itemName");
        });
    }

    @Test
    void 수량이_잘못된_형식일_경우_예외가_발생한다() {
        // 수량이 0 이하이거나 숫자가 아닌 경우 예외 발생
        assertThrows(IllegalArgumentException.class, () -> {
            InputValidator.validateQuantity("-1");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            InputValidator.validateQuantity("abc");
        });
    }

    @Test
    void 수량이_정상일_경우_예외가_발생하지_않는다() {
        // 수량이 정상적인 경우 예외가 발생하지 않음
        assertDoesNotThrow(() -> {
            InputValidator.validateQuantity("5");
        });
    }
}
