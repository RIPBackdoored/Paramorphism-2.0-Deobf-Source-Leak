package site.hackery.paramorphism.api.config.strategies.obfuscation.remapper;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.naming.NameGenerationConfig;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"},
   d2 = {"Lsite/hackery/paramorphism/api/config/strategies/obfuscation/remapper/RemappingStrategyConfig;", "Lsite/hackery/paramorphism/api/config/StrategyConfiguration;", "excludeEnums", "", "getExcludeEnums", "()Z", "flat", "getFlat", "manifestEntries", "", "", "getManifestEntries", "()[Ljava/lang/String;", "nameGeneration", "Lsite/hackery/paramorphism/api/config/naming/NameGenerationConfig;", "getNameGeneration", "()Lsite/hackery/paramorphism/api/config/naming/NameGenerationConfig;", "prefix", "getPrefix", "()Ljava/lang/String;", "paramorphism"}
)
public interface RemappingStrategyConfig extends StrategyConfiguration {
   @NotNull
   String[] getManifestEntries();

   boolean getFlat();

   @Nullable
   String getPrefix();

   @NotNull
   NameGenerationConfig getNameGeneration();

   boolean getExcludeEnums();
}
