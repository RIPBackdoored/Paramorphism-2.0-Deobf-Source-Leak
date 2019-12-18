package org.jline.builtins;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jline.utils.AttributedCharSequence;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.jline.utils.StyleResolver;

public class Options$HelpException extends Exception {
   public static final String DEFAULT_COLORS = "ti=1;34:co=1:ar=3:op=33";

   public Options$HelpException(String var1) {
      super(var1);
   }

   public static StyleResolver defaultStyle() {
      return style("ti=1;34:co=1:ar=3:op=33");
   }

   public static StyleResolver style(String var0) {
      Map var1 = (Map)Arrays.stream(var0.split(":")).collect(Collectors.toMap(Options$HelpException::lambda$style$0, Options$HelpException::lambda$style$1));
      var1.getClass();
      return new StyleResolver(var1::get);
   }

   public static AttributedString highlight(String var0, StyleResolver var1) {
      Matcher var2 = Pattern.compile("(^|\\n)(Usage:)").matcher(var0);
      if (var2.find()) {
         AttributedStringBuilder var3 = new AttributedStringBuilder(var0.length());
         AttributedStringBuilder var4 = (new AttributedStringBuilder()).append((CharSequence)var0.substring(0, var2.start(2))).styleMatches(Pattern.compile("(?:^\\s*)([a-z]+[a-z-]*){1}\\b"), Collections.singletonList(var1.resolve(".co")));
         var3.append((AttributedCharSequence)var4);
         var3.styled((AttributedStyle)var1.resolve(".ti"), (CharSequence)"Usage").append((CharSequence)":");
         String[] var5 = var0.substring(var2.end(2)).split("\n");
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String var8 = var5[var7];
            int var9 = var8.lastIndexOf("  ");
            String var10;
            String var11;
            if (var9 > 20) {
               var10 = var8.substring(0, var9);
               var11 = var8.substring(var9 + 1);
            } else {
               var10 = var8;
               var11 = "";
            }

            AttributedStringBuilder var12 = (new AttributedStringBuilder()).append((CharSequence)var10);
            var12.styleMatches(Pattern.compile("(?:^)(?:\\s*)([a-z]+[a-z-]*){1}\\b"), Collections.singletonList(var1.resolve(".co")));
            var12.styleMatches(Pattern.compile("(?:\\[|\\s|=)([A-Za-z]+[A-Za-z_-]*){1}\\b"), Collections.singletonList(var1.resolve(".ar")));
            var12.styleMatches(Pattern.compile("(?:\\s|\\[)(-\\?|[-]{1,2}[A-Za-z-]+\\b){1}"), Collections.singletonList(var1.resolve(".op")));
            var3.append((AttributedCharSequence)var12);
            AttributedStringBuilder var13 = (new AttributedStringBuilder()).append((CharSequence)var11);
            var13.styleMatches(Pattern.compile("(?:\\s|\\[)(-\\?|[-]{1,2}[A-Za-z-]+\\b){1}"), Collections.singletonList(var1.resolve(".op")));
            var13.styleMatches(Pattern.compile("(?:\\s)([a-z]+[-]+[a-z]+|[A-Z_]{2,}){1}(?:\\s)"), Collections.singletonList(var1.resolve(".ar")));
            var3.append((AttributedCharSequence)var13);
            var3.append((CharSequence)"\n");
         }

         return var3.toAttributedString();
      } else {
         return AttributedString.fromAnsi(var0);
      }
   }

   private static String lambda$style$1(String var0) {
      return var0.substring(var0.indexOf(61) + 1);
   }

   private static String lambda$style$0(String var0) {
      return var0.substring(0, var0.indexOf(61));
   }
}
