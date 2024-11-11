package store.model;

import store.view.InputView;

public class MembershipDiscount {
    public int applyMembershipDiscount(int totalNonPromotionPrice) {
        if (InputView.askMemberShip()) {
            return calculateMembershipDiscount(totalNonPromotionPrice);
        }
        return 0;
    }

    private static int calculateMembershipDiscount(int totalAmount) {
        int membershipDiscount = (int) (totalAmount * 0.3);
        return Math.min(membershipDiscount, 8000);
    }
}