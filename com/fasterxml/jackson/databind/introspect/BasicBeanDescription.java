package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.cfg.*;

public class BasicBeanDescription extends BeanDescription
{
    private static final Class<?>[] NO_VIEWS;
    protected final POJOPropertiesCollector _propCollector;
    protected final MapperConfig<?> _config;
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final AnnotatedClass _classInfo;
    protected Class<?>[] _defaultViews;
    protected boolean _defaultViewsResolved;
    protected List<BeanPropertyDefinition> _properties;
    protected ObjectIdInfo _objectIdInfo;
    
    protected BasicBeanDescription(final POJOPropertiesCollector propCollector, final JavaType javaType, final AnnotatedClass classInfo) {
        super(javaType);
        this._propCollector = propCollector;
        this._config = propCollector.getConfig();
        if (this._config == null) {
            this._annotationIntrospector = null;
        }
        else {
            this._annotationIntrospector = this._config.getAnnotationIntrospector();
        }
        this._classInfo = classInfo;
    }
    
    protected BasicBeanDescription(final MapperConfig<?> config, final JavaType javaType, final AnnotatedClass classInfo, final List<BeanPropertyDefinition> properties) {
        super(javaType);
        this._propCollector = null;
        this._config = config;
        if (this._config == null) {
            this._annotationIntrospector = null;
        }
        else {
            this._annotationIntrospector = this._config.getAnnotationIntrospector();
        }
        this._classInfo = classInfo;
        this._properties = properties;
    }
    
    protected BasicBeanDescription(final POJOPropertiesCollector pojoPropertiesCollector) {
        this(pojoPropertiesCollector, pojoPropertiesCollector.getType(), pojoPropertiesCollector.getClassDef());
        this._objectIdInfo = pojoPropertiesCollector.getObjectIdInfo();
    }
    
    public static BasicBeanDescription forDeserialization(final POJOPropertiesCollector pojoPropertiesCollector) {
        return new BasicBeanDescription(pojoPropertiesCollector);
    }
    
    public static BasicBeanDescription forSerialization(final POJOPropertiesCollector pojoPropertiesCollector) {
        return new BasicBeanDescription(pojoPropertiesCollector);
    }
    
    public static BasicBeanDescription forOtherUse(final MapperConfig<?> mapperConfig, final JavaType javaType, final AnnotatedClass annotatedClass) {
        return new BasicBeanDescription(mapperConfig, javaType, annotatedClass, Collections.emptyList());
    }
    
    protected List<BeanPropertyDefinition> _properties() {
        if (this._properties == null) {
            this._properties = this._propCollector.getProperties();
        }
        return this._properties;
    }
    
