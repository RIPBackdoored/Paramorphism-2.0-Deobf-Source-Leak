package org.jline.utils;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StyleResolver {
   private static final Logger log = Logger.getLogger(StyleResolver.class.getName());
   private final Function source;

   public StyleResolver(Function var1) {
      super();
      this.source = (Function)Objects.requireNonNull(var1);
   }

   private static Integer color(String var0) {
      byte var1 = 0;
      var0 = var0.toLowerCase(Locale.US);
      if (var0.charAt(0) == '!') {
         var0 = var0.substring(1, var0.length());
         var1 = 8;
      } else if (var0.startsWith("bright-")) {
         var0 = var0.substring(7, var0.length());
         var1 = 8;
      } else if (var0.charAt(0) == '~') {
         Integer var10000;
         try {
            var0 = var0.substring(1, var0.length());
            var10000 = Colors.rgbColor(var0);
         } catch (IllegalArgumentException var4) {
            log.warning("Invalid style-color name: " + var0);
            return null;
         }

         return var10000;
      }

      byte var3 = -1;
      switch(var0.hashCode()) {
      case -734239628:
         if (var0.equals("yellow")) {
            var3 = 6;
         }
         break;
      case 98:
         if (var0.equals("b")) {
            var3 = 9;
         }
         break;
      case 99:
         if (var0.equals("c")) {
            var3 = 13;
         }
         break;
      case 103:
         if (var0.equals("g")) {
            var3 = 5;
         }
         break;
      case 107:
         if (var0.equals("k")) {
            var3 = 1;
         }
         break;
      case 109:
         if (var0.equals("m")) {
            var3 = 11;
         }
         break;
      case 114:
         if (var0.equals("r")) {
            var3 = 3;
         }
         break;
      case 119:
         if (var0.equals("w")) {
            var3 = 15;
         }
         break;
      case 121:
         if (var0.equals("y")) {
            var3 = 7;
         }
         break;
      case 112785:
         if (var0.equals("red")) {
            var3 = 2;
         }
         break;
      case 3027034:
         if (var0.equals("blue")) {
            var3 = 8;
         }
         break;
      case 3068707:
         if (var0.equals("cyan")) {
            var3 = 12;
         }
         break;
      case 93818879:
         if (var0.equals("black")) {
            var3 = 0;
         }
         break;
      case 98619139:
         if (var0.equals("green")) {
            var3 = 4;
         }
         break;
      case 113101865:
         if (var0.equals("white")) {
            var3 = 14;
         }
         break;
      case 828922025:
         if (var0.equals("magenta")) {
            var3 = 10;
         }
      }

      switch(var3) {
      case 0:
      case 1:
         return var1 + 0;
      case 2:
      case 3:
         return var1 + 1;
      case 4:
      case 5:
         return var1 + 2;
      case 6:
      case 7:
         return var1 + 3;
      case 8:
      case 9:
         return var1 + 4;
      case 10:
      case 11:
         return var1 + 5;
      case 12:
      case 13:
         return var1 + 6;
      case 14:
      case 15:
         return var1 + 7;
      default:
         return null;
      }
   }

   public AttributedStyle resolve(String var1) {
      Objects.requireNonNull(var1);
      if (log.isLoggable(Level.FINEST)) {
         log.finest("Resolve: " + var1);
      }

      int var2 = var1.indexOf(":-");
      if (var2 != -1) {
         String[] var3 = var1.split(":-");
         return this.resolve(var3[0].trim(), var3[1].trim());
      } else {
         return this.apply(AttributedStyle.DEFAULT, var1);
      }
   }

   public AttributedStyle resolve(String var1, String var2) {
      Objects.requireNonNull(var1);
      if (log.isLoggable(Level.FINEST)) {
         log.finest(String.format("Resolve: %s; default: %s", var1, var2));
      }

      AttributedStyle var3 = this.apply(AttributedStyle.DEFAULT, var1);
      if (var3 == AttributedStyle.DEFAULT && var2 != null) {
         var3 = this.apply(var3, var2);
      }

      return var3;
   }

   private AttributedStyle apply(AttributedStyle var1, String var2) {
      if (log.isLoggable(Level.FINEST)) {
         log.finest("Apply: " + var2);
      }

      String[] var3 = var2.split(",");
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = var3[var5];
         var6 = var6.trim();
         if (!var6.isEmpty()) {
            if (var6.startsWith(".")) {
               var1 = this.applyReference(var1, var6);
            } else if (var6.contains(":")) {
               var1 = this.applyColor(var1, var6);
            } else if (var6.matches("[0-9]+(;[0-9]+)*")) {
               var1 = this.applyAnsi(var1, var6);
            } else {
               var1 = this.applyNamed(var1, var6);
            }
         }
      }

      return var1;
   }

   private AttributedStyle applyAnsi(AttributedStyle var1, String var2) {
      if (log.isLoggable(Level.FINEST)) {
         log.finest("Apply-ansi: " + var2);
      }

      return (new AttributedStringBuilder()).style(var1).ansiAppend("\u001b[" + var2 + "m").style();
   }

   private AttributedStyle applyReference(AttributedStyle var1, String var2) {
      if (log.isLoggable(Level.FINEST)) {
         log.finest("Apply-reference: " + var2);
      }

      if (var2.length() == 1) {
         log.warning("Invalid style-reference; missing discriminator: " + var2);
      } else {
         String var3 = var2.substring(1, var2.length());
         String var4 = (String)this.source.apply(var3);
         if (var4 != null) {
            return this.apply(var1, var4);
         }
      }

      return var1;
   }

   private AttributedStyle applyNamed(AttributedStyle var1, String var2) {
      if (log.isLoggable(Level.FINEST)) {
         log.finest("Apply-named: " + var2);
      }

      String var3 = var2.toLowerCase(Locale.US);
      byte var4 = -1;
      switch(var3.hashCode()) {
      case -1255743117:
         if (var3.equals("inverse-neg")) {
            var4 = 7;
         }
         break;
      case -1217487446:
         if (var3.equals("hidden")) {
            var4 = 12;
         }
         break;
      case -1178781136:
         if (var3.equals("italic")) {
            var4 = 3;
         }
         break;
      case -1026963764:
         if (var3.equals("underline")) {
            var4 = 4;
         }
         break;
      case -967429792:
         if (var3.equals("crossed-out")) {
            var4 = 10;
         }
         break;
      case 3029637:
         if (var3.equals("bold")) {
            var4 = 1;
         }
         break;
      case 93826908:
         if (var3.equals("blink")) {
            var4 = 5;
         }
         break;
      case 97193300:
         if (var3.equals("faint")) {
            var4 = 2;
         }
         break;
      case 384498191:
         if (var3.equals("crossedout")) {
            var4 = 11;
         }
         break;
      case 951023759:
         if (var3.equals("conceal")) {
            var4 = 9;
         }
         break;
      case 1544803905:
         if (var3.equals("default")) {
            var4 = 0;
         }
         break;
      case 1899216992:
         if (var3.equals("inverseneg")) {
            var4 = 8;
         }
         break;
      case 1959910192:
         if (var3.equals("inverse")) {
            var4 = 6;
         }
      }

      switch(var4) {
      case 0:
         return AttributedStyle.DEFAULT;
      case 1:
         return var1.bold();
      case 2:
         return var1.faint();
      case 3:
         return var1.italic();
      case 4:
         return var1.underline();
      case 5:
         return var1.blink();
      case 6:
         return var1.inverse();
      case 7:
      case 8:
         return var1.inverseNeg();
      case 9:
         return var1.conceal();
      case 10:
      case 11:
         return var1.crossedOut();
      case 12:
         return var1.hidden();
      default:
         log.warning("Unknown style: " + var2);
         return var1;
      }
   }

   private AttributedStyle applyColor(AttributedStyle var1, String var2) {
      if (log.isLoggable(Level.FINEST)) {
         log.finest("Apply-color: " + var2);
      }

      String[] var3 = var2.split(":", 2);
      String var4 = var3[0].trim();
      String var5 = var3[1].trim();
      Integer var6 = color(var5);
      if (var6 == null) {
         log.warning("Invalid color-name: " + var5);
      } else {
         String var7 = var4.toLowerCase(Locale.US);
         byte var8 = -1;
         switch(var7.hashCode()) {
         case -1332194002:
            if (var7.equals("background")) {
               var8 = 3;
            }
            break;
         case 98:
            if (var7.equals("b")) {
               var8 = 5;
            }
            break;
         case 102:
            if (var7.equals("f")) {
               var8 = 2;
            }
            break;
         case 3141:
            if (var7.equals("bg")) {
               var8 = 4;
            }
            break;
         case 3265:
            if (var7.equals("fg")) {
               var8 = 1;
            }
            break;
         case 1984457027:
            if (var7.equals("foreground")) {
               var8 = 0;
            }
         }

         switch(var8) {
         case 0:
         case 1:
         case 2:
            return var1.foreground(var6);
         case 3:
         case 4:
         case 5:
            return var1.background(var6);
         default:
            log.warning("Invalid color-mode: " + var4);
         }
      }

      return var1;
   }
}
