package org.example.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SaleItemVO {

    private Long itemId;
    private Integer amount;
    private BigDecimal itemPrice;
}
