package site.hackery.paramorphism.api.config;

import kotlin.*;
import java.io.*;
import org.jetbrains.annotations.*;
import paramorphism-obfuscator.*;
import com.fasterxml.jackson.databind.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0000¢\u0006\u0002\b\n¨\u0006\u000b" }, d2 = { "Lsite/hackery/paramorphism/api/config/ParamorphismConfig$Companion;", "", "()V", "of", "Lsite/hackery/paramorphism/api/config/ParamorphismConfig;", "config", "Ljava/io/File;", "workingDirectory", "tree", "Lcom/fasterxml/jackson/databind/JsonNode;", "of$paramorphism", "paramorphism" })
public static final class Companion
{
    public static final Companion $$INSTANCE;
    
    @NotNull
    public final ParamorphismConfig of(@NotNull final File file) {
        return new jg(file);
    }
    
    @NotNull
    public final ParamorphismConfig of$paramorphism(@NotNull final File file, @NotNull final JsonNode jsonNode) {
        return new jg(file, jsonNode);
    }
    
    private Companion() {
        super();
    }
    
    static {
        $$INSTANCE = new Companion();
    }
}
