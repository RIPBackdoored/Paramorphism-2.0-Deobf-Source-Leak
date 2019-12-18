package org.jline.style;

import java.util.Objects;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public class StyleExpression {
   private final StyleResolver resolver;

   public StyleExpression() {
      this(new StyleResolver(new NopStyleSource(), ""));
   }

   public StyleExpression(StyleResolver var1) {
      super();
      this.resolver = (StyleResolver)Objects.requireNonNull(var1);
   }

   public void evaluate(AttributedStringBuilder var1, String var2) {
      Objects.requireNonNull(var1);
      Objects.requireNonNull(var2);
      String var3 = InterpolationHelper.substVars(var2, this::style, false);
      var1.appendAnsi(var3);
   }

   private String style(String var1) {
      int var2 = var1.indexOf(32);
      if (var2 > 0) {
         String var3 = var1.substring(0, var2);
         String var4 = var1.substring(var2 + 1);
         AttributedStyle var5 = this.resolver.resolve(var3);
         return (new AttributedStringBuilder()).style(var5).ansiAppend(var4).toAnsi();
      } else {
         return null;
      }
   }

   public AttributedString evaluate(String var1) {
      AttributedStringBuilder var2 = new AttributedStringBuilder();
      this.evaluate(var2, var1);
      return var2.toAttributedString();
   }
}
