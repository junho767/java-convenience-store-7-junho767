package store.contoller;

import store.model.*;
import store.validator.InputValidator;
import store.view.InputView;
import store.view.OutputView;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

import camp.nextstep.edu.missionutils.DateTimes;

public class StoreController {
    private static final Map<String, Product> productMap = new LinkedHashMap<>();


    public StoreController() {
        ProductLoader.initProducts(productMap);
    }

    public void run() {
        while (true) {
            try{
                getCustomer();
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                continue;
            }
            if (!InputView.askRetry()) {
                break;
            }
        }
    }

    private void getCustomer() {
        OutputView.welcomeStore();
        printProducts();
        parseItems();
    }

    public void printProducts() {
        for (Product p : productMap.values()) {
            System.out.println(p);
        }
    }

    public static void parseItems() {
        String item = readItemInput();
        String cleanedInput = cleanInput(item);
        List<Item> items = parseItemListFromInput(cleanedInput);
        productMatcher(items);
    }
    // 1. 아이템 입력을 처리하는 메서드
    private static String readItemInput() {
        return InputView.readItem();
    }

    // 2. 아이템 문자열을 정리하는 메서드
    private static String cleanInput(String input) {
        return input.replaceAll("[\\[\\]]", "");
    }

    // 3. 아이템 문자열을 파싱하는 메서드
    private static List<Item> parseItemListFromInput(String input) {
        List<Item> items = new ArrayList<>();
        String[] itemStrings = input.split(",");

        for (String itemString : itemStrings) {
            String[] parts = itemString.split("-");

            InputValidator.validateItemFormat(parts);

            String name = parts[0];
            int quantity = Integer.parseInt(parts[1]);
            items.add(new Item(name, quantity));
        }

        return items;
    }

    public static void productMatcher(List<Item> items) {
        LocalDate today = LocalDate.from(DateTimes.now());
        int totalAmount = 0; // 지불 할 금액
        int nonPromotionTotalPrice = 0; // 프로모션 미적용 금액
        int membershipDiscountPrice = 0; // 멤버쉽 할인 금액
        Map<String, Item> discountPerItem = new HashMap<>();
        Map<String, Item> freeItems = new HashMap<>();

        for (Item item : items) {
            System.out.println();
            String productName = item.getName();
            Product product = productMap.get(productName);

            InputValidator.validateProductExists(product);
            InputValidator.validateStockQuantity(item.getQuantity(), product.getTotalQuantity());

            int itemQuantity = item.getQuantity();
            Promotion promotion = product.getPromotion();
            int promotionQuantity = product.getPromotionQuantity();
            int quantity = product.getQuantity();

            int itemTotalAmount = 0;

            if(promotion != Promotion.NULL && promotion.isAvailable(today) && promotionQuantity > 0){
                int promoApplicableCount = Math.min(itemQuantity, promotionQuantity); // 재고 차감 수
                int remainingRequest = itemQuantity - promoApplicableCount; // 일반 재고 차감 수
                int freeQuantity = (promotion.getBuy() + promotion.getGet()); // 증정 받는 수
                int freeItemCount = (promoApplicableCount / freeQuantity); // 무료 증정된 수량
                int nowPromotionImpossible = (promotionQuantity / freeQuantity) * freeQuantity;

                if(itemQuantity < promotionQuantity) {
                    // 고객이 더 추가할 수 있는 혜택이 있는지 확인
                    int additionalNeeded = itemQuantity % freeQuantity;
                    if (additionalNeeded == promotion.getBuy()) {
                        boolean wantsAdditional = InputView.askAdditionalItem(productName);
                        if (wantsAdditional) {
                            itemQuantity += 1;
                            promoApplicableCount = itemQuantity;
                            freeItemCount = promoApplicableCount / freeQuantity;
                            item.setQuantity(itemQuantity);
                        }
                    }
                }
                if(itemQuantity > promotionQuantity){
                    handlePromotionStockShortage(product, itemQuantity, nowPromotionImpossible);
                }

                // 프로모션 재고 차감 및 일반 재고 차감
                product.setPromotionQuantity(promotionQuantity - promoApplicableCount);
                product.setQuantity(quantity - remainingRequest);

                // 할인 적용 및 무료 증정 품목 추가
                itemTotalAmount += promoApplicableCount * product.getPrice();
                itemTotalAmount += remainingRequest * product.getPrice();
                totalAmount += itemTotalAmount;

                freeItems.put(productName, new Item(productName, freeItemCount, product.getPrice()));
            }

            // 프로모션이 없는 경우: 일반 재고에서 차감
            if (promotion == Promotion.NULL && product.getQuantity() >= itemQuantity) {
                product.setQuantity(product.getQuantity() - itemQuantity);
                itemTotalAmount += itemQuantity * product.getPrice();
                nonPromotionTotalPrice = itemTotalAmount;
                totalAmount += itemTotalAmount;
            }
            discountPerItem.put(productName, new Item(productName, itemQuantity, itemTotalAmount));
        }

        boolean memberShip = InputView.askMemberShip();
        if(memberShip){
            membershipDiscountPrice = calculateMembershipDiscount(nonPromotionTotalPrice);
            totalAmount -= membershipDiscountPrice;
        }
    }

    // 프로모션 재고 부족 시, 일부 수량을 프로모션 없이 결제할지 여부를 확인하는 메서드
    private static void handlePromotionStockShortage(Product product, int itemQuantity, int nowPromotionImpossible) {
        if (product.getPromotionQuantity() <= itemQuantity) {
            int remainingQuantity = itemQuantity - nowPromotionImpossible;
            boolean proceed = InputView.NON_DISCOUNTED_ITEM(product.getName(), remainingQuantity);
            if (!proceed) {
                throw new IllegalArgumentException();
            }
        }
    }

    private static int calculateMembershipDiscount(int totalAmount) {
        int membershipDiscount = (int) (totalAmount * 0.3);
        return Math.min(membershipDiscount, 8000);
    }
}
