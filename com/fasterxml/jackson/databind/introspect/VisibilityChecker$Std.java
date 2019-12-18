package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect$Value;
import com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class VisibilityChecker$Std implements VisibilityChecker, Serializable {
   private static final long serialVersionUID = 1L;
   protected static final VisibilityChecker$Std DEFAULT;
   protected final JsonAutoDetect$Visibility _getterMinLevel;
   protected final JsonAutoDetect$Visibility _isGetterMinLevel;
   protected final JsonAutoDetect$Visibility _setterMinLevel;
   protected final JsonAutoDetect$Visibility _creatorMinLevel;
   protected final JsonAutoDetect$Visibility _fieldMinLevel;

   public static VisibilityChecker$Std defaultInstance() {
      return DEFAULT;
   }

   public VisibilityChecker$Std(JsonAutoDetect var1) {
      super();
      this._getterMinLevel = var1.getterVisibility();
      this._isGetterMinLevel = var1.isGetterVisibility();
      this._setterMinLevel = var1.setterVisibility();
      this._creatorMinLevel = var1.creatorVisibility();
      this._fieldMinLevel = var1.fieldVisibility();
   }

   public VisibilityChecker$Std(JsonAutoDetect$Visibility var1, JsonAutoDetect$Visibility var2, JsonAutoDetect$Visibility var3, JsonAutoDetect$Visibility var4, JsonAutoDetect$Visibility var5) {
      super();
      this._getterMinLevel = var1;
      this._isGetterMinLevel = var2;
      this._setterMinLevel = var3;
      this._creatorMinLevel = var4;
      this._fieldMinLevel = var5;
   }

   public VisibilityChecker$Std(JsonAutoDetect$Visibility var1) {
      super();
      if (var1 == JsonAutoDetect$Visibility.DEFAULT) {
         this._getterMinLevel = DEFAULT._getterMinLevel;
         this._isGetterMinLevel = DEFAULT._isGetterMinLevel;
         this._setterMinLevel = DEFAULT._setterMinLevel;
         this._creatorMinLevel = DEFAULT._creatorMinLevel;
         this._fieldMinLevel = DEFAULT._fieldMinLevel;
      } else {
         this._getterMinLevel = var1;
         this._isGetterMinLevel = var1;
         this._setterMinLevel = var1;
         this._creatorMinLevel = var1;
         this._fieldMinLevel = var1;
      }

   }

   public static VisibilityChecker$Std construct(JsonAutoDetect$Value var0) {
      return DEFAULT.withOverrides(var0);
   }

   protected VisibilityChecker$Std _with(JsonAutoDetect$Visibility var1, JsonAutoDetect$Visibility var2, JsonAutoDetect$Visibility var3, JsonAutoDetect$Visibility var4, JsonAutoDetect$Visibility var5) {
      return var1 == this._getterMinLevel && var2 == this._isGetterMinLevel && var3 == this._setterMinLevel && var4 == this._creatorMinLevel && var5 == this._fieldMinLevel ? this : new VisibilityChecker$Std(var1, var2, var3, var4, var5);
   }

   public VisibilityChecker$Std with(JsonAutoDetect var1) {
      return var1 != null ? this._with(this._defaultOrOverride(this._getterMinLevel, var1.getterVisibility()), this._defaultOrOverride(this._isGetterMinLevel, var1.isGetterVisibility()), this._defaultOrOverride(this._setterMinLevel, var1.setterVisibility()), this._defaultOrOverride(this._creatorMinLevel, var1.creatorVisibility()), this._defaultOrOverride(this._fieldMinLevel, var1.fieldVisibility())) : this;
   }

   public VisibilityChecker$Std withOverrides(JsonAutoDetect$Value var1) {
      return var1 != null ? this._with(this._defaultOrOverride(this._getterMinLevel, var1.getGetterVisibility()), this._defaultOrOverride(this._isGetterMinLevel, var1.getIsGetterVisibility()), this._defaultOrOverride(this._setterMinLevel, var1.getSetterVisibility()), this._defaultOrOverride(this._creatorMinLevel, var1.getCreatorVisibility()), this._defaultOrOverride(this._fieldMinLevel, var1.getFieldVisibility())) : this;
   }

   private JsonAutoDetect$Visibility _defaultOrOverride(JsonAutoDetect$Visibility var1, JsonAutoDetect$Visibility var2) {
      return var2 == JsonAutoDetect$Visibility.DEFAULT ? var1 : var2;
   }

   public VisibilityChecker$Std with(JsonAutoDetect$Visibility var1) {
      return var1 == JsonAutoDetect$Visibility.DEFAULT ? DEFAULT : new VisibilityChecker$Std(var1);
   }

   public VisibilityChecker$Std withVisibility(PropertyAccessor var1, JsonAutoDetect$Visibility var2) {
      // $FF: Couldn't be decompiled
   }

   public VisibilityChecker$Std withGetterVisibility(JsonAutoDetect$Visibility var1) {
      if (var1 == JsonAutoDetect$Visibility.DEFAULT) {
         var1 = DEFAULT._getterMinLevel;
      }

      return this._getterMinLevel == var1 ? this : new VisibilityChecker$Std(var1, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
   }

   public VisibilityChecker$Std withIsGetterVisibility(JsonAutoDetect$Visibility var1) {
      if (var1 == JsonAutoDetect$Visibility.DEFAULT) {
         var1 = DEFAULT._isGetterMinLevel;
      }

      return this._isGetterMinLevel == var1 ? this : new VisibilityChecker$Std(this._getterMinLevel, var1, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
   }

   public VisibilityChecker$Std withSetterVisibility(JsonAutoDetect$Visibility var1) {
      if (var1 == JsonAutoDetect$Visibility.DEFAULT) {
         var1 = DEFAULT._setterMinLevel;
      }

      return this._setterMinLevel == var1 ? this : new VisibilityChecker$Std(this._getterMinLevel, this._isGetterMinLevel, var1, this._creatorMinLevel, this._fieldMinLevel);
   }

   public VisibilityChecker$Std withCreatorVisibility(JsonAutoDetect$Visibility var1) {
      if (var1 == JsonAutoDetect$Visibility.DEFAULT) {
         var1 = DEFAULT._creatorMinLevel;
      }

      return this._creatorMinLevel == var1 ? this : new VisibilityChecker$Std(this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, var1, this._fieldMinLevel);
   }

   public VisibilityChecker$Std withFieldVisibility(JsonAutoDetect$Visibility var1) {
      if (var1 == JsonAutoDetect$Visibility.DEFAULT) {
         var1 = DEFAULT._fieldMinLevel;
      }

      return this._fieldMinLevel == var1 ? this : new VisibilityChecker$Std(this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, var1);
   }

   public boolean isCreatorVisible(Member var1) {
      return this._creatorMinLevel.isVisible(var1);
   }

   public boolean isCreatorVisible(AnnotatedMember var1) {
      return this.isCreatorVisible(var1.getMember());
   }

   public boolean isFieldVisible(Field var1) {
      return this._fieldMinLevel.isVisible(var1);
   }

   public boolean isFieldVisible(AnnotatedField var1) {
      return this.isFieldVisible(var1.getAnnotated());
   }

   public boolean isGetterVisible(Method var1) {
      return this._getterMinLevel.isVisible(var1);
   }

   public boolean isGetterVisible(AnnotatedMethod var1) {
      return this.isGetterVisible(var1.getAnnotated());
   }

   public boolean isIsGetterVisible(Method var1) {
      return this._isGetterMinLevel.isVisible(var1);
   }

   public boolean isIsGetterVisible(AnnotatedMethod var1) {
      return this.isIsGetterVisible(var1.getAnnotated());
   }

   public boolean isSetterVisible(Method var1) {
      return this._setterMinLevel.isVisible(var1);
   }

   public boolean isSetterVisible(AnnotatedMethod var1) {
      return this.isSetterVisible(var1.getAnnotated());
   }

   public String toString() {
      return String.format("[Visibility: getter=%s,isGetter=%s,setter=%s,creator=%s,field=%s]", this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
   }

   public VisibilityChecker withFieldVisibility(JsonAutoDetect$Visibility var1) {
      return this.withFieldVisibility(var1);
   }

   public VisibilityChecker withCreatorVisibility(JsonAutoDetect$Visibility var1) {
      return this.withCreatorVisibility(var1);
   }

   public VisibilityChecker withSetterVisibility(JsonAutoDetect$Visibility var1) {
      return this.withSetterVisibility(var1);
   }

   public VisibilityChecker withIsGetterVisibility(JsonAutoDetect$Visibility var1) {
      return this.withIsGetterVisibility(var1);
   }

   public VisibilityChecker withGetterVisibility(JsonAutoDetect$Visibility var1) {
      return this.withGetterVisibility(var1);
   }

   public VisibilityChecker withVisibility(PropertyAccessor var1, JsonAutoDetect$Visibility var2) {
      return this.withVisibility(var1, var2);
   }

   public VisibilityChecker with(JsonAutoDetect$Visibility var1) {
      return this.with(var1);
   }

   public VisibilityChecker withOverrides(JsonAutoDetect$Value var1) {
      return this.withOverrides(var1);
   }

   public VisibilityChecker with(JsonAutoDetect var1) {
      return this.with(var1);
   }

   static {
      DEFAULT = new VisibilityChecker$Std(JsonAutoDetect$Visibility.PUBLIC_ONLY, JsonAutoDetect$Visibility.PUBLIC_ONLY, JsonAutoDetect$Visibility.ANY, JsonAutoDetect$Visibility.ANY, JsonAutoDetect$Visibility.PUBLIC_ONLY);
   }
}
