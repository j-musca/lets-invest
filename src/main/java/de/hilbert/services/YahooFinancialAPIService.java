package de.hilbert.services;

import de.hilbert.entities.Stock;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author hilbert
 * @since 14.11.14
 */
@Component
public class YahooFinancialAPIService {

    public static Logger log = Logger.getLogger(YahooFinancialAPIService.class);

    private String BASE_URL = "http://finance.yahoo.com/d/quotes.csv";
    private String CSV_FIELDS = "sl1";

    public List<Stock> getStocksFromSymbols(Collection<String> stockSymbols) {
        ArrayList<Stock> stocks = new ArrayList<>();
        try {
            URL url = new URL(BASE_URL + buildParameterString(stockSymbols, CSV_FIELDS));

            URLConnection urlConnection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

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
                stock.setPrice(fields[1]);
                stocks.add(stock);
            }

        } catch (java.io.IOException e) {
            log.error("returning empty list of stocks in cause of error", e);
        }

        return stocks;
    }

    private String buildParameterString(Collection<String> stockSymbols, String csvFields) {
        return "?s=" + StringUtils.arrayToDelimitedString(stockSymbols.toArray(), "+") + "&f=" + csvFields;
    }
}
