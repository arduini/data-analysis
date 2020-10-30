package org.example.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SaleVO {

    private Long saleId;
    private List<SaleItemVO> itens;
    private String salesmanName;
}
