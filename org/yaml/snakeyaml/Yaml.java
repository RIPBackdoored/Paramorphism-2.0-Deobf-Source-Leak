package org.yaml.snakeyaml;

import org.yaml.snakeyaml.resolver.*;
import org.yaml.snakeyaml.representer.*;
import org.yaml.snakeyaml.constructor.*;
import org.yaml.snakeyaml.error.*;
import java.util.*;
import org.yaml.snakeyaml.serializer.*;
import org.yaml.snakeyaml.emitter.*;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.reader.*;
import java.io.*;
import org.yaml.snakeyaml.composer.*;
import org.yaml.snakeyaml.parser.*;

public class Yaml
{
    protected final Resolver resolver;
    private String name;
    protected BaseConstructor constructor;
    protected Representer representer;
    protected DumperOptions dumperOptions;
    protected LoaderOptions loadingConfig;
    
    public Yaml() {
        this(new Constructor(), new Representer(), new DumperOptions(), new LoaderOptions(), new Resolver());
    }
    
    public Yaml(final BaseConstructor constructor, final Representer representer, final DumperOptions dumperOptions) {
        this(constructor, representer, dumperOptions, new LoaderOptions(), new Resolver());
    }
    
    public Yaml(final BaseConstructor constructor, final Representer representer, final DumperOptions dumperOptions, final LoaderOptions loadingConfig, final Resolver resolver) {
        super();
        if (!constructor.isExplicitPropertyUtils()) {
            constructor.setPropertyUtils(representer.getPropertyUtils());
        }
        else if (!representer.isExplicitPropertyUtils()) {
            representer.setPropertyUtils(constructor.getPropertyUtils());
        }
        (this.constructor = constructor).setAllowDuplicateKeys(loadingConfig.isAllowDuplicateKeys());
        if (dumperOptions.getIndent() <= dumperOptions.getIndicatorIndent()) {
            throw new YAMLException("Indicator indent must be smaller then indent.");
        }
        representer.setDefaultFlowStyle(dumperOptions.getDefaultFlowStyle());
        representer.setDefaultScalarStyle(dumperOptions.getDefaultScalarStyle());
        representer.getPropertyUtils().setAllowReadOnlyProperties(dumperOptions.isAllowReadOnlyProperties());
        representer.setTimeZone(dumperOptions.getTimeZone());
        this.representer = representer;
        this.dumperOptions = dumperOptions;
        this.loadingConfig = loadingConfig;
        this.resolver = resolver;
        this.name = "Yaml:" + System.identityHashCode(this);
    }
    
    public void dump(final Object data, final Writer output) {
        final List<Object> list = new ArrayList<Object>(1);
        list.add(data);
        this.dumpAll(list.iterator(), output, null);
    }
    
    private void dumpAll(final Iterator<?> data, final Writer output, final Tag rootTag) {
        final Serializer serializer = new Serializer(new Emitter(output, this.dumperOptions), this.resolver, this.dumperOptions, rootTag);
        try {
            serializer.open();
            while (data.hasNext()) {
                final Node node = this.representer.represent(data.next());
                serializer.serialize(node);
            }
            serializer.close();
        }
        catch (IOException e) {
            throw new YAMLException(e);
        }
    }
    
    public <T> T loadAs(final InputStream input, final Class<T> type) {
        return (T)this.loadFromReader(new StreamReader(new UnicodeReader(input)), type);
    }
    
    private Object loadFromReader(final StreamReader sreader, final Class<?> type) {
        final Composer composer = new Composer(new ParserImpl(sreader), this.resolver);
        this.constructor.setComposer(composer);
        return this.constructor.getSingleData(type);
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
