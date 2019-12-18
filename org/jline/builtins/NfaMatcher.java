package org.jline.builtins;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class NfaMatcher {
   private final String regexp;
   private final BiFunction matcher;
   private NfaMatcher$State start;

   public NfaMatcher(String var1, BiFunction var2) {
      super();
      this.regexp = var1;
      this.matcher = var2;
   }

   public void compile() {
      if (this.start == null) {
         this.start = toNfa(toPostFix(this.regexp));
      }

   }

   public boolean match(List var1) {
      HashSet var2 = new HashSet();
      this.compile();
      this.addState(var2, this.start);

      HashSet var5;
      for(Iterator var3 = var1.iterator(); var3.hasNext(); var2 = var5) {
         Object var4 = var3.next();
         var5 = new HashSet();
         var2.stream().filter(NfaMatcher::lambda$match$0).filter(this::lambda$match$1).forEach(this::lambda$match$2);
      }

      return var2.stream().anyMatch(NfaMatcher::lambda$match$3);
   }

   public Set matchPartial(List var1) {
      HashSet var2 = new HashSet();
      this.compile();
      this.addState(var2, this.start);

      HashSet var5;
      for(Iterator var3 = var1.iterator(); var3.hasNext(); var2 = var5) {
         Object var4 = var3.next();
         var5 = new HashSet();
         var2.stream().filter(NfaMatcher::lambda$matchPartial$4).filter(this::lambda$matchPartial$5).forEach(this::lambda$matchPartial$6);
      }

      return (Set)var2.stream().filter(NfaMatcher::lambda$matchPartial$7).map(NfaMatcher::lambda$matchPartial$8).collect(Collectors.toSet());
   }

   void addState(Set var1, NfaMatcher$State var2) {
      if (var2 != null && var1.add(var2) && Objects.equals("++SPLIT++", var2.c)) {
         this.addState(var1, var2.out);
         this.addState(var1, var2.out1);
      }

   }

   static NfaMatcher$State toNfa(List var0) {
      ArrayDeque var1 = new ArrayDeque();
      Iterator var6 = var0.iterator();

      NfaMatcher$Frag var4;
      while(var6.hasNext()) {
         String var7 = (String)var6.next();
         byte var9 = -1;
         switch(var7.hashCode()) {
         case 42:
            if (var7.equals("*")) {
               var9 = 3;
            }
            break;
         case 43:
            if (var7.equals("+")) {
               var9 = 4;
            }
            break;
         case 46:
            if (var7.equals(".")) {
               var9 = 0;
            }
            break;
         case 63:
            if (var7.equals("?")) {
               var9 = 2;
            }
            break;
         case 124:
            if (var7.equals("|")) {
               var9 = 1;
            }
         }

         NfaMatcher$Frag var2;
         NfaMatcher$Frag var3;
         NfaMatcher$State var5;
         switch(var9) {
         case 0:
            var3 = (NfaMatcher$Frag)var1.pollLast();
            var2 = (NfaMatcher$Frag)var1.pollLast();
            var2.patch(var3.start);
            var1.offerLast(new NfaMatcher$Frag(var2.start, var3.out));
            break;
         case 1:
            var3 = (NfaMatcher$Frag)var1.pollLast();
            var2 = (NfaMatcher$Frag)var1.pollLast();
            var5 = new NfaMatcher$State("++SPLIT++", var2.start, var3.start);
            var1.offerLast(new NfaMatcher$Frag(var5, var2.out, var3.out));
            break;
         case 2:
            var4 = (NfaMatcher$Frag)var1.pollLast();
            var5 = new NfaMatcher$State("++SPLIT++", var4.start, (NfaMatcher$State)null);
            List var10004 = var4.out;
            var5.getClass();
            var1.offerLast(new NfaMatcher$Frag(var5, var10004, var5::setOut1));
            break;
         case 3:
            var4 = (NfaMatcher$Frag)var1.pollLast();
            var5 = new NfaMatcher$State("++SPLIT++", var4.start, (NfaMatcher$State)null);
            var4.patch(var5);
            var5.getClass();
            var1.offerLast(new NfaMatcher$Frag(var5, var5::setOut1));
            break;
         case 4:
            var4 = (NfaMatcher$Frag)var1.pollLast();
            var5 = new NfaMatcher$State("++SPLIT++", var4.start, (NfaMatcher$State)null);
            var4.patch(var5);
            NfaMatcher$State var10003 = var4.start;
            var5.getClass();
            var1.offerLast(new NfaMatcher$Frag(var10003, var5::setOut1));
            break;
         default:
            var5 = new NfaMatcher$State(var7, (NfaMatcher$State)null, (NfaMatcher$State)null);
            var5.getClass();
            var1.offerLast(new NfaMatcher$Frag(var5, var5::setOut));
         }
      }

      var4 = (NfaMatcher$Frag)var1.pollLast();
      if (!var1.isEmpty()) {
         throw new IllegalStateException("Wrong postfix expression, " + var1.size() + " elements remaining");
      } else {
         var4.patch(new NfaMatcher$State("++MATCH++", (NfaMatcher$State)null, (NfaMatcher$State)null));
         return var4.start;
      }
   }

   static List toPostFix(String var0) {
      ArrayList var1 = new ArrayList();
      int var2 = -1;
      int var3 = 0;
      int var4 = 0;
      ArrayDeque var5 = new ArrayDeque();
      ArrayDeque var6 = new ArrayDeque();

      label106:
      for(int var7 = 0; var7 < var0.length(); ++var7) {
         char var8 = var0.charAt(var7);
         if (Character.isJavaIdentifierPart(var8)) {
            if (var2 < 0) {
               var2 = var7;
            }
         } else {
            if (var2 >= 0) {
               if (var3 > 1) {
                  --var3;
                  var1.add(".");
               }

               var1.add(var0.substring(var2, var7));
               ++var3;
               var2 = -1;
            }

            if (!Character.isWhitespace(var8)) {
               switch(var8) {
               case '(':
                  if (var3 > 1) {
                     --var3;
                     var1.add(".");
                  }

                  var6.offerLast(var4);
                  var5.offerLast(var3);
                  var4 = 0;
                  var3 = 0;
                  break;
               case ')':
                  if (var6.isEmpty() || var3 == 0) {
                     throw new IllegalStateException("unexpected '" + var8 + "' at pos " + var7);
                  }

                  while(true) {
                     --var3;
                     if (var3 <= 0) {
                        while(var4 > 0) {
                           var1.add("|");
                           --var4;
                        }

                        var4 = (Integer)var6.pollLast();
                        var3 = (Integer)var5.pollLast();
                        ++var3;
                        continue label106;
                     }

                     var1.add(".");
                  }
               case '*':
               case '+':
               case '?':
                  if (var3 == 0) {
                     throw new IllegalStateException("unexpected '" + var8 + "' at pos " + var7);
                  }

                  var1.add(String.valueOf(var8));
                  break;
               case '|':
                  if (var3 == 0) {
                     throw new IllegalStateException("unexpected '" + var8 + "' at pos " + var7);
                  }

                  while(true) {
                     --var3;
                     if (var3 <= 0) {
                        ++var4;
                        continue label106;
                     }

                     var1.add(".");
                  }
               default:
                  throw new IllegalStateException("unexpected '" + var8 + "' at pos " + var7);
               }
            }
         }
      }

      if (var2 >= 0) {
         if (var3 > 1) {
            --var3;
            var1.add(".");
         }

         var1.add(var0.substring(var2));
         ++var3;
      }

      while(true) {
         --var3;
         if (var3 <= 0) {
            while(var4 > 0) {
               var1.add("|");
               --var4;
            }

            return var1;
         }

         var1.add(".");
      }
   }

   private static String lambda$matchPartial$8(NfaMatcher$State var0) {
      return var0.c;
   }

   private static boolean lambda$matchPartial$7(NfaMatcher$State var0) {
      return !Objects.equals("++MATCH++", var0.c) && !Objects.equals("++SPLIT++", var0.c);
   }

   private void lambda$matchPartial$6(Set var1, NfaMatcher$State var2) {
      this.addState(var1, var2.out);
   }

   private boolean lambda$matchPartial$5(Object var1, NfaMatcher$State var2) {
      return (Boolean)this.matcher.apply(var1, var2.c);
   }

   private static boolean lambda$matchPartial$4(NfaMatcher$State var0) {
      return !Objects.equals("++MATCH++", var0.c) && !Objects.equals("++SPLIT++", var0.c);
   }

   private static boolean lambda$match$3(NfaMatcher$State var0) {
      return Objects.equals("++MATCH++", var0.c);
   }

   private void lambda$match$2(Set var1, NfaMatcher$State var2) {
      this.addState(var1, var2.out);
   }

   private boolean lambda$match$1(Object var1, NfaMatcher$State var2) {
      return (Boolean)this.matcher.apply(var1, var2.c);
   }

   private static boolean lambda$match$0(NfaMatcher$State var0) {
      return !Objects.equals("++MATCH++", var0.c) && !Objects.equals("++SPLIT++", var0.c);
   }
}
