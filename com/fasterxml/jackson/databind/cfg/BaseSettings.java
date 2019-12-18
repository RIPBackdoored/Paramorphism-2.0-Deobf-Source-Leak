package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

public final class BaseSettings implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC");
   protected final ClassIntrospector _classIntrospector;
   protected final AnnotationIntrospector _annotationIntrospector;
   protected final PropertyNamingStrategy _propertyNamingStrategy;
   protected final TypeFactory _typeFactory;
   protected final TypeResolverBuilder _typeResolverBuilder;
   protected final DateFormat _dateFormat;
   protected final HandlerInstantiator _handlerInstantiator;
   protected final Locale _locale;
   protected final TimeZone _timeZone;
   protected final Base64Variant _defaultBase64;

   public BaseSettings(ClassIntrospector var1, AnnotationIntrospector var2, PropertyNamingStrategy var3, TypeFactory var4, TypeResolverBuilder var5, DateFormat var6, HandlerInstantiator var7, Locale var8, TimeZone var9, Base64Variant var10) {
      super();
      this._classIntrospector = var1;
      this._annotationIntrospector = var2;
      this._propertyNamingStrategy = var3;
      this._typeFactory = var4;
      this._typeResolverBuilder = var5;
      this._dateFormat = var6;
      this._handlerInstantiator = var7;
      this._locale = var8;
      this._timeZone = var9;
      this._defaultBase64 = var10;
   }

   public BaseSettings copy() {
      return new BaseSettings(this._classIntrospector.copy(), this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
   }

   public BaseSettings withClassIntrospector(ClassIntrospector var1) {
      return this._classIntrospector == var1 ? this : new BaseSettings(var1, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
   }

   public BaseSettings withAnnotationIntrospector(AnnotationIntrospector var1) {
      return this._annotationIntrospector == var1 ? this : new BaseSettings(this._classIntrospector, var1, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
   }

   public BaseSettings withInsertedAnnotationIntrospector(AnnotationIntrospector var1) {
      return this.withAnnotationIntrospector(AnnotationIntrospectorPair.create(var1, this._annotationIntrospector));
   }

   public BaseSettings withAppendedAnnotationIntrospector(AnnotationIntrospector var1) {
      return this.withAnnotationIntrospector(AnnotationIntrospectorPair.create(this._annotationIntrospector, var1));
   }

   public BaseSettings withPropertyNamingStrategy(PropertyNamingStrategy var1) {
      return this._propertyNamingStrategy == var1 ? this : new BaseSettings(this._classIntrospector, this._annotationIntrospector, var1, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
   }

   public BaseSettings withTypeFactory(TypeFactory var1) {
      return this._typeFactory == var1 ? this : new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, var1, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
   }

   public BaseSettings withTypeResolverBuilder(TypeResolverBuilder var1) {
      return this._typeResolverBuilder == var1 ? this : new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, var1, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
   }

   public BaseSettings withDateFormat(DateFormat var1) {
      if (this._dateFormat == var1) {
         return this;
      } else {
         if (var1 != null && this.hasExplicitTimeZone()) {
            var1 = this._force(var1, this._timeZone);
         }

         return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, var1, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
      }
   }

   public BaseSettings withHandlerInstantiator(HandlerInstantiator var1) {
      return this._handlerInstantiator == var1 ? this : new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, var1, this._locale, this._timeZone, this._defaultBase64);
   }

   public BaseSettings with(Locale var1) {
      return this._locale == var1 ? this : new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, var1, this._timeZone, this._defaultBase64);
   }

   public BaseSettings with(TimeZone var1) {
      if (var1 == null) {
         throw new IllegalArgumentException();
      } else if (var1 == this._timeZone) {
         return this;
      } else {
         DateFormat var2 = this._force(this._dateFormat, var1);
         return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, var2, this._handlerInstantiator, this._locale, var1, this._defaultBase64);
      }
   }

   public BaseSettings with(Base64Variant var1) {
      return var1 == this._defaultBase64 ? this : new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, var1);
   }

   public ClassIntrospector getClassIntrospector() {
      return this._classIntrospector;
   }

   public AnnotationIntrospector getAnnotationIntrospector() {
      return this._annotationIntrospector;
   }

   public PropertyNamingStrategy getPropertyNamingStrategy() {
      return this._propertyNamingStrategy;
   }

   public TypeFactory getTypeFactory() {
      return this._typeFactory;
   }

   public TypeResolverBuilder getTypeResolverBuilder() {
      return this._typeResolverBuilder;
   }

   public DateFormat getDateFormat() {
      return this._dateFormat;
   }

   public HandlerInstantiator getHandlerInstantiator() {
      return this._handlerInstantiator;
   }

   public Locale getLocale() {
      return this._locale;
   }

   public TimeZone getTimeZone() {
      TimeZone var1 = this._timeZone;
      return var1 == null ? DEFAULT_TIMEZONE : var1;
   }

   public boolean hasExplicitTimeZone() {
      return this._timeZone != null;
   }

   public Base64Variant getBase64Variant() {
      return this._defaultBase64;
   }

   private DateFormat _force(DateFormat var1, TimeZone var2) {
      if (var1 instanceof StdDateFormat) {
         return ((StdDateFormat)var1).withTimeZone(var2);
      } else {
         var1 = (DateFormat)var1.clone();
         var1.setTimeZone(var2);
         return var1;
      }
   }
}
