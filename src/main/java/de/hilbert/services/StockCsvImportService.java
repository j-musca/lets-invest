package de.hilbert.services;

import de.hilbert.entities.Stock;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hilbert
 * @since 19.12.14
 */
@Component
public class StockCsvImportService {

    public static Logger log = Logger.getLogger(StockCsvImportService.class);

    @Autowired
    StockService stockService;
    
    public void importStocks() {
        ArrayList<Stock> stocks = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src/main/resources/csv/companylist-amex.csv"))));
            List<String> unparsedStocks = new ArrayList<>();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                unparsedStocks.add(inputLine);
                log.info(inputLine);
            }
            in.close();

            for (String unparsedStock : unparsedStocks) {
                String[] fields = unparsedStock.split(",");
                Stock stock = new Stock();
                stock.setSymbol(fields[0]);
                stock.setCompanyName(fields[1]);
                stocks.add(stock);
            }

            stockService.save(stocks);
            
        } catch (IOException e) {
            log.error(e);
        }

    }
}
