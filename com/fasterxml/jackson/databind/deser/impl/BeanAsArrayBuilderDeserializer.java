package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.databind.util.*;
import java.util.*;
import java.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

public class BeanAsArrayBuilderDeserializer extends BeanDeserializerBase
{
    private static final long serialVersionUID = 1L;
    protected final BeanDeserializerBase _delegate;
    protected final SettableBeanProperty[] _orderedProperties;
    protected final AnnotatedMethod _buildMethod;
    protected final JavaType _targetType;
    
    public BeanAsArrayBuilderDeserializer(final BeanDeserializerBase delegate, final JavaType targetType, final SettableBeanProperty[] orderedProperties, final AnnotatedMethod buildMethod) {
        super(delegate);
        this._delegate = delegate;
        this._targetType = targetType;
        this._orderedProperties = orderedProperties;
        this._buildMethod = buildMethod;
    }
    
    @Override
    public JsonDeserializer<Object> unwrappingDeserializer(final NameTransformer nameTransformer) {
        return this._delegate.unwrappingDeserializer(nameTransformer);
    }
    
    @Override
    public BeanDeserializerBase withObjectIdReader(final ObjectIdReader objectIdReader) {
        return new BeanAsArrayBuilderDeserializer(this._delegate.withObjectIdReader(objectIdReader), this._targetType, this._orderedProperties, this._buildMethod);
    }
    
    @Override
    public BeanDeserializerBase withIgnorableProperties(final Set<String> set) {
        return new BeanAsArrayBuilderDeserializer(this._delegate.withIgnorableProperties(set), this._targetType, this._orderedProperties, this._buildMethod);
    }
    
    @Override
    public BeanDeserializerBase withBeanProperties(final BeanPropertyMap beanPropertyMap) {
        return new BeanAsArrayBuilderDeserializer(this._delegate.withBeanProperties(beanPropertyMap), this._targetType, this._orderedProperties, this._buildMethod);
    }
    
    @Override
    protected BeanDeserializerBase asArrayDeserializer() {
        return this;
    }
    
    @Override
    public Boolean supportsUpdate(final DeserializationConfig deserializationConfig) {
        return Boolean.FALSE;
    }
    
