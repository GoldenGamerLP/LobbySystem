package de.alex.lobbysystem.utils;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public static boolean set(Field field, Object object, Object value) {
        return set(field, object, value, true);
    }

    public static boolean set(Field field, Object object, Object value, boolean printStackTrace) {
        try {
            field.setAccessible(true);

            field.set(object, value);

            return true;
        } catch (Exception e) {
            if (printStackTrace)
                e.printStackTrace();

            return false;
        }
    }

    public static Field getAccessibleField(Class<?> clazz, String fieldName) {
        try {
            final Field field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            return field;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

}
