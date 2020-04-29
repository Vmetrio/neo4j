package org.bigioz.neo4j.echartsBean;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EchartsData {
    private List<Nodes> nodes = new ArrayList<Nodes>();
    private List<Links> links = new ArrayList<Links>();
}
