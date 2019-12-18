package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.*;
import java.util.*;
import java.lang.reflect.*;

public class AnnotatedFieldCollector extends CollectorBase
{
    private final TypeFactory _typeFactory;
    private final ClassIntrospector.MixInResolver _mixInResolver;
    
    AnnotatedFieldCollector(final AnnotationIntrospector annotationIntrospector, final TypeFactory typeFactory, final ClassIntrospector.MixInResolver mixInResolver) {
        super(annotationIntrospector);
        this._typeFactory = typeFactory;
        this._mixInResolver = ((annotationIntrospector == null) ? null : mixInResolver);
    }
    
    public static List<AnnotatedField> collectFields(final AnnotationIntrospector annotationIntrospector, final TypeResolutionContext typeResolutionContext, final ClassIntrospector.MixInResolver mixInResolver, final TypeFactory typeFactory, final JavaType javaType) {
        return new AnnotatedFieldCollector(annotationIntrospector, typeFactory, mixInResolver).collect(typeResolutionContext, javaType);
    }
    
    List<AnnotatedField> collect(final TypeResolutionContext typeResolutionContext, final JavaType javaType) {
        final Map<String, FieldBuilder> findFields = this._findFields(typeResolutionContext, javaType, null);
        if (findFields == null) {
            return Collections.emptyList();
        }
        final ArrayList list = new ArrayList<AnnotatedField>(findFields.size());
        final Iterator<FieldBuilder> iterator = findFields.values().iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().build());
        }
        return (List<AnnotatedField>)list;
    }
    
    private Map<String, FieldBuilder> _findFields(final TypeResolutionContext typeResolutionContext, final JavaType javaType, final Map<String, FieldBuilder> map) {
        final JavaType superClass = javaType.getSuperClass();
        if (superClass == null) {
            return map;
        }
        final Class<?> rawClass = javaType.getRawClass();
        Map<String, FieldBuilder> findFields = this._findFields(new TypeResolutionContext.Basic(this._typeFactory, superClass.getBindings()), superClass, map);
        for (final Field field : ClassUtil.getDeclaredFields(rawClass)) {
            if (this._isIncludableField(field)) {
                if (findFields == null) {
                    findFields = new LinkedHashMap<String, FieldBuilder>();
                }
                final FieldBuilder fieldBuilder = new FieldBuilder(typeResolutionContext, field);
                if (this._intr != null) {
                    fieldBuilder.annotations = this.collectAnnotations(fieldBuilder.annotations, field.getDeclaredAnnotations());
                }
                findFields.put(field.getName(), fieldBuilder);
            }
        }
        if (this._mixInResolver != null) {
            final Class<?> mixInClass = this._mixInResolver.findMixInClassFor(rawClass);
            if (mixInClass != null) {
                this._addFieldMixIns(mixInClass, rawClass, findFields);
            }
        }
        return findFields;
    }
    
    private void _addFieldMixIns(final Class<?> clazz, final Class<?> clazz2, final Map<String, FieldBuilder> map) {
        final Iterator<Class<?>> iterator = ClassUtil.findSuperClasses(clazz, clazz2, true).iterator();
        while (iterator.hasNext()) {
            for (final Field field : ClassUtil.getDeclaredFields(iterator.next())) {
                if (this._isIncludableField(field)) {
                    final FieldBuilder fieldBuilder = map.get(field.getName());
                    if (fieldBuilder != null) {
                        fieldBuilder.annotations = this.collectAnnotations(fieldBuilder.annotations, field.getDeclaredAnnotations());
                    }
                }
            }
        }
    }
    
    private boolean _isIncludableField(final Field field) {
        return !field.isSynthetic() && !Modifier.isStatic(field.getModifiers());
    }
    
    private static final class FieldBuilder
    {
        public final TypeResolutionContext typeContext;
        public final Field field;
        public AnnotationCollector annotations;
        
        public FieldBuilder(final TypeResolutionContext typeContext, final Field field) {
            super();
            this.typeContext = typeContext;
            this.field = field;
            this.annotations = AnnotationCollector.emptyCollector();
        }
        
        public AnnotatedField build() {
            return new AnnotatedField(this.typeContext, this.field, this.annotations.asAnnotationMap());
        }
    }
}
