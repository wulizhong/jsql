package com.wlz.jsql.generator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

	public static Object getValue(Object obj,Field field){
		Object result = null;
		field.setAccessible(true);
		try {
			result = field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		field.setAccessible(false);
		return result;
	}
	
	public static void setValue(Object obj,Field field,Object value){
		field.setAccessible(true);
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		field.setAccessible(false);
	}
	
	/**
	 * @param clzz
	 * @return
	 */
	public static List<Field> findAllFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null) {
			fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
			if (clazz.equals(Object.class)) {
				break;
			}
		}
		return fieldList;
	}
	
	/**
	 * @param clzz
	 * @return
	 */
	public static Field findField(Class<?> clazz,String fieldName) {
		Field field = null;
		while (clazz != null) {
			try {
				field = clazz.getDeclaredField(fieldName);
				if(field!=null) {
					return field;
				}
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
//				if (logger.isDebugEnabled()) {
//					logger.debug("未找到对应属性", e);
//				}
			}
			
			clazz = clazz.getSuperclass();
			if (clazz.equals(Object.class)) {
				break;
			}
		}
		return field;
	}

	/**
	 * 
	 * @param clazz
	 * @param field
	 * @return
	 */
	public static Method findSetMethodByField(Class<?> clazz, Field field) {
		Method setMethod = null;
		while (clazz != null) {
			try {
				setMethod = clazz.getDeclaredMethod("set" + FieldNameUtils.toUpperCaseFirstOne(field.getName()),
						field.getType());
				if (setMethod != null)
					return setMethod;
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
//				if (logger.isDebugEnabled()) {
//					logger.debug("未找到对应方法", e);
//				}
			}
			clazz = clazz.getSuperclass();
			if (clazz.equals(Object.class)) {
				break;
			}
		}
		return setMethod;
	}

	public static void setValueBySetMethod(Object obj,Field field,Object value) {
		Method setMethod = ReflectionUtils.findSetMethodByField(obj.getClass(), field);
		try {
			setMethod.invoke(obj, new Object[]{value});
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setValueBySetMethod(Object obj,String field,Object value) {
		Method setMethod = ReflectionUtils.findSetMethodByFieldName(obj.getClass(), field);
		try {
			setMethod.invoke(obj, new Object[]{value});
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Object getValueByGetMethod(Object obj,Field field) {
		Method getMethod = ReflectionUtils.findGetMethodByField(obj.getClass(), field);
		try {
			return getMethod.invoke(obj, new Object[]{});
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object getValueByGetMethod(Object obj,String field) {
		Method getMethod = ReflectionUtils.findGetMethodByFieldName(obj.getClass(), field);
		try {
			return getMethod.invoke(obj, new Object[]{});
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Method findSetMethodByFieldName(Class<?> clazz, String fieldName) {
		Field field = null;
		while (clazz != null) {
			try {
				field = clazz.getDeclaredField(fieldName);
				if (field != null) {
					return findSetMethodByField(clazz, field);
				}
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
//				if (logger.isDebugEnabled()) {
//					logger.debug("未找到对应字段", e);
//				}
			}
			clazz = clazz.getSuperclass();
			if (clazz.equals(Object.class)) {
				break;
			}
		}
		return null;
	}
	/**
	 * 获取字段对应的无参get方法
	 * @param clazz
	 * @param field
	 * @return
	 */
	public static Method findGetMethodByField(Class<?> clazz, Field field) {
		Method getMethod = null;
		while (clazz != null) {
			try {
				getMethod = clazz.getDeclaredMethod("get" + FieldNameUtils.toUpperCaseFirstOne(field.getName()),
						new Class<?>[] {});
				if (getMethod != null)
					return getMethod;
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
//				if (logger.isDebugEnabled()) {
//					logger.debug("未找到对应方法", e);
//				}
			}
			clazz = clazz.getSuperclass();
			if (clazz.equals(Object.class)) {
				break;
			}
		}
		return getMethod;
	}
	
	public static Method findGetMethodByFieldName(Class<?> clazz, String fieldName) {
		Field field = null;
		while (clazz != null) {
			try {
				field = clazz.getDeclaredField(fieldName);
				if (field != null) {
					return findGetMethodByField(clazz, field);
				}
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
//				if (logger.isDebugEnabled()) {
//					logger.debug("未找到对应字段", e);
//				}
			}
			clazz = clazz.getSuperclass();
			if (clazz.equals(Object.class)) {
				break;
			}
		}
		return null;
	}

	public static Method findSetMethod(Class<?> clazz, String setMethodName) {
		// TODO Auto-generated method stub
			try {
				Method[] setMethods = clazz.getMethods();
				for(Method method : setMethods) {
					Parameter[] parameters = method.getParameters();
					if(method.getName().equals(setMethodName)&&parameters!=null&&parameters.length == 1) {
						return method;
					}
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
//				if (logger.isDebugEnabled()) {
//					logger.debug("未找到对应字段", e);
//				}
			}
		return null;
	}
	

	public static boolean isBasicType(Class<?> t) {

		return t == String.class || t == Long.class || t == long.class || t == Integer.class || t == int.class || t == Short.class || t == short.class
				|| t == Byte.class || t == byte.class || t == char.class || t == StringBuilder.class || t == StringBuffer.class || t == BigInteger.class
				|| t == BigDecimal.class || t == java.sql.Date.class|| t == java.util.Date.class || t == Double.class || t == double.class || t == Float.class || t == float.class;

	}

	public static boolean isListType(Class<?> t) {
		if (t == List.class) {
			return true;
		}
		Class<?>[] types = t.getInterfaces();
		if (types != null) {
			for (Class<?> type : types) {
				boolean isList = isListType(type);
				if (isList) {
					return true;
				}
			}
		}
		return false;
	}

	public static Class<?> getGenericType(Field f) {
		Type genericType = f.getGenericType();
		if (genericType == null)
			return null;
		if (genericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) genericType;
			Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
			return genericClazz;
		}
		return null;
	}
	
	
}
