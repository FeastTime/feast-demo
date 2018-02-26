package com.feast.demo.web.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/im")
public class ImController {

    Logger logger = Logger.getLogger(this.getClass().getName());


    @RequestMapping(value = "/message",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String loginUser(@RequestBody String text) {

        String message = text;

        logger.info(message);

        return "";
    }

    
}
