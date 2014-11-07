package de.hilbert.repositories;

import de.hilbert.entities.Stock;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hilbert
 * @since 31.10.14
 */
@Repository
public interface StockRepository extends GraphRepository<Stock>{}
