package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public interface JsonSerializable {
   void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException;

   void serializeWithType(JsonGenerator var1, SerializerProvider var2, TypeSerializer var3) throws IOException;
}
