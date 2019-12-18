package org.jline.builtins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.terminal.Attributes;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal$Signal;
import org.jline.terminal.Terminal$SignalHandler;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.jline.utils.Display;
import org.jline.utils.InfoCmp$Capability;

public class Less {
   private static final int ESCAPE = 27;
   public boolean quitAtSecondEof;
   public boolean quitAtFirstEof;
   public boolean quitIfOneScreen;
   public boolean printLineNumbers;
   public boolean quiet;
   public boolean veryQuiet;
   public boolean chopLongLines;
   public boolean ignoreCaseCond;
   public boolean ignoreCaseAlways;
   public boolean noKeypad;
   public boolean noInit;
   public int tabs = 4;
   protected final Terminal terminal;
   protected final Display display;
   protected final BindingReader bindingReader;
   protected List sources;
   protected int sourceIdx;
   protected BufferedReader reader;
   protected KeyMap keys;
   protected int firstLineInMemory = 0;
   protected List lines = new ArrayList();
   protected int firstLineToDisplay = 0;
   protected int firstColumnToDisplay = 0;
   protected int offsetInLine = 0;
   protected String message;
   protected final StringBuilder buffer = new StringBuilder();
   protected final Map options = new TreeMap();
   protected int window;
   protected int halfWindow;
   protected int nbEof;
   protected String pattern;
   protected final Size size = new Size();

   public Less(Terminal var1) {
      super();
      this.terminal = var1;
      this.display = new Display(var1, true);
      this.bindingReader = new BindingReader(var1.reader());
   }

