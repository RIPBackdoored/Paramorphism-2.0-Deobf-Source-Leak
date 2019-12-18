package com.fasterxml.jackson.databind.introspect;

import java.lang.reflect.*;

private static final class MethodBuilder
{
    public TypeResolutionContext typeContext;
    public Method method;
    public AnnotationCollector annotations;
    
    public MethodBuilder(final TypeResolutionContext typeContext, final Method method, final AnnotationCollector annotations) {
        super();
        this.typeContext = typeContext;
        this.method = method;
        this.annotations = annotations;
    }
    
    public AnnotatedMethod build() {
        if (this.method == null) {
            return null;
        }
        return new AnnotatedMethod(this.typeContext, this.method, this.annotations.asAnnotationMap(), null);
    }
}
