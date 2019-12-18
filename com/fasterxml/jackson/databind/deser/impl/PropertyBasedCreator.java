package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.cfg.*;
import com.fasterxml.jackson.databind.*;
import java.util.*;
import com.fasterxml.jackson.core.*;
import java.io.*;

public final class PropertyBasedCreator
{
    protected final int _propertyCount;
    protected final ValueInstantiator _valueInstantiator;
    protected final HashMap<String, SettableBeanProperty> _propertyLookup;
    protected final SettableBeanProperty[] _allProperties;
    
    protected PropertyBasedCreator(final DeserializationContext deserializationContext, final ValueInstantiator valueInstantiator, final SettableBeanProperty[] array, final boolean b, final boolean b2) {
        super();
        this._valueInstantiator = valueInstantiator;
        if (b) {
            this._propertyLookup = new CaseInsensitiveMap();
        }
        else {
            this._propertyLookup = new HashMap<String, SettableBeanProperty>();
        }
        final int length = array.length;
        this._propertyCount = length;
        this._allProperties = new SettableBeanProperty[length];
        if (b2) {
            final DeserializationConfig config = deserializationContext.getConfig();
            for (final SettableBeanProperty settableBeanProperty : array) {
                if (!settableBeanProperty.isIgnorable()) {
                    final List<PropertyName> aliases = settableBeanProperty.findAliases(config);
                    if (!aliases.isEmpty()) {
                        final Iterator<PropertyName> iterator = aliases.iterator();
                        while (iterator.hasNext()) {
                            this._propertyLookup.put(iterator.next().getSimpleName(), settableBeanProperty);
                        }
                    }
                }
            }
        }
        for (int j = 0; j < length; ++j) {
            final SettableBeanProperty settableBeanProperty2 = array[j];
            this._allProperties[j] = settableBeanProperty2;
            if (!settableBeanProperty2.isIgnorable()) {
                this._propertyLookup.put(settableBeanProperty2.getName(), settableBeanProperty2);
            }
        }
    }
    
    public static PropertyBasedCreator construct(final DeserializationContext deserializationContext, final ValueInstantiator valueInstantiator, final SettableBeanProperty[] array, final BeanPropertyMap beanPropertyMap) throws JsonMappingException {
        final int length = array.length;
        final SettableBeanProperty[] array2 = new SettableBeanProperty[length];
        for (int i = 0; i < length; ++i) {
            SettableBeanProperty withValueDeserializer = array[i];
            if (!withValueDeserializer.hasValueDeserializer()) {
                withValueDeserializer = withValueDeserializer.withValueDeserializer(deserializationContext.findContextualValueDeserializer(withValueDeserializer.getType(), withValueDeserializer));
            }
            array2[i] = withValueDeserializer;
        }
        return new PropertyBasedCreator(deserializationContext, valueInstantiator, array2, beanPropertyMap.isCaseInsensitive(), beanPropertyMap.hasAliases());
    }
    
    public static PropertyBasedCreator construct(final DeserializationContext deserializationContext, final ValueInstantiator valueInstantiator, final SettableBeanProperty[] array, final boolean b) throws JsonMappingException {
        final int length = array.length;
        final SettableBeanProperty[] array2 = new SettableBeanProperty[length];
        for (int i = 0; i < length; ++i) {
            SettableBeanProperty withValueDeserializer = array[i];
            if (!withValueDeserializer.hasValueDeserializer()) {
                withValueDeserializer = withValueDeserializer.withValueDeserializer(deserializationContext.findContextualValueDeserializer(withValueDeserializer.getType(), withValueDeserializer));
            }
            array2[i] = withValueDeserializer;
        }
        return new PropertyBasedCreator(deserializationContext, valueInstantiator, array2, b, false);
    }
    
    @Deprecated
    public static PropertyBasedCreator construct(final DeserializationContext deserializationContext, final ValueInstantiator valueInstantiator, final SettableBeanProperty[] array) throws JsonMappingException {
        return construct(deserializationContext, valueInstantiator, array, deserializationContext.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES));
    }
    
    public Collection<SettableBeanProperty> properties() {
        return this._propertyLookup.values();
    }
    
    public SettableBeanProperty findCreatorProperty(final String s) {
        return this._propertyLookup.get(s);
    }
    
    public SettableBeanProperty findCreatorProperty(final int n) {
        for (final SettableBeanProperty settableBeanProperty : this._propertyLookup.values()) {
            if (settableBeanProperty.getPropertyIndex() == n) {
                return settableBeanProperty;
            }
        }
        return null;
    }
    
    public PropertyValueBuffer startBuilding(final JsonParser jsonParser, final DeserializationContext deserializationContext, final ObjectIdReader objectIdReader) {
        return new PropertyValueBuffer(jsonParser, deserializationContext, this._propertyCount, objectIdReader);
    }
    
    public Object build(final DeserializationContext deserializationContext, final PropertyValueBuffer propertyValueBuffer) throws IOException {
        Object o = this._valueInstantiator.createFromObjectWith(deserializationContext, this._allProperties, propertyValueBuffer);
        if (o != null) {
            o = propertyValueBuffer.handleIdValue(deserializationContext, o);
            for (PropertyValue propertyValue = propertyValueBuffer.buffered(); propertyValue != null; propertyValue = propertyValue.next) {
                propertyValue.assign(o);
            }
        }
        return o;
    }
    
    static class CaseInsensitiveMap extends HashMap<String, SettableBeanProperty>
    {
        private static final long serialVersionUID = 1L;
        
        CaseInsensitiveMap() {
            super();
        }
        
        @Override
        public SettableBeanProperty get(final Object o) {
            return super.get(((String)o).toLowerCase());
        }
        
        @Override
        public SettableBeanProperty put(String lowerCase, final SettableBeanProperty settableBeanProperty) {
            lowerCase = lowerCase.toLowerCase();
            return super.put(lowerCase, settableBeanProperty);
        }
        
        @Override
        public Object put(final Object o, final Object o2) {
            return this.put((String)o, (SettableBeanProperty)o2);
        }
        
        @Override
        public Object get(final Object o) {
            return this.get(o);
        }
    }
}
