package site.hackery.paramorphism.api.config.naming;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.naming.MappingDictionary;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0007R\u0014\u0010\f\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0007¨\u0006\u000e"},
   d2 = {"Lsite/hackery/paramorphism/api/config/naming/UniformNameGenerationDictionaries;", "Lsite/hackery/paramorphism/api/config/naming/NameGenerationDictionaries;", "dictionary", "Lsite/hackery/paramorphism/api/naming/MappingDictionary;", "(Lsite/hackery/paramorphism/api/naming/MappingDictionary;)V", "classes", "getClasses", "()Lsite/hackery/paramorphism/api/naming/MappingDictionary;", "fields", "getFields", "methods", "getMethods", "packages", "getPackages", "paramorphism"}
)
public final class UniformNameGenerationDictionaries implements NameGenerationDictionaries {
   private final MappingDictionary dictionary;

   @NotNull
   public MappingDictionary getPackages() {
      return this.dictionary;
   }

   @NotNull
   public MappingDictionary getClasses() {
      return this.dictionary;
   }

   @NotNull
   public MappingDictionary getMethods() {
      return this.dictionary;
   }

   @NotNull
   public MappingDictionary getFields() {
      return this.dictionary;
   }

   public UniformNameGenerationDictionaries(@NotNull MappingDictionary var1) {
      super();
      this.dictionary = var1;
   }
}
