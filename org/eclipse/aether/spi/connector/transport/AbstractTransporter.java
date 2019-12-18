package org.eclipse.aether.spi.connector.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.aether.transfer.TransferCancelledException;

public abstract class AbstractTransporter implements Transporter {
   private final AtomicBoolean closed = new AtomicBoolean();

   protected AbstractTransporter() {
      super();
   }

   public void peek(PeekTask var1) throws Exception {
      this.failIfClosed(var1);
      this.implPeek(var1);
   }

   protected abstract void implPeek(PeekTask var1) throws Exception;

   public void get(GetTask var1) throws Exception {
      this.failIfClosed(var1);
      this.implGet(var1);
   }

   protected abstract void implGet(GetTask var1) throws Exception;

   protected void utilGet(GetTask var1, InputStream var2, boolean var3, long var4, boolean var6) throws IOException, TransferCancelledException {
      OutputStream var7 = null;
      boolean var50 = false;

      try {
         var50 = true;
         var7 = var1.newOutputStream(var6);
         var1.getListener().transportStarted(var6 ? var1.getResumeOffset() : 0L, var4);
         copy(var7, var2, var1.getListener());
         var7.close();
         var7 = null;
         if (var3) {
            var2.close();
            var2 = null;
            var50 = false;
         } else {
            var50 = false;
         }
      } finally {
         if (var50) {
            boolean var38 = false;

            label415: {
               label414: {
                  try {
                     var38 = true;
                     if (var7 != null) {
                        var7.close();
                        var38 = false;
                     } else {
                        var38 = false;
                     }
                     break label414;
                  } catch (IOException var57) {
                     var38 = false;
                  } finally {
                     if (var38) {
                        try {
                           if (var3 && var2 != null) {
                              var2.close();
                           }
                        } catch (IOException var51) {
                        }

                     }
                  }

                  try {
                     if (var3 && var2 != null) {
                        var2.close();
                     }
                  } catch (IOException var53) {
                  }
                  break label415;
               }

               try {
                  if (var3 && var2 != null) {
                     var2.close();
                  }
               } catch (IOException var54) {
               }
            }

         }
      }

      boolean var26 = false;

      label439: {
         try {
            var26 = true;
            if (var7 != null) {
               var7.close();
               var26 = false;
            } else {
               var26 = false;
            }
            break label439;
         } catch (IOException var60) {
            var26 = false;
         } finally {
            if (var26) {
               try {
                  if (var3 && var2 != null) {
                     var2.close();
                  }
               } catch (IOException var52) {
               }

            }
         }

         try {
            if (var3 && var2 != null) {
               var2.close();
               return;
            }
         } catch (IOException var55) {
         }

         return;
      }

      try {
         if (var3 && var2 != null) {
            var2.close();
         }
      } catch (IOException var56) {
      }

   }

   public void put(PutTask var1) throws Exception {
      this.failIfClosed(var1);
      this.implPut(var1);
   }

   protected abstract void implPut(PutTask var1) throws Exception;

   protected void utilPut(PutTask var1, OutputStream var2, boolean var3) throws IOException, TransferCancelledException {
      InputStream var4 = null;
      boolean var47 = false;

      try {
         var47 = true;
         var1.getListener().transportStarted(0L, var1.getDataLength());
         var4 = var1.newInputStream();
         copy(var2, var4, var1.getListener());
         if (var3) {
            var2.close();
         } else {
            var2.flush();
         }

         var2 = null;
         var4.close();
         var4 = null;
         var47 = false;
      } finally {
         if (var47) {
            boolean var35 = false;

            label373: {
               label372: {
                  try {
                     var35 = true;
                     if (var3) {
                        if (var2 != null) {
                           var2.close();
                           var35 = false;
                        } else {
                           var35 = false;
                        }
                     } else {
                        var35 = false;
                     }
                     break label372;
                  } catch (IOException var54) {
                     var35 = false;
                  } finally {
                     if (var35) {
                        try {
                           if (var4 != null) {
                              var4.close();
                           }
                        } catch (IOException var48) {
                        }

                     }
                  }

                  try {
                     if (var4 != null) {
                        var4.close();
                     }
                  } catch (IOException var49) {
                  }
                  break label373;
               }

               try {
                  if (var4 != null) {
                     var4.close();
                  }
               } catch (IOException var50) {
               }
            }

         }
      }

      boolean var23 = false;

      label395: {
         try {
            var23 = true;
            if (var3) {
               if (var2 != null) {
                  var2.close();
                  var23 = false;
               } else {
                  var23 = false;
               }
            } else {
               var23 = false;
            }
            break label395;
         } catch (IOException var57) {
            var23 = false;
         } finally {
            if (var23) {
               try {
                  if (var4 != null) {
                     var4.close();
                  }
               } catch (IOException var51) {
               }

            }
         }

         try {
            if (var4 != null) {
               var4.close();
               return;
            }
         } catch (IOException var52) {
         }

         return;
      }

      try {
         if (var4 != null) {
            var4.close();
         }
      } catch (IOException var53) {
      }

   }

   public void close() {
      if (this.closed.compareAndSet(false, true)) {
         this.implClose();
      }

   }

   protected abstract void implClose();

   private void failIfClosed(TransportTask var1) {
      if (this.closed.get()) {
         throw new IllegalStateException("transporter closed, cannot execute task " + var1);
      }
   }

   private static void copy(OutputStream var0, InputStream var1, TransportListener var2) throws IOException, TransferCancelledException {
      ByteBuffer var3 = ByteBuffer.allocate(32768);
      byte[] var4 = var3.array();

      for(int var5 = var1.read(var4); var5 >= 0; var5 = var1.read(var4)) {
         var0.write(var4, 0, var5);
         var3.rewind();
         var3.limit(var5);
         var2.transportProgressed(var3);
      }

   }
}
