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
    private String taxonId;
    private String scname;
    private String chname;

    @Relationship(type="Subspecies", direction = Relationship.INCOMING)
    private Set<Taxon> subspecies;

    @Relationship(type = "Genus", direction = Relationship.OUTGOING)
    public Set<Taxon> genus;

    // 描述
    @Relationship(type = "Descriptions", direction = Relationship.INCOMING)
    private Set<Description> descriptions;

    // 别名
    @Relationship(type = "Aliases", direction = Relationship.INCOMING)
    private Set<Alias> aliases;

    /*
     * 指定亚种关系
     */
    public void subWith(Taxon taxon) {
        if (subspecies == null) {
            subspecies = new HashSet<>();
        }
        subspecies.add(taxon);
    }

    public void genWith(Taxon taxon) {
        if (genus == null) {
            genus = new HashSet<>();
        }
        genus.add(taxon);
    }

    public void desWith(Description description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }
        descriptions.add(description);
    }

    public void aliWith(Alias alias) {
        if (aliases == null) {
            aliases = new HashSet<>();
        }
        aliases.add(alias);
    }
}
