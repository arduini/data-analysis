package org.example.vo;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class FileDataAnalysisReportVO {

    private String fileName;
    private Integer customersAmount;
    private Integer salesPeopleAmount;
    private Long mostExpensiveSaleId;
    private BigDecimal mostExpensiveSalePrice;
    // TODO: usar estrutura ordenada

    public List<String> toStringList() {
        final var stringList = new ArrayList<String>();
        addNonNullInfoInList(stringList, "Quantidade de clientes do arquivo de entrada: ", customersAmount);
        addNonNullInfoInList(stringList, "Quantidade de vendedores no arquivo de entrada: ", salesPeopleAmount);
        addNonNullInfoInList(stringList, "Id da venda mais cara: ", mostExpensiveSaleId);
        addNonNullInfoInList(stringList, "O Pior vendedor: ", "João");
        //TODO listar pior vendedor

        return stringList;
    }

    private void addNonNullInfoInList(List<String> list, final String description, final Object value) {
        if (value != null && !StringUtils.isEmpty(value.toString())) {
            list.add(description + value.toString());
        }
    }

    public FileDataAnalysisReportVO incrementCustomersAmount() {
        customersAmount++;
        return this;
    }

    public FileDataAnalysisReportVO incrementSalesPeopleAmount() {
        salesPeopleAmount++;
        return this;
    }


}
