package com.fasterxml.jackson.dataformat.yaml.snakeyaml.error;

import com.fasterxml.jackson.core.JsonParser;

/** @deprecated */
@Deprecated
public class MarkedYAMLException extends YAMLException {
   private static final long serialVersionUID = 1L;
   protected final org.yaml.snakeyaml.error.MarkedYAMLException _source;

   protected MarkedYAMLException(JsonParser var1, org.yaml.snakeyaml.error.MarkedYAMLException var2) {
      super(var1, var2);
      this._source = var2;
   }

   public static MarkedYAMLException from(JsonParser var0, org.yaml.snakeyaml.error.MarkedYAMLException var1) {
      return new MarkedYAMLException(var0, var1);
   }

   public String getContext() {
      return this._source.getContext();
   }

   public Mark getContextMark() {
      return Mark.from(this._source.getContextMark());
   }

   public String getProblem() {
      return this._source.getProblem();
   }

   public Mark getProblemMark() {
      return Mark.from(this._source.getProblemMark());
   }
}
