package paramorphism-obfuscator;

import org.jetbrains.annotations.*;
import paramorphism-obfuscator.access.*;

public interface IAccess
{
    @NotNull
    DefaultAccess getDefault();
    
    @NotNull
    PublicAccess getPublic();
    
    @NotNull
    PrivateAccess getPrivate();
    
    @NotNull
    ProtectedAccess getProtected();
    
    @NotNull
    StaticAccess getStatic();
    
    @NotNull
    FinalAccess getFinal();
    
    @NotNull
    SynchronizedAccess getSynchronized();
    
    @NotNull
    SynchronizedAccess getSynchronizedDuplicate();
    
    @NotNull
    OpenAccess getOpen();
    
    @NotNull
    SuperAccess getSuper();
    
    @NotNull
    TransitiveAccess getTransitive();
    
    @NotNull
    BridgeAccess getBridge();
    
    @NotNull
    StaticPhaseAccess getStaticPhase();
    
    @NotNull
    VolatileAccess getVolatile();
    
    @NotNull
    TransientAccess getTransient();
    
    @NotNull
    VarargsAccess getVarargs();
    
    @NotNull
    NativeAccess getNative();
    
    @NotNull
    InterfaceAccess getInterface();
    
    @NotNull
    InterfaceAccess getInterfaceDuplicate();
    
    @NotNull
    AbstractAccess getAbstract();
    
    @NotNull
    StrictAccess getStrict();
    
    @NotNull
    SyntheticAccess getSynthetic();
    
    @NotNull
    AnnotationAccess getAnnotation();
    
    @NotNull
    EnumAccess getEnum();
    
    @NotNull
    MandatedAccess getMandated();
    
    @NotNull
    ModuleAccess getModule();
    
    @NotNull
    DeprecatedAccess getDeprecated();
}
