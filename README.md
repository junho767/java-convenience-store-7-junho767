# java-convenience-store-precourse

# 편의점

## 입력 요구 사항

1. **상품 목록과 행사 목록을 파일 입출력을 통해 불러옵니다.**
    - `src/main/resources/products.md`: 상품 목록과 가격, 재고, 프로모션 정보가 담긴 파일.
    - `src/main/resources/promotions.md`: 프로모션 목록이 담긴 파일.

2. **구매할 상품과 수량을 입력받습니다.**
    - 상품명과 수량은 하이픈(`-`)으로 구분되며, 개별 상품은 대괄호(`[]`)로 묶어 쉼표(`,`)로 구분합니다.
        - 예시: `[콜라-10],[사이다-3]`

3. **프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 추가 여부를 입력받습니다.**
    - 예시:
        - "현재 콜라는 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"

4. **프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 정가로 결제 여부를 입력받습니다.**
    - 예시:
        - "현재 사이다 2개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"

5. **멤버십 할인 적용 여부를 입력받습니다.**
    - 예시:
        - "멤버십 할인을 받으시겠습니까? (Y/N)"

6. **추가 구매 여부를 입력받습니다.**
    - 예시:
        - "구매하고 싶은 다른 상품이 있나요? (Y/N)"

## 출력 요구 사항

1. **환영 인사 및 상품 목록 출력**
    - "안녕하세요. W편의점입니다."
    - 상품의 이름, 가격, 재고, 프로모션 상태를 출력합니다.
        - 예시:
          ```
          현재 보유하고 있는 상품입니다.
          - 콜라 1,000원 10개 탄산2+1
          - 사이다 1,000원 8개 탄산2+1
          - 오렌지주스 1,800원 9개 MD추천상품
          - 오렌지주스 1,800원 재고 없음
          ```

2. **구매 상품 내역, 증정 상품 내역, 금액 정보를 출력**
    - 예시:
      ```
      ========== W 편의점 ==========
      상품명      수량   금액
      콜라        3    3,000
      에너지바    5   10,000
      ========== 증정 ============
      콜라        1
      ==========================
      총구매액     8    13,000
      행사할인          -1,000
      멤버십할인        -3,000
      내실돈            9,000
      ```

3. **상품이 없거나 재고가 부족할 경우 적절한 에러 메시지 출력**
    - 예시:
        - "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."
        - "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."

4. **잘못된 형식으로 입력 시, 오류 메시지 출력**
    - 예시:
        - "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."

---

## 예외 처리

1. **잘못된 형식으로 입력한 경우**:
    - 상품 목록 입력 형식이 잘못된 경우:
        - "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."

2. **존재하지 않는 상품 입력**:
    - 상품명이나 코드가 잘못된 경우:
        - "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."

3. **구매 수량 초과**:
    - 재고보다 많은 수량을 입력한 경우:
        - "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."

4. **기타 잘못된 입력**:
    - 수량 또는 멤버십 할인 선택 시 잘못된 값이 입력된 경우:
        - "[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."

---

## 시스템 아키텍처

- **상품 및 재고 관리**: 상품 목록과 재고는 `products.md` 파일에서 읽어와 관리됩니다. 구매가 이루어질 때마다 재고가 차감됩니다.
- **프로모션 관리**: 프로모션 정보는 `promotions.md` 파일에서 관리됩니다. 프로모션에 대한 할인 여부 및 무료 증정 상품에 대한 처리는 상품을 구매할 때 반영됩니다.
- **결제 시스템**: 상품과 수량, 할인 정보가 처리되어 최종 결제 금액을 계산합니다. 멤버십 할인과 프로모션 할인은 결제 금액에 반영됩니다.
- **영수증 출력**: 영수증 형식에 맞춰 구매 내역과 최종 금액을 사용자에게 출력합니다.