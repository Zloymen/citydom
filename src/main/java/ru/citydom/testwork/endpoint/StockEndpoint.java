package ru.citydom.testwork.endpoint;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.citydom.stock.*;
import ru.citydom.testwork.entity.Stock;
import ru.citydom.testwork.error.ServiceException;
import ru.citydom.testwork.service.StockService;
import ru.citydom.testwork.service.UserService;

import java.math.BigDecimal;

import static ru.citydom.testwork.error.ErrorEnum.*;

@Endpoint
@RequiredArgsConstructor
public class StockEndpoint {

    private static final String NAMESPACE_URI = "http://citydom.ru/stock";

    private final StockService stockService;

    private final UserService userService;

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

        if(!userService.currentUserIsAdmin()) throw new ServiceException(ACCESS_DENIED);

        String ind = request.getInd();
        String name = request.getStockName();
        Float price = request.getPrice();
        if(Strings.isNullOrEmpty(ind) || Strings.isNullOrEmpty(name)) throw  new ServiceException(PARAM_IS_NULL);

        if(stockService.getByInd(ind) != null) throw new ServiceException(STOCK_IS_EXISTS);

        stockService.save(new Stock(ind, name, BigDecimal.valueOf(price)));

        response.setResult(1);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateUser")
    @ResponsePayload
    public CreateUserResponse createUser(@RequestPayload CreateUser request) {
        CreateUserResponse response = new CreateUserResponse();

        if(!userService.currentUserIsAdmin()) throw new ServiceException(ACCESS_DENIED);

        String username = request.getUserName();
        String password = request.getPassword();

        if(Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) throw  new ServiceException(PARAM_IS_NULL);

        if(userService.getByUsername(username) != null) throw  new ServiceException(USER_IS_EXISTS);

        userService.create(username, password, request.getUserRole());

        response.setResult(1);

        return response;
    }


}
