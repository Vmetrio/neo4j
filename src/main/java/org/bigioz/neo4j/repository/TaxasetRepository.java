package org.bigioz.neo4j.repository;

import org.bigioz.neo4j.entity.Taxaset;
import org.springframework.data.repository.CrudRepository;

public interface TaxasetRepository extends CrudRepository<Taxaset, Long> {
    Taxaset findByTsname(String tsname);
}
