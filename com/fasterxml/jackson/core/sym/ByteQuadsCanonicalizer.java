package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory$Feature;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public final class ByteQuadsCanonicalizer {
   private static final int DEFAULT_T_SIZE = 64;
   private static final int MAX_T_SIZE = 65536;
   private static final int MIN_HASH_SIZE = 16;
   static final int MAX_ENTRIES_FOR_REUSE = 6000;
   private final ByteQuadsCanonicalizer _parent;
   private final AtomicReference _tableInfo;
   private final int _seed;
   private boolean _intern;
   private final boolean _failOnDoS;
   private int[] _hashArea;
   private int _hashSize;
   private int _secondaryStart;
   private int _tertiaryStart;
   private int _tertiaryShift;
   private int _count;
   private String[] _names;
   private int _spilloverEnd;
   private int _longNameOffset;
   private transient boolean _needRehash;
   private boolean _hashShared;
   private static final int MULT = 33;
   private static final int MULT2 = 65599;
   private static final int MULT3 = 31;

   private ByteQuadsCanonicalizer(int var1, boolean var2, int var3, boolean var4) {
      super();
      this._parent = null;
      this._seed = var3;
      this._intern = var2;
      this._failOnDoS = var4;
      if (var1 < 16) {
         var1 = 16;
      } else if ((var1 & var1 - 1) != 0) {
         int var5;
         for(var5 = 16; var5 < var1; var5 += var5) {
         }

         var1 = var5;
      }

      this._tableInfo = new AtomicReference(ByteQuadsCanonicalizer$TableInfo.createInitial(var1));
   }

   private ByteQuadsCanonicalizer(ByteQuadsCanonicalizer var1, boolean var2, int var3, boolean var4, ByteQuadsCanonicalizer$TableInfo var5) {
      super();
      this._parent = var1;
      this._seed = var3;
      this._intern = var2;
      this._failOnDoS = var4;
      this._tableInfo = null;
      this._count = var5.count;
      this._hashSize = var5.size;
      this._secondaryStart = this._hashSize << 2;
      this._tertiaryStart = this._secondaryStart + (this._secondaryStart >> 1);
      this._tertiaryShift = var5.tertiaryShift;
      this._hashArea = var5.mainHash;
      this._names = var5.names;
      this._spilloverEnd = var5.spilloverEnd;
      this._longNameOffset = var5.longNameOffset;
      this._needRehash = false;
      this._hashShared = true;
   }

   public static ByteQuadsCanonicalizer createRoot() {
      long var0 = System.currentTimeMillis();
      int var2 = (int)var0 + (int)(var0 >>> 32) | 1;
      return createRoot(var2);
   }

   protected static ByteQuadsCanonicalizer createRoot(int var0) {
      return new ByteQuadsCanonicalizer(64, true, var0, true);
   }

   public ByteQuadsCanonicalizer makeChild(int var1) {
      return new ByteQuadsCanonicalizer(this, JsonFactory$Feature.INTERN_FIELD_NAMES.enabledIn(var1), this._seed, JsonFactory$Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(var1), (ByteQuadsCanonicalizer$TableInfo)this._tableInfo.get());
   }

   public void release() {
      if (this._parent != null && this.maybeDirty()) {
         this._parent.mergeChild(new ByteQuadsCanonicalizer$TableInfo(this));
         this._hashShared = true;
      }

   }

   private void mergeChild(ByteQuadsCanonicalizer$TableInfo var1) {
      int var2 = var1.count;
      ByteQuadsCanonicalizer$TableInfo var3 = (ByteQuadsCanonicalizer$TableInfo)this._tableInfo.get();
      if (var2 != var3.count) {
         if (var2 > 6000) {
            var1 = ByteQuadsCanonicalizer$TableInfo.createInitial(64);
         }

         this._tableInfo.compareAndSet(var3, var1);
      }
   }

   public int size() {
      return this._tableInfo != null ? ((ByteQuadsCanonicalizer$TableInfo)this._tableInfo.get()).count : this._count;
   }

   public int bucketCount() {
      return this._hashSize;
   }

   public boolean maybeDirty() {
      return !this._hashShared;
   }

   public int hashSeed() {
      return this._seed;
   }

   public int primaryCount() {
      int var1 = 0;
      int var2 = 3;

      for(int var3 = this._secondaryStart; var2 < var3; var2 += 4) {
         if (this._hashArea[var2] != 0) {
            ++var1;
         }
      }

      return var1;
   }

   public int secondaryCount() {
      int var1 = 0;
      int var2 = this._secondaryStart + 3;

      for(int var3 = this._tertiaryStart; var2 < var3; var2 += 4) {
         if (this._hashArea[var2] != 0) {
            ++var1;
         }
      }

      return var1;
   }

   public int tertiaryCount() {
      int var1 = 0;
      int var2 = this._tertiaryStart + 3;

      for(int var3 = var2 + this._hashSize; var2 < var3; var2 += 4) {
         if (this._hashArea[var2] != 0) {
            ++var1;
         }
      }

      return var1;
   }

   public int spilloverCount() {
      return this._spilloverEnd - this._spilloverStart() >> 2;
   }

   public int totalCount() {
      int var1 = 0;
      int var2 = 3;

      for(int var3 = this._hashSize << 3; var2 < var3; var2 += 4) {
         if (this._hashArea[var2] != 0) {
            ++var1;
         }
      }

      return var1;
   }

   public String toString() {
      int var1 = this.primaryCount();
      int var2 = this.secondaryCount();
      int var3 = this.tertiaryCount();
      int var4 = this.spilloverCount();
      int var5 = this.totalCount();
      return String.format("[%s: size=%d, hashSize=%d, %d/%d/%d/%d pri/sec/ter/spill (=%s), total:%d]", this.getClass().getName(), this._count, this._hashSize, var1, var2, var3, var4, var1 + var2 + var3 + var4, var5);
   }

   public String findName(int var1) {
      int var2 = this._calcOffset(this.calcHash(var1));
      int[] var3 = this._hashArea;
      int var4 = var3[var2 + 3];
      if (var4 == 1) {
         if (var3[var2] == var1) {
            return this._names[var2 >> 2];
         }
      } else if (var4 == 0) {
         return null;
      }

      int var5 = this._secondaryStart + (var2 >> 3 << 2);
      var4 = var3[var5 + 3];
      if (var4 == 1) {
         if (var3[var5] == var1) {
            return this._names[var5 >> 2];
         }
      } else if (var4 == 0) {
         return null;
      }

      return this._findSecondary(var2, var1);
   }

   public String findName(int var1, int var2) {
      int var3 = this._calcOffset(this.calcHash(var1, var2));
      int[] var4 = this._hashArea;
      int var5 = var4[var3 + 3];
      if (var5 == 2) {
         if (var1 == var4[var3] && var2 == var4[var3 + 1]) {
            return this._names[var3 >> 2];
         }
      } else if (var5 == 0) {
         return null;
      }

      int var6 = this._secondaryStart + (var3 >> 3 << 2);
      var5 = var4[var6 + 3];
      if (var5 == 2) {
         if (var1 == var4[var6] && var2 == var4[var6 + 1]) {
            return this._names[var6 >> 2];
         }
      } else if (var5 == 0) {
         return null;
      }

      return this._findSecondary(var3, var1, var2);
   }

   public String findName(int var1, int var2, int var3) {
      int var4 = this._calcOffset(this.calcHash(var1, var2, var3));
      int[] var5 = this._hashArea;
      int var6 = var5[var4 + 3];
      if (var6 == 3) {
         if (var1 == var5[var4] && var5[var4 + 1] == var2 && var5[var4 + 2] == var3) {
            return this._names[var4 >> 2];
         }
      } else if (var6 == 0) {
         return null;
      }

      int var7 = this._secondaryStart + (var4 >> 3 << 2);
      var6 = var5[var7 + 3];
      if (var6 == 3) {
         if (var1 == var5[var7] && var5[var7 + 1] == var2 && var5[var7 + 2] == var3) {
            return this._names[var7 >> 2];
         }
      } else if (var6 == 0) {
         return null;
      }

      return this._findSecondary(var4, var1, var2, var3);
   }

   public String findName(int[] var1, int var2) {
      if (var2 < 4) {
         switch(var2) {
         case 1:
            return this.findName(var1[0]);
         case 2:
            return this.findName(var1[0], var1[1]);
         case 3:
            return this.findName(var1[0], var1[1], var1[2]);
         default:
            return "";
         }
      } else {
         int var3 = this.calcHash(var1, var2);
         int var4 = this._calcOffset(var3);
         int[] var5 = this._hashArea;
         int var6 = var5[var4 + 3];
         if (var3 == var5[var4] && var6 == var2 && this._verifyLongName(var1, var2, var5[var4 + 1])) {
            return this._names[var4 >> 2];
         } else if (var6 == 0) {
            return null;
         } else {
            int var7 = this._secondaryStart + (var4 >> 3 << 2);
            int var8 = var5[var7 + 3];
            return var3 == var5[var7] && var8 == var2 && this._verifyLongName(var1, var2, var5[var7 + 1]) ? this._names[var7 >> 2] : this._findSecondary(var4, var3, var1, var2);
         }
      }
   }

   private final int _calcOffset(int var1) {
      int var2 = var1 & this._hashSize - 1;
      return var2 << 2;
   }

   private String _findSecondary(int var1, int var2) {
      int var3 = this._tertiaryStart + (var1 >> this._tertiaryShift + 2 << this._tertiaryShift);
      int[] var4 = this._hashArea;
      int var5 = 1 << this._tertiaryShift;

      for(int var6 = var3 + var5; var3 < var6; var3 += 4) {
         int var7 = var4[var3 + 3];
         if (var2 == var4[var3] && 1 == var7) {
            return this._names[var3 >> 2];
         }

         if (var7 == 0) {
            return null;
         }
      }

      for(var3 = this._spilloverStart(); var3 < this._spilloverEnd; var3 += 4) {
         if (var2 == var4[var3] && 1 == var4[var3 + 3]) {
            return this._names[var3 >> 2];
         }
      }

      return null;
   }

   private String _findSecondary(int var1, int var2, int var3) {
      int var4 = this._tertiaryStart + (var1 >> this._tertiaryShift + 2 << this._tertiaryShift);
      int[] var5 = this._hashArea;
      int var6 = 1 << this._tertiaryShift;

      for(int var7 = var4 + var6; var4 < var7; var4 += 4) {
         int var8 = var5[var4 + 3];
         if (var2 == var5[var4] && var3 == var5[var4 + 1] && 2 == var8) {
            return this._names[var4 >> 2];
         }

         if (var8 == 0) {
            return null;
         }
      }

      for(var4 = this._spilloverStart(); var4 < this._spilloverEnd; var4 += 4) {
         if (var2 == var5[var4] && var3 == var5[var4 + 1] && 2 == var5[var4 + 3]) {
            return this._names[var4 >> 2];
         }
      }

      return null;
   }

   private String _findSecondary(int var1, int var2, int var3, int var4) {
      int var5 = this._tertiaryStart + (var1 >> this._tertiaryShift + 2 << this._tertiaryShift);
      int[] var6 = this._hashArea;
      int var7 = 1 << this._tertiaryShift;

      for(int var8 = var5 + var7; var5 < var8; var5 += 4) {
         int var9 = var6[var5 + 3];
         if (var2 == var6[var5] && var3 == var6[var5 + 1] && var4 == var6[var5 + 2] && 3 == var9) {
            return this._names[var5 >> 2];
         }

         if (var9 == 0) {
            return null;
         }
      }

      for(var5 = this._spilloverStart(); var5 < this._spilloverEnd; var5 += 4) {
         if (var2 == var6[var5] && var3 == var6[var5 + 1] && var4 == var6[var5 + 2] && 3 == var6[var5 + 3]) {
            return this._names[var5 >> 2];
         }
      }

      return null;
   }

   private String _findSecondary(int var1, int var2, int[] var3, int var4) {
      int var5 = this._tertiaryStart + (var1 >> this._tertiaryShift + 2 << this._tertiaryShift);
      int[] var6 = this._hashArea;
      int var7 = 1 << this._tertiaryShift;

      for(int var8 = var5 + var7; var5 < var8; var5 += 4) {
         int var9 = var6[var5 + 3];
         if (var2 == var6[var5] && var4 == var9 && this._verifyLongName(var3, var4, var6[var5 + 1])) {
            return this._names[var5 >> 2];
         }

         if (var9 == 0) {
            return null;
         }
      }

      for(var5 = this._spilloverStart(); var5 < this._spilloverEnd; var5 += 4) {
         if (var2 == var6[var5] && var4 == var6[var5 + 3] && this._verifyLongName(var3, var4, var6[var5 + 1])) {
            return this._names[var5 >> 2];
         }
      }

      return null;
   }

   private boolean _verifyLongName(int[] var1, int var2, int var3) {
      int[] var4 = this._hashArea;
      int var5 = 0;
      switch(var2) {
      case 8:
         if (var1[var5++] != var4[var3++]) {
            return false;
         }
      case 7:
         if (var1[var5++] != var4[var3++]) {
            return false;
         }
      case 6:
         if (var1[var5++] != var4[var3++]) {
            return false;
         }
      case 5:
         if (var1[var5++] != var4[var3++]) {
            return false;
         }
      case 4:
         if (var1[var5++] != var4[var3++]) {
            return false;
         } else if (var1[var5++] != var4[var3++]) {
            return false;
         } else if (var1[var5++] != var4[var3++]) {
            return false;
         } else {
            if (var1[var5++] != var4[var3++]) {
               return false;
            }

            return true;
         }
      default:
         return this._verifyLongName2(var1, var2, var3);
      }
   }

   private boolean _verifyLongName2(int[] var1, int var2, int var3) {
      int var4 = 0;

      while(var1[var4++] == this._hashArea[var3++]) {
         if (var4 >= var2) {
            return true;
         }
      }

      return false;
   }

   public String addName(String var1, int var2) {
      this._verifySharing();
      if (this._intern) {
         var1 = InternCache.instance.intern(var1);
      }

      int var3 = this._findOffsetForAdd(this.calcHash(var2));
      this._hashArea[var3] = var2;
      this._hashArea[var3 + 3] = 1;
      this._names[var3 >> 2] = var1;
      ++this._count;
      this._verifyNeedForRehash();
      return var1;
   }

   public String addName(String var1, int var2, int var3) {
      this._verifySharing();
      if (this._intern) {
         var1 = InternCache.instance.intern(var1);
      }

      int var4 = var3 == 0 ? this.calcHash(var2) : this.calcHash(var2, var3);
      int var5 = this._findOffsetForAdd(var4);
      this._hashArea[var5] = var2;
      this._hashArea[var5 + 1] = var3;
      this._hashArea[var5 + 3] = 2;
      this._names[var5 >> 2] = var1;
      ++this._count;
      this._verifyNeedForRehash();
      return var1;
   }

   public String addName(String var1, int var2, int var3, int var4) {
      this._verifySharing();
      if (this._intern) {
         var1 = InternCache.instance.intern(var1);
      }

      int var5 = this._findOffsetForAdd(this.calcHash(var2, var3, var4));
      this._hashArea[var5] = var2;
      this._hashArea[var5 + 1] = var3;
      this._hashArea[var5 + 2] = var4;
      this._hashArea[var5 + 3] = 3;
      this._names[var5 >> 2] = var1;
      ++this._count;
      this._verifyNeedForRehash();
      return var1;
   }

   public String addName(String var1, int[] var2, int var3) {
      this._verifySharing();
      if (this._intern) {
         var1 = InternCache.instance.intern(var1);
      }

      int var4;
      switch(var3) {
      case 1:
         var4 = this._findOffsetForAdd(this.calcHash(var2[0]));
         this._hashArea[var4] = var2[0];
         this._hashArea[var4 + 3] = 1;
         break;
      case 2:
         var4 = this._findOffsetForAdd(this.calcHash(var2[0], var2[1]));
         this._hashArea[var4] = var2[0];
         this._hashArea[var4 + 1] = var2[1];
         this._hashArea[var4 + 3] = 2;
         break;
      case 3:
         var4 = this._findOffsetForAdd(this.calcHash(var2[0], var2[1], var2[2]));
         this._hashArea[var4] = var2[0];
         this._hashArea[var4 + 1] = var2[1];
         this._hashArea[var4 + 2] = var2[2];
         this._hashArea[var4 + 3] = 3;
         break;
      default:
         int var5 = this.calcHash(var2, var3);
         var4 = this._findOffsetForAdd(var5);
         this._hashArea[var4] = var5;
         int var6 = this._appendLongName(var2, var3);
         this._hashArea[var4 + 1] = var6;
         this._hashArea[var4 + 3] = var3;
      }

      this._names[var4 >> 2] = var1;
      ++this._count;
      this._verifyNeedForRehash();
      return var1;
   }

   private void _verifyNeedForRehash() {
      if (this._count > this._hashSize >> 1) {
         int var1 = this._spilloverEnd - this._spilloverStart() >> 2;
         if (var1 > 1 + this._count >> 7 || (double)this._count > (double)this._hashSize * 0.8D) {
            this._needRehash = true;
         }
      }

   }

   private void _verifySharing() {
      if (this._hashShared) {
         this._hashArea = Arrays.copyOf(this._hashArea, this._hashArea.length);
         this._names = (String[])Arrays.copyOf(this._names, this._names.length);
         this._hashShared = false;
         this._verifyNeedForRehash();
      }

      if (this._needRehash) {
         this.rehash();
      }

   }

   private int _findOffsetForAdd(int var1) {
      int var2 = this._calcOffset(var1);
      int[] var3 = this._hashArea;
      if (var3[var2 + 3] == 0) {
         return var2;
      } else {
         int var4 = this._secondaryStart + (var2 >> 3 << 2);
         if (var3[var4 + 3] == 0) {
            return var4;
         } else {
            var4 = this._tertiaryStart + (var2 >> this._tertiaryShift + 2 << this._tertiaryShift);
            int var5 = 1 << this._tertiaryShift;

            int var6;
            for(var6 = var4 + var5; var4 < var6; var4 += 4) {
               if (var3[var4 + 3] == 0) {
                  return var4;
               }
            }

            var2 = this._spilloverEnd;
            this._spilloverEnd += 4;
            var6 = this._hashSize << 3;
            if (this._spilloverEnd >= var6) {
               if (this._failOnDoS) {
                  this._reportTooManyCollisions();
               }

               this._needRehash = true;
            }

            return var2;
         }
      }
   }

   private int _appendLongName(int[] var1, int var2) {
      int var3 = this._longNameOffset;
      if (var3 + var2 > this._hashArea.length) {
         int var4 = var3 + var2 - this._hashArea.length;
         int var5 = Math.min(4096, this._hashSize);
         int var6 = this._hashArea.length + Math.max(var4, var5);
         this._hashArea = Arrays.copyOf(this._hashArea, var6);
      }

      System.arraycopy(var1, 0, this._hashArea, var3, var2);
      this._longNameOffset += var2;
      return var3;
   }

   public int calcHash(int var1) {
      int var2 = var1 ^ this._seed;
      var2 += var2 >>> 16;
      var2 ^= var2 << 3;
      var2 += var2 >>> 12;
      return var2;
   }

   public int calcHash(int var1, int var2) {
      int var3 = var1 + (var1 >>> 15);
      var3 ^= var3 >>> 9;
      var3 += var2 * 33;
      var3 ^= this._seed;
      var3 += var3 >>> 16;
      var3 ^= var3 >>> 4;
      var3 += var3 << 3;
      return var3;
   }

   public int calcHash(int var1, int var2, int var3) {
      int var4 = var1 ^ this._seed;
      var4 += var4 >>> 9;
      var4 *= 31;
      var4 += var2;
      var4 *= 33;
      var4 += var4 >>> 15;
      var4 ^= var3;
      var4 += var4 >>> 4;
      var4 += var4 >>> 15;
      var4 ^= var4 << 9;
      return var4;
   }

   public int calcHash(int[] var1, int var2) {
      if (var2 < 4) {
         throw new IllegalArgumentException();
      } else {
         int var3 = var1[0] ^ this._seed;
         var3 += var3 >>> 9;
         var3 += var1[1];
         var3 += var3 >>> 15;
         var3 *= 33;
         var3 ^= var1[2];
         var3 += var3 >>> 4;

         for(int var4 = 3; var4 < var2; ++var4) {
            int var5 = var1[var4];
            var5 ^= var5 >> 21;
            var3 += var5;
         }

         var3 *= 65599;
         var3 += var3 >>> 19;
         var3 ^= var3 << 5;
         return var3;
      }
   }

   private void rehash() {
      this._needRehash = false;
      this._hashShared = false;
      int[] var1 = this._hashArea;
      String[] var2 = this._names;
      int var3 = this._hashSize;
      int var4 = this._count;
      int var5 = var3 + var3;
      int var6 = this._spilloverEnd;
      if (var5 > 65536) {
         this.nukeSymbols(true);
      } else {
         this._hashArea = new int[var1.length + (var3 << 3)];
         this._hashSize = var5;
         this._secondaryStart = var5 << 2;
         this._tertiaryStart = this._secondaryStart + (this._secondaryStart >> 1);
         this._tertiaryShift = _calcTertiaryShift(var5);
         this._names = new String[var2.length << 1];
         this.nukeSymbols(false);
         int var7 = 0;
         int[] var8 = new int[16];
         int var9 = 0;

         for(int var10 = var6; var9 < var10; var9 += 4) {
            int var11 = var1[var9 + 3];
            if (var11 != 0) {
               ++var7;
               String var12 = var2[var9 >> 2];
               switch(var11) {
               case 1:
                  var8[0] = var1[var9];
                  this.addName(var12, var8, 1);
                  break;
               case 2:
                  var8[0] = var1[var9];
                  var8[1] = var1[var9 + 1];
                  this.addName(var12, var8, 2);
                  break;
               case 3:
                  var8[0] = var1[var9];
                  var8[1] = var1[var9 + 1];
                  var8[2] = var1[var9 + 2];
                  this.addName(var12, var8, 3);
                  break;
               default:
                  if (var11 > var8.length) {
                     var8 = new int[var11];
                  }

                  int var13 = var1[var9 + 1];
                  System.arraycopy(var1, var13, var8, 0, var11);
                  this.addName(var12, var8, var11);
               }
            }
         }

         if (var7 != var4) {
            throw new IllegalStateException("Failed rehash(): old count=" + var4 + ", copyCount=" + var7);
         }
      }
   }

   private void nukeSymbols(boolean var1) {
      this._count = 0;
      this._spilloverEnd = this._spilloverStart();
      this._longNameOffset = this._hashSize << 3;
      if (var1) {
         Arrays.fill(this._hashArea, 0);
         Arrays.fill(this._names, (Object)null);
      }

   }

   private final int _spilloverStart() {
      int var1 = this._hashSize;
      return (var1 << 3) - var1;
   }

   protected void _reportTooManyCollisions() {
      if (this._hashSize > 1024) {
         throw new IllegalStateException("Spill-over slots in symbol table with " + this._count + " entries, hash area of " + this._hashSize + " slots is now full (all " + (this._hashSize >> 3) + " slots -- suspect a DoS attack based on hash collisions." + " You can disable the check via `JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW`");
      }
   }

   static int _calcTertiaryShift(int var0) {
      int var1 = var0 >> 2;
      if (var1 < 64) {
         return 4;
      } else if (var1 <= 256) {
         return 5;
      } else {
         return var1 <= 1024 ? 6 : 7;
      }
   }

   static int access$000(ByteQuadsCanonicalizer var0) {
      return var0._hashSize;
   }

   static int access$100(ByteQuadsCanonicalizer var0) {
      return var0._count;
   }

   static int access$200(ByteQuadsCanonicalizer var0) {
      return var0._tertiaryShift;
   }

   static int[] access$300(ByteQuadsCanonicalizer var0) {
      return var0._hashArea;
   }

   static String[] access$400(ByteQuadsCanonicalizer var0) {
      return var0._names;
   }

   static int access$500(ByteQuadsCanonicalizer var0) {
      return var0._spilloverEnd;
   }

   static int access$600(ByteQuadsCanonicalizer var0) {
      return var0._longNameOffset;
   }
}
