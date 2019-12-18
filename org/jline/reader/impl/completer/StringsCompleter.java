package org.jline.reader.impl.completer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jline.utils.AttributedString;

public class StringsCompleter implements Completer {
   protected final Collection candidates;
   static final boolean $assertionsDisabled = !StringsCompleter.class.desiredAssertionStatus();

   public StringsCompleter() {
      super();
      this.candidates = new ArrayList();
   }

   public StringsCompleter(String... var1) {
      this((Iterable)Arrays.asList(var1));
   }

   public StringsCompleter(Iterable var1) {
      super();
      this.candidates = new ArrayList();
      if (!$assertionsDisabled && var1 == null) {
         throw new AssertionError();
      } else {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            this.candidates.add(new Candidate(AttributedString.stripAnsi(var3), var3, (String)null, (String)null, (String)null, (String)null, true));
         }

      }
   }

   public void complete(LineReader var1, ParsedLine var2, List var3) {
      if (!$assertionsDisabled && var2 == null) {
         throw new AssertionError();
      } else if (!$assertionsDisabled && var3 == null) {
         throw new AssertionError();
      } else {
         var3.addAll(this.candidates);
      }
   }
}
