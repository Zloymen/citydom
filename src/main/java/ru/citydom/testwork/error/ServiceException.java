package ru.citydom.testwork.error;

import lombok.Getter;


public class ServiceException extends RuntimeException {

    @Getter
    private final ErrorEnum errorEnum;

    public ServiceException(ErrorEnum errorEnum){
        super("server error");
        this.errorEnum = errorEnum;
    }

}