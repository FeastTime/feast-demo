package com.feast.demo.web.entity;

import com.feast.demo.web.controller.WSService;
import lombok.Data;

import javax.websocket.Session;

/**
 * Created by matao on 2017/10/31.
 */
@Data
public class WsBean {
    private WSService session;
    private String mobileNo;
    private String storeId;

}
