package com.fasterxml.jackson.databind.jsontype.impl;

import java.io.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.cfg.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.*;
import java.util.*;
import java.lang.reflect.*;

public class StdSubtypeResolver extends SubtypeResolver implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected LinkedHashSet<NamedType> _registeredSubtypes;
    
    public StdSubtypeResolver() {
        super();
    }
    
    @Override
    public void registerSubtypes(final NamedType... array) {
        if (this._registeredSubtypes == null) {
            this._registeredSubtypes = new LinkedHashSet<NamedType>();
        }
        for (int length = array.length, i = 0; i < length; ++i) {
            this._registeredSubtypes.add(array[i]);
        }
    }
    
    @Override
    public void registerSubtypes(final Class<?>... array) {
        final NamedType[] array2 = new NamedType[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = new NamedType(array[i]);
        }
        this.registerSubtypes(array2);
    }
    
    @Override
    public void registerSubtypes(final Collection<Class<?>> collection) {
        final NamedType[] array = new NamedType[collection.size()];
        int n = 0;
        final Iterator<Class<?>> iterator = collection.iterator();
        while (iterator.hasNext()) {
            array[n++] = new NamedType(iterator.next());
        }
        this.registerSubtypes(array);
    }
    
    @Override
    public Collection<NamedType> collectAndResolveSubtypesByClass(final MapperConfig<?> mapperConfig, final AnnotatedMember annotatedMember, final JavaType javaType) {
        final AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        final Class<?> clazz = (javaType == null) ? annotatedMember.getRawType() : javaType.getRawClass();
        final HashMap<NamedType, NamedType> hashMap = new HashMap<NamedType, NamedType>();
        if (this._registeredSubtypes != null) {
            for (final NamedType namedType : this._registeredSubtypes) {
                if (clazz.isAssignableFrom(namedType.getType())) {
                    this._collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType.getType()), namedType, mapperConfig, annotationIntrospector, hashMap);
                }
            }
        }
        if (annotatedMember != null) {
            final List<NamedType> subtypes = annotationIntrospector.findSubtypes(annotatedMember);
            if (subtypes != null) {
                for (final NamedType namedType2 : subtypes) {
                    this._collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType2.getType()), namedType2, mapperConfig, annotationIntrospector, hashMap);
                }
            }
        }
        this._collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, clazz), new NamedType(clazz, null), mapperConfig, annotationIntrospector, hashMap);
        return new ArrayList<NamedType>(hashMap.values());
    }
    
    @Override
    public Collection<NamedType> collectAndResolveSubtypesByClass(final MapperConfig<?> mapperConfig, final AnnotatedClass annotatedClass) {
        final AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        final HashMap<NamedType, NamedType> hashMap = new HashMap<NamedType, NamedType>();
        if (this._registeredSubtypes != null) {
            final Class<?> rawType = annotatedClass.getRawType();
            for (final NamedType namedType : this._registeredSubtypes) {
                if (rawType.isAssignableFrom(namedType.getType())) {
                    this._collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType.getType()), namedType, mapperConfig, annotationIntrospector, hashMap);
                }
            }
        }
        this._collectAndResolve(annotatedClass, new NamedType(annotatedClass.getRawType(), null), mapperConfig, annotationIntrospector, hashMap);
        return new ArrayList<NamedType>(hashMap.values());
    }
    
    @Override
    public Collection<NamedType> collectAndResolveSubtypesByTypeId(final MapperConfig<?> mapperConfig, final AnnotatedMember annotatedMember, final JavaType javaType) {
        final AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        final Class<?> rawClass = javaType.getRawClass();
        final HashSet<Class<?>> set = new HashSet<Class<?>>();
        final LinkedHashMap<String, NamedType> linkedHashMap = new LinkedHashMap<String, NamedType>();
        this._collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, rawClass), new NamedType(rawClass, null), mapperConfig, set, linkedHashMap);
        if (annotatedMember != null) {
            final List<NamedType> subtypes = annotationIntrospector.findSubtypes(annotatedMember);
            if (subtypes != null) {
                for (final NamedType namedType : subtypes) {
                    this._collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType.getType()), namedType, mapperConfig, set, linkedHashMap);
                }
            }
        }
        if (this._registeredSubtypes != null) {
            for (final NamedType namedType2 : this._registeredSubtypes) {
                if (rawClass.isAssignableFrom(namedType2.getType())) {
                    this._collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType2.getType()), namedType2, mapperConfig, set, linkedHashMap);
                }
            }
        }
        return this._combineNamedAndUnnamed(rawClass, set, linkedHashMap);
    }
    
    @Override
    public Collection<NamedType> collectAndResolveSubtypesByTypeId(final MapperConfig<?> mapperConfig, final AnnotatedClass annotatedClass) {
        final Class<?> rawType = annotatedClass.getRawType();
        final HashSet<Class<?>> set = new HashSet<Class<?>>();
        final LinkedHashMap<String, NamedType> linkedHashMap = new LinkedHashMap<String, NamedType>();
        this._collectAndResolveByTypeId(annotatedClass, new NamedType(rawType, null), mapperConfig, set, linkedHashMap);
        if (this._registeredSubtypes != null) {
            for (final NamedType namedType : this._registeredSubtypes) {
                if (rawType.isAssignableFrom(namedType.getType())) {
                    this._collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType.getType()), namedType, mapperConfig, set, linkedHashMap);
                }
            }
        }
        return this._combineNamedAndUnnamed(rawType, set, linkedHashMap);
    }
    
    protected void _collectAndResolve(final AnnotatedClass annotatedClass, NamedType namedType, final MapperConfig<?> mapperConfig, final AnnotationIntrospector annotationIntrospector, final HashMap<NamedType, NamedType> hashMap) {
        if (!namedType.hasName()) {
            final String typeName = annotationIntrospector.findTypeName(annotatedClass);
            if (typeName != null) {
                namedType = new NamedType(namedType.getType(), typeName);
            }
        }
        if (hashMap.containsKey(namedType)) {
            if (namedType.hasName() && !hashMap.get(namedType).hasName()) {
                hashMap.put(namedType, namedType);
            }
            return;
        }
        hashMap.put(namedType, namedType);
        final List<NamedType> subtypes = annotationIntrospector.findSubtypes(annotatedClass);
        if (subtypes != null && !subtypes.isEmpty()) {
            for (final NamedType namedType2 : subtypes) {
                this._collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType2.getType()), namedType2, mapperConfig, annotationIntrospector, hashMap);
            }
        }
    }
    
    protected void _collectAndResolveByTypeId(final AnnotatedClass annotatedClass, NamedType namedType, final MapperConfig<?> mapperConfig, final Set<Class<?>> set, final Map<String, NamedType> map) {
        final AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        if (!namedType.hasName()) {
            final String typeName = annotationIntrospector.findTypeName(annotatedClass);
            if (typeName != null) {
                namedType = new NamedType(namedType.getType(), typeName);
            }
        }
        if (namedType.hasName()) {
            map.put(namedType.getName(), namedType);
        }
        if (set.add(namedType.getType())) {
            final List<NamedType> subtypes = annotationIntrospector.findSubtypes(annotatedClass);
            if (subtypes != null && !subtypes.isEmpty()) {
                for (final NamedType namedType2 : subtypes) {
                    this._collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType2.getType()), namedType2, mapperConfig, set, map);
                }
            }
        }
    }
    
    protected Collection<NamedType> _combineNamedAndUnnamed(final Class<?> clazz, final Set<Class<?>> set, final Map<String, NamedType> map) {
        final ArrayList<NamedType> list = new ArrayList<NamedType>(map.values());
        final Iterator<NamedType> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            set.remove(iterator.next().getType());
        }
        for (final Class<?> clazz2 : set) {
            if (clazz2 == clazz && Modifier.isAbstract(clazz2.getModifiers())) {
                continue;
            }
            list.add(new NamedType(clazz2));
        }
        return list;
    }
}
