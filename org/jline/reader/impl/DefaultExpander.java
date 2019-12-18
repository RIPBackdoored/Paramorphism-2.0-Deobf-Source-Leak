package org.jline.reader.impl;

import java.util.ListIterator;
import org.jline.reader.Expander;
import org.jline.reader.History;
import org.jline.reader.History$Entry;

public class DefaultExpander implements Expander {
   public DefaultExpander() {
      super();
   }

   public String expandHistory(History var1, String var2) {
      boolean var3 = false;
      StringBuilder var4 = new StringBuilder();
      boolean var5 = false;
      int var6 = 0;

      for(int var7 = 0; var7 < var2.length(); ++var7) {
         char var8 = var2.charAt(var7);
         if (var6 > 0) {
            --var6;
            var5 = var6 >= 0;
            var4.append(var8);
         } else if (var5) {
            if (var8 == 'u') {
               var6 = 4;
            } else {
               var5 = false;
            }

            var4.append(var8);
         } else if (var8 == '\'') {
            var3 = !var3;
            var4.append(var8);
         } else if (var3) {
            var4.append(var8);
         } else {
            String var13;
            switch(var8) {
            case '!':
               if (var7 + 1 < var2.length()) {
                  ++var7;
                  var8 = var2.charAt(var7);
                  boolean var18 = false;
                  String var19 = null;
                  int var20;
                  int var21;
                  switch(var8) {
                  case '\t':
                  case ' ':
                     var4.append('!');
                     var4.append(var8);
                     break;
                  case '\n':
                  case '\u000b':
                  case '\f':
                  case '\r':
                  case '\u000e':
                  case '\u000f':
                  case '\u0010':
                  case '\u0011':
                  case '\u0012':
                  case '\u0013':
                  case '\u0014':
                  case '\u0015':
                  case '\u0016':
                  case '\u0017':
                  case '\u0018':
                  case '\u0019':
                  case '\u001a':
                  case '\u001b':
                  case '\u001c':
                  case '\u001d':
                  case '\u001e':
                  case '\u001f':
                  case '"':
                  case '%':
                  case '&':
                  case '\'':
                  case '(':
                  case ')':
                  case '*':
                  case '+':
                  case ',':
                  case '.':
                  case '/':
                  case ':':
                  case ';':
                  case '<':
                  case '=':
                  case '>':
                  default:
                     String var16 = var2.substring(var7);
                     var7 = var2.length();
                     var21 = this.searchBackwards(var1, var16, var1.index(), true);
                     if (var21 < 0) {
                        throw new IllegalArgumentException("!" + var16 + ": event not found");
                     }

                     var19 = var1.get(var21);
                     break;
                  case '!':
                     if (var1.size() == 0) {
                        throw new IllegalArgumentException("!!: event not found");
                     }

                     var19 = var1.get(var1.index() - 1);
                     break;
                  case '#':
                     var4.append(var4.toString());
                     break;
                  case '$':
                     if (var1.size() == 0) {
                        throw new IllegalArgumentException("!$: event not found");
                     }

                     String var14 = var1.get(var1.index() - 1).trim();
                     int var15 = var14.lastIndexOf(32);
                     if (var15 != -1) {
                        var19 = var14.substring(var15 + 1);
                     } else {
                        var19 = var14;
                     }
                     break;
                  case '-':
                     var18 = true;
                     ++var7;
                  case '0':
                  case '1':
                  case '2':
                  case '3':
                  case '4':
                  case '5':
                  case '6':
                  case '7':
                  case '8':
                  case '9':
                     for(var20 = var7; var7 < var2.length(); ++var7) {
                        var8 = var2.charAt(var7);
                        if (var8 < '0' || var8 > '9') {
                           break;
                        }
                     }

                     try {
                        var21 = Integer.parseInt(var2.substring(var20, var7));
                     } catch (NumberFormatException var17) {
                        throw new IllegalArgumentException((var18 ? "!-" : "!") + var2.substring(var20, var7) + ": event not found");
                     }

                     if (var18 && var21 > 0 && var21 <= var1.size()) {
                        var19 = var1.get(var1.index() - var21);
                     } else {
                        if (var18 || var21 <= var1.index() - var1.size() || var21 > var1.index()) {
                           throw new IllegalArgumentException((var18 ? "!-" : "!") + var2.substring(var20, var7) + ": event not found");
                        }

                        var19 = var1.get(var21 - 1);
                     }
                     break;
                  case '?':
                     var20 = var2.indexOf(63, var7 + 1);
                     if (var20 < 0) {
                        var20 = var2.length();
                     }

                     var13 = var2.substring(var7 + 1, var20);
                     var7 = var20;
                     var21 = this.searchBackwards(var1, var13, var1.index(), false);
                     if (var21 < 0) {
                        throw new IllegalArgumentException("!?" + var13 + ": event not found");
                     }

                     var19 = var1.get(var21);
                  }

                  if (var19 != null) {
                     var4.append(var19);
                  }
               } else {
                  var4.append(var8);
               }
               break;
            case '\\':
               var5 = true;
               var4.append(var8);
               break;
            case '^':
               if (var7 == 0) {
                  int var9 = var2.indexOf(94, var7 + 1);
                  int var10 = var2.indexOf(94, var9 + 1);
                  if (var10 < 0) {
                     var10 = var2.length();
                  }

                  if (var9 > 0 && var10 > 0) {
                     String var11 = var2.substring(var7 + 1, var9);
                     String var12 = var2.substring(var9 + 1, var10);
                     var13 = var1.get(var1.index() - 1).replace(var11, var12);
                     var4.append(var13);
                     var7 = var10 + 1;
                     break;
                  }
               }

               var4.append(var8);
               break;
            default:
               var4.append(var8);
            }
         }
      }

      return var4.toString();
   }

   public String expandVar(String var1) {
      return var1;
   }

   protected int searchBackwards(History var1, String var2, int var3, boolean var4) {
      ListIterator var5 = var1.iterator(var3);

      while(var5.hasPrevious()) {
         History$Entry var6 = (History$Entry)var5.previous();
         if (var4) {
            if (var6.line().startsWith(var2)) {
               return var6.index();
            }
         } else if (var6.line().contains(var2)) {
            return var6.index();
         }
      }

      return -1;
   }
}
