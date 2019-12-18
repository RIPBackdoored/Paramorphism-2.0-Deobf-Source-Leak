package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import java.lang.annotation.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.exc.*;

public class CreatorProperty extends SettableBeanProperty
{
    private static final long serialVersionUID = 1L;
    protected final AnnotatedParameter _annotated;
    protected final Object _injectableValueId;
    protected SettableBeanProperty _fallbackSetter;
    protected final int _creatorIndex;
    protected boolean _ignorable;
    
    public CreatorProperty(final PropertyName propertyName, final JavaType javaType, final PropertyName propertyName2, final TypeDeserializer typeDeserializer, final Annotations annotations, final AnnotatedParameter annotated, final int creatorIndex, final Object injectableValueId, final PropertyMetadata propertyMetadata) {
        super(propertyName, javaType, propertyName2, typeDeserializer, annotations, propertyMetadata);
        this._annotated = annotated;
        this._creatorIndex = creatorIndex;
        this._injectableValueId = injectableValueId;
        this._fallbackSetter = null;
    }
    
    protected CreatorProperty(final CreatorProperty creatorProperty, final PropertyName propertyName) {
        super(creatorProperty, propertyName);
        this._annotated = creatorProperty._annotated;
        this._injectableValueId = creatorProperty._injectableValueId;
        this._fallbackSetter = creatorProperty._fallbackSetter;
        this._creatorIndex = creatorProperty._creatorIndex;
        this._ignorable = creatorProperty._ignorable;
    }
    
    protected CreatorProperty(final CreatorProperty creatorProperty, final JsonDeserializer<?> jsonDeserializer, final NullValueProvider nullValueProvider) {
        super(creatorProperty, jsonDeserializer, nullValueProvider);
        this._annotated = creatorProperty._annotated;
        this._injectableValueId = creatorProperty._injectableValueId;
        this._fallbackSetter = creatorProperty._fallbackSetter;
        this._creatorIndex = creatorProperty._creatorIndex;
        this._ignorable = creatorProperty._ignorable;
    }
    
    @Override
    public SettableBeanProperty withName(final PropertyName propertyName) {
        return new CreatorProperty(this, propertyName);
    }
    
    @Override
    public SettableBeanProperty withValueDeserializer(final JsonDeserializer<?> jsonDeserializer) {
        if (this._valueDeserializer == jsonDeserializer) {
            return this;
        }
        return new CreatorProperty(this, jsonDeserializer, this._nullProvider);
    }
    
    @Override
    public SettableBeanProperty withNullProvider(final NullValueProvider nullValueProvider) {
        return new CreatorProperty(this, this._valueDeserializer, nullValueProvider);
    }
    
    @Override
    public void fixAccess(final DeserializationConfig deserializationConfig) {
        if (this._fallbackSetter != null) {
            this._fallbackSetter.fixAccess(deserializationConfig);
        }
    }
    
    public void setFallbackSetter(final SettableBeanProperty fallbackSetter) {
        this._fallbackSetter = fallbackSetter;
    }
    
    @Override
    public void markAsIgnorable() {
        this._ignorable = true;
    }
    
    @Override
    public boolean isIgnorable() {
        return this._ignorable;
    }
    
    public Object findInjectableValue(final DeserializationContext deserializationContext, final Object o) throws JsonMappingException {
        if (this._injectableValueId == null) {
            deserializationContext.reportBadDefinition(ClassUtil.classOf(o), String.format("Property '%s' (type %s) has no injectable value id configured", this.getName(), this.getClass().getName()));
        }
        return deserializationContext.findInjectableValue(this._injectableValueId, this, o);
    }
    
    public void inject(final DeserializationContext deserializationContext, final Object o) throws IOException {
        this.set(o, this.findInjectableValue(deserializationContext, o));
    }
    
    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> clazz) {
        if (this._annotated == null) {
            return null;
        }
        return this._annotated.getAnnotation(clazz);
    }
    
    @Override
    public AnnotatedMember getMember() {
        return this._annotated;
    }
    
    @Override
    public int getCreatorIndex() {
        return this._creatorIndex;
    }
    
    @Override
    public void deserializeAndSet(final JsonParser jsonParser, final DeserializationContext deserializationContext, final Object o) throws IOException {
        this._verifySetter();
        this._fallbackSetter.set(o, this.deserialize(jsonParser, deserializationContext));
    }
    
    @Override
    public Object deserializeSetAndReturn(final JsonParser jsonParser, final DeserializationContext deserializationContext, final Object o) throws IOException {
        this._verifySetter();
        return this._fallbackSetter.setAndReturn(o, this.deserialize(jsonParser, deserializationContext));
    }
    
    @Override
    public void set(final Object o, final Object o2) throws IOException {
        this._verifySetter();
        this._fallbackSetter.set(o, o2);
    }
    
    @Override
    public Object setAndReturn(final Object o, final Object o2) throws IOException {
        this._verifySetter();
        return this._fallbackSetter.setAndReturn(o, o2);
    }
    
    @Override
    public Object getInjectableValueId() {
        return this._injectableValueId;
    }
    
    @Override
    public String toString() {
        return "[creator property, name '" + this.getName() + "'; inject id '" + this._injectableValueId + "']";
    }
    
    private final void _verifySetter() throws IOException {
        if (this._fallbackSetter == null) {
            this._reportMissingSetter(null, null);
        }
    }
    
    private void _reportMissingSetter(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final String string = "No fallback setter/field defined for creator property '" + this.getName() + "'";
        if (deserializationContext != null) {
            deserializationContext.reportBadDefinition(this.getType(), string);
            return;
        }
        throw InvalidDefinitionException.from(jsonParser, string, this.getType());
    }
}
