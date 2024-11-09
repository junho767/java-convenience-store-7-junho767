package store.contoller;

import store.model.Product;
import store.model.Promotion;
import store.view.InputView;
import store.view.OutputView;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class StoreController {
    private static final Map<String, Product> productMap = new HashMap<>();

    public StoreController() {
        initProducts();
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
    }

    public void initProducts() {
        String productsPath = "src/main/resources/products.md";

        try (BufferedReader br = Files.newBufferedReader(Paths.get(productsPath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[0];
                int price = Integer.parseInt(values[1]);
                int quantity = Integer.parseInt(values[2]);
                Promotion promotion = Promotion.fromString(values[3]);
                if (!productMap.containsKey(name) && promotion != Promotion.NULL) {
                    Product product = new Product(name, price, 0, promotion.getName());
                    product.setPromotionQuantity(quantity);
                    productMap.put(name, product);
                }
                if (productMap.containsKey(name) && promotion == Promotion.NULL) {
                    Product product = productMap.get(name);
                    product.setQuantity(quantity);
                }
                if (!productMap.containsKey(name) && promotion == Promotion.NULL) {
                    Product product = new Product(name, price, quantity, promotion.getName());
                    productMap.put(name, product);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printProducts() {
        for (Product p : productMap.values()) {
            System.out.println(p);
        }
    }
}
