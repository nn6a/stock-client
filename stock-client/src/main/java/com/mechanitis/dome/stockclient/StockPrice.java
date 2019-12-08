package com.mechanitis.dome.stockclient;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockPrice {
    private String symbol;
    private Double price;
    private LocalDateTime time;
}
