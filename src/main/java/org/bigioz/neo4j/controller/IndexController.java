package org.bigioz.neo4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

}
