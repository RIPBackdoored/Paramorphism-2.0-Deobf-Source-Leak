package com.fasterxml.jackson.core;

import java.io.Serializable;
import java.nio.charset.Charset;

public class JsonLocation implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final int MAX_CONTENT_SNIPPET = 500;
   public static final JsonLocation NA = new JsonLocation((Object)null, -1L, -1L, -1, -1);
   protected final long _totalBytes;
   protected final long _totalChars;
   protected final int _lineNr;
   protected final int _columnNr;
   final transient Object _sourceRef;

   public JsonLocation(Object var1, long var2, int var4, int var5) {
      this(var1, -1L, var2, var4, var5);
   }

   public JsonLocation(Object var1, long var2, long var4, int var6, int var7) {
      super();
      this._sourceRef = var1;
      this._totalBytes = var2;
      this._totalChars = var4;
      this._lineNr = var6;
      this._columnNr = var7;
   }

   public Object getSourceRef() {
      return this._sourceRef;
   }

   public int getLineNr() {
      return this._lineNr;
   }

   public int getColumnNr() {
      return this._columnNr;
   }

   public long getCharOffset() {
      return this._totalChars;
   }

   public long getByteOffset() {
      return this._totalBytes;
   }

   public String sourceDescription() {
      return this._appendSourceDesc(new StringBuilder(100)).toString();
   }

   public int hashCode() {
      int var1 = this._sourceRef == null ? 1 : this._sourceRef.hashCode();
      var1 ^= this._lineNr;
      var1 += this._columnNr;
      var1 ^= (int)this._totalChars;
      var1 += (int)this._totalBytes;
      return var1;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!(var1 instanceof JsonLocation)) {
         return false;
      } else {
         JsonLocation var2 = (JsonLocation)var1;
         if (this._sourceRef == null) {
            if (var2._sourceRef != null) {
               return false;
            }
         } else if (!this._sourceRef.equals(var2._sourceRef)) {
            return false;
         }

         return this._lineNr == var2._lineNr && this._columnNr == var2._columnNr && this._totalChars == var2._totalChars && this.getByteOffset() == var2.getByteOffset();
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(80);
      var1.append("[Source: ");
      this._appendSourceDesc(var1);
      var1.append("; line: ");
      var1.append(this._lineNr);
      var1.append(", column: ");
      var1.append(this._columnNr);
      var1.append(']');
      return var1.toString();
   }

   protected StringBuilder _appendSourceDesc(StringBuilder var1) {
      Object var2 = this._sourceRef;
      if (var2 == null) {
         var1.append("UNKNOWN");
         return var1;
      } else {
         Class var3 = var2 instanceof Class ? (Class)var2 : var2.getClass();
         String var4 = var3.getName();
         if (var4.startsWith("java.")) {
            var4 = var3.getSimpleName();
         } else if (var2 instanceof byte[]) {
            var4 = "byte[]";
         } else if (var2 instanceof char[]) {
            var4 = "char[]";
         }

         var1.append('(').append(var4).append(')');
         String var6 = " chars";
         int var5;
         if (var2 instanceof CharSequence) {
            CharSequence var7 = (CharSequence)var2;
            var5 = var7.length();
            var5 -= this._append(var1, var7.subSequence(0, Math.min(var5, 500)).toString());
         } else if (var2 instanceof char[]) {
            char[] var9 = (char[])((char[])var2);
            var5 = var9.length;
            var5 -= this._append(var1, new String(var9, 0, Math.min(var5, 500)));
         } else if (var2 instanceof byte[]) {
            byte[] var10 = (byte[])((byte[])var2);
            int var8 = Math.min(var10.length, 500);
            this._append(var1, new String(var10, 0, var8, Charset.forName("UTF-8")));
            var5 = var10.length - var8;
            var6 = " bytes";
         } else {
            var5 = 0;
         }

         if (var5 > 0) {
            var1.append("[truncated ").append(var5).append(var6).append(']');
         }

         return var1;
      }
   }

   private int _append(StringBuilder var1, String var2) {
      var1.append('"').append(var2).append('"');
      return var2.length();
   }
}
