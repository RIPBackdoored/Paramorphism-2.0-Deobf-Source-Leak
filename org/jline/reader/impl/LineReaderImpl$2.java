package org.jline.reader.impl;

import java.util.List;
import org.jline.reader.CompletingParsedLine;
import org.jline.reader.ParsedLine;

class LineReaderImpl$2 implements CompletingParsedLine {
   final ParsedLine val$line;
   final LineReaderImpl this$0;

   LineReaderImpl$2(LineReaderImpl var1, ParsedLine var2) {
      super();
      this.this$0 = var1;
      this.val$line = var2;
   }

   public String word() {
      return this.val$line.word();
   }

   public int wordCursor() {
      return this.val$line.wordCursor();
   }

   public int wordIndex() {
      return this.val$line.wordIndex();
   }

   public List words() {
      return this.val$line.words();
   }

   public String line() {
      return this.val$line.line();
   }

   public int cursor() {
      return this.val$line.cursor();
   }

   public CharSequence escape(CharSequence var1, boolean var2) {
      return var1;
   }

   public int rawWordCursor() {
      return this.wordCursor();
   }

   public int rawWordLength() {
      return this.word().length();
   }
}
