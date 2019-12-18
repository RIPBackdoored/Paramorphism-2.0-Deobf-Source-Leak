package org.objectweb.asm;

final class ModuleWriter extends ModuleVisitor {
   private final SymbolTable symbolTable;
   private final int moduleNameIndex;
   private final int moduleFlags;
   private final int moduleVersionIndex;
   private int requiresCount;
   private final ByteVector requires;
   private int exportsCount;
   private final ByteVector exports;
   private int opensCount;
   private final ByteVector opens;
   private int usesCount;
   private final ByteVector usesIndex;
   private int providesCount;
   private final ByteVector provides;
   private int packageCount;
   private final ByteVector packageIndex;
   private int mainClassIndex;

   ModuleWriter(SymbolTable var1, int var2, int var3, int var4) {
      super(458752);
      this.symbolTable = var1;
      this.moduleNameIndex = var2;
      this.moduleFlags = var3;
      this.moduleVersionIndex = var4;
      this.requires = new ByteVector();
      this.exports = new ByteVector();
      this.opens = new ByteVector();
      this.usesIndex = new ByteVector();
      this.provides = new ByteVector();
      this.packageIndex = new ByteVector();
   }

   public void visitMainClass(String var1) {
      this.mainClassIndex = this.symbolTable.addConstantClass(var1).index;
   }

   public void visitPackage(String var1) {
      this.packageIndex.putShort(this.symbolTable.addConstantPackage(var1).index);
      ++this.packageCount;
   }

   public void visitRequire(String var1, int var2, String var3) {
      this.requires.putShort(this.symbolTable.addConstantModule(var1).index).putShort(var2).putShort(var3 == null ? 0 : this.symbolTable.addConstantUtf8(var3));
      ++this.requiresCount;
   }

   public void visitExport(String var1, int var2, String... var3) {
      this.exports.putShort(this.symbolTable.addConstantPackage(var1).index).putShort(var2);
      if (var3 == null) {
         this.exports.putShort(0);
      } else {
         this.exports.putShort(var3.length);
         String[] var4 = var3;
         int var5 = var3.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
            this.exports.putShort(this.symbolTable.addConstantModule(var7).index);
         }
      }

      ++this.exportsCount;
   }

   public void visitOpen(String var1, int var2, String... var3) {
      this.opens.putShort(this.symbolTable.addConstantPackage(var1).index).putShort(var2);
      if (var3 == null) {
         this.opens.putShort(0);
      } else {
         this.opens.putShort(var3.length);
         String[] var4 = var3;
         int var5 = var3.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
            this.opens.putShort(this.symbolTable.addConstantModule(var7).index);
         }
      }

      ++this.opensCount;
   }

   public void visitUse(String var1) {
      this.usesIndex.putShort(this.symbolTable.addConstantClass(var1).index);
      ++this.usesCount;
   }

   public void visitProvide(String var1, String... var2) {
      this.provides.putShort(this.symbolTable.addConstantClass(var1).index);
      this.provides.putShort(var2.length);
      String[] var3 = var2;
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = var3[var5];
         this.provides.putShort(this.symbolTable.addConstantClass(var6).index);
      }

      ++this.providesCount;
   }

   public void visitEnd() {
   }

   int getAttributeCount() {
      return 1 + (this.packageCount > 0 ? 1 : 0) + (this.mainClassIndex > 0 ? 1 : 0);
   }

   int computeAttributesSize() {
      this.symbolTable.addConstantUtf8("Module");
      int var1 = 22 + this.requires.length + this.exports.length + this.opens.length + this.usesIndex.length + this.provides.length;
      if (this.packageCount > 0) {
         this.symbolTable.addConstantUtf8("ModulePackages");
         var1 += 8 + this.packageIndex.length;
      }

      if (this.mainClassIndex > 0) {
         this.symbolTable.addConstantUtf8("ModuleMainClass");
         var1 += 8;
      }

      return var1;
   }

   void putAttributes(ByteVector var1) {
      int var2 = 16 + this.requires.length + this.exports.length + this.opens.length + this.usesIndex.length + this.provides.length;
      var1.putShort(this.symbolTable.addConstantUtf8("Module")).putInt(var2).putShort(this.moduleNameIndex).putShort(this.moduleFlags).putShort(this.moduleVersionIndex).putShort(this.requiresCount).putByteArray(this.requires.data, 0, this.requires.length).putShort(this.exportsCount).putByteArray(this.exports.data, 0, this.exports.length).putShort(this.opensCount).putByteArray(this.opens.data, 0, this.opens.length).putShort(this.usesCount).putByteArray(this.usesIndex.data, 0, this.usesIndex.length).putShort(this.providesCount).putByteArray(this.provides.data, 0, this.provides.length);
      if (this.packageCount > 0) {
         var1.putShort(this.symbolTable.addConstantUtf8("ModulePackages")).putInt(2 + this.packageIndex.length).putShort(this.packageCount).putByteArray(this.packageIndex.data, 0, this.packageIndex.length);
      }

      if (this.mainClassIndex > 0) {
         var1.putShort(this.symbolTable.addConstantUtf8("ModuleMainClass")).putInt(2).putShort(this.mainClassIndex);
      }

   }
}
