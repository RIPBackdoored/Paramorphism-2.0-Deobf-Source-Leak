package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.*;
import java.io.*;
import com.fasterxml.jackson.core.*;

public class InvalidDefinitionException extends JsonMappingException
{
    protected final JavaType _type;
    protected transient BeanDescription _beanDesc;
    protected transient BeanPropertyDefinition _property;
    
    protected InvalidDefinitionException(final JsonParser jsonParser, final String s, final JavaType type) {
        super(jsonParser, s);
        this._type = type;
        this._beanDesc = null;
        this._property = null;
    }
    
    protected InvalidDefinitionException(final JsonGenerator jsonGenerator, final String s, final JavaType type) {
        super(jsonGenerator, s);
        this._type = type;
        this._beanDesc = null;
        this._property = null;
    }
    
    protected InvalidDefinitionException(final JsonParser jsonParser, final String s, final BeanDescription beanDesc, final BeanPropertyDefinition property) {
        super(jsonParser, s);
        this._type = ((beanDesc == null) ? null : beanDesc.getType());
        this._beanDesc = beanDesc;
        this._property = property;
    }
    
    protected InvalidDefinitionException(final JsonGenerator jsonGenerator, final String s, final BeanDescription beanDesc, final BeanPropertyDefinition property) {
        super(jsonGenerator, s);
        this._type = ((beanDesc == null) ? null : beanDesc.getType());
        this._beanDesc = beanDesc;
        this._property = property;
    }
    
    public static InvalidDefinitionException from(final JsonParser jsonParser, final String s, final BeanDescription beanDescription, final BeanPropertyDefinition beanPropertyDefinition) {
        return new InvalidDefinitionException(jsonParser, s, beanDescription, beanPropertyDefinition);
    }
    
    public static InvalidDefinitionException from(final JsonParser jsonParser, final String s, final JavaType javaType) {
        return new InvalidDefinitionException(jsonParser, s, javaType);
    }
    
    public static InvalidDefinitionException from(final JsonGenerator jsonGenerator, final String s, final BeanDescription beanDescription, final BeanPropertyDefinition beanPropertyDefinition) {
        return new InvalidDefinitionException(jsonGenerator, s, beanDescription, beanPropertyDefinition);
    }
    
    public static InvalidDefinitionException from(final JsonGenerator jsonGenerator, final String s, final JavaType javaType) {
        return new InvalidDefinitionException(jsonGenerator, s, javaType);
    }
    
    public JavaType getType() {
        return this._type;
    }
    
    public BeanDescription getBeanDescription() {
        return this._beanDesc;
    }
    
    public BeanPropertyDefinition getProperty() {
        return this._property;
    }
}
