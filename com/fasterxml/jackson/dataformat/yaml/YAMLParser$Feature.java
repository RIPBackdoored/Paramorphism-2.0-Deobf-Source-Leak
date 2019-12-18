package com.fasterxml.jackson.dataformat.yaml;

import com.fasterxml.jackson.core.FormatFeature;

public final class YAMLParser$Feature extends Enum implements FormatFeature {
   final boolean _defaultState;
   final int _mask;
   private static final YAMLParser$Feature[] $VALUES = new YAMLParser$Feature[0];

   public static YAMLParser$Feature[] values() {
      return (YAMLParser$Feature[])$VALUES.clone();
   }

   public static YAMLParser$Feature valueOf(String var0) {
      return (YAMLParser$Feature)Enum.valueOf(YAMLParser$Feature.class, var0);
   }

   public static int collectDefaults() {
      int var0 = 0;
      YAMLParser$Feature[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         YAMLParser$Feature var4 = var1[var3];
         if (var4.enabledByDefault()) {
            var0 |= var4.getMask();
         }
      }

      return var0;
   }

   private YAMLParser$Feature(String var1, int var2, boolean var3) {
      super(var1, var2);
      this._defaultState = var3;
      this._mask = 1 << this.ordinal();
   }

   public boolean enabledByDefault() {
      return this._defaultState;
   }

   public boolean enabledIn(int var1) {
      return (var1 & this._mask) != 0;
   }

   public int getMask() {
      return this._mask;
   }
}
