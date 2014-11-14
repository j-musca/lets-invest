package de.hilbert.services;

import de.hilbert.repositories.StockRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @author hilbert
 * @since 14.11.14
 */
@Component
public class CommonBootstrapService {

    public static Logger log = Logger.getLogger(CommonBootstrapService.class);

    @Autowired
    StockService stockService;

    @Autowired
    StockRepository stockRepository;

    @Transactional
    public void bootstrapStocks() {
        log.info("deleting existing data ...");
        stockRepository.deleteAll();
        log.info("creating new data ...");
        stockService.findWithOnlineUpdate(Arrays.asList("AAPL", "GOOG", "MSFT"));
    }
}
