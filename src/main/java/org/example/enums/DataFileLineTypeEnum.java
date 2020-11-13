package org.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DataFileLineTypeEnum {

    SALESMAN("^(001)\\ç(\\d+)\\ç([ A-Za-z]+)\\ç(\\d+\\.*\\d*)$"),
    CUSTOMER("^(002)\\ç(\\d+)\\ç([ A-Za-z]+)\\ç([ A-Za-z]+)$"),
    SALE("^(003)\\ç(\\d+)\\ç\\[(.+)\\]\\ç([ A-Za-z]+)$");

    private final String regex;
}
