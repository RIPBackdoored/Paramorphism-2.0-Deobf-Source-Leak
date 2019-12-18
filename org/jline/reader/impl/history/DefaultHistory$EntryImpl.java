package org.jline.reader.impl.history;

import java.time.Instant;
import org.jline.reader.History$Entry;

public class DefaultHistory$EntryImpl implements History$Entry {
   private final int index;
   private final Instant time;
   private final String line;

   public DefaultHistory$EntryImpl(int var1, Instant var2, String var3) {
      super();
      this.index = var1;
      this.time = var2;
      this.line = var3;
   }

   public int index() {
      return this.index;
   }

   public Instant time() {
      return this.time;
   }

   public String line() {
      return this.line;
   }

   public String toString() {
      return String.format("%d: %s", this.index, this.line);
   }
}
