package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import java.util.Collection;

public abstract class SubtypeResolver {
   public SubtypeResolver() {
      super();
   }

   public abstract void registerSubtypes(NamedType... var1);

   public abstract void registerSubtypes(Class... var1);

   public abstract void registerSubtypes(Collection var1);

   public Collection collectAndResolveSubtypesByClass(MapperConfig var1, AnnotatedMember var2, JavaType var3) {
      return this.collectAndResolveSubtypes(var2, var1, var1.getAnnotationIntrospector(), var3);
   }

   public Collection collectAndResolveSubtypesByClass(MapperConfig var1, AnnotatedClass var2) {
      return this.collectAndResolveSubtypes(var2, var1, var1.getAnnotationIntrospector());
   }

   public Collection collectAndResolveSubtypesByTypeId(MapperConfig var1, AnnotatedMember var2, JavaType var3) {
      return this.collectAndResolveSubtypes(var2, var1, var1.getAnnotationIntrospector(), var3);
   }

   public Collection collectAndResolveSubtypesByTypeId(MapperConfig var1, AnnotatedClass var2) {
      return this.collectAndResolveSubtypes(var2, var1, var1.getAnnotationIntrospector());
   }

   /** @deprecated */
   @Deprecated
   public Collection collectAndResolveSubtypes(AnnotatedMember var1, MapperConfig var2, AnnotationIntrospector var3, JavaType var4) {
      return this.collectAndResolveSubtypesByClass(var2, var1, var4);
   }

   /** @deprecated */
   @Deprecated
   public Collection collectAndResolveSubtypes(AnnotatedClass var1, MapperConfig var2, AnnotationIntrospector var3) {
      return this.collectAndResolveSubtypesByClass(var2, var1);
   }
}
