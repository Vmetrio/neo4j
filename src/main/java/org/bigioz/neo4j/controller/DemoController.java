package org.bigioz.neo4j.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.bigioz.neo4j.entity.Taxaset;
import org.bigioz.neo4j.entity.Taxon;
import org.bigioz.neo4j.repository.TaxasetRepository;
import org.bigioz.neo4j.repository.TaxonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class DemoController {

    @Autowired
    TaxasetRepository taxasetRepositiory;

    @Autowired
    TaxonRepository taxonRepository;

    // 增
    @GetMapping("/save")
    @Transactional
    public JSON CreateTaxaset() {
        JSONObject json = new JSONObject();
        json.put("message", "ok");

        Taxaset thisTaxaset = new Taxaset();
        thisTaxaset.setTsname("中国鸟类数据库");
        thisTaxaset.setTsinfo("中国鸟类数据库");

        Taxon taxon1 = new Taxon();
        List<Taxon> taxonList = new ArrayList<Taxon>();
        taxon1.setScname("Alauda arvensis dulcllvoxHume");
        taxon1.setChname("新疆亚种");

        Taxon taxon2 = new Taxon();
        taxon2.setScname("Alauda arvensis kiborti Saleskij");
        taxon2.setChname("北方亚种");

        Taxon taxon = new Taxon();
        taxon.setScname("Alauda arvensis");
        taxon.setChname("云雀");
        taxon.subWith(taxon1);
        taxon.subWith(taxon2);

        taxonList.add(taxon1);
        taxonList.add(taxon2);
        taxonList.add(taxon);
        taxonRepository.saveAll(taxonList);

        Taxon evoTaxon = new Taxon();
        evoTaxon.setScname("物种进化");
        evoTaxon.setChname("Species evolution");
        evoTaxon.evoWith(taxon);
        taxonRepository.save(evoTaxon);

        thisTaxaset.taxonWith(taxon);
        taxasetRepositiory.save(thisTaxaset);

        return json;
    }

    // 删
    @GetMapping("/delete/all")
    public JSON TaxasetDelete(){
        JSONObject json = new JSONObject();
        taxasetRepositiory.deleteAll();
        taxonRepository.deleteAll();
        json.put("message", "ok");
        return json;
    }

    // 改
    @GetMapping("/taxon/update/{n}")
    public Taxon TaxonUpdate(@PathVariable("n") long n){
        Taxon taxon = taxonRepository.findByScname("Alauda arvensis");
        Taxon evoTaxon = new Taxon();
        if(n == 0){
            evoTaxon = taxonRepository.findByScname("物种进化(测试建立关系)");
            evoTaxon.setScname("物种进化(测试删除关系)");
            Set<Taxon> evoSet = evoTaxon.getEvolutions();
            evoSet.clear();
            taxonRepository.save(evoTaxon);
        }else{
            evoTaxon = taxonRepository.findByScname("物种进化(测试删除关系)");
            evoTaxon.setScname("物种进化(测试建立关系)");
            evoTaxon.evoWith(taxon);
            taxonRepository.save(evoTaxon);
        }
        return evoTaxon;
    }

    // 查
    @GetMapping("/get")
    public Taxaset TaxasetByName(){
        String name = "中国鸟类数据库";
        return taxasetRepositiory.findByTsname(name);
    }

    @GetMapping("/taxon/get/{name}")
    public Taxon TaxonByName(@PathVariable("name") String name){
        return taxonRepository.findByScname(name);
    }

    @GetMapping("/taxon/get")
    public String Taxon(){
        Taxon taxon = taxonRepository.findByScname("Alauda arvensis");
        return "ok";
    }

    @GetMapping("/taxon/depth")
    public List<Taxon> TaxonDepth(){
        List<Taxon> taxon = taxonRepository.findTaxonDepth("物种进化");
        return taxon;
    }

}
