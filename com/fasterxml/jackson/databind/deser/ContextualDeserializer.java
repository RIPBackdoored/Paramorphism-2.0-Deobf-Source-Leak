package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.*;

public interface ContextualDeserializer
{
    JsonDeserializer<?> createContextual(final DeserializationContext p0, final BeanProperty p1) throws JsonMappingException;
}
