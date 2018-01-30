package com.feast.demo.web.entity;

import com.feast.demo.user.entity.User;
import com.feast.demo.web.controller.WSService;
import lombok.Data;


/**
 * Created by matao on 2017/10/31.
 */
@Data
public class WsBean {
    private WSService wsService;
    private User user;
}
