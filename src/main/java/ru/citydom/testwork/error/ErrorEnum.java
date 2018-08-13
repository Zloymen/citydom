package ru.citydom.testwork.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorEnum {

    STOCK_NOT_FOUND(10, "stock not found"),
    PARAM_IS_NULL(20, "param is null"),
    STOCK_IS_EXISTS(30, "stock is exists"),
    USER_IS_EXISTS(40, "user is exists"),
    ROLE_NOT_FOUND(50, "role not found"),
    ACCESS_DENIED(60, "access denied"),
    ;

    @Getter
    private final Integer code;

    @Getter
    private final String desc;
}
