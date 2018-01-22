package com.feast.demo.web.controller.intercepter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.util.TokenUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class LoginInterceptor implements HandlerInterceptor {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        try{
            Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
            String text = parameterNames.nextElement();
            System.out.println(text);
            JSONObject jsono = JSON.parseObject(text);
            String token = jsono.getString("token");
            System.out.println(token);
            String userId = jsono.getString("userId");
            System.out.println(userId);
            String deviceId = jsono.getString("deviceId");
            System.out.println(deviceId);
            boolean b = TokenUtils.isValidToken(token,deviceId,userId);
            System.out.println(b);
            return b;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


    /*public String readJSONString(HttpServletRequest request){
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
    }*/

}
