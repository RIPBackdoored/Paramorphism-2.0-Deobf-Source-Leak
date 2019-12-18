package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.Converter$None;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.TimeZone;

public abstract class DatabindContext {
   private static final int MAX_ERROR_STR_LEN = 500;

   public DatabindContext() {
      super();
   }

   public abstract MapperConfig getConfig();

   public abstract AnnotationIntrospector getAnnotationIntrospector();

   public abstract boolean isEnabled(MapperFeature var1);

   public abstract boolean canOverrideAccessModifiers();

   public abstract Class getActiveView();

   public abstract Locale getLocale();

   public abstract TimeZone getTimeZone();

   public abstract JsonFormat$Value getDefaultPropertyFormat(Class var1);

   public abstract Object getAttribute(Object var1);

   public abstract DatabindContext setAttribute(Object var1, Object var2);

   public JavaType constructType(Type var1) {
      return var1 == null ? null : this.getTypeFactory().constructType(var1);
   }

   public JavaType constructSpecializedType(JavaType var1, Class var2) {
      return var1.getRawClass() == var2 ? var1 : this.getConfig().constructSpecializedType(var1, var2);
   }

   public JavaType resolveSubType(JavaType var1, String var2) throws JsonMappingException {
      if (var2.indexOf(60) > 0) {
         JavaType var3 = this.getTypeFactory().constructFromCanonical(var2);
         if (var3.isTypeOrSubTypeOf(var1.getRawClass())) {
            return var3;
         }
      } else {
         Class var7;
         try {
            var7 = this.getTypeFactory().findClass(var2);
         } catch (ClassNotFoundException var5) {
            return null;
         } catch (Exception var6) {
            throw this.invalidTypeIdException(var1, var2, String.format("problem: (%s) %s", var6.getClass().getName(), var6.getMessage()));
         }

         if (var1.isTypeOrSuperTypeOf(var7)) {
            return this.getTypeFactory().constructSpecializedType(var1, var7);
         }
      }

      throw this.invalidTypeIdException(var1, var2, "Not a subtype");
   }

   protected abstract JsonMappingException invalidTypeIdException(JavaType var1, String var2, String var3);

   public abstract TypeFactory getTypeFactory();

   public ObjectIdGenerator objectIdGeneratorInstance(Annotated var1, ObjectIdInfo var2) throws JsonMappingException {
      Class var3 = var2.getGeneratorType();
      MapperConfig var4 = this.getConfig();
      HandlerInstantiator var5 = var4.getHandlerInstantiator();
      ObjectIdGenerator var6 = var5 == null ? null : var5.objectIdGeneratorInstance(var4, var1, var3);
      if (var6 == null) {
         var6 = (ObjectIdGenerator)ClassUtil.createInstance(var3, var4.canOverrideAccessModifiers());
      }

      return var6.forScope(var2.getScope());
   }

   public ObjectIdResolver objectIdResolverInstance(Annotated var1, ObjectIdInfo var2) {
      Class var3 = var2.getResolverType();
      MapperConfig var4 = this.getConfig();
      HandlerInstantiator var5 = var4.getHandlerInstantiator();
      ObjectIdResolver var6 = var5 == null ? null : var5.resolverIdGeneratorInstance(var4, var1, var3);
      if (var6 == null) {
         var6 = (ObjectIdResolver)ClassUtil.createInstance(var3, var4.canOverrideAccessModifiers());
      }

      return var6;
   }

   public Converter converterInstance(Annotated var1, Object var2) throws JsonMappingException {
      if (var2 == null) {
         return null;
      } else if (var2 instanceof Converter) {
         return (Converter)var2;
      } else if (!(var2 instanceof Class)) {
         throw new IllegalStateException("AnnotationIntrospector returned Converter definition of type " + var2.getClass().getName() + "; expected type Converter or Class<Converter> instead");
      } else {
         Class var3 = (Class)var2;
         if (var3 != Converter$None.class && !ClassUtil.isBogusClass(var3)) {
            if (!Converter.class.isAssignableFrom(var3)) {
               throw new IllegalStateException("AnnotationIntrospector returned Class " + var3.getName() + "; expected Class<Converter>");
            } else {
               MapperConfig var4 = this.getConfig();
               HandlerInstantiator var5 = var4.getHandlerInstantiator();
               Converter var6 = var5 == null ? null : var5.converterInstance(var4, var1, var3);
               if (var6 == null) {
                  var6 = (Converter)ClassUtil.createInstance(var3, var4.canOverrideAccessModifiers());
               }

               return var6;
            }
         } else {
            return null;
         }
      }
   }

   public abstract Object reportBadDefinition(JavaType var1, String var2) throws JsonMappingException;

   public Object reportBadDefinition(Class var1, String var2) throws JsonMappingException {
      return this.reportBadDefinition(this.constructType(var1), var2);
   }

   protected final String _format(String var1, Object... var2) {
      return var2.length > 0 ? String.format(var1, var2) : var1;
   }

   protected final String _truncate(String var1) {
      if (var1 == null) {
         return "";
      } else {
         return var1.length() <= 500 ? var1 : var1.substring(0, 500) + "]...[" + var1.substring(var1.length() - 500);
      }
   }

   protected String _quotedString(String var1) {
      return var1 == null ? "[N/A]" : String.format("\"%s\"", this._truncate(var1));
   }

   protected String _colonConcat(String var1, String var2) {
      return var2 == null ? var1 : var1 + ": " + var2;
   }

   protected String _desc(String var1) {
      return var1 == null ? "[N/A]" : this._truncate(var1);
   }
}
