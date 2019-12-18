package org.yaml.snakeyaml;

public final class DumperOptions$FlowStyle extends Enum {
   public static final DumperOptions$FlowStyle FLOW = new DumperOptions$FlowStyle("FLOW", 0, Boolean.TRUE);
   public static final DumperOptions$FlowStyle BLOCK = new DumperOptions$FlowStyle("BLOCK", 1, Boolean.FALSE);
   public static final DumperOptions$FlowStyle AUTO = new DumperOptions$FlowStyle("AUTO", 2, (Boolean)null);
   private Boolean styleBoolean;
   private static final DumperOptions$FlowStyle[] $VALUES = new DumperOptions$FlowStyle[]{FLOW, BLOCK, AUTO};

   public static DumperOptions$FlowStyle[] values() {
      return (DumperOptions$FlowStyle[])$VALUES.clone();
   }

   public static DumperOptions$FlowStyle valueOf(String var0) {
      return (DumperOptions$FlowStyle)Enum.valueOf(DumperOptions$FlowStyle.class, var0);
   }

   private DumperOptions$FlowStyle(String var1, int var2, Boolean var3) {
      super(var1, var2);
      this.styleBoolean = var3;
   }

   public Boolean getStyleBoolean() {
      return this.styleBoolean;
   }

   public String toString() {
      return "Flow style: '" + this.styleBoolean + "'";
   }
}
