package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.databind.*;

public interface Converter<IN, OUT>
{
    OUT convert(final IN p0);
    
    JavaType getInputType(final TypeFactory p0);
    
    JavaType getOutputType(final TypeFactory p0);
    
    public abstract static class None implements Converter<Object, Object>
    {
        public None() {
            super();
        }
    }
}
