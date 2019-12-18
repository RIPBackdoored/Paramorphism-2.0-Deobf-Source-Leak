package org.jline.builtins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import org.jline.reader.LineReader;
import org.jline.reader.LineReader$Option;
import org.jline.reader.Macro;
import org.jline.reader.Reference;
import org.jline.utils.Log;

public final class InputRC {
   private final LineReader reader;

   public static void configure(LineReader var0, URL var1) throws IOException {
      InputStream var2 = var1.openStream();
      Throwable var3 = null;
      boolean var11 = false;

      try {
         var11 = true;
         configure(var0, var2);
         var11 = false;
      } catch (Throwable var14) {
         var3 = var14;
         throw var14;
      } finally {
         if (var11) {
            if (var2 != null) {
               if (var3 != null) {
                  try {
                     var2.close();
                  } catch (Throwable var12) {
                     var3.addSuppressed(var12);
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
            } catch (Throwable var13) {
               var3.addSuppressed(var13);
            }
         } else {
            var2.close();
         }
      }

   }

   public static void configure(LineReader var0, InputStream var1) throws IOException {
      InputStreamReader var2 = new InputStreamReader(var1);
      Throwable var3 = null;
      boolean var11 = false;

      try {
         var11 = true;
         configure(var0, (Reader)var2);
         var11 = false;
      } catch (Throwable var14) {
         var3 = var14;
         throw var14;
      } finally {
         if (var11) {
            if (var2 != null) {
               if (var3 != null) {
                  try {
                     var2.close();
                  } catch (Throwable var12) {
                     var3.addSuppressed(var12);
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
            } catch (Throwable var13) {
               var3.addSuppressed(var13);
            }
         } else {
            var2.close();
         }
      }

   }

   public static void configure(LineReader var0, Reader var1) throws IOException {
      BufferedReader var2;
      if (var1 instanceof BufferedReader) {
         var2 = (BufferedReader)var1;
      } else {
         var2 = new BufferedReader(var1);
      }

      var0.getVariables().putIfAbsent("editing-mode", "emacs");
      var0.setKeyMap("main");
      if ("vi".equals(var0.getVariable("editing-mode"))) {
         var0.getKeyMaps().put("main", var0.getKeyMaps().get("viins"));
      } else if ("emacs".equals(var0.getVariable("editing-mode"))) {
         var0.getKeyMaps().put("main", var0.getKeyMaps().get("emacs"));
      }

      (new InputRC(var0)).parse(var2);
      if ("vi".equals(var0.getVariable("editing-mode"))) {
         var0.getKeyMaps().put("main", var0.getKeyMaps().get("viins"));
      } else if ("emacs".equals(var0.getVariable("editing-mode"))) {
         var0.getKeyMaps().put("main", var0.getKeyMaps().get("emacs"));
      }

   }

   private InputRC(LineReader var1) {
      super();
      this.reader = var1;
   }

   private void parse(BufferedReader var1) throws IOException, IllegalArgumentException {
      boolean var3 = true;
      ArrayList var4 = new ArrayList();

      String var2;
      while((var2 = var1.readLine()) != null) {
         try {
            var2 = var2.trim();
            if (var2.length() != 0 && var2.charAt(0) != '#') {
               byte var5 = 0;
               int var8;
               int var13;
               String var14;
               String var17;
               if (var2.charAt(var5) == '$') {
                  for(var13 = var5 + 1; var13 < var2.length() && (var2.charAt(var13) == ' ' || var2.charAt(var13) == '\t'); ++var13) {
                  }

                  for(var8 = var13; var13 < var2.length() && var2.charAt(var13) != ' ' && var2.charAt(var13) != '\t'; ++var13) {
                  }

                  for(var14 = var2.substring(var8, var13); var13 < var2.length() && (var2.charAt(var13) == ' ' || var2.charAt(var13) == '\t'); ++var13) {
                  }

                  for(var8 = var13; var13 < var2.length() && var2.charAt(var13) != ' ' && var2.charAt(var13) != '\t'; ++var13) {
                  }

                  String var15 = var2.substring(var8, var13);
                  if ("if".equalsIgnoreCase(var14)) {
                     var4.add(var3);
                     if (var3 && !var15.startsWith("term=")) {
                        if (var15.startsWith("mode=")) {
                           var17 = (String)this.reader.getVariable("editing-mode");
                           var3 = var15.substring("mode=".length()).equalsIgnoreCase(var17);
                        } else {
                           var3 = var15.equalsIgnoreCase(this.reader.getAppName());
                        }
                     }
                  } else if (!"else".equalsIgnoreCase(var14)) {
                     if ("endif".equalsIgnoreCase(var14)) {
                        if (var4.isEmpty()) {
                           throw new IllegalArgumentException("endif found without matching $if");
                        }

                        var3 = (Boolean)var4.remove(var4.size() - 1);
                     } else if ("include".equalsIgnoreCase(var14)) {
                     }
                  } else {
                     if (var4.isEmpty()) {
                        throw new IllegalArgumentException("$else found without matching $if");
                     }

                     boolean var18 = true;
                     Iterator var22 = var4.iterator();

                     while(var22.hasNext()) {
                        boolean var21 = (Boolean)var22.next();
                        if (!var21) {
                           var18 = false;
                           break;
                        }
                     }

                     if (var18) {
                        var3 = !var3;
                     }
                  }
               } else if (var3) {
                  var13 = var5 + 1;
                  if (var2.charAt(var5) == '"') {
                     boolean var6 = false;

                     while(true) {
                        if (var13 >= var2.length()) {
                           throw new IllegalArgumentException("Missing closing quote on line '" + var2 + "'");
                        }

                        if (var6) {
                           var6 = false;
                        } else if (var2.charAt(var13) == '\\') {
                           var6 = true;
                        } else if (var2.charAt(var13) == '"') {
                           break;
                        }

                        ++var13;
                     }
                  }

                  while(var13 < var2.length() && var2.charAt(var13) != ':' && var2.charAt(var13) != ' ' && var2.charAt(var13) != '\t') {
                     ++var13;
                  }

                  var14 = var2.substring(0, var13);
                  boolean var7 = var13 + 1 < var2.length() && var2.charAt(var13) == ':' && var2.charAt(var13 + 1) == '=';
                  ++var13;
                  if (var7) {
                     ++var13;
                  }

                  if (var14.equalsIgnoreCase("set")) {
                     while(var13 < var2.length() && (var2.charAt(var13) == ' ' || var2.charAt(var13) == '\t')) {
                        ++var13;
                     }

                     int var20;
                     for(var20 = var13; var13 < var2.length() && var2.charAt(var13) != ' ' && var2.charAt(var13) != '\t'; ++var13) {
                     }

                     String var16;
                     for(var16 = var2.substring(var20, var13); var13 < var2.length() && (var2.charAt(var13) == ' ' || var2.charAt(var13) == '\t'); ++var13) {
                     }

                     for(var20 = var13; var13 < var2.length() && var2.charAt(var13) != ' ' && var2.charAt(var13) != '\t'; ++var13) {
                     }

                     var17 = var2.substring(var20, var13);
                     setVar(this.reader, var16, var17);
                  } else {
                     while(var13 < var2.length() && (var2.charAt(var13) == ' ' || var2.charAt(var13) == '\t')) {
                        ++var13;
                     }

                     var8 = var13;
                     if (var13 < var2.length() && (var2.charAt(var13) == '\'' || var2.charAt(var13) == '"')) {
                        char var9 = var2.charAt(var13++);

                        for(boolean var10 = false; var13 < var2.length(); ++var13) {
                           if (var10) {
                              var10 = false;
                           } else if (var2.charAt(var13) == '\\') {
                              var10 = true;
                           } else if (var2.charAt(var13) == var9) {
                              break;
                           }
                        }
                     }

                     while(var13 < var2.length() && var2.charAt(var13) != ' ' && var2.charAt(var13) != '\t') {
                        ++var13;
                     }

                     var17 = var2.substring(Math.min(var8, var2.length()), Math.min(var13, var2.length()));
                     if (var14.charAt(0) == '"') {
                        var14 = translateQuoted(var14);
                     } else {
                        String var19 = var14.lastIndexOf(45) > 0 ? var14.substring(var14.lastIndexOf(45) + 1) : var14;
                        char var11 = getKeyFromName(var19);
                        var19 = var14.toLowerCase();
                        var14 = "";
                        if (var19.contains("meta-") || var19.contains("m-")) {
                           var14 = var14 + "\u001b";
                        }

                        if (var19.contains("control-") || var19.contains("c-") || var19.contains("ctrl-")) {
                           var11 = (char)(Character.toUpperCase(var11) & 31);
                        }

                        var14 = var14 + var11;
                     }

                     if (var17.length() <= 0 || var17.charAt(0) != '\'' && var17.charAt(0) != '"') {
                        this.reader.getKeys().bind(new Reference(var17), (CharSequence)var14);
                     } else {
                        this.reader.getKeys().bind(new Macro(translateQuoted(var17)), (CharSequence)var14);
                     }
                  }
               }
            }
         } catch (IllegalArgumentException var12) {
            Log.warn("Unable to parse user configuration: ", var12);
         }
      }

   }

   private static String translateQuoted(String var0) {
      String var2 = var0.substring(1, var0.length() - 1);
      StringBuilder var3 = new StringBuilder();

      for(int var1 = 0; var1 < var2.length(); ++var1) {
         char var4 = var2.charAt(var1);
         if (var4 == '\\') {
            boolean var5 = var2.regionMatches(var1, "\\C-", 0, 3) || var2.regionMatches(var1, "\\M-\\C-", 0, 6);
            boolean var6 = var2.regionMatches(var1, "\\M-", 0, 3) || var2.regionMatches(var1, "\\C-\\M-", 0, 6);
            var1 += (var6 ? 3 : 0) + (var5 ? 3 : 0) + (!var6 && !var5 ? 1 : 0);
            if (var1 >= var2.length()) {
               break;
            }

            var4 = var2.charAt(var1);
            if (var6) {
               var3.append("\u001b");
            }

            if (var5) {
               var4 = var4 == '?' ? 127 : (char)(Character.toUpperCase(var4) & 31);
            }

            if (!var6 && !var5) {
               int var7;
               int var8;
               label105:
               switch(var4) {
               case '0':
               case '1':
               case '2':
               case '3':
               case '4':
               case '5':
               case '6':
               case '7':
                  var4 = 0;

                  for(var7 = 0; var7 < 3 && var1 < var2.length(); ++var1) {
                     var8 = Character.digit(var2.charAt(var1), 8);
                     if (var8 < 0) {
                        break;
                     }

                     var4 = (char)(var4 * 8 + var8);
                     ++var7;
                  }

                  var4 = (char)(var4 & 255);
               case '8':
               case '9':
               case ':':
               case ';':
               case '<':
               case '=':
               case '>':
               case '?':
               case '@':
               case 'A':
               case 'B':
               case 'C':
               case 'D':
               case 'E':
               case 'F':
               case 'G':
               case 'H':
               case 'I':
               case 'J':
               case 'K':
               case 'L':
               case 'M':
               case 'N':
               case 'O':
               case 'P':
               case 'Q':
               case 'R':
               case 'S':
               case 'T':
               case 'U':
               case 'V':
               case 'W':
               case 'X':
               case 'Y':
               case 'Z':
               case '[':
               case ']':
               case '^':
               case '_':
               case '`':
               case 'c':
               case 'g':
               case 'h':
               case 'i':
               case 'j':
               case 'k':
               case 'l':
               case 'm':
               case 'o':
               case 'p':
               case 'q':
               case 's':
               case 'w':
               default:
                  break;
               case '\\':
                  var4 = '\\';
                  break;
               case 'a':
                  var4 = 7;
                  break;
               case 'b':
                  var4 = '\b';
                  break;
               case 'd':
                  var4 = 127;
                  break;
               case 'e':
                  var4 = 27;
                  break;
               case 'f':
                  var4 = '\f';
                  break;
               case 'n':
                  var4 = '\n';
                  break;
               case 'r':
                  var4 = '\r';
                  break;
               case 't':
                  var4 = '\t';
                  break;
               case 'u':
                  ++var1;
                  var4 = 0;
                  var7 = 0;

                  while(true) {
                     if (var7 >= 4 || var1 >= var2.length()) {
                        break label105;
                     }

                     var8 = Character.digit(var2.charAt(var1), 16);
                     if (var8 < 0) {
                        break label105;
                     }

                     var4 = (char)(var4 * 16 + var8);
                     ++var7;
                     ++var1;
                  }
               case 'v':
                  var4 = 11;
                  break;
               case 'x':
                  ++var1;
                  var4 = 0;

                  for(var7 = 0; var7 < 2 && var1 < var2.length(); ++var1) {
                     var8 = Character.digit(var2.charAt(var1), 16);
                     if (var8 < 0) {
                        break;
                     }

                     var4 = (char)(var4 * 16 + var8);
                     ++var7;
                  }

                  var4 = (char)(var4 & 255);
               }
            }

            var3.append(var4);
         } else {
            var3.append(var4);
         }
      }

      return var3.toString();
   }

   private static char getKeyFromName(String var0) {
      if (!"DEL".equalsIgnoreCase(var0) && !"Rubout".equalsIgnoreCase(var0)) {
         if (!"ESC".equalsIgnoreCase(var0) && !"Escape".equalsIgnoreCase(var0)) {
            if (!"LFD".equalsIgnoreCase(var0) && !"NewLine".equalsIgnoreCase(var0)) {
               if (!"RET".equalsIgnoreCase(var0) && !"Return".equalsIgnoreCase(var0)) {
                  if (!"SPC".equalsIgnoreCase(var0) && !"Space".equalsIgnoreCase(var0)) {
                     return "Tab".equalsIgnoreCase(var0) ? '\t' : var0.charAt(0);
                  } else {
                     return ' ';
                  }
               } else {
                  return '\r';
               }
            } else {
               return '\n';
            }
         } else {
            return '\u001b';
         }
      } else {
         return '\u007f';
      }
   }

   private static void setVar(LineReader var0, String var1, String var2) {
      if ("keymap".equalsIgnoreCase(var1)) {
         var0.setKeyMap(var2);
      } else {
         LineReader$Option[] var3 = LineReader$Option.values();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            LineReader$Option var6 = var3[var5];
            if (var6.name().toLowerCase(Locale.ENGLISH).replace('_', '-').equals(var2)) {
               if ("on".equalsIgnoreCase(var2)) {
                  var0.setOpt(var6);
               } else if ("off".equalsIgnoreCase(var2)) {
                  var0.unsetOpt(var6);
               }

               return;
            }
         }

         var0.setVariable(var1, var2);
      }
   }
}
