package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.MergedStream;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataFormatReaders$Match {
   protected final InputStream _originalStream;
   protected final byte[] _bufferedData;
   protected final int _bufferedStart;
   protected final int _bufferedLength;
   protected final ObjectReader _match;
   protected final MatchStrength _matchStrength;

   protected DataFormatReaders$Match(InputStream var1, byte[] var2, int var3, int var4, ObjectReader var5, MatchStrength var6) {
      super();
      this._originalStream = var1;
      this._bufferedData = var2;
      this._bufferedStart = var3;
      this._bufferedLength = var4;
      this._match = var5;
      this._matchStrength = var6;
   }

   public boolean hasMatch() {
      return this._match != null;
   }

   public MatchStrength getMatchStrength() {
      return this._matchStrength == null ? MatchStrength.INCONCLUSIVE : this._matchStrength;
   }

   public ObjectReader getReader() {
      return this._match;
   }

   public String getMatchedFormatName() {
      return this._match.getFactory().getFormatName();
   }

   public JsonParser createParserWithMatch() throws IOException {
      if (this._match == null) {
         return null;
      } else {
         JsonFactory var1 = this._match.getFactory();
         return this._originalStream == null ? var1.createParser(this._bufferedData, this._bufferedStart, this._bufferedLength) : var1.createParser(this.getDataStream());
      }
   }

   public InputStream getDataStream() {
      return (InputStream)(this._originalStream == null ? new ByteArrayInputStream(this._bufferedData, this._bufferedStart, this._bufferedLength) : new MergedStream((IOContext)null, this._originalStream, this._bufferedData, this._bufferedStart, this._bufferedLength));
   }
}
