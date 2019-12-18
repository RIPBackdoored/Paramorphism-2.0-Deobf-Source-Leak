package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface JsonFormatVisitorWrapper extends JsonFormatVisitorWithSerializerProvider {
   JsonObjectFormatVisitor expectObjectFormat(JavaType var1) throws JsonMappingException;

   JsonArrayFormatVisitor expectArrayFormat(JavaType var1) throws JsonMappingException;

   JsonStringFormatVisitor expectStringFormat(JavaType var1) throws JsonMappingException;

   JsonNumberFormatVisitor expectNumberFormat(JavaType var1) throws JsonMappingException;

   JsonIntegerFormatVisitor expectIntegerFormat(JavaType var1) throws JsonMappingException;

   JsonBooleanFormatVisitor expectBooleanFormat(JavaType var1) throws JsonMappingException;

   JsonNullFormatVisitor expectNullFormat(JavaType var1) throws JsonMappingException;

   JsonAnyFormatVisitor expectAnyFormat(JavaType var1) throws JsonMappingException;

   JsonMapFormatVisitor expectMapFormat(JavaType var1) throws JsonMappingException;
}
