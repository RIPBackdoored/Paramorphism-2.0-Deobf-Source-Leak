package org.jline.style;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;

public class NopStyleSource implements StyleSource {
   public NopStyleSource() {
      super();
   }

   @Nullable
   public String get(String var1, String var2) {
      Objects.requireNonNull(var1);
      Objects.requireNonNull(var2);
      return null;
   }

   public void set(String var1, String var2, String var3) {
      Objects.requireNonNull(var1);
      Objects.requireNonNull(var2);
      Objects.requireNonNull(var3);
   }

   public void remove(String var1) {
      Objects.requireNonNull(var1);
   }

   public void remove(String var1, String var2) {
      Objects.requireNonNull(var1);
      Objects.requireNonNull(var2);
   }

   public void clear() {
   }

   public Iterable groups() {
      return Collections.unmodifiableList(Collections.emptyList());
   }

   public Map styles(String var1) {
      return Collections.unmodifiableMap(Collections.emptyMap());
   }
}
