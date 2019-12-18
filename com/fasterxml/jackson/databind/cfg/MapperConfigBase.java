package com.fasterxml.jackson.databind.cfg;

import java.io.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.core.*;
import java.text.*;
import java.util.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

public abstract class MapperConfigBase<CFG extends ConfigFeature, T extends MapperConfigBase<CFG, T>> extends MapperConfig<T> implements Serializable
{
    protected static final ConfigOverride EMPTY_OVERRIDE;
    private static final int DEFAULT_MAPPER_FEATURES;
    private static final int AUTO_DETECT_MASK;
    protected final SimpleMixInResolver _mixIns;
    protected final SubtypeResolver _subtypeResolver;
    protected final PropertyName _rootName;
    protected final Class<?> _view;
    protected final ContextAttributes _attributes;
    protected final RootNameLookup _rootNames;
    protected final ConfigOverrides _configOverrides;
    
    protected MapperConfigBase(final BaseSettings baseSettings, final SubtypeResolver subtypeResolver, final SimpleMixInResolver mixIns, final RootNameLookup rootNames, final ConfigOverrides configOverrides) {
        super(baseSettings, MapperConfigBase.DEFAULT_MAPPER_FEATURES);
        this._mixIns = mixIns;
        this._subtypeResolver = subtypeResolver;
        this._rootNames = rootNames;
        this._rootName = null;
        this._view = null;
        this._attributes = ContextAttributes.getEmpty();
        this._configOverrides = configOverrides;
    }
    
    protected MapperConfigBase(final MapperConfigBase<CFG, T> mapperConfigBase, final SimpleMixInResolver mixIns, final RootNameLookup rootNames, final ConfigOverrides configOverrides) {
        super(mapperConfigBase, mapperConfigBase._base.copy());
        this._mixIns = mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = configOverrides;
    }
    
    protected MapperConfigBase(final MapperConfigBase<CFG, T> mapperConfigBase) {
        super(mapperConfigBase);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
    }
    
    protected MapperConfigBase(final MapperConfigBase<CFG, T> mapperConfigBase, final BaseSettings baseSettings) {
        super(mapperConfigBase, baseSettings);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
    }
    
    protected MapperConfigBase(final MapperConfigBase<CFG, T> mapperConfigBase, final int n) {
        super(mapperConfigBase, n);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
    }
    
    protected MapperConfigBase(final MapperConfigBase<CFG, T> mapperConfigBase, final SubtypeResolver subtypeResolver) {
        super(mapperConfigBase);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
    }
    
    protected MapperConfigBase(final MapperConfigBase<CFG, T> mapperConfigBase, final PropertyName rootName) {
        super(mapperConfigBase);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
    }
    
    protected MapperConfigBase(final MapperConfigBase<CFG, T> mapperConfigBase, final Class<?> view) {
        super(mapperConfigBase);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
    }
    
    protected MapperConfigBase(final MapperConfigBase<CFG, T> mapperConfigBase, final SimpleMixInResolver mixIns) {
        super(mapperConfigBase);
        this._mixIns = mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
    }
    
    protected MapperConfigBase(final MapperConfigBase<CFG, T> mapperConfigBase, final ContextAttributes attributes) {
        super(mapperConfigBase);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
    }
    
    protected abstract T _withBase(final BaseSettings p0);
    
    protected abstract T _withMapperFeatures(final int p0);
    
    @Override
    public final T with(final MapperFeature... array) {
        int mapperFeatures = this._mapperFeatures;
        for (int length = array.length, i = 0; i < length; ++i) {
            mapperFeatures |= array[i].getMask();
        }
        if (mapperFeatures == this._mapperFeatures) {
            return (T)this;
        }
        return this._withMapperFeatures(mapperFeatures);
    }
    
    @Override
    public final T without(final MapperFeature... array) {
        int mapperFeatures = this._mapperFeatures;
        for (int length = array.length, i = 0; i < length; ++i) {
            mapperFeatures &= ~array[i].getMask();
        }
        if (mapperFeatures == this._mapperFeatures) {
            return (T)this;
        }
        return this._withMapperFeatures(mapperFeatures);
    }
    
    @Override
    public final T with(final MapperFeature mapperFeature, final boolean b) {
        int n;
        if (b) {
            n = (this._mapperFeatures | mapperFeature.getMask());
        }
        else {
            n = (this._mapperFeatures & ~mapperFeature.getMask());
        }
        if (n == this._mapperFeatures) {
            return (T)this;
        }
        return this._withMapperFeatures(n);
    }
    
    public final T with(final AnnotationIntrospector annotationIntrospector) {
        return this._withBase(this._base.withAnnotationIntrospector(annotationIntrospector));
    }
    
    public final T withAppendedAnnotationIntrospector(final AnnotationIntrospector annotationIntrospector) {
        return this._withBase(this._base.withAppendedAnnotationIntrospector(annotationIntrospector));
    }
    
