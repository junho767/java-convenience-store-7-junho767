package store.model;

public class Item {
    private final String name;
    private final int quantity;
    private int price;

    public Item(String name, int quantity){
        this.quantity = quantity;
        this.name = name;
    }

    public Item(String name, int quantity, int price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getName(){
        return name;
    }

    public int getPrice() {
        return price;
    }
}
