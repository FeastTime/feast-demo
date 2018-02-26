package com.feast.demo.web.controller.intercepter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.util.TokenUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.logging.Logger;

public class LoginInterceptor implements HandlerInterceptor {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String requestURI = httpServletRequest.getRequestURI();
        System.out.println(requestURI);

        if(requestURI.contains("/websocket")
                || requestURI.contains("/im/message")){

            System.out.println("放过路径  ： " + requestURI);

            return true;
        }

        String text = readJSONString(httpServletRequest);
        httpServletRequest.setAttribute("json",text);
        logger.info("收到内容 ： " + text);
        JSONObject jsono = JSON.parseObject(text);

        if (null == jsono) {
            httpServletResponse.getWriter().write("{\"resultCode\":\"201\",\"resultMsg\":\"token invalid!\"}");
            return false;
        }

        String deviceId = jsono.getString("deviceId");
        String userId = jsono.getString("userId");
        String token = jsono.getString("token");
        boolean b = TokenUtils.isValidToken(token, deviceId, userId);
        logger.info(b+"");

        if (!b) {
            httpServletResponse.getWriter().write("{\"resultCode\":\"201\",\"resultMsg\":\"token invalid!\"}");
            return false;
        }


        logger.info("TokenUtils" + b);
        return b;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public String readJSONString(HttpServletRequest request){
        StringBuffer json = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                json.append(line);
            }
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
        return json.toString();
    }

}
