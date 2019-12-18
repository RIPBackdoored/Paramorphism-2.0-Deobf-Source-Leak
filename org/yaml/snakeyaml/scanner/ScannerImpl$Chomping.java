package org.yaml.snakeyaml.scanner;

class ScannerImpl$Chomping {
   private final Boolean value;
   private final int increment;

   public ScannerImpl$Chomping(Boolean var1, int var2) {
      super();
      this.value = var1;
      this.increment = var2;
   }

   public boolean chompTailIsNotFalse() {
      return this.value == null || this.value;
   }

   public boolean chompTailIsTrue() {
      return this.value != null && this.value;
   }

   public int getIncrement() {
      return this.increment;
   }
}
