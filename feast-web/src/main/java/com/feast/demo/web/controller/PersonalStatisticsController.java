package com.feast.demo.web.controller;

import com.feast.demo.web.service.PersonalStatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by ggke on 2017/5/15.
 */

@Controller
@RequestMapping(value = "/PersonalStatistics")
public class PersonalStatisticsController {

    @Resource
    private PersonalStatisticsService personalStatisticsService;
}
