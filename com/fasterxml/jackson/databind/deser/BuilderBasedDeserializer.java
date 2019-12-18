package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.introspect.*;
import java.util.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.deser.impl.*;

public class BuilderBasedDeserializer extends BeanDeserializerBase
{
    private static final long serialVersionUID = 1L;
    protected final AnnotatedMethod _buildMethod;
    protected final JavaType _targetType;
    
    public BuilderBasedDeserializer(final BeanDeserializerBuilder beanDeserializerBuilder, final BeanDescription beanDescription, final JavaType targetType, final BeanPropertyMap beanPropertyMap, final Map<String, SettableBeanProperty> map, final Set<String> set, final boolean b, final boolean b2) {
        super(beanDeserializerBuilder, beanDescription, beanPropertyMap, map, set, b, b2);
        this._targetType = targetType;
        this._buildMethod = beanDeserializerBuilder.getBuildMethod();
        if (this._objectIdReader != null) {
            throw new IllegalArgumentException("Cannot use Object Id with Builder-based deserialization (type " + beanDescription.getType() + ")");
        }
    }
    
    @Deprecated
    public BuilderBasedDeserializer(final BeanDeserializerBuilder beanDeserializerBuilder, final BeanDescription beanDescription, final BeanPropertyMap beanPropertyMap, final Map<String, SettableBeanProperty> map, final Set<String> set, final boolean b, final boolean b2) {
        this(beanDeserializerBuilder, beanDescription, beanDescription.getType(), beanPropertyMap, map, set, b, b2);
    }
    
    protected BuilderBasedDeserializer(final BuilderBasedDeserializer builderBasedDeserializer) {
        this(builderBasedDeserializer, builderBasedDeserializer._ignoreAllUnknown);
    }
    
    protected BuilderBasedDeserializer(final BuilderBasedDeserializer builderBasedDeserializer, final boolean b) {
        super(builderBasedDeserializer, b);
        this._buildMethod = builderBasedDeserializer._buildMethod;
        this._targetType = builderBasedDeserializer._targetType;
    }
    
    protected BuilderBasedDeserializer(final BuilderBasedDeserializer builderBasedDeserializer, final NameTransformer nameTransformer) {
        super(builderBasedDeserializer, nameTransformer);
        this._buildMethod = builderBasedDeserializer._buildMethod;
        this._targetType = builderBasedDeserializer._targetType;
    }
    
    public BuilderBasedDeserializer(final BuilderBasedDeserializer builderBasedDeserializer, final ObjectIdReader objectIdReader) {
        super(builderBasedDeserializer, objectIdReader);
        this._buildMethod = builderBasedDeserializer._buildMethod;
        this._targetType = builderBasedDeserializer._targetType;
    }
    
    public BuilderBasedDeserializer(final BuilderBasedDeserializer builderBasedDeserializer, final Set<String> set) {
        super(builderBasedDeserializer, set);
        this._buildMethod = builderBasedDeserializer._buildMethod;
        this._targetType = builderBasedDeserializer._targetType;
    }
    
    public BuilderBasedDeserializer(final BuilderBasedDeserializer builderBasedDeserializer, final BeanPropertyMap beanPropertyMap) {
        super(builderBasedDeserializer, beanPropertyMap);
        this._buildMethod = builderBasedDeserializer._buildMethod;
        this._targetType = builderBasedDeserializer._targetType;
    }
    
    @Override
    public JsonDeserializer<Object> unwrappingDeserializer(final NameTransformer nameTransformer) {
        return new BuilderBasedDeserializer(this, nameTransformer);
    }
    
    @Override
    public BeanDeserializerBase withObjectIdReader(final ObjectIdReader objectIdReader) {
        return new BuilderBasedDeserializer(this, objectIdReader);
    }
    
    @Override
    public BeanDeserializerBase withIgnorableProperties(final Set<String> set) {
        return new BuilderBasedDeserializer(this, set);
    }
    
    @Override
    public BeanDeserializerBase withBeanProperties(final BeanPropertyMap beanPropertyMap) {
        return new BuilderBasedDeserializer(this, beanPropertyMap);
    }
    
    @Override
    protected BeanDeserializerBase asArrayDeserializer() {
        return new BeanAsArrayBuilderDeserializer(this, this._targetType, this._beanProperties.getPropertiesInInsertionOrder(), this._buildMethod);
    }
    
    @Override
    public Boolean supportsUpdate(final DeserializationConfig deserializationConfig) {
        return Boolean.FALSE;
    }
    
