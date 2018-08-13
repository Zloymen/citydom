package ru.citydom.testwork.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.citydom.testwork.entity.Stock;

public interface StockDao extends JpaRepository<Stock, Long> {

    Stock getByIndEquals(String index);
}
