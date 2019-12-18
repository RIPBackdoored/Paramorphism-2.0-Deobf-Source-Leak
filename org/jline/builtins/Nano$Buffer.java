package org.jline.builtins;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.mozilla.universalchardet.CharsetListener;
import org.mozilla.universalchardet.UniversalDetector;

public class Nano$Buffer {
   String file;
   Charset charset;
   Nano$WriteFormat format;
   List lines;
   int firstLineToDisplay;
   int firstColumnToDisplay;
   int offsetInLineToDisplay;
   int line;
   List offsets;
   int offsetInLine;
   int column;
   int wantedColumn;
   boolean dirty;
   final Nano this$0;

   protected Nano$Buffer(Nano var1, String var2) {
      super();
      this.this$0 = var1;
      this.format = Nano$WriteFormat.UNIX;
      this.offsets = new ArrayList();
      this.file = var2;
   }

   void open() throws IOException {
      if (this.lines == null) {
         this.lines = new ArrayList();
         this.lines.add("");
         this.charset = Charset.defaultCharset();
         this.computeAllOffsets();
         if (this.file != null) {
            Path var1 = this.this$0.root.resolve(this.file);
            if (Files.isDirectory(var1, new LinkOption[0])) {
               this.this$0.setMessage("\"" + this.file + "\" is a directory");
            } else {
               try {
                  InputStream var2 = Files.newInputStream(var1);
                  Throwable var3 = null;
                  boolean var12 = false;

                  try {
                     var12 = true;
                     this.read(var2);
                     var12 = false;
                  } catch (Throwable var15) {
                     var3 = var15;
                     throw var15;
                  } finally {
                     if (var12) {
                        if (var2 != null) {
                           if (var3 != null) {
                              try {
                                 var2.close();
                              } catch (Throwable var13) {
                                 var3.addSuppressed(var13);
                              }
                           } else {
                              var2.close();
                           }
                        }

                     }
                  }

                  if (var2 != null) {
                     if (var3 != null) {
                        try {
                           var2.close();
                        } catch (Throwable var14) {
                           var3.addSuppressed(var14);
                        }
                     } else {
                        var2.close();
                     }
                  }
               } catch (IOException var17) {
                  this.this$0.setMessage("Error reading " + this.file + ": " + var17.getMessage());
               }

            }
         }
      }
   }

   void open(InputStream var1) throws IOException {
      if (this.lines == null) {
         this.lines = new ArrayList();
         this.lines.add("");
         this.charset = Charset.defaultCharset();
         this.computeAllOffsets();
         this.read(var1);
      }
   }

