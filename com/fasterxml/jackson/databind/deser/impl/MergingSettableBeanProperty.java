package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty$Delegating;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import java.io.IOException;

public class MergingSettableBeanProperty extends SettableBeanProperty$Delegating {
   private static final long serialVersionUID = 1L;
   protected final AnnotatedMember _accessor;

   protected MergingSettableBeanProperty(SettableBeanProperty var1, AnnotatedMember var2) {
      super(var1);
      this._accessor = var2;
   }

   protected MergingSettableBeanProperty(MergingSettableBeanProperty var1, SettableBeanProperty var2) {
      super(var2);
      this._accessor = var1._accessor;
   }

   public static MergingSettableBeanProperty construct(SettableBeanProperty var0, AnnotatedMember var1) {
      return new MergingSettableBeanProperty(var0, var1);
   }

   protected SettableBeanProperty withDelegate(SettableBeanProperty var1) {
      return new MergingSettableBeanProperty(var1, this._accessor);
   }

   public void deserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      Object var4 = this._accessor.getValue(var3);
      Object var5;
      if (var4 == null) {
         var5 = this.delegate.deserialize(var1, var2);
      } else {
         var5 = this.delegate.deserializeWith(var1, var2, var4);
      }

      if (var5 != var4) {
         this.delegate.set(var3, var5);
      }

   }

   public Object deserializeSetAndReturn(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      Object var4 = this._accessor.getValue(var3);
      Object var5;
      if (var4 == null) {
         var5 = this.delegate.deserialize(var1, var2);
      } else {
         var5 = this.delegate.deserializeWith(var1, var2, var4);
      }

      return var5 != var4 && var5 != null ? this.delegate.setAndReturn(var3, var5) : var3;
   }

   public void set(Object var1, Object var2) throws IOException {
      if (var2 != null) {
         this.delegate.set(var1, var2);
      }

   }

   public Object setAndReturn(Object var1, Object var2) throws IOException {
      return var2 != null ? this.delegate.setAndReturn(var1, var2) : var1;
   }
}
