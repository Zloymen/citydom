package ru.citydom.testwork.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ind;

    @Column
    private String name;

    @Column
    private BigDecimal cost;

    public Stock(String ind, String name, BigDecimal cost){

        this.ind = ind;
        this.name = name;
        this.cost = cost;
    }
}
