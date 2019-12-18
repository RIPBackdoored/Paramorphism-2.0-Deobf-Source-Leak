package org.objectweb.asm.commons;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class SerialVersionUIDAdder extends ClassVisitor {
   private static final String CLINIT = "<clinit>";
   private boolean computeSvuid;
   private boolean hasSvuid;
   private int access;
   private String name;
   private String[] interfaces;
   private Collection svuidFields;
   private boolean hasStaticInitializer;
   private Collection svuidConstructors;
   private Collection svuidMethods;

   public SerialVersionUIDAdder(ClassVisitor var1) {
      this(458752, var1);
      if (this.getClass() != SerialVersionUIDAdder.class) {
         throw new IllegalStateException();
      }
   }

   protected SerialVersionUIDAdder(int var1, ClassVisitor var2) {
      super(var1, var2);
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      this.computeSvuid = (var2 & 16384) == 0;
      if (this.computeSvuid) {
         this.name = var3;
         this.access = var2;
         this.interfaces = new String[var6.length];
         this.svuidFields = new ArrayList();
         this.svuidConstructors = new ArrayList();
         this.svuidMethods = new ArrayList();
         System.arraycopy(var6, 0, this.interfaces, 0, var6.length);
      }

      super.visit(var1, var2, var3, var4, var5, var6);
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      if (this.computeSvuid) {
         if ("<clinit>".equals(var2)) {
            this.hasStaticInitializer = true;
         }

         int var6 = var1 & 3391;
         if ((var1 & 2) == 0) {
            if ("<init>".equals(var2)) {
               this.svuidConstructors.add(new SerialVersionUIDAdder$Item(var2, var6, var3));
            } else if (!"<clinit>".equals(var2)) {
               this.svuidMethods.add(new SerialVersionUIDAdder$Item(var2, var6, var3));
            }
         }
      }

      return super.visitMethod(var1, var2, var3, var4, var5);
   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      if (this.computeSvuid) {
         if ("serialVersionUID".equals(var2)) {
            this.computeSvuid = false;
            this.hasSvuid = true;
         }

         if ((var1 & 2) == 0 || (var1 & 136) == 0) {
            int var6 = var1 & 223;
            this.svuidFields.add(new SerialVersionUIDAdder$Item(var2, var6, var3));
         }
      }

      return super.visitField(var1, var2, var3, var4, var5);
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      if (this.name != null && this.name.equals(var1)) {
         this.access = var4;
      }

      super.visitInnerClass(var1, var2, var3, var4);
   }

   public void visitEnd() {
      if (this.computeSvuid && !this.hasSvuid) {
         try {
            this.addSVUID(this.computeSVUID());
         } catch (IOException var2) {
            throw new IllegalStateException("Error while computing SVUID for " + this.name, var2);
         }
      }

      super.visitEnd();
   }

   public boolean hasSVUID() {
      return this.hasSvuid;
   }

   protected void addSVUID(long var1) {
      FieldVisitor var3 = super.visitField(24, "serialVersionUID", "J", (String)null, var1);
      if (var3 != null) {
         var3.visitEnd();
      }

   }

   protected long computeSVUID() throws IOException {
      long var1 = 0L;
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      Throwable var4 = null;
      boolean var24 = false;

      try {
         var24 = true;
         DataOutputStream var5 = new DataOutputStream(var3);
         Throwable var6 = null;
         boolean var33 = false;

         try {
            var33 = true;
            var5.writeUTF(this.name.replace('/', '.'));
            int var7 = this.access;
            if ((var7 & 512) != 0) {
               var7 = this.svuidMethods.isEmpty() ? var7 & -1025 : var7 | 1024;
            }

            var5.writeInt(var7 & 1553);
            Arrays.sort(this.interfaces);
            String[] var8 = this.interfaces;
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               String var11 = var8[var10];
               var5.writeUTF(var11.replace('/', '.'));
            }

            writeItems(this.svuidFields, var5, false);
            if (this.hasStaticInitializer) {
               var5.writeUTF("<clinit>");
               var5.writeInt(8);
               var5.writeUTF("()V");
            }

            writeItems(this.svuidConstructors, var5, true);
            writeItems(this.svuidMethods, var5, true);
            var5.flush();
            byte[] var42 = this.computeSHAdigest(var3.toByteArray());

            for(var9 = Math.min(var42.length, 8) - 1; var9 >= 0; --var9) {
               var1 = var1 << 8 | (long)(var42[var9] & 255);
            }

            var33 = false;
         } catch (Throwable var38) {
            var6 = var38;
            throw var38;
         } finally {
            if (var33) {
               if (var5 != null) {
                  if (var6 != null) {
                     try {
                        var5.close();
                     } catch (Throwable var35) {
                     }
                  } else {
                     var5.close();
                  }
               }

            }
         }

         if (var5 != null) {
            if (var6 != null) {
               try {
                  var5.close();
                  var24 = false;
               } catch (Throwable var37) {
                  var24 = false;
               }
            } else {
               var5.close();
               var24 = false;
            }
         } else {
            var24 = false;
         }
      } catch (Throwable var40) {
         var4 = var40;
         throw var40;
      } finally {
         if (var24) {
            if (var3 != null) {
               if (var4 != null) {
                  try {
                     var3.close();
                  } catch (Throwable var34) {
                  }
               } else {
                  var3.close();
               }
            }

         }
      }

      if (var3 != null) {
         if (var4 != null) {
            try {
               var3.close();
            } catch (Throwable var36) {
            }
         } else {
            var3.close();
         }
      }

      return var1;
   }

   protected byte[] computeSHAdigest(byte[] var1) {
      byte[] var10000;
      try {
         var10000 = MessageDigest.getInstance("SHA").digest(var1);
      } catch (NoSuchAlgorithmException var3) {
         throw new UnsupportedOperationException(var3);
      }

      return var10000;
   }

   private static void writeItems(Collection var0, DataOutput var1, boolean var2) throws IOException {
      SerialVersionUIDAdder$Item[] var3 = (SerialVersionUIDAdder$Item[])var0.toArray(new SerialVersionUIDAdder$Item[0]);
      Arrays.sort(var3);
      SerialVersionUIDAdder$Item[] var4 = var3;
      int var5 = var3.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         SerialVersionUIDAdder$Item var7 = var4[var6];
         var1.writeUTF(var7.name);
         var1.writeInt(var7.access);
         var1.writeUTF(var2 ? var7.descriptor.replace('/', '.') : var7.descriptor);
      }

   }
}
