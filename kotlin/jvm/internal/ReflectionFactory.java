package kotlin.jvm.internal;

import java.util.List;
import kotlin.SinceKotlin;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KType;

public class ReflectionFactory {
   private static final String KOTLIN_JVM_FUNCTIONS = "kotlin.jvm.functions.";

   public ReflectionFactory() {
      super();
   }

   public KClass createKotlinClass(Class var1) {
      return new ClassReference(var1);
   }

   public KClass createKotlinClass(Class var1, String var2) {
      return new ClassReference(var1);
   }

   public KDeclarationContainer getOrCreateKotlinPackage(Class var1, String var2) {
      return new PackageReference(var1, var2);
   }

   public KClass getOrCreateKotlinClass(Class var1) {
      return new ClassReference(var1);
   }

   public KClass getOrCreateKotlinClass(Class var1, String var2) {
      return new ClassReference(var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   public String renderLambdaToString(Lambda var1) {
      return this.renderLambdaToString((FunctionBase)var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   public String renderLambdaToString(FunctionBase var1) {
      String var2 = var1.getClass().getGenericInterfaces()[0].toString();
      return var2.startsWith("kotlin.jvm.functions.") ? var2.substring("kotlin.jvm.functions.".length()) : var2;
   }

   public KFunction function(FunctionReference var1) {
      return var1;
   }

   public KProperty0 property0(PropertyReference0 var1) {
      return var1;
   }

   public KMutableProperty0 mutableProperty0(MutablePropertyReference0 var1) {
      return var1;
   }

   public KProperty1 property1(PropertyReference1 var1) {
      return var1;
   }

   public KMutableProperty1 mutableProperty1(MutablePropertyReference1 var1) {
      return var1;
   }

   public KProperty2 property2(PropertyReference2 var1) {
      return var1;
   }

   public KMutableProperty2 mutableProperty2(MutablePropertyReference2 var1) {
      return var1;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public KType typeOf(KClassifier var1, List var2, boolean var3) {
      return new TypeReference(var1, var2, var3);
   }
}
