package com.fasterxml.jackson.core.util;

import java.io.IOException;
import java.io.Serializable;

public class RequestPayload implements Serializable {
   private static final long serialVersionUID = 1L;
   protected byte[] _payloadAsBytes;
   protected CharSequence _payloadAsText;
   protected String _charset;

   public RequestPayload(byte[] var1, String var2) {
      super();
      if (var1 == null) {
         throw new IllegalArgumentException();
      } else {
         this._payloadAsBytes = var1;
         this._charset = var2 != null && !var2.isEmpty() ? var2 : "UTF-8";
      }
   }

   public RequestPayload(CharSequence var1) {
      super();
      if (var1 == null) {
         throw new IllegalArgumentException();
      } else {
         this._payloadAsText = var1;
      }
   }

   public Object getRawPayload() {
      return this._payloadAsBytes != null ? this._payloadAsBytes : this._payloadAsText;
   }

   public String toString() {
      if (this._payloadAsBytes != null) {
         String var10000;
         try {
            var10000 = new String(this._payloadAsBytes, this._charset);
         } catch (IOException var2) {
            throw new RuntimeException(var2);
         }

         return var10000;
      } else {
         return this._payloadAsText.toString();
      }
   }
}
