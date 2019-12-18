package org.jline.reader;

import java.io.IOError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.Log;

public final class LineReaderBuilder {
   Terminal terminal;
   String appName;
   Map variables = new HashMap();
   Map options = new HashMap();
   History history;
   Completer completer;
   History memoryHistory;
   Highlighter highlighter;
   Parser parser;
   Expander expander;

   public static LineReaderBuilder builder() {
      return new LineReaderBuilder();
   }

   private LineReaderBuilder() {
      super();
   }

   public LineReaderBuilder terminal(Terminal var1) {
      this.terminal = var1;
      return this;
   }

   public LineReaderBuilder appName(String var1) {
      this.appName = var1;
      return this;
   }

   public LineReaderBuilder variables(Map var1) {
      Map var2 = this.variables;
      this.variables = (Map)Objects.requireNonNull(var1);
      this.variables.putAll(var2);
      return this;
   }

   public LineReaderBuilder variable(String var1, Object var2) {
      this.variables.put(var1, var2);
      return this;
   }

   public LineReaderBuilder option(LineReader$Option var1, boolean var2) {
      this.options.put(var1, var2);
      return this;
   }

   public LineReaderBuilder history(History var1) {
      this.history = var1;
      return this;
   }

   public LineReaderBuilder completer(Completer var1) {
      this.completer = var1;
      return this;
   }

   public LineReaderBuilder highlighter(Highlighter var1) {
      this.highlighter = var1;
      return this;
   }

   public LineReaderBuilder parser(Parser var1) {
      if (var1 != null) {
         try {
            if (!Boolean.getBoolean("org.jline.reader.support.parsedline") && !(var1.parse("", 0) instanceof CompletingParsedLine)) {
               Log.warn("The Parser of class " + var1.getClass().getName() + " does not support the CompletingParsedLine interface. Completion with escaped or quoted words won't work correctly.");
            }
         } catch (Throwable var3) {
         }
      }

      this.parser = var1;
      return this;
   }

   public LineReaderBuilder expander(Expander var1) {
      this.expander = var1;
      return this;
   }

   public LineReader build() {
      Terminal var1 = this.terminal;
      if (var1 == null) {
         try {
            var1 = TerminalBuilder.terminal();
         } catch (IOException var5) {
            throw new IOError(var5);
         }
      }

      LineReaderImpl var2 = new LineReaderImpl(var1, this.appName, this.variables);
      if (this.history != null) {
         var2.setHistory(this.history);
      } else {
         if (this.memoryHistory == null) {
            this.memoryHistory = new DefaultHistory();
         }

         var2.setHistory(this.memoryHistory);
      }

      if (this.completer != null) {
         var2.setCompleter(this.completer);
      }

      if (this.highlighter != null) {
         var2.setHighlighter(this.highlighter);
      }

      if (this.parser != null) {
         var2.setParser(this.parser);
      }

      if (this.expander != null) {
         var2.setExpander(this.expander);
      }

      Iterator var3 = this.options.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         var2.option((LineReader$Option)var4.getKey(), (Boolean)var4.getValue());
      }

      return var2;
   }
}
