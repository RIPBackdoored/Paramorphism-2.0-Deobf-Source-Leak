package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.util.JsonGeneratorDelegate;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class FilteringGeneratorDelegate extends JsonGeneratorDelegate {
   protected TokenFilter rootFilter;
   protected boolean _allowMultipleMatches;
   protected boolean _includePath;
   /** @deprecated */
   @Deprecated
   protected boolean _includeImmediateParent;
   protected TokenFilterContext _filterContext;
   protected TokenFilter _itemFilter;
   protected int _matchCount;

   public FilteringGeneratorDelegate(JsonGenerator var1, TokenFilter var2, boolean var3, boolean var4) {
      super(var1, false);
      this.rootFilter = var2;
      this._itemFilter = var2;
      this._filterContext = TokenFilterContext.createRootContext(var2);
      this._includePath = var3;
      this._allowMultipleMatches = var4;
   }

   public TokenFilter getFilter() {
      return this.rootFilter;
   }

   public JsonStreamContext getFilterContext() {
      return this._filterContext;
   }

   public int getMatchCount() {
      return this._matchCount;
   }

   public JsonStreamContext getOutputContext() {
      return this._filterContext;
   }

   public void writeStartArray() throws IOException {
      if (this._itemFilter == null) {
         this._filterContext = this._filterContext.createChildArrayContext((TokenFilter)null, false);
      } else if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
         this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
         this.delegate.writeStartArray();
      } else {
         this._itemFilter = this._filterContext.checkValue(this._itemFilter);
         if (this._itemFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext((TokenFilter)null, false);
         } else {
            if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
               this._itemFilter = this._itemFilter.filterStartArray();
            }

            if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
               this._checkParentPath();
               this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
               this.delegate.writeStartArray();
            } else {
               this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
            }

         }
      }
   }

   public void writeStartArray(int var1) throws IOException {
      if (this._itemFilter == null) {
         this._filterContext = this._filterContext.createChildArrayContext((TokenFilter)null, false);
      } else if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
         this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
         this.delegate.writeStartArray(var1);
      } else {
         this._itemFilter = this._filterContext.checkValue(this._itemFilter);
         if (this._itemFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext((TokenFilter)null, false);
         } else {
            if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
               this._itemFilter = this._itemFilter.filterStartArray();
            }

            if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
               this._checkParentPath();
               this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
               this.delegate.writeStartArray(var1);
            } else {
               this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
            }

         }
      }
   }

   public void writeEndArray() throws IOException {
      this._filterContext = this._filterContext.closeArray(this.delegate);
      if (this._filterContext != null) {
         this._itemFilter = this._filterContext.getFilter();
      }

   }

   public void writeStartObject() throws IOException {
      if (this._itemFilter == null) {
         this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, false);
      } else if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
         this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
         this.delegate.writeStartObject();
      } else {
         TokenFilter var1 = this._filterContext.checkValue(this._itemFilter);
         if (var1 != null) {
            if (var1 != TokenFilter.INCLUDE_ALL) {
               var1 = var1.filterStartObject();
            }

            if (var1 == TokenFilter.INCLUDE_ALL) {
               this._checkParentPath();
               this._filterContext = this._filterContext.createChildObjectContext(var1, true);
               this.delegate.writeStartObject();
            } else {
               this._filterContext = this._filterContext.createChildObjectContext(var1, false);
            }

         }
      }
   }

   public void writeStartObject(Object var1) throws IOException {
      if (this._itemFilter == null) {
         this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, false);
      } else if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
         this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
         this.delegate.writeStartObject(var1);
      } else {
         TokenFilter var2 = this._filterContext.checkValue(this._itemFilter);
         if (var2 != null) {
            if (var2 != TokenFilter.INCLUDE_ALL) {
               var2 = var2.filterStartObject();
            }

            if (var2 == TokenFilter.INCLUDE_ALL) {
               this._checkParentPath();
               this._filterContext = this._filterContext.createChildObjectContext(var2, true);
               this.delegate.writeStartObject(var1);
            } else {
               this._filterContext = this._filterContext.createChildObjectContext(var2, false);
            }

         }
      }
   }

   public void writeEndObject() throws IOException {
      this._filterContext = this._filterContext.closeObject(this.delegate);
      if (this._filterContext != null) {
         this._itemFilter = this._filterContext.getFilter();
      }

   }

   public void writeFieldName(String var1) throws IOException {
      TokenFilter var2 = this._filterContext.setFieldName(var1);
      if (var2 == null) {
         this._itemFilter = null;
      } else if (var2 == TokenFilter.INCLUDE_ALL) {
         this._itemFilter = var2;
         this.delegate.writeFieldName(var1);
      } else {
         var2 = var2.includeProperty(var1);
         this._itemFilter = var2;
         if (var2 == TokenFilter.INCLUDE_ALL) {
            this._checkPropertyParentPath();
         }

      }
   }

   public void writeFieldName(SerializableString var1) throws IOException {
      TokenFilter var2 = this._filterContext.setFieldName(var1.getValue());
      if (var2 == null) {
         this._itemFilter = null;
      } else if (var2 == TokenFilter.INCLUDE_ALL) {
         this._itemFilter = var2;
         this.delegate.writeFieldName(var1);
      } else {
         var2 = var2.includeProperty(var1.getValue());
         this._itemFilter = var2;
         if (var2 == TokenFilter.INCLUDE_ALL) {
            this._checkPropertyParentPath();
         }

      }
   }

   public void writeString(String var1) throws IOException {
      if (this._itemFilter != null) {
         if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            TokenFilter var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeString(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeString(var1);
      }
   }

   public void writeString(char[] var1, int var2, int var3) throws IOException {
      if (this._itemFilter != null) {
         if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            String var4 = new String(var1, var2, var3);
            TokenFilter var5 = this._filterContext.checkValue(this._itemFilter);
            if (var5 == null) {
               return;
            }

            if (var5 != TokenFilter.INCLUDE_ALL && !var5.includeString(var4)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeString(var1, var2, var3);
      }
   }

   public void writeString(SerializableString var1) throws IOException {
      if (this._itemFilter != null) {
         if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            TokenFilter var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeString(var1.getValue())) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeString(var1);
      }
   }

   public void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRawUTF8String(var1, var2, var3);
      }

   }

   public void writeUTF8String(byte[] var1, int var2, int var3) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeUTF8String(var1, var2, var3);
      }

   }

   public void writeRaw(String var1) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRaw(var1);
      }

   }

   public void writeRaw(String var1, int var2, int var3) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRaw(var1);
      }

   }

   public void writeRaw(SerializableString var1) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRaw(var1);
      }

   }

   public void writeRaw(char[] var1, int var2, int var3) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRaw(var1, var2, var3);
      }

   }

   public void writeRaw(char var1) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRaw(var1);
      }

   }

   public void writeRawValue(String var1) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRaw(var1);
      }

   }

   public void writeRawValue(String var1, int var2, int var3) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRaw(var1, var2, var3);
      }

   }

   public void writeRawValue(char[] var1, int var2, int var3) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRaw(var1, var2, var3);
      }

   }

   public void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException {
      if (this._checkBinaryWrite()) {
         this.delegate.writeBinary(var1, var2, var3, var4);
      }

   }

   public int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException {
      return this._checkBinaryWrite() ? this.delegate.writeBinary(var1, var2, var3) : -1;
   }

   public void writeNumber(short var1) throws IOException {
      if (this._itemFilter != null) {
         if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            TokenFilter var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(int var1) throws IOException {
      if (this._itemFilter != null) {
         if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            TokenFilter var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(long var1) throws IOException {
      if (this._itemFilter != null) {
         if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            TokenFilter var3 = this._filterContext.checkValue(this._itemFilter);
            if (var3 == null) {
               return;
            }

            if (var3 != TokenFilter.INCLUDE_ALL && !var3.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(BigInteger var1) throws IOException {
      if (this._itemFilter != null) {
         if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            TokenFilter var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(double var1) throws IOException {
      if (this._itemFilter != null) {
         if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            TokenFilter var3 = this._filterContext.checkValue(this._itemFilter);
            if (var3 == null) {
               return;
            }

            if (var3 != TokenFilter.INCLUDE_ALL && !var3.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(float var1) throws IOException {
      if (this._itemFilter != null) {
         if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            TokenFilter var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(BigDecimal var1) throws IOException {
      if (this._itemFilter != null) {
         if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            TokenFilter var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(String var1) throws IOException, UnsupportedOperationException {
      if (this._itemFilter != null) {
         if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            TokenFilter var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeRawValue()) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeBoolean(boolean var1) throws IOException {
      if (this._itemFilter != null) {
         if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            TokenFilter var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeBoolean(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeBoolean(var1);
      }
   }

   public void writeNull() throws IOException {
      if (this._itemFilter != null) {
         if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            TokenFilter var1 = this._filterContext.checkValue(this._itemFilter);
            if (var1 == null) {
               return;
            }

            if (var1 != TokenFilter.INCLUDE_ALL && !var1.includeNull()) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNull();
      }
   }

   public void writeOmittedField(String var1) throws IOException {
      if (this._itemFilter != null) {
         this.delegate.writeOmittedField(var1);
      }

   }

   public void writeObjectId(Object var1) throws IOException {
      if (this._itemFilter != null) {
         this.delegate.writeObjectId(var1);
      }

   }

   public void writeObjectRef(Object var1) throws IOException {
      if (this._itemFilter != null) {
         this.delegate.writeObjectRef(var1);
      }

   }

   public void writeTypeId(Object var1) throws IOException {
      if (this._itemFilter != null) {
         this.delegate.writeTypeId(var1);
      }

   }

   protected void _checkParentPath() throws IOException {
      ++this._matchCount;
      if (this._includePath) {
         this._filterContext.writePath(this.delegate);
      }

      if (!this._allowMultipleMatches) {
         this._filterContext.skipParentChecks();
      }

   }

   protected void _checkPropertyParentPath() throws IOException {
      ++this._matchCount;
      if (this._includePath) {
         this._filterContext.writePath(this.delegate);
      } else if (this._includeImmediateParent) {
         this._filterContext.writeImmediatePath(this.delegate);
      }

      if (!this._allowMultipleMatches) {
         this._filterContext.skipParentChecks();
      }

   }

   protected boolean _checkBinaryWrite() throws IOException {
      if (this._itemFilter == null) {
         return false;
      } else if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
         return true;
      } else if (this._itemFilter.includeBinary()) {
         this._checkParentPath();
         return true;
      } else {
         return false;
      }
   }

   protected boolean _checkRawValueWrite() throws IOException {
      if (this._itemFilter == null) {
         return false;
      } else if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
         return true;
      } else if (this._itemFilter.includeRawValue()) {
         this._checkParentPath();
         return true;
      } else {
         return false;
      }
   }
}
