package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder$Value;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.Converter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BeanDescription {
   protected final JavaType _type;

   protected BeanDescription(JavaType var1) {
      super();
      this._type = var1;
   }

   public JavaType getType() {
      return this._type;
   }

   public Class getBeanClass() {
      return this._type.getRawClass();
   }

   public boolean isNonStaticInnerClass() {
      return this.getClassInfo().isNonStaticInnerClass();
   }

   public abstract AnnotatedClass getClassInfo();

   public abstract ObjectIdInfo getObjectIdInfo();

   public abstract boolean hasKnownClassAnnotations();

   /** @deprecated */
   @Deprecated
   public abstract TypeBindings bindingsForBeanType();

   /** @deprecated */
   @Deprecated
   public abstract JavaType resolveType(Type var1);

   public abstract Annotations getClassAnnotations();

   public abstract List findProperties();

   public abstract Set getIgnoredPropertyNames();

   public abstract List findBackReferences();

   /** @deprecated */
   @Deprecated
   public abstract Map findBackReferenceProperties();

   public abstract List getConstructors();

   public abstract List getFactoryMethods();

   public abstract AnnotatedConstructor findDefaultConstructor();

   public abstract Constructor findSingleArgConstructor(Class... var1);

   public abstract Method findFactoryMethod(Class... var1);

   public abstract AnnotatedMember findJsonValueAccessor();

   public abstract AnnotatedMember findAnyGetter();

   public abstract AnnotatedMember findAnySetterAccessor();

   public abstract AnnotatedMethod findMethod(String var1, Class[] var2);

   /** @deprecated */
   @Deprecated
   public abstract AnnotatedMethod findJsonValueMethod();

   /** @deprecated */
   @Deprecated
   public AnnotatedMethod findAnySetter() {
      AnnotatedMember var1 = this.findAnySetterAccessor();
      return var1 instanceof AnnotatedMethod ? (AnnotatedMethod)var1 : null;
   }

   /** @deprecated */
   @Deprecated
   public AnnotatedMember findAnySetterField() {
      AnnotatedMember var1 = this.findAnySetterAccessor();
      return var1 instanceof AnnotatedField ? var1 : null;
   }

   public abstract JsonInclude$Value findPropertyInclusion(JsonInclude$Value var1);

   public abstract JsonFormat$Value findExpectedFormat(JsonFormat$Value var1);

   public abstract Converter findSerializationConverter();

   public abstract Converter findDeserializationConverter();

   public String findClassDescription() {
      return null;
   }

   public abstract Map findInjectables();

   public abstract Class findPOJOBuilder();

   public abstract JsonPOJOBuilder$Value findPOJOBuilderConfig();

   public abstract Object instantiateBean(boolean var1);

   public abstract Class[] findDefaultViews();
}
