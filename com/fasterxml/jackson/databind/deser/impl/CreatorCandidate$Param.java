package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.annotation.JacksonInject$Value;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

public final class CreatorCandidate$Param {
   public final AnnotatedParameter annotated;
   public final BeanPropertyDefinition propDef;
   public final JacksonInject$Value injection;

   public CreatorCandidate$Param(AnnotatedParameter var1, BeanPropertyDefinition var2, JacksonInject$Value var3) {
      super();
      this.annotated = var1;
      this.propDef = var2;
      this.injection = var3;
   }

   public PropertyName fullName() {
      return this.propDef == null ? null : this.propDef.getFullName();
   }

   public boolean hasFullName() {
      if (this.propDef == null) {
         return false;
      } else {
         PropertyName var1 = this.propDef.getFullName();
         return var1.hasSimpleName();
      }
   }
}
