package cn.echocow.xiaoming.Utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.Arrays;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 21:37
 */
public class CustomBeanUtils {
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        return Arrays.stream(beanWrapper.getPropertyDescriptors())
                .filter(propertyDescriptor -> beanWrapper.getPropertyValue(propertyDescriptor.getName()) == null)
                .map(FeatureDescriptor::getName).toArray(String[]::new);
    }
}
