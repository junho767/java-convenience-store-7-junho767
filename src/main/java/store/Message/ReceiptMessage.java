package store.message;

public enum ReceiptMessage {
    PRODUCT_FORMAT("%s\t\t\t%d\t\t%s\n"),
    FREE_ITEM_FORMAT("%s\t\t\t%d개 증정\n"),
    LINE("===================================="),
    WELCOME_STORE("=============W 편의점================"),
    PRODUCT_LIST("상품명\t\t수량\t\t금액"),
    PROMOTION_HEADER("=============증    정================"),
    TOTAL_AMOUNT("총구매액\t\t\t\t%s\n"),
    PROMOTION_DISCOUNT("행사할인\t\t\t\t-%s\n"),
    MEMBERSHIP_DISCOUNT("멤버십할인\t\t\t\t-%s\n"),
    FINAL_AMOUNT("내실돈\t\t\t\t%s\n");

    private final String message;

    ReceiptMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }
}
