package org.bigioz.neo4j.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Getter
@Setter
@ToString
@NodeEntity
public class Description {
    @Id
    @GeneratedValue
    private Long id;
    private String taxonId;
    private String desname;
    private String desinfo;
}
