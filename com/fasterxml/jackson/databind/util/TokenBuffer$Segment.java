package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.JsonToken;
import java.util.TreeMap;

public final class TokenBuffer$Segment {
   public static final int TOKENS_PER_SEGMENT = 16;
   private static final JsonToken[] TOKEN_TYPES_BY_INDEX = new JsonToken[16];
   protected TokenBuffer$Segment _next;
   protected long _tokenTypes;
   protected final Object[] _tokens = new Object[16];
   protected TreeMap _nativeIds;

   public TokenBuffer$Segment() {
      super();
   }

   public JsonToken type(int var1) {
      long var2 = this._tokenTypes;
      if (var1 > 0) {
         var2 >>= var1 << 2;
      }

      int var4 = (int)var2 & 15;
      return TOKEN_TYPES_BY_INDEX[var4];
   }

   public int rawType(int var1) {
      long var2 = this._tokenTypes;
      if (var1 > 0) {
         var2 >>= var1 << 2;
      }

      int var4 = (int)var2 & 15;
      return var4;
   }

   public Object get(int var1) {
      return this._tokens[var1];
   }

   public TokenBuffer$Segment next() {
      return this._next;
   }

   public boolean hasIds() {
      return this._nativeIds != null;
   }

   public TokenBuffer$Segment append(int var1, JsonToken var2) {
      if (var1 < 16) {
         this.set(var1, var2);
         return null;
      } else {
         this._next = new TokenBuffer$Segment();
         this._next.set(0, var2);
         return this._next;
      }
   }

   public TokenBuffer$Segment append(int var1, JsonToken var2, Object var3, Object var4) {
      if (var1 < 16) {
         this.set(var1, var2, var3, var4);
         return null;
      } else {
         this._next = new TokenBuffer$Segment();
         this._next.set(0, var2, var3, var4);
         return this._next;
      }
   }

   public TokenBuffer$Segment append(int var1, JsonToken var2, Object var3) {
      if (var1 < 16) {
         this.set(var1, var2, var3);
         return null;
      } else {
         this._next = new TokenBuffer$Segment();
         this._next.set(0, var2, var3);
         return this._next;
      }
   }

   public TokenBuffer$Segment append(int var1, JsonToken var2, Object var3, Object var4, Object var5) {
      if (var1 < 16) {
         this.set(var1, var2, var3, var4, var5);
         return null;
      } else {
         this._next = new TokenBuffer$Segment();
         this._next.set(0, var2, var3, var4, var5);
         return this._next;
      }
   }

   private void set(int var1, JsonToken var2) {
      long var3 = (long)var2.ordinal();
      if (var1 > 0) {
         var3 <<= var1 << 2;
      }

      this._tokenTypes |= var3;
   }

   private void set(int var1, JsonToken var2, Object var3, Object var4) {
      long var5 = (long)var2.ordinal();
      if (var1 > 0) {
         var5 <<= var1 << 2;
      }

      this._tokenTypes |= var5;
      this.assignNativeIds(var1, var3, var4);
   }

   private void set(int var1, JsonToken var2, Object var3) {
      this._tokens[var1] = var3;
      long var4 = (long)var2.ordinal();
      if (var1 > 0) {
         var4 <<= var1 << 2;
      }

      this._tokenTypes |= var4;
   }

   private void set(int var1, JsonToken var2, Object var3, Object var4, Object var5) {
      this._tokens[var1] = var3;
      long var6 = (long)var2.ordinal();
      if (var1 > 0) {
         var6 <<= var1 << 2;
      }

      this._tokenTypes |= var6;
      this.assignNativeIds(var1, var4, var5);
   }

   private final void assignNativeIds(int var1, Object var2, Object var3) {
      if (this._nativeIds == null) {
         this._nativeIds = new TreeMap();
      }

      if (var2 != null) {
         this._nativeIds.put(this._objectIdIndex(var1), var2);
      }

      if (var3 != null) {
         this._nativeIds.put(this._typeIdIndex(var1), var3);
      }

   }

   private Object findObjectId(int var1) {
      return this._nativeIds == null ? null : this._nativeIds.get(this._objectIdIndex(var1));
   }

   private Object findTypeId(int var1) {
      return this._nativeIds == null ? null : this._nativeIds.get(this._typeIdIndex(var1));
   }

   private final int _typeIdIndex(int var1) {
      return var1 + var1;
   }

   private final int _objectIdIndex(int var1) {
      return var1 + var1 + 1;
   }

   static Object access$000(TokenBuffer$Segment var0, int var1) {
      return var0.findObjectId(var1);
   }

   static Object access$100(TokenBuffer$Segment var0, int var1) {
      return var0.findTypeId(var1);
   }

   static {
      JsonToken[] var0 = JsonToken.values();
      System.arraycopy(var0, 1, TOKEN_TYPES_BY_INDEX, 1, Math.min(15, var0.length - 1));
   }
}
