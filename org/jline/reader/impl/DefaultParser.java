package org.jline.reader.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import org.jline.reader.EOFError;
import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.Parser$ParseContext;

public class DefaultParser implements Parser {
   private char[] quoteChars = new char[]{'\'', '"'};
   private char[] escapeChars = new char[]{'\\'};
   private boolean eofOnUnclosedQuote;
   private boolean eofOnEscapedNewLine;
   private char[] openingBrackets = null;
   private char[] closingBrackets = null;

   public DefaultParser() {
      super();
   }

   public DefaultParser quoteChars(char[] var1) {
      this.quoteChars = var1;
      return this;
   }

   public DefaultParser escapeChars(char[] var1) {
      this.escapeChars = var1;
      return this;
   }

   public DefaultParser eofOnUnclosedQuote(boolean var1) {
      this.eofOnUnclosedQuote = var1;
      return this;
   }

   public DefaultParser eofOnUnclosedBracket(DefaultParser$Bracket... var1) {
      this.setEofOnUnclosedBracket(var1);
      return this;
   }

   public DefaultParser eofOnEscapedNewLine(boolean var1) {
      this.eofOnEscapedNewLine = var1;
      return this;
   }

   public void setQuoteChars(char[] var1) {
      this.quoteChars = var1;
   }

   public char[] getQuoteChars() {
      return this.quoteChars;
   }

   public void setEscapeChars(char[] var1) {
      this.escapeChars = var1;
   }

   public char[] getEscapeChars() {
      return this.escapeChars;
   }

   public void setEofOnUnclosedQuote(boolean var1) {
      this.eofOnUnclosedQuote = var1;
   }

   public boolean isEofOnUnclosedQuote() {
      return this.eofOnUnclosedQuote;
   }

   public void setEofOnEscapedNewLine(boolean var1) {
      this.eofOnEscapedNewLine = var1;
   }

   public boolean isEofOnEscapedNewLine() {
      return this.eofOnEscapedNewLine;
   }

   public void setEofOnUnclosedBracket(DefaultParser$Bracket... var1) {
      // $FF: Couldn't be decompiled
   }

   public ParsedLine parse(String var1, int var2, Parser$ParseContext var3) {
      LinkedList var4 = new LinkedList();
      StringBuilder var5 = new StringBuilder();
      int var6 = -1;
      int var7 = -1;
      int var8 = -1;
      int var9 = -1;
      int var10 = -1;
      int var11 = 0;
      DefaultParser$BracketChecker var12 = new DefaultParser$BracketChecker(this);
      boolean var13 = false;

      for(int var14 = 0; var1 != null && var14 < var1.length(); ++var14) {
         if (var14 == var2) {
            var7 = var4.size();
            var6 = var5.length();
            var9 = var14 - var11;
         }

         if (var8 < 0 && this.isQuoteChar(var1, var14)) {
            var8 = var14;
            if (var5.length() == 0) {
               var13 = true;
            } else {
               var5.append(var1.charAt(var14));
            }
         } else if (var8 >= 0 && var1.charAt(var8) == var1.charAt(var14) && !this.isEscaped(var1, var14)) {
            if (!var13) {
               var5.append(var1.charAt(var14));
            } else if (var9 >= 0 && var10 < 0) {
               var10 = var14 - var11 + 1;
            }

            var8 = -1;
            var13 = false;
         } else if (var8 < 0 && this.isDelimiter(var1, var14)) {
            if (var5.length() > 0) {
               var4.add(var5.toString());
               var5.setLength(0);
               if (var9 >= 0 && var10 < 0) {
                  var10 = var14 - var11;
               }
            }

            var11 = var14 + 1;
         } else if (!this.isEscapeChar(var1, var14)) {
            var5.append(var1.charAt(var14));
            if (var8 < 0) {
               var12.check(var1, var14);
            }
         }
      }

      if (var5.length() > 0 || var2 == var1.length()) {
         var4.add(var5.toString());
         if (var9 >= 0 && var10 < 0) {
            var10 = var1.length() - var11;
         }
      }

      if (var2 == var1.length()) {
         var7 = var4.size() - 1;
         var6 = ((String)var4.get(var4.size() - 1)).length();
         var9 = var2 - var11;
         var10 = var9;
      }

      if (var3 != Parser$ParseContext.COMPLETE) {
         if (this.eofOnEscapedNewLine && this.isEscapeChar(var1, var1.length() - 1)) {
            throw new EOFError(-1, -1, "Escaped new line", "newline");
         }

         if (this.eofOnUnclosedQuote && var8 >= 0) {
            throw new EOFError(-1, -1, "Missing closing quote", var1.charAt(var8) == '\'' ? "quote" : "dquote");
         }

         if (var12.isOpeningBracketMissing()) {
            throw new EOFError(-1, -1, "Missing opening bracket", "missing: " + var12.getMissingOpeningBracket());
         }

         if (var12.isClosingBracketMissing()) {
            throw new EOFError(-1, -1, "Missing closing brackets", "add: " + var12.getMissingClosingBrackets());
         }
      }

      String var15 = var13 ? var1.substring(var8, var8 + 1) : null;
      return new DefaultParser$ArgumentList(this, var1, var4, var7, var6, var2, var15, var9, var10);
   }

   public boolean isDelimiter(CharSequence var1, int var2) {
      return !this.isQuoted(var1, var2) && !this.isEscaped(var1, var2) && this.isDelimiterChar(var1, var2);
   }

   public boolean isQuoted(CharSequence var1, int var2) {
      return false;
   }

   public boolean isQuoteChar(CharSequence var1, int var2) {
      if (var2 < 0) {
         return false;
      } else {
         if (this.quoteChars != null) {
            char[] var3 = this.quoteChars;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               char var6 = var3[var5];
               if (var6 == var1.charAt(var2)) {
                  return !this.isEscaped(var1, var2);
               }
            }
         }

         return false;
      }
   }

   public boolean isEscapeChar(char var1) {
      if (this.escapeChars != null) {
         char[] var2 = this.escapeChars;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            char var5 = var2[var4];
            if (var5 == var1) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean isEscapeChar(CharSequence var1, int var2) {
      if (var2 < 0) {
         return false;
      } else {
         char var3 = var1.charAt(var2);
         return this.isEscapeChar(var3) && !this.isEscaped(var1, var2);
      }
   }

   public boolean isEscaped(CharSequence var1, int var2) {
      return var2 <= 0 ? false : this.isEscapeChar(var1, var2 - 1);
   }

   public boolean isDelimiterChar(CharSequence var1, int var2) {
      return Character.isWhitespace(var1.charAt(var2));
   }

   private boolean isRawEscapeChar(char var1) {
      if (this.escapeChars != null) {
         char[] var2 = this.escapeChars;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            char var5 = var2[var4];
            if (var5 == var1) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isRawQuoteChar(char var1) {
      if (this.quoteChars != null) {
         char[] var2 = this.quoteChars;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            char var5 = var2[var4];
            if (var5 == var1) {
               return true;
            }
         }
      }

      return false;
   }

   static char[] access$000(DefaultParser var0) {
      return var0.openingBrackets;
   }

   static char[] access$100(DefaultParser var0) {
      return var0.closingBrackets;
   }

   static char[] access$200(DefaultParser var0) {
      return var0.escapeChars;
   }

   static boolean access$300(DefaultParser var0, char var1) {
      return var0.isRawEscapeChar(var1);
   }

   static boolean access$400(DefaultParser var0, char var1) {
      return var0.isRawQuoteChar(var1);
   }
}
