package com.fasterxml.jackson.databind.introspect;

import java.lang.annotation.*;
import com.fasterxml.jackson.databind.ext.*;
import com.fasterxml.jackson.core.*;
import java.lang.reflect.*;
import java.util.*;
import com.fasterxml.jackson.databind.ser.std.*;
import java.io.*;
import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.ser.impl.*;
import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.databind.cfg.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.jsontype.impl.*;

public class JacksonAnnotationIntrospector extends AnnotationIntrospector implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Class<? extends Annotation>[] ANNOTATIONS_TO_INFER_SER;
    private static final Class<? extends Annotation>[] ANNOTATIONS_TO_INFER_DESER;
    private static final Java7Support _java7Helper;
    protected transient LRUMap<Class<?>, Boolean> _annotationsInside;
    protected boolean _cfgConstructorPropertiesImpliesCreator;
    
    public JacksonAnnotationIntrospector() {
        super();
        this._annotationsInside = new LRUMap<Class<?>, Boolean>(48, 48);
        this._cfgConstructorPropertiesImpliesCreator = true;
    }
    
    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
    
    protected Object readResolve() {
        if (this._annotationsInside == null) {
            this._annotationsInside = new LRUMap<Class<?>, Boolean>(48, 48);
        }
        return this;
    }
    
    public JacksonAnnotationIntrospector setConstructorPropertiesImpliesCreator(final boolean cfgConstructorPropertiesImpliesCreator) {
        this._cfgConstructorPropertiesImpliesCreator = cfgConstructorPropertiesImpliesCreator;
        return this;
    }
    
    @Override
    public boolean isAnnotationBundle(final Annotation annotation) {
        final Class<? extends Annotation> annotationType = annotation.annotationType();
        Boolean value = this._annotationsInside.get(annotationType);
        if (value == null) {
            value = (annotationType.getAnnotation(JacksonAnnotationsInside.class) != null);
            this._annotationsInside.putIfAbsent(annotationType, value);
        }
        return value;
    }
    
    @Deprecated
    @Override
    public String findEnumValue(final Enum<?> enum1) {
        try {
            final Field field = enum1.getClass().getField(enum1.name());
            if (field != null) {
                final JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                if (jsonProperty != null) {
                    final String value = jsonProperty.value();
                    if (value != null && !value.isEmpty()) {
                        return value;
                    }
                }
            }
        }
        catch (SecurityException ex) {}
        catch (NoSuchFieldException ex2) {}
        return enum1.name();
    }
    
    @Override
    public String[] findEnumValues(final Class<?> clazz, final Enum<?>[] array, final String[] array2) {
        HashMap<String, String> hashMap = null;
        for (final Field field : ClassUtil.getDeclaredFields(clazz)) {
            if (field.isEnumConstant()) {
                final JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                if (jsonProperty != null) {
                    final String value = jsonProperty.value();
                    if (!value.isEmpty()) {
                        if (hashMap == null) {
                            hashMap = new HashMap<String, String>();
                        }
                        hashMap.put(field.getName(), value);
                    }
                }
            }
        }
        if (hashMap != null) {
            for (int j = 0; j < array.length; ++j) {
                final String s = hashMap.get(array[j].name());
                if (s != null) {
                    array2[j] = s;
                }
            }
        }
        return array2;
    }
    
    @Override
    public Enum<?> findDefaultEnumValue(final Class<Enum<?>> clazz) {
        return ClassUtil.findFirstAnnotatedEnumValue(clazz, JsonEnumDefaultValue.class);
    }
    
    @Override
    public PropertyName findRootName(final AnnotatedClass annotatedClass) {
        final JsonRootName jsonRootName = this._findAnnotation(annotatedClass, JsonRootName.class);
        if (jsonRootName == null) {
            return null;
        }
        String namespace = jsonRootName.namespace();
        if (namespace != null && namespace.length() == 0) {
            namespace = null;
        }
        return PropertyName.construct(jsonRootName.value(), namespace);
    }
    
    @Override
    public JsonIgnoreProperties.Value findPropertyIgnorals(final Annotated annotated) {
        final JsonIgnoreProperties jsonIgnoreProperties = this._findAnnotation(annotated, JsonIgnoreProperties.class);
        if (jsonIgnoreProperties == null) {
            return JsonIgnoreProperties.Value.empty();
        }
        return JsonIgnoreProperties.Value.from(jsonIgnoreProperties);
    }
    
    @Override
    public Boolean isIgnorableType(final AnnotatedClass annotatedClass) {
        final JsonIgnoreType jsonIgnoreType = this._findAnnotation(annotatedClass, JsonIgnoreType.class);
        return (jsonIgnoreType == null) ? null : Boolean.valueOf(jsonIgnoreType.value());
    }
    
    @Override
    public Object findFilterId(final Annotated annotated) {
        final JsonFilter jsonFilter = this._findAnnotation(annotated, JsonFilter.class);
        if (jsonFilter != null) {
            final String value = jsonFilter.value();
            if (value.length() > 0) {
                return value;
            }
        }
        return null;
    }
    
    @Override
    public Object findNamingStrategy(final AnnotatedClass annotatedClass) {
        final JsonNaming jsonNaming = this._findAnnotation(annotatedClass, JsonNaming.class);
        return (jsonNaming == null) ? null : jsonNaming.value();
    }
    
    @Override
    public String findClassDescription(final AnnotatedClass annotatedClass) {
        final JsonClassDescription jsonClassDescription = this._findAnnotation(annotatedClass, JsonClassDescription.class);
        return (jsonClassDescription == null) ? null : jsonClassDescription.value();
    }
    
    @Override
    public VisibilityChecker<?> findAutoDetectVisibility(final AnnotatedClass annotatedClass, final VisibilityChecker<?> visibilityChecker) {
        final JsonAutoDetect jsonAutoDetect = this._findAnnotation(annotatedClass, JsonAutoDetect.class);
        return (VisibilityChecker<?>)((jsonAutoDetect == null) ? visibilityChecker : visibilityChecker.with(jsonAutoDetect));
    }
    
    @Override
    public String findImplicitPropertyName(final AnnotatedMember annotatedMember) {
        final PropertyName findConstructorName = this._findConstructorName(annotatedMember);
        return (findConstructorName == null) ? null : findConstructorName.getSimpleName();
    }
    
    @Override
    public List<PropertyName> findPropertyAliases(final Annotated annotated) {
        final JsonAlias jsonAlias = this._findAnnotation(annotated, JsonAlias.class);
        if (jsonAlias == null) {
            return null;
        }
        final String[] value = jsonAlias.value();
        final int length = value.length;
        if (length == 0) {
            return Collections.emptyList();
        }
        final ArrayList list = new ArrayList<PropertyName>(length);
        for (int i = 0; i < length; ++i) {
            list.add(PropertyName.construct(value[i]));
        }
        return (List<PropertyName>)list;
    }
    
    @Override
    public boolean hasIgnoreMarker(final AnnotatedMember annotatedMember) {
        return this._isIgnorable(annotatedMember);
    }
    
    @Override
    public Boolean hasRequiredMarker(final AnnotatedMember annotatedMember) {
        final JsonProperty jsonProperty = this._findAnnotation(annotatedMember, JsonProperty.class);
        if (jsonProperty != null) {
            return jsonProperty.required();
        }
        return null;
    }
    
    @Override
    public JsonProperty.Access findPropertyAccess(final Annotated annotated) {
        final JsonProperty jsonProperty = this._findAnnotation(annotated, JsonProperty.class);
        if (jsonProperty != null) {
            return jsonProperty.access();
        }
        return null;
    }
    
    @Override
    public String findPropertyDescription(final Annotated annotated) {
        final JsonPropertyDescription jsonPropertyDescription = this._findAnnotation(annotated, JsonPropertyDescription.class);
        return (jsonPropertyDescription == null) ? null : jsonPropertyDescription.value();
    }
    
    @Override
    public Integer findPropertyIndex(final Annotated annotated) {
        final JsonProperty jsonProperty = this._findAnnotation(annotated, JsonProperty.class);
        if (jsonProperty != null) {
            final int index = jsonProperty.index();
            if (index != -1) {
                return index;
            }
        }
        return null;
    }
    
    @Override
    public String findPropertyDefaultValue(final Annotated annotated) {
        final JsonProperty jsonProperty = this._findAnnotation(annotated, JsonProperty.class);
        if (jsonProperty == null) {
            return null;
        }
        final String defaultValue = jsonProperty.defaultValue();
        return defaultValue.isEmpty() ? null : defaultValue;
    }
    
    @Override
    public JsonFormat.Value findFormat(final Annotated annotated) {
        final JsonFormat jsonFormat = this._findAnnotation(annotated, JsonFormat.class);
        return (jsonFormat == null) ? null : new JsonFormat.Value(jsonFormat);
    }
    
    @Override
    public ReferenceProperty findReferenceType(final AnnotatedMember annotatedMember) {
        final JsonManagedReference jsonManagedReference = this._findAnnotation(annotatedMember, JsonManagedReference.class);
        if (jsonManagedReference != null) {
            return ReferenceProperty.managed(jsonManagedReference.value());
        }
        final JsonBackReference jsonBackReference = this._findAnnotation(annotatedMember, JsonBackReference.class);
        if (jsonBackReference != null) {
            return ReferenceProperty.back(jsonBackReference.value());
        }
        return null;
    }
    
    @Override
    public NameTransformer findUnwrappingNameTransformer(final AnnotatedMember annotatedMember) {
        final JsonUnwrapped jsonUnwrapped = this._findAnnotation(annotatedMember, JsonUnwrapped.class);
        if (jsonUnwrapped == null || !jsonUnwrapped.enabled()) {
            return null;
        }
        return NameTransformer.simpleTransformer(jsonUnwrapped.prefix(), jsonUnwrapped.suffix());
    }
    
    @Override
    public JacksonInject.Value findInjectableValue(final AnnotatedMember annotatedMember) {
        final JacksonInject jacksonInject = this._findAnnotation(annotatedMember, JacksonInject.class);
        if (jacksonInject == null) {
            return null;
        }
        JacksonInject.Value value = JacksonInject.Value.from(jacksonInject);
        if (!value.hasId()) {
            String s;
            if (!(annotatedMember instanceof AnnotatedMethod)) {
                s = annotatedMember.getRawType().getName();
            }
            else {
                final AnnotatedMethod annotatedMethod = (AnnotatedMethod)annotatedMember;
                if (annotatedMethod.getParameterCount() == 0) {
                    s = annotatedMember.getRawType().getName();
                }
                else {
                    s = annotatedMethod.getRawParameterType(0).getName();
                }
            }
            value = value.withId(s);
        }
        return value;
    }
    
    @Deprecated
    @Override
    public Object findInjectableValueId(final AnnotatedMember annotatedMember) {
        final JacksonInject.Value injectableValue = this.findInjectableValue(annotatedMember);
        return (injectableValue == null) ? null : injectableValue.getId();
    }
    
    @Override
    public Class<?>[] findViews(final Annotated annotated) {
        final JsonView jsonView = this._findAnnotation(annotated, JsonView.class);
        return (Class<?>[])((jsonView == null) ? null : jsonView.value());
    }
    
    @Override
    public AnnotatedMethod resolveSetterConflict(final MapperConfig<?> mapperConfig, final AnnotatedMethod annotatedMethod, final AnnotatedMethod annotatedMethod2) {
        final Class<?> rawParameterType = annotatedMethod.getRawParameterType(0);
        final Class<?> rawParameterType2 = annotatedMethod2.getRawParameterType(0);
        if (rawParameterType.isPrimitive()) {
            if (!rawParameterType2.isPrimitive()) {
                return annotatedMethod;
            }
        }
        else if (rawParameterType2.isPrimitive()) {
            return annotatedMethod2;
        }
        if (rawParameterType == String.class) {
            if (rawParameterType2 != String.class) {
                return annotatedMethod;
            }
        }
        else if (rawParameterType2 == String.class) {
            return annotatedMethod2;
        }
        return null;
    }
    
    @Override
    public TypeResolverBuilder<?> findTypeResolver(final MapperConfig<?> mapperConfig, final AnnotatedClass annotatedClass, final JavaType javaType) {
        return this._findTypeResolver(mapperConfig, annotatedClass, javaType);
    }
    
    @Override
    public TypeResolverBuilder<?> findPropertyTypeResolver(final MapperConfig<?> mapperConfig, final AnnotatedMember annotatedMember, final JavaType javaType) {
        if (javaType.isContainerType() || javaType.isReferenceType()) {
            return null;
        }
        return this._findTypeResolver(mapperConfig, annotatedMember, javaType);
    }
    
    @Override
    public TypeResolverBuilder<?> findPropertyContentTypeResolver(final MapperConfig<?> mapperConfig, final AnnotatedMember annotatedMember, final JavaType javaType) {
        if (javaType.getContentType() == null) {
            throw new IllegalArgumentException("Must call method with a container or reference type (got " + javaType + ")");
        }
        return this._findTypeResolver(mapperConfig, annotatedMember, javaType);
    }
    
    @Override
    public List<NamedType> findSubtypes(final Annotated annotated) {
        final JsonSubTypes jsonSubTypes = this._findAnnotation(annotated, JsonSubTypes.class);
        if (jsonSubTypes == null) {
            return null;
        }
        final JsonSubTypes.Type[] value = jsonSubTypes.value();
        final ArrayList list = new ArrayList<NamedType>(value.length);
        for (final JsonSubTypes.Type type : value) {
            list.add(new NamedType(type.value(), type.name()));
        }
        return (List<NamedType>)list;
    }
    
    @Override
    public String findTypeName(final AnnotatedClass annotatedClass) {
        final JsonTypeName jsonTypeName = this._findAnnotation(annotatedClass, JsonTypeName.class);
        return (jsonTypeName == null) ? null : jsonTypeName.value();
    }
    
    @Override
    public Boolean isTypeId(final AnnotatedMember annotatedMember) {
        return this._hasAnnotation(annotatedMember, JsonTypeId.class);
    }
    
    @Override
    public ObjectIdInfo findObjectIdInfo(final Annotated annotated) {
        final JsonIdentityInfo jsonIdentityInfo = this._findAnnotation(annotated, JsonIdentityInfo.class);
        if (jsonIdentityInfo == null || jsonIdentityInfo.generator() == ObjectIdGenerators.None.class) {
            return null;
        }
        return new ObjectIdInfo(PropertyName.construct(jsonIdentityInfo.property()), jsonIdentityInfo.scope(), jsonIdentityInfo.generator(), jsonIdentityInfo.resolver());
    }
    
    @Override
    public ObjectIdInfo findObjectReferenceInfo(final Annotated annotated, ObjectIdInfo empty) {
        final JsonIdentityReference jsonIdentityReference = this._findAnnotation(annotated, JsonIdentityReference.class);
        if (jsonIdentityReference == null) {
            return empty;
        }
        if (empty == null) {
            empty = ObjectIdInfo.empty();
        }
        return empty.withAlwaysAsId(jsonIdentityReference.alwaysAsId());
    }
    
    @Override
    public Object findSerializer(final Annotated annotated) {
        final JsonSerialize jsonSerialize = this._findAnnotation(annotated, JsonSerialize.class);
        if (jsonSerialize != null) {
            final Class<? extends JsonSerializer> using = jsonSerialize.using();
            if (using != JsonSerializer.None.class) {
                return using;
            }
        }
        final JsonRawValue jsonRawValue = this._findAnnotation(annotated, JsonRawValue.class);
        if (jsonRawValue != null && jsonRawValue.value()) {
            return new RawSerializer(annotated.getRawType());
        }
        return null;
    }
    
    @Override
    public Object findKeySerializer(final Annotated annotated) {
        final JsonSerialize jsonSerialize = this._findAnnotation(annotated, JsonSerialize.class);
        if (jsonSerialize != null) {
            final Class<? extends JsonSerializer> keyUsing = jsonSerialize.keyUsing();
            if (keyUsing != JsonSerializer.None.class) {
                return keyUsing;
            }
        }
        return null;
    }
    
    @Override
    public Object findContentSerializer(final Annotated annotated) {
        final JsonSerialize jsonSerialize = this._findAnnotation(annotated, JsonSerialize.class);
        if (jsonSerialize != null) {
            final Class<? extends JsonSerializer> contentUsing = jsonSerialize.contentUsing();
            if (contentUsing != JsonSerializer.None.class) {
                return contentUsing;
            }
        }
        return null;
    }
    
    @Override
    public Object findNullSerializer(final Annotated annotated) {
        final JsonSerialize jsonSerialize = this._findAnnotation(annotated, JsonSerialize.class);
        if (jsonSerialize != null) {
            final Class<? extends JsonSerializer> nullsUsing = jsonSerialize.nullsUsing();
            if (nullsUsing != JsonSerializer.None.class) {
                return nullsUsing;
            }
        }
        return null;
    }
    
    @Override
    public JsonInclude.Value findPropertyInclusion(final Annotated annotated) {
        final JsonInclude jsonInclude = this._findAnnotation(annotated, JsonInclude.class);
        JsonInclude.Value refinePropertyInclusion = (jsonInclude == null) ? JsonInclude.Value.empty() : JsonInclude.Value.from(jsonInclude);
        if (refinePropertyInclusion.getValueInclusion() == JsonInclude.Include.USE_DEFAULTS) {
            refinePropertyInclusion = this._refinePropertyInclusion(annotated, refinePropertyInclusion);
        }
        return refinePropertyInclusion;
    }
    
    private JsonInclude.Value _refinePropertyInclusion(final Annotated annotated, final JsonInclude.Value value) {
        final JsonSerialize jsonSerialize = this._findAnnotation(annotated, JsonSerialize.class);
        if (jsonSerialize != null) {
            switch (jsonSerialize.include()) {
                case ALWAYS: {
                    return value.withValueInclusion(JsonInclude.Include.ALWAYS);
                }
                case NON_NULL: {
                    return value.withValueInclusion(JsonInclude.Include.NON_NULL);
                }
                case NON_DEFAULT: {
                    return value.withValueInclusion(JsonInclude.Include.NON_DEFAULT);
                }
                case NON_EMPTY: {
                    return value.withValueInclusion(JsonInclude.Include.NON_EMPTY);
                }
            }
        }
        return value;
    }
    
    @Override
    public JsonSerialize.Typing findSerializationTyping(final Annotated annotated) {
        final JsonSerialize jsonSerialize = this._findAnnotation(annotated, JsonSerialize.class);
        return (jsonSerialize == null) ? null : jsonSerialize.typing();
    }
    
    @Override
    public Object findSerializationConverter(final Annotated annotated) {
        final JsonSerialize jsonSerialize = this._findAnnotation(annotated, JsonSerialize.class);
        return (jsonSerialize == null) ? null : this._classIfExplicit(jsonSerialize.converter(), Converter.None.class);
    }
    
    @Override
    public Object findSerializationContentConverter(final AnnotatedMember annotatedMember) {
        final JsonSerialize jsonSerialize = this._findAnnotation(annotatedMember, JsonSerialize.class);
        return (jsonSerialize == null) ? null : this._classIfExplicit(jsonSerialize.contentConverter(), Converter.None.class);
    }
    
    @Override
    public JavaType refineSerializationType(final MapperConfig<?> mapperConfig, final Annotated annotated, final JavaType javaType) throws JsonMappingException {
        JavaType javaType2 = javaType;
        final TypeFactory typeFactory = mapperConfig.getTypeFactory();
        final JsonSerialize jsonSerialize = this._findAnnotation(annotated, JsonSerialize.class);
        final Class<?> clazz = (jsonSerialize == null) ? null : this._classIfExplicit(jsonSerialize.as());
        if (clazz != null) {
            if (javaType2.hasRawClass(clazz)) {
                javaType2 = javaType2.withStaticTyping();
            }
            else {
                final Class<?> rawClass = javaType2.getRawClass();
                try {
                    if (clazz.isAssignableFrom(rawClass)) {
                        javaType2 = typeFactory.constructGeneralizedType(javaType2, clazz);
                    }
                    else if (rawClass.isAssignableFrom(clazz)) {
                        javaType2 = typeFactory.constructSpecializedType(javaType2, clazz);
                    }
                    else {
                        if (!this._primitiveAndWrapper(rawClass, clazz)) {
                            throw new JsonMappingException(null, String.format("Cannot refine serialization type %s into %s; types not related", javaType2, clazz.getName()));
                        }
                        javaType2 = javaType2.withStaticTyping();
                    }
                }
                catch (IllegalArgumentException ex) {
                    throw new JsonMappingException(null, String.format("Failed to widen type %s with annotation (value %s), from '%s': %s", javaType2, clazz.getName(), annotated.getName(), ex.getMessage()), ex);
                }
            }
        }
        if (javaType2.isMapLikeType()) {
            final JavaType keyType = javaType2.getKeyType();
            final Class<?> clazz2 = (jsonSerialize == null) ? null : this._classIfExplicit(jsonSerialize.keyAs());
            if (clazz2 != null) {
                JavaType javaType3;
                if (keyType.hasRawClass(clazz2)) {
                    javaType3 = keyType.withStaticTyping();
                }
                else {
                    final Class<?> rawClass2 = keyType.getRawClass();
                    try {
                        if (clazz2.isAssignableFrom(rawClass2)) {
                            javaType3 = typeFactory.constructGeneralizedType(keyType, clazz2);
                        }
                        else if (rawClass2.isAssignableFrom(clazz2)) {
                            javaType3 = typeFactory.constructSpecializedType(keyType, clazz2);
                        }
                        else {
                            if (!this._primitiveAndWrapper(rawClass2, clazz2)) {
                                throw new JsonMappingException(null, String.format("Cannot refine serialization key type %s into %s; types not related", keyType, clazz2.getName()));
                            }
                            javaType3 = keyType.withStaticTyping();
                        }
                    }
                    catch (IllegalArgumentException ex2) {
                        throw new JsonMappingException(null, String.format("Failed to widen key type of %s with concrete-type annotation (value %s), from '%s': %s", javaType2, clazz2.getName(), annotated.getName(), ex2.getMessage()), ex2);
                    }
                }
                javaType2 = ((MapLikeType)javaType2).withKeyType(javaType3);
            }
        }
        final JavaType contentType = javaType2.getContentType();
        if (contentType != null) {
            final Class<?> clazz3 = (jsonSerialize == null) ? null : this._classIfExplicit(jsonSerialize.contentAs());
            if (clazz3 != null) {
                JavaType javaType4;
                if (contentType.hasRawClass(clazz3)) {
                    javaType4 = contentType.withStaticTyping();
                }
                else {
                    final Class<?> rawClass3 = contentType.getRawClass();
                    try {
                        if (clazz3.isAssignableFrom(rawClass3)) {
                            javaType4 = typeFactory.constructGeneralizedType(contentType, clazz3);
                        }
                        else if (rawClass3.isAssignableFrom(clazz3)) {
                            javaType4 = typeFactory.constructSpecializedType(contentType, clazz3);
                        }
                        else {
                            if (!this._primitiveAndWrapper(rawClass3, clazz3)) {
                                throw new JsonMappingException(null, String.format("Cannot refine serialization content type %s into %s; types not related", contentType, clazz3.getName()));
                            }
                            javaType4 = contentType.withStaticTyping();
                        }
                    }
                    catch (IllegalArgumentException ex3) {
                        throw new JsonMappingException(null, String.format("Internal error: failed to refine value type of %s with concrete-type annotation (value %s), from '%s': %s", javaType2, clazz3.getName(), annotated.getName(), ex3.getMessage()), ex3);
                    }
                }
                javaType2 = javaType2.withContentType(javaType4);
            }
        }
        return javaType2;
    }
    
    @Deprecated
    @Override
    public Class<?> findSerializationType(final Annotated annotated) {
        return null;
    }
    
    @Deprecated
    @Override
    public Class<?> findSerializationKeyType(final Annotated annotated, final JavaType javaType) {
        return null;
    }
    
    @Deprecated
    @Override
    public Class<?> findSerializationContentType(final Annotated annotated, final JavaType javaType) {
        return null;
    }
    
    @Override
    public String[] findSerializationPropertyOrder(final AnnotatedClass annotatedClass) {
        final JsonPropertyOrder jsonPropertyOrder = this._findAnnotation(annotatedClass, JsonPropertyOrder.class);
        return (String[])((jsonPropertyOrder == null) ? null : jsonPropertyOrder.value());
    }
    
    @Override
    public Boolean findSerializationSortAlphabetically(final Annotated annotated) {
        return this._findSortAlpha(annotated);
    }
    
    private final Boolean _findSortAlpha(final Annotated annotated) {
        final JsonPropertyOrder jsonPropertyOrder = this._findAnnotation(annotated, JsonPropertyOrder.class);
        if (jsonPropertyOrder != null && jsonPropertyOrder.alphabetic()) {
            return Boolean.TRUE;
        }
        return null;
    }
    
    @Override
    public void findAndAddVirtualProperties(final MapperConfig<?> mapperConfig, final AnnotatedClass annotatedClass, final List<BeanPropertyWriter> list) {
        final JsonAppend jsonAppend = this._findAnnotation(annotatedClass, JsonAppend.class);
        if (jsonAppend == null) {
            return;
        }
        final boolean prepend = jsonAppend.prepend();
        JavaType constructType = null;
        final JsonAppend.Attr[] attrs = jsonAppend.attrs();
        for (int i = 0; i < attrs.length; ++i) {
            if (constructType == null) {
                constructType = mapperConfig.constructType(Object.class);
            }
            final BeanPropertyWriter constructVirtualProperty = this._constructVirtualProperty(attrs[i], mapperConfig, annotatedClass, constructType);
            if (prepend) {
                list.add(i, constructVirtualProperty);
            }
            else {
                list.add(constructVirtualProperty);
            }
        }
        final JsonAppend.Prop[] props = jsonAppend.props();
        for (int j = 0; j < props.length; ++j) {
            final BeanPropertyWriter constructVirtualProperty2 = this._constructVirtualProperty(props[j], mapperConfig, annotatedClass);
            if (prepend) {
                list.add(j, constructVirtualProperty2);
            }
            else {
                list.add(constructVirtualProperty2);
            }
        }
    }
    
    protected BeanPropertyWriter _constructVirtualProperty(final JsonAppend.Attr attr, final MapperConfig<?> mapperConfig, final AnnotatedClass annotatedClass, final JavaType javaType) {
        final PropertyMetadata propertyMetadata = attr.required() ? PropertyMetadata.STD_REQUIRED : PropertyMetadata.STD_OPTIONAL;
        final String value = attr.value();
        PropertyName propertyName = this._propertyName(attr.propName(), attr.propNamespace());
        if (!propertyName.hasSimpleName()) {
            propertyName = PropertyName.construct(value);
        }
        return AttributePropertyWriter.construct(value, SimpleBeanPropertyDefinition.construct(mapperConfig, new VirtualAnnotatedMember(annotatedClass, annotatedClass.getRawType(), value, javaType), propertyName, propertyMetadata, attr.include()), annotatedClass.getAnnotations(), javaType);
    }
    
    protected BeanPropertyWriter _constructVirtualProperty(final JsonAppend.Prop prop, final MapperConfig<?> mapperConfig, final AnnotatedClass annotatedClass) {
        final PropertyMetadata propertyMetadata = prop.required() ? PropertyMetadata.STD_REQUIRED : PropertyMetadata.STD_OPTIONAL;
        final PropertyName propertyName = this._propertyName(prop.name(), prop.namespace());
        final JavaType constructType = mapperConfig.constructType(prop.type());
        final SimpleBeanPropertyDefinition construct = SimpleBeanPropertyDefinition.construct(mapperConfig, new VirtualAnnotatedMember(annotatedClass, annotatedClass.getRawType(), propertyName.getSimpleName(), constructType), propertyName, propertyMetadata, prop.include());
        final Class<? extends VirtualBeanPropertyWriter> value = prop.value();
        final HandlerInstantiator handlerInstantiator = mapperConfig.getHandlerInstantiator();
        VirtualBeanPropertyWriter virtualBeanPropertyWriter = (handlerInstantiator == null) ? null : handlerInstantiator.virtualPropertyWriterInstance(mapperConfig, value);
        if (virtualBeanPropertyWriter == null) {
            virtualBeanPropertyWriter = ClassUtil.createInstance(value, mapperConfig.canOverrideAccessModifiers());
        }
        return virtualBeanPropertyWriter.withConfig(mapperConfig, annotatedClass, construct, constructType);
    }
    
    @Override
    public PropertyName findNameForSerialization(final Annotated annotated) {
        boolean b = false;
        final JsonGetter jsonGetter = this._findAnnotation(annotated, JsonGetter.class);
        if (jsonGetter != null) {
            final String value = jsonGetter.value();
            if (!value.isEmpty()) {
                return PropertyName.construct(value);
            }
            b = true;
        }
        final JsonProperty jsonProperty = this._findAnnotation(annotated, JsonProperty.class);
        if (jsonProperty != null) {
            return PropertyName.construct(jsonProperty.value());
        }
        if (b || this._hasOneOf(annotated, JacksonAnnotationIntrospector.ANNOTATIONS_TO_INFER_SER)) {
            return PropertyName.USE_DEFAULT;
        }
        return null;
    }
    
    @Override
    public Boolean hasAsValue(final Annotated annotated) {
        final JsonValue jsonValue = this._findAnnotation(annotated, JsonValue.class);
        if (jsonValue == null) {
            return null;
        }
        return jsonValue.value();
    }
    
    @Override
    public Boolean hasAnyGetter(final Annotated annotated) {
        final JsonAnyGetter jsonAnyGetter = this._findAnnotation(annotated, JsonAnyGetter.class);
        if (jsonAnyGetter == null) {
            return null;
        }
        return jsonAnyGetter.enabled();
    }
    
    @Deprecated
    @Override
    public boolean hasAnyGetterAnnotation(final AnnotatedMethod annotatedMethod) {
        return this._hasAnnotation(annotatedMethod, JsonAnyGetter.class);
    }
    
    @Deprecated
    @Override
    public boolean hasAsValueAnnotation(final AnnotatedMethod annotatedMethod) {
        final JsonValue jsonValue = this._findAnnotation(annotatedMethod, JsonValue.class);
        return jsonValue != null && jsonValue.value();
    }
    
    @Override
    public Object findDeserializer(final Annotated annotated) {
        final JsonDeserialize jsonDeserialize = this._findAnnotation(annotated, JsonDeserialize.class);
        if (jsonDeserialize != null) {
            final Class<? extends JsonDeserializer> using = jsonDeserialize.using();
            if (using != JsonDeserializer.None.class) {
                return using;
            }
        }
        return null;
    }
    
    @Override
    public Object findKeyDeserializer(final Annotated annotated) {
        final JsonDeserialize jsonDeserialize = this._findAnnotation(annotated, JsonDeserialize.class);
        if (jsonDeserialize != null) {
            final Class<? extends KeyDeserializer> keyUsing = jsonDeserialize.keyUsing();
            if (keyUsing != KeyDeserializer.None.class) {
                return keyUsing;
            }
        }
        return null;
    }
    
    @Override
    public Object findContentDeserializer(final Annotated annotated) {
        final JsonDeserialize jsonDeserialize = this._findAnnotation(annotated, JsonDeserialize.class);
        if (jsonDeserialize != null) {
            final Class<? extends JsonDeserializer> contentUsing = jsonDeserialize.contentUsing();
            if (contentUsing != JsonDeserializer.None.class) {
                return contentUsing;
            }
        }
        return null;
    }
    
    @Override
    public Object findDeserializationConverter(final Annotated annotated) {
        final JsonDeserialize jsonDeserialize = this._findAnnotation(annotated, JsonDeserialize.class);
        return (jsonDeserialize == null) ? null : this._classIfExplicit(jsonDeserialize.converter(), Converter.None.class);
    }
    
    @Override
    public Object findDeserializationContentConverter(final AnnotatedMember annotatedMember) {
        final JsonDeserialize jsonDeserialize = this._findAnnotation(annotatedMember, JsonDeserialize.class);
        return (jsonDeserialize == null) ? null : this._classIfExplicit(jsonDeserialize.contentConverter(), Converter.None.class);
    }
    
    @Override
    public JavaType refineDeserializationType(final MapperConfig<?> mapperConfig, final Annotated annotated, final JavaType javaType) throws JsonMappingException {
        JavaType javaType2 = javaType;
        final TypeFactory typeFactory = mapperConfig.getTypeFactory();
        final JsonDeserialize jsonDeserialize = this._findAnnotation(annotated, JsonDeserialize.class);
        final Class<?> clazz = (jsonDeserialize == null) ? null : this._classIfExplicit(jsonDeserialize.as());
        if (clazz != null && !javaType2.hasRawClass(clazz) && !this._primitiveAndWrapper(javaType2, clazz)) {
            try {
                javaType2 = typeFactory.constructSpecializedType(javaType2, clazz);
            }
            catch (IllegalArgumentException ex) {
                throw new JsonMappingException(null, String.format("Failed to narrow type %s with annotation (value %s), from '%s': %s", javaType2, clazz.getName(), annotated.getName(), ex.getMessage()), ex);
            }
        }
        if (javaType2.isMapLikeType()) {
            final JavaType keyType = javaType2.getKeyType();
            final Class<?> clazz2 = (jsonDeserialize == null) ? null : this._classIfExplicit(jsonDeserialize.keyAs());
            if (clazz2 != null && !this._primitiveAndWrapper(keyType, clazz2)) {
                try {
                    javaType2 = ((MapLikeType)javaType2).withKeyType(typeFactory.constructSpecializedType(keyType, clazz2));
                }
                catch (IllegalArgumentException ex2) {
                    throw new JsonMappingException(null, String.format("Failed to narrow key type of %s with concrete-type annotation (value %s), from '%s': %s", javaType2, clazz2.getName(), annotated.getName(), ex2.getMessage()), ex2);
                }
            }
        }
        final JavaType contentType = javaType2.getContentType();
        if (contentType != null) {
            final Class<?> clazz3 = (jsonDeserialize == null) ? null : this._classIfExplicit(jsonDeserialize.contentAs());
            if (clazz3 != null && !this._primitiveAndWrapper(contentType, clazz3)) {
                try {
                    javaType2 = javaType2.withContentType(typeFactory.constructSpecializedType(contentType, clazz3));
                }
                catch (IllegalArgumentException ex3) {
                    throw new JsonMappingException(null, String.format("Failed to narrow value type of %s with concrete-type annotation (value %s), from '%s': %s", javaType2, clazz3.getName(), annotated.getName(), ex3.getMessage()), ex3);
                }
            }
        }
        return javaType2;
    }
    
    @Deprecated
    @Override
    public Class<?> findDeserializationContentType(final Annotated annotated, final JavaType javaType) {
        return null;
    }
    
    @Deprecated
    @Override
    public Class<?> findDeserializationType(final Annotated annotated, final JavaType javaType) {
        return null;
    }
    
    @Deprecated
    @Override
    public Class<?> findDeserializationKeyType(final Annotated annotated, final JavaType javaType) {
        return null;
    }
    
    @Override
    public Object findValueInstantiator(final AnnotatedClass annotatedClass) {
        final JsonValueInstantiator jsonValueInstantiator = this._findAnnotation(annotatedClass, JsonValueInstantiator.class);
        return (jsonValueInstantiator == null) ? null : jsonValueInstantiator.value();
    }
    
    @Override
    public Class<?> findPOJOBuilder(final AnnotatedClass annotatedClass) {
        final JsonDeserialize jsonDeserialize = this._findAnnotation(annotatedClass, JsonDeserialize.class);
        return (jsonDeserialize == null) ? null : this._classIfExplicit(jsonDeserialize.builder());
    }
    
    @Override
    public JsonPOJOBuilder.Value findPOJOBuilderConfig(final AnnotatedClass annotatedClass) {
        final JsonPOJOBuilder jsonPOJOBuilder = this._findAnnotation(annotatedClass, JsonPOJOBuilder.class);
        return (jsonPOJOBuilder == null) ? null : new JsonPOJOBuilder.Value(jsonPOJOBuilder);
    }
    
    @Override
    public PropertyName findNameForDeserialization(final Annotated annotated) {
        boolean b = false;
        final JsonSetter jsonSetter = this._findAnnotation(annotated, JsonSetter.class);
        if (jsonSetter != null) {
            final String value = jsonSetter.value();
            if (!value.isEmpty()) {
                return PropertyName.construct(value);
            }
            b = true;
        }
        final JsonProperty jsonProperty = this._findAnnotation(annotated, JsonProperty.class);
        if (jsonProperty != null) {
            return PropertyName.construct(jsonProperty.value());
        }
        if (b || this._hasOneOf(annotated, JacksonAnnotationIntrospector.ANNOTATIONS_TO_INFER_DESER)) {
            return PropertyName.USE_DEFAULT;
        }
        return null;
    }
    
    @Override
    public Boolean hasAnySetter(final Annotated annotated) {
        final JsonAnySetter jsonAnySetter = this._findAnnotation(annotated, JsonAnySetter.class);
        return (jsonAnySetter == null) ? null : Boolean.valueOf(jsonAnySetter.enabled());
    }
    
    @Override
    public JsonSetter.Value findSetterInfo(final Annotated annotated) {
        return JsonSetter.Value.from(this._findAnnotation(annotated, JsonSetter.class));
    }
    
    @Override
    public Boolean findMergeInfo(final Annotated annotated) {
        final JsonMerge jsonMerge = this._findAnnotation(annotated, JsonMerge.class);
        return (jsonMerge == null) ? null : jsonMerge.value().asBoolean();
    }
    
    @Deprecated
    @Override
    public boolean hasAnySetterAnnotation(final AnnotatedMethod annotatedMethod) {
        return this._hasAnnotation(annotatedMethod, JsonAnySetter.class);
    }
    
    @Deprecated
    @Override
    public boolean hasCreatorAnnotation(final Annotated annotated) {
        final JsonCreator jsonCreator = this._findAnnotation(annotated, JsonCreator.class);
        if (jsonCreator != null) {
            return jsonCreator.mode() != JsonCreator.Mode.DISABLED;
        }
        if (this._cfgConstructorPropertiesImpliesCreator && annotated instanceof AnnotatedConstructor && JacksonAnnotationIntrospector._java7Helper != null) {
            final Boolean hasCreatorAnnotation = JacksonAnnotationIntrospector._java7Helper.hasCreatorAnnotation(annotated);
            if (hasCreatorAnnotation != null) {
                return hasCreatorAnnotation;
            }
        }
        return false;
    }
    
    @Deprecated
    @Override
    public JsonCreator.Mode findCreatorBinding(final Annotated annotated) {
        final JsonCreator jsonCreator = this._findAnnotation(annotated, JsonCreator.class);
        return (jsonCreator == null) ? null : jsonCreator.mode();
    }
    
    @Override
    public JsonCreator.Mode findCreatorAnnotation(final MapperConfig<?> mapperConfig, final Annotated annotated) {
        final JsonCreator jsonCreator = this._findAnnotation(annotated, JsonCreator.class);
        if (jsonCreator != null) {
            return jsonCreator.mode();
        }
        if (this._cfgConstructorPropertiesImpliesCreator && mapperConfig.isEnabled(MapperFeature.INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES) && annotated instanceof AnnotatedConstructor && JacksonAnnotationIntrospector._java7Helper != null) {
            final Boolean hasCreatorAnnotation = JacksonAnnotationIntrospector._java7Helper.hasCreatorAnnotation(annotated);
            if (hasCreatorAnnotation != null && hasCreatorAnnotation) {
                return JsonCreator.Mode.PROPERTIES;
            }
        }
        return null;
    }
    
    protected boolean _isIgnorable(final Annotated annotated) {
        final JsonIgnore jsonIgnore = this._findAnnotation(annotated, JsonIgnore.class);
        if (jsonIgnore != null) {
            return jsonIgnore.value();
        }
        if (JacksonAnnotationIntrospector._java7Helper != null) {
            final Boolean transient1 = JacksonAnnotationIntrospector._java7Helper.findTransient(annotated);
            if (transient1 != null) {
                return transient1;
            }
        }
        return false;
    }
    
    protected Class<?> _classIfExplicit(final Class<?> clazz) {
        if (clazz == null || ClassUtil.isBogusClass(clazz)) {
            return null;
        }
        return clazz;
    }
    
    protected Class<?> _classIfExplicit(final Class<?> clazz, final Class<?> clazz2) {
        final Class<?> classIfExplicit = this._classIfExplicit(clazz);
        return (classIfExplicit == null || classIfExplicit == clazz2) ? null : classIfExplicit;
    }
    
    protected PropertyName _propertyName(final String s, final String s2) {
        if (s.isEmpty()) {
            return PropertyName.USE_DEFAULT;
        }
        if (s2 == null || s2.isEmpty()) {
            return PropertyName.construct(s);
        }
        return PropertyName.construct(s, s2);
    }
    
    protected PropertyName _findConstructorName(final Annotated annotated) {
        if (annotated instanceof AnnotatedParameter) {
            final AnnotatedParameter annotatedParameter = (AnnotatedParameter)annotated;
            if (annotatedParameter.getOwner() != null && JacksonAnnotationIntrospector._java7Helper != null) {
                final PropertyName constructorName = JacksonAnnotationIntrospector._java7Helper.findConstructorName(annotatedParameter);
                if (constructorName != null) {
                    return constructorName;
                }
            }
        }
        return null;
    }
    
    protected TypeResolverBuilder<?> _findTypeResolver(final MapperConfig<?> mapperConfig, final Annotated annotated, final JavaType javaType) {
        final JsonTypeInfo jsonTypeInfo = this._findAnnotation(annotated, JsonTypeInfo.class);
        final JsonTypeResolver jsonTypeResolver = this._findAnnotation(annotated, JsonTypeResolver.class);
        TypeResolverBuilder typeResolverBuilder;
        if (jsonTypeResolver != null) {
            if (jsonTypeInfo == null) {
                return null;
            }
            typeResolverBuilder = mapperConfig.typeResolverBuilderInstance(annotated, jsonTypeResolver.value());
        }
        else {
            if (jsonTypeInfo == null) {
                return null;
            }
            if (jsonTypeInfo.use() == JsonTypeInfo.Id.NONE) {
                return this._constructNoTypeResolverBuilder();
            }
            typeResolverBuilder = this._constructStdTypeResolverBuilder();
        }
        final JsonTypeIdResolver jsonTypeIdResolver = this._findAnnotation(annotated, JsonTypeIdResolver.class);
        final TypeIdResolver typeIdResolver = (jsonTypeIdResolver == null) ? null : mapperConfig.typeIdResolverInstance(annotated, jsonTypeIdResolver.value());
        if (typeIdResolver != null) {
            typeIdResolver.init(javaType);
        }
        final TypeResolverBuilder<TypeResolverBuilder<TypeResolverBuilder<TypeResolverBuilder<?>>>> init = typeResolverBuilder.init(jsonTypeInfo.use(), typeIdResolver);
        JsonTypeInfo.As as = jsonTypeInfo.include();
        if (as == JsonTypeInfo.As.EXTERNAL_PROPERTY && annotated instanceof AnnotatedClass) {
            as = JsonTypeInfo.As.PROPERTY;
        }
        Object o = init.inclusion(as).typeProperty(jsonTypeInfo.property());
        final Class<?> defaultImpl = jsonTypeInfo.defaultImpl();
        if (defaultImpl != JsonTypeInfo.None.class && !defaultImpl.isAnnotation()) {
            o = ((TypeResolverBuilder<?>)o).defaultImpl(defaultImpl);
        }
        return (TypeResolverBuilder<?>)((TypeResolverBuilder<?>)o).typeIdVisibility(jsonTypeInfo.visible());
    }
    
    protected StdTypeResolverBuilder _constructStdTypeResolverBuilder() {
        return new StdTypeResolverBuilder();
    }
    
    protected StdTypeResolverBuilder _constructNoTypeResolverBuilder() {
        return StdTypeResolverBuilder.noTypeInfoBuilder();
    }
    
    private boolean _primitiveAndWrapper(final Class<?> clazz, final Class<?> clazz2) {
        if (clazz.isPrimitive()) {
            return clazz == ClassUtil.primitiveType(clazz2);
        }
        return clazz2.isPrimitive() && clazz2 == ClassUtil.primitiveType(clazz);
    }
    
    private boolean _primitiveAndWrapper(final JavaType javaType, final Class<?> clazz) {
        if (javaType.isPrimitive()) {
            return javaType.hasRawClass(ClassUtil.primitiveType(clazz));
        }
        return clazz.isPrimitive() && clazz == ClassUtil.primitiveType(javaType.getRawClass());
    }
    
    static {
        ANNOTATIONS_TO_INFER_SER = new Class[] { JsonSerialize.class, JsonView.class, JsonFormat.class, JsonTypeInfo.class, JsonRawValue.class, JsonUnwrapped.class, JsonBackReference.class, JsonManagedReference.class };
        ANNOTATIONS_TO_INFER_DESER = new Class[] { JsonDeserialize.class, JsonView.class, JsonFormat.class, JsonTypeInfo.class, JsonUnwrapped.class, JsonBackReference.class, JsonManagedReference.class, JsonMerge.class };
        Java7Support instance = null;
        try {
            instance = Java7Support.instance();
        }
        catch (Throwable t) {}
        _java7Helper = instance;
    }
}
