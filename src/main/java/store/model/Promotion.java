package store.model;

import java.time.LocalDate;

public enum Promotion {
    CARBONATED_TWO_PLUS_ONE("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")),
    MD_RECOMMENDED("MD추천상품", 1, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")),
    FLASH_SALE("반짝할인", 1, 1, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-30")),
     NULL(" ", 0, 0, null, null);

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public static Promotion fromString(String promotionName) {
        for (Promotion promo : values()) {
            if (promo.getName().equals(promotionName)) {
                return promo;
            }
        }
        return NULL;
    }

    public boolean isAvailable(LocalDate date) {
        return (startDate == null || !date.isBefore(startDate)) &&
                (endDate == null || !date.isAfter(endDate));
    }
}
