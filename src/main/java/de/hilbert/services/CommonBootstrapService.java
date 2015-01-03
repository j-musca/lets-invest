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
    
    @Autowired
    StockCsvImportService stockCsvImportService;

    @Transactional
    public void bootstrapStocks() {
        log.info("deleting existing data ...");
        stockRepository.deleteAll();
        log.info("importing companies from local csv-files ...");
        stockCsvImportService.importStocks();
        log.info("request data from yahoo for companies ...");
        stockService.findAllWithOnlineUpdate();
    }
}
