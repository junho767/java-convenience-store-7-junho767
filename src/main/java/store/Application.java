package store;

import store.contoller.StoreController;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = new StoreController();
        storeController.run();
    }
}