    public boolean removeProperty(final String s) {
        final Iterator<BeanPropertyDefinition> iterator = this._properties().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(s)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    public boolean addProperty(final BeanPropertyDefinition beanPropertyDefinition) {
        if (this.hasProperty(beanPropertyDefinition.getFullName())) {
            return false;
        }
        this._properties().add(beanPropertyDefinition);
        return true;
    }
    
    public boolean hasProperty(final PropertyName propertyName) {
        return this.findProperty(propertyName) != null;
    }
    
    public BeanPropertyDefinition findProperty(final PropertyName propertyName) {
        for (final BeanPropertyDefinition beanPropertyDefinition : this._properties()) {
            if (beanPropertyDefinition.hasName(propertyName)) {
                return beanPropertyDefinition;
            }
        }
        return null;
    }
    
    @Override
    public AnnotatedClass getClassInfo() {
        return this._classInfo;
    }
    
    @Override
    public ObjectIdInfo getObjectIdInfo() {
        return this._objectIdInfo;
    }
    
    @Override
    public List<BeanPropertyDefinition> findProperties() {
        return this._properties();
    }
    
    @Deprecated
    @Override
    public AnnotatedMethod findJsonValueMethod() {
        return (this._propCollector == null) ? null : this._propCollector.getJsonValueMethod();
    }
    
    @Override
    public AnnotatedMember findJsonValueAccessor() {
        return (this._propCollector == null) ? null : this._propCollector.getJsonValueAccessor();
    }
    
    @Override
    public Set<String> getIgnoredPropertyNames() {
        final Set<String> set = (this._propCollector == null) ? null : this._propCollector.getIgnoredPropertyNames();
        if (set == null) {
            return Collections.emptySet();
        }
        return set;
    }
    
    @Override
    public boolean hasKnownClassAnnotations() {
        return this._classInfo.hasAnnotations();
    }
    
    @Override
    public Annotations getClassAnnotations() {
        return this._classInfo.getAnnotations();
    }
    
    @Deprecated
    @Override
    public TypeBindings bindingsForBeanType() {
        return this._type.getBindings();
    }
    
    @Deprecated
    @Override
    public JavaType resolveType(final Type type) {
        if (type == null) {
            return null;
        }
        return this._config.getTypeFactory().constructType(type, this._type.getBindings());
    }
    
    @Override
    public AnnotatedConstructor findDefaultConstructor() {
        return this._classInfo.getDefaultConstructor();
    }
    
    @Override
    public AnnotatedMember findAnySetterAccessor() throws IllegalArgumentException {
        if (this._propCollector != null) {
            final AnnotatedMethod anySetterMethod = this._propCollector.getAnySetterMethod();
            if (anySetterMethod != null) {
                final Class<?> rawParameterType = anySetterMethod.getRawParameterType(0);
                if (rawParameterType != String.class && rawParameterType != Object.class) {
                    throw new IllegalArgumentException(String.format("Invalid 'any-setter' annotation on method '%s()': first argument not of type String or Object, but %s", anySetterMethod.getName(), rawParameterType.getName()));
                }
                return anySetterMethod;
            }
            else {
                final AnnotatedMember anySetterField = this._propCollector.getAnySetterField();
                if (anySetterField != null) {
                    if (!Map.class.isAssignableFrom(anySetterField.getRawType())) {
                        throw new IllegalArgumentException(String.format("Invalid 'any-setter' annotation on field '%s': type is not instance of java.util.Map", anySetterField.getName()));
                    }
                    return anySetterField;
                }
            }
        }
        return null;
    }
    
    @Override
    public Map<Object, AnnotatedMember> findInjectables() {
        if (this._propCollector != null) {
            return this._propCollector.getInjectables();
        }
        return Collections.emptyMap();
    }
    
    @Override
    public List<AnnotatedConstructor> getConstructors() {
        return this._classInfo.getConstructors();
    }
    
    @Override
    public Object instantiateBean(final boolean b) {
        final AnnotatedConstructor defaultConstructor = this._classInfo.getDefaultConstructor();
        if (defaultConstructor == null) {
            return null;
        }
        if (b) {
            defaultConstructor.fixAccess(this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
        }
        try {
            return defaultConstructor.getAnnotated().newInstance(new Object[0]);
        }
        catch (Exception ex) {
            Throwable cause;
            for (cause = ex; cause.getCause() != null; cause = cause.getCause()) {}
            ClassUtil.throwIfError(cause);
            ClassUtil.throwIfRTE(cause);
            throw new IllegalArgumentException("Failed to instantiate bean of type " + this._classInfo.getAnnotated().getName() + ": (" + ((Exception)cause).getClass().getName() + ") " + cause.getMessage(), cause);
        }
    }
    
    @Override
    public AnnotatedMethod findMethod(final String s, final Class<?>[] array) {
        return this._classInfo.findMethod(s, array);
    }
    
    @Override
    public JsonFormat.Value findExpectedFormat(JsonFormat.Value value) {
        if (this._annotationIntrospector != null) {
            final JsonFormat.Value format = this._annotationIntrospector.findFormat(this._classInfo);
            if (format != null) {
                if (value == null) {
                    value = format;
                }
                else {
                    value = value.withOverrides(format);
                }
            }
        }
        final JsonFormat.Value defaultPropertyFormat = this._config.getDefaultPropertyFormat(this._classInfo.getRawType());
        if (defaultPropertyFormat != null) {
            if (value == null) {
                value = defaultPropertyFormat;
            }
            else {
                value = value.withOverrides(defaultPropertyFormat);
            }
        }
        return value;
    }
    
    @Override
    public Class<?>[] findDefaultViews() {
        if (!this._defaultViewsResolved) {
            this._defaultViewsResolved = true;
            Class<?>[] no_VIEWS = (Class<?>[])((this._annotationIntrospector == null) ? null : this._annotationIntrospector.findViews(this._classInfo));
            if (no_VIEWS == null && !this._config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION)) {
                no_VIEWS = BasicBeanDescription.NO_VIEWS;
            }
            this._defaultViews = no_VIEWS;
        }
        return this._defaultViews;
    }
    
    @Override
    public Converter<Object, Object> findSerializationConverter() {
        if (this._annotationIntrospector == null) {
            return null;
        }
        return this._createConverter(this._annotationIntrospector.findSerializationConverter(this._classInfo));
    }
    
    @Override
    public JsonInclude.Value findPropertyInclusion(final JsonInclude.Value value) {
        if (this._annotationIntrospector != null) {
            final JsonInclude.Value propertyInclusion = this._annotationIntrospector.findPropertyInclusion(this._classInfo);
            if (propertyInclusion != null) {
                return (value == null) ? propertyInclusion : value.withOverrides(propertyInclusion);
            }
        }
        return value;
    }
    
    @Override
    public AnnotatedMember findAnyGetter() throws IllegalArgumentException {
        final AnnotatedMember annotatedMember = (this._propCollector == null) ? null : this._propCollector.getAnyGetter();
        if (annotatedMember != null && !Map.class.isAssignableFrom(annotatedMember.getRawType())) {
            throw new IllegalArgumentException("Invalid 'any-getter' annotation on method " + annotatedMember.getName() + "(): return type is not instance of java.util.Map");
        }
        return annotatedMember;
    }
    
    @Override
    public List<BeanPropertyDefinition> findBackReferences() {
        List<BeanPropertyDefinition> list = null;
        HashSet<String> set = null;
        for (final BeanPropertyDefinition beanPropertyDefinition : this._properties()) {
            final AnnotationIntrospector.ReferenceProperty referenceType = beanPropertyDefinition.findReferenceType();
            if (referenceType != null) {
                if (!referenceType.isBackReference()) {
                    continue;
                }
                final String name = referenceType.getName();
                if (list == null) {
                    list = new ArrayList<BeanPropertyDefinition>();
                    set = new HashSet<String>();
                    set.add(name);
                }
                else if (!set.add(name)) {
                    throw new IllegalArgumentException("Multiple back-reference properties with name '" + name + "'");
                }
                list.add(beanPropertyDefinition);
            }
        }
        return list;
    }
    
    @Deprecated
    @Override
    public Map<String, AnnotatedMember> findBackReferenceProperties() {
        final List<BeanPropertyDefinition> backReferences = this.findBackReferences();
        if (backReferences == null) {
            return null;
        }
        final HashMap<String, AnnotatedMember> hashMap = new HashMap<String, AnnotatedMember>();
        for (final BeanPropertyDefinition beanPropertyDefinition : backReferences) {
            hashMap.put(beanPropertyDefinition.getName(), beanPropertyDefinition.getMutator());
        }
        return hashMap;
    }
    
    @Override
    public List<AnnotatedMethod> getFactoryMethods() {
        final List<AnnotatedMethod> factoryMethods = this._classInfo.getFactoryMethods();
        if (factoryMethods.isEmpty()) {
            return factoryMethods;
        }
        List<AnnotatedMethod> list = null;
        for (final AnnotatedMethod annotatedMethod : factoryMethods) {
            if (this.isFactoryMethod(annotatedMethod)) {
                if (list == null) {
                    list = new ArrayList<AnnotatedMethod>();
                }
                list.add(annotatedMethod);
            }
        }
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }
    
    @Override
    public Constructor<?> findSingleArgConstructor(final Class<?>... array) {
        for (final AnnotatedConstructor annotatedConstructor : this._classInfo.getConstructors()) {
            if (annotatedConstructor.getParameterCount() == 1) {
                final Class<?> rawParameterType = annotatedConstructor.getRawParameterType(0);
                for (int length = array.length, i = 0; i < length; ++i) {
                    if (array[i] == rawParameterType) {
                        return annotatedConstructor.getAnnotated();
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public Method findFactoryMethod(final Class<?>... array) {
        for (final AnnotatedMethod annotatedMethod : this._classInfo.getFactoryMethods()) {
            if (this.isFactoryMethod(annotatedMethod) && annotatedMethod.getParameterCount() == 1) {
                final Class<?> rawParameterType = annotatedMethod.getRawParameterType(0);
                for (int length = array.length, i = 0; i < length; ++i) {
                    if (rawParameterType.isAssignableFrom(array[i])) {
                        return annotatedMethod.getAnnotated();
                    }
                }
            }
        }
        return null;
    }
    
    protected boolean isFactoryMethod(final AnnotatedMethod annotatedMethod) {
        if (!this.getBeanClass().isAssignableFrom(annotatedMethod.getRawReturnType())) {
            return false;
        }
        final JsonCreator.Mode creatorAnnotation = this._annotationIntrospector.findCreatorAnnotation(this._config, annotatedMethod);
        if (creatorAnnotation != null && creatorAnnotation != JsonCreator.Mode.DISABLED) {
            return true;
        }
        final String name = annotatedMethod.getName();
        if ("valueOf".equals(name) && annotatedMethod.getParameterCount() == 1) {
            return true;
        }
        if ("fromString".equals(name) && annotatedMethod.getParameterCount() == 1) {
            final Class<?> rawParameterType = annotatedMethod.getRawParameterType(0);
            if (rawParameterType == String.class || CharSequence.class.isAssignableFrom(rawParameterType)) {
                return true;
            }
        }
        return false;
    }
    
    @Deprecated
    protected PropertyName _findCreatorPropertyName(final AnnotatedParameter annotatedParameter) {
        PropertyName propertyName = this._annotationIntrospector.findNameForDeserialization(annotatedParameter);
        if (propertyName == null || propertyName.isEmpty()) {
            final String implicitPropertyName = this._annotationIntrospector.findImplicitPropertyName(annotatedParameter);
            if (implicitPropertyName != null && !implicitPropertyName.isEmpty()) {
                propertyName = PropertyName.construct(implicitPropertyName);
            }
        }
        return propertyName;
    }
    
    @Override
    public Class<?> findPOJOBuilder() {
        return (this._annotationIntrospector == null) ? null : this._annotationIntrospector.findPOJOBuilder(this._classInfo);
    }
    
    @Override
    public JsonPOJOBuilder.Value findPOJOBuilderConfig() {
        return (this._annotationIntrospector == null) ? null : this._annotationIntrospector.findPOJOBuilderConfig(this._classInfo);
    }
    
    @Override
    public Converter<Object, Object> findDeserializationConverter() {
        if (this._annotationIntrospector == null) {
            return null;
        }
        return this._createConverter(this._annotationIntrospector.findDeserializationConverter(this._classInfo));
    }
    
    @Override
    public String findClassDescription() {
        return (this._annotationIntrospector == null) ? null : this._annotationIntrospector.findClassDescription(this._classInfo);
    }
    
    @Deprecated
    public LinkedHashMap<String, AnnotatedField> _findPropertyFields(final Collection<String> collection, final boolean b) {
        final LinkedHashMap<String, AnnotatedField> linkedHashMap = new LinkedHashMap<String, AnnotatedField>();
        for (final BeanPropertyDefinition beanPropertyDefinition : this._properties()) {
            final AnnotatedField field = beanPropertyDefinition.getField();
            if (field != null) {
                final String name = beanPropertyDefinition.getName();
                if (collection != null && collection.contains(name)) {
                    continue;
                }
                linkedHashMap.put(name, field);
            }
        }
        return linkedHashMap;
    }
    
    protected Converter<Object, Object> _createConverter(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Converter) {
            return (Converter<Object, Object>)o;
        }
        if (!(o instanceof Class)) {
            throw new IllegalStateException("AnnotationIntrospector returned Converter definition of type " + o.getClass().getName() + "; expected type Converter or Class<Converter> instead");
        }
        final Class clazz = (Class)o;
        if (clazz == Converter.None.class || ClassUtil.isBogusClass(clazz)) {
            return null;
        }
        if (!Converter.class.isAssignableFrom(clazz)) {
            throw new IllegalStateException("AnnotationIntrospector returned Class " + clazz.getName() + "; expected Class<Converter>");
        }
        final HandlerInstantiator handlerInstantiator = this._config.getHandlerInstantiator();
        Converter<?, ?> converter = (handlerInstantiator == null) ? null : handlerInstantiator.converterInstance(this._config, this._classInfo, clazz);
        if (converter == null) {
            converter = ClassUtil.createInstance((Class<Converter<Object, Object>>)clazz, this._config.canOverrideAccessModifiers());
        }
        return (Converter<Object, Object>)converter;
    }
    
    static {
        NO_VIEWS = new Class[0];
    }
}
