package org.objectweb.asm.commons;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

public final class ModuleHashesAttribute extends Attribute {
   public String algorithm;
   public List modules;
   public List hashes;

   public ModuleHashesAttribute(String var1, List var2, List var3) {
      super("ModuleHashes");
      this.algorithm = var1;
      this.modules = var2;
      this.hashes = var3;
   }

   public ModuleHashesAttribute() {
      this((String)null, (List)null, (List)null);
   }

   protected Attribute read(ClassReader var1, int var2, int var3, char[] var4, int var5, Label[] var6) {
      String var8 = var1.readUTF8(var2, var4);
      int var7 = var2 + 2;
      int var9 = var1.readUnsignedShort(var7);
      var7 += 2;
      ArrayList var10 = new ArrayList(var9);
      ArrayList var11 = new ArrayList(var9);

      for(int var12 = 0; var12 < var9; ++var12) {
         String var13 = var1.readModule(var7, var4);
         var7 += 2;
         var10.add(var13);
         int var14 = var1.readUnsignedShort(var7);
         var7 += 2;
         byte[] var15 = new byte[var14];

         for(int var16 = 0; var16 < var14; ++var16) {
            var15[var16] = (byte)(var1.readByte(var7) & 255);
            ++var7;
         }

         var11.add(var15);
      }

      return new ModuleHashesAttribute(var8, var10, var11);
   }

   protected ByteVector write(ClassWriter var1, byte[] var2, int var3, int var4, int var5) {
      ByteVector var6 = new ByteVector();
      var6.putShort(var1.newUTF8(this.algorithm));
      if (this.modules == null) {
         var6.putShort(0);
      } else {
         int var7 = this.modules.size();
         var6.putShort(var7);

         for(int var8 = 0; var8 < var7; ++var8) {
            String var9 = (String)this.modules.get(var8);
            byte[] var10 = (byte[])this.hashes.get(var8);
            var6.putShort(var1.newModule(var9)).putShort(var10.length).putByteArray(var10, 0, var10.length);
         }
      }

      return var6;
   }
}
