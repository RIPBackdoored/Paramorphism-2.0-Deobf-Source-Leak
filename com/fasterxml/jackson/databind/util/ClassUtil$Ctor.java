package com.fasterxml.jackson.databind.util;

import java.lang.reflect.*;
import java.lang.annotation.*;

public static final class Ctor
{
    public final Constructor<?> _ctor;
    private Annotation[] _annotations;
    private Annotation[][] _paramAnnotations;
    private int _paramCount;
    
    public Ctor(final Constructor<?> ctor) {
        super();
        this._paramCount = -1;
        this._ctor = ctor;
    }
    
    public Constructor<?> getConstructor() {
        return this._ctor;
    }
    
    public int getParamCount() {
        int paramCount = this._paramCount;
        if (paramCount < 0) {
            paramCount = this._ctor.getParameterTypes().length;
            this._paramCount = paramCount;
        }
        return paramCount;
    }
    
    public Class<?> getDeclaringClass() {
        return this._ctor.getDeclaringClass();
    }
    
    public Annotation[] getDeclaredAnnotations() {
        Annotation[] annotations = this._annotations;
        if (annotations == null) {
            annotations = this._ctor.getDeclaredAnnotations();
            this._annotations = annotations;
        }
        return annotations;
    }
    
    public Annotation[][] getParameterAnnotations() {
        Annotation[][] paramAnnotations = this._paramAnnotations;
        if (paramAnnotations == null) {
            paramAnnotations = this._ctor.getParameterAnnotations();
            this._paramAnnotations = paramAnnotations;
        }
        return paramAnnotations;
    }
}
