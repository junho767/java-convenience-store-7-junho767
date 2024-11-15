package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.message.ViewMessage;
import store.validator.InputValidator;

public class InputView {
    private static final String YES = "Y";

    public static String readItem() {
        System.out.println(ViewMessage.INPUT_ITEM_REQUEST.getMessage());
        return Console.readLine();
    }
    public static boolean askRetry() {
        System.out.println(ViewMessage.ASK_RETRY.getMessage());
        String input = Console.readLine();
        InputValidator.validateYesOrNo(input);
        return input.equals(YES);
    }

    public static boolean askMemberShip() {
        System.out.println(ViewMessage.MEMBERSHIP_REQUEST.getMessage());
        String input = Console.readLine();
        InputValidator.validateYesOrNo(input);
        return input.equals(YES);
    }

    public static boolean askAdditionalItem(String name) {
        System.out.printf(ViewMessage.ASK_ADDITIONAL_ITEM.format(name));
        String input = Console.readLine();
        InputValidator.validateYesOrNo(input);
        return input.equals(YES);
    }

    public static boolean NON_DISCOUNTED_ITEM(String name, int quantity) {
        System.out.printf(ViewMessage.NON_DISCOUNTED_ITEM.format(name, quantity));
        String input = Console.readLine();
        InputValidator.validateYesOrNo(input);
        return input.equals(YES);
    }
}
