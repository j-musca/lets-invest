package de.hilbert.controller;

import de.hilbert.entities.Stock;
import de.hilbert.repositories.StockRepository;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hilbert
 * @since 31.10.14
 */
@RestController
public class HelloController {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    GraphDatabase graphDatabase;

    @RequestMapping("/")
    public String index() throws IOException {

        //http://stackoverflow.com/questions/10040954/alternative-to-google-finance-api
        //http://www.jarloo.com/yahoo_finance/
//        URL url = new URL("http://finance.yahoo.com/d/quotes.csv?s=AAPL+GOOG+MSFT&f=sl1");
//        URLConnection urlConnection = url.openConnection();
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(
//                        urlConnection.getInputStream()));
//
//        List<String> unparsedStocks = new ArrayList<>();
//        String inputLine;
//        while ((inputLine = in.readLine()) != null) {
//            unparsedStocks.add(inputLine);
//            System.out.println(inputLine);
//        }
//        in.close();
//
//
//        List<Stock> stocks = new ArrayList<>();
//        for(String unparsedStock : unparsedStocks) {
//            String[] fields = unparsedStock.split(",");
//            Stock stock = new Stock();
//            stock.setSymbol(fields[0]);
//            stock.setPrice(fields[1]);
//            stocks.add(stock);
//        }
//
//        Transaction tx = graphDatabase.beginTx();
//        try {
//            stockRepository.save(stocks);
//            tx.success();
//        } finally {
//            tx.close();
//        }
//
//        Iterable<Stock> stockResult = stockRepository.findAll();

        Transaction tx = graphDatabase.beginTx();
        try {
            Stock stock = new Stock();
            stock.setSymbol("SYMBOL");
            stock.setPrice("1");
            stockRepository.save(stock);
            tx.success();
        } finally {
            tx.close();
        }

        return "Greetings from Spring Boot!";
    }
}
