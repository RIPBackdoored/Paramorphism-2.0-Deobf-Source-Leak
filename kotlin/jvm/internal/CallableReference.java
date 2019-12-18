package kotlin.jvm.internal;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import kotlin.SinceKotlin;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.reflect.KCallable;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KType;
import kotlin.reflect.KVisibility;

public abstract class CallableReference implements KCallable, Serializable {
   private transient KCallable reflected;
   @SinceKotlin(
      version = "1.1"
   )
   protected final Object receiver;
   @SinceKotlin(
      version = "1.1"
   )
   public static final Object NO_RECEIVER = CallableReference$NoReceiver.access$000();

   public CallableReference() {
      this(NO_RECEIVER);
   }

   @SinceKotlin(
      version = "1.1"
   )
   protected CallableReference(Object var1) {
      super();
      this.receiver = var1;
   }

   protected abstract KCallable computeReflected();

   @SinceKotlin(
      version = "1.1"
   )
   public Object getBoundReceiver() {
      return this.receiver;
   }

   @SinceKotlin(
      version = "1.1"
   )
   public KCallable compute() {
      KCallable var1 = this.reflected;
      if (var1 == null) {
         var1 = this.computeReflected();
         this.reflected = var1;
      }

      return var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   protected KCallable getReflected() {
      KCallable var1 = this.compute();
      if (var1 == this) {
         throw new KotlinReflectionNotSupportedError();
      } else {
         return var1;
      }
   }

   public KDeclarationContainer getOwner() {
      throw new AbstractMethodError();
   }

   public String getName() {
      throw new AbstractMethodError();
   }

   public String getSignature() {
      throw new AbstractMethodError();
   }

   public List getParameters() {
      return this.getReflected().getParameters();
   }

   public KType getReturnType() {
      return this.getReflected().getReturnType();
   }

   public List getAnnotations() {
      return this.getReflected().getAnnotations();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public List getTypeParameters() {
      return this.getReflected().getTypeParameters();
   }

   public Object call(Object... var1) {
      return this.getReflected().call(var1);
   }

   public Object callBy(Map var1) {
      return this.getReflected().callBy(var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   public KVisibility getVisibility() {
      return this.getReflected().getVisibility();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isFinal() {
      return this.getReflected().isFinal();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isOpen() {
      return this.getReflected().isOpen();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isAbstract() {
      return this.getReflected().isAbstract();
   }

   @SinceKotlin(
      version = "1.3"
   )
   public boolean isSuspend() {
      return this.getReflected().isSuspend();
   }
}
