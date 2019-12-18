package org.yaml.snakeyaml.constructor;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class SafeConstructor$ConstructYamlTimestamp extends AbstractConstruct {
   private Calendar calendar;

   public SafeConstructor$ConstructYamlTimestamp() {
      super();
   }

   public Calendar getCalendar() {
      return this.calendar;
   }

   public Object construct(Node var1) {
      ScalarNode var2 = (ScalarNode)var1;
      String var3 = var2.getValue();
      Matcher var4 = SafeConstructor.access$200().matcher(var3);
      String var5;
      String var6;
      String var7;
      if (var4.matches()) {
         var5 = var4.group(1);
         var6 = var4.group(2);
         var7 = var4.group(3);
         this.calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
         this.calendar.clear();
         this.calendar.set(1, Integer.parseInt(var5));
         this.calendar.set(2, Integer.parseInt(var6) - 1);
         this.calendar.set(5, Integer.parseInt(var7));
         return this.calendar.getTime();
      } else {
         var4 = SafeConstructor.access$300().matcher(var3);
         if (!var4.matches()) {
            throw new YAMLException("Unexpected timestamp: " + var3);
         } else {
            var5 = var4.group(1);
            var6 = var4.group(2);
            var7 = var4.group(3);
            String var8 = var4.group(4);
            String var9 = var4.group(5);
            String var10 = var4.group(6);
            String var11 = var4.group(7);
            if (var11 != null) {
               var10 = var10 + "." + var11;
            }

            double var12 = Double.parseDouble(var10);
            int var14 = (int)Math.round(Math.floor(var12));
            int var15 = (int)Math.round((var12 - (double)var14) * 1000.0D);
            String var16 = var4.group(8);
            String var17 = var4.group(9);
            TimeZone var18;
            if (var16 != null) {
               String var19 = var17 != null ? ":" + var17 : "00";
               var18 = TimeZone.getTimeZone("GMT" + var16 + var19);
            } else {
               var18 = TimeZone.getTimeZone("UTC");
            }

            this.calendar = Calendar.getInstance(var18);
            this.calendar.set(1, Integer.parseInt(var5));
            this.calendar.set(2, Integer.parseInt(var6) - 1);
            this.calendar.set(5, Integer.parseInt(var7));
            this.calendar.set(11, Integer.parseInt(var8));
            this.calendar.set(12, Integer.parseInt(var9));
            this.calendar.set(13, var14);
            this.calendar.set(14, var15);
            return this.calendar.getTime();
         }
      }
   }
}
