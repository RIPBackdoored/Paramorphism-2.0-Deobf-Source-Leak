package com.fasterxml.jackson.databind.deser;

import java.io.*;
import com.fasterxml.jackson.databind.exc.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.cfg.*;
import com.fasterxml.jackson.databind.deser.std.*;
import com.fasterxml.jackson.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.impl.*;
import com.fasterxml.jackson.databind.jsontype.impl.*;

public class BeanDeserializerFactory extends BasicDeserializerFactory implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Class<?>[] INIT_CAUSE_PARAMS;
    public static final BeanDeserializerFactory instance;
    
    public BeanDeserializerFactory(final DeserializerFactoryConfig deserializerFactoryConfig) {
        super(deserializerFactoryConfig);
    }
    
    public DeserializerFactory withConfig(final DeserializerFactoryConfig deserializerFactoryConfig) {
        if (this._factoryConfig == deserializerFactoryConfig) {
            return this;
        }
        ClassUtil.verifyMustOverride(BeanDeserializerFactory.class, this, "withConfig");
        return new BeanDeserializerFactory(deserializerFactoryConfig);
    }
    
    @Override
    public JsonDeserializer<Object> createBeanDeserializer(final DeserializationContext deserializationContext, final JavaType javaType, BeanDescription introspect) throws JsonMappingException {
        final DeserializationConfig config = deserializationContext.getConfig();
        final JsonDeserializer<Object> findCustomBeanDeserializer = this._findCustomBeanDeserializer(javaType, config, introspect);
        if (findCustomBeanDeserializer != null) {
            return findCustomBeanDeserializer;
        }
        if (javaType.isThrowable()) {
            return this.buildThrowableDeserializer(deserializationContext, javaType, introspect);
        }
        if (javaType.isAbstract() && !javaType.isPrimitive() && !javaType.isEnumType()) {
            final JavaType materializeAbstractType = this.materializeAbstractType(deserializationContext, javaType, introspect);
            if (materializeAbstractType != null) {
                introspect = config.introspect(materializeAbstractType);
                return this.buildBeanDeserializer(deserializationContext, materializeAbstractType, introspect);
            }
        }
        final JsonDeserializer<?> stdDeserializer = this.findStdDeserializer(deserializationContext, javaType, introspect);
        if (stdDeserializer != null) {
            return (JsonDeserializer<Object>)stdDeserializer;
        }
        if (!this.isPotentialBeanType(javaType.getRawClass())) {
            return null;
        }
        this._validateSubType(deserializationContext, javaType, introspect);
        return this.buildBeanDeserializer(deserializationContext, javaType, introspect);
    }
    
    @Override
    public JsonDeserializer<Object> createBuilderBasedDeserializer(final DeserializationContext deserializationContext, final JavaType javaType, final BeanDescription beanDescription, final Class<?> clazz) throws JsonMappingException {
        return this.buildBuilderBasedDeserializer(deserializationContext, javaType, deserializationContext.getConfig().introspectForBuilder(deserializationContext.constructType(clazz)));
    }
    
    protected JsonDeserializer<?> findStdDeserializer(final DeserializationContext deserializationContext, final JavaType javaType, final BeanDescription beanDescription) throws JsonMappingException {
        JsonDeserializer<?> jsonDeserializer = this.findDefaultDeserializer(deserializationContext, javaType, beanDescription);
        if (jsonDeserializer != null && this._factoryConfig.hasDeserializerModifiers()) {
            final Iterator<BeanDeserializerModifier> iterator = this._factoryConfig.deserializerModifiers().iterator();
            while (iterator.hasNext()) {
                jsonDeserializer = iterator.next().modifyDeserializer(deserializationContext.getConfig(), beanDescription, jsonDeserializer);
            }
        }
        return jsonDeserializer;
    }
    
    protected JavaType materializeAbstractType(final DeserializationContext deserializationContext, final JavaType javaType, final BeanDescription beanDescription) throws JsonMappingException {
        final Iterator<AbstractTypeResolver> iterator = this._factoryConfig.abstractTypeResolvers().iterator();
        while (iterator.hasNext()) {
            final JavaType resolveAbstractType = iterator.next().resolveAbstractType(deserializationContext.getConfig(), beanDescription);
            if (resolveAbstractType != null) {
                return resolveAbstractType;
            }
        }
        return null;
    }
    
    public JsonDeserializer<Object> buildBeanDeserializer(final DeserializationContext deserializationContext, final JavaType javaType, final BeanDescription beanDescription) throws JsonMappingException {
        ValueInstantiator valueInstantiator;
        try {
            valueInstantiator = this.findValueInstantiator(deserializationContext, beanDescription);
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            return new ErrorThrowingDeserializer(noClassDefFoundError);
        }
        catch (IllegalArgumentException ex) {
            throw InvalidDefinitionException.from(deserializationContext.getParser(), ex.getMessage(), beanDescription, null);
        }
        BeanDeserializerBuilder beanDeserializerBuilder = this.constructBeanDeserializerBuilder(deserializationContext, beanDescription);
        beanDeserializerBuilder.setValueInstantiator(valueInstantiator);
        this.addBeanProps(deserializationContext, beanDescription, beanDeserializerBuilder);
        this.addObjectIdReader(deserializationContext, beanDescription, beanDeserializerBuilder);
        this.addBackReferenceProperties(deserializationContext, beanDescription, beanDeserializerBuilder);
        this.addInjectables(deserializationContext, beanDescription, beanDeserializerBuilder);
        final DeserializationConfig config = deserializationContext.getConfig();
        if (this._factoryConfig.hasDeserializerModifiers()) {
            final Iterator<BeanDeserializerModifier> iterator = this._factoryConfig.deserializerModifiers().iterator();
            while (iterator.hasNext()) {
                beanDeserializerBuilder = iterator.next().updateBuilder(config, beanDescription, beanDeserializerBuilder);
            }
        }
        JsonDeserializer<?> jsonDeserializer;
        if (javaType.isAbstract() && !valueInstantiator.canInstantiate()) {
            jsonDeserializer = beanDeserializerBuilder.buildAbstract();
        }
        else {
            jsonDeserializer = beanDeserializerBuilder.build();
        }
        if (this._factoryConfig.hasDeserializerModifiers()) {
            final Iterator<BeanDeserializerModifier> iterator2 = this._factoryConfig.deserializerModifiers().iterator();
            while (iterator2.hasNext()) {
                jsonDeserializer = iterator2.next().modifyDeserializer(config, beanDescription, jsonDeserializer);
            }
        }
        return (JsonDeserializer<Object>)jsonDeserializer;
    }
    
    protected JsonDeserializer<Object> buildBuilderBasedDeserializer(final DeserializationContext deserializationContext, final JavaType javaType, final BeanDescription beanDescription) throws JsonMappingException {
        ValueInstantiator valueInstantiator;
        try {
            valueInstantiator = this.findValueInstantiator(deserializationContext, beanDescription);
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            return new ErrorThrowingDeserializer(noClassDefFoundError);
        }
        catch (IllegalArgumentException ex) {
            throw InvalidDefinitionException.from(deserializationContext.getParser(), ex.getMessage(), beanDescription, null);
        }
        final DeserializationConfig config = deserializationContext.getConfig();
        BeanDeserializerBuilder beanDeserializerBuilder = this.constructBeanDeserializerBuilder(deserializationContext, beanDescription);
        beanDeserializerBuilder.setValueInstantiator(valueInstantiator);
        this.addBeanProps(deserializationContext, beanDescription, beanDeserializerBuilder);
        this.addObjectIdReader(deserializationContext, beanDescription, beanDeserializerBuilder);
        this.addBackReferenceProperties(deserializationContext, beanDescription, beanDeserializerBuilder);
        this.addInjectables(deserializationContext, beanDescription, beanDeserializerBuilder);
        final JsonPOJOBuilder.Value pojoBuilderConfig = beanDescription.findPOJOBuilderConfig();
        final String s = (pojoBuilderConfig == null) ? "build" : pojoBuilderConfig.buildMethodName;
        final AnnotatedMethod method = beanDescription.findMethod(s, null);
        if (method != null && config.canOverrideAccessModifiers()) {
            ClassUtil.checkAndFixAccess(method.getMember(), config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
        }
        beanDeserializerBuilder.setPOJOBuilder(method, pojoBuilderConfig);
        if (this._factoryConfig.hasDeserializerModifiers()) {
            final Iterator<BeanDeserializerModifier> iterator = this._factoryConfig.deserializerModifiers().iterator();
            while (iterator.hasNext()) {
                beanDeserializerBuilder = iterator.next().updateBuilder(config, beanDescription, beanDeserializerBuilder);
            }
        }
        JsonDeserializer<?> jsonDeserializer = beanDeserializerBuilder.buildBuilderBased(javaType, s);
        if (this._factoryConfig.hasDeserializerModifiers()) {
            final Iterator<BeanDeserializerModifier> iterator2 = this._factoryConfig.deserializerModifiers().iterator();
            while (iterator2.hasNext()) {
                jsonDeserializer = iterator2.next().modifyDeserializer(config, beanDescription, jsonDeserializer);
            }
        }
        return (JsonDeserializer<Object>)jsonDeserializer;
    }
    
    protected void addObjectIdReader(final DeserializationContext deserializationContext, final BeanDescription beanDescription, final BeanDeserializerBuilder beanDeserializerBuilder) throws JsonMappingException {
        final ObjectIdInfo objectIdInfo = beanDescription.getObjectIdInfo();
        if (objectIdInfo == null) {
            return;
        }
        final Class<? extends ObjectIdGenerator<?>> generatorType = objectIdInfo.getGeneratorType();
        final ObjectIdResolver objectIdResolverInstance = deserializationContext.objectIdResolverInstance(beanDescription.getClassInfo(), objectIdInfo);
        SettableBeanProperty property;
        JavaType type;
        ObjectIdGenerator<?> objectIdGeneratorInstance;
        if (generatorType == ObjectIdGenerators.PropertyGenerator.class) {
            final PropertyName propertyName = objectIdInfo.getPropertyName();
            property = beanDeserializerBuilder.findProperty(propertyName);
            if (property == null) {
                throw new IllegalArgumentException("Invalid Object Id definition for " + beanDescription.getBeanClass().getName() + ": cannot find property with name '" + propertyName + "'");
            }
            type = property.getType();
            objectIdGeneratorInstance = new PropertyBasedObjectIdGenerator(objectIdInfo.getScope());
        }
        else {
            type = deserializationContext.getTypeFactory().findTypeParameters(deserializationContext.constructType(generatorType), ObjectIdGenerator.class)[0];
            property = null;
            objectIdGeneratorInstance = deserializationContext.objectIdGeneratorInstance(beanDescription.getClassInfo(), objectIdInfo);
        }
        beanDeserializerBuilder.setObjectIdReader(ObjectIdReader.construct(type, objectIdInfo.getPropertyName(), objectIdGeneratorInstance, deserializationContext.findRootValueDeserializer(type), property, objectIdResolverInstance));
    }
    
    public JsonDeserializer<Object> buildThrowableDeserializer(final DeserializationContext deserializationContext, final JavaType javaType, final BeanDescription beanDescription) throws JsonMappingException {
        final DeserializationConfig config = deserializationContext.getConfig();
        BeanDeserializerBuilder beanDeserializerBuilder = this.constructBeanDeserializerBuilder(deserializationContext, beanDescription);
        beanDeserializerBuilder.setValueInstantiator(this.findValueInstantiator(deserializationContext, beanDescription));
        this.addBeanProps(deserializationContext, beanDescription, beanDeserializerBuilder);
        final AnnotatedMethod method = beanDescription.findMethod("initCause", BeanDeserializerFactory.INIT_CAUSE_PARAMS);
        if (method != null) {
            final SettableBeanProperty constructSettableProperty = this.constructSettableProperty(deserializationContext, beanDescription, SimpleBeanPropertyDefinition.construct(deserializationContext.getConfig(), method, new PropertyName("cause")), method.getParameterType(0));
            if (constructSettableProperty != null) {
                beanDeserializerBuilder.addOrReplaceProperty(constructSettableProperty, true);
            }
        }
        beanDeserializerBuilder.addIgnorable("localizedMessage");
        beanDeserializerBuilder.addIgnorable("suppressed");
        if (this._factoryConfig.hasDeserializerModifiers()) {
            final Iterator<BeanDeserializerModifier> iterator = this._factoryConfig.deserializerModifiers().iterator();
            while (iterator.hasNext()) {
                beanDeserializerBuilder = iterator.next().updateBuilder(config, beanDescription, beanDeserializerBuilder);
            }
        }
        JsonDeserializer<?> jsonDeserializer = beanDeserializerBuilder.build();
        if (jsonDeserializer instanceof BeanDeserializer) {
            jsonDeserializer = new ThrowableDeserializer((BeanDeserializer)jsonDeserializer);
        }
        if (this._factoryConfig.hasDeserializerModifiers()) {
            final Iterator<BeanDeserializerModifier> iterator2 = this._factoryConfig.deserializerModifiers().iterator();
            while (iterator2.hasNext()) {
                jsonDeserializer = iterator2.next().modifyDeserializer(config, beanDescription, jsonDeserializer);
            }
        }
        return (JsonDeserializer<Object>)jsonDeserializer;
    }
    
    protected BeanDeserializerBuilder constructBeanDeserializerBuilder(final DeserializationContext deserializationContext, final BeanDescription beanDescription) {
        return new BeanDeserializerBuilder(beanDescription, deserializationContext);
    }
    
    protected void addBeanProps(final DeserializationContext deserializationContext, final BeanDescription beanDescription, final BeanDeserializerBuilder beanDeserializerBuilder) throws JsonMappingException {
        final SettableBeanProperty[] array = (SettableBeanProperty[])(beanDescription.getType().isAbstract() ? null : beanDeserializerBuilder.getValueInstantiator().getFromObjectArguments(deserializationContext.getConfig()));
        final boolean b = array != null;
        final JsonIgnoreProperties.Value defaultPropertyIgnorals = deserializationContext.getConfig().getDefaultPropertyIgnorals(beanDescription.getBeanClass(), beanDescription.getClassInfo());
        Set<String> set;
        if (defaultPropertyIgnorals != null) {
            beanDeserializerBuilder.setIgnoreUnknownProperties(defaultPropertyIgnorals.getIgnoreUnknown());
            set = defaultPropertyIgnorals.findIgnoredForDeserialization();
            final Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                beanDeserializerBuilder.addIgnorable(iterator.next());
            }
        }
        else {
            set = Collections.emptySet();
        }
        final AnnotatedMember anySetterAccessor = beanDescription.findAnySetterAccessor();
        if (anySetterAccessor != null) {
            beanDeserializerBuilder.setAnySetter(this.constructAnySetter(deserializationContext, beanDescription, anySetterAccessor));
        }
        else {
            final Set<String> ignoredPropertyNames = beanDescription.getIgnoredPropertyNames();
            if (ignoredPropertyNames != null) {
                final Iterator<Object> iterator2 = ignoredPropertyNames.iterator();
                while (iterator2.hasNext()) {
                    beanDeserializerBuilder.addIgnorable(iterator2.next());
                }
            }
        }
        final boolean b2 = deserializationContext.isEnabled(MapperFeature.USE_GETTERS_AS_SETTERS) && deserializationContext.isEnabled(MapperFeature.AUTO_DETECT_GETTERS);
        List<BeanPropertyDefinition> list = this.filterBeanProps(deserializationContext, beanDescription, beanDeserializerBuilder, beanDescription.findProperties(), set);
        if (this._factoryConfig.hasDeserializerModifiers()) {
            final Iterator<BeanDeserializerModifier> iterator3 = this._factoryConfig.deserializerModifiers().iterator();
            while (iterator3.hasNext()) {
                list = iterator3.next().updateProperties(deserializationContext.getConfig(), beanDescription, list);
            }
        }
        for (final BeanPropertyDefinition beanPropertyDefinition : list) {
            SettableBeanProperty fallbackSetter = null;
            if (beanPropertyDefinition.hasSetter()) {
                fallbackSetter = this.constructSettableProperty(deserializationContext, beanDescription, beanPropertyDefinition, beanPropertyDefinition.getSetter().getParameterType(0));
            }
            else if (beanPropertyDefinition.hasField()) {
                fallbackSetter = this.constructSettableProperty(deserializationContext, beanDescription, beanPropertyDefinition, beanPropertyDefinition.getField().getType());
            }
            else {
                final AnnotatedMethod getter = beanPropertyDefinition.getGetter();
                if (getter != null) {
                    if (b2 && this._isSetterlessType(getter.getRawType())) {
                        if (!beanDeserializerBuilder.hasIgnorable(beanPropertyDefinition.getName())) {
                            fallbackSetter = this.constructSetterlessProperty(deserializationContext, beanDescription, beanPropertyDefinition);
                        }
                    }
                    else if (!beanPropertyDefinition.hasConstructorParameter() && beanPropertyDefinition.getMetadata().getMergeInfo() != null) {
                        fallbackSetter = this.constructSetterlessProperty(deserializationContext, beanDescription, beanPropertyDefinition);
                    }
                }
            }
            if (b && beanPropertyDefinition.hasConstructorParameter()) {
                final String name = beanPropertyDefinition.getName();
                CreatorProperty creatorProperty = null;
                if (array != null) {
                    for (final SettableBeanProperty settableBeanProperty : array) {
                        if (name.equals(settableBeanProperty.getName()) && settableBeanProperty instanceof CreatorProperty) {
                            creatorProperty = (CreatorProperty)settableBeanProperty;
                            break;
                        }
                    }
                }
                if (creatorProperty == null) {
                    final ArrayList<String> list2 = new ArrayList<String>();
                    final SettableBeanProperty[] array3 = array;
                    for (int length2 = array3.length, j = 0; j < length2; ++j) {
                        list2.add(array3[j].getName());
                    }
                    deserializationContext.reportBadPropertyDefinition(beanDescription, beanPropertyDefinition, "Could not find creator property with name '%s' (known Creator properties: %s)", name, list2);
                }
                else {
                    if (fallbackSetter != null) {
                        creatorProperty.setFallbackSetter(fallbackSetter);
                    }
                    Class<?>[] views = beanPropertyDefinition.findViews();
                    if (views == null) {
                        views = beanDescription.findDefaultViews();
                    }
                    creatorProperty.setViews(views);
                    beanDeserializerBuilder.addCreatorProperty(creatorProperty);
                }
            }
            else {
                if (fallbackSetter == null) {
                    continue;
                }
                Class<?>[] views2 = beanPropertyDefinition.findViews();
                if (views2 == null) {
                    views2 = beanDescription.findDefaultViews();
                }
                fallbackSetter.setViews(views2);
                beanDeserializerBuilder.addProperty(fallbackSetter);
            }
        }
    }
    
    private boolean _isSetterlessType(final Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz);
    }
    
    protected List<BeanPropertyDefinition> filterBeanProps(final DeserializationContext deserializationContext, final BeanDescription beanDescription, final BeanDeserializerBuilder beanDeserializerBuilder, final List<BeanPropertyDefinition> list, final Set<String> set) throws JsonMappingException {
        final ArrayList<BeanPropertyDefinition> list2 = new ArrayList<BeanPropertyDefinition>(Math.max(4, list.size()));
        final HashMap<Class<?>, Boolean> hashMap = new HashMap<Class<?>, Boolean>();
        for (final BeanPropertyDefinition beanPropertyDefinition : list) {
            final String name = beanPropertyDefinition.getName();
            if (set.contains(name)) {
                continue;
            }
            if (!beanPropertyDefinition.hasConstructorParameter()) {
                final Class<?> rawPrimaryType = beanPropertyDefinition.getRawPrimaryType();
                if (rawPrimaryType != null && this.isIgnorableType(deserializationContext.getConfig(), beanPropertyDefinition, rawPrimaryType, hashMap)) {
                    beanDeserializerBuilder.addIgnorable(name);
                    continue;
                }
            }
            list2.add(beanPropertyDefinition);
        }
        return list2;
    }
    
    protected void addBackReferenceProperties(final DeserializationContext deserializationContext, final BeanDescription beanDescription, final BeanDeserializerBuilder beanDeserializerBuilder) throws JsonMappingException {
        final List<BeanPropertyDefinition> backReferences = beanDescription.findBackReferences();
        if (backReferences != null) {
            for (final BeanPropertyDefinition beanPropertyDefinition : backReferences) {
                beanDeserializerBuilder.addBackReferenceProperty(beanPropertyDefinition.findReferenceName(), this.constructSettableProperty(deserializationContext, beanDescription, beanPropertyDefinition, beanPropertyDefinition.getPrimaryType()));
            }
        }
    }
    
    @Deprecated
    protected void addReferenceProperties(final DeserializationContext deserializationContext, final BeanDescription beanDescription, final BeanDeserializerBuilder beanDeserializerBuilder) throws JsonMappingException {
        this.addBackReferenceProperties(deserializationContext, beanDescription, beanDeserializerBuilder);
    }
    
    protected void addInjectables(final DeserializationContext deserializationContext, final BeanDescription beanDescription, final BeanDeserializerBuilder beanDeserializerBuilder) throws JsonMappingException {
        final Map<Object, AnnotatedMember> injectables = beanDescription.findInjectables();
        if (injectables != null) {
            for (final Map.Entry<K, AnnotatedMember> entry : injectables.entrySet()) {
                final AnnotatedMember annotatedMember = entry.getValue();
                beanDeserializerBuilder.addInjectable(PropertyName.construct(annotatedMember.getName()), annotatedMember.getType(), beanDescription.getClassAnnotations(), annotatedMember, entry.getKey());
            }
        }
    }
    
    protected SettableAnyProperty constructAnySetter(final DeserializationContext deserializationContext, final BeanDescription beanDescription, final AnnotatedMember annotatedMember) throws JsonMappingException {
        JavaType javaType;
        JavaType javaType2;
        BeanProperty.Std std;
        if (annotatedMember instanceof AnnotatedMethod) {
            final AnnotatedMethod annotatedMethod = (AnnotatedMethod)annotatedMember;
            javaType = annotatedMethod.getParameterType(0);
            javaType2 = this.resolveMemberAndTypeAnnotations(deserializationContext, annotatedMember, annotatedMethod.getParameterType(1));
            std = new BeanProperty.Std(PropertyName.construct(annotatedMember.getName()), javaType2, null, annotatedMember, PropertyMetadata.STD_OPTIONAL);
        }
        else {
            if (!(annotatedMember instanceof AnnotatedField)) {
                return deserializationContext.reportBadDefinition(beanDescription.getType(), String.format("Unrecognized mutator type for any setter: %s", annotatedMember.getClass()));
            }
            final JavaType resolveMemberAndTypeAnnotations = this.resolveMemberAndTypeAnnotations(deserializationContext, annotatedMember, ((AnnotatedField)annotatedMember).getType());
            javaType = resolveMemberAndTypeAnnotations.getKeyType();
            javaType2 = resolveMemberAndTypeAnnotations.getContentType();
            std = new BeanProperty.Std(PropertyName.construct(annotatedMember.getName()), resolveMemberAndTypeAnnotations, null, annotatedMember, PropertyMetadata.STD_OPTIONAL);
        }
        KeyDeserializer keyDeserializer = this.findKeyDeserializerFromAnnotation(deserializationContext, annotatedMember);
        if (keyDeserializer == null) {
            keyDeserializer = javaType.getValueHandler();
        }
        if (keyDeserializer == null) {
            keyDeserializer = deserializationContext.findKeyDeserializer(javaType, std);
        }
        else if (keyDeserializer instanceof ContextualKeyDeserializer) {
            keyDeserializer = ((ContextualKeyDeserializer)keyDeserializer).createContextual(deserializationContext, std);
        }
        JsonDeserializer<?> jsonDeserializer = this.findContentDeserializerFromAnnotation(deserializationContext, annotatedMember);
        if (jsonDeserializer == null) {
            jsonDeserializer = javaType2.getValueHandler();
        }
        if (jsonDeserializer != null) {
            jsonDeserializer = deserializationContext.handlePrimaryContextualization(jsonDeserializer, std, javaType2);
        }
        return new SettableAnyProperty(std, annotatedMember, javaType2, keyDeserializer, (JsonDeserializer<Object>)jsonDeserializer, javaType2.getTypeHandler());
    }
    
    protected SettableBeanProperty constructSettableProperty(final DeserializationContext deserializationContext, final BeanDescription beanDescription, final BeanPropertyDefinition beanPropertyDefinition, final JavaType javaType) throws JsonMappingException {
        final AnnotatedMember nonConstructorMutator = beanPropertyDefinition.getNonConstructorMutator();
        if (nonConstructorMutator == null) {
            deserializationContext.reportBadPropertyDefinition(beanDescription, beanPropertyDefinition, "No non-constructor mutator available", new Object[0]);
        }
        final JavaType resolveMemberAndTypeAnnotations = this.resolveMemberAndTypeAnnotations(deserializationContext, nonConstructorMutator, javaType);
        final TypeDeserializer typeDeserializer = resolveMemberAndTypeAnnotations.getTypeHandler();
        SettableBeanProperty withValueDeserializer;
        if (nonConstructorMutator instanceof AnnotatedMethod) {
            withValueDeserializer = new MethodProperty(beanPropertyDefinition, resolveMemberAndTypeAnnotations, typeDeserializer, beanDescription.getClassAnnotations(), (AnnotatedMethod)nonConstructorMutator);
        }
        else {
            withValueDeserializer = new FieldProperty(beanPropertyDefinition, resolveMemberAndTypeAnnotations, typeDeserializer, beanDescription.getClassAnnotations(), (AnnotatedField)nonConstructorMutator);
        }
        JsonDeserializer<?> deserializerFromAnnotation = this.findDeserializerFromAnnotation(deserializationContext, nonConstructorMutator);
        if (deserializerFromAnnotation == null) {
            deserializerFromAnnotation = resolveMemberAndTypeAnnotations.getValueHandler();
        }
        if (deserializerFromAnnotation != null) {
            withValueDeserializer = withValueDeserializer.withValueDeserializer(deserializationContext.handlePrimaryContextualization(deserializerFromAnnotation, withValueDeserializer, resolveMemberAndTypeAnnotations));
        }
        final AnnotationIntrospector.ReferenceProperty referenceType = beanPropertyDefinition.findReferenceType();
        if (referenceType != null && referenceType.isManagedReference()) {
            withValueDeserializer.setManagedReferenceName(referenceType.getName());
        }
        final ObjectIdInfo objectIdInfo = beanPropertyDefinition.findObjectIdInfo();
        if (objectIdInfo != null) {
            withValueDeserializer.setObjectIdInfo(objectIdInfo);
        }
        return withValueDeserializer;
    }
    
    protected SettableBeanProperty constructSetterlessProperty(final DeserializationContext deserializationContext, final BeanDescription beanDescription, final BeanPropertyDefinition beanPropertyDefinition) throws JsonMappingException {
        final AnnotatedMethod getter = beanPropertyDefinition.getGetter();
        final JavaType resolveMemberAndTypeAnnotations = this.resolveMemberAndTypeAnnotations(deserializationContext, getter, getter.getType());
        SettableBeanProperty withValueDeserializer = new SetterlessProperty(beanPropertyDefinition, resolveMemberAndTypeAnnotations, resolveMemberAndTypeAnnotations.getTypeHandler(), beanDescription.getClassAnnotations(), getter);
        JsonDeserializer<?> deserializerFromAnnotation = this.findDeserializerFromAnnotation(deserializationContext, getter);
        if (deserializerFromAnnotation == null) {
            deserializerFromAnnotation = resolveMemberAndTypeAnnotations.getValueHandler();
        }
        if (deserializerFromAnnotation != null) {
            withValueDeserializer = withValueDeserializer.withValueDeserializer(deserializationContext.handlePrimaryContextualization(deserializerFromAnnotation, withValueDeserializer, resolveMemberAndTypeAnnotations));
        }
        return withValueDeserializer;
    }
    
    protected boolean isPotentialBeanType(final Class<?> clazz) {
        final String canBeABeanType = ClassUtil.canBeABeanType(clazz);
        if (canBeABeanType != null) {
            throw new IllegalArgumentException("Cannot deserialize Class " + clazz.getName() + " (of type " + canBeABeanType + ") as a Bean");
        }
        if (ClassUtil.isProxyType(clazz)) {
            throw new IllegalArgumentException("Cannot deserialize Proxy class " + clazz.getName() + " as a Bean");
        }
        final String localType = ClassUtil.isLocalType(clazz, true);
        if (localType != null) {
            throw new IllegalArgumentException("Cannot deserialize Class " + clazz.getName() + " (of type " + localType + ") as a Bean");
        }
        return true;
    }
    
    protected boolean isIgnorableType(final DeserializationConfig deserializationConfig, final BeanPropertyDefinition beanPropertyDefinition, final Class<?> clazz, final Map<Class<?>, Boolean> map) {
        final Boolean b = map.get(clazz);
        if (b != null) {
            return b;
        }
        Boolean b2;
        if (clazz == String.class || clazz.isPrimitive()) {
            b2 = Boolean.FALSE;
        }
        else {
            b2 = deserializationConfig.getConfigOverride(clazz).getIsIgnoredType();
            if (b2 == null) {
                b2 = deserializationConfig.getAnnotationIntrospector().isIgnorableType(deserializationConfig.introspectClassAnnotations(clazz).getClassInfo());
                if (b2 == null) {
                    b2 = Boolean.FALSE;
                }
            }
        }
        map.put(clazz, b2);
        return b2;
    }
    
    protected void _validateSubType(final DeserializationContext deserializationContext, final JavaType javaType, final BeanDescription beanDescription) throws JsonMappingException {
        SubTypeValidator.instance().validateSubType(deserializationContext, javaType, beanDescription);
    }
    
    static {
        INIT_CAUSE_PARAMS = new Class[] { Throwable.class };
        instance = new BeanDeserializerFactory(new DeserializerFactoryConfig());
    }
}
