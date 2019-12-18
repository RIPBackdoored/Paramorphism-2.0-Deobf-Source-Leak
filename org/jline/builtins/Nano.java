package org.jline.builtins;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.terminal.Attributes;
import org.jline.terminal.Attributes$ControlChar;
import org.jline.terminal.Attributes$InputFlag;
import org.jline.terminal.Attributes$LocalFlag;
import org.jline.terminal.MouseEvent;
import org.jline.terminal.MouseEvent$Button;
import org.jline.terminal.MouseEvent$Type;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal$MouseTracking;
import org.jline.terminal.Terminal$Signal;
import org.jline.terminal.Terminal$SignalHandler;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.jline.utils.Display;
import org.jline.utils.InfoCmp$Capability;

public class Nano {
   protected final Terminal terminal;
   protected final Display display;
   protected final BindingReader bindingReader;
   protected final Size size;
   protected final Path root;
   protected KeyMap keys;
   public String title;
   public boolean printLineNumbers;
   public boolean wrapping;
   public boolean smoothScrolling;
   public boolean mouseSupport;
   public boolean oneMoreLine;
   public boolean constantCursor;
   public int tabs;
   public String brackets;
   public String matchBrackets;
   public String punct;
   public String quoteStr;
   protected final List buffers;
   protected int bufferIndex;
   protected Nano$Buffer buffer;
   protected String message;
   protected int nbBindings;
   protected LinkedHashMap shortcuts;
   protected String editMessage;
   protected final StringBuilder editBuffer;
   protected boolean searchCaseSensitive;
   protected boolean searchRegexp;
   protected boolean searchBackwards;
   protected String searchTerm;
   protected Nano$WriteMode writeMode;
   protected boolean writeBackup;
   protected boolean readNewBuffer;

   public Nano(Terminal var1, File var2) {
      this(var1, var2.toPath());
   }

   public Nano(Terminal var1, Path var2) {
      super();
      this.title = "JLine Nano 3.0.0";
      this.printLineNumbers = true;
      this.wrapping = true;
      this.smoothScrolling = true;
      this.mouseSupport = false;
      this.oneMoreLine = true;
      this.tabs = 4;
      this.brackets = "\"’)>]}";
      this.matchBrackets = "(<[{)>]}";
      this.punct = "!.?";
      this.quoteStr = "^([ \\t]*[#:>\\|}])+";
      this.buffers = new ArrayList();
      this.nbBindings = 0;
      this.editBuffer = new StringBuilder();
      this.writeMode = Nano$WriteMode.WRITE;
      this.readNewBuffer = true;
      this.terminal = var1;
      this.root = var2;
      this.display = new Display(var1, true);
      this.bindingReader = new BindingReader(var1.reader());
      this.size = new Size();
      this.bindKeys();
   }

   public void open(String... var1) throws IOException {
      this.open(Arrays.asList(var1));
   }