   public void handle(Terminal$Signal var1) {
      this.size.copy(this.terminal.getSize());

      try {
         this.display.clear();
         this.display(false);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void run(Source... var1) throws IOException, InterruptedException {
      this.run(Arrays.asList(var1));
   }

   public void run(List var1) throws IOException, InterruptedException {
      // $FF: Couldn't be decompiled
   }

   protected void openSource() throws IOException {
      if (this.reader != null) {
         this.reader.close();
      }

      Source var1 = (Source)this.sources.get(this.sourceIdx);
      InputStream var2 = var1.read();
      if (this.sources.size() == 1) {
         this.message = var1.getName();
      } else {
         this.message = var1.getName() + " (file " + (this.sourceIdx + 1) + " of " + this.sources.size() + ")";
      }

      this.reader = new BufferedReader(new InputStreamReader(new Less$InterruptibleInputStream(var2)));
      this.firstLineInMemory = 0;
      this.lines = new ArrayList();
      this.firstLineToDisplay = 0;
      this.firstColumnToDisplay = 0;
      this.offsetInLine = 0;
   }

   private void moveToNextMatch() throws IOException {
      Pattern var1 = this.getPattern();
      if (var1 != null) {
         int var2 = this.firstLineToDisplay + 1;

         while(true) {
            AttributedString var3 = this.getLine(var2);
            if (var3 == null) {
               break;
            }

            if (var1.matcher(var3).find()) {
               this.firstLineToDisplay = var2;
               this.offsetInLine = 0;
               return;
            }

            ++var2;
         }
      }

      this.message = "Pattern not found";
   }

   private void moveToPreviousMatch() throws IOException {
      Pattern var1 = this.getPattern();
      if (var1 != null) {
         for(int var2 = this.firstLineToDisplay - 1; var2 >= this.firstLineInMemory; --var2) {
            AttributedString var3 = this.getLine(var2);
            if (var3 == null) {
               break;
            }

            if (var1.matcher(var3).find()) {
               this.firstLineToDisplay = var2;
               this.offsetInLine = 0;
               return;
            }
         }
      }

      this.message = "Pattern not found";
   }

   private String printable(String var1) {
      StringBuilder var2 = new StringBuilder();

      for(int var3 = 0; var3 < var1.length(); ++var3) {
         char var4 = var1.charAt(var3);
         if (var4 == 27) {
            var2.append("ESC");
         } else if (var4 < ' ') {
            var2.append('^').append((char)(var4 + 64));
         } else if (var4 < 128) {
            var2.append(var4);
         } else {
            var2.append('\\').append(String.format("%03o", Integer.valueOf(var4)));
         }
      }

      return var2.toString();
   }

   void moveForward(int var1) throws IOException {
      int var2 = this.size.getColumns() - (this.printLineNumbers ? 8 : 0);
      int var3 = this.size.getRows();

      while(true) {
         --var1;
         if (var1 < 0) {
            return;
         }

         int var4 = this.firstLineToDisplay;
         if (this.firstColumnToDisplay <= 0 && !this.chopLongLines) {
            int var5 = this.offsetInLine;

            for(int var6 = 0; var6 < var3 - 1; ++var6) {
               AttributedString var7 = this.getLine(var4);
               if (var7 == null) {
                  break;
               }

               if (var7.columnLength() > var5 + var2) {
                  var5 += var2;
               } else {
                  var5 = 0;
                  ++var4;
               }
            }
         } else {
            var4 += var3 - 1;
         }

         if (this.getLine(var4) == null) {
            this.eof();
            return;
         }

         AttributedString var8 = this.getLine(this.firstLineToDisplay);
         if (var8.columnLength() > var2 + this.offsetInLine) {
            this.offsetInLine += var2;
         } else {
            this.offsetInLine = 0;
            ++this.firstLineToDisplay;
         }
      }
   }

   void moveBackward(int var1) throws IOException {
      int var2 = this.size.getColumns() - (this.printLineNumbers ? 8 : 0);

      while(true) {
         --var1;
         if (var1 < 0) {
            return;
         }

         if (this.offsetInLine > 0) {
            this.offsetInLine = Math.max(0, this.offsetInLine - var2);
         } else {
            if (this.firstLineInMemory >= this.firstLineToDisplay) {
               this.bof();
               return;
            }

            --this.firstLineToDisplay;
            AttributedString var3 = this.getLine(this.firstLineToDisplay);
            int var4 = var3.columnLength();
            this.offsetInLine = var4 - var4 % var2;
         }
      }
   }

   private void eof() {
      ++this.nbEof;
      if (this.sourceIdx < this.sources.size() - 1) {
         this.message = "(END) - Next: " + ((Source)this.sources.get(this.sourceIdx + 1)).getName();
      } else {
         this.message = "(END)";
      }

      if (!this.quiet && !this.veryQuiet && !this.quitAtFirstEof && !this.quitAtSecondEof) {
         this.terminal.puts(InfoCmp$Capability.bell);
         this.terminal.writer().flush();
      }

   }

   private void bof() {
      if (!this.quiet && !this.veryQuiet) {
         this.terminal.puts(InfoCmp$Capability.bell);
         this.terminal.writer().flush();
      }

   }

   int getStrictPositiveNumberInBuffer(int var1) {
      boolean var7 = false;

      int var3;
      label43: {
         try {
            var7 = true;
            int var2 = Integer.parseInt(this.buffer.toString());
            var3 = var2 > 0 ? var2 : var1;
            var7 = false;
            break label43;
         } catch (NumberFormatException var8) {
            var3 = var1;
            var7 = false;
         } finally {
            if (var7) {
               this.buffer.setLength(0);
            }
         }

         this.buffer.setLength(0);
         return var3;
      }

      this.buffer.setLength(0);
      return var3;
   }

   synchronized boolean display(boolean var1) throws IOException {
      ArrayList var2 = new ArrayList();
      int var3 = this.size.getColumns() - (this.printLineNumbers ? 8 : 0);
      int var4 = this.size.getRows();
      int var5 = this.firstLineToDisplay;
      AttributedString var6 = null;
      Pattern var7 = this.getPattern();
      boolean var8 = false;

      for(int var9 = 0; var9 < var4 - 1; ++var9) {
         if (var6 == null) {
            var6 = this.getLine(var5++);
            if (var6 == null) {
               if (var1) {
                  var8 = true;
                  break;
               }

               var6 = new AttributedString("");
            }

            if (var7 != null) {
               var6 = var6.styleMatches(var7, AttributedStyle.DEFAULT.inverse());
            }
         }

         AttributedString var10;
         if (this.firstColumnToDisplay <= 0 && !this.chopLongLines) {
            if (var9 == 0 && this.offsetInLine > 0) {
               var6 = var6.columnSubSequence(this.offsetInLine, 0);
            }

            var10 = var6.columnSubSequence(0, var3);
            var6 = var6.columnSubSequence(var3, 0);
            if (var6.length() == 0) {
               var6 = null;
            }
         } else {
            int var11 = this.firstColumnToDisplay;
            if (var9 == 0 && this.offsetInLine > 0) {
               var11 = Math.max(this.offsetInLine, var11);
            }

            var10 = var6.columnSubSequence(var11, var11 + var3);
            var6 = null;
         }

         if (this.printLineNumbers) {
            AttributedStringBuilder var13 = new AttributedStringBuilder();
            var13.append((CharSequence)String.format("%7d ", var5));
            var13.append(var10);
            var2.add(var13.toAttributedString());
         } else {
            var2.add(var10);
         }
      }

      if (var1) {
         if (var8) {
            var2.forEach(this::lambda$display$0);
         }

         return var8;
      } else {
         AttributedStringBuilder var12 = new AttributedStringBuilder();
         if (this.buffer.length() > 0) {
            var12.append((CharSequence)" ").append((CharSequence)this.buffer);
         } else if (this.bindingReader.getCurrentBuffer().length() > 0 && this.terminal.reader().peek(1L) == -2) {
            var12.append((CharSequence)" ").append((CharSequence)this.printable(this.bindingReader.getCurrentBuffer()));
         } else if (this.message != null) {
            var12.style(AttributedStyle.INVERSE);
            var12.append((CharSequence)this.message);
            var12.style(AttributedStyle.INVERSE.inverseOff());
         } else {
            var12.append((CharSequence)":");
         }

         var2.add(var12.toAttributedString());
         this.display.resize(this.size.getRows(), this.size.getColumns());
         this.display.update(var2, -1);
         return false;
      }
   }

   private Pattern getPattern() {
      Pattern var1 = null;
      if (this.pattern != null) {
         boolean var2 = this.ignoreCaseAlways || this.ignoreCaseCond && this.pattern.toLowerCase().equals(this.pattern);
         var1 = Pattern.compile("(" + this.pattern + ")", var2 ? 66 : 0);
      }

      return var1;
   }

   AttributedString getLine(int var1) throws IOException {
      while(true) {
         if (var1 >= this.lines.size()) {
            String var2 = this.reader.readLine();
            if (var2 != null) {
               this.lines.add(AttributedString.fromAnsi(var2, this.tabs));
               continue;
            }
         }

         if (var1 < this.lines.size()) {
            return (AttributedString)this.lines.get(var1);
         }

         return null;
      }
   }

   public static void checkInterrupted() throws InterruptedException {
      Thread.yield();
      if (Thread.currentThread().isInterrupted()) {
         throw new InterruptedException();
      }
   }

   private void bindKeys(KeyMap var1) {
      var1.bind(Less$Operation.HELP, (CharSequence[])("h", "H"));
      var1.bind(Less$Operation.EXIT, (CharSequence[])("q", ":q", "Q", ":Q", "ZZ"));
      var1.bind(Less$Operation.FORWARD_ONE_LINE, (CharSequence[])("e", KeyMap.ctrl('E'), "j", KeyMap.ctrl('N'), "\r", KeyMap.key(this.terminal, InfoCmp$Capability.key_down)));
      var1.bind(Less$Operation.BACKWARD_ONE_LINE, (CharSequence[])("y", KeyMap.ctrl('Y'), "k", KeyMap.ctrl('K'), KeyMap.ctrl('P'), KeyMap.key(this.terminal, InfoCmp$Capability.key_up)));
      var1.bind(Less$Operation.FORWARD_ONE_WINDOW_OR_LINES, (CharSequence[])("f", KeyMap.ctrl('F'), KeyMap.ctrl('V'), " "));
      var1.bind(Less$Operation.BACKWARD_ONE_WINDOW_OR_LINES, (CharSequence[])("b", KeyMap.ctrl('B'), KeyMap.alt('v')));
      var1.bind(Less$Operation.FORWARD_ONE_WINDOW_AND_SET, (CharSequence)"z");
      var1.bind(Less$Operation.BACKWARD_ONE_WINDOW_AND_SET, (CharSequence)"w");
      var1.bind(Less$Operation.FORWARD_ONE_WINDOW_NO_STOP, (CharSequence)KeyMap.alt(' '));
      var1.bind(Less$Operation.FORWARD_HALF_WINDOW_AND_SET, (CharSequence[])("d", KeyMap.ctrl('D')));
      var1.bind(Less$Operation.BACKWARD_HALF_WINDOW_AND_SET, (CharSequence[])("u", KeyMap.ctrl('U')));
      var1.bind(Less$Operation.RIGHT_ONE_HALF_SCREEN, (CharSequence[])(KeyMap.alt(')'), KeyMap.key(this.terminal, InfoCmp$Capability.key_right)));
      var1.bind(Less$Operation.LEFT_ONE_HALF_SCREEN, (CharSequence[])(KeyMap.alt('('), KeyMap.key(this.terminal, InfoCmp$Capability.key_left)));
      var1.bind(Less$Operation.FORWARD_FOREVER, (CharSequence)"F");
      var1.bind(Less$Operation.REPEAT_SEARCH_FORWARD, (CharSequence[])("n", "N"));
      var1.bind(Less$Operation.REPEAT_SEARCH_FORWARD_SPAN_FILES, (CharSequence[])(KeyMap.alt('n'), KeyMap.alt('N')));
      var1.bind(Less$Operation.UNDO_SEARCH, (CharSequence)KeyMap.alt('u'));
      var1.bind(Less$Operation.GO_TO_FIRST_LINE_OR_N, (CharSequence[])("g", "<", KeyMap.alt('<')));
      var1.bind(Less$Operation.GO_TO_LAST_LINE_OR_N, (CharSequence[])("G", ">", KeyMap.alt('>')));
      var1.bind(Less$Operation.NEXT_FILE, (CharSequence)":n");
      var1.bind(Less$Operation.PREV_FILE, (CharSequence)":p");
      "-/0123456789?".chars().forEach(Less::lambda$bindKeys$1);
   }

   private static void lambda$bindKeys$1(KeyMap var0, int var1) {
      var0.bind(Less$Operation.CHAR, (CharSequence)Character.toString((char)var1));
   }

   private void lambda$display$0(AttributedString var1) {
      var1.println(this.terminal);
   }
}
