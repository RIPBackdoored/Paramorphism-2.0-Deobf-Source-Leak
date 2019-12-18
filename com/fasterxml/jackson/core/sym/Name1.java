package com.fasterxml.jackson.core.sym;

public final class Name1 extends Name {
   private static final Name1 EMPTY = new Name1("", 0, 0);
   private final int q;

   Name1(String var1, int var2, int var3) {
      super(var1, var2);
      this.q = var3;
   }

   public static Name1 getEmptyName() {
      return EMPTY;
   }

   public boolean equals(int var1) {
      return var1 == this.q;
   }

   public boolean equals(int var1, int var2) {
      return var1 == this.q && var2 == 0;
   }

   public boolean equals(int var1, int var2, int var3) {
      return false;
   }

   public boolean equals(int[] var1, int var2) {
      return var2 == 1 && var1[0] == this.q;
   }
}
