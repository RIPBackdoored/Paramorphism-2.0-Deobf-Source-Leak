package org.jline.builtins;

class NfaMatcher$State {
   static final String Match = "++MATCH++";
   static final String Split = "++SPLIT++";
   final String c;
   NfaMatcher$State out;
   NfaMatcher$State out1;

   public NfaMatcher$State(String var1, NfaMatcher$State var2, NfaMatcher$State var3) {
      super();
      this.c = var1;
      this.out = var2;
      this.out1 = var3;
   }

   public void setOut(NfaMatcher$State var1) {
      this.out = var1;
   }

   public void setOut1(NfaMatcher$State var1) {
      this.out1 = var1;
   }
}
