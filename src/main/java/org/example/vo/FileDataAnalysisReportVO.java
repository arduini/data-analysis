package org.example.vo;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Builder
public class FileDataAnalysisReportVO {

    private String fileName;
    private Integer customersAmount;
    private Integer salesPeopleAmount;
    private Long mostExpensiveSaleId;
    private BigDecimal mostExpensiveSalePrice;;
    private HashMap<String, BigDecimal> totalSalesBySalesMan;

    public List<String> toStringList() {

        final var stringList = new ArrayList<String>();
        addNonEmptyInfoInStringList(stringList, "Quantidade de clientes do arquivo de entrada: ", customersAmount);
        addNonEmptyInfoInStringList(stringList, "Quantidade de vendedores no arquivo de entrada: ", salesPeopleAmount);
        addNonEmptyInfoInStringList(stringList, "Id da venda mais cara: ", mostExpensiveSaleId);
        addNonEmptyInfoInStringList(stringList, "O Pior vendedor: ", getWorstSalesmanName());

        return stringList;
    }

    public String getWorstSalesmanName() {

        return  totalSalesBySalesMan.entrySet().stream()
                .min(Comparator.comparing(Map.Entry::getValue))
                .orElseGet(null)
                .getKey();
    }

    public FileDataAnalysisReportVO incrementCustomersAmount() {
        customersAmount++;
        return this;
    }

    public FileDataAnalysisReportVO incrementSalesPeopleAmount() {
        salesPeopleAmount++;
        return this;
    }

    private void addNonEmptyInfoInStringList(List<String> list, final String description, final Object value) {
        if (Objects.nonNull(value) && !StringUtils.isEmpty(value.toString())) {
            list.add(description + value.toString());
        }
    }
}
