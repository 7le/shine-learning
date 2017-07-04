package com.shine.annotation.resource;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

/**
 * Spring容器
 */
public class ClassPathXMLApplicationContext {

    private List<BeanDefinition> beanDefines = new ArrayList<>();

    private Map<String, Object> sigletons = new HashMap<>();

    public ClassPathXMLApplicationContext(String filename) {
        this.readXML(filename);
    }

    /**
     * 读取xml配置文件
     *
     * @param filename
     */
    private void readXML(String filename) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            URL xmlpath = this.getClass().getClassLoader().getResource(filename);
            document = saxReader.read(xmlpath);
            Map<String, String> nsMap = new HashMap<String, String>();
            nsMap.put("ns", "http://www.springframework.org/schema/beans");// 加入命名空间
            XPath xsub = document.createXPath("//ns:beans/ns:bean");// 创建beans/bean查询路径
            xsub.setNamespaceURIs(nsMap);// 设置命名空间
            List<Element> beans = xsub.selectNodes(document);// 获取文档下所有bean节点
            for (Element element : beans) {
                String id = element.attributeValue("id");// 获取id属性值
                String clazz = element.attributeValue("class"); // 获取class属性值
                BeanDefinition beanDefine = new BeanDefinition(id, clazz);
                XPath propertysub = element.createXPath("ns:property");
                propertysub.setNamespaceURIs(nsMap);// 设置命名空间
                List<Element> propertys = propertysub.selectNodes(element);
                for (Element property : propertys) {
                    String propertyName = property.attributeValue("name");
                    String propertyRef = property.attributeValue("ref");
                    String propertyValue = property.attributeValue("value");
                    PropertyDefinition propertyDefinition = new PropertyDefinition(propertyName, propertyRef, propertyValue);
                    beanDefine.getPropertys().add(propertyDefinition);
                }
                beanDefines.add(beanDefine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
