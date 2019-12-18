package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.Annotations;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

class AnnotationCollector$NCollector extends AnnotationCollector {
   protected final HashMap _annotations = new HashMap();

   public AnnotationCollector$NCollector(Object var1, Class var2, Annotation var3, Class var4, Annotation var5) {
      super(var1);
      this._annotations.put(var2, var3);
      this._annotations.put(var4, var5);
   }

   public Annotations asAnnotations() {
      if (this._annotations.size() == 2) {
         Iterator var1 = this._annotations.entrySet().iterator();
         Entry var2 = (Entry)var1.next();
         Entry var3 = (Entry)var1.next();
         return new AnnotationCollector$TwoAnnotations((Class)var2.getKey(), (Annotation)var2.getValue(), (Class)var3.getKey(), (Annotation)var3.getValue());
      } else {
         return new AnnotationMap(this._annotations);
      }
   }

   public AnnotationMap asAnnotationMap() {
      AnnotationMap var1 = new AnnotationMap();
      Iterator var2 = this._annotations.values().iterator();

      while(var2.hasNext()) {
         Annotation var3 = (Annotation)var2.next();
         var1.add(var3);
      }

      return var1;
   }

   public boolean isPresent(Annotation var1) {
      return this._annotations.containsKey(var1.annotationType());
   }

   public AnnotationCollector addOrOverride(Annotation var1) {
      this._annotations.put(var1.annotationType(), var1);
      return this;
   }
}
