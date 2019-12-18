package com.fasterxml.jackson.core.sym;

final class ByteQuadsCanonicalizer$TableInfo {
   public final int size;
   public final int count;
   public final int tertiaryShift;
   public final int[] mainHash;
   public final String[] names;
   public final int spilloverEnd;
   public final int longNameOffset;

   public ByteQuadsCanonicalizer$TableInfo(int var1, int var2, int var3, int[] var4, String[] var5, int var6, int var7) {
      super();
      this.size = var1;
      this.count = var2;
      this.tertiaryShift = var3;
      this.mainHash = var4;
      this.names = var5;
      this.spilloverEnd = var6;
      this.longNameOffset = var7;
   }

   public ByteQuadsCanonicalizer$TableInfo(ByteQuadsCanonicalizer var1) {
      super();
      this.size = ByteQuadsCanonicalizer.access$000(var1);
      this.count = ByteQuadsCanonicalizer.access$100(var1);
      this.tertiaryShift = ByteQuadsCanonicalizer.access$200(var1);
      this.mainHash = ByteQuadsCanonicalizer.access$300(var1);
      this.names = ByteQuadsCanonicalizer.access$400(var1);
      this.spilloverEnd = ByteQuadsCanonicalizer.access$500(var1);
      this.longNameOffset = ByteQuadsCanonicalizer.access$600(var1);
   }

   public static ByteQuadsCanonicalizer$TableInfo createInitial(int var0) {
      int var1 = var0 << 3;
      int var2 = ByteQuadsCanonicalizer._calcTertiaryShift(var0);
      return new ByteQuadsCanonicalizer$TableInfo(var0, 0, var2, new int[var1], new String[var0 << 1], var1 - var0, var1);
   }
}
