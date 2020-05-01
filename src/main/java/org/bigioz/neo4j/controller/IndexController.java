package org.bigioz.neo4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @GetMapping("/")
    public String Index(){
        return "index";
    }

    @GetMapping("/index")
    public String Index2(HttpServletRequest request){
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

    @GetMapping("/search/{species}/{depth}")
    public String SearchIndex(@PathVariable("species") String species,
                              @PathVariable("depth") String depth){
        System.out.println(species);
        System.out.println(depth);
        return "index2";
    }

}
