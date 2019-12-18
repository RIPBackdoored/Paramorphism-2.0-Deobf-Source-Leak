package org.jline.reader.impl.completer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

public class AggregateCompleter implements Completer {
   private final Collection completers;
   static final boolean $assertionsDisabled = !AggregateCompleter.class.desiredAssertionStatus();

   public AggregateCompleter(Completer... var1) {
      this((Collection)Arrays.asList(var1));
   }

   public AggregateCompleter(Collection var1) {
      super();
      if (!$assertionsDisabled && var1 == null) {
         throw new AssertionError();
      } else {
         this.completers = var1;
      }
   }

   public Collection getCompleters() {
      return this.completers;
   }

   public void complete(LineReader var1, ParsedLine var2, List var3) {
      Objects.requireNonNull(var2);
      Objects.requireNonNull(var3);
      this.completers.forEach(AggregateCompleter::lambda$complete$0);
   }

   public String toString() {
      return this.getClass().getSimpleName() + "{completers=" + this.completers + '}';
   }

   private static void lambda$complete$0(LineReader var0, ParsedLine var1, List var2, Completer var3) {
      var3.complete(var0, var1, var2);
   }
}