    public final T withInsertedAnnotationIntrospector(final AnnotationIntrospector annotationIntrospector) {
        return this._withBase(this._base.withInsertedAnnotationIntrospector(annotationIntrospector));
    }
    
    public final T with(final ClassIntrospector classIntrospector) {
        return this._withBase(this._base.withClassIntrospector(classIntrospector));
    }
    
    public abstract T with(final ContextAttributes p0);
    
    public T withAttributes(final Map<?, ?> map) {
        return this.with(this.getAttributes().withSharedAttributes(map));
    }
    
    public T withAttribute(final Object o, final Object o2) {
        return this.with(this.getAttributes().withSharedAttribute(o, o2));
    }
    
    public T withoutAttribute(final Object o) {
        return this.with(this.getAttributes().withoutSharedAttribute(o));
    }
    
    public final T with(final TypeFactory typeFactory) {
        return this._withBase(this._base.withTypeFactory(typeFactory));
    }
    
    public final T with(final TypeResolverBuilder<?> typeResolverBuilder) {
        return this._withBase(this._base.withTypeResolverBuilder(typeResolverBuilder));
    }
    
    public final T with(final PropertyNamingStrategy propertyNamingStrategy) {
        return this._withBase(this._base.withPropertyNamingStrategy(propertyNamingStrategy));
    }
    
    public final T with(final HandlerInstantiator handlerInstantiator) {
        return this._withBase(this._base.withHandlerInstantiator(handlerInstantiator));
    }
    
    public final T with(final Base64Variant base64Variant) {
        return this._withBase(this._base.with(base64Variant));
    }
    
    public T with(final DateFormat dateFormat) {
        return this._withBase(this._base.withDateFormat(dateFormat));
    }
    
    public final T with(final Locale locale) {
        return this._withBase(this._base.with(locale));
    }
    
    public final T with(final TimeZone timeZone) {
        return this._withBase(this._base.with(timeZone));
    }
    
    public abstract T withRootName(final PropertyName p0);
    
    public T withRootName(final String s) {
        if (s == null) {
            return this.withRootName((PropertyName)null);
        }
        return this.withRootName(PropertyName.construct(s));
    }
    
    public abstract T with(final SubtypeResolver p0);
    
    public abstract T withView(final Class<?> p0);
    
    @Override
    public final SubtypeResolver getSubtypeResolver() {
        return this._subtypeResolver;
    }
    
    @Deprecated
    public final String getRootName() {
        return (this._rootName == null) ? null : this._rootName.getSimpleName();
    }
    
    public final PropertyName getFullRootName() {
        return this._rootName;
    }
    
    @Override
    public final Class<?> getActiveView() {
        return this._view;
    }
    
    @Override
    public final ContextAttributes getAttributes() {
        return this._attributes;
    }
    
    @Override
    public final ConfigOverride getConfigOverride(final Class<?> clazz) {
        final ConfigOverride override = this._configOverrides.findOverride(clazz);
        return (override == null) ? MapperConfigBase.EMPTY_OVERRIDE : override;
    }
    
    @Override
    public final ConfigOverride findConfigOverride(final Class<?> clazz) {
        return this._configOverrides.findOverride(clazz);
    }
    
    @Override
    public final JsonInclude.Value getDefaultPropertyInclusion() {
        return this._configOverrides.getDefaultInclusion();
    }
    
    @Override
    public final JsonInclude.Value getDefaultPropertyInclusion(final Class<?> clazz) {
        final JsonInclude.Value include = this.getConfigOverride(clazz).getInclude();
        final JsonInclude.Value defaultPropertyInclusion = this.getDefaultPropertyInclusion();
        if (defaultPropertyInclusion == null) {
            return include;
        }
        return defaultPropertyInclusion.withOverrides(include);
    }
    
    @Override
    public final JsonInclude.Value getDefaultInclusion(final Class<?> clazz, final Class<?> clazz2) {
        final JsonInclude.Value includeAsProperty = this.getConfigOverride(clazz2).getIncludeAsProperty();
        final JsonInclude.Value defaultPropertyInclusion = this.getDefaultPropertyInclusion(clazz);
        if (defaultPropertyInclusion == null) {
            return includeAsProperty;
        }
        return defaultPropertyInclusion.withOverrides(includeAsProperty);
    }
    
    @Override
    public final JsonFormat.Value getDefaultPropertyFormat(final Class<?> clazz) {
        final ConfigOverride override = this._configOverrides.findOverride(clazz);
        if (override != null) {
            final JsonFormat.Value format = override.getFormat();
            if (format != null) {
                return format;
            }
        }
        return MapperConfigBase.EMPTY_FORMAT;
    }
    
    @Override
    public final JsonIgnoreProperties.Value getDefaultPropertyIgnorals(final Class<?> clazz) {
        final ConfigOverride override = this._configOverrides.findOverride(clazz);
        if (override != null) {
            final JsonIgnoreProperties.Value ignorals = override.getIgnorals();
            if (ignorals != null) {
                return ignorals;
            }
        }
        return null;
    }
    
