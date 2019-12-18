package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.TypePath;

public class ClassNode extends ClassVisitor {
   public int version;
   public int access;
   public String name;
   public String signature;
   public String superName;
   public List interfaces;
   public String sourceFile;
   public String sourceDebug;
   public ModuleNode module;
   public String outerClass;
   public String outerMethod;
   public String outerMethodDesc;
   public List visibleAnnotations;
   public List invisibleAnnotations;
   public List visibleTypeAnnotations;
   public List invisibleTypeAnnotations;
   public List attrs;
   public List innerClasses;
   public String nestHostClass;
   public List nestMembers;
   public List fields;
   public List methods;

   public ClassNode() {
      this(458752);
      if (this.getClass() != ClassNode.class) {
         throw new IllegalStateException();
      }
   }

   public ClassNode(int var1) {
      super(var1);
      this.interfaces = new ArrayList();
      this.innerClasses = new ArrayList();
      this.fields = new ArrayList();
      this.methods = new ArrayList();
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      this.version = var1;
      this.access = var2;
      this.name = var3;
      this.signature = var4;
      this.superName = var5;
      this.interfaces = Util.asArrayList((Object[])var6);
   }

   public void visitSource(String var1, String var2) {
      this.sourceFile = var1;
      this.sourceDebug = var2;
   }

   public ModuleVisitor visitModule(String var1, int var2, String var3) {
      this.module = new ModuleNode(var1, var2, var3);
      return this.module;
   }

   public void visitNestHost(String var1) {
      this.nestHostClass = var1;
   }

   public void visitOuterClass(String var1, String var2, String var3) {
      this.outerClass = var1;
      this.outerMethod = var2;
      this.outerMethodDesc = var3;
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      AnnotationNode var3 = new AnnotationNode(var1);
      if (var2) {
         if (this.visibleAnnotations == null) {
            this.visibleAnnotations = new ArrayList(1);
         }

         this.visibleAnnotations.add(var3);
      } else {
         if (this.invisibleAnnotations == null) {
            this.invisibleAnnotations = new ArrayList(1);
         }

         this.invisibleAnnotations.add(var3);
      }

      return var3;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      TypeAnnotationNode var5 = new TypeAnnotationNode(var1, var2, var3);
      if (var4) {
         if (this.visibleTypeAnnotations == null) {
            this.visibleTypeAnnotations = new ArrayList(1);
         }

         this.visibleTypeAnnotations.add(var5);
      } else {
         if (this.invisibleTypeAnnotations == null) {
            this.invisibleTypeAnnotations = new ArrayList(1);
         }

         this.invisibleTypeAnnotations.add(var5);
      }

      return var5;
   }

   public void visitAttribute(Attribute var1) {
      if (this.attrs == null) {
         this.attrs = new ArrayList(1);
      }

      this.attrs.add(var1);
   }

   public void visitNestMember(String var1) {
      if (this.nestMembers == null) {
         this.nestMembers = new ArrayList();
      }

      this.nestMembers.add(var1);
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      InnerClassNode var5 = new InnerClassNode(var1, var2, var3, var4);
      this.innerClasses.add(var5);
   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      FieldNode var6 = new FieldNode(var1, var2, var3, var4, var5);
      this.fields.add(var6);
      return var6;
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodNode var6 = new MethodNode(var1, var2, var3, var4, var5);
      this.methods.add(var6);
      return var6;
   }

   public void visitEnd() {
   }

