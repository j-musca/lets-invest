package de.hilbert.entities;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * @author hilbert
 * @since 23.11.14
 */
@RelationshipEntity
public class Buy {

    @GraphId
    private Long id;

    @StartNode
    User user;

    @EndNode
    Stock stock;

    
}
