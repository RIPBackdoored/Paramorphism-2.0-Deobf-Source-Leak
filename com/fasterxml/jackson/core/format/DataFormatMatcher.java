package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.MergedStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataFormatMatcher {
   protected final InputStream _originalStream;
   protected final byte[] _bufferedData;
   protected final int _bufferedStart;
   protected final int _bufferedLength;
   protected final JsonFactory _match;
   protected final MatchStrength _matchStrength;

   protected DataFormatMatcher(InputStream var1, byte[] var2, int var3, int var4, JsonFactory var5, MatchStrength var6) {
      super();
      this._originalStream = var1;
      this._bufferedData = var2;
      this._bufferedStart = var3;
      this._bufferedLength = var4;
      this._match = var5;
      this._matchStrength = var6;
      if ((var3 | var4) < 0 || var3 + var4 > var2.length) {
         throw new IllegalArgumentException(String.format("Illegal start/length (%d/%d) wrt input array of %d bytes", var3, var4, var2.length));
      }
   }

   public boolean hasMatch() {
      return this._match != null;
   }

   public MatchStrength getMatchStrength() {
      return this._matchStrength == null ? MatchStrength.INCONCLUSIVE : this._matchStrength;
   }

   public JsonFactory getMatch() {
      return this._match;
   }

   public String getMatchedFormatName() {
      return this._match.getFormatName();
   }

   public JsonParser createParserWithMatch() throws IOException {
      if (this._match == null) {
         return null;
      } else {
         return this._originalStream == null ? this._match.createParser(this._bufferedData, this._bufferedStart, this._bufferedLength) : this._match.createParser(this.getDataStream());
      }
   }

   public InputStream getDataStream() {
      return (InputStream)(this._originalStream == null ? new ByteArrayInputStream(this._bufferedData, this._bufferedStart, this._bufferedLength) : new MergedStream((IOContext)null, this._originalStream, this._bufferedData, this._bufferedStart, this._bufferedLength));
   }
}
