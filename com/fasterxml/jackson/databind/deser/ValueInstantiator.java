package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import java.io.IOException;

public abstract class ValueInstantiator {
   public ValueInstantiator() {
      super();
   }

   public Class getValueClass() {
      return Object.class;
   }

   public String getValueTypeDesc() {
      Class var1 = this.getValueClass();
      return var1 == null ? "UNKNOWN" : var1.getName();
   }

   public boolean canInstantiate() {
      return this.canCreateUsingDefault() || this.canCreateUsingDelegate() || this.canCreateUsingArrayDelegate() || this.canCreateFromObjectWith() || this.canCreateFromString() || this.canCreateFromInt() || this.canCreateFromLong() || this.canCreateFromDouble() || this.canCreateFromBoolean();
   }

   public boolean canCreateFromString() {
      return false;
   }

   public boolean canCreateFromInt() {
      return false;
   }

   public boolean canCreateFromLong() {
      return false;
   }

   public boolean canCreateFromDouble() {
      return false;
   }

   public boolean canCreateFromBoolean() {
      return false;
   }

   public boolean canCreateUsingDefault() {
      return this.getDefaultCreator() != null;
   }

   public boolean canCreateUsingDelegate() {
      return false;
   }

   public boolean canCreateUsingArrayDelegate() {
      return false;
   }

   public boolean canCreateFromObjectWith() {
      return false;
   }

   public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig var1) {
      return null;
   }

   public JavaType getDelegateType(DeserializationConfig var1) {
      return null;
   }

   public JavaType getArrayDelegateType(DeserializationConfig var1) {
      return null;
   }

   public Object createUsingDefault(DeserializationContext var1) throws IOException {
      return var1.handleMissingInstantiator(this.getValueClass(), this, (JsonParser)null, "no default no-arguments constructor found");
   }

   public Object createFromObjectWith(DeserializationContext var1, Object[] var2) throws IOException {
      return var1.handleMissingInstantiator(this.getValueClass(), this, (JsonParser)null, "no creator with arguments specified");
   }

   public Object createFromObjectWith(DeserializationContext var1, SettableBeanProperty[] var2, PropertyValueBuffer var3) throws IOException {
      return this.createFromObjectWith(var1, var3.getParameters(var2));
   }

   public Object createUsingDelegate(DeserializationContext var1, Object var2) throws IOException {
      return var1.handleMissingInstantiator(this.getValueClass(), this, (JsonParser)null, "no delegate creator specified");
   }

   public Object createUsingArrayDelegate(DeserializationContext var1, Object var2) throws IOException {
      return var1.handleMissingInstantiator(this.getValueClass(), this, (JsonParser)null, "no array delegate creator specified");
   }

   public Object createFromString(DeserializationContext var1, String var2) throws IOException {
      return this._createFromStringFallbacks(var1, var2);
   }

   public Object createFromInt(DeserializationContext var1, int var2) throws IOException {
      return var1.handleMissingInstantiator(this.getValueClass(), this, (JsonParser)null, "no int/Int-argument constructor/factory method to deserialize from Number value (%s)", var2);
   }

   public Object createFromLong(DeserializationContext var1, long var2) throws IOException {
      return var1.handleMissingInstantiator(this.getValueClass(), this, (JsonParser)null, "no long/Long-argument constructor/factory method to deserialize from Number value (%s)", var2);
   }

   public Object createFromDouble(DeserializationContext var1, double var2) throws IOException {
      return var1.handleMissingInstantiator(this.getValueClass(), this, (JsonParser)null, "no double/Double-argument constructor/factory method to deserialize from Number value (%s)", var2);
   }

   public Object createFromBoolean(DeserializationContext var1, boolean var2) throws IOException {
      return var1.handleMissingInstantiator(this.getValueClass(), this, (JsonParser)null, "no boolean/Boolean-argument constructor/factory method to deserialize from boolean value (%s)", var2);
   }

   public AnnotatedWithParams getDefaultCreator() {
      return null;
   }

   public AnnotatedWithParams getDelegateCreator() {
      return null;
   }

   public AnnotatedWithParams getArrayDelegateCreator() {
      return null;
   }

   public AnnotatedWithParams getWithArgsCreator() {
      return null;
   }

   public AnnotatedParameter getIncompleteParameter() {
      return null;
   }

   protected Object _createFromStringFallbacks(DeserializationContext var1, String var2) throws IOException {
      if (this.canCreateFromBoolean()) {
         String var3 = var2.trim();
         if ("true".equals(var3)) {
            return this.createFromBoolean(var1, true);
         }

         if ("false".equals(var3)) {
            return this.createFromBoolean(var1, false);
         }
      }

      return var2.length() == 0 && var1.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) ? null : var1.handleMissingInstantiator(this.getValueClass(), this, var1.getParser(), "no String-argument constructor/factory method to deserialize from String value ('%s')", var2);
   }
}
