package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;

public abstract class TypeIdResolverBase implements TypeIdResolver
{
    protected final TypeFactory _typeFactory;
    protected final JavaType _baseType;
    
    protected TypeIdResolverBase() {
        this(null, null);
    }
    
    protected TypeIdResolverBase(final JavaType baseType, final TypeFactory typeFactory) {
        super();
        this._baseType = baseType;
        this._typeFactory = typeFactory;
    }
    
    @Override
    public void init(final JavaType javaType) {
    }
    
    @Override
    public String idFromBaseType() {
        return this.idFromValueAndType(null, this._baseType.getRawClass());
    }
    
    @Override
    public JavaType typeFromId(final DatabindContext databindContext, final String s) throws IOException {
        throw new IllegalStateException("Sub-class " + this.getClass().getName() + " MUST implement " + "`typeFromId(DatabindContext,String)");
    }
    
    @Override
    public String getDescForKnownTypeIds() {
        return null;
    }
}
