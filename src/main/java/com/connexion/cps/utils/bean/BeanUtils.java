package com.connexion.cps.utils.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bean utils
 * 
 * 
 */
public class BeanUtils extends org.springframework.beans.BeanUtils
{
    /** Bean,The subscript starting from the attribute name in the method name */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /** * Regular expression matching getter methods */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /** * Regular expression matching setter methods */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * Bean Property copy tool methods.
     * 
     * @param dest
     * @param src
     */
    public static void copyBeanProp(Object dest, Object src)
    {
        try
        {
            copyProperties(src, dest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * get setting function
     * 
     * @param obj
     * @return method
     */
    public static List<Method> getSetterMethods(Object obj)
    {
        // setter method list
        List<Method> setterMethods = new ArrayList<Method>();

        // get all method
        Method[] methods = obj.getClass().getMethods();

        // search method

        for (Method method : methods)
        {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1))
            {
                setterMethods.add(method);
            }
        }
        // return setter method
        return setterMethods;
    }

    /**
     * get setting function
     *
     * @param obj
     * @return method
     */

    public static List<Method> getGetterMethods(Object obj)
    {
        // getter method list
        List<Method> getterMethods = new ArrayList<Method>();
        // get all method
        Method[] methods = obj.getClass().getMethods();
        // search get set method
        for (Method method : methods)
        {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0))
            {
                getterMethods.add(method);
            }
        }
        // return get method
        return getterMethods;
    }

    /**
     * Check whether the property names in the Bean method name are equal。<br>
     * For example, the attribute names of getName() and setName() are the same, but the attribute names of getName() and setAge() are different.。
     * 
     * @param m1 method1
     * @param m2 method2
     * @return Returns true if the attribute name is the same, otherwise returns false
     */

    public static boolean isMethodPropEquals(String m1, String m2)
    {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }
}
