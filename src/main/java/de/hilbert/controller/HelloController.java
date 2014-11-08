package de.hilbert.controller;

import de.hilbert.services.StockService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author hilbert
 * @since 31.10.14
 */
@RestController
public class HelloController {

    public static Logger log = Logger.getLogger(HelloController.class);

    @Autowired
    StockService stockService;

    @RequestMapping("/")
    public String index() throws IOException {

        //http://stackoverflow.com/questions/10040954/alternative-to-google-finance-api
        //http://www.jarloo.com/yahoo_finance/

        stockService.importSomeStocks();
        stockService.readGraph();

        return "Greetings from Spring Boot!";
    }
}
