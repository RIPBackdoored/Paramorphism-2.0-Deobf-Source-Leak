package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.*;
import java.util.*;
import com.fasterxml.jackson.databind.cfg.*;
import java.lang.annotation.*;
import com.fasterxml.jackson.databind.util.*;
import java.lang.reflect.*;

public final class AnnotatedClass extends Annotated implements TypeResolutionContext
{
    private static final Creators NO_CREATORS;
    protected final JavaType _type;
    protected final Class<?> _class;
    protected final TypeBindings _bindings;
    protected final List<JavaType> _superTypes;
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final TypeFactory _typeFactory;
    protected final ClassIntrospector.MixInResolver _mixInResolver;
    protected final Class<?> _primaryMixIn;
    protected final Annotations _classAnnotations;
    protected Creators _creators;
    protected AnnotatedMethodMap _memberMethods;
    protected List<AnnotatedField> _fields;
    protected transient Boolean _nonStaticInnerClass;
    
    AnnotatedClass(final JavaType type, final Class<?> class1, final List<JavaType> superTypes, final Class<?> primaryMixIn, final Annotations classAnnotations, final TypeBindings bindings, final AnnotationIntrospector annotationIntrospector, final ClassIntrospector.MixInResolver mixInResolver, final TypeFactory typeFactory) {
        super();
        this._type = type;
        this._class = class1;
        this._superTypes = superTypes;
        this._primaryMixIn = primaryMixIn;
        this._classAnnotations = classAnnotations;
        this._bindings = bindings;
        this._annotationIntrospector = annotationIntrospector;
        this._mixInResolver = mixInResolver;
        this._typeFactory = typeFactory;
    }
    
    AnnotatedClass(final Class<?> class1) {
        super();
        this._type = null;
        this._class = class1;
        this._superTypes = Collections.emptyList();
        this._primaryMixIn = null;
        this._classAnnotations = AnnotationCollector.emptyAnnotations();
        this._bindings = TypeBindings.emptyBindings();
        this._annotationIntrospector = null;
        this._mixInResolver = null;
        this._typeFactory = null;
    }
    
    @Deprecated
    public static AnnotatedClass construct(final JavaType javaType, final MapperConfig<?> mapperConfig) {
        return construct(javaType, mapperConfig, mapperConfig);
    }
    
    @Deprecated
    public static AnnotatedClass construct(final JavaType javaType, final MapperConfig<?> mapperConfig, final ClassIntrospector.MixInResolver mixInResolver) {
        return AnnotatedClassResolver.resolve(mapperConfig, javaType, mixInResolver);
    }
    
    @Deprecated
    public static AnnotatedClass constructWithoutSuperTypes(final Class<?> clazz, final MapperConfig<?> mapperConfig) {
        return constructWithoutSuperTypes(clazz, mapperConfig, mapperConfig);
    }
    
