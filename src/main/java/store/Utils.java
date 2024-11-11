package store;

import store.model.PurchaseItem;
import store.validator.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static final String BRACKETS_PATTERN = "[\\[\\]]";
    private static final String ITEM_DELIMITER = ",";
    private static final String PARTS_DELIMITER = "-";

    public static String removeBrackets(String input) {
        return input.replaceAll(BRACKETS_PATTERN, "");
    }

    public static List<PurchaseItem> parseItems(String input) {
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        String[] itemDelimiter = input.split(ITEM_DELIMITER);

        for (String item : itemDelimiter) {
            purchaseItems.add(parseItem(item));
        }

        return purchaseItems;
    }

    private static PurchaseItem parseItem(String itemString) {
        String[] parts = itemString.split(PARTS_DELIMITER);
        InputValidator.validateItemFormat(parts);

        String name = parts[0];
        int quantity = Integer.parseInt(parts[1]);
        return new PurchaseItem(name, quantity);
    }
}
