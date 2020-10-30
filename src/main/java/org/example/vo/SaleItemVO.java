package org.example.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SaleItemVO {

    private Long id;
    private Integer quantity;
    private BigDecimal price;

    public BigDecimal calculateSaleItemTotalPrice() {

        return price.multiply(new BigDecimal(quantity));
    }
}
