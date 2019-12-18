package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.core.JsonParser$NumberType;

public interface JsonIntegerFormatVisitor extends JsonValueFormatVisitor {
   void numberType(JsonParser$NumberType var1);
}
