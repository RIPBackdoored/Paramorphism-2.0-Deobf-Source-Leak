package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;

public class ThrowableDeserializer extends BeanDeserializer {
   private static final long serialVersionUID = 1L;
   protected static final String PROP_NAME_MESSAGE = "message";

   public ThrowableDeserializer(BeanDeserializer var1) {
      super(var1);
      this._vanillaProcessing = false;
   }

   protected ThrowableDeserializer(BeanDeserializer var1, NameTransformer var2) {
      super(var1, (NameTransformer)var2);
   }

   public JsonDeserializer unwrappingDeserializer(NameTransformer var1) {
      return this.getClass() != ThrowableDeserializer.class ? this : new ThrowableDeserializer(this, var1);
   }

   public Object deserializeFromObject(JsonParser var1, DeserializationContext var2) throws IOException {
      if (this._propertyBasedCreator != null) {
         return this._deserializeUsingPropertyBased(var1, var2);
      } else if (this._delegateDeserializer != null) {
         return this._valueInstantiator.createUsingDelegate(var2, this._delegateDeserializer.deserialize(var1, var2));
      } else if (this._beanType.isAbstract()) {
         return var2.handleMissingInstantiator(this.handledType(), this.getValueInstantiator(), var1, "abstract type (need to add/enable type information?)");
      } else {
         boolean var3 = this._valueInstantiator.canCreateFromString();
         boolean var4 = this._valueInstantiator.canCreateUsingDefault();
         if (!var3 && !var4) {
            return var2.handleMissingInstantiator(this.handledType(), this.getValueInstantiator(), var1, "Throwable needs a default contructor, a single-String-arg constructor; or explicit @JsonCreator");
         } else {
            Object var5 = null;
            Object[] var6 = null;

            int var7;
            for(var7 = 0; var1.getCurrentToken() != JsonToken.END_OBJECT; var1.nextToken()) {
               String var8 = var1.getCurrentName();
               SettableBeanProperty var9 = this._beanProperties.find(var8);
               var1.nextToken();
               if (var9 != null) {
                  if (var5 != null) {
                     var9.deserializeAndSet(var1, var2, var5);
                  } else {
                     if (var6 == null) {
                        int var15 = this._beanProperties.size();
                        var6 = new Object[var15 + var15];
                     }

                     var6[var7++] = var9;
                     var6[var7++] = var9.deserialize(var1, var2);
                  }
               } else {
                  boolean var10 = "message".equals(var8);
                  if (var10 && var3) {
                     var5 = this._valueInstantiator.createFromString(var2, var1.getValueAsString());
                     if (var6 != null) {
                        int var11 = 0;

                        for(int var12 = var7; var11 < var12; var11 += 2) {
                           var9 = (SettableBeanProperty)var6[var11];
                           var9.set(var5, var6[var11 + 1]);
                        }

                        var6 = null;
                     }
                  } else if (this._ignorableProps != null && this._ignorableProps.contains(var8)) {
                     var1.skipChildren();
                  } else if (this._anySetter != null) {
                     this._anySetter.deserializeAndSet(var1, var2, var5, var8);
                  } else {
                     this.handleUnknownProperty(var1, var2, var5, var8);
                  }
               }
            }

            if (var5 == null) {
               if (var3) {
                  var5 = this._valueInstantiator.createFromString(var2, (String)null);
               } else {
                  var5 = this._valueInstantiator.createUsingDefault(var2);
               }

               if (var6 != null) {
                  int var13 = 0;

                  for(int var14 = var7; var13 < var14; var13 += 2) {
                     SettableBeanProperty var16 = (SettableBeanProperty)var6[var13];
                     var16.set(var5, var6[var13 + 1]);
                  }
               }
            }

            return var5;
         }
      }
   }
}
