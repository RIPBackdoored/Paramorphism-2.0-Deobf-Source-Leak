package kotlin.jvm.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SpreadBuilder {
   private final ArrayList list;

   public SpreadBuilder(int var1) {
      super();
      this.list = new ArrayList(var1);
   }

   public void addSpread(Object var1) {
      if (var1 != null) {
         if (var1 instanceof Object[]) {
            Object[] var2 = (Object[])((Object[])var1);
            if (var2.length > 0) {
               this.list.ensureCapacity(this.list.size() + var2.length);
               Collections.addAll(this.list, var2);
            }
         } else if (var1 instanceof Collection) {
            this.list.addAll((Collection)var1);
         } else {
            Iterator var4;
            if (var1 instanceof Iterable) {
               var4 = ((Iterable)var1).iterator();

               while(var4.hasNext()) {
                  Object var3 = var4.next();
                  this.list.add(var3);
               }
            } else {
               if (!(var1 instanceof Iterator)) {
                  throw new UnsupportedOperationException("Don't know how to spread " + var1.getClass());
               }

               var4 = (Iterator)var1;

               while(var4.hasNext()) {
                  this.list.add(var4.next());
               }
            }
         }

      }
   }

   public int size() {
      return this.list.size();
   }

   public void add(Object var1) {
      this.list.add(var1);
   }

   public Object[] toArray(Object[] var1) {
      return this.list.toArray(var1);
   }
}
