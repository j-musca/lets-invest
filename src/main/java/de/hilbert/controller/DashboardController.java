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
public class DashboardController {

    public static Logger log = Logger.getLogger(DashboardController.class);

    @Autowired
    StockService stockService;

    @RequestMapping("/")
    public String index() throws IOException {

        stockService.importSomeStocks();
        stockService.readGraph();

        return "Greetings from Spring Boot!";
    }
}
