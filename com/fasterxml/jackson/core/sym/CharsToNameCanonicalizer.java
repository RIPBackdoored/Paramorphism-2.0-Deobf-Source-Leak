package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory$Feature;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicReference;

public final class CharsToNameCanonicalizer {
   public static final int HASH_MULT = 33;
   private static final int DEFAULT_T_SIZE = 64;
   private static final int MAX_T_SIZE = 65536;
   static final int MAX_ENTRIES_FOR_REUSE = 12000;
   static final int MAX_COLL_CHAIN_LENGTH = 100;
   private final CharsToNameCanonicalizer _parent;
   private final AtomicReference _tableInfo;
   private final int _seed;
   private final int _flags;
   private boolean _canonicalize;
   private String[] _symbols;
   private CharsToNameCanonicalizer$Bucket[] _buckets;
   private int _size;
   private int _sizeThreshold;
   private int _indexMask;
   private int _longestCollisionList;
   private boolean _hashShared;
   private BitSet _overflows;

   private CharsToNameCanonicalizer(int var1) {
      super();
      this._parent = null;
      this._seed = var1;
      this._canonicalize = true;
      this._flags = -1;
      this._hashShared = false;
      this._longestCollisionList = 0;
      this._tableInfo = new AtomicReference(CharsToNameCanonicalizer$TableInfo.createInitial(64));
   }

   private CharsToNameCanonicalizer(CharsToNameCanonicalizer var1, int var2, int var3, CharsToNameCanonicalizer$TableInfo var4) {
      super();
      this._parent = var1;
      this._seed = var3;
      this._tableInfo = null;
      this._flags = var2;
      this._canonicalize = JsonFactory$Feature.CANONICALIZE_FIELD_NAMES.enabledIn(var2);
      this._symbols = var4.symbols;
      this._buckets = var4.buckets;
      this._size = var4.size;
      this._longestCollisionList = var4.longestCollisionList;
      int var5 = this._symbols.length;
      this._sizeThreshold = _thresholdSize(var5);
      this._indexMask = var5 - 1;
      this._hashShared = true;
   }

   private static int _thresholdSize(int var0) {
      return var0 - (var0 >> 2);
   }

   public static CharsToNameCanonicalizer createRoot() {
      long var0 = System.currentTimeMillis();
      int var2 = (int)var0 + (int)(var0 >>> 32) | 1;
      return createRoot(var2);
   }

   protected static CharsToNameCanonicalizer createRoot(int var0) {
      return new CharsToNameCanonicalizer(var0);
   }

   public CharsToNameCanonicalizer makeChild(int var1) {
      return new CharsToNameCanonicalizer(this, var1, this._seed, (CharsToNameCanonicalizer$TableInfo)this._tableInfo.get());
   }

   public void release() {
      if (this.maybeDirty()) {
         if (this._parent != null && this._canonicalize) {
            this._parent.mergeChild(new CharsToNameCanonicalizer$TableInfo(this));
            this._hashShared = true;
         }

      }
   }

   private void mergeChild(CharsToNameCanonicalizer$TableInfo var1) {
      int var2 = var1.size;
      CharsToNameCanonicalizer$TableInfo var3 = (CharsToNameCanonicalizer$TableInfo)this._tableInfo.get();
      if (var2 != var3.size) {
         if (var2 > 12000) {
            var1 = CharsToNameCanonicalizer$TableInfo.createInitial(64);
         }

         this._tableInfo.compareAndSet(var3, var1);
      }
   }

   public int size() {
      return this._tableInfo != null ? ((CharsToNameCanonicalizer$TableInfo)this._tableInfo.get()).size : this._size;
   }

   public int bucketCount() {
      return this._symbols.length;
   }

   public boolean maybeDirty() {
      return !this._hashShared;
   }

   public int hashSeed() {
      return this._seed;
   }

   public int collisionCount() {
      int var1 = 0;
      CharsToNameCanonicalizer$Bucket[] var2 = this._buckets;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         CharsToNameCanonicalizer$Bucket var5 = var2[var4];
         if (var5 != null) {
            var1 += var5.length;
         }
      }

