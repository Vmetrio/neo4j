package org.bigioz.neo4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        //System.out.println(dataId);
        return "taxon/info";
    }

    @GetMapping("/search")
    public String Search(){
        return "taxon/search";
    }

    @GetMapping("/search/{species}/{depth}")
    public String SearchIndex(Model model, @PathVariable("species") String species,
                              @PathVariable("depth") String depth){
        model.addAttribute("species", species);
        model.addAttribute("depth", depth);
        return "taxon/speciesDepth";
    }

}
