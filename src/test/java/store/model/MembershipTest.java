package store.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MembershipTest {
    private MembershipDiscount membershipDiscount;

    @BeforeEach
    void setUp() {
        membershipDiscount = new MembershipDiscount();
    }

    @Test
    void applyMembershipDiscount_WithMembership_ReturnsDiscountedAmount() {
        int totalNonPromotionPrice = 20000;
        int expectedDiscount = 6000;

        int result = membershipDiscount.calculateMembershipDiscount(totalNonPromotionPrice);

        assertEquals(expectedDiscount, result);
    }

    @Test
    void applyMembershipDiscount_WithMembership_CapsAt8000() {
        int totalNonPromotionPrice = 50000;
        int expectedDiscount = 8000;

        int result = membershipDiscount.calculateMembershipDiscount(totalNonPromotionPrice);

        assertEquals(expectedDiscount, result);
    }
}
