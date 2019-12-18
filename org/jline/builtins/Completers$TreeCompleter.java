package org.jline.builtins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

public class Completers$TreeCompleter implements Completer {
   final Map completers;
   final Completers$RegexCompleter completer;

   public Completers$TreeCompleter(Completers$TreeCompleter$Node... var1) {
      this(Arrays.asList(var1));
   }

   public Completers$TreeCompleter(List var1) {
      super();
      this.completers = new HashMap();
      StringBuilder var2 = new StringBuilder();
      this.addRoots(var2, var1);
      String var10003 = var2.toString();
      Map var10004 = this.completers;
      var10004.getClass();
      this.completer = new Completers$RegexCompleter(var10003, var10004::get);
   }

   public static Completers$TreeCompleter$Node node(Object... var0) {
      Completer var1 = null;
      ArrayList var2 = new ArrayList();
      ArrayList var3 = new ArrayList();
      Object[] var4 = var0;
      int var5 = var0.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Object var7 = var4[var6];
         if (var7 instanceof String) {
            var2.add(new Candidate((String)var7));
         } else if (var7 instanceof Candidate) {
            var2.add((Candidate)var7);
         } else if (var7 instanceof Completers$TreeCompleter$Node) {
            var3.add((Completers$TreeCompleter$Node)var7);
         } else {
            if (!(var7 instanceof Completer)) {
               throw new IllegalArgumentException();
            }

            var1 = (Completer)var7;
         }
      }

      if (var1 != null) {
         if (!var2.isEmpty()) {
            throw new IllegalArgumentException();
         } else {
            return new Completers$TreeCompleter$Node(var1, var3);
         }
      } else if (!var2.isEmpty()) {
         return new Completers$TreeCompleter$Node(Completers$TreeCompleter::lambda$node$0, var3);
      } else {
         throw new IllegalArgumentException();
      }
   }

   void addRoots(StringBuilder var1, List var2) {
      if (!var2.isEmpty()) {
         var1.append(" ( ");
         boolean var3 = true;
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            Completers$TreeCompleter$Node var5 = (Completers$TreeCompleter$Node)var4.next();
            if (var3) {
               var3 = false;
            } else {
               var1.append(" | ");
            }

            String var6 = "c" + this.completers.size();
            this.completers.put(var6, var5.completer);
            var1.append(var6);
            this.addRoots(var1, var5.nodes);
         }

         var1.append(" ) ");
      }

   }

   public void complete(LineReader var1, ParsedLine var2, List var3) {
      this.completer.complete(var1, var2, var3);
   }

   private static void lambda$node$0(List var0, LineReader var1, ParsedLine var2, List var3) {
      var3.addAll(var0);
   }
}
