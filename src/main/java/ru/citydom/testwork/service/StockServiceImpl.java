package ru.citydom.testwork.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.citydom.testwork.annotation.ReadOnlyTransactional;
import ru.citydom.testwork.dao.StockDao;
import ru.citydom.testwork.entity.Stock;

@Service
@RequiredArgsConstructor
@ReadOnlyTransactional
public class StockServiceImpl implements StockService {

    private final StockDao stockDao;


    @Override
    public Stock getByInd(String index) {
        return stockDao.getByIndEquals(index);
    }

    @Transactional
    @Override
    public void save(Stock stock) {
        stockDao.save(stock);
    }
}
