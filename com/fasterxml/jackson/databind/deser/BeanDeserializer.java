package com.fasterxml.jackson.databind.deser;

import java.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.util.*;
import java.util.*;
import com.fasterxml.jackson.databind.deser.impl.*;
import com.fasterxml.jackson.databind.*;

public class BeanDeserializer extends BeanDeserializerBase implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected transient Exception _nullFromCreator;
    private transient NameTransformer _currentlyTransforming;
    
    public BeanDeserializer(final BeanDeserializerBuilder beanDeserializerBuilder, final BeanDescription beanDescription, final BeanPropertyMap beanPropertyMap, final Map<String, SettableBeanProperty> map, final HashSet<String> set, final boolean b, final boolean b2) {
        super(beanDeserializerBuilder, beanDescription, beanPropertyMap, map, set, b, b2);
    }
    
    protected BeanDeserializer(final BeanDeserializerBase beanDeserializerBase) {
        super(beanDeserializerBase, beanDeserializerBase._ignoreAllUnknown);
    }
    
    protected BeanDeserializer(final BeanDeserializerBase beanDeserializerBase, final boolean b) {
        super(beanDeserializerBase, b);
    }
    
    protected BeanDeserializer(final BeanDeserializerBase beanDeserializerBase, final NameTransformer nameTransformer) {
        super(beanDeserializerBase, nameTransformer);
    }
    
    public BeanDeserializer(final BeanDeserializerBase beanDeserializerBase, final ObjectIdReader objectIdReader) {
        super(beanDeserializerBase, objectIdReader);
    }
    
    public BeanDeserializer(final BeanDeserializerBase beanDeserializerBase, final Set<String> set) {
        super(beanDeserializerBase, set);
    }
    
    public BeanDeserializer(final BeanDeserializerBase beanDeserializerBase, final BeanPropertyMap beanPropertyMap) {
        super(beanDeserializerBase, beanPropertyMap);
    }
    
    @Override
    public JsonDeserializer<Object> unwrappingDeserializer(final NameTransformer currentlyTransforming) {
        if (this.getClass() != BeanDeserializer.class) {
            return this;
        }
        if (this._currentlyTransforming == currentlyTransforming) {
            return this;
        }
        this._currentlyTransforming = currentlyTransforming;
        try {
            return new BeanDeserializer(this, currentlyTransforming);
        }
        finally {
            this._currentlyTransforming = null;
        }
    }
    
    @Override
    public BeanDeserializer withObjectIdReader(final ObjectIdReader objectIdReader) {
        return new BeanDeserializer(this, objectIdReader);
    }
    
    @Override
    public BeanDeserializer withIgnorableProperties(final Set<String> set) {
        return new BeanDeserializer(this, set);
    }
    
    @Override
    public BeanDeserializerBase withBeanProperties(final BeanPropertyMap beanPropertyMap) {
        return new BeanDeserializer(this, beanPropertyMap);
    }
    
    @Override
    protected BeanDeserializerBase asArrayDeserializer() {
        return new BeanAsArrayDeserializer(this, this._beanProperties.getPropertiesInInsertionOrder());
    }
    
    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (!jsonParser.isExpectedStartObjectToken()) {
            return this._deserializeOther(jsonParser, deserializationContext, jsonParser.getCurrentToken());
        }
        if (this._vanillaProcessing) {
            return this.vanillaDeserialize(jsonParser, deserializationContext, jsonParser.nextToken());
        }
        jsonParser.nextToken();
        if (this._objectIdReader != null) {
            return this.deserializeWithObjectId(jsonParser, deserializationContext);
        }
        return this.deserializeFromObject(jsonParser, deserializationContext);
    }
    
    protected final Object _deserializeOther(final JsonParser jsonParser, final DeserializationContext deserializationContext, final JsonToken jsonToken) throws IOException {
        if (jsonToken != null) {
            switch (jsonToken) {
                case VALUE_STRING: {
                    return this.deserializeFromString(jsonParser, deserializationContext);
                }
                case VALUE_NUMBER_INT: {
                    return this.deserializeFromNumber(jsonParser, deserializationContext);
                }
                case VALUE_NUMBER_FLOAT: {
                    return this.deserializeFromDouble(jsonParser, deserializationContext);
                }
                case VALUE_EMBEDDED_OBJECT: {
                    return this.deserializeFromEmbedded(jsonParser, deserializationContext);
                }
                case VALUE_TRUE:
                case VALUE_FALSE: {
                    return this.deserializeFromBoolean(jsonParser, deserializationContext);
                }
                case VALUE_NULL: {
                    return this.deserializeFromNull(jsonParser, deserializationContext);
                }
                case START_ARRAY: {
                    return this.deserializeFromArray(jsonParser, deserializationContext);
                }
                case FIELD_NAME:
                case END_OBJECT: {
                    if (this._vanillaProcessing) {
                        return this.vanillaDeserialize(jsonParser, deserializationContext, jsonToken);
                    }
                    if (this._objectIdReader != null) {
                        return this.deserializeWithObjectId(jsonParser, deserializationContext);
                    }
                    return this.deserializeFromObject(jsonParser, deserializationContext);
                }
            }
        }
        return deserializationContext.handleUnexpectedToken(this.handledType(), jsonParser);
    }
    
    @Deprecated
    protected Object _missingToken(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        throw deserializationContext.endOfInputException(this.handledType());
    }
    
    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext, final Object currentValue) throws IOException {
        jsonParser.setCurrentValue(currentValue);
        if (this._injectables != null) {
            this.injectValues(deserializationContext, currentValue);
        }
        if (this._unwrappedPropertyHandler != null) {
            return this.deserializeWithUnwrapped(jsonParser, deserializationContext, currentValue);
        }
        if (this._externalTypeIdHandler != null) {
            return this.deserializeWithExternalTypeId(jsonParser, deserializationContext, currentValue);
        }
        String s;
        if (jsonParser.isExpectedStartObjectToken()) {
            s = jsonParser.nextFieldName();
            if (s == null) {
                return currentValue;
            }
        }
        else {
            if (!jsonParser.hasTokenId(5)) {
                return currentValue;
            }
            s = jsonParser.getCurrentName();
        }
        if (this._needViewProcesing) {
            final Class<?> activeView = deserializationContext.getActiveView();
            if (activeView != null) {
                return this.deserializeWithView(jsonParser, deserializationContext, currentValue, activeView);
            }
        }
        do {
            jsonParser.nextToken();
            final SettableBeanProperty find = this._beanProperties.find(s);
            if (find != null) {
                try {
                    find.deserializeAndSet(jsonParser, deserializationContext, currentValue);
                }
                catch (Exception ex) {
                    this.wrapAndThrow(ex, currentValue, s, deserializationContext);
                }
            }
            else {
                this.handleUnknownVanilla(jsonParser, deserializationContext, currentValue, s);
            }
        } while ((s = jsonParser.nextFieldName()) != null);
        return currentValue;
    }
    
    private final Object vanillaDeserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext, final JsonToken jsonToken) throws IOException {
        final Object usingDefault = this._valueInstantiator.createUsingDefault(deserializationContext);
        jsonParser.setCurrentValue(usingDefault);
        if (jsonParser.hasTokenId(5)) {
            String s = jsonParser.getCurrentName();
            do {
                jsonParser.nextToken();
                final SettableBeanProperty find = this._beanProperties.find(s);
                if (find != null) {
                    try {
                        find.deserializeAndSet(jsonParser, deserializationContext, usingDefault);
                    }
                    catch (Exception ex) {
                        this.wrapAndThrow(ex, usingDefault, s, deserializationContext);
                    }
                }
                else {
                    this.handleUnknownVanilla(jsonParser, deserializationContext, usingDefault, s);
                }
            } while ((s = jsonParser.nextFieldName()) != null);
        }
        return usingDefault;
    }
    
    @Override
    public Object deserializeFromObject(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (this._objectIdReader != null && this._objectIdReader.maySerializeAsObject() && jsonParser.hasTokenId(5) && this._objectIdReader.isValidReferencePropertyName(jsonParser.getCurrentName(), jsonParser)) {
            return this.deserializeFromObjectId(jsonParser, deserializationContext);
        }
        if (!this._nonStandardCreation) {
            final Object usingDefault = this._valueInstantiator.createUsingDefault(deserializationContext);
            jsonParser.setCurrentValue(usingDefault);
            if (jsonParser.canReadObjectId()) {
                final Object objectId = jsonParser.getObjectId();
                if (objectId != null) {
                    this._handleTypedObjectId(jsonParser, deserializationContext, usingDefault, objectId);
                }
            }
            if (this._injectables != null) {
                this.injectValues(deserializationContext, usingDefault);
            }
            if (this._needViewProcesing) {
                final Class<?> activeView = deserializationContext.getActiveView();
                if (activeView != null) {
                    return this.deserializeWithView(jsonParser, deserializationContext, usingDefault, activeView);
                }
            }
            if (jsonParser.hasTokenId(5)) {
                String s = jsonParser.getCurrentName();
                do {
                    jsonParser.nextToken();
                    final SettableBeanProperty find = this._beanProperties.find(s);
                    if (find != null) {
                        try {
                            find.deserializeAndSet(jsonParser, deserializationContext, usingDefault);
                        }
                        catch (Exception ex) {
                            this.wrapAndThrow(ex, usingDefault, s, deserializationContext);
                        }
                    }
                    else {
                        this.handleUnknownVanilla(jsonParser, deserializationContext, usingDefault, s);
                    }
                } while ((s = jsonParser.nextFieldName()) != null);
            }
            return usingDefault;
        }
        if (this._unwrappedPropertyHandler != null) {
            return this.deserializeWithUnwrapped(jsonParser, deserializationContext);
        }
        if (this._externalTypeIdHandler != null) {
            return this.deserializeWithExternalTypeId(jsonParser, deserializationContext);
        }
        final Object deserializeFromObjectUsingNonDefault = this.deserializeFromObjectUsingNonDefault(jsonParser, deserializationContext);
        if (this._injectables != null) {
            this.injectValues(deserializationContext, deserializeFromObjectUsingNonDefault);
        }
        return deserializeFromObjectUsingNonDefault;
    }
    
    @Override
    protected Object _deserializeUsingPropertyBased(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        final PropertyValueBuffer startBuilding = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, this._objectIdReader);
        TokenBuffer tokenBuffer = null;
        final Class<?> clazz = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        JsonToken jsonToken = jsonParser.getCurrentToken();
        List<BeanReferring> list = null;
        while (jsonToken == JsonToken.FIELD_NAME) {
            final String currentName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if (!startBuilding.readIdProperty(currentName)) {
                final SettableBeanProperty creatorProperty = propertyBasedCreator.findCreatorProperty(currentName);
                if (creatorProperty != null) {
                    if (clazz != null && !creatorProperty.visibleInView(clazz)) {
                        jsonParser.skipChildren();
                    }
                    else if (startBuilding.assignParameter(creatorProperty, this._deserializeWithErrorWrapping(jsonParser, deserializationContext, creatorProperty))) {
                        jsonParser.nextToken();
                        Object currentValue;
                        try {
                            currentValue = propertyBasedCreator.build(deserializationContext, startBuilding);
                        }
                        catch (Exception ex) {
                            currentValue = this.wrapInstantiationProblem(ex, deserializationContext);
                        }
                        if (currentValue == null) {
                            return deserializationContext.handleInstantiationProblem(this.handledType(), null, this._creatorReturnedNullException());
                        }
                        jsonParser.setCurrentValue(currentValue);
                        if (currentValue.getClass() != this._beanType.getRawClass()) {
                            return this.handlePolymorphic(jsonParser, deserializationContext, currentValue, tokenBuffer);
                        }
                        if (tokenBuffer != null) {
                            currentValue = this.handleUnknownProperties(deserializationContext, currentValue, tokenBuffer);
                        }
                        return this.deserialize(jsonParser, deserializationContext, currentValue);
                    }
                }
                else {
                    final SettableBeanProperty find = this._beanProperties.find(currentName);
                    if (find != null) {
                        try {
                            startBuilding.bufferProperty(find, this._deserializeWithErrorWrapping(jsonParser, deserializationContext, find));
                        }
                        catch (UnresolvedForwardReference unresolvedForwardReference) {
                            final BeanReferring handleUnresolvedReference = this.handleUnresolvedReference(deserializationContext, find, startBuilding, unresolvedForwardReference);
                            if (list == null) {
                                list = new ArrayList<BeanReferring>();
                            }
                            list.add(handleUnresolvedReference);
                        }
                    }
                    else if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                        this.handleIgnoredProperty(jsonParser, deserializationContext, this.handledType(), currentName);
                    }
                    else if (this._anySetter != null) {
                        try {
                            startBuilding.bufferAnyProperty(this._anySetter, currentName, this._anySetter.deserialize(jsonParser, deserializationContext));
                        }
                        catch (Exception ex2) {
                            this.wrapAndThrow(ex2, this._beanType.getRawClass(), currentName, deserializationContext);
                        }
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
            jsonToken = jsonParser.nextToken();
        }
        Object build;
        try {
            build = propertyBasedCreator.build(deserializationContext, startBuilding);
        }
        catch (Exception ex3) {
            this.wrapInstantiationProblem(ex3, deserializationContext);
            build = null;
        }
        if (list != null) {
            final Iterator<BeanReferring> iterator = list.iterator();
            while (iterator.hasNext()) {
                iterator.next().setBean(build);
            }
        }
        if (tokenBuffer == null) {
            return build;
        }
        if (build.getClass() != this._beanType.getRawClass()) {
            return this.handlePolymorphic(null, deserializationContext, build, tokenBuffer);
        }
        return this.handleUnknownProperties(deserializationContext, build, tokenBuffer);
    }
    
    private BeanReferring handleUnresolvedReference(final DeserializationContext deserializationContext, final SettableBeanProperty settableBeanProperty, final PropertyValueBuffer propertyValueBuffer, final UnresolvedForwardReference unresolvedForwardReference) throws JsonMappingException {
        final BeanReferring beanReferring = new BeanReferring(deserializationContext, unresolvedForwardReference, settableBeanProperty.getType(), propertyValueBuffer, settableBeanProperty);
        unresolvedForwardReference.getRoid().appendReferring(beanReferring);
        return beanReferring;
    }
    
    protected final Object _deserializeWithErrorWrapping(final JsonParser jsonParser, final DeserializationContext deserializationContext, final SettableBeanProperty settableBeanProperty) throws IOException {
        try {
            return settableBeanProperty.deserialize(jsonParser, deserializationContext);
        }
        catch (Exception ex) {
            this.wrapAndThrow(ex, this._beanType.getRawClass(), settableBeanProperty.getName(), deserializationContext);
            return null;
        }
    }
    
    protected Object deserializeFromNull(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.requiresCustomCodec()) {
            final TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
            tokenBuffer.writeEndObject();
            final JsonParser parser = tokenBuffer.asParser(jsonParser);
            parser.nextToken();
            final Object o = this._vanillaProcessing ? this.vanillaDeserialize(parser, deserializationContext, JsonToken.END_OBJECT) : this.deserializeFromObject(parser, deserializationContext);
            parser.close();
            return o;
        }
        return deserializationContext.handleUnexpectedToken(this.handledType(), jsonParser);
    }
    
    protected final Object deserializeWithView(final JsonParser jsonParser, final DeserializationContext deserializationContext, final Object o, final Class<?> clazz) throws IOException {
        if (jsonParser.hasTokenId(5)) {
            String s = jsonParser.getCurrentName();
            do {
                jsonParser.nextToken();
                final SettableBeanProperty find = this._beanProperties.find(s);
                if (find != null) {
                    if (!find.visibleInView(clazz)) {
                        jsonParser.skipChildren();
                    }
                    else {
                        try {
                            find.deserializeAndSet(jsonParser, deserializationContext, o);
                        }
                        catch (Exception ex) {
                            this.wrapAndThrow(ex, o, s, deserializationContext);
                        }
                    }
                }
                else {
                    this.handleUnknownVanilla(jsonParser, deserializationContext, o, s);
                }
            } while ((s = jsonParser.nextFieldName()) != null);
        }
        return o;
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
        final Object usingDefault = this._valueInstantiator.createUsingDefault(deserializationContext);
        jsonParser.setCurrentValue(usingDefault);
        if (this._injectables != null) {
            this.injectValues(deserializationContext, usingDefault);
        }
        final Class<?> clazz = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        for (String nextFieldName = jsonParser.hasTokenId(5) ? jsonParser.getCurrentName() : null; nextFieldName != null; nextFieldName = jsonParser.nextFieldName()) {
            jsonParser.nextToken();
            final SettableBeanProperty find = this._beanProperties.find(nextFieldName);
            if (find != null) {
                if (clazz != null && !find.visibleInView(clazz)) {
                    jsonParser.skipChildren();
                }
                else {
                    try {
                        find.deserializeAndSet(jsonParser, deserializationContext, usingDefault);
                    }
                    catch (Exception ex) {
                        this.wrapAndThrow(ex, usingDefault, nextFieldName, deserializationContext);
                    }
                }
            }
            else if (this._ignorableProps != null && this._ignorableProps.contains(nextFieldName)) {
                this.handleIgnoredProperty(jsonParser, deserializationContext, usingDefault, nextFieldName);
            }
            else if (this._anySetter == null) {
                tokenBuffer.writeFieldName(nextFieldName);
                tokenBuffer.copyCurrentStructure(jsonParser);
            }
            else {
                final TokenBuffer copyOfValue = TokenBuffer.asCopyOfValue(jsonParser);
                tokenBuffer.writeFieldName(nextFieldName);
                tokenBuffer.append(copyOfValue);
                try {
                    this._anySetter.deserializeAndSet(copyOfValue.asParserOnFirstToken(), deserializationContext, usingDefault, nextFieldName);
                }
                catch (Exception ex2) {
                    this.wrapAndThrow(ex2, usingDefault, nextFieldName, deserializationContext);
                }
            }
        }
        tokenBuffer.writeEndObject();
        this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, usingDefault, tokenBuffer);
        return usingDefault;
    }
    
    protected Object deserializeWithUnwrapped(final JsonParser jsonParser, final DeserializationContext deserializationContext, final Object o) throws IOException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonToken = jsonParser.nextToken();
        }
        final TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
        tokenBuffer.writeStartObject();
        final Class<?> clazz = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        while (jsonToken == JsonToken.FIELD_NAME) {
            final String currentName = jsonParser.getCurrentName();
            final SettableBeanProperty find = this._beanProperties.find(currentName);
            jsonParser.nextToken();
            if (find != null) {
                if (clazz != null && !find.visibleInView(clazz)) {
                    jsonParser.skipChildren();
                }
                else {
                    try {
                        find.deserializeAndSet(jsonParser, deserializationContext, o);
                    }
                    catch (Exception ex) {
                        this.wrapAndThrow(ex, o, currentName, deserializationContext);
                    }
                }
            }
            else if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                this.handleIgnoredProperty(jsonParser, deserializationContext, o, currentName);
            }
            else if (this._anySetter == null) {
                tokenBuffer.writeFieldName(currentName);
                tokenBuffer.copyCurrentStructure(jsonParser);
            }
            else {
                final TokenBuffer copyOfValue = TokenBuffer.asCopyOfValue(jsonParser);
                tokenBuffer.writeFieldName(currentName);
                tokenBuffer.append(copyOfValue);
                try {
                    this._anySetter.deserializeAndSet(copyOfValue.asParserOnFirstToken(), deserializationContext, o, currentName);
                }
                catch (Exception ex2) {
                    this.wrapAndThrow(ex2, o, currentName, deserializationContext);
                }
            }
            jsonToken = jsonParser.nextToken();
        }
        tokenBuffer.writeEndObject();
        this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, o, tokenBuffer);
        return o;
    }
    
    protected Object deserializeUsingPropertyBasedWithUnwrapped(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        final PropertyValueBuffer startBuilding = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, this._objectIdReader);
        final TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
        tokenBuffer.writeStartObject();
        for (JsonToken jsonToken = jsonParser.getCurrentToken(); jsonToken == JsonToken.FIELD_NAME; jsonToken = jsonParser.nextToken()) {
            final String currentName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            final SettableBeanProperty creatorProperty = propertyBasedCreator.findCreatorProperty(currentName);
            if (creatorProperty != null) {
                if (startBuilding.assignParameter(creatorProperty, this._deserializeWithErrorWrapping(jsonParser, deserializationContext, creatorProperty))) {
                    JsonToken jsonToken2 = jsonParser.nextToken();
                    Object currentValue;
                    try {
                        currentValue = propertyBasedCreator.build(deserializationContext, startBuilding);
                    }
                    catch (Exception ex) {
                        currentValue = this.wrapInstantiationProblem(ex, deserializationContext);
                    }
                    jsonParser.setCurrentValue(currentValue);
                    while (jsonToken2 == JsonToken.FIELD_NAME) {
                        jsonParser.nextToken();
                        tokenBuffer.copyCurrentStructure(jsonParser);
                        jsonToken2 = jsonParser.nextToken();
                    }
                    tokenBuffer.writeEndObject();
                    if (currentValue.getClass() != this._beanType.getRawClass()) {
                        deserializationContext.reportInputMismatch(creatorProperty, "Cannot create polymorphic instances with unwrapped values", new Object[0]);
                        return null;
                    }
                    return this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, currentValue, tokenBuffer);
                }
            }
            else if (!startBuilding.readIdProperty(currentName)) {
                final SettableBeanProperty find = this._beanProperties.find(currentName);
                if (find != null) {
                    startBuilding.bufferProperty(find, this._deserializeWithErrorWrapping(jsonParser, deserializationContext, find));
                }
                else if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                    this.handleIgnoredProperty(jsonParser, deserializationContext, this.handledType(), currentName);
                }
                else if (this._anySetter == null) {
                    tokenBuffer.writeFieldName(currentName);
                    tokenBuffer.copyCurrentStructure(jsonParser);
                }
                else {
                    final TokenBuffer copyOfValue = TokenBuffer.asCopyOfValue(jsonParser);
                    tokenBuffer.writeFieldName(currentName);
                    tokenBuffer.append(copyOfValue);
                    try {
                        startBuilding.bufferAnyProperty(this._anySetter, currentName, this._anySetter.deserialize(copyOfValue.asParserOnFirstToken(), deserializationContext));
                    }
                    catch (Exception ex2) {
                        this.wrapAndThrow(ex2, this._beanType.getRawClass(), currentName, deserializationContext);
                    }
                }
            }
        }
        Object build;
        try {
            build = propertyBasedCreator.build(deserializationContext, startBuilding);
        }
        catch (Exception ex3) {
            this.wrapInstantiationProblem(ex3, deserializationContext);
            return null;
        }
        return this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, build, tokenBuffer);
    }
    
    protected Object deserializeWithExternalTypeId(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (this._propertyBasedCreator != null) {
            return this.deserializeUsingPropertyBasedWithExternalTypeId(jsonParser, deserializationContext);
        }
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        return this.deserializeWithExternalTypeId(jsonParser, deserializationContext, this._valueInstantiator.createUsingDefault(deserializationContext));
    }
    
    protected Object deserializeWithExternalTypeId(final JsonParser jsonParser, final DeserializationContext deserializationContext, final Object o) throws IOException {
        final Class<?> clazz = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        final ExternalTypeHandler start = this._externalTypeIdHandler.start();
        for (JsonToken jsonToken = jsonParser.getCurrentToken(); jsonToken == JsonToken.FIELD_NAME; jsonToken = jsonParser.nextToken()) {
            final String currentName = jsonParser.getCurrentName();
            final JsonToken nextToken = jsonParser.nextToken();
            final SettableBeanProperty find = this._beanProperties.find(currentName);
            if (find != null) {
                if (nextToken.isScalarValue()) {
                    start.handleTypePropertyValue(jsonParser, deserializationContext, currentName, o);
                }
                if (clazz != null && !find.visibleInView(clazz)) {
                    jsonParser.skipChildren();
                }
                else {
                    try {
                        find.deserializeAndSet(jsonParser, deserializationContext, o);
                    }
                    catch (Exception ex) {
                        this.wrapAndThrow(ex, o, currentName, deserializationContext);
                    }
                }
            }
            else if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                this.handleIgnoredProperty(jsonParser, deserializationContext, o, currentName);
            }
            else if (!start.handlePropertyValue(jsonParser, deserializationContext, currentName, o)) {
                if (this._anySetter != null) {
                    try {
                        this._anySetter.deserializeAndSet(jsonParser, deserializationContext, o, currentName);
                    }
                    catch (Exception ex2) {
                        this.wrapAndThrow(ex2, o, currentName, deserializationContext);
                    }
                }
                else {
                    this.handleUnknownProperty(jsonParser, deserializationContext, o, currentName);
                }
            }
        }
        return start.complete(jsonParser, deserializationContext, o);
    }
    
    protected Object deserializeUsingPropertyBasedWithExternalTypeId(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final ExternalTypeHandler start = this._externalTypeIdHandler.start();
        final PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        final PropertyValueBuffer startBuilding = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, this._objectIdReader);
        final TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
        tokenBuffer.writeStartObject();
        for (JsonToken jsonToken = jsonParser.getCurrentToken(); jsonToken == JsonToken.FIELD_NAME; jsonToken = jsonParser.nextToken()) {
            final String currentName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            final SettableBeanProperty creatorProperty = propertyBasedCreator.findCreatorProperty(currentName);
            if (creatorProperty != null) {
                if (!start.handlePropertyValue(jsonParser, deserializationContext, currentName, null)) {
                    if (startBuilding.assignParameter(creatorProperty, this._deserializeWithErrorWrapping(jsonParser, deserializationContext, creatorProperty))) {
                        JsonToken jsonToken2 = jsonParser.nextToken();
                        Object build;
                        try {
                            build = propertyBasedCreator.build(deserializationContext, startBuilding);
                        }
                        catch (Exception ex) {
                            this.wrapAndThrow(ex, this._beanType.getRawClass(), currentName, deserializationContext);
                            continue;
                        }
                        while (jsonToken2 == JsonToken.FIELD_NAME) {
                            jsonParser.nextToken();
                            tokenBuffer.copyCurrentStructure(jsonParser);
                            jsonToken2 = jsonParser.nextToken();
                        }
                        if (build.getClass() != this._beanType.getRawClass()) {
                            return deserializationContext.reportBadDefinition(this._beanType, String.format("Cannot create polymorphic instances with external type ids (%s -> %s)", this._beanType, build.getClass()));
                        }
                        return start.complete(jsonParser, deserializationContext, build);
                    }
                }
            }
            else if (!startBuilding.readIdProperty(currentName)) {
                final SettableBeanProperty find = this._beanProperties.find(currentName);
                if (find != null) {
                    startBuilding.bufferProperty(find, find.deserialize(jsonParser, deserializationContext));
                }
                else if (!start.handlePropertyValue(jsonParser, deserializationContext, currentName, null)) {
                    if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                        this.handleIgnoredProperty(jsonParser, deserializationContext, this.handledType(), currentName);
                    }
                    else if (this._anySetter != null) {
                        startBuilding.bufferAnyProperty(this._anySetter, currentName, this._anySetter.deserialize(jsonParser, deserializationContext));
                    }
                }
            }
        }
        tokenBuffer.writeEndObject();
        try {
            return start.complete(jsonParser, deserializationContext, startBuilding, propertyBasedCreator);
        }
        catch (Exception ex2) {
            return this.wrapInstantiationProblem(ex2, deserializationContext);
        }
    }
    
    protected Exception _creatorReturnedNullException() {
        if (this._nullFromCreator == null) {
            this._nullFromCreator = new NullPointerException("JSON Creator returned null");
        }
        return this._nullFromCreator;
    }
    
    @Override
    public BeanDeserializerBase withIgnorableProperties(final Set set) {
        return this.withIgnorableProperties((Set<String>)set);
    }
    
    @Override
    public BeanDeserializerBase withObjectIdReader(final ObjectIdReader objectIdReader) {
        return this.withObjectIdReader(objectIdReader);
    }
    
    static class BeanReferring extends ReadableObjectId.Referring
    {
        private final DeserializationContext _context;
        private final SettableBeanProperty _prop;
        private Object _bean;
        
        BeanReferring(final DeserializationContext context, final UnresolvedForwardReference unresolvedForwardReference, final JavaType javaType, final PropertyValueBuffer propertyValueBuffer, final SettableBeanProperty prop) {
            super(unresolvedForwardReference, javaType);
            this._context = context;
            this._prop = prop;
        }
        
        public void setBean(final Object bean) {
            this._bean = bean;
        }
        
        @Override
        public void handleResolvedForwardReference(final Object o, final Object o2) throws IOException {
            if (this._bean == null) {
                this._context.reportInputMismatch(this._prop, "Cannot resolve ObjectId forward reference using property '%s' (of type %s): Bean not yet resolved", this._prop.getName(), this._prop.getDeclaringClass().getName());
            }
            this._prop.set(this._bean, o2);
        }
    }
}
