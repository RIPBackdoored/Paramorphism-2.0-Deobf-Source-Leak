package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;

public class ValueInjector extends BeanProperty.Std
{
    private static final long serialVersionUID = 1L;
    protected final Object _valueId;
    
    public ValueInjector(final PropertyName propertyName, final JavaType javaType, final AnnotatedMember annotatedMember, final Object valueId) {
        super(propertyName, javaType, null, annotatedMember, PropertyMetadata.STD_OPTIONAL);
        this._valueId = valueId;
    }
    
    @Deprecated
    public ValueInjector(final PropertyName propertyName, final JavaType javaType, final Annotations annotations, final AnnotatedMember annotatedMember, final Object o) {
        this(propertyName, javaType, annotatedMember, o);
    }
    
    public Object findValue(final DeserializationContext deserializationContext, final Object o) throws JsonMappingException {
        return deserializationContext.findInjectableValue(this._valueId, this, o);
    }
    
    public void inject(final DeserializationContext deserializationContext, final Object o) throws IOException {
        this._member.setValue(o, this.findValue(deserializationContext, o));
    }
}
