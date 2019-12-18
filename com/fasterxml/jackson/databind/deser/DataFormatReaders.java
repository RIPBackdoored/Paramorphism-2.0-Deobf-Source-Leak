package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class DataFormatReaders {
   public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
   protected final ObjectReader[] _readers;
   protected final MatchStrength _optimalMatch;
   protected final MatchStrength _minimalMatch;
   protected final int _maxInputLookahead;

   public DataFormatReaders(ObjectReader... var1) {
      this(var1, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
   }

   public DataFormatReaders(Collection var1) {
      this((ObjectReader[])var1.toArray(new ObjectReader[var1.size()]));
   }

   private DataFormatReaders(ObjectReader[] var1, MatchStrength var2, MatchStrength var3, int var4) {
      super();
      this._readers = var1;
      this._optimalMatch = var2;
      this._minimalMatch = var3;
      this._maxInputLookahead = var4;
   }

   public DataFormatReaders withOptimalMatch(MatchStrength var1) {
      return var1 == this._optimalMatch ? this : new DataFormatReaders(this._readers, var1, this._minimalMatch, this._maxInputLookahead);
   }

   public DataFormatReaders withMinimalMatch(MatchStrength var1) {
      return var1 == this._minimalMatch ? this : new DataFormatReaders(this._readers, this._optimalMatch, var1, this._maxInputLookahead);
   }

   public DataFormatReaders with(ObjectReader[] var1) {
      return new DataFormatReaders(var1, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
   }

   public DataFormatReaders withMaxInputLookahead(int var1) {
      return var1 == this._maxInputLookahead ? this : new DataFormatReaders(this._readers, this._optimalMatch, this._minimalMatch, var1);
   }

   public DataFormatReaders with(DeserializationConfig var1) {
      int var2 = this._readers.length;
      ObjectReader[] var3 = new ObjectReader[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         var3[var4] = this._readers[var4].with(var1);
      }

      return new DataFormatReaders(var3, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
   }

   public DataFormatReaders withType(JavaType var1) {
      int var2 = this._readers.length;
      ObjectReader[] var3 = new ObjectReader[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         var3[var4] = this._readers[var4].forType(var1);
      }

      return new DataFormatReaders(var3, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
   }

   public DataFormatReaders$Match findFormat(InputStream var1) throws IOException {
      return this._findFormat(new DataFormatReaders$AccessorForReader(this, var1, new byte[this._maxInputLookahead]));
   }

   public DataFormatReaders$Match findFormat(byte[] var1) throws IOException {
      return this._findFormat(new DataFormatReaders$AccessorForReader(this, var1));
   }

   public DataFormatReaders$Match findFormat(byte[] var1, int var2, int var3) throws IOException {
      return this._findFormat(new DataFormatReaders$AccessorForReader(this, var1, var2, var3));
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append('[');
      int var2 = this._readers.length;
      if (var2 > 0) {
         var1.append(this._readers[0].getFactory().getFormatName());

         for(int var3 = 1; var3 < var2; ++var3) {
            var1.append(", ");
            var1.append(this._readers[var3].getFactory().getFormatName());
         }
      }

      var1.append(']');
      return var1.toString();
   }

   private DataFormatReaders$Match _findFormat(DataFormatReaders$AccessorForReader var1) throws IOException {
      ObjectReader var2 = null;
      MatchStrength var3 = null;
      ObjectReader[] var4 = this._readers;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ObjectReader var7 = var4[var6];
         var1.reset();
         MatchStrength var8 = var7.getFactory().hasFormat(var1);
         if (var8 != null && var8.ordinal() >= this._minimalMatch.ordinal() && (var2 == null || var3.ordinal() < var8.ordinal())) {
            var2 = var7;
            var3 = var8;
            if (var8.ordinal() >= this._optimalMatch.ordinal()) {
               break;
            }
         }
      }

      return var1.createMatcher(var2, var3);
   }
}
