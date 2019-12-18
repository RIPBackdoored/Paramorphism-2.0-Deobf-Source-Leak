package org.jline.utils;

public class DiffHelper$Diff {
   public final DiffHelper$Operation operation;
   public final AttributedString text;

   public DiffHelper$Diff(DiffHelper$Operation var1, AttributedString var2) {
      super();
      this.operation = var1;
      this.text = var2;
   }

   public String toString() {
      return "Diff(" + this.operation + ",\"" + this.text + "\")";
   }
}
