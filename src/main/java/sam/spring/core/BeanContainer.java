package sam.spring.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanContainer {

    private Map<String, Object> beanMap = new HashMap<>();
    private Map<String, Object> controllerMap = new HashMap<>();

    private static BeanContainer instance;

    private BeanContainer() {
        this.setBeanMap();
    }

    public static BeanContainer getInstance() {
        if (instance == null) instance = new BeanContainer();
        return instance;
    }

    public Object getBeanByName(String beanName) {
        return beanMap.get(beanName);
    }

    public Object getBeanByType(Class<?> type) {
        Set<String> beanNames = beanMap.keySet();
        for (String beanName : beanNames) {
            Object bean = beanMap.get(beanName);
            Class<?> beanType = bean.getClass();

            if (type.isAssignableFrom(beanType)) return bean;
        }

        return null;
    }

    private void setBeanMap() {
        BeanLoader beanLoader = BeanLoader.getInstance();

        Map<String, Class<?>> controllerClasses = beanLoader.getControllerClasses();
        Map<String, Class<?>> componentClasses = beanLoader.getComponentClasses();
        controllerMap = insertBean(controllerClasses);
        beanMap = insertBean(componentClasses);
        beanMap.putAll(controllerMap);

        Map<String, Class<?>> beanMethods = beanLoader.getBeanClasses();
        Set<String> keys = beanMethods.keySet();
        for (String key : keys) {
            try {
                beanMap.put(key, beanMethods.get(key).newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, Object> insertBean(Map<String, Class<?>> classes) {
        Map<String, Object> map = new HashMap<>();
        Set<String> keys = classes.keySet();
        for (String key : keys) {
            try {
                String beanName = classes.get(key).getSimpleName();
                beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
                Object beanInstance = classes.get(key).newInstance();

                map.put(beanName, beanInstance);

            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public Map<String, Object> getBean() {
        return this.beanMap;
    }

    public Map<String, Object> getControllerMap() {
        return this.controllerMap;
    }

}
