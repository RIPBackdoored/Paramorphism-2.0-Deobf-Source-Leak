package kotlin.collections;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"},
   d2 = {"Lkotlin/collections/State;", "", "(Ljava/lang/String;I)V", "Ready", "NotReady", "Done", "Failed", "kotlin-stdlib"}
)
final class State extends Enum {
   public static final State Ready;
   public static final State NotReady;
   public static final State Done;
   public static final State Failed;
   private static final State[] $VALUES = new State[]{Ready = new State("Ready", 0), NotReady = new State("NotReady", 1), Done = new State("Done", 2), Failed = new State("Failed", 3)};

   private State(String var1, int var2) {
      super(var1, var2);
   }

   public static State[] values() {
      return (State[])$VALUES.clone();
   }

   public static State valueOf(String var0) {
      return (State)Enum.valueOf(State.class, var0);
   }
}
