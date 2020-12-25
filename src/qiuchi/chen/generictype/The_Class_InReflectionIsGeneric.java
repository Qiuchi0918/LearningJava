package qiuchi.chen.generictype;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class The_Class_InReflectionIsGeneric {
    public static void main(String[] args) {
        //Class类是泛型类
        Class<String> theClassOfString = String.class;
        System.out.println(theClassOfString.getName());
    }

    public static <T> Pair<T> makePair(Class<T> c) throws
            InstantiationException, IllegalAccessException, InvocationTargetException {
        return (Pair<T>) new Pair<>(c.getDeclaredConstructors()[0].newInstance(),
                c.getDeclaredConstructors()[0].newInstance());
    }
}
