package org.yaml.snakeyaml.representer;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeRepresenter$RepresentDate implements Represent {
   final SafeRepresenter this$0;

   protected SafeRepresenter$RepresentDate(SafeRepresenter var1) {
      super();
      this.this$0 = var1;
   }

   public Node representData(Object var1) {
      Calendar var2;
      if (var1 instanceof Calendar) {
         var2 = (Calendar)var1;
      } else {
         var2 = Calendar.getInstance(this.this$0.getTimeZone() == null ? TimeZone.getTimeZone("UTC") : this.this$0.timeZone);
         var2.setTime((Date)var1);
      }

      int var3 = var2.get(1);
      int var4 = var2.get(2) + 1;
      int var5 = var2.get(5);
      int var6 = var2.get(11);
      int var7 = var2.get(12);
      int var8 = var2.get(13);
      int var9 = var2.get(14);
      StringBuilder var10 = new StringBuilder(String.valueOf(var3));

      while(var10.length() < 4) {
         var10.insert(0, "0");
      }

      var10.append("-");
      if (var4 < 10) {
         var10.append("0");
      }

      var10.append(String.valueOf(var4));
      var10.append("-");
      if (var5 < 10) {
         var10.append("0");
      }

      var10.append(String.valueOf(var5));
      var10.append("T");
      if (var6 < 10) {
         var10.append("0");
      }

      var10.append(String.valueOf(var6));
      var10.append(":");
      if (var7 < 10) {
         var10.append("0");
      }

      var10.append(String.valueOf(var7));
      var10.append(":");
      if (var8 < 10) {
         var10.append("0");
      }

      var10.append(String.valueOf(var8));
      if (var9 > 0) {
         if (var9 < 10) {
            var10.append(".00");
         } else if (var9 < 100) {
            var10.append(".0");
         } else {
            var10.append(".");
         }

         var10.append(String.valueOf(var9));
      }

      if (TimeZone.getTimeZone("UTC").equals(var2.getTimeZone())) {
         var10.append("Z");
      } else {
         int var11 = var2.getTimeZone().getOffset(var2.get(0), var2.get(1), var2.get(2), var2.get(5), var2.get(7), var2.get(14));
         int var12 = var11 / '\uea60';
         int var13 = var12 / 60;
         int var14 = var12 % 60;
         var10.append((var13 > 0 ? "+" : "") + var13 + ":" + (var14 < 10 ? "0" + var14 : var14));
      }

      return this.this$0.representScalar(this.this$0.getTag(var1.getClass(), Tag.TIMESTAMP), var10.toString(), (Character)null);
   }
}
