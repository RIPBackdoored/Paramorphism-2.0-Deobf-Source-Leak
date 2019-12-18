package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.format.InputAccessor$Std;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.InputStream;

public class DataFormatReaders$AccessorForReader extends InputAccessor$Std {
   final DataFormatReaders this$0;

   public DataFormatReaders$AccessorForReader(DataFormatReaders var1, InputStream var2, byte[] var3) {
      super(var2, var3);
      this.this$0 = var1;
   }

   public DataFormatReaders$AccessorForReader(DataFormatReaders var1, byte[] var2) {
      super(var2);
      this.this$0 = var1;
   }

   public DataFormatReaders$AccessorForReader(DataFormatReaders var1, byte[] var2, int var3, int var4) {
      super(var2, var3, var4);
      this.this$0 = var1;
   }

   public DataFormatReaders$Match createMatcher(ObjectReader var1, MatchStrength var2) {
      return new DataFormatReaders$Match(this._in, this._buffer, this._bufferedStart, this._bufferedEnd - this._bufferedStart, var1, var2);
   }
}
