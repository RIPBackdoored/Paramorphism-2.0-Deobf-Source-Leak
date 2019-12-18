package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import java.io.IOException;

public abstract class DeserializationProblemHandler {
   public static final Object NOT_HANDLED = new Object();

   public DeserializationProblemHandler() {
      super();
   }

   public boolean handleUnknownProperty(DeserializationContext var1, JsonParser var2, JsonDeserializer var3, Object var4, String var5) throws IOException {
      return false;
   }

   public Object handleWeirdKey(DeserializationContext var1, Class var2, String var3, String var4) throws IOException {
      return NOT_HANDLED;
   }

   public Object handleWeirdStringValue(DeserializationContext var1, Class var2, String var3, String var4) throws IOException {
      return NOT_HANDLED;
   }

   public Object handleWeirdNumberValue(DeserializationContext var1, Class var2, Number var3, String var4) throws IOException {
      return NOT_HANDLED;
   }

   public Object handleWeirdNativeValue(DeserializationContext var1, JavaType var2, Object var3, JsonParser var4) throws IOException {
      return NOT_HANDLED;
   }

   public Object handleUnexpectedToken(DeserializationContext var1, Class var2, JsonToken var3, JsonParser var4, String var5) throws IOException {
      return NOT_HANDLED;
   }

   public Object handleInstantiationProblem(DeserializationContext var1, Class var2, Object var3, Throwable var4) throws IOException {
      return NOT_HANDLED;
   }

   public Object handleMissingInstantiator(DeserializationContext var1, Class var2, ValueInstantiator var3, JsonParser var4, String var5) throws IOException {
      return this.handleMissingInstantiator(var1, var2, var4, var5);
   }

   public JavaType handleUnknownTypeId(DeserializationContext var1, JavaType var2, String var3, TypeIdResolver var4, String var5) throws IOException {
      return null;
   }

   public JavaType handleMissingTypeId(DeserializationContext var1, JavaType var2, TypeIdResolver var3, String var4) throws IOException {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public Object handleMissingInstantiator(DeserializationContext var1, Class var2, JsonParser var3, String var4) throws IOException {
      return NOT_HANDLED;
   }
}
