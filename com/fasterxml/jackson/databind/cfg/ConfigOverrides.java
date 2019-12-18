package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.annotation.JsonSetter$Value;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker$Std;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ConfigOverrides implements Serializable {
   private static final long serialVersionUID = 1L;
   protected Map _overrides;
   protected JsonInclude$Value _defaultInclusion;
   protected JsonSetter$Value _defaultSetterInfo;
   protected VisibilityChecker _visibilityChecker;
   protected Boolean _defaultMergeable;

   public ConfigOverrides() {
      this((Map)null, JsonInclude$Value.empty(), JsonSetter$Value.empty(), VisibilityChecker$Std.defaultInstance(), (Boolean)null);
   }

   protected ConfigOverrides(Map var1, JsonInclude$Value var2, JsonSetter$Value var3, VisibilityChecker var4, Boolean var5) {
      super();
      this._overrides = var1;
      this._defaultInclusion = var2;
      this._defaultSetterInfo = var3;
      this._visibilityChecker = var4;
      this._defaultMergeable = var5;
   }

   public ConfigOverrides copy() {
      Map var1;
      if (this._overrides == null) {
         var1 = null;
      } else {
         var1 = this._newMap();
         Iterator var2 = this._overrides.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            var1.put(var3.getKey(), ((MutableConfigOverride)var3.getValue()).copy());
         }
      }

      return new ConfigOverrides(var1, this._defaultInclusion, this._defaultSetterInfo, this._visibilityChecker, this._defaultMergeable);
   }

   public ConfigOverride findOverride(Class var1) {
      return this._overrides == null ? null : (ConfigOverride)this._overrides.get(var1);
   }

   public MutableConfigOverride findOrCreateOverride(Class var1) {
      if (this._overrides == null) {
         this._overrides = this._newMap();
      }

      MutableConfigOverride var2 = (MutableConfigOverride)this._overrides.get(var1);
      if (var2 == null) {
         var2 = new MutableConfigOverride();
         this._overrides.put(var1, var2);
      }

      return var2;
   }

   public JsonInclude$Value getDefaultInclusion() {
      return this._defaultInclusion;
   }

   public JsonSetter$Value getDefaultSetterInfo() {
      return this._defaultSetterInfo;
   }

   public Boolean getDefaultMergeable() {
      return this._defaultMergeable;
   }

   public VisibilityChecker getDefaultVisibility() {
      return this._visibilityChecker;
   }

   public void setDefaultInclusion(JsonInclude$Value var1) {
      this._defaultInclusion = var1;
   }

   public void setDefaultSetterInfo(JsonSetter$Value var1) {
      this._defaultSetterInfo = var1;
   }

   public void setDefaultMergeable(Boolean var1) {
      this._defaultMergeable = var1;
   }

   public void setDefaultVisibility(VisibilityChecker var1) {
      this._visibilityChecker = var1;
   }

   protected Map _newMap() {
      return new HashMap();
   }
}
