package com.alinesno.infra.base.config;

import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.base.config.agent.properties.AgentProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoRest {

    private static final Logger log = LoggerFactory.getLogger(DemoRest.class) ;

//    @Autowired
    private AgentProperties agentProperties ;

    @GetMapping("/index")
    public String index(){
        return "this is a test , agentProperties = " + JSONObject.toJSON(agentProperties)  ;
    }

}
