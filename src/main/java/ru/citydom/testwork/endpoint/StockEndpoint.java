package ru.citydom.testwork.endpoint;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.citydom.stock.GetStockPrice;
import ru.citydom.stock.GetStockPriceResponse;
import ru.citydom.stock.SetStockPrice;
import ru.citydom.stock.SetStockPriceResponse;
import ru.citydom.testwork.entity.Stock;
import ru.citydom.testwork.error.ServiceException;
import ru.citydom.testwork.service.StockService;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import java.math.BigDecimal;

import static ru.citydom.testwork.error.ErrorEnum.*;

@Endpoint
@RequiredArgsConstructor
public class StockEndpoint {

    private static final String NAMESPACE_URI = "http://citydom.ru/stock";

    private final StockService stockService;

    @Resource
    WebServiceContext wsctxt;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetStockPrice")
    @ResponsePayload
    public GetStockPriceResponse getStockPrice(@RequestPayload GetStockPrice request) {
        GetStockPriceResponse response = new GetStockPriceResponse();
        String ind = request.getStockName();
        if(Strings.isNullOrEmpty(ind)) throw  new ServiceException(PARAM_IS_NULL);
        Stock stock = stockService.getByInd(ind);
        if(stock == null) throw  new ServiceException(STOCK_NOT_FOUND);
        response.setPrice(stock.getCost().floatValue());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SetStockPrice")
    @ResponsePayload
    public SetStockPriceResponse setStockPrice(@RequestPayload SetStockPrice request) {
        SetStockPriceResponse response = new SetStockPriceResponse();
        String ind = request.getInd();
        String name = request.getStockName();
        Float price = request.getPrice();
        if(Strings.isNullOrEmpty(ind) || Strings.isNullOrEmpty(name)) throw  new ServiceException(PARAM_IS_NULL);

        if(stockService.getByInd(ind) != null) throw new ServiceException(STOCK_IS_EXIST);

        stockService.save(new Stock(ind, name, new BigDecimal(price)));


        return response;
    }
}
