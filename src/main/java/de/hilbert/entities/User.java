package de.hilbert.entities;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * @author hilbert
 * @since 23.11.14
 */
@NodeEntity
public class User {

    @GraphId
    private Long id;

    String userName;

    public User() {
    }
}
