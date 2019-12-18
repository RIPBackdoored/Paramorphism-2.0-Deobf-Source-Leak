package org.objectweb.asm.commons;

import java.util.ArrayList;
import org.objectweb.asm.signature.SignatureVisitor;

public class SignatureRemapper extends SignatureVisitor {
   private final SignatureVisitor signatureVisitor;
   private final Remapper remapper;
   private ArrayList classNames;

   public SignatureRemapper(SignatureVisitor var1, Remapper var2) {
      this(458752, var1, var2);
   }

   protected SignatureRemapper(int var1, SignatureVisitor var2, Remapper var3) {
      super(var1);
      this.classNames = new ArrayList();
      this.signatureVisitor = var2;
      this.remapper = var3;
   }

   public void visitClassType(String var1) {
      this.classNames.add(var1);
      this.signatureVisitor.visitClassType(this.remapper.mapType(var1));
   }

   public void visitInnerClassType(String var1) {
      String var2 = (String)this.classNames.remove(this.classNames.size() - 1);
      String var3 = var2 + '$' + var1;
      this.classNames.add(var3);
      String var4 = this.remapper.mapType(var2) + '$';
      String var5 = this.remapper.mapType(var3);
      int var6 = var5.startsWith(var4) ? var4.length() : var5.lastIndexOf(36) + 1;
      this.signatureVisitor.visitInnerClassType(var5.substring(var6));
   }

   public void visitFormalTypeParameter(String var1) {
      this.signatureVisitor.visitFormalTypeParameter(var1);
   }

   public void visitTypeVariable(String var1) {
      this.signatureVisitor.visitTypeVariable(var1);
   }

   public SignatureVisitor visitArrayType() {
      this.signatureVisitor.visitArrayType();
      return this;
   }

   public void visitBaseType(char var1) {
      this.signatureVisitor.visitBaseType(var1);
   }

   public SignatureVisitor visitClassBound() {
      this.signatureVisitor.visitClassBound();
      return this;
   }

   public SignatureVisitor visitExceptionType() {
      this.signatureVisitor.visitExceptionType();
      return this;
   }

   public SignatureVisitor visitInterface() {
      this.signatureVisitor.visitInterface();
      return this;
   }

   public SignatureVisitor visitInterfaceBound() {
      this.signatureVisitor.visitInterfaceBound();
      return this;
   }

   public SignatureVisitor visitParameterType() {
      this.signatureVisitor.visitParameterType();
      return this;
   }

   public SignatureVisitor visitReturnType() {
      this.signatureVisitor.visitReturnType();
      return this;
   }

   public SignatureVisitor visitSuperclass() {
      this.signatureVisitor.visitSuperclass();
      return this;
   }

   public void visitTypeArgument() {
      this.signatureVisitor.visitTypeArgument();
   }

   public SignatureVisitor visitTypeArgument(char var1) {
      this.signatureVisitor.visitTypeArgument(var1);
      return this;
   }

   public void visitEnd() {
      this.signatureVisitor.visitEnd();
      this.classNames.remove(this.classNames.size() - 1);
   }
}
