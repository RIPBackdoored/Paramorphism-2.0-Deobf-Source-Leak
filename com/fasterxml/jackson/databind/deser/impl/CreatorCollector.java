package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.reflect.Member;
import java.util.HashMap;

public class CreatorCollector {
   protected static final int C_DEFAULT = 0;
   protected static final int C_STRING = 1;
   protected static final int C_INT = 2;
   protected static final int C_LONG = 3;
   protected static final int C_DOUBLE = 4;
   protected static final int C_BOOLEAN = 5;
   protected static final int C_DELEGATE = 6;
   protected static final int C_PROPS = 7;
   protected static final int C_ARRAY_DELEGATE = 8;
   protected static final String[] TYPE_DESCS = new String[]{"default", "from-String", "from-int", "from-long", "from-double", "from-boolean", "delegate", "property-based"};
   protected final BeanDescription _beanDesc;
   protected final boolean _canFixAccess;
   protected final boolean _forceAccess;
   protected final AnnotatedWithParams[] _creators = new AnnotatedWithParams[9];
   protected int _explicitCreators = 0;
   protected boolean _hasNonDefaultCreator = false;
   protected SettableBeanProperty[] _delegateArgs;
   protected SettableBeanProperty[] _arrayDelegateArgs;
   protected SettableBeanProperty[] _propertyBasedArgs;