      return var1;
   }

   public int maxCollisionLength() {
      return this._longestCollisionList;
   }

   public String findSymbol(char[] var1, int var2, int var3, int var4) {
      if (var3 < 1) {
         return "";
      } else if (!this._canonicalize) {
         return new String(var1, var2, var3);
      } else {
         int var5 = this._hashToIndex(var4);
         String var6 = this._symbols[var5];
         if (var6 != null) {
            if (var6.length() == var3) {
               int var7 = 0;

               while(var6.charAt(var7) == var1[var2 + var7]) {
                  ++var7;
                  if (var7 == var3) {
                     return var6;
                  }
               }
            }

            CharsToNameCanonicalizer$Bucket var8 = this._buckets[var5 >> 1];
            if (var8 != null) {
               var6 = var8.has(var1, var2, var3);
               if (var6 != null) {
                  return var6;
               }

               var6 = this._findSymbol2(var1, var2, var3, var8.next);
               if (var6 != null) {
                  return var6;
               }
            }
         }

         return this._addSymbol(var1, var2, var3, var4, var5);
      }
   }

   private String _findSymbol2(char[] var1, int var2, int var3, CharsToNameCanonicalizer$Bucket var4) {
      while(var4 != null) {
         String var5 = var4.has(var1, var2, var3);
         if (var5 != null) {
            return var5;
         }

         var4 = var4.next;
      }

      return null;
   }

   private String _addSymbol(char[] var1, int var2, int var3, int var4, int var5) {
      if (this._hashShared) {
         this.copyArrays();
         this._hashShared = false;
      } else if (this._size >= this._sizeThreshold) {
         this.rehash();
         var5 = this._hashToIndex(this.calcHash(var1, var2, var3));
      }

      String var6 = new String(var1, var2, var3);
      if (JsonFactory$Feature.INTERN_FIELD_NAMES.enabledIn(this._flags)) {
         var6 = InternCache.instance.intern(var6);
      }

      ++this._size;
      if (this._symbols[var5] == null) {
         this._symbols[var5] = var6;
      } else {
         int var7 = var5 >> 1;
         CharsToNameCanonicalizer$Bucket var8 = new CharsToNameCanonicalizer$Bucket(var6, this._buckets[var7]);
         int var9 = var8.length;
         if (var9 > 100) {
            this._handleSpillOverflow(var7, var8);
         } else {
            this._buckets[var7] = var8;
            this._longestCollisionList = Math.max(var9, this._longestCollisionList);
         }
      }

      return var6;
   }

   private void _handleSpillOverflow(int var1, CharsToNameCanonicalizer$Bucket var2) {
      if (this._overflows == null) {
         this._overflows = new BitSet();
         this._overflows.set(var1);
      } else if (this._overflows.get(var1)) {
         if (JsonFactory$Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(this._flags)) {
            this.reportTooManyCollisions(100);
         }

         this._canonicalize = false;
      } else {
         this._overflows.set(var1);
      }

      this._symbols[var1 + var1] = var2.symbol;
      this._buckets[var1] = null;
      this._size -= var2.length;
      this._longestCollisionList = -1;
   }

   public int _hashToIndex(int var1) {
      var1 += var1 >>> 15;
      var1 ^= var1 << 7;
      var1 += var1 >>> 3;
      return var1 & this._indexMask;
   }

   public int calcHash(char[] var1, int var2, int var3) {
      int var4 = this._seed;
      int var5 = var2;

      for(int var6 = var2 + var3; var5 < var6; ++var5) {
         var4 = var4 * 33 + var1[var5];
      }

      return var4 == 0 ? 1 : var4;
   }

   public int calcHash(String var1) {
      int var2 = var1.length();
      int var3 = this._seed;

      for(int var4 = 0; var4 < var2; ++var4) {
         var3 = var3 * 33 + var1.charAt(var4);
      }

      return var3 == 0 ? 1 : var3;
   }

   private void copyArrays() {
      String[] var1 = this._symbols;
      this._symbols = (String[])Arrays.copyOf(var1, var1.length);
      CharsToNameCanonicalizer$Bucket[] var2 = this._buckets;
      this._buckets = (CharsToNameCanonicalizer$Bucket[])Arrays.copyOf(var2, var2.length);
   }

   private void rehash() {
      int var1 = this._symbols.length;
      int var2 = var1 + var1;
      if (var2 > 65536) {
         this._size = 0;
         this._canonicalize = false;
         this._symbols = new String[64];
         this._buckets = new CharsToNameCanonicalizer$Bucket[32];
         this._indexMask = 63;
         this._hashShared = false;
      } else {
         String[] var3 = this._symbols;
         CharsToNameCanonicalizer$Bucket[] var4 = this._buckets;
         this._symbols = new String[var2];
         this._buckets = new CharsToNameCanonicalizer$Bucket[var2 >> 1];
         this._indexMask = var2 - 1;
         this._sizeThreshold = _thresholdSize(var2);
         int var5 = 0;
         int var6 = 0;

         int var7;
         int var10;
         for(var7 = 0; var7 < var1; ++var7) {
            String var8 = var3[var7];
            if (var8 != null) {
               ++var5;
               int var9 = this._hashToIndex(this.calcHash(var8));
               if (this._symbols[var9] == null) {
                  this._symbols[var9] = var8;
               } else {
                  var10 = var9 >> 1;
                  CharsToNameCanonicalizer$Bucket var11 = new CharsToNameCanonicalizer$Bucket(var8, this._buckets[var10]);
                  this._buckets[var10] = var11;
                  var6 = Math.max(var6, var11.length);
               }
            }
         }

         var1 >>= 1;

         for(var7 = 0; var7 < var1; ++var7) {
            for(CharsToNameCanonicalizer$Bucket var13 = var4[var7]; var13 != null; var13 = var13.next) {
               ++var5;
               String var14 = var13.symbol;
               var10 = this._hashToIndex(this.calcHash(var14));
               if (this._symbols[var10] == null) {
                  this._symbols[var10] = var14;
               } else {
                  int var15 = var10 >> 1;
                  CharsToNameCanonicalizer$Bucket var12 = new CharsToNameCanonicalizer$Bucket(var14, this._buckets[var15]);
                  this._buckets[var15] = var12;
                  var6 = Math.max(var6, var12.length);
               }
            }
         }

         this._longestCollisionList = var6;
         this._overflows = null;
         if (var5 != this._size) {
            throw new IllegalStateException(String.format("Internal error on SymbolTable.rehash(): had %d entries; now have %d", this._size, var5));
         }
      }
   }

   protected void reportTooManyCollisions(int var1) {
      throw new IllegalStateException("Longest collision chain in symbol table (of size " + this._size + ") now exceeds maximum, " + var1 + " -- suspect a DoS attack based on hash collisions");
   }

   static int access$000(CharsToNameCanonicalizer var0) {
      return var0._size;
   }

   static int access$100(CharsToNameCanonicalizer var0) {
      return var0._longestCollisionList;
   }

   static String[] access$200(CharsToNameCanonicalizer var0) {
      return var0._symbols;
   }

   static CharsToNameCanonicalizer$Bucket[] access$300(CharsToNameCanonicalizer var0) {
      return var0._buckets;
   }
}
