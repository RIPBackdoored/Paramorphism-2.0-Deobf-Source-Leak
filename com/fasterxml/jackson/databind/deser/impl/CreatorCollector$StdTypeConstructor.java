package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public final class CreatorCollector$StdTypeConstructor extends AnnotatedWithParams implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final int TYPE_ARRAY_LIST = 1;
   public static final int TYPE_HASH_MAP = 2;
   public static final int TYPE_LINKED_HASH_MAP = 3;
   private final AnnotatedWithParams _base;
   private final int _type;

   public CreatorCollector$StdTypeConstructor(AnnotatedWithParams var1, int var2) {
      super(var1, (AnnotationMap[])null);
      this._base = var1;
      this._type = var2;
   }

   public static AnnotatedWithParams tryToOptimize(AnnotatedWithParams var0) {
      if (var0 != null) {
         Class var1 = var0.getDeclaringClass();
         if (var1 == List.class || var1 == ArrayList.class) {
            return new CreatorCollector$StdTypeConstructor(var0, 1);
         }

         if (var1 == LinkedHashMap.class) {
            return new CreatorCollector$StdTypeConstructor(var0, 3);
         }

         if (var1 == HashMap.class) {
            return new CreatorCollector$StdTypeConstructor(var0, 2);
         }
      }

      return var0;
   }

   protected final Object _construct() {
      switch(this._type) {
      case 1:
         return new ArrayList();
      case 2:
         return new HashMap();
      case 3:
         return new LinkedHashMap();
      default:
         throw new IllegalStateException("Unknown type " + this._type);
      }
   }

   public int getParameterCount() {
      return this._base.getParameterCount();
   }

   public Class getRawParameterType(int var1) {
      return this._base.getRawParameterType(var1);
   }

   public JavaType getParameterType(int var1) {
      return this._base.getParameterType(var1);
   }

   /** @deprecated */
   @Deprecated
   public Type getGenericParameterType(int var1) {
      return this._base.getGenericParameterType(var1);
   }

   public Object call() throws Exception {
      return this._construct();
   }

   public Object call(Object[] var1) throws Exception {
      return this._construct();
   }

   public Object call1(Object var1) throws Exception {
      return this._construct();
   }

   public Class getDeclaringClass() {
      return this._base.getDeclaringClass();
   }

   public Member getMember() {
      return this._base.getMember();
   }

   public void setValue(Object var1, Object var2) throws UnsupportedOperationException, IllegalArgumentException {
      throw new UnsupportedOperationException();
   }

   public Object getValue(Object var1) throws UnsupportedOperationException, IllegalArgumentException {
      throw new UnsupportedOperationException();
   }

   public Annotated withAnnotations(AnnotationMap var1) {
      throw new UnsupportedOperationException();
   }

   public AnnotatedElement getAnnotated() {
      return this._base.getAnnotated();
   }

   protected int getModifiers() {
      return this._base.getMember().getModifiers();
   }

   public String getName() {
      return this._base.getName();
   }

   public JavaType getType() {
      return this._base.getType();
   }

   public Class getRawType() {
      return this._base.getRawType();
   }

   public boolean equals(Object var1) {
      return var1 == this;
   }

   public int hashCode() {
      return this._base.hashCode();
   }

   public String toString() {
      return this._base.toString();
   }
}
