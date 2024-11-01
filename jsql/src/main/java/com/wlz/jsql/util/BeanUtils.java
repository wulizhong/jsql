package com.wlz.jsql.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BeanUtils {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> distinct(List<T> list, String... ids) {
		if (CollectionUtils.isEmpty(list)) {
			return list;
		}
		List<T> result = new ArrayList<>();
		for (T obj : list) {
			boolean find = false;
			for (T r : result) {
				if (obj.equals(r)) {
					find = true;
					break;
				}
				boolean isSame = true;
				for (String id : ids) {
					Object o1 = ReflectionUtils.getValueByGetMethod(obj, id);
					Object o2 = ReflectionUtils.getValueByGetMethod(r, id);
					isSame = o1.equals(o2);
					if (!isSame) {
						break;
					}
				}
				if (isSame) {
					find = true;
					break;
				}
			}
			if (!find) {
				result.add(obj);
			}
		}
		list.clear();
		list.addAll(result);
		return list;
	}

	
	public static <T>   List<T> distinct(List<T> list, Equals<T> e) {
		if(CollectionUtils.isEmpty(list)) {
			return list;
		}
		List<T> result = new ArrayList<>();
		for (T obj : list) {
			boolean find = false;
			for (T r : result) {
				if (obj.equals(r)||e.isEquals(obj,r)) {
					find = true;
					break;
				}
			}
			if (!find) {
				result.add(obj);
			}
		}
		list.clear();
		list.addAll(result);
		return list;
	}
	
	public static <T> List<T> distinct(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<T> result = list.stream().distinct().collect(Collectors.toList());
		list.clear();
		list.addAll(result);
		return result;
	}
	@SuppressWarnings("unchecked")
	public static void merge(List<?> parent, List<?> children, String parentField, String childrenField) {

		if (CollectionUtils.isEmpty(parent)) {
			return;
		}
		if (CollectionUtils.isEmpty(children)) {
			return;
		}

		Object obj = parent.get(0);
		Object obj2 = children.get(0);

		List<Field> fields = ReflectionUtils.findAllFields(obj.getClass());
		boolean isList = false;
		Field field = null;
		for (Field f : fields) {
			if (ReflectionUtils.isListType(f.getType())) {
				Type t = ReflectionUtils.getGenericType(f);
				if (t.toString().endsWith(obj2.getClass().toString())) {
					isList = true;
					field = f;
					break;
				}

			} else {
				if (f.getType().equals(obj2.getClass())) {
					field = f;
					break;
				}

			}

		}
		if (field == null) {
			return;
		}
		for (Object parentObj : parent) {
			for (Object childrenObj : children) {
				Object o1 = ReflectionUtils.getValueByGetMethod(parentObj, parentField);
				Object o2 = ReflectionUtils.getValueByGetMethod(childrenObj, childrenField);
				if (o1.equals(o2)) {
					if (isList) {
						Object o = ReflectionUtils.getValueByGetMethod(parentObj, field);
						List<Object> list = null;
						if (o != null) {
							list = (List<Object>) o;
						} else {
							list = new ArrayList<>();
							ReflectionUtils.setValueBySetMethod(parentObj, field, list);
						}
						list.add(childrenObj);
					} else {
						ReflectionUtils.setValueBySetMethod(parentObj, field, childrenObj);
					}
				}
			}

		}

	}
	
	@SuppressWarnings("unchecked")
	public static void merge(List<?> parent, List<?> children, String parentField, String childrenField,String targetField) {

		if (CollectionUtils.isEmpty(parent)) {
			return;
		}
		if (CollectionUtils.isEmpty(children)) {
			return;
		}

		Object obj = parent.get(0);

		Field field = ReflectionUtils.findField(obj.getClass(), targetField);
		if (field == null) {
			return;
		}
		boolean isList  = ReflectionUtils.isListType(field.getType());
		for (Object parentObj : parent) {
			for (Object childrenObj : children) {
				Object o1 = ReflectionUtils.getValueByGetMethod(parentObj, parentField);
				Object o2 = ReflectionUtils.getValueByGetMethod(childrenObj, childrenField);
				if (o1.equals(o2)) {
					if (isList) {
						Object o = ReflectionUtils.getValueByGetMethod(parentObj, field);
						List<Object> list = null;
						if (o != null) {
							list = (List<Object>) o;
						} else {
							list = new ArrayList<>();
							ReflectionUtils.setValueBySetMethod(parentObj, field, list);
						}
						list.add(childrenObj);
					} else {
						ReflectionUtils.setValueBySetMethod(parentObj, field, childrenObj);
					}
				}
			}
		}

	}

	public static <T> List<T> copyList(List<?> srcList, Class<T> clazz) {
		if (CollectionUtils.isEmpty(srcList)) {
			return null;
		} else {
			ArrayList<T> result = new ArrayList<>();
			for (Object o : srcList) {
				result.add(copy(o, clazz));
			}
			return result;
		}
	}

	public static <T> T copy(Object src, Class<T> clazz) {
		try {
			T obj = clazz.newInstance();
			List<Field> fields = ReflectionUtils.findAllFields(src.getClass());
			for (Field f : fields) {
				Method setMethod = ReflectionUtils.findSetMethod(clazz,
						"set" + FieldNameUtils.toUpperCaseFirstOne(f.getName()));
				if (setMethod != null) {
					Class<?> parameterType = setMethod.getParameterTypes()[0];
					Object value = ReflectionUtils.getValueByGetMethod(src, f);
					if(value == null){
						continue;
					}
					if (value.getClass() != parameterType) {
						if (parameterType == Long.class || parameterType == long.class) {
							setMethod.invoke(obj, Long.parseLong(value.toString()));
						} else if (parameterType == Integer.class || parameterType == int.class) {
							setMethod.invoke(obj, Integer.parseInt(value.toString()));
						} else if (parameterType == Short.class || parameterType == short.class) {
							setMethod.invoke(obj, Short.parseShort(value.toString()));
						} else if (parameterType == Byte.class || parameterType == byte.class) {
							setMethod.invoke(obj, Byte.parseByte(value.toString()));
						} else if (parameterType == Double.class || parameterType == double.class) {
							setMethod.invoke(obj, Double.parseDouble(value.toString()));
						} else if (parameterType == Float.class || parameterType == float.class) {
							setMethod.invoke(obj, Float.parseFloat(value.toString()));
						} else if (parameterType == BigDecimal.class) {
							setMethod.invoke(obj, new BigDecimal(value.toString()));
						} else if (parameterType == BigInteger.class) {
							setMethod.invoke(obj, new BigInteger(value.toString()));
						} else {
							setMethod.invoke(obj, value);
						}
					} else {
						setMethod.invoke(obj, value);
					}

				}

			}
			return obj;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//	public static void main(String args[]) {
//		
//		
//		
//	}
	
//	private static boolean isReturnList(Field field) {
//		boolean isList = field.getType().getSuperclass().equals(Collection.class);
//		if (!isList) {
//			Class<?> clazz = field.getType().getSuperclass().getSuperclass();
//			while (clazz != null) {
//				if (clazz.equals(Collection.class)) {
//					isList = true;
//					break;
//				}
//			}
//		}
//		return isList;
//	}
//
//	private static ParameterizedType getListType(Field field) {
//		ParameterizedType type = (ParameterizedType) field.getGenericType();
//		return type;
//	}

}
