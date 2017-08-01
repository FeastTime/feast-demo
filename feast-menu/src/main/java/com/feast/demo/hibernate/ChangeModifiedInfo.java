package com.feast.demo.hibernate;

/**
 * Created by xuewu on 2017/1/5.
 * 更新实体的时候是否更新lastModified字段，本来在TimedEntityListener做特殊处理即可，但由于base 不依赖entity，所以必须写接口处理
 */
public interface ChangeModifiedInfo {
    boolean isChangeLastModifiedField();
}

