package com.feast.demo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ggke on 2017/11/1.
 */
@Service
public class TestService {

    @Autowired
    private Test2Service test2Service;

    public String m1(){
        return "this is Test2Service"+"\n"+test2Service.m1();
    }


}