   void read(InputStream var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      byte[] var3 = new byte[4096];

      int var4;
      while((var4 = var1.read(var3)) > 0) {
         var2.write(var3, 0, var4);
      }

      byte[] var5 = var2.toByteArray();

      try {
         UniversalDetector var6 = new UniversalDetector((CharsetListener)null);
         var6.handleData(var5, 0, var5.length);
         var6.dataEnd();
         if (var6.getDetectedCharset() != null) {
            this.charset = Charset.forName(var6.getDetectedCharset());
         }
      } catch (Throwable var19) {
      }

      BufferedReader var22 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(var5), this.charset));
      Throwable var7 = null;
      boolean var16 = false;

      try {
         var16 = true;
         this.lines.clear();

         String var8;
         while((var8 = var22.readLine()) != null) {
            this.lines.add(var8);
         }

         var16 = false;
      } catch (Throwable var20) {
         var7 = var20;
         throw var20;
      } finally {
         if (var16) {
            if (var22 != null) {
               if (var7 != null) {
                  try {
                     var22.close();
                  } catch (Throwable var17) {
                     var7.addSuppressed(var17);
                  }
               } else {
                  var22.close();
               }
            }

         }
      }

      if (var22 != null) {
         if (var7 != null) {
            try {
               var22.close();
            } catch (Throwable var18) {
               var7.addSuppressed(var18);
            }
         } else {
            var22.close();
         }
      }

      if (this.lines.isEmpty()) {
         this.lines.add("");
      }

      this.computeAllOffsets();
      this.moveToChar(0);
   }

   void insert(String var1) {
      String var2 = (String)this.lines.get(this.line);
      int var3 = this.offsetInLine + this.column;
      var1 = var1.replaceAll("\r\n", "\n");
      var1 = var1.replaceAll("\r", "\n");
      String var4;
      if (var3 == var2.length()) {
         var4 = var2 + var1;
      } else {
         var4 = var2.substring(0, var3) + var1 + var2.substring(var3);
      }

      ArrayList var5 = new ArrayList();
      int var6 = 0;

      for(int var7 = var4.indexOf(10, var6); var7 >= 0; var7 = var4.indexOf(10, var6)) {
         var5.add(var4.substring(var6, var7));
         var6 = var7 + 1;
      }

      var5.add(var4.substring(var6));
      this.lines.set(this.line, var5.get(0));
      this.offsets.set(this.line, this.computeOffsets((String)var5.get(0)));

      for(int var8 = 1; var8 < var5.size(); ++var8) {
         ++this.line;
         this.lines.add(this.line, var5.get(var8));
         this.offsets.add(this.line, this.computeOffsets((String)var5.get(var8)));
      }

      this.moveToChar(((String)var5.get(var5.size() - 1)).length() - (var2.length() - var3));
      this.dirty = true;
   }

   void computeAllOffsets() {
      this.offsets.clear();
      Iterator var1 = this.lines.iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         this.offsets.add(this.computeOffsets(var2));
      }

   }

   LinkedList computeOffsets(String var1) {
      int var2 = this.this$0.size.getColumns() - (this.this$0.printLineNumbers ? 8 : 0);
      LinkedList var3 = new LinkedList();
      var3.add(0);
      int var4 = 0;
      int var5 = 0;
      boolean var6 = false;

      for(int var7 = 0; var7 < var1.length(); ++var7) {
         if (this.isBreakable(var1.charAt(var7))) {
            var6 = true;
         } else if (var6) {
            var5 = var7;
            var6 = false;
         }

         if (var7 == var4 + var2 - 1) {
            if (var5 == var4) {
               var5 = var7;
            }

            var3.add(var5);
            var4 = var5;
         }
      }

      return var3;
   }

   boolean isBreakable(char var1) {
      return var1 == ' ';
   }

   void moveToChar(int var1) {
      this.offsetInLine = (Integer)this.prevLineOffset(this.line, var1 + 1).get();
      this.column = var1 - this.offsetInLine;
   }

   void delete(int var1) {
      do {
         --var1;
      } while(var1 >= 0 && this.moveRight(1) && this.backspace(1));

   }

   boolean backspace(int var1) {
      while(var1 > 0) {
         String var2 = (String)this.lines.get(this.line);
         int var3 = this.offsetInLine + this.column;
         if (var3 == 0) {
            if (this.line == 0) {
               this.bof();
               return false;
            }

            String var5 = (String)this.lines.get(--this.line);
            this.lines.set(this.line, var5 + var2);
            this.offsets.set(this.line, this.computeOffsets(var5 + var2));
            this.moveToChar(this.length(var5, this.this$0.tabs));
            this.lines.remove(this.line + 1);
            this.offsets.remove(this.line + 1);
            --var1;
            this.dirty = true;
         } else {
            int var4 = Math.min(var3, var1);
            var2 = var2.substring(0, var3 - var4) + var2.substring(var3);
            this.lines.set(this.line, var2);
            this.offsets.set(this.line, this.computeOffsets(var2));
            this.moveToChar(this.offsetInLine + this.column - var4);
            var1 -= var4;
            this.dirty = true;
         }
      }

      return true;
   }

   boolean moveLeft(int var1) {
      boolean var2 = true;

      while(true) {
         --var1;
         if (var1 < 0) {
            break;
         }

         if (this.offsetInLine + this.column > 0) {
            this.moveToChar(this.offsetInLine + this.column - 1);
         } else {
            if (this.line <= 0) {
               this.bof();
               var2 = false;
               break;
            }

            --this.line;
            this.moveToChar(this.length(this.getLine(this.line), this.this$0.tabs));
         }
      }

      this.wantedColumn = this.column;
      this.ensureCursorVisible();
      return var2;
   }

   boolean moveRight(int var1) {
      boolean var2 = true;

      while(true) {
         --var1;
         if (var1 < 0) {
            break;
         }

         int var3 = this.length(this.getLine(this.line), this.this$0.tabs);
         if (this.offsetInLine + this.column + 1 <= var3) {
            this.moveToChar(this.offsetInLine + this.column + 1);
         } else {
            if (this.getLine(this.line + 1) == null) {
               this.eof();
               var2 = false;
               break;
            }

            ++this.line;
            this.offsetInLine = 0;
            this.column = 0;
         }
      }

      this.wantedColumn = this.column;
      this.ensureCursorVisible();
      return var2;
   }

   void moveDown(int var1) {
      this.cursorDown(var1);
      this.ensureCursorVisible();
   }

   void moveUp(int var1) {
      this.cursorUp(var1);
      this.ensureCursorVisible();
   }

   private Optional prevLineOffset(int var1, int var2) {
      if (var1 >= this.offsets.size()) {
         return Optional.empty();
      } else {
         Iterator var3 = ((LinkedList)this.offsets.get(var1)).descendingIterator();

         int var4;
         do {
            if (!var3.hasNext()) {
               return Optional.empty();
            }

            var4 = (Integer)var3.next();
         } while(var4 >= var2);

         return Optional.of(var4);
      }
   }

   private Optional nextLineOffset(int var1, int var2) {
      return var1 >= this.offsets.size() ? Optional.empty() : ((LinkedList)this.offsets.get(var1)).stream().filter(Nano$Buffer::lambda$nextLineOffset$0).findFirst();
   }

   void moveDisplayDown(int var1) {
      int var2 = this.this$0.size.getRows() - this.computeHeader().size() - this.this$0.computeFooter().size();

      while(true) {
         --var1;
         if (var1 < 0) {
            return;
         }

         int var3 = this.firstLineToDisplay;
         if (this.firstColumnToDisplay <= 0 && this.this$0.wrapping) {
            int var4 = this.offsetInLineToDisplay;

            for(int var5 = 0; var5 < var2 - 1; ++var5) {
               Optional var6 = this.nextLineOffset(var3, var4);
               if (var6.isPresent()) {
                  var4 = (Integer)var6.get();
               } else {
                  var4 = 0;
                  ++var3;
               }
            }
         } else {
            var3 += var2 - 1;
         }

         if (this.getLine(var3) == null) {
            this.eof();
            return;
         }

         Optional var7 = this.nextLineOffset(this.firstLineToDisplay, this.offsetInLineToDisplay);
         if (var7.isPresent()) {
            this.offsetInLineToDisplay = (Integer)var7.get();
         } else {
            this.offsetInLineToDisplay = 0;
            ++this.firstLineToDisplay;
         }
      }
   }

   void moveDisplayUp(int var1) {
      int var2 = this.this$0.size.getColumns() - (this.this$0.printLineNumbers ? 8 : 0);

      while(true) {
         --var1;
         if (var1 < 0) {
            return;
         }

         if (this.offsetInLineToDisplay > 0) {
            this.offsetInLineToDisplay = Math.max(0, this.offsetInLineToDisplay - (var2 - 1));
         } else {
            if (this.firstLineToDisplay <= 0) {
               this.bof();
               return;
            }

            --this.firstLineToDisplay;
            this.offsetInLineToDisplay = (Integer)this.prevLineOffset(this.firstLineToDisplay, 0).get();
         }
      }
   }

   private void cursorDown(int var1) {
      while(true) {
         String var2;
         label31: {
            while(true) {
               --var1;
               if (var1 < 0) {
                  break;
               }

               if (this.firstColumnToDisplay <= 0 && this.this$0.wrapping) {
                  var2 = this.getLine(this.line);
                  Optional var3 = this.nextLineOffset(this.line, this.offsetInLine);
                  if (var3.isPresent()) {
                     this.offsetInLine = (Integer)var3.get();
                     break label31;
                  }

                  if (this.getLine(this.line + 1) != null) {
                     ++this.line;
                     this.offsetInLine = 0;
                     var2 = this.getLine(this.line);
                     break label31;
                  }

                  this.eof();
                  break;
               }

               if (this.getLine(this.line + 1) == null) {
                  this.bof();
                  break;
               }

               ++this.line;
               this.offsetInLine = 0;
               this.column = Math.min(this.getLine(this.line).length(), this.wantedColumn);
            }

            return;
         }

         Optional var10000 = this.nextLineOffset(this.line, this.offsetInLine);
         var2.getClass();
         int var5 = (Integer)var10000.orElseGet(var2::length);
         this.column = Math.min(this.wantedColumn, var5 - this.offsetInLine);
      }
   }

   private void cursorUp(int var1) {
      while(true) {
         --var1;
         if (var1 >= 0) {
            if (this.firstColumnToDisplay <= 0 && this.this$0.wrapping) {
               Optional var2 = this.prevLineOffset(this.line, this.offsetInLine);
               if (var2.isPresent()) {
                  this.offsetInLine = (Integer)var2.get();
                  continue;
               }

               if (this.line > 0) {
                  --this.line;
                  this.offsetInLine = (Integer)this.prevLineOffset(this.line, 0).get();
                  int var3 = (Integer)this.nextLineOffset(this.line, this.offsetInLine).orElse(this.getLine(this.line).length());
                  this.column = Math.min(this.wantedColumn, var3 - this.offsetInLine);
                  continue;
               }

               this.bof();
            } else {
               if (this.line > 0) {
                  --this.line;
                  this.column = Math.min(this.length(this.getLine(this.line), this.this$0.tabs) - this.offsetInLine, this.wantedColumn);
                  continue;
               }

               this.bof();
            }
         }

         return;
      }
   }

   void ensureCursorVisible() {
      List var1 = this.computeHeader();
      int var2 = this.this$0.size.getColumns();
      int var3 = this.this$0.size.getRows() - var1.size() - this.this$0.computeFooter().size();

      while(this.line < this.firstLineToDisplay || this.line == this.firstLineToDisplay && this.offsetInLine < this.offsetInLineToDisplay) {
         this.moveDisplayUp(this.this$0.smoothScrolling ? 1 : var3 / 2);
      }

      while(true) {
         int var4 = var1.size() * this.this$0.size.getColumns() + (this.this$0.printLineNumbers ? 8 : 0);
         int var5 = this.firstLineToDisplay;
         int var6 = this.offsetInLineToDisplay;

         while(true) {
            while(var5 < this.line || var6 < this.offsetInLine) {
               if (this.firstColumnToDisplay <= 0 && this.this$0.wrapping) {
                  var4 += var2;
                  Optional var7 = this.nextLineOffset(var5, var6);
                  if (var7.isPresent()) {
                     var6 = (Integer)var7.get();
                  } else {
                     ++var5;
                     var6 = 0;
                  }
               } else {
                  var4 += var2;
                  ++var5;
               }
            }

            if (var5 != this.line) {
               throw new IllegalStateException();
            }

            var4 += this.column;
            if (var4 < (var3 + var1.size()) * var2) {
               return;
            }

            this.moveDisplayDown(this.this$0.smoothScrolling ? 1 : var3 / 2);
            break;
         }
      }
   }

   void eof() {
   }

   void bof() {
   }

   void resetDisplay() {
      int var1 = this.this$0.size.getColumns() - (this.this$0.printLineNumbers ? 8 : 0);
      this.column += this.offsetInLine;
      this.offsetInLine = this.column / var1 * (var1 - 1);
      this.column -= this.offsetInLine;
   }

   String getLine(int var1) {
      return var1 < this.lines.size() ? (String)this.lines.get(var1) : null;
   }

   String getTitle() {
      return this.file != null ? "File: " + this.file : "New Buffer";
   }

   List computeHeader() {
      String var1 = this.this$0.getTitle();
      String var2 = null;
      String var3 = this.dirty ? "Modified" : "        ";
      int var4 = this.this$0.size.getColumns();
      int var5 = 2 + var1.length() + 1;
      int var6 = var4 - 2 - 8;
      int var7;
      int var9;
      if (this.file == null) {
         var2 = "New Buffer";
      } else {
         var7 = var6 - var5;
         String var8 = this.file;
         if ("File: ".length() + var8.length() > var7) {
            var9 = var8.lastIndexOf(47);
            if (var9 > 0) {
               String var10 = var8.substring(var9);

               String var11;
               for(var11 = var8.substring(0, var9); var11.startsWith("."); var11 = var11.substring(1)) {
               }

               int var12 = var7 - var10.length() - "File: ...".length();
               int var13 = Math.max(0, Math.min(var11.length(), var11.length() - var12));
               var2 = "File: ..." + var11.substring(var13, var11.length()) + var10;
            }

            if (var2 == null || var2.length() > var7) {
               var1 = null;
               var7 = var6 - 2;
               int var16 = var7 - "File: ...".length();
               int var17 = Math.max(0, Math.min(var8.length(), var8.length() - var16));
               var2 = "File: ..." + var8.substring(var17, var8.length());
               if (var2.length() > var7) {
                  var2 = var2.substring(0, var7);
               }
            }
         } else {
            var2 = "File: " + var8;
         }
      }

      byte var14 = 0;
      AttributedStringBuilder var15 = new AttributedStringBuilder();
      var15.style(AttributedStyle.INVERSE);
      var15.append((CharSequence)"  ");
      var7 = var14 + 2;
      if (var1 != null) {
         var15.append((CharSequence)var1);
         var7 += var1.length();
         var15.append((CharSequence)" ");
         ++var7;

         for(var9 = 1; var9 < (this.this$0.size.getColumns() - var2.length()) / 2 - var1.length() - 1 - 2; ++var9) {
            var15.append((CharSequence)" ");
            ++var7;
         }
      }

      var15.append((CharSequence)var2);

      for(var7 += var2.length(); var7 < var4 - 8 - 2; ++var7) {
         var15.append((CharSequence)" ");
      }

      var15.append((CharSequence)var3);
      var15.append((CharSequence)"  \n");
      return this.this$0.oneMoreLine ? Collections.singletonList(var15.toAttributedString()) : Arrays.asList(var15.toAttributedString(), new AttributedString("\n"));
   }

   List getDisplayedLines(int var1) {
      AttributedStyle var2 = AttributedStyle.DEFAULT.foreground(8);
      AttributedString var3 = new AttributedString("…", var2);
      AttributedString var4 = new AttributedString("↩", var2);
      ArrayList var5 = new ArrayList();
      int var6 = this.this$0.size.getColumns();
      int var7 = var6 - (this.this$0.printLineNumbers ? 8 : 0);
      int var8 = this.firstLineToDisplay;
      int var9 = this.offsetInLineToDisplay;
      int var10 = -1;

      for(int var11 = 0; var11 < var1; ++var11) {
         AttributedStringBuilder var12 = (new AttributedStringBuilder()).tabs(this.this$0.tabs);
         if (this.this$0.printLineNumbers && var8 < this.lines.size()) {
            var12.style(var2);
            if (var8 != var10) {
               var12.append((CharSequence)String.format("%7d ", var8 + 1));
            } else {
               var12.append((CharSequence)"      ‧ ");
            }

            var12.style(AttributedStyle.DEFAULT);
            var10 = var8;
         }

         if (var8 < this.lines.size()) {
            if (this.firstColumnToDisplay <= 0 && this.this$0.wrapping) {
               Optional var15 = this.nextLineOffset(var8, var9);
               AttributedString var14;
               if (var15.isPresent()) {
                  var14 = new AttributedString(this.getLine(var8));
                  var12.append(var14.columnSubSequence(var9, (Integer)var15.get()));
                  var12.append(var4);
                  var9 = (Integer)var15.get();
               } else {
                  var14 = new AttributedString(this.getLine(var8));
                  var12.append(var14.columnSubSequence(var9, 0));
                  ++var8;
                  var9 = 0;
               }
            } else {
               AttributedString var13 = new AttributedString(this.getLine(var8));
               var13 = var13.columnSubSequence(this.firstColumnToDisplay, 0);
               if (var13.columnLength() >= var7) {
                  var12.append(var13.columnSubSequence(0, var7 - var3.columnLength()));
                  var12.append(var3);
               } else {
                  var12.append(var13);
               }

               ++var8;
            }
         }

         var12.append('\n');
         var5.add(var12.toAttributedString());
      }

      return var5;
   }

   public void moveTo(int var1, int var2) {
      if (this.this$0.printLineNumbers) {
         var1 = Math.max(var1 - 8, 0);
      }

      this.line = this.firstLineToDisplay;
      this.offsetInLine = this.offsetInLineToDisplay;
      this.wantedColumn = var1;
      this.cursorDown(var2);
   }

   public int getDisplayedCursor() {
      int var1 = this.this$0.size.getColumns() + 1;
      int var2 = this.this$0.printLineNumbers ? 8 : 0;
      int var3 = this.firstLineToDisplay;
      int var4 = this.offsetInLineToDisplay;

      while(true) {
         while(var3 < this.line || var4 < this.offsetInLine) {
            if (this.firstColumnToDisplay <= 0 && this.this$0.wrapping) {
               var2 += var1;
               Optional var5 = this.nextLineOffset(var3, var4);
               if (var5.isPresent()) {
                  var4 = (Integer)var5.get();
               } else {
                  ++var3;
                  var4 = 0;
               }
            } else {
               var2 += var1;
               ++var3;
            }
         }

         if (var3 == this.line) {
            var2 += this.column;
            return var2;
         }

         throw new IllegalStateException();
      }
   }

   char getCurrentChar() {
      String var1 = (String)this.lines.get(this.line);
      if (this.column + this.offsetInLine < var1.length()) {
         return var1.charAt(this.column + this.offsetInLine);
      } else {
         return (char)(this.line < this.lines.size() - 1 ? '\n' : '\u0000');
      }
   }

   public void prevWord() {
      while(Character.isAlphabetic(this.getCurrentChar()) && this.moveLeft(1)) {
      }

      while(!Character.isAlphabetic(this.getCurrentChar()) && this.moveLeft(1)) {
      }

      while(Character.isAlphabetic(this.getCurrentChar()) && this.moveLeft(1)) {
      }

      this.moveRight(1);
   }

   public void nextWord() {
      while(Character.isAlphabetic(this.getCurrentChar()) && this.moveRight(1)) {
      }

      while(!Character.isAlphabetic(this.getCurrentChar()) && this.moveRight(1)) {
      }

   }

   public void beginningOfLine() {
      this.column = this.offsetInLine = 0;
      this.wantedColumn = 0;
   }

   public void endOfLine() {
      this.column = this.length((String)this.lines.get(this.line), this.this$0.tabs);
      int var1 = this.this$0.size.getColumns() - (this.this$0.printLineNumbers ? 8 : 0);
      this.offsetInLine = this.column / var1 * (var1 - 1);
      this.column -= this.offsetInLine;
      this.wantedColumn = this.column;
   }

   public void prevPage() {
      int var1 = this.this$0.size.getRows() - this.computeHeader().size() - this.this$0.computeFooter().size();
      this.scrollUp(var1 - 2);
   }

   public void nextPage() {
      int var1 = this.this$0.size.getRows() - this.computeHeader().size() - this.this$0.computeFooter().size();
      this.scrollDown(var1 - 2);
   }

   public void scrollUp(int var1) {
      this.cursorUp(var1);
      this.moveDisplayUp(var1);
   }

   public void scrollDown(int var1) {
      this.cursorDown(var1);
      this.moveDisplayDown(var1);
   }

   public void firstLine() {
      this.line = 0;
      this.offsetInLine = this.column = 0;
      this.ensureCursorVisible();
   }

   public void lastLine() {
      this.line = this.lines.size() - 1;
      this.offsetInLine = this.column = 0;
      this.ensureCursorVisible();
   }

   void nextSearch() {
      if (this.this$0.searchTerm == null) {
         this.this$0.setMessage("No current search pattern");
      } else {
         this.this$0.setMessage((String)null);
         int var1 = this.line;
         int var2 = this.this$0.searchBackwards ? -1 : 1;
         int var3 = -1;
         int var4 = -1;
         List var5 = this.doSearch((String)this.lines.get(this.line));
         if (this.this$0.searchBackwards) {
            Collections.reverse(var5);
         }

         Iterator var6 = var5.iterator();

         while(var6.hasNext()) {
            int var7 = (Integer)var6.next();
            if (this.this$0.searchBackwards) {
               if (var7 >= this.offsetInLine + this.column) {
                  continue;
               }
            } else if (var7 <= this.offsetInLine + this.column) {
               continue;
            }

            var3 = var7;
            var4 = this.line;
            break;
         }

         if (var3 < 0) {
            while(true) {
               var1 = (var1 + var2 + this.lines.size()) % this.lines.size();
               if (var1 == this.line) {
                  break;
               }

               List var8 = this.doSearch((String)this.lines.get(var1));
               if (!var8.isEmpty()) {
                  var3 = this.this$0.searchBackwards ? (Integer)var8.get(var8.size() - 1) : (Integer)var8.get(0);
                  var4 = var1;
                  break;
               }
            }
         }

         if (var3 < 0 && !var5.isEmpty()) {
            var3 = (Integer)var5.get(0);
            var4 = this.line;
         }

         if (var3 >= 0) {
            if (var4 == this.line && var3 == this.offsetInLine + this.column) {
               this.this$0.setMessage("This is the only occurence");
               return;
            }

            if (this.this$0.searchBackwards && (var4 > this.line || var4 == this.line && var3 > this.offsetInLine + this.column) || !this.this$0.searchBackwards && (var4 < this.line || var4 == this.line && var3 < this.offsetInLine + this.column)) {
               this.this$0.setMessage("Search Wrapped");
            }

            int var9 = this.this$0.size.getColumns() - (this.this$0.printLineNumbers ? 8 : 0);
            this.line = var4;
            this.column = var3;
            this.offsetInLine = this.column / var9 * (var9 - 1);
            this.ensureCursorVisible();
         } else {
            this.this$0.setMessage("\"" + this.this$0.searchTerm + "\" not found");
         }

      }
   }

   private List doSearch(String var1) {
      Pattern var2 = Pattern.compile(this.this$0.searchTerm, (this.this$0.searchCaseSensitive ? 0 : 66) | (this.this$0.searchRegexp ? 0 : 16));
      Matcher var3 = var2.matcher(var1);
      ArrayList var4 = new ArrayList();

      while(var3.find()) {
         var4.add(var3.start());
      }

      return var4;
   }

   public void matching() {
      char var1 = this.getCurrentChar();
      int var2 = this.this$0.matchBrackets.indexOf(var1);
      if (var2 < 0) {
         this.this$0.setMessage("Not a bracket");
      } else {
         int var3 = var2 >= this.this$0.matchBrackets.length() / 2 ? -1 : 1;
         char var4 = this.this$0.matchBrackets.charAt((var2 + this.this$0.matchBrackets.length() / 2) % this.this$0.matchBrackets.length());
         int var5 = 1;
         int var6 = this.line;
         int var7 = this.offsetInLine + this.column;

         while(true) {
            do {
               if (var7 + var3 >= 0 && var7 + var3 < this.getLine(var6).length()) {
                  var7 += var3;
                  break;
               }

               if (var6 + var3 < 0 || var6 + var3 >= this.lines.size()) {
                  this.this$0.setMessage("No matching bracket");
                  return;
               }

               var6 += var3;
               var7 = var3 > 0 ? 0 : ((String)this.lines.get(var6)).length() - 1;
            } while(var7 < 0 || var7 >= ((String)this.lines.get(var6)).length());

            char var8 = ((String)this.lines.get(var6)).charAt(var7);
            if (var8 == var1) {
               ++var5;
            } else if (var8 == var4) {
               --var5;
               if (var5 == 0) {
                  this.line = var6;
                  this.moveToChar(var7);
                  this.ensureCursorVisible();
                  return;
               }
            }
         }
      }
   }

   private int length(String var1, int var2) {
      return (new AttributedStringBuilder()).tabs(var2).append((CharSequence)var1).columnLength();
   }

   private static boolean lambda$nextLineOffset$0(int var0, Integer var1) {
      return var1 > var0;
   }
}
