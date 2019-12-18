package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo$Id;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class TypeNameIdResolver extends TypeIdResolverBase {
   protected final MapperConfig _config;
   protected final Map _typeToId;
   protected final Map _idToType;

   protected TypeNameIdResolver(MapperConfig var1, JavaType var2, Map var3, Map var4) {
      super(var2, var1.getTypeFactory());
      this._config = var1;
      this._typeToId = var3;
      this._idToType = var4;
   }

   public static TypeNameIdResolver construct(MapperConfig var0, JavaType var1, Collection var2, boolean var3, boolean var4) {
      if (var3 == var4) {
         throw new IllegalArgumentException();
      } else {
         Object var5 = null;
         HashMap var6 = null;
         if (var3) {
            var5 = new HashMap();
         }

         if (var4) {
            var6 = new HashMap();
            var5 = new TreeMap();
         }

         if (var2 != null) {
            Iterator var7 = var2.iterator();

            while(true) {
               Class var9;
               String var10;
               JavaType var11;
               do {
                  do {
                     if (!var7.hasNext()) {
                        return new TypeNameIdResolver(var0, var1, (Map)var5, var6);
                     }

                     NamedType var8 = (NamedType)var7.next();
                     var9 = var8.getType();
                     var10 = var8.hasName() ? var8.getName() : _defaultTypeId(var9);
                     if (var3) {
                        ((Map)var5).put(var9.getName(), var10);
                     }
                  } while(!var4);

                  var11 = (JavaType)var6.get(var10);
               } while(var11 != null && var9.isAssignableFrom(var11.getRawClass()));

               var6.put(var10, var0.constructType(var9));
            }
         } else {
            return new TypeNameIdResolver(var0, var1, (Map)var5, var6);
         }
      }
   }

   public JsonTypeInfo$Id getMechanism() {
      return JsonTypeInfo$Id.NAME;
   }

   public String idFromValue(Object var1) {
      return this.idFromClass(var1.getClass());
   }

   protected String idFromClass(Class var1) {
      if (var1 == null) {
         return null;
      } else {
         Class var2 = this._typeFactory.constructType((Type)var1).getRawClass();
         String var3 = var2.getName();
         String var4;
         synchronized(this._typeToId) {
            var4 = (String)this._typeToId.get(var3);
            if (var4 == null) {
               if (this._config.isAnnotationProcessingEnabled()) {
                  BeanDescription var6 = this._config.introspectClassAnnotations(var2);
                  var4 = this._config.getAnnotationIntrospector().findTypeName(var6.getClassInfo());
               }

               if (var4 == null) {
                  var4 = _defaultTypeId(var2);
               }

               this._typeToId.put(var3, var4);
            }
         }

         return var4;
      }
   }

   public String idFromValueAndType(Object var1, Class var2) {
      return var1 == null ? this.idFromClass(var2) : this.idFromValue(var1);
   }

   public JavaType typeFromId(DatabindContext var1, String var2) {
      return this._typeFromId(var2);
   }

   protected JavaType _typeFromId(String var1) {
      return (JavaType)this._idToType.get(var1);
   }

   public String getDescForKnownTypeIds() {
      return (new TreeSet(this._idToType.keySet())).toString();
   }

   public String toString() {
      return String.format("[%s; id-to-type=%s]", this.getClass().getName(), this._idToType);
   }

   protected static String _defaultTypeId(Class var0) {
      String var1 = var0.getName();
      int var2 = var1.lastIndexOf(46);
      return var2 < 0 ? var1 : var1.substring(var2 + 1);
   }
}
