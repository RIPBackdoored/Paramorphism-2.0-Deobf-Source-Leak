package org.jline.reader.impl.completer;

import java.util.Objects;
import org.jline.reader.Candidate;

public class EnumCompleter extends StringsCompleter {
   public EnumCompleter(Class var1) {
      super();
      Objects.requireNonNull(var1);
      Enum[] var2 = (Enum[])var1.getEnumConstants();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Enum var5 = var2[var4];
         this.candidates.add(new Candidate(var5.name().toLowerCase()));
      }

   }
}
