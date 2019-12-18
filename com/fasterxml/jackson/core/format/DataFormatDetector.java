package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class DataFormatDetector {
   public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
   protected final JsonFactory[] _detectors;
   protected final MatchStrength _optimalMatch;
   protected final MatchStrength _minimalMatch;
   protected final int _maxInputLookahead;

   public DataFormatDetector(JsonFactory... var1) {
      this(var1, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
   }

   public DataFormatDetector(Collection var1) {
      this((JsonFactory[])var1.toArray(new JsonFactory[var1.size()]));
   }

   public DataFormatDetector withOptimalMatch(MatchStrength var1) {
      return var1 == this._optimalMatch ? this : new DataFormatDetector(this._detectors, var1, this._minimalMatch, this._maxInputLookahead);
   }

   public DataFormatDetector withMinimalMatch(MatchStrength var1) {
      return var1 == this._minimalMatch ? this : new DataFormatDetector(this._detectors, this._optimalMatch, var1, this._maxInputLookahead);
   }

   public DataFormatDetector withMaxInputLookahead(int var1) {
      return var1 == this._maxInputLookahead ? this : new DataFormatDetector(this._detectors, this._optimalMatch, this._minimalMatch, var1);
   }

   private DataFormatDetector(JsonFactory[] var1, MatchStrength var2, MatchStrength var3, int var4) {
      super();
      this._detectors = var1;
      this._optimalMatch = var2;
      this._minimalMatch = var3;
      this._maxInputLookahead = var4;
   }

   public DataFormatMatcher findFormat(InputStream var1) throws IOException {
      return this._findFormat(new InputAccessor$Std(var1, new byte[this._maxInputLookahead]));
   }

   public DataFormatMatcher findFormat(byte[] var1) throws IOException {
      return this._findFormat(new InputAccessor$Std(var1));
   }

   public DataFormatMatcher findFormat(byte[] var1, int var2, int var3) throws IOException {
      return this._findFormat(new InputAccessor$Std(var1, var2, var3));
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append('[');
      int var2 = this._detectors.length;
      if (var2 > 0) {
         var1.append(this._detectors[0].getFormatName());

         for(int var3 = 1; var3 < var2; ++var3) {
            var1.append(", ");
            var1.append(this._detectors[var3].getFormatName());
         }
      }

      var1.append(']');
      return var1.toString();
   }

   private DataFormatMatcher _findFormat(InputAccessor$Std var1) throws IOException {
      JsonFactory var2 = null;
      MatchStrength var3 = null;
      JsonFactory[] var4 = this._detectors;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         JsonFactory var7 = var4[var6];
         var1.reset();
         MatchStrength var8 = var7.hasFormat(var1);
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
