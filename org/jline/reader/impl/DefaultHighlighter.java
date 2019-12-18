package org.jline.reader.impl;

import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.reader.LineReader$RegionType;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.jline.utils.WCWidth;

public class DefaultHighlighter implements Highlighter {
   public DefaultHighlighter() {
      super();
   }

   public AttributedString highlight(LineReader var1, String var2) {
      int var3 = -1;
      int var4 = -1;
      int var5 = -1;
      int var6 = -1;
      String var7 = var1.getSearchTerm();
      if (var7 != null && var7.length() > 0) {
         var3 = var2.indexOf(var7);
         if (var3 >= 0) {
            var4 = var3 + var7.length() - 1;
         }
      }

      if (var1.getRegionActive() != LineReader$RegionType.NONE) {
         var5 = var1.getRegionMark();
         var6 = var1.getBuffer().cursor();
         if (var5 > var6) {
            int var8 = var6;
            var6 = var5;
            var5 = var8;
         }

         if (var1.getRegionActive() == LineReader$RegionType.LINE) {
            while(var5 > 0 && var1.getBuffer().atChar(var5 - 1) != 10) {
               --var5;
            }

            while(var6 < var1.getBuffer().length() - 1 && var1.getBuffer().atChar(var6 + 1) != 10) {
               ++var6;
            }
         }
      }

      AttributedStringBuilder var12 = new AttributedStringBuilder();

      for(int var9 = 0; var9 < var2.length(); ++var9) {
         if (var9 == var3) {
            var12.style(AttributedStyle::underline);
         }

         if (var9 == var5) {
            var12.style(AttributedStyle::inverse);
         }

         char var10 = var2.charAt(var9);
         if (var10 != '\t' && var10 != '\n') {
            if (var10 < ' ') {
               var12.style(AttributedStyle::inverseNeg).append('^').append((char)(var10 + 64)).style(AttributedStyle::inverseNeg);
            } else {
               int var11 = WCWidth.wcwidth(var10);
               if (var11 > 0) {
                  var12.append(var10);
               }
            }
         } else {
            var12.append(var10);
         }

         if (var9 == var4) {
            var12.style(AttributedStyle::underlineOff);
         }

         if (var9 == var6) {
            var12.style(AttributedStyle::inverseOff);
         }
      }

      return var12.toAttributedString();
   }
}
