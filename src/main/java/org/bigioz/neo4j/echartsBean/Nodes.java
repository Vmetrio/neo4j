package org.bigioz.neo4j.echartsBean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Nodes {
    private String name;
    private String des;
    private Integer symbolSize;
    private Integer category;

    public boolean equals(Object obj) {
        Nodes u = (Nodes) obj;
        return name.equals(u.name);
    }

    public int hashCode() {
        String in = name;
        return in.hashCode();
    }
}
