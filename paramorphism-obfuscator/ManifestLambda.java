package paramorphism-obfuscator;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import java.io.*;
import java.util.jar.*;
import site.hackery.paramorphism.api.resources.*;
import org.jetbrains.annotations.*;
import java.util.*;

public final class ManifestLambda extends Lambda implements Function0<Manifest>
{
    public final kg bje;
    
    @Override
    public Object invoke() {
        return this.a();
    }
    
    @NotNull
    public final Manifest a() {
        final Resource value = this.bje.d().get("META-INF/MANIFEST.MF");
        Manifest manifest;
        if (value != null) {
            manifest = new Manifest(new ByteArrayInputStream(value.getData()));
        }
        else {
            final Manifest manifest2 = new Manifest();
            ((Map<Attributes.Name, String>)manifest2.getMainAttributes()).put(Attributes.Name.MANIFEST_VERSION, "1.0");
            manifest = manifest2;
        }
        return manifest;
    }
    
    public ManifestLambda(final kg bje) {
        this.bje = bje;
        super(0);
    }
}