    @Override
    public final JsonIgnoreProperties.Value getDefaultPropertyIgnorals(final Class<?> clazz, final AnnotatedClass annotatedClass) {
        final AnnotationIntrospector annotationIntrospector = this.getAnnotationIntrospector();
        return JsonIgnoreProperties.Value.merge((annotationIntrospector == null) ? null : annotationIntrospector.findPropertyIgnorals(annotatedClass), this.getDefaultPropertyIgnorals(clazz));
    }
    
    @Override
    public final VisibilityChecker<?> getDefaultVisibilityChecker() {
        Object o = this._configOverrides.getDefaultVisibility();
        if ((this._mapperFeatures & MapperConfigBase.AUTO_DETECT_MASK) != MapperConfigBase.AUTO_DETECT_MASK) {
            if (!this.isEnabled(MapperFeature.AUTO_DETECT_FIELDS)) {
                o = ((VisibilityChecker<?>)o).withFieldVisibility(JsonAutoDetect.Visibility.NONE);
            }
            if (!this.isEnabled(MapperFeature.AUTO_DETECT_GETTERS)) {
                o = ((VisibilityChecker<?>)o).withGetterVisibility(JsonAutoDetect.Visibility.NONE);
            }
            if (!this.isEnabled(MapperFeature.AUTO_DETECT_IS_GETTERS)) {
                o = ((VisibilityChecker<?>)o).withIsGetterVisibility(JsonAutoDetect.Visibility.NONE);
            }
            if (!this.isEnabled(MapperFeature.AUTO_DETECT_SETTERS)) {
                o = ((VisibilityChecker<?>)o).withSetterVisibility(JsonAutoDetect.Visibility.NONE);
            }
            if (!this.isEnabled(MapperFeature.AUTO_DETECT_CREATORS)) {
                o = ((VisibilityChecker<?>)o).withCreatorVisibility(JsonAutoDetect.Visibility.NONE);
            }
        }
        return (VisibilityChecker<?>)o;
    }
    
    @Override
    public final VisibilityChecker<?> getDefaultVisibilityChecker(final Class<?> clazz, final AnnotatedClass annotatedClass) {
        Object o = this.getDefaultVisibilityChecker();
        final AnnotationIntrospector annotationIntrospector = this.getAnnotationIntrospector();
        if (annotationIntrospector != null) {
            o = annotationIntrospector.findAutoDetectVisibility(annotatedClass, (VisibilityChecker<?>)o);
        }
        final ConfigOverride override = this._configOverrides.findOverride(clazz);
        if (override != null) {
            o = ((VisibilityChecker<?>)o).withOverrides(override.getVisibility());
        }
        return (VisibilityChecker<?>)o;
    }
    
    @Override
    public final JsonSetter.Value getDefaultSetterInfo() {
        return this._configOverrides.getDefaultSetterInfo();
    }
    
    @Override
    public Boolean getDefaultMergeable() {
        return this._configOverrides.getDefaultMergeable();
    }
    
    @Override
    public Boolean getDefaultMergeable(final Class<?> clazz) {
        final ConfigOverride override = this._configOverrides.findOverride(clazz);
        if (override != null) {
            final Boolean mergeable = override.getMergeable();
            if (mergeable != null) {
                return mergeable;
            }
        }
        return this._configOverrides.getDefaultMergeable();
    }
    
    @Override
    public PropertyName findRootName(final JavaType javaType) {
        if (this._rootName != null) {
            return this._rootName;
        }
        return this._rootNames.findRootName(javaType, this);
    }
    
    @Override
    public PropertyName findRootName(final Class<?> clazz) {
        if (this._rootName != null) {
            return this._rootName;
        }
        return this._rootNames.findRootName(clazz, this);
    }
    
    @Override
    public final Class<?> findMixInClassFor(final Class<?> clazz) {
        return this._mixIns.findMixInClassFor(clazz);
    }
    
    @Override
    public ClassIntrospector.MixInResolver copy() {
        throw new UnsupportedOperationException();
    }
    
    public final int mixInCount() {
        return this._mixIns.localSize();
    }
    
    @Override
    public MapperConfig with(final MapperFeature mapperFeature, final boolean b) {
        return this.with(mapperFeature, b);
    }
    
    @Override
    public MapperConfig without(final MapperFeature[] array) {
        return this.without(array);
    }
    
    @Override
    public MapperConfig with(final MapperFeature[] array) {
        return this.with(array);
    }
    
    static {
        EMPTY_OVERRIDE = ConfigOverride.empty();
        DEFAULT_MAPPER_FEATURES = MapperConfig.collectFeatureDefaults(MapperFeature.class);
        AUTO_DETECT_MASK = (MapperFeature.AUTO_DETECT_FIELDS.getMask() | MapperFeature.AUTO_DETECT_GETTERS.getMask() | MapperFeature.AUTO_DETECT_IS_GETTERS.getMask() | MapperFeature.AUTO_DETECT_SETTERS.getMask() | MapperFeature.AUTO_DETECT_CREATORS.getMask());
    }
}
