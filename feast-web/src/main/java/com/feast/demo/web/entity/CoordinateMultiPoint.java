package com.feast.demo.web.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by ggke on 2017/5/15.
 */

@Data
public class CoordinateMultiPoint {

    /**
     * y轴数据，同一X轴或Y轴有可能有多个数值，所以用数组
     */
    private List<String> yAxisNum;//125",

    /**
     * x轴数据，同一X轴或Y轴有可能有多个数值，所以用数组
     */
    private List<String> xAxisNum;//":"4",

    /**
     * X轴文字信息
     */
    private String xAxisText;//":"6月5日"

    /**
     * Y轴文字信息
     */
    private String yAxisText;//":"6月5日"
}
