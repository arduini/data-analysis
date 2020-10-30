package org.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DataFileLineTypeEnum {

    SALESMAN("001"),
    CUSTOMER("002"),
    PURCHASE("003");

    private final String lineCode;

    public static DataFileLineTypeEnum getByCode(final String code) {

        return Arrays.stream(values())
                .filter(value -> value.getLineCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
