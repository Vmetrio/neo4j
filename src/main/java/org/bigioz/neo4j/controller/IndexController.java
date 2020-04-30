package org.bigioz.neo4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @GetMapping("/")
    public String Index(){
        return "index";
    }

    @GetMapping("/index")
    public String Index2(){
        return "index2";
    }

    @GetMapping("/info/{dataId}")
    public String Info(@PathVariable("dataId") String dataId){
        System.out.println(dataId);
        return "info";
    }

    @GetMapping("/search")
    public String Search(){
        return "search";
    }

}
