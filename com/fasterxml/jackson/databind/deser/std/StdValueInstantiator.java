package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@JacksonStdImpl
public class StdValueInstantiator extends ValueInstantiator implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final String _valueTypeDesc;
   protected final Class _valueClass;
   protected AnnotatedWithParams _defaultCreator;
   protected AnnotatedWithParams _withArgsCreator;
   protected SettableBeanProperty[] _constructorArguments;
   protected JavaType _delegateType;
   protected AnnotatedWithParams _delegateCreator;
   protected SettableBeanProperty[] _delegateArguments;
   protected JavaType _arrayDelegateType;
   protected AnnotatedWithParams _arrayDelegateCreator;
   protected SettableBeanProperty[] _arrayDelegateArguments;
   protected AnnotatedWithParams _fromStringCreator;
   protected AnnotatedWithParams _fromIntCreator;
   protected AnnotatedWithParams _fromLongCreator;
   protected AnnotatedWithParams _fromDoubleCreator;
   protected AnnotatedWithParams _fromBooleanCreator;
   protected AnnotatedParameter _incompleteParameter;

   /** @deprecated */
   @Deprecated
   public StdValueInstantiator(DeserializationConfig var1, Class var2) {
      super();
      this._valueTypeDesc = ClassUtil.nameOf(var2);
      this._valueClass = var2 == null ? Object.class : var2;
   }

   public StdValueInstantiator(DeserializationConfig var1, JavaType var2) {
      super();
      this._valueTypeDesc = var2 == null ? "UNKNOWN TYPE" : var2.toString();
      this._valueClass = var2 == null ? Object.class : var2.getRawClass();
   }

   protected StdValueInstantiator(StdValueInstantiator var1) {
      super();
      this._valueTypeDesc = var1._valueTypeDesc;
      this._valueClass = var1._valueClass;
      this._defaultCreator = var1._defaultCreator;
      this._constructorArguments = var1._constructorArguments;
      this._withArgsCreator = var1._withArgsCreator;
      this._delegateType = var1._delegateType;
      this._delegateCreator = var1._delegateCreator;
      this._delegateArguments = var1._delegateArguments;
      this._arrayDelegateType = var1._arrayDelegateType;
      this._arrayDelegateCreator = var1._arrayDelegateCreator;
      this._arrayDelegateArguments = var1._arrayDelegateArguments;
      this._fromStringCreator = var1._fromStringCreator;
      this._fromIntCreator = var1._fromIntCreator;
      this._fromLongCreator = var1._fromLongCreator;
      this._fromDoubleCreator = var1._fromDoubleCreator;
      this._fromBooleanCreator = var1._fromBooleanCreator;
   }

   public void configureFromObjectSettings(AnnotatedWithParams var1, AnnotatedWithParams var2, JavaType var3, SettableBeanProperty[] var4, AnnotatedWithParams var5, SettableBeanProperty[] var6) {
      this._defaultCreator = var1;
      this._delegateCreator = var2;
      this._delegateType = var3;
      this._delegateArguments = var4;
      this._withArgsCreator = var5;
      this._constructorArguments = var6;
   }

   public void configureFromArraySettings(AnnotatedWithParams var1, JavaType var2, SettableBeanProperty[] var3) {
      this._arrayDelegateCreator = var1;
      this._arrayDelegateType = var2;
      this._arrayDelegateArguments = var3;
   }

   public void configureFromStringCreator(AnnotatedWithParams var1) {
      this._fromStringCreator = var1;
   }

   public void configureFromIntCreator(AnnotatedWithParams var1) {
      this._fromIntCreator = var1;
   }

   public void configureFromLongCreator(AnnotatedWithParams var1) {
      this._fromLongCreator = var1;
   }

   public void configureFromDoubleCreator(AnnotatedWithParams var1) {
      this._fromDoubleCreator = var1;
   }

   public void configureFromBooleanCreator(AnnotatedWithParams var1) {
      this._fromBooleanCreator = var1;
   }

   public void configureIncompleteParameter(AnnotatedParameter var1) {
      this._incompleteParameter = var1;
   }

   public String getValueTypeDesc() {
      return this._valueTypeDesc;
   }

   public Class getValueClass() {
      return this._valueClass;
   }

   public boolean canCreateFromString() {
      return this._fromStringCreator != null;
   }

   public boolean canCreateFromInt() {
      return this._fromIntCreator != null;
   }

   public boolean canCreateFromLong() {
      return this._fromLongCreator != null;
   }

   public boolean canCreateFromDouble() {
      return this._fromDoubleCreator != null;
   }

   public boolean canCreateFromBoolean() {
      return this._fromBooleanCreator != null;
   }

   public boolean canCreateUsingDefault() {
      return this._defaultCreator != null;
   }

   public boolean canCreateUsingDelegate() {
      return this._delegateType != null;
   }

   public boolean canCreateUsingArrayDelegate() {
      return this._arrayDelegateType != null;
   }

   public boolean canCreateFromObjectWith() {
      return this._withArgsCreator != null;
   }

   public boolean canInstantiate() {
      return this.canCreateUsingDefault() || this.canCreateUsingDelegate() || this.canCreateUsingArrayDelegate() || this.canCreateFromObjectWith() || this.canCreateFromString() || this.canCreateFromInt() || this.canCreateFromLong() || this.canCreateFromDouble() || this.canCreateFromBoolean();
   }

   public JavaType getDelegateType(DeserializationConfig var1) {
      return this._delegateType;
   }

   public JavaType getArrayDelegateType(DeserializationConfig var1) {
      return this._arrayDelegateType;
   }

   public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig var1) {
      return this._constructorArguments;
   }

   public Object createUsingDefault(DeserializationContext var1) throws IOException {
      if (this._defaultCreator == null) {
         return super.createUsingDefault(var1);
      } else {
         Object var10000;
         try {
            var10000 = this._defaultCreator.call();
         } catch (Exception var3) {
            return var1.handleInstantiationProblem(this._valueClass, (Object)null, this.rewrapCtorProblem(var1, var3));
         }

         return var10000;
      }
   }

   public Object createFromObjectWith(DeserializationContext var1, Object[] var2) throws IOException {
      if (this._withArgsCreator == null) {
         return super.createFromObjectWith(var1, var2);
      } else {
         Object var10000;
         try {
            var10000 = this._withArgsCreator.call(var2);
         } catch (Exception var4) {
            return var1.handleInstantiationProblem(this._valueClass, var2, this.rewrapCtorProblem(var1, var4));
         }

         return var10000;
      }
   }

   public Object createUsingDelegate(DeserializationContext var1, Object var2) throws IOException {
      return this._delegateCreator == null && this._arrayDelegateCreator != null ? this._createUsingDelegate(this._arrayDelegateCreator, this._arrayDelegateArguments, var1, var2) : this._createUsingDelegate(this._delegateCreator, this._delegateArguments, var1, var2);
   }

   public Object createUsingArrayDelegate(DeserializationContext var1, Object var2) throws IOException {
      return this._arrayDelegateCreator == null && this._delegateCreator != null ? this.createUsingDelegate(var1, var2) : this._createUsingDelegate(this._arrayDelegateCreator, this._arrayDelegateArguments, var1, var2);
   }

   public Object createFromString(DeserializationContext var1, String var2) throws IOException {
      if (this._fromStringCreator == null) {
         return this._createFromStringFallbacks(var1, var2);
      } else {
         Object var10000;
         try {
            var10000 = this._fromStringCreator.call1(var2);
         } catch (Throwable var4) {
            return var1.handleInstantiationProblem(this._fromStringCreator.getDeclaringClass(), var2, this.rewrapCtorProblem(var1, var4));
         }

         return var10000;
      }
   }

   public Object createFromInt(DeserializationContext var1, int var2) throws IOException {
      Object var10000;
      if (this._fromIntCreator != null) {
         Integer var7 = var2;

         try {
            var10000 = this._fromIntCreator.call1(var7);
         } catch (Throwable var5) {
            return var1.handleInstantiationProblem(this._fromIntCreator.getDeclaringClass(), var7, this.rewrapCtorProblem(var1, var5));
         }

         return var10000;
      } else if (this._fromLongCreator != null) {
         Long var3 = (long)var2;

         try {
            var10000 = this._fromLongCreator.call1(var3);
         } catch (Throwable var6) {
            return var1.handleInstantiationProblem(this._fromLongCreator.getDeclaringClass(), var3, this.rewrapCtorProblem(var1, var6));
         }

         return var10000;
      } else {
         return super.createFromInt(var1, var2);
      }
   }

   public Object createFromLong(DeserializationContext var1, long var2) throws IOException {
      if (this._fromLongCreator == null) {
         return super.createFromLong(var1, var2);
      } else {
         Long var4 = var2;

         Object var10000;
         try {
            var10000 = this._fromLongCreator.call1(var4);
         } catch (Throwable var6) {
            return var1.handleInstantiationProblem(this._fromLongCreator.getDeclaringClass(), var4, this.rewrapCtorProblem(var1, var6));
         }

         return var10000;
      }
   }

   public Object createFromDouble(DeserializationContext var1, double var2) throws IOException {
      if (this._fromDoubleCreator == null) {
         return super.createFromDouble(var1, var2);
      } else {
         Double var4 = var2;

         Object var10000;
         try {
            var10000 = this._fromDoubleCreator.call1(var4);
         } catch (Throwable var6) {
            return var1.handleInstantiationProblem(this._fromDoubleCreator.getDeclaringClass(), var4, this.rewrapCtorProblem(var1, var6));
         }

         return var10000;
      }
   }

   public Object createFromBoolean(DeserializationContext var1, boolean var2) throws IOException {
      if (this._fromBooleanCreator == null) {
         return super.createFromBoolean(var1, var2);
      } else {
         Boolean var3 = var2;

         Object var10000;
         try {
            var10000 = this._fromBooleanCreator.call1(var3);
         } catch (Throwable var5) {
            return var1.handleInstantiationProblem(this._fromBooleanCreator.getDeclaringClass(), var3, this.rewrapCtorProblem(var1, var5));
         }

         return var10000;
      }
   }

   public AnnotatedWithParams getDelegateCreator() {
      return this._delegateCreator;
   }

   public AnnotatedWithParams getArrayDelegateCreator() {
      return this._arrayDelegateCreator;
   }

   public AnnotatedWithParams getDefaultCreator() {
      return this._defaultCreator;
   }

   public AnnotatedWithParams getWithArgsCreator() {
      return this._withArgsCreator;
   }

   public AnnotatedParameter getIncompleteParameter() {
      return this._incompleteParameter;
   }

   /** @deprecated */
   @Deprecated
   protected JsonMappingException wrapException(Throwable var1) {
      for(Throwable var2 = var1; var2 != null; var2 = var2.getCause()) {
         if (var2 instanceof JsonMappingException) {
            return (JsonMappingException)var2;
         }
      }

      return new JsonMappingException((Closeable)null, "Instantiation of " + this.getValueTypeDesc() + " value failed: " + var1.getMessage(), var1);
   }

   protected JsonMappingException unwrapAndWrapException(DeserializationContext var1, Throwable var2) {
      for(Throwable var3 = var2; var3 != null; var3 = var3.getCause()) {
         if (var3 instanceof JsonMappingException) {
            return (JsonMappingException)var3;
         }
      }

      return var1.instantiationException(this.getValueClass(), var2);
   }

   protected JsonMappingException wrapAsJsonMappingException(DeserializationContext var1, Throwable var2) {
      return var2 instanceof JsonMappingException ? (JsonMappingException)var2 : var1.instantiationException(this.getValueClass(), var2);
   }

   protected JsonMappingException rewrapCtorProblem(DeserializationContext var1, Throwable var2) {
      if (var2 instanceof ExceptionInInitializerError || var2 instanceof InvocationTargetException) {
         Throwable var3 = var2.getCause();
         if (var3 != null) {
            var2 = var3;
         }
      }

      return this.wrapAsJsonMappingException(var1, var2);
   }

   private Object _createUsingDelegate(AnnotatedWithParams var1, SettableBeanProperty[] var2, DeserializationContext var3, Object var4) throws IOException {
      if (var1 == null) {
         throw new IllegalStateException("No delegate constructor for " + this.getValueTypeDesc());
      } else {
         try {
            Object var10000;
            if (var2 == null) {
               var10000 = var1.call1(var4);
               return var10000;
            } else {
               int var5 = var2.length;
               Object[] var6 = new Object[var5];

               for(int var7 = 0; var7 < var5; ++var7) {
                  SettableBeanProperty var8 = var2[var7];
                  if (var8 == null) {
                     var6[var7] = var4;
                  } else {
                     var6[var7] = var3.findInjectableValue(var8.getInjectableValueId(), var8, (Object)null);
                  }
               }

               var10000 = var1.call(var6);
               return var10000;
            }
         } catch (Throwable var9) {
            throw this.rewrapCtorProblem(var3, var9);
         }
      }
   }
}
