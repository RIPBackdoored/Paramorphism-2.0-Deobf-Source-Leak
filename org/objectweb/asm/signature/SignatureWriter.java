package org.objectweb.asm.signature;

public class SignatureWriter extends SignatureVisitor {
   private final StringBuilder stringBuilder = new StringBuilder();
   private boolean hasFormals;
   private boolean hasParameters;
   private int argumentStack;

   public SignatureWriter() {
      super(458752);
   }

   public void visitFormalTypeParameter(String var1) {
      if (!this.hasFormals) {
         this.hasFormals = true;
         this.stringBuilder.append('<');
      }

      this.stringBuilder.append(var1);
      this.stringBuilder.append(':');
   }

   public SignatureVisitor visitClassBound() {
      return this;
   }

   public SignatureVisitor visitInterfaceBound() {
      this.stringBuilder.append(':');
      return this;
   }

   public SignatureVisitor visitSuperclass() {
      this.endFormals();
      return this;
   }

   public SignatureVisitor visitInterface() {
      return this;
   }

   public SignatureVisitor visitParameterType() {
      this.endFormals();
      if (!this.hasParameters) {
         this.hasParameters = true;
         this.stringBuilder.append('(');
      }

      return this;
   }

   public SignatureVisitor visitReturnType() {
      this.endFormals();
      if (!this.hasParameters) {
         this.stringBuilder.append('(');
      }

      this.stringBuilder.append(')');
      return this;
   }

   public SignatureVisitor visitExceptionType() {
      this.stringBuilder.append('^');
      return this;
   }

   public void visitBaseType(char var1) {
      this.stringBuilder.append(var1);
   }

   public void visitTypeVariable(String var1) {
      this.stringBuilder.append('T');
      this.stringBuilder.append(var1);
      this.stringBuilder.append(';');
   }

   public SignatureVisitor visitArrayType() {
      this.stringBuilder.append('[');
      return this;
   }

   public void visitClassType(String var1) {
      this.stringBuilder.append('L');
      this.stringBuilder.append(var1);
      this.argumentStack *= 2;
   }

   public void visitInnerClassType(String var1) {
      this.endArguments();
      this.stringBuilder.append('.');
      this.stringBuilder.append(var1);
      this.argumentStack *= 2;
   }

   public void visitTypeArgument() {
      if (this.argumentStack % 2 == 0) {
         this.argumentStack |= 1;
         this.stringBuilder.append('<');
      }

      this.stringBuilder.append('*');
   }

   public SignatureVisitor visitTypeArgument(char var1) {
      if (this.argumentStack % 2 == 0) {
         this.argumentStack |= 1;
         this.stringBuilder.append('<');
      }

      if (var1 != '=') {
         this.stringBuilder.append(var1);
      }

      return this;
   }

   public void visitEnd() {
      this.endArguments();
      this.stringBuilder.append(';');
   }

   public String toString() {
      return this.stringBuilder.toString();
   }

   private void endFormals() {
      if (this.hasFormals) {
         this.hasFormals = false;
         this.stringBuilder.append('>');
      }

   }

   private void endArguments() {
      if (this.argumentStack % 2 == 1) {
         this.stringBuilder.append('>');
      }

      this.argumentStack /= 2;
   }
}
