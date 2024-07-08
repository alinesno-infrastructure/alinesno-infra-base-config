package com.alinesno.infra.base.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DemoRest {

    @GetMapping("/index")
    public String index(){
        return "this is a test , agentProperties = "  ;
    }

}
