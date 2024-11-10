package store.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class ProductLoader {
    public static void initProducts(Map<String, Product> productMap) {
        String productsPath = "src/main/resources/products.md";

        try (BufferedReader br = Files.newBufferedReader(Paths.get(productsPath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                processLine(line, productMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processLine(String line, Map<String, Product> productMap) {
        String[] values = line.split(",");
        String name = values[0];
        int price = Integer.parseInt(values[1]);
        int quantity = Integer.parseInt(values[2]);
        Promotion promotion = Promotion.fromString(values[3]);

        if (!productMap.containsKey(name) && promotion != Promotion.NULL) {
            createNewProductWithPromotion(name, price, quantity, promotion, productMap);
        }

        if (productMap.containsKey(name) && promotion == Promotion.NULL) {
            updateProductQuantity(name, quantity, productMap);
        }

        if (!productMap.containsKey(name) && promotion == Promotion.NULL) {
            createNewProductWithoutPromotion(name, price, quantity, productMap);
        }
    }

    private static void createNewProductWithPromotion(String name, int price, int quantity, Promotion promotion, Map<String, Product> productMap) {
        Product product = new Product(name, price, 0, promotion.getName());
        product.setPromotionQuantity(quantity);
        productMap.put(name, product);
    }

    private static void createNewProductWithoutPromotion(String name, int price, int quantity, Map<String, Product> productMap) {
        Product product = new Product(name, price, quantity, Promotion.NULL.getName());
        productMap.put(name, product);
    }

    private static void updateProductQuantity(String name, int quantity, Map<String, Product> productMap) {
        Product product = productMap.get(name);
        product.setQuantity(quantity);
    }
}
