package com.fzb.common.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;


public class Start {
    public static void main(String[] args) {
        Set<Class<?>> set = ScanClassUtil.getClasses("com.fzb");
        for (Class<?> class1 : set) {
            Method methods[] = class1.getDeclaredMethods();
            for (Method method : methods) {
                Annotation annot[] = method.getAnnotations();
                for (Annotation annotation : annot) {
                    if (annotation.annotationType() == RunOnStart.class) {
                        try {
                            method.invoke(Class.forName(
                                    class1.getCanonicalName()).newInstance());
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

}