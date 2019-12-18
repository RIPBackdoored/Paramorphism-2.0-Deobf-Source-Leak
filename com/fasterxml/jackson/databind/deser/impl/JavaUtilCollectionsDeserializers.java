package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.deser.std.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.*;
import java.util.*;
import com.fasterxml.jackson.databind.type.*;

public abstract class JavaUtilCollectionsDeserializers
{
    private static final int TYPE_SINGLETON_SET = 1;
    private static final int TYPE_SINGLETON_LIST = 2;
    private static final int TYPE_SINGLETON_MAP = 3;
    private static final int TYPE_UNMODIFIABLE_SET = 4;
    private static final int TYPE_UNMODIFIABLE_LIST = 5;
    private static final int TYPE_UNMODIFIABLE_MAP = 6;
    public static final int TYPE_AS_LIST = 7;
    private static final Class<?> CLASS_AS_ARRAYS_LIST;
    private static final Class<?> CLASS_SINGLETON_SET;
    private static final Class<?> CLASS_SINGLETON_LIST;
    private static final Class<?> CLASS_SINGLETON_MAP;
    private static final Class<?> CLASS_UNMODIFIABLE_SET;
    private static final Class<?> CLASS_UNMODIFIABLE_LIST;
    private static final Class<?> CLASS_UNMODIFIABLE_MAP;
    
    public JavaUtilCollectionsDeserializers() {
        super();
    }
    
    public static JsonDeserializer<?> findForCollection(final DeserializationContext deserializationContext, final JavaType javaType) throws JsonMappingException {
        JavaUtilCollectionsConverter javaUtilCollectionsConverter;
        if (javaType.hasRawClass(JavaUtilCollectionsDeserializers.CLASS_AS_ARRAYS_LIST)) {
            javaUtilCollectionsConverter = converter(7, javaType, List.class);
        }
        else if (javaType.hasRawClass(JavaUtilCollectionsDeserializers.CLASS_SINGLETON_LIST)) {
            javaUtilCollectionsConverter = converter(2, javaType, List.class);
        }
        else if (javaType.hasRawClass(JavaUtilCollectionsDeserializers.CLASS_SINGLETON_SET)) {
            javaUtilCollectionsConverter = converter(1, javaType, Set.class);
        }
        else if (javaType.hasRawClass(JavaUtilCollectionsDeserializers.CLASS_UNMODIFIABLE_LIST)) {
            javaUtilCollectionsConverter = converter(5, javaType, List.class);
        }
        else {
            if (!javaType.hasRawClass(JavaUtilCollectionsDeserializers.CLASS_UNMODIFIABLE_SET)) {
                return null;
            }
            javaUtilCollectionsConverter = converter(4, javaType, Set.class);
        }
        return new StdDelegatingDeserializer<Object>(javaUtilCollectionsConverter);
    }
    
    public static JsonDeserializer<?> findForMap(final DeserializationContext deserializationContext, final JavaType javaType) throws JsonMappingException {
        JavaUtilCollectionsConverter javaUtilCollectionsConverter;
        if (javaType.hasRawClass(JavaUtilCollectionsDeserializers.CLASS_SINGLETON_MAP)) {
            javaUtilCollectionsConverter = converter(3, javaType, Map.class);
        }
        else {
            if (!javaType.hasRawClass(JavaUtilCollectionsDeserializers.CLASS_UNMODIFIABLE_MAP)) {
                return null;
            }
            javaUtilCollectionsConverter = converter(6, javaType, Map.class);
        }
        return new StdDelegatingDeserializer<Object>(javaUtilCollectionsConverter);
    }
    
    static JavaUtilCollectionsConverter converter(final int n, final JavaType javaType, final Class<?> clazz) {
        return new JavaUtilCollectionsConverter(n, javaType.findSuperType(clazz), null);
    }
    
    static {
        CLASS_AS_ARRAYS_LIST = Arrays.asList(null, null).getClass();
        final Set<Boolean> singleton = Collections.singleton(Boolean.TRUE);
        CLASS_SINGLETON_SET = singleton.getClass();
        CLASS_UNMODIFIABLE_SET = Collections.unmodifiableSet((Set<?>)singleton).getClass();
        final List<Boolean> singletonList = Collections.singletonList(Boolean.TRUE);
        CLASS_SINGLETON_LIST = singletonList.getClass();
        CLASS_UNMODIFIABLE_LIST = Collections.unmodifiableList((List<?>)singletonList).getClass();
        final Map<String, String> singletonMap = Collections.singletonMap("a", "b");
        CLASS_SINGLETON_MAP = singletonMap.getClass();
        CLASS_UNMODIFIABLE_MAP = Collections.unmodifiableMap((Map<?, ?>)singletonMap).getClass();
    }
    
    private static class JavaUtilCollectionsConverter implements Converter<Object, Object>
    {
        private final JavaType _inputType;
        private final int _kind;
        
        private JavaUtilCollectionsConverter(final int kind, final JavaType inputType) {
            super();
            this._inputType = inputType;
            this._kind = kind;
        }
        
        @Override
        public Object convert(final Object o) {
            if (o == null) {
                return null;
            }
            switch (this._kind) {
                case 1: {
                    final Set set = (Set)o;
                    this._checkSingleton(set.size());
                    return Collections.singleton((Object)set.iterator().next());
                }
                case 2: {
                    final List list = (List)o;
                    this._checkSingleton(list.size());
                    return Collections.singletonList((Object)list.get(0));
                }
                case 3: {
                    final Map map = (Map)o;
                    this._checkSingleton(map.size());
                    final Map.Entry<K, V> entry = map.entrySet().iterator().next();
                    return Collections.singletonMap((Object)entry.getKey(), entry.getValue());
                }
                case 4: {
                    return Collections.unmodifiableSet((Set<?>)o);
                }
                case 5: {
                    return Collections.unmodifiableList((List<?>)o);
                }
                case 6: {
                    return Collections.unmodifiableMap((Map<?, ?>)o);
                }
                default: {
                    return o;
                }
            }
        }
        
        @Override
        public JavaType getInputType(final TypeFactory typeFactory) {
            return this._inputType;
        }
        
        @Override
        public JavaType getOutputType(final TypeFactory typeFactory) {
            return this._inputType;
        }
        
        private void _checkSingleton(final int n) {
            if (n != 1) {
                throw new IllegalArgumentException("Can not deserialize Singleton container from " + n + " entries");
            }
        }
        
        JavaUtilCollectionsConverter(final int n, final JavaType javaType, final JavaUtilCollectionsDeserializers$1 object) {
            this(n, javaType);
        }
    }
}
