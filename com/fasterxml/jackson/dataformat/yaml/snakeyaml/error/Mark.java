package com.fasterxml.jackson.dataformat.yaml.snakeyaml.error;

/** @deprecated */
@Deprecated
public class Mark {
   protected final org.yaml.snakeyaml.error.Mark _source;

   protected Mark(org.yaml.snakeyaml.error.Mark var1) {
      super();
      this._source = var1;
   }

   public static Mark from(org.yaml.snakeyaml.error.Mark var0) {
      return var0 == null ? null : new Mark(var0);
   }

   public String getName() {
      return this._source.getName();
   }

   public String get_snippet() {
      return this._source.get_snippet();
   }

   public String get_snippet(int var1, int var2) {
      return this._source.get_snippet(var1, var2);
   }

   public int getColumn() {
      return this._source.getColumn();
   }

   public int getLine() {
      return this._source.getLine();
   }

   public int getIndex() {
      return this._source.getIndex();
   }
}
