package org.objectweb.asm;

public class Attribute {
   public final String type;
   private byte[] content;
   Attribute nextAttribute;

   protected Attribute(String var1) {
      super();
      this.type = var1;
   }

   public boolean isUnknown() {
      return true;
   }

   public boolean isCodeAttribute() {
      return false;
   }

   protected Label[] getLabels() {
      return new Label[0];
   }

   protected Attribute read(ClassReader var1, int var2, int var3, char[] var4, int var5, Label[] var6) {
      Attribute var7 = new Attribute(this.type);
      var7.content = new byte[var3];
      System.arraycopy(var1.classFileBuffer, var2, var7.content, 0, var3);
      return var7;
   }

   protected ByteVector write(ClassWriter var1, byte[] var2, int var3, int var4, int var5) {
      return new ByteVector(this.content);
   }

   final int getAttributeCount() {
      int var1 = 0;

      for(Attribute var2 = this; var2 != null; var2 = var2.nextAttribute) {
         ++var1;
      }

      return var1;
   }

   final int computeAttributesSize(SymbolTable var1) {
      Object var2 = null;
      boolean var3 = false;
      boolean var4 = true;
      boolean var5 = true;
      return this.computeAttributesSize(var1, (byte[])var2, 0, -1, -1);
   }

   final int computeAttributesSize(SymbolTable var1, byte[] var2, int var3, int var4, int var5) {
      ClassWriter var6 = var1.classWriter;
      int var7 = 0;

      for(Attribute var8 = this; var8 != null; var8 = var8.nextAttribute) {
         var1.addConstantUtf8(var8.type);
         var7 += 6 + var8.write(var6, var2, var3, var4, var5).length;
      }

      return var7;
   }

   static int computeAttributesSize(SymbolTable var0, int var1, int var2) {
      int var3 = 0;
      if ((var1 & 4096) != 0 && var0.getMajorVersion() < 49) {
         var0.addConstantUtf8("Synthetic");
         var3 += 6;
      }

      if (var2 != 0) {
         var0.addConstantUtf8("Signature");
         var3 += 8;
      }

      if ((var1 & 131072) != 0) {
         var0.addConstantUtf8("Deprecated");
         var3 += 6;
      }

      return var3;
   }

   final void putAttributes(SymbolTable var1, ByteVector var2) {
      Object var3 = null;
      boolean var4 = false;
      boolean var5 = true;
      boolean var6 = true;
      this.putAttributes(var1, (byte[])var3, 0, -1, -1, var2);
   }

   final void putAttributes(SymbolTable var1, byte[] var2, int var3, int var4, int var5, ByteVector var6) {
      ClassWriter var7 = var1.classWriter;

      for(Attribute var8 = this; var8 != null; var8 = var8.nextAttribute) {
         ByteVector var9 = var8.write(var7, var2, var3, var4, var5);
         var6.putShort(var1.addConstantUtf8(var8.type)).putInt(var9.length);
         var6.putByteArray(var9.data, 0, var9.length);
      }

   }

   static void putAttributes(SymbolTable var0, int var1, int var2, ByteVector var3) {
      if ((var1 & 4096) != 0 && var0.getMajorVersion() < 49) {
         var3.putShort(var0.addConstantUtf8("Synthetic")).putInt(0);
      }

      if (var2 != 0) {
         var3.putShort(var0.addConstantUtf8("Signature")).putInt(2).putShort(var2);
      }

      if ((var1 & 131072) != 0) {
         var3.putShort(var0.addConstantUtf8("Deprecated")).putInt(0);
      }

   }
}
