package com.litian.jdbc;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: ReflectionUtils.java
 * @time: 2020/3/26 18:57
 * @desc: |JDBC 查询得到属性字段 反射机制返回到 JavaBean中相同类属性名的对象中
 */

import java.lang.reflect.*;


public class ReflectionUtils {

    /**
     * 使 filed 变为可访问
     *
     * @param field
     */
    public static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 直接设置对象的属性，忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object
     * @param fieldName 属性名称
     * @param value
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {

        Field field = getDeclaredField(object, fieldName);
        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        // 让 private 元素变得可以访问,field.setAccessible();
        makeAccessible(field);

        try {
            field.set(object, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object
     * @param filedName
     * @return
     */
    public static Field getDeclaredField(Object object, String filedName) {
        // 一步步的循环得到 获取声明对象的祖宗类
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(filedName);
            } catch (NoSuchFieldException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        makeAccessible(field);
        Object result = null;

        try {
            result = field.get(object);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param object
     * @param methodName
     * @param parameterTypes: 指定特定成员方法有重载可能性，必须指定特定的类型变量
     * @return
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                //superClass.getMethod(methodName, parameterTypes);
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                //Method 不在当前类定义, 继续向上转型
            }
            //..
        }

        return null;
    }

    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected)
     *
     * @param object
     * @param methodName
     * @param parameterTypes
     * @param parameters
     * @return
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     */
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes,
                                      Object[] parameters) throws InvocationTargetException {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);

        if (method == null)
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
        method.setAccessible(true);

        // 使用 method.invoke()方法进行运行
        try {
            method.invoke(object, parameters);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return method;
    }

    /**
     * 通过反射, 获得定义 Class 时声明的父类的泛型参数的类型
     * 如: public EmployeeDao extends BaseDao<Employee, String>
     *
     * @param clazz
     * @return
     */
    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();

        // 判定s是否是ParameterType相对应的
        if (!(genType instanceof ParameterizedType))
            return Object.class;

        // 强制转换 获取超类泛型参数实际类型，返回genType 是类的接口，基本类型或者void
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0)
            return Object.class;

        if (!(params[index] instanceof Class))
            return Object.class;
        return (Class) params[index];
    }

    /**
     * 通过反射,获得Class定义中声明的父类的泛型参数的类型.
     * eg.
     * public UserDao extends HibernateDao<User>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }
}
