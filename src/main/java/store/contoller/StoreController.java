package store.contoller;

import store.model.Item;
import store.model.Product;
import store.model.ProductLoader;
import store.view.InputView;
import store.view.OutputView;
import java.util.*;


public class StoreController {
    private static Map<String, Product> productMap = new HashMap<>();

    public StoreController() {
        ProductLoader.initProducts(productMap);
    }

    public void run() {
        while (true) {
            getCustomer();
            if (!InputView.askRetry()) {
                break;
            }
        }
    }

    private void getCustomer() {
        OutputView.welcomeStore();
        printProducts();
        parseItems();
    }

    public void printProducts() {
        for (Product p : productMap.values()) {
            System.out.println(p);
        }
    }

    public static List<Item> parseItems() {
        List<Item> items = new ArrayList<>();
        String input = InputView.readItem();
        String cleanedInput = input.replaceAll("[\\[\\]]", "");
        String[] itemStrings = cleanedInput.split(",");

        for (String itemString : itemStrings) {
            String[] parts = itemString.split("-"); // '-'를 기준으로 분리
            String name = parts[0];
            int quantity = Integer.parseInt(parts[1]);
            items.add(new Item(name, quantity));
        }

        return items;
    }
}