    @Deprecated
    public static AnnotatedClass constructWithoutSuperTypes(final Class<?> clazz, final MapperConfig<?> mapperConfig, final ClassIntrospector.MixInResolver mixInResolver) {
        return AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, clazz, mixInResolver);
    }
    
    @Override
    public JavaType resolveType(final Type type) {
        return this._typeFactory.constructType(type, this._bindings);
    }
    
    @Override
    public Class<?> getAnnotated() {
        return this._class;
    }
    
    public int getModifiers() {
        return this._class.getModifiers();
    }
    
    @Override
    public String getName() {
        return this._class.getName();
    }
    
    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> clazz) {
        return this._classAnnotations.get(clazz);
    }
    
    @Override
    public boolean hasAnnotation(final Class<?> clazz) {
        return this._classAnnotations.has(clazz);
    }
    
    @Override
    public boolean hasOneOf(final Class<? extends Annotation>[] array) {
        return this._classAnnotations.hasOneOf(array);
    }
    
    @Override
    public Class<?> getRawType() {
        return this._class;
    }
    
    @Deprecated
    @Override
    public Iterable<Annotation> annotations() {
        if (this._classAnnotations instanceof AnnotationMap) {
            return ((AnnotationMap)this._classAnnotations).annotations();
        }
        if (this._classAnnotations instanceof AnnotationCollector.OneAnnotation || this._classAnnotations instanceof AnnotationCollector.TwoAnnotations) {
            throw new UnsupportedOperationException("please use getAnnotations/ hasAnnotation to check for Annotations");
        }
        return (Iterable<Annotation>)Collections.emptyList();
    }
    
    @Override
    public JavaType getType() {
        return this._type;
    }
    
    public Annotations getAnnotations() {
        return this._classAnnotations;
    }
    
    public boolean hasAnnotations() {
        return this._classAnnotations.size() > 0;
    }
    
    public AnnotatedConstructor getDefaultConstructor() {
        return this._creators().defaultConstructor;
    }
    
    public List<AnnotatedConstructor> getConstructors() {
        return this._creators().constructors;
    }
    
    public List<AnnotatedMethod> getFactoryMethods() {
        return this._creators().creatorMethods;
    }
    
    @Deprecated
    public List<AnnotatedMethod> getStaticMethods() {
        return this.getFactoryMethods();
    }
    
    public Iterable<AnnotatedMethod> memberMethods() {
        return this._methods();
    }
    
    public int getMemberMethodCount() {
        return this._methods().size();
    }
    
    public AnnotatedMethod findMethod(final String s, final Class<?>[] array) {
        return this._methods().find(s, array);
    }
    
    public int getFieldCount() {
        return this._fields().size();
    }
    
    public Iterable<AnnotatedField> fields() {
        return this._fields();
    }
    
    public boolean isNonStaticInnerClass() {
        Boolean nonStaticInnerClass = this._nonStaticInnerClass;
        if (nonStaticInnerClass == null) {
            nonStaticInnerClass = (this._nonStaticInnerClass = ClassUtil.isNonStaticInnerClass(this._class));
        }
        return nonStaticInnerClass;
    }
    
    private final List<AnnotatedField> _fields() {
        List<AnnotatedField> fields = this._fields;
        if (fields == null) {
            if (this._type == null) {
                fields = Collections.emptyList();
            }
            else {
                fields = AnnotatedFieldCollector.collectFields(this._annotationIntrospector, this, this._mixInResolver, this._typeFactory, this._type);
            }
            this._fields = fields;
        }
        return fields;
    }
    
    private final AnnotatedMethodMap _methods() {
        AnnotatedMethodMap memberMethods = this._memberMethods;
        if (memberMethods == null) {
            if (this._type == null) {
                memberMethods = new AnnotatedMethodMap();
            }
            else {
                memberMethods = AnnotatedMethodCollector.collectMethods(this._annotationIntrospector, this, this._mixInResolver, this._typeFactory, this._type, this._superTypes, this._primaryMixIn);
            }
            this._memberMethods = memberMethods;
        }
        return memberMethods;
    }
    
    private final Creators _creators() {
        Creators creators = this._creators;
        if (creators == null) {
            if (this._type == null) {
                creators = AnnotatedClass.NO_CREATORS;
            }
            else {
                creators = AnnotatedCreatorCollector.collectCreators(this._annotationIntrospector, this, this._type, this._primaryMixIn);
            }
            this._creators = creators;
        }
        return creators;
    }
    
    @Override
    public String toString() {
        return "[AnnotedClass " + this._class.getName() + "]";
    }
    
    @Override
    public int hashCode() {
        return this._class.getName().hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (ClassUtil.hasClass(o, this.getClass()) && ((AnnotatedClass)o)._class == this._class);
    }
    
    @Override
    public AnnotatedElement getAnnotated() {
        return this.getAnnotated();
    }
    
    static {
        NO_CREATORS = new Creators(null, Collections.emptyList(), Collections.emptyList());
    }
    
    public static final class Creators
    {
        public final AnnotatedConstructor defaultConstructor;
        public final List<AnnotatedConstructor> constructors;
        public final List<AnnotatedMethod> creatorMethods;
        
        public Creators(final AnnotatedConstructor defaultConstructor, final List<AnnotatedConstructor> constructors, final List<AnnotatedMethod> creatorMethods) {
            super();
            this.defaultConstructor = defaultConstructor;
            this.constructors = constructors;
            this.creatorMethods = creatorMethods;
        }
    }
}
