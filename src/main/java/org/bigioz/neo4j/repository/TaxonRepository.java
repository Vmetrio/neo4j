package org.bigioz.neo4j.repository;

import org.bigioz.neo4j.entity.Taxon;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaxonRepository extends CrudRepository<Taxon, Long> {

    Taxon findByScname(String scname);

    Taxon findByTaxonId(String taxonId);

    @Query("match data=(t1:Taxon{scname:{0}})<-[*0..1]-(t2:Taxon) return data")
    List<Taxon> findTaxonDepthIn(String scname);

    @Query("match data=(t1:Taxon{scname:{0}})-[*0..1]->(t2:Taxon) return data")
    List<Taxon> findTaxonDepthOut(String scname);

}