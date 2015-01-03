package de.hilbert.services;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import de.hilbert.entities.Stock;
import de.hilbert.repositories.StockRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author hilbert
 * @since 08.11.14
 */
@Component
public class StockService {

    public static Logger log = Logger.getLogger(StockService.class);

    @Autowired
    StockRepository stockRepository;

    @Autowired
    GraphDatabase graphDatabase;

    @Autowired
    YahooFinancialAPIService yahooFinancialAPIService;

    /**
     * @see org.springframework.data.repository.CrudRepository#save(Iterable)
     */
    @Transactional
    public Iterable<Stock> save(Iterable<Stock> entities) {
        return stockRepository.save(entities);
    }
    
    @Transactional
    public Iterator<Stock> findWithOnlineUpdate(Collection<String> symbols) {
        List<Stock> stocks = yahooFinancialAPIService.getStocksFromSymbols(new ArrayList<>(symbols));
        return stockRepository.save(stocks).iterator();
    }

    @Transactional
    public Iterator<Stock> findAllWithOnlineUpdate() {
        List<Stock> stocks = yahooFinancialAPIService.getStocksFromSymbols(Lists.newArrayList(stockRepository.findAllSymbols()));
        return stockRepository.save(stocks).iterator();
    }

    @Transactional(readOnly = true)
    public List<Stock> findAllStocks() {
        return Lists.newArrayList(stockRepository.findAll().iterator());
    }
}
