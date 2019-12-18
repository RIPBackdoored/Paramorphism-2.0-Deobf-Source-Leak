package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.cfg.*;
import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.databind.jsontype.*;
import java.util.*;
import com.fasterxml.jackson.databind.deser.*;

public interface SetupContext
{
    Version getMapperVersion();
    
     <C extends ObjectCodec> C getOwner();
    
    TypeFactory getTypeFactory();
    
    boolean isEnabled(final MapperFeature p0);
    
    boolean isEnabled(final DeserializationFeature p0);
    
    boolean isEnabled(final SerializationFeature p0);
    
    boolean isEnabled(final JsonFactory.Feature p0);
    
    boolean isEnabled(final JsonParser.Feature p0);
    
    boolean isEnabled(final JsonGenerator.Feature p0);
    
    MutableConfigOverride configOverride(final Class<?> p0);
    
    void addDeserializers(final Deserializers p0);
    
    void addKeyDeserializers(final KeyDeserializers p0);
    
    void addSerializers(final Serializers p0);
    
    void addKeySerializers(final Serializers p0);
    
    void addBeanDeserializerModifier(final BeanDeserializerModifier p0);
    
    void addBeanSerializerModifier(final BeanSerializerModifier p0);
    
    void addAbstractTypeResolver(final AbstractTypeResolver p0);
    
    void addTypeModifier(final TypeModifier p0);
    
    void addValueInstantiators(final ValueInstantiators p0);
    
    void setClassIntrospector(final ClassIntrospector p0);
    
    void insertAnnotationIntrospector(final AnnotationIntrospector p0);
    
    void appendAnnotationIntrospector(final AnnotationIntrospector p0);
    
    void registerSubtypes(final Class<?>... p0);
    
    void registerSubtypes(final NamedType... p0);
    
    void registerSubtypes(final Collection<Class<?>> p0);
    
    void setMixInAnnotations(final Class<?> p0, final Class<?> p1);
    
    void addDeserializationProblemHandler(final DeserializationProblemHandler p0);
    
    void setNamingStrategy(final PropertyNamingStrategy p0);
}
