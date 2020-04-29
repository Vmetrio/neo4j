package org.bigioz.neo4j.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.bigioz.neo4j.entity.Alias;
import org.bigioz.neo4j.entity.Description;
import org.bigioz.neo4j.entity.Taxaset;
import org.bigioz.neo4j.entity.Taxon;
import org.bigioz.neo4j.repository.AliasRepository;
import org.bigioz.neo4j.repository.DescriptionRepository;
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
    @Autowired
    DescriptionRepository descriptionRepository;
    @Autowired
    AliasRepository aliasRepository;

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
            Set<Taxon> evoSet = evoTaxon.getGenus();
            evoSet.clear();
            taxonRepository.save(evoTaxon);
        }else{
            evoTaxon = taxonRepository.findByScname("物种进化(测试删除关系)");
            evoTaxon.setScname("物种进化(测试建立关系)");
            evoTaxon.genWith(taxon);
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
    public Taxon Taxon(){
        Taxon taxon = taxonRepository.findByScname("Alauda arvensis");
        return taxon;
    }

    @GetMapping("/taxon/depth")
    public List<Taxon> TaxonDepth(){
        List<Taxon> taxon = taxonRepository.findTaxonDepthIn("Alauda arvensis");
        return taxon;
    }

    // 增
    @GetMapping("/save")
    @Transactional
    public JSON CreateTaxaset() {
        JSONObject json = new JSONObject();
        json.put("message", "ok");

        Description description1 = new Description();
        description1.setTaxonId("855BF3EB-C80D-4F44-9412-E0F819757B0D");
        description1.setDesname("形态描述");
        description1.setDesinfo("中等体型(17厘米)的云雀。上体褐色斑驳，冠羽短。" +
                "特征为棕色的小覆羽成独特的三角形肩斑，白色的眉纹绕棕色的耳羽而过。");
        Description description2 = new Description();
        description2.setTaxonId("57526169-6c6a-4c36-95fb-58863f561e2d");
        description2.setDesname("形态描述");
        description2.setDesinfo("体小(15厘米)的褐色斑驳而似鹨的鸟。略具浅色眉纹及羽冠。" +
                "与鹨的区别在嘴较厚重，飞行较柔弱且姿势不同。");
        Description description3 = new Description();
        description3.setTaxonId("29e58761-51d2-4e55-95d8-82f986e7afb1");
        description3.setDesname("形态描述");
        description3.setDesinfo("中等体型(18厘米)而具灰褐色杂斑的百灵。" +
                "顶冠及耸起的羽冠具细纹，尾分叉，羽缘白色，后翼缘的白色于飞行时可见。");
        List<Description> descriptionList = new ArrayList<Description>();
        descriptionList.add(description1);
        descriptionList.add(description2);
        descriptionRepository.save(description3);
        descriptionRepository.saveAll(descriptionList);

        Alias alias1 = new Alias();
        alias1.setTaxonId("855BF3EB-C80D-4F44-9412-E0F819757B0D");
        alias1.setName("Japanese Skylark");
        alias1.setRef("约翰.马敬能,卡伦.菲利普斯,何芬奇，2000. 中国鸟类野外手册. 长沙: 湖南教育出版社");
        Alias alias2 = new Alias();
        alias2.setTaxonId("57526169-6c6a-4c36-95fb-58863f561e2d");
        alias2.setName("大鹨");
        alias2.setRef("约翰.马敬能，卡伦.菲利普斯，何芬奇（2000），中国鸟类野外手册，湖南教育出版社,pp1-571");
        List<Alias> aliasList = new ArrayList<Alias>();
        aliasList.add(alias1);
        aliasList.add(alias2);
        aliasRepository.saveAll(aliasList);

        Taxaset thisTaxaset = new Taxaset();
        thisTaxaset.setTsname("中国鸟类数据库");
        thisTaxaset.setTsinfo("中国鸟类数据库");

        // 云雀亚种
        Taxon taxon1 = new Taxon();
        List<Taxon> taxonList = new ArrayList<Taxon>();
        taxon1.setScname("Alauda arvensis dulcllvoxHume");
        taxon1.setChname("新疆亚种");
        Taxon taxon2 = new Taxon();
        taxon2.setScname("Alauda arvensis kiborti Saleskij");
        taxon2.setChname("北方亚种");

        // 日本云雀
        Taxon japonicaTaxon = new Taxon();
        japonicaTaxon.setScname("Alauda japonica");
        japonicaTaxon.setChname("日本云雀");
        japonicaTaxon.desWith(description1);
        japonicaTaxon.aliWith(alias1);

        // 小云雀
        Taxon gulgulaTaxon = new Taxon();
        gulgulaTaxon.setScname("Alauda gulgula");
        gulgulaTaxon.setChname("小云雀");
        gulgulaTaxon.desWith(description2);
        gulgulaTaxon.aliWith(alias2);

        // 云雀
        Taxon taxon = new Taxon();
        taxon.setScname("Alauda arvensis");
        taxon.setChname("云雀");
        taxon.desWith(description3);
        taxon.aliWith(alias2);

        taxon.subWith(taxon1);
        taxon.subWith(taxon2);

        taxon.genWith(japonicaTaxon);
        japonicaTaxon.genWith(gulgulaTaxon);
        gulgulaTaxon.genWith(taxon);

        taxonList.add(taxon1);
        taxonList.add(taxon2);
        taxonList.add(taxon);
        taxonList.add(japonicaTaxon);
        taxonList.add(gulgulaTaxon);
        taxonRepository.saveAll(taxonList);

        thisTaxaset.taxonWith(taxon);
        thisTaxaset.taxonWith(japonicaTaxon);
        thisTaxaset.taxonWith(gulgulaTaxon);
        taxasetRepositiory.save(thisTaxaset);

        return json;
    }

}
