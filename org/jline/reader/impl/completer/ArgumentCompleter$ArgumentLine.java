package org.jline.reader.impl.completer;

import java.util.Collections;
import java.util.List;
import org.jline.reader.ParsedLine;

public class ArgumentCompleter$ArgumentLine implements ParsedLine {
   private final String word;
   private final int cursor;

   public ArgumentCompleter$ArgumentLine(String var1, int var2) {
      super();
      this.word = var1;
      this.cursor = var2;
   }

   public String word() {
      return this.word;
   }

   public int wordCursor() {
      return this.cursor;
   }

   public int wordIndex() {
      return 0;
   }

   public List words() {
      return Collections.singletonList(this.word);
   }

   public String line() {
      return this.word;
   }

   public int cursor() {
      return this.cursor;
   }
}
