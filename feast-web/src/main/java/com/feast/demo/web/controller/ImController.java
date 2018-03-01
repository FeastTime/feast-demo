package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.feast.demo.web.entity.UserStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/im")
public class ImController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @RequestMapping(value = "/message",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String message(@RequestBody String text) throws Exception{

        if (null == text || text.length() == 0) {
            return "";
        }

        String message = getURLDecoderString(text);

        logger.info(message);

        return "";
    }


    @RequestMapping(value = "/userStatus",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String userStatus(@RequestBody String text) throws Exception{

        if (null == text || text.length() == 0) {
            return "";
        }

        String message = getURLDecoderString(text);

        List<UserStatus> list = JSONArray.parseArray(message, UserStatus.class);
//        ArrayList<UserStatus> list = JSON.parseObject(message, new TypeReference<ArrayList<UserStatus>>(){});

        logger.info("用户状态监听消息     ：      " + message);

        for (UserStatus userStatus : list) {
            logger.info("userStatus     ：   getUserid   :  "
                    + userStatus.getUserid()
                    + "   --getTime--- :  " + userStatus.getTime()
                    + "   --getOs--- :   " + userStatus.getOs()
                    + "   --getStatus--- :   " + userStatus.getStatus());
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