   public void check(int var1) {
      if (var1 >= 458752 || this.nestHostClass == null && this.nestMembers == null) {
         if (var1 < 393216 && this.module != null) {
            throw new UnsupportedClassVersionException();
         } else {
            if (var1 < 327680) {
               if (this.visibleTypeAnnotations != null && !this.visibleTypeAnnotations.isEmpty()) {
                  throw new UnsupportedClassVersionException();
               }

               if (this.invisibleTypeAnnotations != null && !this.invisibleTypeAnnotations.isEmpty()) {
                  throw new UnsupportedClassVersionException();
               }
            }

            int var2;
            if (this.visibleAnnotations != null) {
               for(var2 = this.visibleAnnotations.size() - 1; var2 >= 0; --var2) {
                  ((AnnotationNode)this.visibleAnnotations.get(var2)).check(var1);
               }
            }

            if (this.invisibleAnnotations != null) {
               for(var2 = this.invisibleAnnotations.size() - 1; var2 >= 0; --var2) {
                  ((AnnotationNode)this.invisibleAnnotations.get(var2)).check(var1);
               }
            }

            if (this.visibleTypeAnnotations != null) {
               for(var2 = this.visibleTypeAnnotations.size() - 1; var2 >= 0; --var2) {
                  ((TypeAnnotationNode)this.visibleTypeAnnotations.get(var2)).check(var1);
               }
            }

            if (this.invisibleTypeAnnotations != null) {
               for(var2 = this.invisibleTypeAnnotations.size() - 1; var2 >= 0; --var2) {
                  ((TypeAnnotationNode)this.invisibleTypeAnnotations.get(var2)).check(var1);
               }
            }

            for(var2 = this.fields.size() - 1; var2 >= 0; --var2) {
               ((FieldNode)this.fields.get(var2)).check(var1);
            }

            for(var2 = this.methods.size() - 1; var2 >= 0; --var2) {
               ((MethodNode)this.methods.get(var2)).check(var1);
            }

         }
      } else {
         throw new UnsupportedClassVersionException();
      }
   }

   public void accept(ClassVisitor var1) {
      String[] var2 = new String[this.interfaces.size()];
      this.interfaces.toArray(var2);
      var1.visit(this.version, this.access, this.name, this.signature, this.superName, var2);
      if (this.sourceFile != null || this.sourceDebug != null) {
         var1.visitSource(this.sourceFile, this.sourceDebug);
      }

      if (this.module != null) {
         this.module.accept(var1);
      }

      if (this.nestHostClass != null) {
         var1.visitNestHost(this.nestHostClass);
      }

      if (this.outerClass != null) {
         var1.visitOuterClass(this.outerClass, this.outerMethod, this.outerMethodDesc);
      }

      int var3;
      int var4;
      AnnotationNode var5;
      if (this.visibleAnnotations != null) {
         var3 = 0;

         for(var4 = this.visibleAnnotations.size(); var3 < var4; ++var3) {
            var5 = (AnnotationNode)this.visibleAnnotations.get(var3);
            var5.accept(var1.visitAnnotation(var5.desc, true));
         }
      }

      if (this.invisibleAnnotations != null) {
         var3 = 0;

         for(var4 = this.invisibleAnnotations.size(); var3 < var4; ++var3) {
            var5 = (AnnotationNode)this.invisibleAnnotations.get(var3);
            var5.accept(var1.visitAnnotation(var5.desc, false));
         }
      }

      TypeAnnotationNode var6;
      if (this.visibleTypeAnnotations != null) {
         var3 = 0;

         for(var4 = this.visibleTypeAnnotations.size(); var3 < var4; ++var3) {
            var6 = (TypeAnnotationNode)this.visibleTypeAnnotations.get(var3);
            var6.accept(var1.visitTypeAnnotation(var6.typeRef, var6.typePath, var6.desc, true));
         }
      }

      if (this.invisibleTypeAnnotations != null) {
         var3 = 0;

         for(var4 = this.invisibleTypeAnnotations.size(); var3 < var4; ++var3) {
            var6 = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(var3);
            var6.accept(var1.visitTypeAnnotation(var6.typeRef, var6.typePath, var6.desc, false));
         }
      }

      if (this.attrs != null) {
         var3 = 0;

         for(var4 = this.attrs.size(); var3 < var4; ++var3) {
            var1.visitAttribute((Attribute)this.attrs.get(var3));
         }
      }

      if (this.nestMembers != null) {
         var3 = 0;

         for(var4 = this.nestMembers.size(); var3 < var4; ++var3) {
            var1.visitNestMember((String)this.nestMembers.get(var3));
         }
      }

      var3 = 0;

      for(var4 = this.innerClasses.size(); var3 < var4; ++var3) {
         ((InnerClassNode)this.innerClasses.get(var3)).accept(var1);
      }

      var3 = 0;

      for(var4 = this.fields.size(); var3 < var4; ++var3) {
         ((FieldNode)this.fields.get(var3)).accept(var1);
      }

      var3 = 0;

      for(var4 = this.methods.size(); var3 < var4; ++var3) {
         ((MethodNode)this.methods.get(var3)).accept(var1);
      }

      var1.visitEnd();
   }
}