    protected final Object finishBuild(final DeserializationContext deserializationContext, final Object o) throws IOException {
        try {
            return this._buildMethod.getMember().invoke(o, (Object[])null);
        }
        catch (Exception ex) {
            return this.wrapInstantiationProblem(ex, deserializationContext);
        }
    }
    
    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (!jsonParser.isExpectedStartArrayToken()) {
            return this.finishBuild(deserializationContext, this._deserializeFromNonArray(jsonParser, deserializationContext));
        }
        if (!this._vanillaProcessing) {
            return this.finishBuild(deserializationContext, this._deserializeNonVanilla(jsonParser, deserializationContext));
        }
        Object o = this._valueInstantiator.createUsingDefault(deserializationContext);
        final SettableBeanProperty[] orderedProperties = this._orderedProperties;
        int n = 0;
        final int length = orderedProperties.length;
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            if (n == length) {
                if (!this._ignoreAllUnknown && deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
                    deserializationContext.reportInputMismatch(this.handledType(), "Unexpected JSON values; expected at most %d properties (in JSON Array)", length);
                }
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    jsonParser.skipChildren();
                }
                return this.finishBuild(deserializationContext, o);
            }
            final SettableBeanProperty settableBeanProperty = orderedProperties[n];
            if (settableBeanProperty != null) {
                try {
                    o = settableBeanProperty.deserializeSetAndReturn(jsonParser, deserializationContext, o);
                }
                catch (Exception ex) {
                    this.wrapAndThrow(ex, o, settableBeanProperty.getName(), deserializationContext);
                }
            }
            else {
                jsonParser.skipChildren();
            }
            ++n;
        }
        return this.finishBuild(deserializationContext, o);
    }
    
    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext, final Object o) throws IOException {
        return this._delegate.deserialize(jsonParser, deserializationContext, o);
    }
    
    @Override
    public Object deserializeFromObject(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        return this._deserializeFromNonArray(jsonParser, deserializationContext);
    }
    
    protected Object _deserializeNonVanilla(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (this._nonStandardCreation) {
            return this.deserializeFromObjectUsingNonDefault(jsonParser, deserializationContext);
        }
        final Object usingDefault = this._valueInstantiator.createUsingDefault(deserializationContext);
        if (this._injectables != null) {
            this.injectValues(deserializationContext, usingDefault);
        }
        final Class<?> clazz = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        final SettableBeanProperty[] orderedProperties = this._orderedProperties;
        int n = 0;
        final int length = orderedProperties.length;
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            if (n == length) {
                if (!this._ignoreAllUnknown && deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
                    deserializationContext.reportWrongTokenException(this, JsonToken.END_ARRAY, "Unexpected JSON value(s); expected at most %d properties (in JSON Array)", length);
                }
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    jsonParser.skipChildren();
                }
                return usingDefault;
            }
            final SettableBeanProperty settableBeanProperty = orderedProperties[n];
            ++n;
            Label_0149: {
                if (settableBeanProperty != null) {
                    if (clazz != null) {
                        if (!settableBeanProperty.visibleInView(clazz)) {
                            break Label_0149;
                        }
                    }
                    try {
                        settableBeanProperty.deserializeSetAndReturn(jsonParser, deserializationContext, usingDefault);
                    }
                    catch (Exception ex) {
                        this.wrapAndThrow(ex, usingDefault, settableBeanProperty.getName(), deserializationContext);
                    }
                    continue;
                }
            }
            jsonParser.skipChildren();
        }
        return usingDefault;
    }
    
    @Override
    protected final Object _deserializeUsingPropertyBased(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        final PropertyValueBuffer startBuilding = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, this._objectIdReader);
        final SettableBeanProperty[] orderedProperties = this._orderedProperties;
        final int length = orderedProperties.length;
        final Class<?> clazz = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        int n = 0;
        Object o = null;
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            final SettableBeanProperty settableBeanProperty = (n < length) ? orderedProperties[n] : null;
            Label_0308: {
                if (settableBeanProperty == null) {
                    jsonParser.skipChildren();
                }
                else if (clazz != null && !settableBeanProperty.visibleInView(clazz)) {
                    jsonParser.skipChildren();
                }
                else if (o != null) {
                    try {
                        o = settableBeanProperty.deserializeSetAndReturn(jsonParser, deserializationContext, o);
                    }
                    catch (Exception ex) {
                        this.wrapAndThrow(ex, o, settableBeanProperty.getName(), deserializationContext);
                    }
                }
                else {
                    final String name = settableBeanProperty.getName();
                    final SettableBeanProperty creatorProperty = propertyBasedCreator.findCreatorProperty(name);
                    if (creatorProperty != null) {
                        if (startBuilding.assignParameter(creatorProperty, creatorProperty.deserialize(jsonParser, deserializationContext))) {
                            try {
                                o = propertyBasedCreator.build(deserializationContext, startBuilding);
                            }
                            catch (Exception ex2) {
                                this.wrapAndThrow(ex2, this._beanType.getRawClass(), name, deserializationContext);
                                break Label_0308;
                            }
                            if (o.getClass() != this._beanType.getRawClass()) {
                                return deserializationContext.reportBadDefinition(this._beanType, String.format("Cannot support implicit polymorphic deserialization for POJOs-as-Arrays style: nominal type %s, actual type %s", this._beanType.getRawClass().getName(), o.getClass().getName()));
                            }
                        }
                    }
                    else if (!startBuilding.readIdProperty(name)) {
                        startBuilding.bufferProperty(settableBeanProperty, settableBeanProperty.deserialize(jsonParser, deserializationContext));
                    }
                }
            }
            ++n;
        }
        if (o == null) {
            try {
                o = propertyBasedCreator.build(deserializationContext, startBuilding);
            }
            catch (Exception ex3) {
                return this.wrapInstantiationProblem(ex3, deserializationContext);
            }
        }
        return o;
    }
    
    protected Object _deserializeFromNonArray(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        return deserializationContext.handleUnexpectedToken(this.handledType(), jsonParser.getCurrentToken(), jsonParser, "Cannot deserialize a POJO (of type %s) from non-Array representation (token: %s): type/property designed to be serialized as JSON Array", this._beanType.getRawClass().getName(), jsonParser.getCurrentToken());
    }
}
