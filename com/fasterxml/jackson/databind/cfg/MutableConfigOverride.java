package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.JsonAutoDetect$Value;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties$Value;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.annotation.JsonSetter$Value;
import java.io.Serializable;

public class MutableConfigOverride extends ConfigOverride implements Serializable {
   private static final long serialVersionUID = 1L;

   public MutableConfigOverride() {
      super();
   }

   protected MutableConfigOverride(MutableConfigOverride var1) {
      super(var1);
   }

   public MutableConfigOverride copy() {
      return new MutableConfigOverride(this);
   }

   public MutableConfigOverride setFormat(JsonFormat$Value var1) {
      this._format = var1;
      return this;
   }

   public MutableConfigOverride setInclude(JsonInclude$Value var1) {
      this._include = var1;
      return this;
   }

   public MutableConfigOverride setIncludeAsProperty(JsonInclude$Value var1) {
      this._includeAsProperty = var1;
      return this;
   }

   public MutableConfigOverride setIgnorals(JsonIgnoreProperties$Value var1) {
      this._ignorals = var1;
      return this;
   }

   public MutableConfigOverride setIsIgnoredType(Boolean var1) {
      this._isIgnoredType = var1;
      return this;
   }

   public MutableConfigOverride setSetterInfo(JsonSetter$Value var1) {
      this._setterInfo = var1;
      return this;
   }

   public MutableConfigOverride setVisibility(JsonAutoDetect$Value var1) {
      this._visibility = var1;
      return this;
   }

   public MutableConfigOverride setMergeable(Boolean var1) {
      this._mergeable = var1;
      return this;
   }
}
