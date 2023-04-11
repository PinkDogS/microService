package com.ailen.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamTool {
    private static XStream xStream = new XStream(new DomDriver());
    static {
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypesByWildcard(new String[] {"com.anshare.base.**"});
        xStream.ignoreUnknownElements();
    }

    public static <T> T parse(String objXml){
        return (T) xStream.fromXML(objXml);
    }

    public static <T> T parse(String objXml, Class type){
        xStream.processAnnotations(type);
        return parse(objXml);
    }

    public static <T> T parse(String objXml, Class ...types){
        xStream.processAnnotations(types);
        return parse(objXml);
    }

    public static <T> String toXml(Object obj, Class ...types){
        xStream.processAnnotations(types);
        return xStream.toXML(obj);
    }

    /**
     * 默认序列化时，普通类对应的根元素会包含包路径，数组元素会追加 -Array
     * 普通类可以通过 @XStreamAlias注解指定别名，也可以调用该方法另外指定别名
     * 数组元素目前只能自己指定，如：ArrayOfSparepartApplyStatisticsInfo --> SparepartApplyStatisticsInfo[].class
     * @param name
     * @param clz
     */
    public static void alias(String name, Class clz){
        xStream.alias(name, clz);
    }

}
