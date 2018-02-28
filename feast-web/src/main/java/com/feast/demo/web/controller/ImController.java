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
    public String loginUser(@RequestBody String text) throws Exception{

        if (null == text || text.length() == 0) {
            return "";
        }

        String message = getURLDecoderString(text);

        logger.info(message);

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
