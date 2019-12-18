package com.fasterxml.jackson.dataformat.yaml;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class YAMLMapper extends ObjectMapper {
   private static final long serialVersionUID = 1L;

   public YAMLMapper() {
      this(new YAMLFactory());
   }

   public YAMLMapper(YAMLFactory var1) {
      super((JsonFactory)var1);
   }

   public YAMLMapper(YAMLMapper var1) {
      super((ObjectMapper)var1);
   }

   public YAMLMapper copy() {
      this._checkInvalidCopy(YAMLMapper.class);
      return new YAMLMapper(this);
   }

   public YAMLMapper configure(YAMLGenerator$Feature var1, boolean var2) {
      return var2 ? this.enable(var1) : this.disable(var1);
   }

   public YAMLMapper configure(YAMLParser$Feature var1, boolean var2) {
      return var2 ? this.enable(var1) : this.disable(var1);
   }

   public YAMLMapper enable(YAMLGenerator$Feature var1) {
      ((YAMLFactory)this._jsonFactory).enable(var1);
      return this;
   }

   public YAMLMapper enable(YAMLParser$Feature var1) {
      ((YAMLFactory)this._jsonFactory).enable(var1);
      return this;
   }

   public YAMLMapper disable(YAMLGenerator$Feature var1) {
      ((YAMLFactory)this._jsonFactory).disable(var1);
      return this;
   }

   public YAMLMapper disable(YAMLParser$Feature var1) {
      ((YAMLFactory)this._jsonFactory).disable(var1);
      return this;
   }

   public final YAMLFactory getFactory() {
      return (YAMLFactory)this._jsonFactory;
   }

   public JsonFactory getFactory() {
      return this.getFactory();
   }

   public ObjectMapper copy() {
      return this.copy();
   }
}
