package com.feast.demo.web.controller;

import com.feast.demo.web.service.IMEvent;
import com.feast.demo.web.util.URLParser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/im")
public class ImController {

    Logger logger = Logger.getLogger(this.getClass().getName());


    @RequestMapping(value = "/message",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String loginUser(@RequestBody String text) {


        if (null == text || text.length() == 0) {
            return "";
        }

        String message = getURLDecoderString(text);

        logger.info(message);

        Map<String, String> map =  URLParser.urlSplit(message);

        if(map.get("").equals(IMEvent.ENTER_STORE)){

            // 进店操作
        }

        return "";
    }


    /**
     * url 解码
     *
     * @param inputStr 输入字符串
     * @return 解码后字符串
     */
    private String getURLDecoderString(String inputStr) {

        try {

            return java.net.URLDecoder.decode(inputStr, "UTF-8");

        } catch (Exception e) {

            logger.info("url 解码 失败！");
            e.printStackTrace();
        }

        return "";
    }

    
}
