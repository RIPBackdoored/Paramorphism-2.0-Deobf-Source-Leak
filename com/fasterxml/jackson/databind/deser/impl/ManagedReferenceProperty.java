package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty$Delegating;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class ManagedReferenceProperty extends SettableBeanProperty$Delegating {
   private static final long serialVersionUID = 1L;
   protected final String _referenceName;
   protected final boolean _isContainer;
   protected final SettableBeanProperty _backProperty;

   public ManagedReferenceProperty(SettableBeanProperty var1, String var2, SettableBeanProperty var3, boolean var4) {
      super(var1);
      this._referenceName = var2;
      this._backProperty = var3;
      this._isContainer = var4;
   }

   protected SettableBeanProperty withDelegate(SettableBeanProperty var1) {
      throw new IllegalStateException("Should never try to reset delegate");
   }

   public void fixAccess(DeserializationConfig var1) {
      this.delegate.fixAccess(var1);
      this._backProperty.fixAccess(var1);
   }

   public void deserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      this.set(var3, this.delegate.deserialize(var1, var2));
   }

   public Object deserializeSetAndReturn(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.setAndReturn(var3, this.deserialize(var1, var2));
   }

   public final void set(Object var1, Object var2) throws IOException {
      this.setAndReturn(var1, var2);
   }

   public Object setAndReturn(Object var1, Object var2) throws IOException {
      if (var2 != null) {
         if (this._isContainer) {
            if (var2 instanceof Object[]) {
               Object[] var3 = (Object[])((Object[])var2);
               int var4 = var3.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  Object var6 = var3[var5];
                  if (var6 != null) {
                     this._backProperty.set(var6, var1);
                  }
               }
            } else {
               Iterator var7;
               Object var8;
               if (var2 instanceof Collection) {
                  var7 = ((Collection)var2).iterator();

                  while(var7.hasNext()) {
                     var8 = var7.next();
                     if (var8 != null) {
                        this._backProperty.set(var8, var1);
                     }
                  }
               } else {
                  if (!(var2 instanceof Map)) {
                     throw new IllegalStateException("Unsupported container type (" + var2.getClass().getName() + ") when resolving reference '" + this._referenceName + "'");
                  }

                  var7 = ((Map)var2).values().iterator();

                  while(var7.hasNext()) {
                     var8 = var7.next();
                     if (var8 != null) {
                        this._backProperty.set(var8, var1);
                     }
                  }
               }
            }
         } else {
            this._backProperty.set(var2, var1);
         }
      }

      return this.delegate.setAndReturn(var1, var2);
   }
}
