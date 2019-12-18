package org.jline.reader.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import org.jline.reader.Candidate;
import org.jline.reader.LineReader$Option;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

class LineReaderImpl$MenuSupport implements Supplier {
   final List possible;
   final BiFunction escaper;
   int selection;
   int topLine;
   String word;
   AttributedString computed;
   int lines;
   int columns;
   String completed;
   final LineReaderImpl this$0;

   public LineReaderImpl$MenuSupport(LineReaderImpl var1, List var2, String var3, BiFunction var4) {
      super();
      this.this$0 = var1;
      this.possible = new ArrayList();
      this.escaper = var4;
      this.selection = -1;
      this.topLine = 0;
      this.word = "";
      this.completed = var3;
      var1.computePost(var2, (Candidate)null, this.possible, var3);
      this.next();
   }

   public Candidate completion() {
      return (Candidate)this.possible.get(this.selection);
   }

   public void next() {
      this.selection = (this.selection + 1) % this.possible.size();
      this.update();
   }

   public void previous() {
      this.selection = (this.selection + this.possible.size() - 1) % this.possible.size();
      this.update();
   }

   private void major(int var1) {
      int var2 = this.this$0.isSet(LineReader$Option.LIST_ROWS_FIRST) ? this.columns : this.lines;
      int var3 = this.selection + var1 * var2;
      if (var3 < 0) {
         int var4 = (var3 + var2) % var2;
         int var5 = this.possible.size() % var2;
         var3 = this.possible.size() - var5 + var4;
         if (var3 >= this.possible.size()) {
            var3 -= var2;
         }
      } else if (var3 >= this.possible.size()) {
         var3 %= var2;
      }

      this.selection = var3;
      this.update();
   }

   private void minor(int var1) {
      int var2 = this.this$0.isSet(LineReader$Option.LIST_ROWS_FIRST) ? this.columns : this.lines;
      int var3 = this.selection % var2;
      int var4 = this.possible.size();
      if (this.selection - var3 + var2 > var4) {
         var2 = var4 % var2;
      }

      this.selection = this.selection - var3 + (var2 + var3 + var1) % var2;
      this.update();
   }

   public void up() {
      if (this.this$0.isSet(LineReader$Option.LIST_ROWS_FIRST)) {
         this.major(-1);
      } else {
         this.minor(-1);
      }

   }

   public void down() {
      if (this.this$0.isSet(LineReader$Option.LIST_ROWS_FIRST)) {
         this.major(1);
      } else {
         this.minor(1);
      }

   }

   public void left() {
      if (this.this$0.isSet(LineReader$Option.LIST_ROWS_FIRST)) {
         this.minor(-1);
      } else {
         this.major(-1);
      }

   }

   public void right() {
      if (this.this$0.isSet(LineReader$Option.LIST_ROWS_FIRST)) {
         this.minor(1);
      } else {
         this.major(1);
      }

   }

   private void update() {
      this.this$0.buf.backspace(this.word.length());
      this.word = ((CharSequence)this.escaper.apply(this.completion().value(), true)).toString();
      this.this$0.buf.write(this.word);
      LineReaderImpl$PostResult var1 = this.this$0.computePost(this.possible, this.completion(), (List)null, this.completed);
      AttributedString var2 = LineReaderImpl.access$000(this.this$0, AttributedStringBuilder.append(this.this$0.prompt, this.this$0.buf.toString()), new ArrayList());
      int var3 = var2.columnSplitLength(this.this$0.size.getColumns(), false, this.this$0.display.delayLineWrap()).size();
      if (var1.lines > this.this$0.size.getRows() - var3) {
         int var4 = this.this$0.size.getRows() - var3 - 1;
         if (var1.selectedLine >= 0) {
            if (var1.selectedLine < this.topLine) {
               this.topLine = var1.selectedLine;
            } else if (var1.selectedLine >= this.topLine + var4) {
               this.topLine = var1.selectedLine - var4 + 1;
            }
         }

         AttributedString var5 = var1.post;
         if (var5.length() > 0 && var5.charAt(var5.length() - 1) != '\n') {
            var5 = (new AttributedStringBuilder(var5.length() + 1)).append(var5).append((CharSequence)"\n").toAttributedString();
         }

         List var6 = var5.columnSplitLength(this.this$0.size.getColumns(), true, this.this$0.display.delayLineWrap());
         ArrayList var7 = new ArrayList(var6.subList(this.topLine, this.topLine + var4));
         var7.add((new AttributedStringBuilder()).style(AttributedStyle.DEFAULT.foreground(6)).append((CharSequence)"rows ").append((CharSequence)Integer.toString(this.topLine + 1)).append((CharSequence)" to ").append((CharSequence)Integer.toString(this.topLine + var4)).append((CharSequence)" of ").append((CharSequence)Integer.toString(var6.size())).append((CharSequence)"\n").style(AttributedStyle.DEFAULT).toAttributedString());
         this.computed = AttributedString.join(AttributedString.EMPTY, (Iterable)var7);
      } else {
         this.computed = var1.post;
      }

      this.lines = var1.lines;
      this.columns = (this.possible.size() + this.lines - 1) / this.lines;
   }

   public AttributedString get() {
      return this.computed;
   }

   public Object get() {
      return this.get();
   }
}
