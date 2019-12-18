package org.eclipse.aether.util;

import org.eclipse.aether.*;
import java.util.*;

public final class ConfigUtils
{
    private ConfigUtils() {
        super();
    }
    
    public static Object getObject(final Map<?, ?> map, final Object o, final String... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            final Object value = map.get(array[i]);
            if (value != null) {
                return value;
            }
        }
        return o;
    }
    
    public static Object getObject(final RepositorySystemSession repositorySystemSession, final Object o, final String... array) {
        return getObject(repositorySystemSession.getConfigProperties(), o, array);
    }
    
    public static String getString(final Map<?, ?> map, final String s, final String... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            final Object value = map.get(array[i]);
            if (value instanceof String) {
                return (String)value;
            }
        }
        return s;
    }
    
    public static String getString(final RepositorySystemSession repositorySystemSession, final String s, final String... array) {
        return getString(repositorySystemSession.getConfigProperties(), s, array);
    }
    
    public static int getInteger(final Map<?, ?> map, final int n, final String... array) {
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final Object value = map.get(array[i]);
            if (value instanceof Number) {
                return ((Number)value).intValue();
            }
            try {
                return Integer.valueOf((String)value);
            }
            catch (Exception ex) {
                ++i;
                continue;
            }
            break;
        }
        return n;
    }
    
    public static int getInteger(final RepositorySystemSession repositorySystemSession, final int n, final String... array) {
        return getInteger(repositorySystemSession.getConfigProperties(), n, array);
    }
    
    public static long getLong(final Map<?, ?> map, final long n, final String... array) {
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final Object value = map.get(array[i]);
            if (value instanceof Number) {
                return ((Number)value).longValue();
            }
            try {
                return Long.valueOf((String)value);
            }
            catch (Exception ex) {
                ++i;
                continue;
            }
            break;
        }
        return n;
    }
    
    public static long getLong(final RepositorySystemSession repositorySystemSession, final long n, final String... array) {
        return getLong(repositorySystemSession.getConfigProperties(), n, array);
    }
    
    public static float getFloat(final Map<?, ?> map, final float n, final String... array) {
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final Object value = map.get(array[i]);
            if (value instanceof Number) {
                return ((Number)value).floatValue();
            }
            try {
                return Float.valueOf((String)value);
            }
            catch (Exception ex) {
                ++i;
                continue;
            }
            break;
        }
        return n;
    }
    
    public static float getFloat(final RepositorySystemSession repositorySystemSession, final float n, final String... array) {
        return getFloat(repositorySystemSession.getConfigProperties(), n, array);
    }
    
    public static boolean getBoolean(final Map<?, ?> map, final boolean b, final String... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            final Object value = map.get(array[i]);
            if (value instanceof Boolean) {
                return (boolean)value;
            }
            if (value instanceof String) {
                return Boolean.parseBoolean((String)value);
            }
        }
        return b;
    }
    
    public static boolean getBoolean(final RepositorySystemSession repositorySystemSession, final boolean b, final String... array) {
        return getBoolean(repositorySystemSession.getConfigProperties(), b, array);
    }
    
    public static List<?> getList(final Map<?, ?> map, final List<?> list, final String... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            final Object value = map.get(array[i]);
            if (value instanceof List) {
                return (List<?>)value;
            }
            if (value instanceof Collection) {
                return Collections.unmodifiableList((List<?>)new ArrayList<Object>((Collection<?>)value));
            }
        }
        return list;
    }
    
    public static List<?> getList(final RepositorySystemSession repositorySystemSession, final List<?> list, final String... array) {
        return getList(repositorySystemSession.getConfigProperties(), list, array);
    }
    
    public static Map<?, ?> getMap(final Map<?, ?> map, final Map<?, ?> map2, final String... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            final Object value = map.get(array[i]);
            if (value instanceof Map) {
                return (Map<?, ?>)value;
            }
        }
        return map2;
    }
    
    public static Map<?, ?> getMap(final RepositorySystemSession repositorySystemSession, final Map<?, ?> map, final String... array) {
        return getMap(repositorySystemSession.getConfigProperties(), map, array);
    }
}
