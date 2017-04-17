package com.feast.demo.ad.entity;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ggke on 2017/4/12.
 */
public class SerializationOptimizerImpl   implements SerializationOptimizer {
    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<Class>();
        classes.add(TAd.class);
        classes.add(String.class);
        return classes;
    }
}
