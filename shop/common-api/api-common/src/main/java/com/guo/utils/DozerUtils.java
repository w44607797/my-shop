package com.guo.utils;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author guokaifeng
 * @createDate: 2022/4/6
 **/
@Configuration
public class DozerUtils {
    static DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List destinationList = Lists.newArrayList();
        for (Iterator iterator = sourceList.iterator(); iterator.hasNext(); ) {
            Object sourceObject = iterator.next();
            Object destinationObject = dozerBeanMapper.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }
}
