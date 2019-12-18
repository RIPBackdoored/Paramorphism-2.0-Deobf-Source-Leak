package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.cfg.*;
import com.fasterxml.jackson.databind.introspect.*;

public abstract static class PropertyNamingStrategyBase extends PropertyNamingStrategy
{
    public PropertyNamingStrategyBase() {
        super();
    }
    
    @Override
    public String nameForField(final MapperConfig<?> mapperConfig, final AnnotatedField annotatedField, final String s) {
        return this.translate(s);
    }
    
    @Override
    public String nameForGetterMethod(final MapperConfig<?> mapperConfig, final AnnotatedMethod annotatedMethod, final String s) {
        return this.translate(s);
    }
    
    @Override
    public String nameForSetterMethod(final MapperConfig<?> mapperConfig, final AnnotatedMethod annotatedMethod, final String s) {
        return this.translate(s);
    }
    
    @Override
    public String nameForConstructorParameter(final MapperConfig<?> mapperConfig, final AnnotatedParameter annotatedParameter, final String s) {
        return this.translate(s);
    }
    
    public abstract String translate(final String p0);
}
