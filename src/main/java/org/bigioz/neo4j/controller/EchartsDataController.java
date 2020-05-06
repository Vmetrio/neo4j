package org.bigioz.neo4j.controller;

import org.bigioz.neo4j.echartsBean.EchartsData;
import org.bigioz.neo4j.echartsBean.Links;
import org.bigioz.neo4j.echartsBean.Nodes;
import org.bigioz.neo4j.entity.Alias;
import org.bigioz.neo4j.entity.Description;
import org.bigioz.neo4j.entity.Taxaset;
import org.bigioz.neo4j.entity.Taxon;
import org.bigioz.neo4j.repository.TaxasetRepository;
import org.bigioz.neo4j.repository.TaxonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class EchartsDataController {

    @Autowired
    TaxasetRepository taxasetRepositiory;

    @Autowired
    TaxonRepository taxonRepository;

    @PostMapping("/data/list")
    public EchartsData EchartsData() {
        EchartsData echartsData = new EchartsData();
        List<Nodes> nodes =new ArrayList<Nodes>();
        List<Links> links = new ArrayList<Links>();

        Iterable<Taxaset> TaxasetAll = taxasetRepositiory.findAll();
        for (Taxaset taxaset : TaxasetAll) {
            // 节点
            Nodes taxasetNode = new Nodes();
            taxasetNode.setName(taxaset.getTsname());
            taxasetNode.setDes(taxaset.getTsinfo());
            taxasetNode.setCategory(0);
            taxasetNode.setSymbolSize(80);
            nodes.add(taxasetNode);

            List<Taxon> taxons = taxaset.getTaxons();
            for (Taxon taxon : taxons) {
                // 节点
                Nodes taxonNode = new Nodes();
                taxonNode.setName(taxon.getChname());
                taxonNode.setDes(taxon.getScname());
                taxonNode.setCategory(1);
                taxonNode.setSymbolSize(60);
                nodes.add(taxonNode);
                // 关系
                Links taxonLink = new Links();
                taxonLink.setSource(taxon.getChname());
                taxonLink.setName("属于");
                taxonLink.setTarget(taxaset.getTsname());
                links.add(taxonLink);

                Taxon taxonNow = taxonRepository.findByScname(taxon.getScname());
                if(taxonNow.getSubspecies() != null){
                    Set<Taxon> subspecies = taxonNow.getSubspecies();
                    for (Taxon subspecie : subspecies) {
                        // 节点
                        Nodes node = new Nodes();
                        node.setName(subspecie.getChname());
                        node.setDes(subspecie.getScname());
                        node.setCategory(2);
                        node.setSymbolSize(50);
                        nodes.add(node);
                        // 关系
                        Links link = new Links();
                        link.setSource(subspecie.getChname());
                        link.setName("亚种");
                        link.setTarget(taxon.getChname());
                        links.add(link);
                    }
                }

                if(taxonNow.getGenus() != null){
                    Set<Taxon> evolutions = taxonNow.getGenus();
                    for (Taxon evolution : evolutions) {
                        // 节点
                        Nodes node = new Nodes();
                        node.setName(evolution.getChname());
                        node.setDes(evolution.getScname());
                        node.setCategory(1);
                        node.setSymbolSize(60);
                        nodes.add(node);
                        // 关系
                        Links link = new Links();
                        link.setSource(taxon.getChname());
                        link.setName("云雀属");
                        link.setTarget(evolution.getChname());
                        links.add(link);
                    }
                }

                // 描述
                if(taxonNow.getDescriptions() != null){
                    Set<Description> descriptions = taxonNow.getDescriptions();
                    for (Description description : descriptions) {
                        // 节点
                        Nodes node = new Nodes();
                        node.setName(taxonNow.getChname()+description.getDesname());
                        node.setDes(description.getDesinfo());
                        node.setCategory(3);
                        node.setSymbolSize(70);
                        nodes.add(node);
                        // 关系
                        Links link = new Links();
                        link.setSource(taxonNow.getChname()+description.getDesname());
                        link.setName("描述");
                        link.setTarget(taxon.getChname());
                        links.add(link);
                    }
                }

                // 别名
                if(taxonNow.getAliases() != null){
                    Set<Alias> aliases = taxonNow.getAliases();
                    for (Alias alias : aliases) {
                        // 节点
                        Nodes node = new Nodes();
                        node.setName(alias.getName());
                        node.setDes(alias.getRef());
                        node.setCategory(4);
                        node.setSymbolSize(50);;
                        nodes.add(node);
                        // 关系
                        Links link = new Links();
                        link.setSource(alias.getName());
                        link.setName("别名");
                        link.setTarget(taxon.getChname());
                        links.add(link);
                    }
                }
            }
        }

        Set<Nodes> userSet = new HashSet<>(nodes);
        List<Nodes> list = new ArrayList<>(userSet);
        //list.forEach(System.out::println);

        echartsData.setNodes(list);
        echartsData.setLinks(links);

        return echartsData;
    }

    @PostMapping("/data/species")
    public EchartsData EchartsData(HttpServletRequest request) {
        String species = request.getParameter("species");
        String depth = request.getParameter("depth");

        EchartsData echartsData = new EchartsData();
        List<Nodes> nodes =new ArrayList<Nodes>();
        List<Links> links = new ArrayList<Links>();
        List<Taxon> taxonList = new ArrayList<Taxon>();

        if("1".equals(depth)){
            taxonList = taxonRepository.findTaxonDepthInOne(species);
        }else{
            taxonList = taxonRepository.findTaxonDepthInTwo(species);
        }

        if (!taxonList.isEmpty()){
            //Taxon taxon = taxonList.get(0);
            for (Taxon taxon : taxonList) {
                // 节点
                Nodes taxonNode = new Nodes();
                taxonNode.setName(taxon.getChname());
                taxonNode.setDes(taxon.getScname());
                taxonNode.setCategory(0);
                taxonNode.setSymbolSize(60);
                nodes.add(taxonNode);

                Taxon taxonNow = taxonRepository.findByScname(taxon.getScname());
                if(taxonNow.getSubspecies() != null){
                    Set<Taxon> subspecies = taxonNow.getSubspecies();
                    for (Taxon subspecie : subspecies) {
                        // 节点
                        Nodes node = new Nodes();
                        node.setName(subspecie.getChname());
                        node.setDes(subspecie.getScname());
                        node.setCategory(1);
                        node.setSymbolSize(50);
                        nodes.add(node);
                        // 关系
                        Links link = new Links();
                        link.setSource(subspecie.getChname());
                        link.setName("亚种");
                        link.setTarget(taxon.getChname());
                        links.add(link);
                    }
                }

                if(taxonNow.getGenus() != null){
                    Set<Taxon> evolutions = taxonNow.getGenus();
                    for (Taxon evolution : evolutions) {
                        // 节点
                        Nodes node = new Nodes();
                        node.setName(evolution.getChname());
                        node.setDes(evolution.getScname());
                        node.setCategory(0);
                        node.setSymbolSize(60);
                        nodes.add(node);
                        // 关系
                        Links link = new Links();
                        link.setSource(taxon.getChname());
                        link.setName("云雀属");
                        link.setTarget(evolution.getChname());
                        links.add(link);
                    }
                }
            }

        }

        echartsData.setNodes(nodes);
        echartsData.setLinks(links);

        Set<Nodes> userSet = new HashSet<>(nodes);
        List<Nodes> list = new ArrayList<>(userSet);

        echartsData.setNodes(list);
        echartsData.setLinks(links);

        return echartsData;
    }

}
