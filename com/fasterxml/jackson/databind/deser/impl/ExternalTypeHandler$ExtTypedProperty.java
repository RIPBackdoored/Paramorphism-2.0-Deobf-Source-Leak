package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.jsontype.*;

private static final class ExtTypedProperty
{
    private final SettableBeanProperty _property;
    private final TypeDeserializer _typeDeserializer;
    private final String _typePropertyName;
    private SettableBeanProperty _typeProperty;
    
    public ExtTypedProperty(final SettableBeanProperty property, final TypeDeserializer typeDeserializer) {
        super();
        this._property = property;
        this._typeDeserializer = typeDeserializer;
        this._typePropertyName = typeDeserializer.getPropertyName();
    }
    
    public void linkTypeProperty(final SettableBeanProperty typeProperty) {
        this._typeProperty = typeProperty;
    }
    
    public boolean hasTypePropertyName(final String s) {
        return s.equals(this._typePropertyName);
    }
    
    public boolean hasDefaultType() {
        return this._typeDeserializer.getDefaultImpl() != null;
    }
    
    public String getDefaultTypeId() {
        final Class<?> defaultImpl = this._typeDeserializer.getDefaultImpl();
        if (defaultImpl == null) {
            return null;
        }
        return this._typeDeserializer.getTypeIdResolver().idFromValueAndType(null, defaultImpl);
    }
    
    public String getTypePropertyName() {
        return this._typePropertyName;
    }
    
    public SettableBeanProperty getProperty() {
        return this._property;
    }
    
    public SettableBeanProperty getTypeProperty() {
        return this._typeProperty;
    }
}
