package org.jline.builtins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReader$Option;
import org.jline.reader.ParsedLine;

public class Completers$RegexCompleter implements Completer {
   private final NfaMatcher matcher;
   private final Function completers;
   private final ThreadLocal reader = new ThreadLocal();

   public Completers$RegexCompleter(String var1, Function var2) {
      super();
      this.matcher = new NfaMatcher(var1, this::doMatch);
      this.completers = var2;
   }

   public synchronized void complete(LineReader var1, ParsedLine var2, List var3) {
      List var4 = var2.words().subList(0, var2.wordIndex());
      this.reader.set(var1);
      Set var5 = this.matcher.matchPartial(var4);
      Iterator var6 = var5.iterator();

      while(var6.hasNext()) {
         String var7 = (String)var6.next();
         ((Completer)this.completers.apply(var7)).complete(var1, new Completers$RegexCompleter$ArgumentLine(var2.word(), var2.wordCursor()), var3);
      }

      this.reader.set((Object)null);
   }

   private boolean doMatch(String var1, String var2) {
      ArrayList var3 = new ArrayList();
      LineReader var4 = (LineReader)this.reader.get();
      boolean var5 = var4 != null && var4.isSet(LineReader$Option.CASE_INSENSITIVE);
      ((Completer)this.completers.apply(var2)).complete(var4, new Completers$RegexCompleter$ArgumentLine(var1, var1.length()), var3);
      return var3.stream().anyMatch(Completers$RegexCompleter::lambda$doMatch$0);
   }

   private static boolean lambda$doMatch$0(boolean var0, String var1, Candidate var2) {
      return var0 ? var2.value().equalsIgnoreCase(var1) : var2.value().equals(var1);
   }
}
