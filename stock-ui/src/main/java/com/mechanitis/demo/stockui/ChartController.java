package com.mechanitis.demo.stockui;

import com.mechanitis.dome.stockclient.StockPrice;
import com.mechanitis.dome.stockclient.WebClientStockClient;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static java.lang.String.valueOf;
import static javafx.collections.FXCollections.observableArrayList;

@Component
public class ChartController implements Consumer<StockPrice> {
    @FXML
    public LineChart<String, Double> chart;
    private WebClientStockClient webClientStockClient;
    private ObservableList<Data<String, Double>> seriesData = observableArrayList();

    public ChartController(WebClientStockClient webClientStockClient) {
        this.webClientStockClient = webClientStockClient;
    }

    @FXML
    public void initialize() {
        String symbol = "SYMBOL";
        ObservableList<Series<String, Double>> data = observableArrayList();
        data.add(new Series<>(symbol,  seriesData));
        chart.setData(data);

        webClientStockClient.pricesFor(symbol)
                            .subscribe(this);
    }

    @Override
    public void accept(StockPrice stockPrice) {
        Platform.runLater(() -> seriesData.add(new Data<>(
                valueOf(stockPrice.getTime().getSecond()),
                stockPrice.getPrice()
        )));
    }
}
