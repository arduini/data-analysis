package org.example.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class SaleVO {

    private Long id;
    private List<SaleItemVO> items;
    private String salesmanName;

    public BigDecimal calculateSaleTotalPrice() {

        return items.stream()
                .map(SaleItemVO::calculateSaleItemTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
