package store.view;

import store.message.ViewMessage;

public class OutputView {
    public static void welcomeStore(){
        System.out.println(ViewMessage.WELCOME_STORE);
        System.out.println(ViewMessage.PRODUCT_LIST);
    }
}