   public void open(List var1) throws IOException {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         this.buffers.add(new Nano$Buffer(this, var3));
      }

   }

   public void run() throws IOException {
      // $FF: Couldn't be decompiled
   }

   boolean write() throws IOException {
      // $FF: Couldn't be decompiled
   }

   private Nano$Operation readOperation(KeyMap var1) {
      while(true) {
         Nano$Operation var2 = (Nano$Operation)this.bindingReader.readBinding(var1);
         if (var2 != Nano$Operation.DO_LOWER_CASE) {
            return var2;
         }

         this.bindingReader.runMacro(this.bindingReader.getLastBinding().toLowerCase());
      }
   }

   private boolean save(String var1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private Nano$Operation getYNC(String var1) {
      String var2 = this.editMessage;
      String var3 = this.editBuffer.toString();
      LinkedHashMap var4 = this.shortcuts;
      boolean var9 = false;

      Nano$Operation var6;
      try {
         var9 = true;
         this.editMessage = var1;
         this.editBuffer.setLength(0);
         KeyMap var5 = new KeyMap();
         var5.bind(Nano$Operation.YES, (CharSequence[])("y", "Y"));
         var5.bind(Nano$Operation.NO, (CharSequence[])("n", "N"));
         var5.bind(Nano$Operation.CANCEL, (CharSequence)KeyMap.ctrl('C'));
         this.shortcuts = new LinkedHashMap();
         this.shortcuts.put(" Y", "Yes");
         this.shortcuts.put(" N", "No");
         this.shortcuts.put("^C", "Cancel");
         this.display();
         var6 = this.readOperation(var5);
         var9 = false;
      } finally {
         if (var9) {
            this.editMessage = var2;
            this.editBuffer.append(var3);
            this.shortcuts = var4;
         }
      }

      this.editMessage = var2;
      this.editBuffer.append(var3);
      this.shortcuts = var4;
      return var6;
   }

   private String getWriteMessage() {
      // $FF: Couldn't be decompiled
   }

   void read() {
      // $FF: Couldn't be decompiled
   }

   private String getReadMessage() {
      StringBuilder var1 = new StringBuilder();
      var1.append("File to insert");
      if (this.readNewBuffer) {
         var1.append(" into new buffer");
      }

      var1.append(" [from ./]: ");
      return var1.toString();
   }

   private LinkedHashMap readShortcuts() {
      LinkedHashMap var1 = new LinkedHashMap();
      var1.put("^G", "Get Help");
      var1.put("^T", "To Files");
      var1.put("M-F", "New Buffer");
      var1.put("^C", "Cancel");
      var1.put("^X", "Execute Command");
      return var1;
   }

   private LinkedHashMap writeShortcuts() {
      LinkedHashMap var1 = new LinkedHashMap();
      var1.put("^G", "Get Help");
      var1.put("^T", "To Files");
      var1.put("M-M", "Mac Format");
      var1.put("M-P", "Prepend");
      var1.put("^C", "Cancel");
      var1.put("M-D", "DOS Format");
      var1.put("M-A", "Append");
      var1.put("M-B", "Backup File");
      return var1;
   }

   private LinkedHashMap helpShortcuts() {
      LinkedHashMap var1 = new LinkedHashMap();
      var1.put("^L", "Refresh");
      var1.put("^Y", "Prev Page");
      var1.put("^P", "Prev Line");
      var1.put("M-\\", "First Line");
      var1.put("^X", "Exit");
      var1.put("^V", "Next Page");
      var1.put("^N", "Next Line");
      var1.put("M-/", "Last Line");
      return var1;
   }

   private LinkedHashMap searchShortcuts() {
      LinkedHashMap var1 = new LinkedHashMap();
      var1.put("^G", "Get Help");
      var1.put("^Y", "First Line");
      var1.put("^R", "Replace");
      var1.put("^W", "Beg of Par");
      var1.put("M-C", "Case Sens");
      var1.put("M-R", "Regexp");
      var1.put("^C", "Cancel");
      var1.put("^V", "Last Line");
      var1.put("^T", "Go To Line");
      var1.put("^O", "End of Par");
      var1.put("M-B", "Backwards");
      var1.put("^P", "PrevHstory");
      return var1;
   }

   private LinkedHashMap standardShortcuts() {
      LinkedHashMap var1 = new LinkedHashMap();
      var1.put("^G", "Get Help");
      var1.put("^O", "WriteOut");
      var1.put("^R", "Read File");
      var1.put("^Y", "Prev Page");
      var1.put("^K", "Cut Text");
      var1.put("^C", "Cur Pos");
      var1.put("^X", "Exit");
      var1.put("^J", "Justify");
      var1.put("^W", "Where Is");
      var1.put("^V", "Next Page");
      var1.put("^U", "UnCut Text");
      var1.put("^T", "To Spell");
      return var1;
   }

   void help(String var1) {
      // $FF: Couldn't be decompiled
   }

   void search() throws IOException {
      // $FF: Couldn't be decompiled
   }

   private String getSearchMessage() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Search");
      if (this.searchCaseSensitive) {
         var1.append(" [Case Sensitive]");
      }

      if (this.searchRegexp) {
         var1.append(" [Regexp]");
      }

      if (this.searchBackwards) {
         var1.append(" [Backwards]");
      }

      if (this.searchTerm != null) {
         var1.append(" [");
         var1.append(this.searchTerm);
         var1.append("]");
      }

      var1.append(": ");
      return var1.toString();
   }

   String computeCurPos() {
      int var1 = 0;
      int var2 = 0;

      for(int var3 = 0; var3 < this.buffer.lines.size(); ++var3) {
         int var4 = ((String)this.buffer.lines.get(var3)).length() + 1;
         if (var3 < this.buffer.line) {
            var1 += var4;
         } else if (var3 == this.buffer.line) {
            var1 += this.buffer.offsetInLine + this.buffer.column;
         }

         var2 += var4;
      }

      StringBuilder var5 = new StringBuilder();
      var5.append("line ");
      var5.append(this.buffer.line + 1);
      var5.append("/");
      var5.append(this.buffer.lines.size());
      var5.append(" (");
      var5.append(Math.round(100.0D * (double)this.buffer.line / (double)this.buffer.lines.size()));
      var5.append("%), ");
      var5.append("col ");
      var5.append(this.buffer.offsetInLine + this.buffer.column + 1);
      var5.append("/");
      var5.append(((String)this.buffer.lines.get(this.buffer.line)).length() + 1);
      var5.append(" (");
      if (((String)this.buffer.lines.get(this.buffer.line)).length() > 0) {
         var5.append(Math.round(100.0D * (double)(this.buffer.offsetInLine + this.buffer.column) / (double)((String)this.buffer.lines.get(this.buffer.line)).length()));
      } else {
         var5.append("100");
      }

      var5.append("%), ");
      var5.append("char ");
      var5.append(var1 + 1);
      var5.append("/");
      var5.append(var2);
      var5.append(" (");
      var5.append(Math.round(100.0D * (double)var1 / (double)var2));
      var5.append("%)");
      return var5.toString();
   }

   void curPos() {
      this.setMessage(this.computeCurPos());
   }

   void prevBuffer() throws IOException {
      if (this.buffers.size() > 1) {
         this.bufferIndex = (this.bufferIndex + this.buffers.size() - 1) % this.buffers.size();
         this.buffer = (Nano$Buffer)this.buffers.get(this.bufferIndex);
         this.setMessage("Switched to " + this.buffer.getTitle());
         this.buffer.open();
         this.display.clear();
      } else {
         this.setMessage("No more open file buffers");
      }

   }

   void nextBuffer() throws IOException {
      if (this.buffers.size() > 1) {
         this.bufferIndex = (this.bufferIndex + 1) % this.buffers.size();
         this.buffer = (Nano$Buffer)this.buffers.get(this.bufferIndex);
         this.setMessage("Switched to " + this.buffer.getTitle());
         this.buffer.open();
         this.display.clear();
      } else {
         this.setMessage("No more open file buffers");
      }

   }

   void setMessage(String var1) {
      this.message = var1;
      this.nbBindings = 25;
   }

   boolean quit() throws IOException {
      // $FF: Couldn't be decompiled
   }

   void numbers() {
      this.printLineNumbers = !this.printLineNumbers;
      this.resetDisplay();
      this.setMessage("Lines numbering " + (this.printLineNumbers ? "enabled" : "disabled"));
   }

   void smoothScrolling() {
      this.smoothScrolling = !this.smoothScrolling;
      this.setMessage("Smooth scrolling " + (this.smoothScrolling ? "enabled" : "disabled"));
   }

   void mouseSupport() throws IOException {
      this.mouseSupport = !this.mouseSupport;
      this.setMessage("Mouse support " + (this.mouseSupport ? "enabled" : "disabled"));
      this.terminal.trackMouse(this.mouseSupport ? Terminal$MouseTracking.Normal : Terminal$MouseTracking.Off);
   }

   void constantCursor() {
      this.constantCursor = !this.constantCursor;
      this.setMessage("Constant cursor position display " + (this.constantCursor ? "enabled" : "disabled"));
   }

   void oneMoreLine() {
      this.oneMoreLine = !this.oneMoreLine;
      this.setMessage("Use of one more line for editing " + (this.oneMoreLine ? "enabled" : "disabled"));
   }

   void wrap() {
      this.wrapping = !this.wrapping;
      this.resetDisplay();
      this.setMessage("Lines wrapping " + (this.wrapping ? "enabled" : "disabled"));
   }

   void clearScreen() {
      this.resetDisplay();
   }

   void mouseEvent() {
      MouseEvent var1 = this.terminal.readMouseEvent();
      if (var1.getModifiers().isEmpty() && var1.getType() == MouseEvent$Type.Released && var1.getButton() == MouseEvent$Button.Button1) {
         int var2 = var1.getX();
         int var3 = var1.getY();
         int var4 = this.buffer.computeHeader().size();
         int var5 = this.computeFooter().size();
         if (var3 >= var4) {
            if (var3 < this.size.getRows() - var5) {
               this.buffer.moveTo(var2, var3 - var4);
            } else {
               int var6 = (this.shortcuts.size() + 1) / 2;
               int var7 = this.size.getColumns() / var6;
               int var8 = var3 - (this.size.getRows() - var5) - 1;
               int var9 = var8 * var6 + var2 / var7;
               String var10 = null;

               for(Iterator var11 = this.shortcuts.keySet().iterator(); var9-- >= 0 && var11.hasNext(); var10 = (String)var11.next()) {
               }

               if (var10 != null) {
                  var10 = var10.replaceAll("M-", "\\\\E");
                  String var12 = KeyMap.translate(var10);
                  this.bindingReader.runMacro(var12);
               }
            }
         }
      } else if (var1.getType() == MouseEvent$Type.Wheel) {
         if (var1.getButton() == MouseEvent$Button.WheelDown) {
            this.buffer.moveDown(1);
         } else if (var1.getButton() == MouseEvent$Button.WheelUp) {
            this.buffer.moveUp(1);
         }
      }

   }

   public String getTitle() {
      return this.title;
   }

   void resetDisplay() {
      this.display.clear();
      this.display.resize(this.size.getRows(), this.size.getColumns());
      Iterator var1 = this.buffers.iterator();

      while(var1.hasNext()) {
         Nano$Buffer var2 = (Nano$Buffer)var1.next();
         var2.resetDisplay();
      }

   }

   synchronized void display() {
      if (this.nbBindings > 0 && --this.nbBindings == 0) {
         this.message = null;
      }

      List var1 = this.buffer.computeHeader();
      List var2 = this.computeFooter();
      int var3 = this.size.getRows() - var1.size() - var2.size();
      List var4 = this.buffer.getDisplayedLines(var3);
      var4.addAll(0, var1);
      var4.addAll(var2);
      int var5;
      if (this.editMessage != null) {
         var5 = this.editMessage.length() + this.editBuffer.length();
         var5 = this.size.cursorPos(this.size.getRows() - var2.size(), var5);
      } else {
         var5 = this.size.cursorPos(var1.size(), this.buffer.getDisplayedCursor());
      }

      this.display.update(var4, var5);
   }

   protected List computeFooter() {
      ArrayList var1 = new ArrayList();
      int var3;
      int var4;
      int var6;
      if (this.editMessage != null) {
         AttributedStringBuilder var2 = new AttributedStringBuilder();
         var2.style(AttributedStyle.INVERSE);
         var2.append((CharSequence)this.editMessage);
         var2.append((CharSequence)this.editBuffer);

         for(var3 = this.editMessage.length() + this.editBuffer.length(); var3 < this.size.getColumns(); ++var3) {
            var2.append(' ');
         }

         var2.append('\n');
         var1.add(var2.toAttributedString());
      } else if (this.message == null && !this.constantCursor) {
         var1.add(new AttributedString("\n"));
      } else {
         int var14 = this.size.getColumns();
         String var16 = "[ " + (this.message == null ? this.computeCurPos() : this.message) + " ]";
         var4 = var16.length();
         AttributedStringBuilder var5 = new AttributedStringBuilder();

         for(var6 = 0; var6 < (var14 - var4) / 2; ++var6) {
            var5.append(' ');
         }

         var5.style(AttributedStyle.INVERSE);
         var5.append((CharSequence)var16);
         var5.append('\n');
         var1.add(var5.toAttributedString());
      }

      Iterator var15 = this.shortcuts.entrySet().iterator();
      var3 = (this.shortcuts.size() + 1) / 2;
      var4 = (this.size.getColumns() - 1) / var3;
      int var17 = (this.size.getColumns() - 1) % var3;

      for(var6 = 0; var6 < 2; ++var6) {
         AttributedStringBuilder var7 = new AttributedStringBuilder();

         for(int var8 = 0; var8 < var3; ++var8) {
            Entry var9 = var15.hasNext() ? (Entry)var15.next() : null;
            String var10 = var9 != null ? (String)var9.getKey() : "";
            String var11 = var9 != null ? (String)var9.getValue() : "";
            var7.style(AttributedStyle.INVERSE);
            var7.append((CharSequence)var10);
            var7.style(AttributedStyle.DEFAULT);
            var7.append((CharSequence)" ");
            int var12 = var4 - var10.length() - 1 + (var8 < var17 ? 1 : 0);
            if (var11.length() > var12) {
               var7.append((CharSequence)var11.substring(0, var12));
            } else {
               var7.append((CharSequence)var11);
               if (var8 < var3 - 1) {
                  for(int var13 = 0; var13 < var12 - var11.length(); ++var13) {
                     var7.append((CharSequence)" ");
                  }
               }
            }
         }

         var7.append('\n');
         var1.add(var7.toAttributedString());
      }

      return var1;
   }

   protected void handle(Terminal$Signal var1) {
      if (this.buffer != null) {
         this.size.copy(this.terminal.getSize());
         this.buffer.computeAllOffsets();
         this.buffer.moveToChar(this.buffer.offsetInLine + this.buffer.column);
         this.resetDisplay();
         this.display();
      }

   }

   protected void bindKeys() {
      this.keys = new KeyMap();
      this.keys.setUnicode(Nano$Operation.INSERT);

      char var1;
      for(var1 = ' '; var1 < 128; ++var1) {
         this.keys.bind(Nano$Operation.INSERT, (CharSequence)Character.toString(var1));
      }

      this.keys.bind(Nano$Operation.BACKSPACE, (CharSequence)KeyMap.del());

      for(var1 = 'A'; var1 <= 'Z'; ++var1) {
         this.keys.bind(Nano$Operation.DO_LOWER_CASE, (CharSequence)KeyMap.alt(var1));
      }

      this.keys.bind(Nano$Operation.HELP, (CharSequence[])(KeyMap.ctrl('G'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f1)));
      this.keys.bind(Nano$Operation.QUIT, (CharSequence[])(KeyMap.ctrl('X'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f2)));
      this.keys.bind(Nano$Operation.WRITE, (CharSequence[])(KeyMap.ctrl('O'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f3)));
      this.keys.bind(Nano$Operation.JUSTIFY_PARAGRAPH, (CharSequence[])(KeyMap.ctrl('J'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f4)));
      this.keys.bind(Nano$Operation.READ, (CharSequence[])(KeyMap.ctrl('R'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f5)));
      this.keys.bind(Nano$Operation.SEARCH, (CharSequence[])(KeyMap.ctrl('W'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f6)));
      this.keys.bind(Nano$Operation.PREV_PAGE, (CharSequence[])(KeyMap.ctrl('Y'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f7)));
      this.keys.bind(Nano$Operation.NEXT_PAGE, (CharSequence[])(KeyMap.ctrl('V'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f8)));
      this.keys.bind(Nano$Operation.CUT, (CharSequence[])(KeyMap.ctrl('K'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f9)));
      this.keys.bind(Nano$Operation.UNCUT, (CharSequence[])(KeyMap.ctrl('U'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f10)));
      this.keys.bind(Nano$Operation.CUR_POS, (CharSequence[])(KeyMap.ctrl('C'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f11)));
      this.keys.bind(Nano$Operation.TO_SPELL, (CharSequence[])(KeyMap.ctrl('T'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f11)));
      this.keys.bind(Nano$Operation.GOTO, (CharSequence[])(KeyMap.ctrl('_'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f13), KeyMap.alt('g')));
      this.keys.bind(Nano$Operation.REPLACE, (CharSequence[])(KeyMap.ctrl('\\'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f14), KeyMap.alt('r')));
      this.keys.bind(Nano$Operation.MARK, (CharSequence[])(KeyMap.ctrl('^'), KeyMap.key(this.terminal, InfoCmp$Capability.key_f15), KeyMap.alt('a')));
      this.keys.bind(Nano$Operation.NEXT_SEARCH, (CharSequence[])(KeyMap.key(this.terminal, InfoCmp$Capability.key_f16), KeyMap.alt('w')));
      this.keys.bind(Nano$Operation.COPY, (CharSequence)KeyMap.alt('^'));
      this.keys.bind(Nano$Operation.INDENT, (CharSequence)KeyMap.alt('}'));
      this.keys.bind(Nano$Operation.UNINDENT, (CharSequence)KeyMap.alt('{'));
      this.keys.bind(Nano$Operation.RIGHT, (CharSequence)KeyMap.ctrl('F'));
      this.keys.bind(Nano$Operation.LEFT, (CharSequence)KeyMap.ctrl('B'));
      this.keys.bind(Nano$Operation.NEXT_WORD, (CharSequence)KeyMap.ctrl(' '));
      this.keys.bind(Nano$Operation.PREV_WORD, (CharSequence)KeyMap.alt(' '));
      this.keys.bind(Nano$Operation.UP, (CharSequence)KeyMap.ctrl('P'));
      this.keys.bind(Nano$Operation.DOWN, (CharSequence)KeyMap.ctrl('N'));
      this.keys.bind(Nano$Operation.BEGINNING_OF_LINE, (CharSequence)KeyMap.ctrl('A'));
      this.keys.bind(Nano$Operation.END_OF_LINE, (CharSequence)KeyMap.ctrl('E'));
      this.keys.bind(Nano$Operation.BEGINNING_OF_PARAGRAPH, (CharSequence[])(KeyMap.alt('('), KeyMap.alt('9')));
      this.keys.bind(Nano$Operation.END_OF_PARAGRAPH, (CharSequence[])(KeyMap.alt(')'), KeyMap.alt('0')));
      this.keys.bind(Nano$Operation.FIRST_LINE, (CharSequence[])(KeyMap.alt('\\'), KeyMap.alt('|')));
      this.keys.bind(Nano$Operation.LAST_LINE, (CharSequence[])(KeyMap.alt('/'), KeyMap.alt('?')));
      this.keys.bind(Nano$Operation.MATCHING, (CharSequence)KeyMap.alt(']'));
      this.keys.bind(Nano$Operation.SCROLL_UP, (CharSequence[])(KeyMap.alt('-'), KeyMap.alt('_')));
      this.keys.bind(Nano$Operation.SCROLL_DOWN, (CharSequence[])(KeyMap.alt('+'), KeyMap.alt('=')));
      this.keys.bind(Nano$Operation.PREV_BUFFER, (CharSequence)KeyMap.alt('<'));
      this.keys.bind(Nano$Operation.NEXT_BUFFER, (CharSequence)KeyMap.alt('>'));
      this.keys.bind(Nano$Operation.PREV_BUFFER, (CharSequence)KeyMap.alt(','));
      this.keys.bind(Nano$Operation.NEXT_BUFFER, (CharSequence)KeyMap.alt('.'));
      this.keys.bind(Nano$Operation.VERBATIM, (CharSequence)KeyMap.alt('v'));
      this.keys.bind(Nano$Operation.INSERT, (CharSequence[])(KeyMap.ctrl('I'), KeyMap.ctrl('M')));
      this.keys.bind(Nano$Operation.DELETE, (CharSequence)KeyMap.ctrl('D'));
      this.keys.bind(Nano$Operation.BACKSPACE, (CharSequence)KeyMap.ctrl('H'));
      this.keys.bind(Nano$Operation.CUT_TO_END, (CharSequence)KeyMap.alt('t'));
      this.keys.bind(Nano$Operation.JUSTIFY_FILE, (CharSequence)KeyMap.alt('j'));
      this.keys.bind(Nano$Operation.COUNT, (CharSequence)KeyMap.alt('d'));
      this.keys.bind(Nano$Operation.CLEAR_SCREEN, (CharSequence)KeyMap.ctrl('L'));
      this.keys.bind(Nano$Operation.HELP, (CharSequence)KeyMap.alt('x'));
      this.keys.bind(Nano$Operation.CONSTANT_CURSOR, (CharSequence)KeyMap.alt('c'));
      this.keys.bind(Nano$Operation.ONE_MORE_LINE, (CharSequence)KeyMap.alt('o'));
      this.keys.bind(Nano$Operation.SMOOTH_SCROLLING, (CharSequence)KeyMap.alt('s'));
      this.keys.bind(Nano$Operation.MOUSE_SUPPORT, (CharSequence)KeyMap.alt('m'));
      this.keys.bind(Nano$Operation.WHITESPACE, (CharSequence)KeyMap.alt('p'));
      this.keys.bind(Nano$Operation.HIGHLIGHT, (CharSequence)KeyMap.alt('y'));
      this.keys.bind(Nano$Operation.SMART_HOME_KEY, (CharSequence)KeyMap.alt('h'));
      this.keys.bind(Nano$Operation.AUTO_INDENT, (CharSequence)KeyMap.alt('i'));
      this.keys.bind(Nano$Operation.CUT_TO_END_TOGGLE, (CharSequence)KeyMap.alt('k'));
      this.keys.bind(Nano$Operation.TABS_TO_SPACE, (CharSequence)KeyMap.alt('q'));
      this.keys.bind(Nano$Operation.BACKUP, (CharSequence)KeyMap.alt('b'));
      this.keys.bind(Nano$Operation.NUMBERS, (CharSequence)KeyMap.alt('n'));
      this.keys.bind(Nano$Operation.UP, (CharSequence)KeyMap.key(this.terminal, InfoCmp$Capability.key_up));
      this.keys.bind(Nano$Operation.DOWN, (CharSequence)KeyMap.key(this.terminal, InfoCmp$Capability.key_down));
      this.keys.bind(Nano$Operation.RIGHT, (CharSequence)KeyMap.key(this.terminal, InfoCmp$Capability.key_right));
      this.keys.bind(Nano$Operation.LEFT, (CharSequence)KeyMap.key(this.terminal, InfoCmp$Capability.key_left));
      this.keys.bind(Nano$Operation.MOUSE_EVENT, (CharSequence)KeyMap.key(this.terminal, InfoCmp$Capability.key_mouse));
   }
}
