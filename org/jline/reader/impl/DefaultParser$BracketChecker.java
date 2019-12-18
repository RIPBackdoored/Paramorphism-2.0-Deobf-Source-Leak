package org.jline.reader.impl;

import java.util.ArrayList;
import java.util.List;

class DefaultParser$BracketChecker {
   private int missingOpeningBracket;
   private List nested;
   final DefaultParser this$0;

   public DefaultParser$BracketChecker(DefaultParser var1) {
      super();
      this.this$0 = var1;
      this.missingOpeningBracket = -1;
      this.nested = new ArrayList();
   }

   public void check(CharSequence var1, int var2) {
      if (DefaultParser.access$000(this.this$0) != null && var2 >= 0) {
         int var3 = this.bracketId(DefaultParser.access$000(this.this$0), var1, var2);
         if (var3 >= 0) {
            this.nested.add(var3);
         } else {
            var3 = this.bracketId(DefaultParser.access$100(this.this$0), var1, var2);
            if (var3 >= 0) {
               if (!this.nested.isEmpty() && var3 == (Integer)this.nested.get(this.nested.size() - 1)) {
                  this.nested.remove(this.nested.size() - 1);
               } else {
                  this.missingOpeningBracket = var3;
               }
            }
         }

      }
   }

   public boolean isOpeningBracketMissing() {
      return this.missingOpeningBracket != -1;
   }

   public String getMissingOpeningBracket() {
      return !this.isOpeningBracketMissing() ? null : Character.toString(DefaultParser.access$000(this.this$0)[this.missingOpeningBracket]);
   }

   public boolean isClosingBracketMissing() {
      return !this.nested.isEmpty();
   }

   public String getMissingClosingBrackets() {
      if (!this.isClosingBracketMissing()) {
         return null;
      } else {
         StringBuilder var1 = new StringBuilder();

         for(int var2 = this.nested.size() - 1; var2 > -1; --var2) {
            var1.append(DefaultParser.access$100(this.this$0)[(Integer)this.nested.get(var2)]);
         }

         return var1.toString();
      }
   }

   private int bracketId(char[] var1, CharSequence var2, int var3) {
      for(int var4 = 0; var4 < var1.length; ++var4) {
         if (var2.charAt(var3) == var1[var4]) {
            return var4;
         }
      }

      return -1;
   }
}
