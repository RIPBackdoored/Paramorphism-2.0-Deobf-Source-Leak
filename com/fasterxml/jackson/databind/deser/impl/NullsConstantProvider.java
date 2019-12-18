package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.deser.*;
import java.io.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.*;

public class NullsConstantProvider implements NullValueProvider, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final NullsConstantProvider SKIPPER;
    private static final NullsConstantProvider NULLER;
    protected final Object _nullValue;
    protected final AccessPattern _access;
    
    protected NullsConstantProvider(final Object nullValue) {
        super();
        this._nullValue = nullValue;
        this._access = ((this._nullValue == null) ? AccessPattern.ALWAYS_NULL : AccessPattern.CONSTANT);
    }
    
    public static NullsConstantProvider skipper() {
        return NullsConstantProvider.SKIPPER;
    }
    
    public static NullsConstantProvider nuller() {
        return NullsConstantProvider.NULLER;
    }
    
    public static NullsConstantProvider forValue(final Object o) {
        if (o == null) {
            return NullsConstantProvider.NULLER;
        }
        return new NullsConstantProvider(o);
    }
    
    public static boolean isSkipper(final NullValueProvider nullValueProvider) {
        return nullValueProvider == NullsConstantProvider.SKIPPER;
    }
    
    public static boolean isNuller(final NullValueProvider nullValueProvider) {
        return nullValueProvider == NullsConstantProvider.NULLER;
    }
    
    @Override
    public AccessPattern getNullAccessPattern() {
        return this._access;
    }
    
    @Override
    public Object getNullValue(final DeserializationContext deserializationContext) {
        return this._nullValue;
    }
    
    static {
        SKIPPER = new NullsConstantProvider(null);
        NULLER = new NullsConstantProvider(null);
    }
}
