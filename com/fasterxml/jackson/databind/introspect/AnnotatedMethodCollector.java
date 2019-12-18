package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.databind.*;
import java.util.*;
import com.fasterxml.jackson.databind.util.*;
import java.lang.reflect.*;
import java.lang.annotation.*;

public class AnnotatedMethodCollector extends CollectorBase
{
    private final ClassIntrospector.MixInResolver _mixInResolver;
    
    AnnotatedMethodCollector(final AnnotationIntrospector annotationIntrospector, final ClassIntrospector.MixInResolver mixInResolver) {
        super(annotationIntrospector);
        this._mixInResolver = ((annotationIntrospector == null) ? null : mixInResolver);
    }
    
    public static AnnotatedMethodMap collectMethods(final AnnotationIntrospector annotationIntrospector, final TypeResolutionContext typeResolutionContext, final ClassIntrospector.MixInResolver mixInResolver, final TypeFactory typeFactory, final JavaType javaType, final List<JavaType> list, final Class<?> clazz) {
        return new AnnotatedMethodCollector(annotationIntrospector, mixInResolver).collect(typeFactory, typeResolutionContext, javaType, list, clazz);
    }
    
    AnnotatedMethodMap collect(final TypeFactory typeFactory, final TypeResolutionContext typeResolutionContext, final JavaType javaType, final List<JavaType> list, final Class<?> clazz) {
        final LinkedHashMap linkedHashMap = new LinkedHashMap<Object, Object>();
        this._addMemberMethods(typeResolutionContext, javaType.getRawClass(), linkedHashMap, clazz);
        for (final JavaType javaType2 : list) {
            this._addMemberMethods(new TypeResolutionContext.Basic(typeFactory, javaType2.getBindings()), javaType2.getRawClass(), linkedHashMap, (this._mixInResolver == null) ? null : this._mixInResolver.findMixInClassFor(javaType2.getRawClass()));
        }
        boolean b = false;
        if (this._mixInResolver != null) {
            final Class<?> mixInClass = this._mixInResolver.findMixInClassFor(Object.class);
            if (mixInClass != null) {
                this._addMethodMixIns(typeResolutionContext, javaType.getRawClass(), linkedHashMap, mixInClass);
                b = true;
            }
        }
        if (b && this._intr != null && !linkedHashMap.isEmpty()) {
            for (final Map.Entry<Object, Object> entry : linkedHashMap.entrySet()) {
                final MemberKey memberKey = entry.getKey();
                if ("hashCode".equals(memberKey.getName())) {
                    if (0 != memberKey.argCount()) {
                        continue;
                    }
                    try {
                        final Method declaredMethod = Object.class.getDeclaredMethod(memberKey.getName(), (Class<?>[])new Class[0]);
                        if (declaredMethod == null) {
                            continue;
                        }
                        final MethodBuilder methodBuilder = entry.getValue();
                        methodBuilder.annotations = this.collectDefaultAnnotations(methodBuilder.annotations, declaredMethod.getDeclaredAnnotations());
                        methodBuilder.method = declaredMethod;
                    }
                    catch (Exception ex) {}
                }
            }
        }
        if (linkedHashMap.isEmpty()) {
            return new AnnotatedMethodMap();
        }
        final LinkedHashMap linkedHashMap2 = new LinkedHashMap<MemberKey, AnnotatedMethod>(linkedHashMap.size());
        for (final Map.Entry<Object, Object> entry2 : linkedHashMap.entrySet()) {
            final AnnotatedMethod build = entry2.getValue().build();
            if (build != null) {
                linkedHashMap2.put((K)entry2.getKey(), build);
            }
        }
        return new AnnotatedMethodMap((Map<MemberKey, AnnotatedMethod>)linkedHashMap2);
    }
    
    private void _addMemberMethods(final TypeResolutionContext typeContext, final Class<?> clazz, final Map<MemberKey, MethodBuilder> map, final Class<?> clazz2) {
        if (clazz2 != null) {
            this._addMethodMixIns(typeContext, clazz, map, clazz2);
        }
        if (clazz == null) {
            return;
        }
        for (final Method method : ClassUtil.getClassMethods(clazz)) {
            if (this._isIncludableMemberMethod(method)) {
                final MemberKey memberKey = new MemberKey(method);
                final MethodBuilder methodBuilder = map.get(memberKey);
                if (methodBuilder == null) {
                    map.put(memberKey, new MethodBuilder(typeContext, method, (this._intr == null) ? AnnotationCollector.emptyCollector() : this.collectAnnotations(method.getDeclaredAnnotations())));
                }
                else {
                    if (this._intr != null) {
                        methodBuilder.annotations = this.collectDefaultAnnotations(methodBuilder.annotations, method.getDeclaredAnnotations());
                    }
                    final Method method2 = methodBuilder.method;
                    if (method2 == null) {
                        methodBuilder.method = method;
                    }
                    else if (Modifier.isAbstract(method2.getModifiers()) && !Modifier.isAbstract(method.getModifiers())) {
                        methodBuilder.method = method;
                        methodBuilder.typeContext = typeContext;
                    }
                }
            }
        }
    }
    
    protected void _addMethodMixIns(final TypeResolutionContext typeResolutionContext, final Class<?> clazz, final Map<MemberKey, MethodBuilder> map, final Class<?> clazz2) {
        if (this._intr == null) {
            return;
        }
        final Iterator<Class<?>> iterator = ClassUtil.findRawSuperTypes(clazz2, clazz, true).iterator();
        while (iterator.hasNext()) {
            for (final Method method : ClassUtil.getDeclaredMethods(iterator.next())) {
                if (this._isIncludableMemberMethod(method)) {
                    final MemberKey memberKey = new MemberKey(method);
                    final MethodBuilder methodBuilder = map.get(memberKey);
                    final Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                    if (methodBuilder == null) {
                        map.put(memberKey, new MethodBuilder(typeResolutionContext, null, this.collectAnnotations(declaredAnnotations)));
                    }
                    else {
                        methodBuilder.annotations = this.collectDefaultAnnotations(methodBuilder.annotations, declaredAnnotations);
                    }
                }
            }
        }
    }
    
    private boolean _isIncludableMemberMethod(final Method method) {
        return !Modifier.isStatic(method.getModifiers()) && !method.isSynthetic() && !method.isBridge() && method.getParameterTypes().length <= 2;
    }
    
    private static final class MethodBuilder
    {
        public TypeResolutionContext typeContext;
        public Method method;
        public AnnotationCollector annotations;
        
        public MethodBuilder(final TypeResolutionContext typeContext, final Method method, final AnnotationCollector annotations) {
            super();
            this.typeContext = typeContext;
            this.method = method;
            this.annotations = annotations;
        }
        
        public AnnotatedMethod build() {
            if (this.method == null) {
                return null;
            }
            return new AnnotatedMethod(this.typeContext, this.method, this.annotations.asAnnotationMap(), null);
        }
    }
}
