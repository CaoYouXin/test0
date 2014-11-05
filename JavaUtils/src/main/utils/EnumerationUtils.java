package utils;

import java.util.Enumeration;

/**
 * Created by caoyouxin on 14-11-5.
 */
public class EnumerationUtils {

    public static <T>Enumeration<T> emptyEnumeration(Class<T> kass) {
        if (null == kass) throw new NullPointerException();
        return new Enumeration<T>() {
            @Override
            public boolean hasMoreElements() {
                return false;
            }

            @Override
            public T nextElement() {
                return null;
            }
        };
    }

}