   public CreatorCollector(BeanDescription var1, MapperConfig var2) {
      super();
      this._beanDesc = var1;
      this._canFixAccess = var2.canOverrideAccessModifiers();
      this._forceAccess = var2.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS);
   }

   public ValueInstantiator constructValueInstantiator(DeserializationContext var1) throws JsonMappingException {
      DeserializationConfig var2 = var1.getConfig();
      JavaType var3 = this._computeDelegateType(var1, this._creators[6], this._delegateArgs);
      JavaType var4 = this._computeDelegateType(var1, this._creators[8], this._arrayDelegateArgs);
      JavaType var5 = this._beanDesc.getType();
      AnnotatedWithParams var6 = CreatorCollector$StdTypeConstructor.tryToOptimize(this._creators[0]);
      StdValueInstantiator var7 = new StdValueInstantiator(var2, var5);
      var7.configureFromObjectSettings(var6, this._creators[6], var3, this._delegateArgs, this._creators[7], this._propertyBasedArgs);
      var7.configureFromArraySettings(this._creators[8], var4, this._arrayDelegateArgs);
      var7.configureFromStringCreator(this._creators[1]);
      var7.configureFromIntCreator(this._creators[2]);
      var7.configureFromLongCreator(this._creators[3]);
      var7.configureFromDoubleCreator(this._creators[4]);
      var7.configureFromBooleanCreator(this._creators[5]);
      return var7;
   }

   public void setDefaultCreator(AnnotatedWithParams var1) {
      this._creators[0] = (AnnotatedWithParams)this._fixAccess(var1);
   }

   public void addStringCreator(AnnotatedWithParams var1, boolean var2) {
      this.verifyNonDup(var1, 1, var2);
   }

   public void addIntCreator(AnnotatedWithParams var1, boolean var2) {
      this.verifyNonDup(var1, 2, var2);
   }

   public void addLongCreator(AnnotatedWithParams var1, boolean var2) {
      this.verifyNonDup(var1, 3, var2);
   }

   public void addDoubleCreator(AnnotatedWithParams var1, boolean var2) {
      this.verifyNonDup(var1, 4, var2);
   }

   public void addBooleanCreator(AnnotatedWithParams var1, boolean var2) {
      this.verifyNonDup(var1, 5, var2);
   }

   public void addDelegatingCreator(AnnotatedWithParams var1, boolean var2, SettableBeanProperty[] var3, int var4) {
      if (var1.getParameterType(var4).isCollectionLikeType()) {
         if (this.verifyNonDup(var1, 8, var2)) {
            this._arrayDelegateArgs = var3;
         }
      } else if (this.verifyNonDup(var1, 6, var2)) {
         this._delegateArgs = var3;
      }

   }

   public void addPropertyCreator(AnnotatedWithParams var1, boolean var2, SettableBeanProperty[] var3) {
      if (this.verifyNonDup(var1, 7, var2)) {
         if (var3.length > 1) {
            HashMap var4 = new HashMap();
            int var5 = 0;

            for(int var6 = var3.length; var5 < var6; ++var5) {
               String var7 = var3[var5].getName();
               if (!var7.isEmpty() || var3[var5].getInjectableValueId() == null) {
                  Integer var8 = (Integer)var4.put(var7, var5);
                  if (var8 != null) {
                     throw new IllegalArgumentException(String.format("Duplicate creator property \"%s\" (index %s vs %d) for type %s ", var7, var8, var5, ClassUtil.nameOf(this._beanDesc.getBeanClass())));
                  }
               }
            }
         }

         this._propertyBasedArgs = var3;
      }

   }

   public boolean hasDefaultCreator() {
      return this._creators[0] != null;
   }

   public boolean hasDelegatingCreator() {
      return this._creators[6] != null;
   }

   public boolean hasPropertyBasedCreator() {
      return this._creators[7] != null;
   }

   private JavaType _computeDelegateType(DeserializationContext var1, AnnotatedWithParams var2, SettableBeanProperty[] var3) throws JsonMappingException {
      if (this._hasNonDefaultCreator && var2 != null) {
         int var4 = 0;
         if (var3 != null) {
            int var5 = 0;

            for(int var6 = var3.length; var5 < var6; ++var5) {
               if (var3[var5] == null) {
                  var4 = var5;
                  break;
               }
            }
         }

         DeserializationConfig var11 = var1.getConfig();
         JavaType var12 = var2.getParameterType(var4);
         AnnotationIntrospector var7 = var11.getAnnotationIntrospector();
         if (var7 != null) {
            AnnotatedParameter var8 = var2.getParameter(var4);
            Object var9 = var7.findDeserializer(var8);
            if (var9 != null) {
               JsonDeserializer var10 = var1.deserializerInstance(var8, var9);
               var12 = var12.withValueHandler(var10);
            } else {
               var12 = var7.refineDeserializationType(var11, var8, var12);
            }
         }

         return var12;
      } else {
         return null;
      }
   }

   private AnnotatedMember _fixAccess(AnnotatedMember var1) {
      if (var1 != null && this._canFixAccess) {
         ClassUtil.checkAndFixAccess((Member)var1.getAnnotated(), this._forceAccess);
      }

      return var1;
   }

   protected boolean verifyNonDup(AnnotatedWithParams var1, int var2, boolean var3) {
      int var4 = 1 << var2;
      this._hasNonDefaultCreator = true;
      AnnotatedWithParams var5 = this._creators[var2];
      if (var5 != null) {
         boolean var6;
         if ((this._explicitCreators & var4) != 0) {
            if (!var3) {
               return false;
            }

            var6 = true;
         } else {
            var6 = !var3;
         }

         if (var6 && var5.getClass() == var1.getClass()) {
            Class var7 = var5.getRawParameterType(0);
            Class var8 = var1.getRawParameterType(0);
            if (var7 == var8) {
               if (this._isEnumValueOf(var1)) {
                  return false;
               }

               if (!this._isEnumValueOf(var5)) {
                  throw new IllegalArgumentException(String.format("Conflicting %s creators: already had %s creator %s, encountered another: %s", TYPE_DESCS[var2], var3 ? "explicitly marked" : "implicitly discovered", var5, var1));
               }
            } else if (var8.isAssignableFrom(var7)) {
               return false;
            }
         }
      }

      if (var3) {
         this._explicitCreators |= var4;
      }

      this._creators[var2] = (AnnotatedWithParams)this._fixAccess(var1);
      return true;
   }

   protected boolean _isEnumValueOf(AnnotatedWithParams var1) {
      return var1.getDeclaringClass().isEnum() && "valueOf".equals(var1.getName());
   }
}
