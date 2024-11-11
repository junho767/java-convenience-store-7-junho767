package store.model;

public class PurchaseItem {
    private final String name;
    private int quantity;
    private int price;

    public PurchaseItem(String name, int quantity) {
        this.quantity = quantity;
        this.name = name;
    }

    public PurchaseItem(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
