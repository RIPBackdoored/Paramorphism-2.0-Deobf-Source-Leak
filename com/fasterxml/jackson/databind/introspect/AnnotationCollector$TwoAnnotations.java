package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.*;
import java.io.*;
import java.lang.annotation.*;

public static class TwoAnnotations implements Annotations, Serializable
{
    private static final long serialVersionUID = 1L;
    private final Class<?> _type1;
    private final Class<?> _type2;
    private final Annotation _value1;
    private final Annotation _value2;
    
    public TwoAnnotations(final Class<?> type1, final Annotation value1, final Class<?> type2, final Annotation value2) {
        super();
        this._type1 = type1;
        this._value1 = value1;
        this._type2 = type2;
        this._value2 = value2;
    }
    
    @Override
    public <A extends Annotation> A get(final Class<A> clazz) {
        if (this._type1 == clazz) {
            return (A)this._value1;
        }
        if (this._type2 == clazz) {
            return (A)this._value2;
        }
        return null;
    }
    
    @Override
    public boolean has(final Class<?> clazz) {
        return this._type1 == clazz || this._type2 == clazz;
    }
    
    @Override
    public boolean hasOneOf(final Class<? extends Annotation>[] array) {
        for (final Class<? extends Annotation> clazz : array) {
            if (clazz == this._type1 || clazz == this._type2) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int size() {
        return 2;
    }
}
