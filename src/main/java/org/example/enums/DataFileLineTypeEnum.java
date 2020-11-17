package org.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor
public enum DataFileLineTypeEnum {

    SALESMAN("001", "^(001)\\ç(\\d+)\\ç([ A-Za-z]+)\\ç(\\d+\\.*\\d*)$"),
    CUSTOMER("002", "^(002)\\ç(\\d+)\\ç([ A-Za-z]+)\\ç([ A-Za-z]+)$"),
    SALE("003", "^(003)\\ç(\\d+)\\ç\\[(.+)\\]\\ç([ A-Za-z]+)$");

    private static final String LINE_CODE_REGEX = "^(\\d*)\\ç(.*)$";

    private final String code;
    private final String regex;

    public static Optional<DataFileLineTypeEnum> fromLine(final String line) {

        Matcher saleItemMatcher = Pattern.compile(LINE_CODE_REGEX).matcher(line);
        if (saleItemMatcher.find()) {
            final var code = saleItemMatcher.group(1);
            return Arrays.stream(values())
                    .filter(c -> c.code.equals(code))
                    .findFirst();
        }
        else {
            return Optional.empty();
        }
    }
}
