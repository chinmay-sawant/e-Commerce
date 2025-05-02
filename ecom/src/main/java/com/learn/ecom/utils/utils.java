package com.learn.ecom.utils;

import org.springframework.stereotype.Component;

@Component
public interface utils {
    static <T, U> U convertModel(T source, Class<U> targetClass) {
        try {
            U target = targetClass.getDeclaredConstructor().newInstance();
            java.lang.reflect.Field[] sourceFields = source.getClass().getDeclaredFields();
            java.lang.reflect.Field[] targetFields = targetClass.getDeclaredFields();

            for (java.lang.reflect.Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                for (java.lang.reflect.Field targetField : targetFields) {
                    targetField.setAccessible(true);
                    if (sourceField.getName().equals(targetField.getName()) &&
                        sourceField.getType().equals(targetField.getType())) {
                        targetField.set(target, sourceField.get(source));
                    }
                }
            }
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Error converting models", e);
        }
    }

    static <T, U> java.util.List<U> convertModelList(java.util.List<T> sourceList, Class<U> targetClass) {
        java.util.List<U> targetList = new java.util.ArrayList<>();
        for (T source : sourceList) {
            targetList.add(convertModel(source, targetClass));
        }
        return targetList;
    }

    static <T> void updateModel(T source, T target) {
        try {
            java.lang.reflect.Field[] sourceFields = source.getClass().getDeclaredFields();
            java.lang.reflect.Field[] targetFields = target.getClass().getDeclaredFields();

            for (java.lang.reflect.Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                for (java.lang.reflect.Field targetField : targetFields) {
                    targetField.setAccessible(true);
                    if (sourceField.getName().equals(targetField.getName()) &&
                        sourceField.getType().equals(targetField.getType())) {
                        Object value = sourceField.get(source);
                        if (value != null) { // Only update non-null values
                            targetField.set(target, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating model", e);
        }
    }


}
