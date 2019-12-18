package org.yaml.snakeyaml.representer;

import java.util.ArrayList;
import java.util.List;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeRepresenter$RepresentPrimitiveArray implements Represent {
   final SafeRepresenter this$0;

   protected SafeRepresenter$RepresentPrimitiveArray(SafeRepresenter var1) {
      super();
      this.this$0 = var1;
   }

   public Node representData(Object var1) {
      Class var2 = var1.getClass().getComponentType();
      if (Byte.TYPE == var2) {
         return this.this$0.representSequence(Tag.SEQ, this.asByteList(var1), (Boolean)null);
      } else if (Short.TYPE == var2) {
         return this.this$0.representSequence(Tag.SEQ, this.asShortList(var1), (Boolean)null);
      } else if (Integer.TYPE == var2) {
         return this.this$0.representSequence(Tag.SEQ, this.asIntList(var1), (Boolean)null);
      } else if (Long.TYPE == var2) {
         return this.this$0.representSequence(Tag.SEQ, this.asLongList(var1), (Boolean)null);
      } else if (Float.TYPE == var2) {
         return this.this$0.representSequence(Tag.SEQ, this.asFloatList(var1), (Boolean)null);
      } else if (Double.TYPE == var2) {
         return this.this$0.representSequence(Tag.SEQ, this.asDoubleList(var1), (Boolean)null);
      } else if (Character.TYPE == var2) {
         return this.this$0.representSequence(Tag.SEQ, this.asCharList(var1), (Boolean)null);
      } else if (Boolean.TYPE == var2) {
         return this.this$0.representSequence(Tag.SEQ, this.asBooleanList(var1), (Boolean)null);
      } else {
         throw new YAMLException("Unexpected primitive '" + var2.getCanonicalName() + "'");
      }
   }

   private List asByteList(Object var1) {
      byte[] var2 = (byte[])((byte[])var1);
      ArrayList var3 = new ArrayList(var2.length);

      for(int var4 = 0; var4 < var2.length; ++var4) {
         var3.add(var2[var4]);
      }

      return var3;
   }

   private List asShortList(Object var1) {
      short[] var2 = (short[])((short[])var1);
      ArrayList var3 = new ArrayList(var2.length);

      for(int var4 = 0; var4 < var2.length; ++var4) {
         var3.add(var2[var4]);
      }

      return var3;
   }

   private List asIntList(Object var1) {
      int[] var2 = (int[])((int[])var1);
      ArrayList var3 = new ArrayList(var2.length);

      for(int var4 = 0; var4 < var2.length; ++var4) {
         var3.add(var2[var4]);
      }

      return var3;
   }

   private List asLongList(Object var1) {
      long[] var2 = (long[])((long[])var1);
      ArrayList var3 = new ArrayList(var2.length);

      for(int var4 = 0; var4 < var2.length; ++var4) {
         var3.add(var2[var4]);
      }

      return var3;
   }

   private List asFloatList(Object var1) {
      float[] var2 = (float[])((float[])var1);
      ArrayList var3 = new ArrayList(var2.length);

      for(int var4 = 0; var4 < var2.length; ++var4) {
         var3.add(var2[var4]);
      }

      return var3;
   }

   private List asDoubleList(Object var1) {
      double[] var2 = (double[])((double[])var1);
      ArrayList var3 = new ArrayList(var2.length);

      for(int var4 = 0; var4 < var2.length; ++var4) {
         var3.add(var2[var4]);
      }

      return var3;
   }

   private List asCharList(Object var1) {
      char[] var2 = (char[])((char[])var1);
      ArrayList var3 = new ArrayList(var2.length);

      for(int var4 = 0; var4 < var2.length; ++var4) {
         var3.add(var2[var4]);
      }

      return var3;
   }

   private List asBooleanList(Object var1) {
      boolean[] var2 = (boolean[])((boolean[])var1);
      ArrayList var3 = new ArrayList(var2.length);

      for(int var4 = 0; var4 < var2.length; ++var4) {
         var3.add(var2[var4]);
      }

      return var3;
   }
}
