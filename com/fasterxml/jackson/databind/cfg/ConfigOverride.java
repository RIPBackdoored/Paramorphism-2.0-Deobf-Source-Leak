package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.JsonAutoDetect$Value;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties$Value;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.annotation.JsonSetter$Value;

public abstract class ConfigOverride {
   protected JsonFormat$Value _format;
   protected JsonInclude$Value _include;
   protected JsonInclude$Value _includeAsProperty;
   protected JsonIgnoreProperties$Value _ignorals;
   protected JsonSetter$Value _setterInfo;
   protected JsonAutoDetect$Value _visibility;
   protected Boolean _isIgnoredType;
   protected Boolean _mergeable;

   protected ConfigOverride() {
      super();
   }

   protected ConfigOverride(ConfigOverride var1) {
      super();
      this._format = var1._format;
      this._include = var1._include;
      this._includeAsProperty = var1._includeAsProperty;
      this._ignorals = var1._ignorals;
      this._setterInfo = var1._setterInfo;
      this._visibility = var1._visibility;
      this._isIgnoredType = var1._isIgnoredType;
      this._mergeable = var1._mergeable;
   }

   public static ConfigOverride empty() {
      return ConfigOverride$Empty.INSTANCE;
   }

   public JsonFormat$Value getFormat() {
      return this._format;
   }

   public JsonInclude$Value getInclude() {
      return this._include;
   }

   public JsonInclude$Value getIncludeAsProperty() {
      return this._includeAsProperty;
   }

   public JsonIgnoreProperties$Value getIgnorals() {
      return this._ignorals;
   }

   public Boolean getIsIgnoredType() {
      return this._isIgnoredType;
   }

   public JsonSetter$Value getSetterInfo() {
      return this._setterInfo;
   }

   public JsonAutoDetect$Value getVisibility() {
      return this._visibility;
   }

   public Boolean getMergeable() {
      return this._mergeable;
   }
}
