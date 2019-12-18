package org.jline.style;

import java.util.Objects;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

public class StyleFactory {
   private final StyleResolver resolver;

   public StyleFactory(StyleResolver var1) {
      super();
      this.resolver = (StyleResolver)Objects.requireNonNull(var1);
   }

   public AttributedString style(String var1, String var2) {
      Objects.requireNonNull(var2);
      AttributedStyle var3 = this.resolver.resolve(var1);
      return new AttributedString(var2, var3);
   }

   public AttributedString style(String var1, String var2, Object... var3) {
      Objects.requireNonNull(var2);
      Objects.requireNonNull(var3);
      return this.style(var1, String.format(var2, var3));
   }

   public AttributedString evaluate(String var1) {
      Objects.requireNonNull(var1);
      return (new StyleExpression(this.resolver)).evaluate(var1);
   }

   public AttributedString evaluate(String var1, Object... var2) {
      Objects.requireNonNull(var1);
      Objects.requireNonNull(var2);
      return this.evaluate(String.format(var1, var2));
   }
}
