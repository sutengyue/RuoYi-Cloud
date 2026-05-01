package com.ruoyi.common.core.utils.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Bean转换工具类
 * 用于DTO/VO与Entity之间的转换
 * 
 * @author ruoyi
 */
public class BeanConvertUtils extends BeanUtils
{
    /**
     * 转换对象
     * 
     * @param source 源对象
     * @param targetClass 目标类
     * @return 目标对象
     */
    public static <T> T convert(Object source, Class<T> targetClass)
    {
        if (source == null)
        {
            return null;
        }
        try
        {
            T target = targetClass.getDeclaredConstructor().newInstance();
            copyProperties(source, target);
            return target;
        }
        catch (Exception e)
        {
            throw new RuntimeException("Bean转换失败: " + e.getMessage(), e);
        }
    }

    /**
     * 转换对象列表
     * 
     * @param sourceList 源对象列表
     * @param targetClass 目标类
     * @return 目标对象列表
     */
    public static <T> List<T> convertList(List<?> sourceList, Class<T> targetClass)
    {
        if (sourceList == null || sourceList.isEmpty())
        {
            return new ArrayList<>();
        }
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (Object source : sourceList)
        {
            T target = convert(source, targetClass);
            if (target != null)
            {
                targetList.add(target);
            }
        }
        return targetList;
    }

    /**
     * 转换对象（忽略null值）
     * 
     * @param source 源对象
     * @param targetClass 目标类
     * @return 目标对象
     */
    public static <T> T convertIgnoreNull(Object source, Class<T> targetClass)
    {
        if (source == null)
        {
            return null;
        }
        try
        {
            T target = targetClass.getDeclaredConstructor().newInstance();
            copyBeanPropIgnoreNull(source, target);
            return target;
        }
        catch (Exception e)
        {
            throw new RuntimeException("Bean转换失败: " + e.getMessage(), e);
        }
    }

    /**
     * 复制属性（忽略null值）
     * 
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyBeanPropIgnoreNull(Object source, Object target)
    {
        if (source == null || target == null)
        {
            return;
        }
        org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * 获取null属性名数组
     * 
     * @param source 源对象
     * @return null属性名数组
     */
    private static String[] getNullPropertyNames(Object source)
    {
        java.util.Set<String> emptyNames = new java.util.HashSet<>();
        java.beans.BeanInfo beanInfo;
        try
        {
            beanInfo = java.beans.Introspector.getBeanInfo(source.getClass());
            java.beans.PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (java.beans.PropertyDescriptor propertyDescriptor : propertyDescriptors)
            {
                String propertyName = propertyDescriptor.getName();
                java.lang.reflect.Method readMethod = propertyDescriptor.getReadMethod();
                if (readMethod != null)
                {
                    Object value = readMethod.invoke(source);
                    if (value == null)
                    {
                        emptyNames.add(propertyName);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return emptyNames.toArray(new String[0]);
    }
}
