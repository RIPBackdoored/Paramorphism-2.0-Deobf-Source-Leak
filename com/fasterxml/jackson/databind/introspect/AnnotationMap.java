package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.Annotations;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public final class AnnotationMap implements Annotations {
   protected HashMap _annotations;

   public AnnotationMap() {
      super();
   }

   public static AnnotationMap of(Class var0, Annotation var1) {
      HashMap var2 = new HashMap(4);
      var2.put(var0, var1);
      return new AnnotationMap(var2);
   }

   AnnotationMap(HashMap var1) {
      super();
      this._annotations = var1;
   }

   public Annotation get(Class var1) {
      return this._annotations == null ? null : (Annotation)this._annotations.get(var1);
   }

   public boolean has(Class var1) {
      return this._annotations == null ? false : this._annotations.containsKey(var1);
   }

   public boolean hasOneOf(Class[] var1) {
      if (this._annotations != null) {
         int var2 = 0;

         for(int var3 = var1.length; var2 < var3; ++var2) {
            if (this._annotations.containsKey(var1[var2])) {
               return true;
            }
         }
      }

      return false;
   }

   public Iterable annotations() {
      return (Iterable)(this._annotations != null && this._annotations.size() != 0 ? this._annotations.values() : Collections.emptyList());
   }

   public static AnnotationMap merge(AnnotationMap var0, AnnotationMap var1) {
      if (var0 != null && var0._annotations != null && !var0._annotations.isEmpty()) {
         if (var1 != null && var1._annotations != null && !var1._annotations.isEmpty()) {
            HashMap var2 = new HashMap();
            Iterator var3 = var1._annotations.values().iterator();

            Annotation var4;
            while(var3.hasNext()) {
               var4 = (Annotation)var3.next();
               var2.put(var4.annotationType(), var4);
            }

            var3 = var0._annotations.values().iterator();

            while(var3.hasNext()) {
               var4 = (Annotation)var3.next();
               var2.put(var4.annotationType(), var4);
            }

            return new AnnotationMap(var2);
         } else {
            return var0;
         }
      } else {
         return var1;
      }
   }

   public int size() {
      return this._annotations == null ? 0 : this._annotations.size();
   }

   public boolean addIfNotPresent(Annotation var1) {
      if (this._annotations != null && this._annotations.containsKey(var1.annotationType())) {
         return false;
      } else {
         this._add(var1);
         return true;
      }
   }

   public boolean add(Annotation var1) {
      return this._add(var1);
   }

   public String toString() {
      return this._annotations == null ? "[null]" : this._annotations.toString();
   }

   protected final boolean _add(Annotation var1) {
      if (this._annotations == null) {
         this._annotations = new HashMap();
      }

      Annotation var2 = (Annotation)this._annotations.put(var1.annotationType(), var1);
      return var2 == null || !var2.equals(var1);
   }
}
