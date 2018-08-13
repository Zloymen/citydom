package ru.citydom.testwork.error;

import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;

public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

    private static final QName CODE = new QName("code");
    private static final QName DESCRIPTION = new QName("description");

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        logger.warn("Exception processed ", ex);

        if (ex instanceof ServiceException) {
            ErrorEnum errorEnum = ((ServiceException) ex).getErrorEnum();
            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(CODE).addText(errorEnum.getCode().toString());
            detail.addFaultDetailElement(DESCRIPTION).addText(errorEnum.getDesc());
        }else{
            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(CODE).addText("-999");
            detail.addFaultDetailElement(DESCRIPTION).addText("Unknown error");
        }
    }

}