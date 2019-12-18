package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.annotation.JsonValue;

public final class JsonValueFormat extends Enum {
   public static final JsonValueFormat COLOR = new JsonValueFormat("COLOR", 0, "color");
   public static final JsonValueFormat DATE = new JsonValueFormat("DATE", 1, "date");
   public static final JsonValueFormat DATE_TIME = new JsonValueFormat("DATE_TIME", 2, "date-time");
   public static final JsonValueFormat EMAIL = new JsonValueFormat("EMAIL", 3, "email");
   public static final JsonValueFormat HOST_NAME = new JsonValueFormat("HOST_NAME", 4, "host-name");
   public static final JsonValueFormat IP_ADDRESS = new JsonValueFormat("IP_ADDRESS", 5, "ip-address");
   public static final JsonValueFormat IPV6 = new JsonValueFormat("IPV6", 6, "ipv6");
   public static final JsonValueFormat PHONE = new JsonValueFormat("PHONE", 7, "phone");
   public static final JsonValueFormat REGEX = new JsonValueFormat("REGEX", 8, "regex");
   public static final JsonValueFormat STYLE = new JsonValueFormat("STYLE", 9, "style");
   public static final JsonValueFormat TIME = new JsonValueFormat("TIME", 10, "time");
   public static final JsonValueFormat URI = new JsonValueFormat("URI", 11, "uri");
   public static final JsonValueFormat UTC_MILLISEC = new JsonValueFormat("UTC_MILLISEC", 12, "utc-millisec");
   private final String _desc;
   private static final JsonValueFormat[] $VALUES = new JsonValueFormat[]{COLOR, DATE, DATE_TIME, EMAIL, HOST_NAME, IP_ADDRESS, IPV6, PHONE, REGEX, STYLE, TIME, URI, UTC_MILLISEC};

   public static JsonValueFormat[] values() {
      return (JsonValueFormat[])$VALUES.clone();
   }

   public static JsonValueFormat valueOf(String var0) {
      return (JsonValueFormat)Enum.valueOf(JsonValueFormat.class, var0);
   }

   private JsonValueFormat(String var1, int var2, String var3) {
      super(var1, var2);
      this._desc = var3;
   }

   @JsonValue
   public String toString() {
      return this._desc;
   }
}
