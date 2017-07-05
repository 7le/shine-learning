package resource;

import org.apache.commons.beanutils.ConvertUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring容器
 */
public class ClassPathXMLApplicationContext {

    private List<BeanDefinition> beanDefines = new ArrayList<>();

    private Map<String, Object> singletons = new HashMap<>();

    public ClassPathXMLApplicationContext(String filename) {
        this.readXML(filename);
        this.instanceBeans();
        this.injectObject();
        this.annotationInject();
    }

    /**
     * 读取xml配置文件
     * @param filename
     */
    private void readXML(String filename) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            URL xmlpath = this.getClass().getClassLoader().getResource(filename);
            document = saxReader.read(xmlpath);
            Map<String, String> nsMap = new HashMap<>();
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

    /**
     * 完成bean的实例化
     */
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefines) {
            try {
                if (beanDefinition.getClassName() != null && !"".equals(beanDefinition.getClassName().trim())) {
                    singletons.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 为bean对象的属性(依赖)注入值
     */
    private void injectObject() {
        for (BeanDefinition beanDefinition : beanDefines) {
            Object bean = singletons.get(beanDefinition.getId());
            if (bean != null) {
                try {
                    PropertyDescriptor[] ps = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
                    for (PropertyDefinition propertyDefinition : beanDefinition.getPropertys()) {
                        for (PropertyDescriptor propertyDesc : ps) {
                            if (propertyDefinition.getName().equals(propertyDesc.getName())) {
                                Method setter = propertyDesc.getWriteMethod(); // 获取属性的setter方法，private
                                if (setter != null) { // 最好判断有无setter方法，因为属性可以没有setter方法
                                    /* Object value = sigletons.get(propertyDefinition.getRef()); setter.setAccessible(true); // 允许访问私有的setter方法 setter.invoke(bean, value); // 把引用对象注入到属性中 */

                                    Object value = null;
                                    if (propertyDefinition.getRef() != null && !"".equals(propertyDefinition.getRef().trim()) ) {
                                        value = singletons.get(propertyDefinition.getRef());
                                    } else { // 注入基本类型
                                        value = ConvertUtils.convert(propertyDefinition.getValue(), propertyDesc.getPropertyType()); // 把本身为字符串的值转成相应的属性类型的值
                                    }
                                    setter.setAccessible(true); // 允许访问私有的setter方法
                                    setter.invoke(bean, value); // 把引用对象注入到属性中
                                }
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void annotationInject() {
        for (String beanName : singletons.keySet()) {
            Object bean = singletons.get(beanName);
            if (bean != null) {
                try {
                    PropertyDescriptor[] ps = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
                    for (PropertyDescriptor propertyDesc : ps) {
                        Method setter = propertyDesc.getWriteMethod(); // 获取属性的setter方法
                        if (setter != null && setter.isAnnotationPresent(MyResource.class)) { // setter方法存在注解
                            MyResource resource = setter.getAnnotation(MyResource.class);
                            Object value = null;
                            if (resource.name() != null && !"".equals(resource.name())) { // 指定了注解的name属性
                                value = singletons.get(resource.name());
                            } else {
                                value = singletons.get(propertyDesc.getName());
                                if (value == null) {
                                    for (String key : singletons.keySet()) {
                                        // isAssignableFrom(xxx)方法判断propertyDesc.getPropertyType()获得的类型是否是xxx的接口或父类，或者是xxx本身
                                        if (propertyDesc.getPropertyType().isAssignableFrom(singletons.get(key).getClass())) {
                                            value = singletons.get(key);
                                            break;
                                        }
                                    }
                                }
                            }
                            setter.setAccessible(true); // 允许访问私有的setter方法
                            setter.invoke(bean, value); // 把引用对象注入到属性中
                        }
                    }
                    Field[] fields = bean.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(MyResource.class)) {
                            MyResource resource = field.getAnnotation(MyResource.class);
                            Object value = null;
                            if (resource.name() != null && !"".equals(resource.name())) { // 指定了注解的name属性
                                value = singletons.get(resource.name());
                            } else {
                                value = singletons.get(field.getName());
                                if (value == null) {
                                    for (String key : singletons.keySet()) {
                                        // isAssignableFrom(xxx)方法判断field.getPropertyType()获得的类型是否是xxx的接口或父类，或者是xxx本身
                                        if (field.getType().isAssignableFrom(singletons.get(key).getClass())) {
                                            value = singletons.get(key);
                                            break;
                                        }
                                    }
                                }
                            }
                            field.setAccessible(true); // 允许访问private字段
                            field.set(bean, value);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 获取bean实例
     */
    public Object getBean(String beanName) {
        return this.singletons.get(beanName);
    }
}
