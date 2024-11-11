package store.validator;

import store.message.ErrorMessage;
import store.model.Product;

public class InputValidator {
    private static final int ITEM_PARTS_COUNT = 2;
    private static final String YES_NO_PATTERN = "^[YN]$";

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

    public static void validateYesOrNo(String input) {
        if(!input.matches(YES_NO_PATTERN)){
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
    }

    public static void validateItemFormat(String[] parts) {
        validatePartsCount(parts);
        validateItemName(parts[0]);
        validateQuantity(parts[1]);
    }

    private static void validatePartsCount(String[] parts) {
        if (parts.length != ITEM_PARTS_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT.getMessage());
        }
    }

    public static void validateItemName(String name) {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT.getMessage());
        }
    }

    public static void validateQuantity(String quantityString) {
        try {
            int quantity = Integer.parseInt(quantityString.trim());
            if (quantity <= 0) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
    }
}
