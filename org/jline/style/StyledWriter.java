package org.jline.style;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;

public class StyledWriter extends PrintWriter {
   private final Terminal terminal;
   private final StyleExpression expression;

   public StyledWriter(Writer var1, Terminal var2, StyleResolver var3, boolean var4) {
      super(var1, var4);
      this.terminal = (Terminal)Objects.requireNonNull(var2);
      this.expression = new StyleExpression(var3);
   }

   public StyledWriter(OutputStream var1, Terminal var2, StyleResolver var3, boolean var4) {
      super(var1, var4);
      this.terminal = (Terminal)Objects.requireNonNull(var2);
      this.expression = new StyleExpression(var3);
   }

   public void write(@Nonnull String var1) {
      AttributedString var2 = this.expression.evaluate(var1);
      super.write(var2.toAnsi(this.terminal));
   }

   public PrintWriter format(@Nonnull String var1, Object... var2) {
      this.print(String.format(var1, var2));
      return this;
   }

   public PrintWriter format(Locale var1, @Nonnull String var2, Object... var3) {
      this.print(String.format(var1, var2, var3));
      return this;
   }
}
