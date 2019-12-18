package kotlin.jvm.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import kotlin.SinceKotlin;

@SinceKotlin(
   version = "1.2"
)
class CallableReference$NoReceiver implements Serializable {
   private static final CallableReference$NoReceiver INSTANCE = new CallableReference$NoReceiver();

   private CallableReference$NoReceiver() {
      super();
   }

   private Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }

   static CallableReference$NoReceiver access$000() {
      return INSTANCE;
   }
}
