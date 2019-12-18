package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerator$IdKey;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonDeserializer$None;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.KeyDeserializer$None;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId$Referring;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public abstract class DefaultDeserializationContext extends DeserializationContext implements Serializable {
   private static final long serialVersionUID = 1L;
   protected transient LinkedHashMap _objectIds;
   private List _objectIdResolvers;

   protected DefaultDeserializationContext(DeserializerFactory var1, DeserializerCache var2) {
      super(var1, var2);
   }

   protected DefaultDeserializationContext(DefaultDeserializationContext var1, DeserializationConfig var2, JsonParser var3, InjectableValues var4) {
      super(var1, var2, var3, var4);
   }

   protected DefaultDeserializationContext(DefaultDeserializationContext var1, DeserializerFactory var2) {
      super((DeserializationContext)var1, (DeserializerFactory)var2);
   }

   protected DefaultDeserializationContext(DefaultDeserializationContext var1) {
      super((DeserializationContext)var1);
   }

   public DefaultDeserializationContext copy() {
      throw new IllegalStateException("DefaultDeserializationContext sub-class not overriding copy()");
   }

   public ReadableObjectId findObjectId(Object var1, ObjectIdGenerator var2, ObjectIdResolver var3) {
      if (var1 == null) {
         return null;
      } else {
         ObjectIdGenerator$IdKey var4 = var2.key(var1);
         if (this._objectIds == null) {
            this._objectIds = new LinkedHashMap();
         } else {
            ReadableObjectId var5 = (ReadableObjectId)this._objectIds.get(var4);
            if (var5 != null) {
               return var5;
            }
         }

         ObjectIdResolver var8 = null;
         if (this._objectIdResolvers == null) {
            this._objectIdResolvers = new ArrayList(8);
         } else {
            Iterator var6 = this._objectIdResolvers.iterator();

            while(var6.hasNext()) {
               ObjectIdResolver var7 = (ObjectIdResolver)var6.next();
               if (var7.canUseFor(var3)) {
                  var8 = var7;
                  break;
               }
            }
         }

         if (var8 == null) {
            var8 = var3.newForDeserialization(this);
            this._objectIdResolvers.add(var8);
         }

         ReadableObjectId var9 = this.createReadableObjectId(var4);
         var9.setResolver(var8);
         this._objectIds.put(var4, var9);
         return var9;
      }
   }

   protected ReadableObjectId createReadableObjectId(ObjectIdGenerator$IdKey var1) {
      return new ReadableObjectId(var1);
   }

   public void checkUnresolvedObjectId() throws UnresolvedForwardReference {
      if (this._objectIds != null) {
         if (this.isEnabled(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS)) {
            UnresolvedForwardReference var1 = null;
            Iterator var2 = this._objectIds.entrySet().iterator();

            while(true) {
               ReadableObjectId var4;
               do {
                  do {
                     if (!var2.hasNext()) {
                        if (var1 != null) {
                           throw var1;
                        }

                        return;
                     }

                     Entry var3 = (Entry)var2.next();
                     var4 = (ReadableObjectId)var3.getValue();
                  } while(!var4.hasReferringProperties());
               } while(this.tryToResolveUnresolvedObjectId(var4));

               if (var1 == null) {
                  var1 = new UnresolvedForwardReference(this.getParser(), "Unresolved forward references for: ");
               }

               Object var5 = var4.getKey().key;
               Iterator var6 = var4.referringProperties();

               while(var6.hasNext()) {
                  ReadableObjectId$Referring var7 = (ReadableObjectId$Referring)var6.next();
                  var1.addUnresolvedId(var5, var7.getBeanType(), var7.getLocation());
               }
            }
         }
      }
   }

   protected boolean tryToResolveUnresolvedObjectId(ReadableObjectId var1) {
      return var1.tryToResolveUnresolved(this);
   }

   public JsonDeserializer deserializerInstance(Annotated var1, Object var2) throws JsonMappingException {
      if (var2 == null) {
         return null;
      } else {
         JsonDeserializer var3;
         if (var2 instanceof JsonDeserializer) {
            var3 = (JsonDeserializer)var2;
         } else {
            if (!(var2 instanceof Class)) {
               throw new IllegalStateException("AnnotationIntrospector returned deserializer definition of type " + var2.getClass().getName() + "; expected type JsonDeserializer or Class<JsonDeserializer> instead");
            }

            Class var4 = (Class)var2;
            if (var4 == JsonDeserializer$None.class || ClassUtil.isBogusClass(var4)) {
               return null;
            }

            if (!JsonDeserializer.class.isAssignableFrom(var4)) {
               throw new IllegalStateException("AnnotationIntrospector returned Class " + var4.getName() + "; expected Class<JsonDeserializer>");
            }

            HandlerInstantiator var5 = this._config.getHandlerInstantiator();
            var3 = var5 == null ? null : var5.deserializerInstance(this._config, var1, var4);
            if (var3 == null) {
               var3 = (JsonDeserializer)ClassUtil.createInstance(var4, this._config.canOverrideAccessModifiers());
            }
         }

         if (var3 instanceof ResolvableDeserializer) {
            ((ResolvableDeserializer)var3).resolve(this);
         }

         return var3;
      }
   }

   public final KeyDeserializer keyDeserializerInstance(Annotated var1, Object var2) throws JsonMappingException {
      if (var2 == null) {
         return null;
      } else {
         KeyDeserializer var3;
         if (var2 instanceof KeyDeserializer) {
            var3 = (KeyDeserializer)var2;
         } else {
            if (!(var2 instanceof Class)) {
               throw new IllegalStateException("AnnotationIntrospector returned key deserializer definition of type " + var2.getClass().getName() + "; expected type KeyDeserializer or Class<KeyDeserializer> instead");
            }

            Class var4 = (Class)var2;
            if (var4 == KeyDeserializer$None.class || ClassUtil.isBogusClass(var4)) {
               return null;
            }

            if (!KeyDeserializer.class.isAssignableFrom(var4)) {
               throw new IllegalStateException("AnnotationIntrospector returned Class " + var4.getName() + "; expected Class<KeyDeserializer>");
            }

            HandlerInstantiator var5 = this._config.getHandlerInstantiator();
            var3 = var5 == null ? null : var5.keyDeserializerInstance(this._config, var1, var4);
            if (var3 == null) {
               var3 = (KeyDeserializer)ClassUtil.createInstance(var4, this._config.canOverrideAccessModifiers());
            }
         }

         if (var3 instanceof ResolvableDeserializer) {
            ((ResolvableDeserializer)var3).resolve(this);
         }

         return var3;
      }
   }

   public abstract DefaultDeserializationContext with(DeserializerFactory var1);

   public abstract DefaultDeserializationContext createInstance(DeserializationConfig var1, JsonParser var2, InjectableValues var3);
}
