package com.feast.demo.web.entity;

import lombok.Data;

/**
 * Created by ggke on 2017/5/15.
 */

@Data
public class CoordinatePoint {

    /**
     * y轴数据
     */
    private String yAxisNum;//125",

    /**
     * x轴数据
     */
    private String xAxisNum;//":"4",

    /**
     * X轴文字信息
     */
    private String xAxisText;//":"6月5日"

    /**
     * Y轴文字信息
     */
    private String yAxisText;//":"6月5日"
}
