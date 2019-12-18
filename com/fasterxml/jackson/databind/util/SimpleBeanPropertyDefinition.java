package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.annotation.JsonInclude$Include;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.Collections;
import java.util.Iterator;

public class SimpleBeanPropertyDefinition extends BeanPropertyDefinition {
   protected final AnnotationIntrospector _annotationIntrospector;
   protected final AnnotatedMember _member;
   protected final PropertyMetadata _metadata;
   protected final PropertyName _fullName;
   protected final JsonInclude$Value _inclusion;

   protected SimpleBeanPropertyDefinition(AnnotationIntrospector var1, AnnotatedMember var2, PropertyName var3, PropertyMetadata var4, JsonInclude$Value var5) {
      super();
      this._annotationIntrospector = var1;
      this._member = var2;
      this._fullName = var3;
      this._metadata = var4 == null ? PropertyMetadata.STD_OPTIONAL : var4;
      this._inclusion = var5;
   }

   public static SimpleBeanPropertyDefinition construct(MapperConfig var0, AnnotatedMember var1) {
      return new SimpleBeanPropertyDefinition(var0.getAnnotationIntrospector(), var1, PropertyName.construct(var1.getName()), (PropertyMetadata)null, EMPTY_INCLUDE);
   }

   public static SimpleBeanPropertyDefinition construct(MapperConfig var0, AnnotatedMember var1, PropertyName var2) {
      return construct(var0, var1, var2, (PropertyMetadata)null, (JsonInclude$Value)EMPTY_INCLUDE);
   }

   public static SimpleBeanPropertyDefinition construct(MapperConfig var0, AnnotatedMember var1, PropertyName var2, PropertyMetadata var3, JsonInclude$Include var4) {
      JsonInclude$Value var5 = var4 != null && var4 != JsonInclude$Include.USE_DEFAULTS ? JsonInclude$Value.construct(var4, (JsonInclude$Include)null) : EMPTY_INCLUDE;
      return new SimpleBeanPropertyDefinition(var0.getAnnotationIntrospector(), var1, var2, var3, var5);
   }

   public static SimpleBeanPropertyDefinition construct(MapperConfig var0, AnnotatedMember var1, PropertyName var2, PropertyMetadata var3, JsonInclude$Value var4) {
      return new SimpleBeanPropertyDefinition(var0.getAnnotationIntrospector(), var1, var2, var3, var4);
   }

   public BeanPropertyDefinition withSimpleName(String var1) {
      return this._fullName.hasSimpleName(var1) && !this._fullName.hasNamespace() ? this : new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, new PropertyName(var1), this._metadata, this._inclusion);
   }

   public BeanPropertyDefinition withName(PropertyName var1) {
      return this._fullName.equals(var1) ? this : new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, var1, this._metadata, this._inclusion);
   }

   public BeanPropertyDefinition withMetadata(PropertyMetadata var1) {
      return var1.equals(this._metadata) ? this : new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, this._fullName, var1, this._inclusion);
   }

   public BeanPropertyDefinition withInclusion(JsonInclude$Value var1) {
      return this._inclusion == var1 ? this : new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, this._fullName, this._metadata, var1);
   }

   public String getName() {
      return this._fullName.getSimpleName();
   }

   public PropertyName getFullName() {
      return this._fullName;
   }

   public boolean hasName(PropertyName var1) {
      return this._fullName.equals(var1);
   }

   public String getInternalName() {
      return this.getName();
   }

   public PropertyName getWrapperName() {
      return this._annotationIntrospector != null && this._member != null ? this._annotationIntrospector.findWrapperName(this._member) : null;
   }

   public boolean isExplicitlyIncluded() {
      return false;
   }

   public boolean isExplicitlyNamed() {
      return false;
   }

   public PropertyMetadata getMetadata() {
      return this._metadata;
   }

   public JavaType getPrimaryType() {
      return this._member == null ? TypeFactory.unknownType() : this._member.getType();
   }

   public Class getRawPrimaryType() {
      return this._member == null ? Object.class : this._member.getRawType();
   }

   public JsonInclude$Value findInclusion() {
      return this._inclusion;
   }

   public boolean hasGetter() {
      return this.getGetter() != null;
   }

   public boolean hasSetter() {
      return this.getSetter() != null;
   }

   public boolean hasField() {
      return this._member instanceof AnnotatedField;
   }

   public boolean hasConstructorParameter() {
      return this._member instanceof AnnotatedParameter;
   }

   public AnnotatedMethod getGetter() {
      return this._member instanceof AnnotatedMethod && ((AnnotatedMethod)this._member).getParameterCount() == 0 ? (AnnotatedMethod)this._member : null;
   }

   public AnnotatedMethod getSetter() {
      return this._member instanceof AnnotatedMethod && ((AnnotatedMethod)this._member).getParameterCount() == 1 ? (AnnotatedMethod)this._member : null;
   }

   public AnnotatedField getField() {
      return this._member instanceof AnnotatedField ? (AnnotatedField)this._member : null;
   }

   public AnnotatedParameter getConstructorParameter() {
      return this._member instanceof AnnotatedParameter ? (AnnotatedParameter)this._member : null;
   }

   public Iterator getConstructorParameters() {
      AnnotatedParameter var1 = this.getConstructorParameter();
      return var1 == null ? ClassUtil.emptyIterator() : Collections.singleton(var1).iterator();
   }

   public AnnotatedMember getPrimaryMember() {
      return this._member;
   }
}
