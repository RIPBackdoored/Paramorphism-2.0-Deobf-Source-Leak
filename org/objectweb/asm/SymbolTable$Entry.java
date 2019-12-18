package org.objectweb.asm;

class SymbolTable$Entry extends Symbol {
   final int hashCode;
   SymbolTable$Entry next;

   SymbolTable$Entry(int var1, int var2, String var3, String var4, String var5, long var6, int var8) {
      super(var1, var2, var3, var4, var5, var6);
      this.hashCode = var8;
   }

   SymbolTable$Entry(int var1, int var2, String var3, int var4) {
      super(var1, var2, (String)null, (String)null, var3, 0L);
      this.hashCode = var4;
   }

   SymbolTable$Entry(int var1, int var2, String var3, long var4, int var6) {
      super(var1, var2, (String)null, (String)null, var3, var4);
      this.hashCode = var6;
   }

   SymbolTable$Entry(int var1, int var2, String var3, String var4, int var5) {
      super(var1, var2, (String)null, var3, var4, 0L);
      this.hashCode = var5;
   }

   SymbolTable$Entry(int var1, int var2, long var3, int var5) {
      super(var1, var2, (String)null, (String)null, (String)null, var3);
      this.hashCode = var5;
   }
}
