package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.Nulls;
import java.io.Serializable;

public class PropertyMetadata implements Serializable {
   private static final long serialVersionUID = -1L;
   public static final PropertyMetadata STD_REQUIRED;
   public static final PropertyMetadata STD_OPTIONAL;
   public static final PropertyMetadata STD_REQUIRED_OR_OPTIONAL;
   protected final Boolean _required;
   protected final String _description;
   protected final Integer _index;
   protected final String _defaultValue;
   protected final transient PropertyMetadata$MergeInfo _mergeInfo;
   protected Nulls _valueNulls;
   protected Nulls _contentNulls;

   protected PropertyMetadata(Boolean var1, String var2, Integer var3, String var4, PropertyMetadata$MergeInfo var5, Nulls var6, Nulls var7) {
      super();
      this._required = var1;
      this._description = var2;
      this._index = var3;
      this._defaultValue = var4 != null && !var4.isEmpty() ? var4 : null;
      this._mergeInfo = var5;
      this._valueNulls = var6;
      this._contentNulls = var7;
   }

   public static PropertyMetadata construct(Boolean var0, String var1, Integer var2, String var3) {
      if (var1 == null && var2 == null && var3 == null) {
         if (var0 == null) {
            return STD_REQUIRED_OR_OPTIONAL;
         } else {
            return var0 ? STD_REQUIRED : STD_OPTIONAL;
         }
      } else {
         return new PropertyMetadata(var0, var1, var2, var3, (PropertyMetadata$MergeInfo)null, (Nulls)null, (Nulls)null);
      }
   }

   /** @deprecated */
   @Deprecated
   public static PropertyMetadata construct(boolean var0, String var1, Integer var2, String var3) {
      if (var1 == null && var2 == null && var3 == null) {
         return var0 ? STD_REQUIRED : STD_OPTIONAL;
      } else {
         return new PropertyMetadata(var0, var1, var2, var3, (PropertyMetadata$MergeInfo)null, (Nulls)null, (Nulls)null);
      }
   }

   protected Object readResolve() {
      if (this._description == null && this._index == null && this._defaultValue == null && this._mergeInfo == null && this._valueNulls == null && this._contentNulls == null) {
         if (this._required == null) {
            return STD_REQUIRED_OR_OPTIONAL;
         } else {
            return this._required ? STD_REQUIRED : STD_OPTIONAL;
         }
      } else {
         return this;
      }
   }

   public PropertyMetadata withDescription(String var1) {
      return new PropertyMetadata(this._required, var1, this._index, this._defaultValue, this._mergeInfo, this._valueNulls, this._contentNulls);
   }

   public PropertyMetadata withMergeInfo(PropertyMetadata$MergeInfo var1) {
      return new PropertyMetadata(this._required, this._description, this._index, this._defaultValue, var1, this._valueNulls, this._contentNulls);
   }

   public PropertyMetadata withNulls(Nulls var1, Nulls var2) {
      return new PropertyMetadata(this._required, this._description, this._index, this._defaultValue, this._mergeInfo, var1, var2);
   }

   public PropertyMetadata withDefaultValue(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         if (var1.equals(this._defaultValue)) {
            return this;
         }
      } else {
         if (this._defaultValue == null) {
            return this;
         }

         var1 = null;
      }

      return new PropertyMetadata(this._required, this._description, this._index, var1, this._mergeInfo, this._valueNulls, this._contentNulls);
   }

   public PropertyMetadata withIndex(Integer var1) {
      return new PropertyMetadata(this._required, this._description, var1, this._defaultValue, this._mergeInfo, this._valueNulls, this._contentNulls);
   }

   public PropertyMetadata withRequired(Boolean var1) {
      if (var1 == null) {
         if (this._required == null) {
            return this;
         }
      } else if (var1.equals(this._required)) {
         return this;
      }

      return new PropertyMetadata(var1, this._description, this._index, this._defaultValue, this._mergeInfo, this._valueNulls, this._contentNulls);
   }

   public String getDescription() {
      return this._description;
   }

   public String getDefaultValue() {
      return this._defaultValue;
   }

   public boolean hasDefaultValue() {
      return this._defaultValue != null;
   }

   public boolean isRequired() {
      return this._required != null && this._required;
   }

   public Boolean getRequired() {
      return this._required;
   }

   public Integer getIndex() {
      return this._index;
   }

   public boolean hasIndex() {
      return this._index != null;
   }

   public PropertyMetadata$MergeInfo getMergeInfo() {
      return this._mergeInfo;
   }

   public Nulls getValueNulls() {
      return this._valueNulls;
   }

   public Nulls getContentNulls() {
      return this._contentNulls;
   }

   static {
      STD_REQUIRED = new PropertyMetadata(Boolean.TRUE, (String)null, (Integer)null, (String)null, (PropertyMetadata$MergeInfo)null, (Nulls)null, (Nulls)null);
      STD_OPTIONAL = new PropertyMetadata(Boolean.FALSE, (String)null, (Integer)null, (String)null, (PropertyMetadata$MergeInfo)null, (Nulls)null, (Nulls)null);
      STD_REQUIRED_OR_OPTIONAL = new PropertyMetadata((Boolean)null, (String)null, (Integer)null, (String)null, (PropertyMetadata$MergeInfo)null, (Nulls)null, (Nulls)null);
   }
}
