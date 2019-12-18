package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

public final class CreatorCandidate
{
    protected final AnnotationIntrospector _intr;
    protected final AnnotatedWithParams _creator;
    protected final int _paramCount;
    protected final Param[] _params;
    
    protected CreatorCandidate(final AnnotationIntrospector intr, final AnnotatedWithParams creator, final Param[] params, final int paramCount) {
        super();
        this._intr = intr;
        this._creator = creator;
        this._params = params;
        this._paramCount = paramCount;
    }
    
    public static CreatorCandidate construct(final AnnotationIntrospector annotationIntrospector, final AnnotatedWithParams annotatedWithParams, final BeanPropertyDefinition[] array) {
        final int parameterCount = annotatedWithParams.getParameterCount();
        final Param[] array2 = new Param[parameterCount];
        for (int i = 0; i < parameterCount; ++i) {
            final AnnotatedParameter parameter = annotatedWithParams.getParameter(i);
            array2[i] = new Param(parameter, (array == null) ? null : array[i], annotationIntrospector.findInjectableValue(parameter));
        }
        return new CreatorCandidate(annotationIntrospector, annotatedWithParams, array2, parameterCount);
    }
    
    public AnnotatedWithParams creator() {
        return this._creator;
    }
    
    public int paramCount() {
        return this._paramCount;
    }
    
    public JacksonInject.Value injection(final int n) {
        return this._params[n].injection;
    }
    
    public AnnotatedParameter parameter(final int n) {
        return this._params[n].annotated;
    }
    
    public BeanPropertyDefinition propertyDef(final int n) {
        return this._params[n].propDef;
    }
    
    public PropertyName paramName(final int n) {
        final BeanPropertyDefinition propDef = this._params[n].propDef;
        if (propDef != null) {
            return propDef.getFullName();
        }
        return null;
    }
    
    public PropertyName explicitParamName(final int n) {
        final BeanPropertyDefinition propDef = this._params[n].propDef;
        if (propDef != null && propDef.isExplicitlyNamed()) {
            return propDef.getFullName();
        }
        return null;
    }
    
    public PropertyName findImplicitParamName(final int n) {
        final String implicitPropertyName = this._intr.findImplicitPropertyName(this._params[n].annotated);
        if (implicitPropertyName != null && !implicitPropertyName.isEmpty()) {
            return PropertyName.construct(implicitPropertyName);
        }
        return null;
    }
    
    public int findOnlyParamWithoutInjection() {
        int n = -1;
        for (int i = 0; i < this._paramCount; ++i) {
            if (this._params[i].injection == null) {
                if (n >= 0) {
                    return -1;
                }
                n = i;
            }
        }
        return n;
    }
    
    @Override
    public String toString() {
        return this._creator.toString();
    }
    
    public static final class Param
    {
        public final AnnotatedParameter annotated;
        public final BeanPropertyDefinition propDef;
        public final JacksonInject.Value injection;
        
        public Param(final AnnotatedParameter annotated, final BeanPropertyDefinition propDef, final JacksonInject.Value injection) {
            super();
            this.annotated = annotated;
            this.propDef = propDef;
            this.injection = injection;
        }
        
        public PropertyName fullName() {
            if (this.propDef == null) {
                return null;
            }
            return this.propDef.getFullName();
        }
        
        public boolean hasFullName() {
            return this.propDef != null && this.propDef.getFullName().hasSimpleName();
        }
    }
}
