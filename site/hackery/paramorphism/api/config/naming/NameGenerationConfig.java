package site.hackery.paramorphism.api.config.naming;

import kotlin.*;
import org.jetbrains.annotations.*;
import site.hackery.paramorphism.api.naming.*;

@Metadata(mv = { 1, 1, 16 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\n" }, d2 = { "Lsite/hackery/paramorphism/api/config/naming/NameGenerationConfig;", "", "dictionaries", "Lsite/hackery/paramorphism/api/config/naming/NameGenerationDictionaries;", "getDictionaries", "()Lsite/hackery/paramorphism/api/config/naming/NameGenerationDictionaries;", "shuffle", "", "getShuffle", "()Z", "paramorphism" })
public interface NameGenerationConfig
{
    @NotNull
    NameGenerationDictionaries getDictionaries();
    
    boolean getShuffle();
    
    @Metadata(mv = { 1, 1, 16 }, bv = { 1, 0, 3 }, k = 3)
    public static final class DefaultImpls
    {
        @NotNull
        public static NameGenerationDictionaries getDictionaries(final NameGenerationConfig nameGenerationConfig) {
            return new UniformNameGenerationDictionaries(Dictionaries.INSTANCE.getALPHABET());
        }
        
        public static boolean getShuffle(final NameGenerationConfig nameGenerationConfig) {
            return false;
        }
    }
}
