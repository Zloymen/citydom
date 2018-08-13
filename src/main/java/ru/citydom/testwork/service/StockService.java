package ru.citydom.testwork.service;

import ru.citydom.testwork.entity.Stock;

public interface StockService {

    Stock getByInd(String index);

    void save(Stock stock);
}
