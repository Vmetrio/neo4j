package org.bigioz.neo4j.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NodeEntity
public class Taxaset {
    @Id
    @GeneratedValue
    private Long id;
    private String tsname;
    private String tsinfo;

    @Relationship(type = "Have", direction = Relationship.INCOMING)
    private List<Taxon> taxons;

    /*
     * 指定taxon关系
     */
    public void taxonWith(Taxon taxon) {
        if (taxons == null) {
            taxons = new ArrayList<Taxon>();
        }
        taxons.add(taxon);
    }
}
