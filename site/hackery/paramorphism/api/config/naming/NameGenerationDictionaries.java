package site.hackery.paramorphism.api.config.naming;

import kotlin.*;
import site.hackery.paramorphism.api.naming.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 16 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005R\u0012\u0010\n\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005¨\u0006\f" }, d2 = { "Lsite/hackery/paramorphism/api/config/naming/NameGenerationDictionaries;", "", "classes", "Lsite/hackery/paramorphism/api/naming/MappingDictionary;", "getClasses", "()Lsite/hackery/paramorphism/api/naming/MappingDictionary;", "fields", "getFields", "methods", "getMethods", "packages", "getPackages", "paramorphism" })
public interface NameGenerationDictionaries
{
    @NotNull
    MappingDictionary getPackages();
    
    @NotNull
    MappingDictionary getClasses();
    
    @NotNull
    MappingDictionary getMethods();
    
    @NotNull
    MappingDictionary getFields();
}
