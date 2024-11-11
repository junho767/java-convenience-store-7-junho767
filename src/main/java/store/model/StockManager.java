package store.model;

import store.validator.InputValidator;

public class StockManager {
    public void validateStock(PurchaseItem item, Product product) {
        InputValidator.validateProductExists(product);
        InputValidator.validateStockQuantity(item.getQuantity(), product.getTotalQuantity());
    }
}