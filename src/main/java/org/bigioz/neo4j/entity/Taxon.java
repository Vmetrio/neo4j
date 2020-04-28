package org.bigioz.neo4j.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NodeEntity
public class Taxon {
    @Id
    @GeneratedValue
    private Long id;
    private String scname;
    private String chname;

    /**
     * 忽略关系的方向
     */
    @Relationship(type="Subspecies", direction = Relationship.INCOMING)
    private Set<Taxon> subspecies;

    @Relationship(type = "Evolution", direction = Relationship.INCOMING)
    public Set<Taxon> evolutions;

    /*
     * 指定亚种关系
     */
    public void subWith(Taxon taxon) {
        if (subspecies == null) {
            subspecies = new HashSet<>();
        }
        subspecies.add(taxon);
    }

    public void evoWith(Taxon taxon) {
        if (evolutions == null) {
            evolutions = new HashSet<>();
        }
        evolutions.add(taxon);
    }
}
