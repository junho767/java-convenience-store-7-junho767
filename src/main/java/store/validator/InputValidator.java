package store.validator;

import store.message.ErrorMessage;
import store.model.Product;

public class InputValidator {
    public static void validateProductExists(Product product) {
        if (product == null) {
            throw new IllegalArgumentException(ErrorMessage.PRODUCT_NOT_FOUND.getMessage());
        }
    }

    public static void validateStockQuantity(int itemQuantity, int productQuantity) {
        if (itemQuantity > productQuantity) {
            throw new IllegalArgumentException(ErrorMessage.STOCK_QUANTITY_EXCEED.getMessage());
        }
    }

    public static void validateItemFormat(String[] parts) {
        if (parts.length != 2) {
           throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
    }
}