    protected Object finishBuild(final DeserializationContext deserializationContext, final Object o) throws IOException {
        if (null == this._buildMethod) {
            return o;
        }
        try {
            return this._buildMethod.getMember().invoke(o, (Object[])null);
        }
        catch (Exception ex) {
            return this.wrapInstantiationProblem(ex, deserializationContext);
        }
    }
    
    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.isExpectedStartObjectToken()) {
            final JsonToken nextToken = jsonParser.nextToken();
            if (this._vanillaProcessing) {
                return this.finishBuild(deserializationContext, this.vanillaDeserialize(jsonParser, deserializationContext, nextToken));
            }
            return this.finishBuild(deserializationContext, this.deserializeFromObject(jsonParser, deserializationContext));
        }
        else {
            switch (jsonParser.getCurrentTokenId()) {
                case 6: {
                    return this.finishBuild(deserializationContext, this.deserializeFromString(jsonParser, deserializationContext));
                }
                case 7: {
                    return this.finishBuild(deserializationContext, this.deserializeFromNumber(jsonParser, deserializationContext));
                }
                case 8: {
                    return this.finishBuild(deserializationContext, this.deserializeFromDouble(jsonParser, deserializationContext));
                }
                case 12: {
                    return jsonParser.getEmbeddedObject();
                }
                case 9:
                case 10: {
                    return this.finishBuild(deserializationContext, this.deserializeFromBoolean(jsonParser, deserializationContext));
                }
                case 3: {
                    return this.finishBuild(deserializationContext, this.deserializeFromArray(jsonParser, deserializationContext));
                }
                case 2:
                case 5: {
                    return this.finishBuild(deserializationContext, this.deserializeFromObject(jsonParser, deserializationContext));
                }
                default: {
                    return deserializationContext.handleUnexpectedToken(this.handledType(), jsonParser);
                }
            }
        }
    }
    
    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext, final Object o) throws IOException {
        final JavaType targetType = this._targetType;
        final Class<?> handledType = this.handledType();
        final Class<?> class1 = o.getClass();
        if (handledType.isAssignableFrom(class1)) {
            return deserializationContext.reportBadDefinition(targetType, String.format("Deserialization of %s by passing existing Builder (%s) instance not supported", targetType, handledType.getName()));
        }
        return deserializationContext.reportBadDefinition(targetType, String.format("Deserialization of %s by passing existing instance (of %s) not supported", targetType, class1.getName()));
    }
    
    private final Object vanillaDeserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext, final JsonToken jsonToken) throws IOException {
        Object o = this._valueInstantiator.createUsingDefault(deserializationContext);
        while (jsonParser.getCurrentToken() == JsonToken.FIELD_NAME) {
            final String currentName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            final SettableBeanProperty find = this._beanProperties.find(currentName);
            if (find != null) {
                try {
                    o = find.deserializeSetAndReturn(jsonParser, deserializationContext, o);
                }
                catch (Exception ex) {
                    this.wrapAndThrow(ex, o, currentName, deserializationContext);
                }
            }
            else {
                this.handleUnknownVanilla(jsonParser, deserializationContext, o, currentName);
            }
            jsonParser.nextToken();
        }
        return o;
    }
    
    @Override
    public Object deserializeFromObject(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (!this._nonStandardCreation) {
            Object o = this._valueInstantiator.createUsingDefault(deserializationContext);
            if (this._injectables != null) {
                this.injectValues(deserializationContext, o);
            }
            if (this._needViewProcesing) {
                final Class<?> activeView = deserializationContext.getActiveView();
                if (activeView != null) {
                    return this.deserializeWithView(jsonParser, deserializationContext, o, activeView);
                }
            }
            while (jsonParser.getCurrentToken() == JsonToken.FIELD_NAME) {
                final String currentName = jsonParser.getCurrentName();
                jsonParser.nextToken();
                final SettableBeanProperty find = this._beanProperties.find(currentName);
                if (find != null) {
                    try {
                        o = find.deserializeSetAndReturn(jsonParser, deserializationContext, o);
                    }
                    catch (Exception ex) {
                        this.wrapAndThrow(ex, o, currentName, deserializationContext);
                    }
                }
                else {
                    this.handleUnknownVanilla(jsonParser, deserializationContext, o, currentName);
                }
                jsonParser.nextToken();
            }
            return o;
        }
        if (this._unwrappedPropertyHandler != null) {
            return this.deserializeWithUnwrapped(jsonParser, deserializationContext);
        }
        if (this._externalTypeIdHandler != null) {
            return this.deserializeWithExternalTypeId(jsonParser, deserializationContext);
        }
        return this.deserializeFromObjectUsingNonDefault(jsonParser, deserializationContext);
    }
    
    @Override
    protected Object _deserializeUsingPropertyBased(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        final PropertyValueBuffer startBuilding = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, this._objectIdReader);
        final Class<?> clazz = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        TokenBuffer tokenBuffer = null;
        for (JsonToken jsonToken = jsonParser.getCurrentToken(); jsonToken == JsonToken.FIELD_NAME; jsonToken = jsonParser.nextToken()) {
            final String currentName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            final SettableBeanProperty creatorProperty = propertyBasedCreator.findCreatorProperty(currentName);
            if (creatorProperty != null) {
                if (clazz != null && !creatorProperty.visibleInView(clazz)) {
                    jsonParser.skipChildren();
                }
                else if (startBuilding.assignParameter(creatorProperty, creatorProperty.deserialize(jsonParser, deserializationContext))) {
                    jsonParser.nextToken();
                    Object o;
                    try {
                        o = propertyBasedCreator.build(deserializationContext, startBuilding);
                    }
                    catch (Exception ex) {
                        this.wrapAndThrow(ex, this._beanType.getRawClass(), currentName, deserializationContext);
                        continue;
                    }
                    if (o.getClass() != this._beanType.getRawClass()) {
                        return this.handlePolymorphic(jsonParser, deserializationContext, o, tokenBuffer);
                    }
                    if (tokenBuffer != null) {
                        o = this.handleUnknownProperties(deserializationContext, o, tokenBuffer);
                    }
                    return this._deserialize(jsonParser, deserializationContext, o);
                }
            }
            else if (!startBuilding.readIdProperty(currentName)) {
                final SettableBeanProperty find = this._beanProperties.find(currentName);
                if (find != null) {
                    startBuilding.bufferProperty(find, find.deserialize(jsonParser, deserializationContext));
                }
                else if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                    this.handleIgnoredProperty(jsonParser, deserializationContext, this.handledType(), currentName);
                }
                else if (this._anySetter != null) {
                    startBuilding.bufferAnyProperty(this._anySetter, currentName, this._anySetter.deserialize(jsonParser, deserializationContext));
                }
                else {
                    if (tokenBuffer == null) {
                        tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
                    }
                    tokenBuffer.writeFieldName(currentName);
                    tokenBuffer.copyCurrentStructure(jsonParser);
                }
            }
        }
        Object o2;
        try {
            o2 = propertyBasedCreator.build(deserializationContext, startBuilding);
        }
        catch (Exception ex2) {
            o2 = this.wrapInstantiationProblem(ex2, deserializationContext);
        }
        if (tokenBuffer == null) {
            return o2;
        }
        if (o2.getClass() != this._beanType.getRawClass()) {
            return this.handlePolymorphic(null, deserializationContext, o2, tokenBuffer);
        }
        return this.handleUnknownProperties(deserializationContext, o2, tokenBuffer);
    }
    
    protected final Object _deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext, Object deserializeSetAndReturn) throws IOException {
        if (this._injectables != null) {
            this.injectValues(deserializationContext, deserializeSetAndReturn);
        }
        if (this._unwrappedPropertyHandler != null) {
            if (jsonParser.hasToken(JsonToken.START_OBJECT)) {
                jsonParser.nextToken();
            }
            final TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
            tokenBuffer.writeStartObject();
            return this.deserializeWithUnwrapped(jsonParser, deserializationContext, deserializeSetAndReturn, tokenBuffer);
        }
        if (this._externalTypeIdHandler != null) {
            return this.deserializeWithExternalTypeId(jsonParser, deserializationContext, deserializeSetAndReturn);
        }
        if (this._needViewProcesing) {
            final Class<?> activeView = deserializationContext.getActiveView();
            if (activeView != null) {
                return this.deserializeWithView(jsonParser, deserializationContext, deserializeSetAndReturn, activeView);
            }
        }
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonToken = jsonParser.nextToken();
        }
        while (jsonToken == JsonToken.FIELD_NAME) {
            final String currentName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            final SettableBeanProperty find = this._beanProperties.find(currentName);
            if (find != null) {
                try {
                    deserializeSetAndReturn = find.deserializeSetAndReturn(jsonParser, deserializationContext, deserializeSetAndReturn);
                }
                catch (Exception ex) {
                    this.wrapAndThrow(ex, deserializeSetAndReturn, currentName, deserializationContext);
                }
            }
            else {
                this.handleUnknownVanilla(jsonParser, deserializationContext, this.handledType(), currentName);
            }
            jsonToken = jsonParser.nextToken();
        }
        return deserializeSetAndReturn;
    }
    
    protected final Object deserializeWithView(final JsonParser jsonParser, final DeserializationContext deserializationContext, Object deserializeSetAndReturn, final Class<?> clazz) throws IOException {
        for (JsonToken jsonToken = jsonParser.getCurrentToken(); jsonToken == JsonToken.FIELD_NAME; jsonToken = jsonParser.nextToken()) {
            final String currentName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            final SettableBeanProperty find = this._beanProperties.find(currentName);
            if (find != null) {
                if (!find.visibleInView(clazz)) {
                    jsonParser.skipChildren();
                }
                else {
                    try {
                        deserializeSetAndReturn = find.deserializeSetAndReturn(jsonParser, deserializationContext, deserializeSetAndReturn);
                    }
                    catch (Exception ex) {
                        this.wrapAndThrow(ex, deserializeSetAndReturn, currentName, deserializationContext);
                    }
                }
            }
            else {
                this.handleUnknownVanilla(jsonParser, deserializationContext, deserializeSetAndReturn, currentName);
            }
        }
        return deserializeSetAndReturn;
    }
    
    protected Object deserializeWithUnwrapped(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (this._propertyBasedCreator != null) {
            return this.deserializeUsingPropertyBasedWithUnwrapped(jsonParser, deserializationContext);
        }
        final TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
        tokenBuffer.writeStartObject();
        Object o = this._valueInstantiator.createUsingDefault(deserializationContext);
        if (this._injectables != null) {
            this.injectValues(deserializationContext, o);
        }
        final Class<?> clazz = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        while (jsonParser.getCurrentToken() == JsonToken.FIELD_NAME) {
            final String currentName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            final SettableBeanProperty find = this._beanProperties.find(currentName);
            if (find != null) {
                if (clazz != null && !find.visibleInView(clazz)) {
                    jsonParser.skipChildren();
                }
                else {
                    try {
                        o = find.deserializeSetAndReturn(jsonParser, deserializationContext, o);
                    }
                    catch (Exception ex) {
                        this.wrapAndThrow(ex, o, currentName, deserializationContext);
                    }
                }
            }
            else if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                this.handleIgnoredProperty(jsonParser, deserializationContext, o, currentName);
            }
            else {
                tokenBuffer.writeFieldName(currentName);
                tokenBuffer.copyCurrentStructure(jsonParser);
                if (this._anySetter != null) {
                    try {
                        this._anySetter.deserializeAndSet(jsonParser, deserializationContext, o, currentName);
                    }
                    catch (Exception ex2) {
                        this.wrapAndThrow(ex2, o, currentName, deserializationContext);
                    }
                }
            }
            jsonParser.nextToken();
        }
        tokenBuffer.writeEndObject();
        return this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, o, tokenBuffer);
    }
    
    protected Object deserializeUsingPropertyBasedWithUnwrapped(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        final PropertyValueBuffer startBuilding = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, this._objectIdReader);
        final TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
        tokenBuffer.writeStartObject();
        Object o = null;
        for (JsonToken jsonToken = jsonParser.getCurrentToken(); jsonToken == JsonToken.FIELD_NAME; jsonToken = jsonParser.nextToken()) {
            final String currentName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            final SettableBeanProperty creatorProperty = propertyBasedCreator.findCreatorProperty(currentName);
            if (creatorProperty != null) {
                if (startBuilding.assignParameter(creatorProperty, creatorProperty.deserialize(jsonParser, deserializationContext))) {
                    jsonParser.nextToken();
                    try {
                        o = propertyBasedCreator.build(deserializationContext, startBuilding);
                    }
                    catch (Exception ex) {
                        this.wrapAndThrow(ex, this._beanType.getRawClass(), currentName, deserializationContext);
                        continue;
                    }
                    if (o.getClass() != this._beanType.getRawClass()) {
                        return this.handlePolymorphic(jsonParser, deserializationContext, o, tokenBuffer);
                    }
                    return this.deserializeWithUnwrapped(jsonParser, deserializationContext, o, tokenBuffer);
                }
            }
            else if (!startBuilding.readIdProperty(currentName)) {
                final SettableBeanProperty find = this._beanProperties.find(currentName);
                if (find != null) {
                    startBuilding.bufferProperty(find, find.deserialize(jsonParser, deserializationContext));
                }
                else if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                    this.handleIgnoredProperty(jsonParser, deserializationContext, this.handledType(), currentName);
                }
                else {
                    tokenBuffer.writeFieldName(currentName);
                    tokenBuffer.copyCurrentStructure(jsonParser);
                    if (this._anySetter != null) {
                        startBuilding.bufferAnyProperty(this._anySetter, currentName, this._anySetter.deserialize(jsonParser, deserializationContext));
                    }
                }
            }
        }
        tokenBuffer.writeEndObject();
        if (o == null) {
            try {
                o = propertyBasedCreator.build(deserializationContext, startBuilding);
            }
            catch (Exception ex2) {
                return this.wrapInstantiationProblem(ex2, deserializationContext);
            }
        }
        return this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, o, tokenBuffer);
    }
    
    protected Object deserializeWithUnwrapped(final JsonParser jsonParser, final DeserializationContext deserializationContext, Object deserializeSetAndReturn, final TokenBuffer tokenBuffer) throws IOException {
        final Class<?> clazz = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        for (JsonToken jsonToken = jsonParser.getCurrentToken(); jsonToken == JsonToken.FIELD_NAME; jsonToken = jsonParser.nextToken()) {
            final String currentName = jsonParser.getCurrentName();
            final SettableBeanProperty find = this._beanProperties.find(currentName);
            jsonParser.nextToken();
            if (find != null) {
                if (clazz != null && !find.visibleInView(clazz)) {
                    jsonParser.skipChildren();
                }
                else {
                    try {
                        deserializeSetAndReturn = find.deserializeSetAndReturn(jsonParser, deserializationContext, deserializeSetAndReturn);
                    }
                    catch (Exception ex) {
                        this.wrapAndThrow(ex, deserializeSetAndReturn, currentName, deserializationContext);
                    }
                }
            }
            else if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                this.handleIgnoredProperty(jsonParser, deserializationContext, deserializeSetAndReturn, currentName);
            }
            else {
                tokenBuffer.writeFieldName(currentName);
                tokenBuffer.copyCurrentStructure(jsonParser);
                if (this._anySetter != null) {
                    this._anySetter.deserializeAndSet(jsonParser, deserializationContext, deserializeSetAndReturn, currentName);
                }
            }
        }
        tokenBuffer.writeEndObject();
        return this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, deserializeSetAndReturn, tokenBuffer);
    }
    
    protected Object deserializeWithExternalTypeId(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (this._propertyBasedCreator != null) {
            return this.deserializeUsingPropertyBasedWithExternalTypeId(jsonParser, deserializationContext);
        }
        return this.deserializeWithExternalTypeId(jsonParser, deserializationContext, this._valueInstantiator.createUsingDefault(deserializationContext));
    }
    
    protected Object deserializeWithExternalTypeId(final JsonParser jsonParser, final DeserializationContext deserializationContext, Object deserializeSetAndReturn) throws IOException {
        final Class<?> clazz = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        final ExternalTypeHandler start = this._externalTypeIdHandler.start();
        for (JsonToken jsonToken = jsonParser.getCurrentToken(); jsonToken == JsonToken.FIELD_NAME; jsonToken = jsonParser.nextToken()) {
            final String currentName = jsonParser.getCurrentName();
            final JsonToken nextToken = jsonParser.nextToken();
            final SettableBeanProperty find = this._beanProperties.find(currentName);
            if (find != null) {
                if (nextToken.isScalarValue()) {
                    start.handleTypePropertyValue(jsonParser, deserializationContext, currentName, deserializeSetAndReturn);
                }
                if (clazz != null && !find.visibleInView(clazz)) {
                    jsonParser.skipChildren();
                }
                else {
                    try {
                        deserializeSetAndReturn = find.deserializeSetAndReturn(jsonParser, deserializationContext, deserializeSetAndReturn);
                    }
                    catch (Exception ex) {
                        this.wrapAndThrow(ex, deserializeSetAndReturn, currentName, deserializationContext);
                    }
                }
            }
            else if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                this.handleIgnoredProperty(jsonParser, deserializationContext, deserializeSetAndReturn, currentName);
            }
            else if (!start.handlePropertyValue(jsonParser, deserializationContext, currentName, deserializeSetAndReturn)) {
                if (this._anySetter != null) {
                    try {
                        this._anySetter.deserializeAndSet(jsonParser, deserializationContext, deserializeSetAndReturn, currentName);
                    }
                    catch (Exception ex2) {
                        this.wrapAndThrow(ex2, deserializeSetAndReturn, currentName, deserializationContext);
                    }
                }
                else {
                    this.handleUnknownProperty(jsonParser, deserializationContext, deserializeSetAndReturn, currentName);
                }
            }
        }
        return start.complete(jsonParser, deserializationContext, deserializeSetAndReturn);
    }
    
    protected Object deserializeUsingPropertyBasedWithExternalTypeId(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final JavaType targetType = this._targetType;
        return deserializationContext.reportBadDefinition(targetType, String.format("Deserialization (of %s) with Builder, External type id, @JsonCreator not yet implemented", targetType));
    }
}
