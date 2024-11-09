package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.Message.ViewMessage;

public class InputView {
    public static String readItem() {
        System.out.println(ViewMessage.INPUT_ITEM_REQUEST);
        return Console.readLine();
    }
    public static boolean askRetry() {
        System.out.println(ViewMessage.ASK_RETRY);
        String input = Console.readLine();
        return input.equals("Y") || input.equals("y");
    }

    public static boolean askMemberShip() {
        System.out.println(ViewMessage.MEMBERSHIP_REQUEST);
        String input = Console.readLine();
        return input.equals("Y") || input.equals("y");
    }

    public static boolean askAdditionalItem(String name){
        System.out.printf(ViewMessage.ASK_ADDITIONAL_ITEM.format(name));
        String input = Console.readLine();
        return input.equals("Y") || input.equals("y");
    }
}
