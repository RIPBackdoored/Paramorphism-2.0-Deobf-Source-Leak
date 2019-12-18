package org.jline.keymap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.jline.terminal.Terminal;
import org.jline.utils.Curses;
import org.jline.utils.InfoCmp$Capability;

public class KeyMap {
   public static final int KEYMAP_LENGTH = 128;
   public static final long DEFAULT_AMBIGUOUS_TIMEOUT = 1000L;
   private Object[] mapping = new Object[128];
   private Object anotherKey = null;
   private Object unicode;
   private Object nomatch;
   private long ambiguousTimeout = 1000L;
   public static final Comparator KEYSEQ_COMPARATOR = KeyMap::lambda$static$0;

   public KeyMap() {
      super();
   }

   public static String display(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("\"");

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         char var3 = var0.charAt(var2);
         if (var3 < ' ') {
            var1.append('^');
            var1.append((char)(var3 + 65 - 1));
         } else if (var3 == 127) {
            var1.append("^?");
         } else if (var3 != '^' && var3 != '\\') {
            if (var3 >= 128) {
               var1.append(String.format("\\u%04x", Integer.valueOf(var3)));
            } else {
               var1.append(var3);
            }
         } else {
            var1.append('\\').append(var3);
         }
      }

      var1.append("\"");
      return var1.toString();
   }

   public static String translate(String var0) {
      if (!var0.isEmpty()) {
         char var2 = var0.charAt(0);
         if ((var2 == '\'' || var2 == '"') && var0.charAt(var0.length() - 1) == var2) {
            var0 = var0.substring(1, var0.length() - 1);
         }
      }

      StringBuilder var6 = new StringBuilder();

      for(int var1 = 0; var1 < var0.length(); ++var1) {
         char var3 = var0.charAt(var1);
         if (var3 == '\\') {
            ++var1;
            if (var1 >= var0.length()) {
               break;
            }

            var3 = var0.charAt(var1);
            int var4;
            int var5;
            label117:
            switch(var3) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
               var3 = 0;

               for(var4 = 0; var4 < 3 && var1 < var0.length(); ++var1) {
                  var5 = Character.digit(var0.charAt(var1), 8);
                  if (var5 < 0) {
                     break;
                  }

                  var3 = (char)(var3 * 8 + var5);
                  ++var4;
               }

               --var1;
               var3 = (char)(var3 & 255);
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
            case 'D':
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
            case 'C':
               ++var1;
               if (var1 < var0.length()) {
                  label140: {
                     var3 = var0.charAt(var1);
                     if (var3 == '-') {
                        ++var1;
                        if (var1 >= var0.length()) {
                           break label140;
                        }

                        var3 = var0.charAt(var1);
                     }

                     var3 = var3 == '?' ? 127 : (char)(Character.toUpperCase(var3) & 31);
                  }
               }
               break;
            case 'E':
            case 'e':
               var3 = 27;
               break;
            case '\\':
               var3 = '\\';
               break;
            case 'a':
               var3 = 7;
               break;
            case 'b':
               var3 = '\b';
               break;
            case 'd':
               var3 = 127;
               break;
            case 'f':
               var3 = '\f';
               break;
            case 'n':
               var3 = '\n';
               break;
            case 'r':
               var3 = '\r';
               break;
            case 't':
               var3 = '\t';
               break;
            case 'u':
               ++var1;
               var3 = 0;
               var4 = 0;

               while(true) {
                  if (var4 >= 4 || var1 >= var0.length()) {
                     break label117;
                  }

                  var5 = Character.digit(var0.charAt(var1), 16);
                  if (var5 < 0) {
                     break label117;
                  }

                  var3 = (char)(var3 * 16 + var5);
                  ++var4;
                  ++var1;
               }
            case 'v':
               var3 = 11;
               break;
            case 'x':
               ++var1;
               var3 = 0;

               for(var4 = 0; var4 < 2 && var1 < var0.length(); ++var1) {
                  var5 = Character.digit(var0.charAt(var1), 16);
                  if (var5 < 0) {
                     break;
                  }

                  var3 = (char)(var3 * 16 + var5);
                  ++var4;
               }

               --var1;
               var3 = (char)(var3 & 255);
            }
         } else if (var3 == '^') {
            ++var1;
            if (var1 >= var0.length()) {
               break;
            }

            var3 = var0.charAt(var1);
            if (var3 != '^') {
               var3 = var3 == '?' ? 127 : (char)(Character.toUpperCase(var3) & 31);
            }
         }

         var6.append(var3);
      }

      return var6.toString();
   }

   public static Collection range(String var0) {
      String[] var1 = var0.split("-");
      if (var1.length != 2) {
         return null;
      } else {
         var1[0] = translate(var1[0]);
         var1[1] = translate(var1[1]);
         if (var1[0].length() != var1[1].length()) {
            return null;
         } else {
            String var2;
            if (var1[0].length() > 1) {
               var2 = var1[0].substring(0, var1[0].length() - 1);
               if (!var1[1].startsWith(var2)) {
                  return null;
               }
            } else {
               var2 = "";
            }

            char var3 = var1[0].charAt(var1[0].length() - 1);
            char var4 = var1[1].charAt(var1[1].length() - 1);
            if (var3 > var4) {
               return null;
            } else {
               ArrayList var5 = new ArrayList();

               for(char var6 = var3; var6 <= var4; ++var6) {
                  var5.add(var2 + var6);
               }

               return var5;
            }
         }
      }
   }

   public static String esc() {
      return "\u001b";
   }

   public static String alt(char var0) {
      return "\u001b" + var0;
   }

   public static String alt(String var0) {
      return "\u001b" + var0;
   }

   public static String del() {
      return "\u007f";
   }

   public static String ctrl(char var0) {
      return var0 == '?' ? del() : Character.toString((char)(Character.toUpperCase(var0) & 31));
   }

   public static String key(Terminal var0, InfoCmp$Capability var1) {
      return Curses.tputs(var0.getStringCapability(var1));
   }

   public Object getUnicode() {
      return this.unicode;
   }

   public void setUnicode(Object var1) {
      this.unicode = var1;
   }

   public Object getNomatch() {
      return this.nomatch;
   }

   public void setNomatch(Object var1) {
      this.nomatch = var1;
   }

   public long getAmbiguousTimeout() {
      return this.ambiguousTimeout;
   }

   public void setAmbiguousTimeout(long var1) {
      this.ambiguousTimeout = var1;
   }

   public Object getAnotherKey() {
      return this.anotherKey;
   }

   public Map getBoundKeys() {
      TreeMap var1 = new TreeMap(KEYSEQ_COMPARATOR);
      doGetBoundKeys(this, "", var1);
      return var1;
   }

   private static void doGetBoundKeys(KeyMap var0, String var1, Map var2) {
      if (var0.anotherKey != null) {
         var2.put(var1, var0.anotherKey);
      }

      for(int var3 = 0; var3 < var0.mapping.length; ++var3) {
         if (var0.mapping[var3] instanceof KeyMap) {
            doGetBoundKeys((KeyMap)var0.mapping[var3], var1 + (char)var3, var2);
         } else if (var0.mapping[var3] != null) {
            var2.put(var1 + (char)var3, var0.mapping[var3]);
         }
      }

   }

   public Object getBound(CharSequence var1, int[] var2) {
      var2[0] = -1;
      if (var1 != null && var1.length() > 0) {
         char var3 = var1.charAt(0);
         if (var3 >= this.mapping.length) {
            var2[0] = Character.codePointCount(var1, 0, var1.length());
            return null;
         } else if (this.mapping[var3] instanceof KeyMap) {
            CharSequence var4 = var1.subSequence(1, var1.length());
            return ((KeyMap)this.mapping[var3]).getBound(var4, var2);
         } else if (this.mapping[var3] != null) {
            var2[0] = var1.length() - 1;
            return this.mapping[var3];
         } else {
            var2[0] = var1.length();
            return this.anotherKey;
         }
      } else {
         return this.anotherKey;
      }
   }

   public Object getBound(CharSequence var1) {
      int[] var2 = new int[1];
      Object var3 = this.getBound(var1, var2);
      return var2[0] <= 0 ? var3 : null;
   }

   public void bindIfNotBound(Object var1, CharSequence var2) {
      if (var1 != null && var2 != null) {
         bind(this, var2, var1, true);
      }

   }

   public void bind(Object var1, CharSequence... var2) {
      CharSequence[] var3 = var2;
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         CharSequence var6 = var3[var5];
         this.bind(var1, var6);
      }

   }

   public void bind(Object var1, Iterable var2) {
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         CharSequence var4 = (CharSequence)var3.next();
         this.bind(var1, var4);
      }

   }

   public void bind(Object var1, CharSequence var2) {
      if (var2 != null) {
         if (var1 == null) {
            this.unbind(var2);
         } else {
            bind(this, var2, var1, false);
         }
      }

   }

   public void unbind(CharSequence... var1) {
      CharSequence[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         CharSequence var5 = var2[var4];
         this.unbind(var5);
      }

   }

   public void unbind(CharSequence var1) {
      if (var1 != null) {
         unbind(this, var1);
      }

   }

   private static Object unbind(KeyMap var0, CharSequence var1) {
      KeyMap var2 = null;
      if (var1 != null && var1.length() > 0) {
         for(int var3 = 0; var3 < var1.length() - 1; ++var3) {
            char var4 = var1.charAt(var3);
            if (var4 > var0.mapping.length) {
               return null;
            }

            if (!(var0.mapping[var4] instanceof KeyMap)) {
               return null;
            }

            var2 = var0;
            var0 = (KeyMap)var0.mapping[var4];
         }

         char var7 = var1.charAt(var1.length() - 1);
         if (var7 > var0.mapping.length) {
            return null;
         } else if (var0.mapping[var7] instanceof KeyMap) {
            KeyMap var9 = (KeyMap)var0.mapping[var7];
            Object var10 = var9.anotherKey;
            var9.anotherKey = null;
            return var10;
         } else {
            Object var8 = var0.mapping[var7];
            var0.mapping[var7] = null;
            int var5 = 0;

            for(int var6 = 0; var6 < var0.mapping.length; ++var6) {
               if (var0.mapping[var6] != null) {
                  ++var5;
               }
            }

            if (var5 == 0 && var2 != null) {
               var2.mapping[var1.charAt(var1.length() - 2)] = var0.anotherKey;
            }

            return var8;
         }
      } else {
         return null;
      }
   }

   private static void bind(KeyMap var0, CharSequence var1, Object var2, boolean var3) {
      if (var1 != null && var1.length() > 0) {
         for(int var4 = 0; var4 < var1.length(); ++var4) {
            char var5 = var1.charAt(var4);
            if (var5 >= var0.mapping.length) {
               return;
            }

            if (var4 < var1.length() - 1) {
               if (!(var0.mapping[var5] instanceof KeyMap)) {
                  KeyMap var7 = new KeyMap();
                  var7.anotherKey = var0.mapping[var5];
                  var0.mapping[var5] = var7;
               }

               var0 = (KeyMap)var0.mapping[var5];
            } else if (var0.mapping[var5] instanceof KeyMap) {
               ((KeyMap)var0.mapping[var5]).anotherKey = var2;
            } else {
               Object var6 = var0.mapping[var5];
               if (!var3 || var6 == null) {
                  var0.mapping[var5] = var2;
               }
            }
         }
      }

   }

   private static int lambda$static$0(String var0, String var1) {
      int var2 = var0.length();
      int var3 = var1.length();
      int var4 = Math.min(var2, var3);

      for(int var5 = 0; var5 < var4; ++var5) {
         char var6 = var0.charAt(var5);
         char var7 = var1.charAt(var5);
         if (var6 != var7) {
            int var8 = var2 - var3;
            return var8 != 0 ? var8 : var6 - var7;
         }
      }

      return var2 - var3;
   }
}
