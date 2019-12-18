package com.fasterxml.jackson.databind.introspect;

import java.lang.annotation.*;
import com.fasterxml.jackson.databind.util.*;

static class OneCollector extends AnnotationCollector
{
    private Class<?> _type;
    private Annotation _value;
    
    public OneCollector(final Object o, final Class<?> type, final Annotation value) {
        super(o);
        this._type = type;
        this._value = value;
    }
    
    @Override
    public Annotations asAnnotations() {
        return new OneAnnotation(this._type, this._value);
    }
    
    @Override
    public AnnotationMap asAnnotationMap() {
        return AnnotationMap.of(this._type, this._value);
    }
    
    @Override
    public boolean isPresent(final Annotation annotation) {
        return annotation.annotationType() == this._type;
    }
    
    @Override
    public AnnotationCollector addOrOverride(final Annotation value) {
        final Class<? extends Annotation> annotationType = value.annotationType();
        if (this._type == annotationType) {
            this._value = value;
            return this;
        }
        return new NCollector(this._data, this._type, this._value, annotationType, value);
    }
}
