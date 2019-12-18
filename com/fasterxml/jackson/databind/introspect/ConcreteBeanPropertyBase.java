package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public abstract class ConcreteBeanPropertyBase implements BeanProperty, Serializable {
   private static final long serialVersionUID = 1L;
   protected final PropertyMetadata _metadata;
   protected transient JsonFormat$Value _propertyFormat;
   protected transient List _aliases;

   protected ConcreteBeanPropertyBase(PropertyMetadata var1) {
      super();
      this._metadata = var1 == null ? PropertyMetadata.STD_REQUIRED_OR_OPTIONAL : var1;
   }

   protected ConcreteBeanPropertyBase(ConcreteBeanPropertyBase var1) {
      super();
      this._metadata = var1._metadata;
      this._propertyFormat = var1._propertyFormat;
   }

   public boolean isRequired() {
      return this._metadata.isRequired();
   }

   public PropertyMetadata getMetadata() {
      return this._metadata;
   }

   public boolean isVirtual() {
      return false;
   }

   /** @deprecated */
   @Deprecated
   public final JsonFormat$Value findFormatOverrides(AnnotationIntrospector var1) {
      JsonFormat$Value var2 = null;
      if (var1 != null) {
         AnnotatedMember var3 = this.getMember();
         if (var3 != null) {
            var2 = var1.findFormat(var3);
         }
      }

      if (var2 == null) {
         var2 = EMPTY_FORMAT;
      }

      return var2;
   }

   public JsonFormat$Value findPropertyFormat(MapperConfig var1, Class var2) {
      JsonFormat$Value var3 = this._propertyFormat;
      if (var3 == null) {
         JsonFormat$Value var4 = var1.getDefaultPropertyFormat(var2);
         JsonFormat$Value var5 = null;
         AnnotationIntrospector var6 = var1.getAnnotationIntrospector();
         if (var6 != null) {
            AnnotatedMember var7 = this.getMember();
            if (var7 != null) {
               var5 = var6.findFormat(var7);
            }
         }

         if (var4 == null) {
            var3 = var5 == null ? EMPTY_FORMAT : var5;
         } else {
            var3 = var5 == null ? var4 : var4.withOverrides(var5);
         }

         this._propertyFormat = var3;
      }

      return var3;
   }

   public JsonInclude$Value findPropertyInclusion(MapperConfig var1, Class var2) {
      AnnotationIntrospector var3 = var1.getAnnotationIntrospector();
      AnnotatedMember var4 = this.getMember();
      JsonInclude$Value var5;
      if (var4 == null) {
         var5 = var1.getDefaultPropertyInclusion(var2);
         return var5;
      } else {
         var5 = var1.getDefaultInclusion(var2, var4.getRawType());
         if (var3 == null) {
            return var5;
         } else {
            JsonInclude$Value var6 = var3.findPropertyInclusion(var4);
            return var5 == null ? var6 : var5.withOverrides(var6);
         }
      }
   }

   public List findAliases(MapperConfig var1) {
      List var2 = this._aliases;
      if (var2 == null) {
         AnnotationIntrospector var3 = var1.getAnnotationIntrospector();
         if (var3 != null) {
            var2 = var3.findPropertyAliases(this.getMember());
         }

         if (var2 == null) {
            var2 = Collections.emptyList();
         }

         this._aliases = var2;
      }

      return var2;
   }
}
