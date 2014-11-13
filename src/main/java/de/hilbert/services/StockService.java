package de.hilbert.services;

import de.hilbert.entities.Stock;
import de.hilbert.repositories.StockRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author hilbert
 * @since 08.11.14
 */
@Component
public class StockService {

    public static Logger log = Logger.getLogger(StockService.class);

    @Qualifier("stockRepository")
    @Autowired
    StockRepository stockRepository;

    @Autowired
    GraphDatabase graphDatabase;

    @Transactional
    public void importSomeStocks() throws IOException {
        URL url = new URL("http://finance.yahoo.com/d/quotes.csv?s=AAPL+GOOG+MSFT&f=sl1");
        URLConnection urlConnection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        List<String> unparsedStocks = new ArrayList<>();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            unparsedStocks.add(inputLine);
            log.info(inputLine);
        }
        in.close();


        List<Stock> stocks = new ArrayList<>();
        for (String unparsedStock : unparsedStocks) {
            String[] fields = unparsedStock.split(",");
            Stock stock = new Stock();
            stock.setSymbol(fields[0]);
            stock.setPrice(fields[1]);
            stocks.add(stock);
        }

        stockRepository.save(stocks);


    }

    @Transactional
    public void readGraph() {
        Iterator<Stock> stockResult = stockRepository.findAll().iterator();

        while (stockResult.hasNext()) {
            log.info("following found and saved:" + stockResult.next().toString());
        }
    }
}
