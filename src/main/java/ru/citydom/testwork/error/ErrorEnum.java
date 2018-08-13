package ru.citydom.testwork.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorEnum {

    STOCK_NOT_FOUND(10, "stock not found"),
    PARAM_IS_NULL(20, "param is null"),
    STOCK_IS_EXIST(30, "stock is exist")
    ;

    @Getter
    private final Integer code;

    @Getter
    private final String desc;
}
