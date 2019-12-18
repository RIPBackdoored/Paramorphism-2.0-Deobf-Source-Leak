package site.hackery.paramorphism.api.config.util;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.config.ElementMask$DefaultImpls;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"},
   d2 = {"Lsite/hackery/paramorphism/api/config/util/EmptyElementMask;", "Lsite/hackery/paramorphism/api/config/ElementMask;", "()V", "paramorphism"}
)
public final class EmptyElementMask implements ElementMask {
   public EmptyElementMask() {
      super();
   }

   @NotNull
   public String[] getInclude() {
      return ElementMask$DefaultImpls.getInclude(this);
   }

   @NotNull
   public String[] getExclude() {
      return ElementMask$DefaultImpls.getExclude(this);
   }
}
