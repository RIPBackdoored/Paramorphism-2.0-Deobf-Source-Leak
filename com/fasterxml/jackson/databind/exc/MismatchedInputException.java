package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;
import java.io.*;
import com.fasterxml.jackson.databind.util.*;

public class MismatchedInputException extends JsonMappingException
{
    protected Class<?> _targetType;
    
    protected MismatchedInputException(final JsonParser jsonParser, final String s) {
        this(jsonParser, s, (JavaType)null);
    }
    
    protected MismatchedInputException(final JsonParser jsonParser, final String s, final JsonLocation jsonLocation) {
        super(jsonParser, s, jsonLocation);
    }
    
    protected MismatchedInputException(final JsonParser jsonParser, final String s, final Class<?> targetType) {
        super(jsonParser, s);
        this._targetType = targetType;
    }
    
    protected MismatchedInputException(final JsonParser jsonParser, final String s, final JavaType javaType) {
        super(jsonParser, s);
        this._targetType = ClassUtil.rawClass(javaType);
    }
    
    @Deprecated
    public static MismatchedInputException from(final JsonParser jsonParser, final String s) {
        return from(jsonParser, (Class<?>)null, s);
    }
    
    public static MismatchedInputException from(final JsonParser jsonParser, final JavaType javaType, final String s) {
        return new MismatchedInputException(jsonParser, s, javaType);
    }
    
    public static MismatchedInputException from(final JsonParser jsonParser, final Class<?> clazz, final String s) {
        return new MismatchedInputException(jsonParser, s, clazz);
    }
    
    public MismatchedInputException setTargetType(final JavaType javaType) {
        this._targetType = javaType.getRawClass();
        return this;
    }
    
    public Class<?> getTargetType() {
        return this._targetType;
    }
}
