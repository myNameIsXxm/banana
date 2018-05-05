package org.xixi.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class ReflectionUtils {

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

	private ReflectionUtils() {
	}

	public static Object newInstance(String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Class not found:" + name);
		}
		try {
			Class c = Class.forName(name);
			return c.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException("error instance object from class " + name);
		}
	}

	public static Object getFieldValue(final Object object, final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName
					+ "] on target [" + object + "]");

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	public static void setFieldValue(final Object object, final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName
					+ "] on target [" + object + "]");

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public static Method getDeclaredMethod(final Class clazz, final String methodName,
			final Class... parameterTypes) {
		Assert.notNull(clazz);
		Assert.hasText(methodName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
			}
		}
		return null;
	}

	protected static Field getDeclaredField(final Object object, final String fieldName) {
		Assert.notNull(object);
		return getDeclaredField(object.getClass(), fieldName);
	}

	protected static Field getDeclaredField(final Class clazz, final String fieldName) {
		Assert.notNull(clazz);
		Assert.hasText(fieldName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	public static Class getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	public static Class getSuperClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName()
					+ "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName()
					+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class) params[index];
	}

	@SuppressWarnings("unchecked")
	public static List fetchElementPropertyToList(final Collection collection,
			final String propertyName) throws Exception {

		List list = new ArrayList();

		for (Object obj : collection) {
			list.add(getProperty(obj, propertyName));
		}

		return list;
	}

	public static Object getProperty(Object bean, String propertyName) {

		if (bean == null) {
			throw new IllegalArgumentException("No bean specified");
		}

		if (propertyName == null) {
			throw new IllegalArgumentException("No name specified for bean class '"
					+ bean.getClass() + "'");
		}

		String m = "get".concat(propertyName.substring(0, 1).toUpperCase()).concat(
				propertyName.substring(1));

		try {
			Method getMethod = bean.getClass().getMethod(m, new Class[] {});
			return getMethod.invoke(bean, new Object[] {});
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("No such method");
		} catch (Exception e) {
			throw new IllegalArgumentException("Invocation Exception");
		}
	}

	public static Object setProperty(Object bean, String propertyName, Object val, Class[] types) {
		if (bean == null) {
			throw new IllegalArgumentException("No bean specified");
		}

		if (propertyName == null) {
			throw new IllegalArgumentException("No name specified for bean class '"
					+ bean.getClass() + "'");
		}

		String m = "set".concat(propertyName.substring(0, 1).toUpperCase()).concat(
				propertyName.substring(1));

		try {
			Method getMethod = bean.getClass().getMethod(m, types);
			return getMethod.invoke(bean, new Object[] { val });
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("No such method");
		} catch (Exception e) {
			throw new IllegalArgumentException("Invocation Exception");
		}
	}

	public static Object addListItem(Object bean, String propertyName, Object val, Class[] types) {
		if (bean == null) {
			throw new IllegalArgumentException("No bean specified");
		}

		if (propertyName == null) {
			throw new IllegalArgumentException("No name specified for bean class '"
					+ bean.getClass() + "'");
		}

		String m = "add".concat(propertyName.substring(0, 1).toUpperCase()).concat(
				propertyName.substring(1));

		try {
			Method getMethod = bean.getClass().getMethod(m, types);
			return getMethod.invoke(bean, new Object[] { val });
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("No such method");
		} catch (Exception e) {
			throw new IllegalArgumentException("Invocation Exception");
		}
	}

	public static Object removeListItem(Object bean, String propertyName, Object val, Class[] types) {
		if (bean == null) {
			throw new IllegalArgumentException("No bean specified");
		}

		if (propertyName == null) {
			throw new IllegalArgumentException("No name specified for bean class '"
					+ bean.getClass() + "'");
		}

		String m = "remove".concat(propertyName.substring(0, 1).toUpperCase()).concat(
				propertyName.substring(1));
		try {
			Method getMethod = bean.getClass().getMethod(m, types);
			return getMethod.invoke(bean, new Object[] { val });
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("No such method");
		} catch (Exception e) {
			throw new IllegalArgumentException("Invocation Exception");
		}
	}

	public static String fetchElementPropertyToString(final Collection collection,
			final String propertyName, final String separator) throws Exception {
		List list = fetchElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}
}
