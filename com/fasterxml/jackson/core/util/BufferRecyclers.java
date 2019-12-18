package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import java.lang.ref.SoftReference;

public class BufferRecyclers {
   public static final String SYSTEM_PROPERTY_TRACK_REUSABLE_BUFFERS = "com.fasterxml.jackson.core.util.BufferRecyclers.trackReusableBuffers";
   private static final ThreadLocalBufferManager _bufferRecyclerTracker = "true".equals(System.getProperty("com.fasterxml.jackson.core.util.BufferRecyclers.trackReusableBuffers")) ? ThreadLocalBufferManager.instance() : null;
   protected static final ThreadLocal _recyclerRef = new ThreadLocal();
   protected static final ThreadLocal _encoderRef = new ThreadLocal();

   public BufferRecyclers() {
      super();
   }

   public static BufferRecycler getBufferRecycler() {
      SoftReference var0 = (SoftReference)_recyclerRef.get();
      BufferRecycler var1 = var0 == null ? null : (BufferRecycler)var0.get();
      if (var1 == null) {
         var1 = new BufferRecycler();
         if (_bufferRecyclerTracker != null) {
            var0 = _bufferRecyclerTracker.wrapAndTrack(var1);
         } else {
            var0 = new SoftReference(var1);
         }

         _recyclerRef.set(var0);
      }

      return var1;
   }

   public static int releaseBuffers() {
      return _bufferRecyclerTracker != null ? _bufferRecyclerTracker.releaseBuffers() : -1;
   }

   public static JsonStringEncoder getJsonStringEncoder() {
      SoftReference var0 = (SoftReference)_encoderRef.get();
      JsonStringEncoder var1 = var0 == null ? null : (JsonStringEncoder)var0.get();
      if (var1 == null) {
         var1 = new JsonStringEncoder();
         _encoderRef.set(new SoftReference(var1));
      }

      return var1;
   }

   public static byte[] encodeAsUTF8(String var0) {
      return getJsonStringEncoder().encodeAsUTF8(var0);
   }

   public static char[] quoteAsJsonText(String var0) {
      return getJsonStringEncoder().quoteAsString(var0);
   }

   public static void quoteAsJsonText(CharSequence var0, StringBuilder var1) {
      getJsonStringEncoder().quoteAsString(var0, var1);
   }

   public static byte[] quoteAsJsonUTF8(String var0) {
      return getJsonStringEncoder().quoteAsUTF8(var0);
   }
}
