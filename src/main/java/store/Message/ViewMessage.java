package store.Message;

public enum ViewMessage {
    WELCOME_STORE("안녕하세요 W편의점입니다."),
    PRODUCT_LIST("현재 보유하고 있는 상품입니다."),
    INPUT_ITEM_REQUEST("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    MEMBERSHIP_REQUEST("멤버십 할인을 받으시겠습니까? (Y/N)"),
    ASK_RETRY("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"),
    ASK_ADDITIONAL_ITEM("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N): "),
    PROMOTION_WITH_STOCK("- %s %d원 %d개 %s\n- %s %d원 %d개 %s"),
    NO_PROMOTION("- %s %d원 %d개 %s"),
    OUT_OF_STOCK_WITH_PROMOTION("- %s %d원 재고없음 %s\n- %s %d원 재고없음 %s"),
    PROMOTION_ONLY_OUT_OF_STOCK("- %s %d원 %d개 %s\n- %s %d원 재고없음 %s");

    private final String message;

    ViewMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }
}