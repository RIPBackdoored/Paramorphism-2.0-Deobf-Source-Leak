package com.fasterxml.jackson.databind.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;

/** @deprecated */
@Deprecated
public class ISO8601DateFormat extends DateFormat {
   private static final long serialVersionUID = 1L;

   public ISO8601DateFormat() {
      super();
      this.numberFormat = new DecimalFormat();
      this.calendar = new GregorianCalendar();
   }

   public StringBuffer format(Date var1, StringBuffer var2, FieldPosition var3) {
      var2.append(ISO8601Utils.format(var1));
      return var2;
   }

   public Date parse(String var1, ParsePosition var2) {
      Date var10000;
      try {
         var10000 = ISO8601Utils.parse(var1, var2);
      } catch (ParseException var4) {
         return null;
      }

      return var10000;
   }

   public Date parse(String var1) throws ParseException {
      return ISO8601Utils.parse(var1, new ParsePosition(0));
   }

   public Object clone() {
      return this;
   }
}
