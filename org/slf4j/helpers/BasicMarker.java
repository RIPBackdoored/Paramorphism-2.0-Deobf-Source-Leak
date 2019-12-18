package org.slf4j.helpers;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Marker;

public class BasicMarker implements Marker {
   private static final long serialVersionUID = -2849567615646933777L;
   private final String name;
   private List referenceList = new CopyOnWriteArrayList();
   private static String OPEN = "[ ";
   private static String CLOSE = " ]";
   private static String SEP = ", ";

   BasicMarker(String var1) {
      super();
      if (var1 == null) {
         throw new IllegalArgumentException("A marker name cannot be null");
      } else {
         this.name = var1;
      }
   }

   public String getName() {
      return this.name;
   }

   public void add(Marker var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("A null value cannot be added to a Marker as reference.");
      } else if (!this.contains(var1)) {
         if (!var1.contains((Marker)this)) {
            this.referenceList.add(var1);
         }
      }
   }

   public boolean hasReferences() {
      return this.referenceList.size() > 0;
   }

   public boolean hasChildren() {
      return this.hasReferences();
   }

   public Iterator iterator() {
      return this.referenceList.iterator();
   }

   public boolean remove(Marker var1) {
      return this.referenceList.remove(var1);
   }

   public boolean contains(Marker var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Other cannot be null");
      } else if (this.equals(var1)) {
         return true;
      } else {
         if (this.hasReferences()) {
            Iterator var2 = this.referenceList.iterator();

            while(var2.hasNext()) {
               Marker var3 = (Marker)var2.next();
               if (var3.contains(var1)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean contains(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Other cannot be null");
      } else if (this.name.equals(var1)) {
         return true;
      } else {
         if (this.hasReferences()) {
            Iterator var2 = this.referenceList.iterator();

            while(var2.hasNext()) {
               Marker var3 = (Marker)var2.next();
               if (var3.contains(var1)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!(var1 instanceof Marker)) {
         return false;
      } else {
         Marker var2 = (Marker)var1;
         return this.name.equals(var2.getName());
      }
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public String toString() {
      if (!this.hasReferences()) {
         return this.getName();
      } else {
         Iterator var1 = this.iterator();
         StringBuilder var3 = new StringBuilder(this.getName());
         var3.append(' ').append(OPEN);

         while(var1.hasNext()) {
            Marker var2 = (Marker)var1.next();
            var3.append(var2.getName());
            if (var1.hasNext()) {
               var3.append(SEP);
            }
         }

         var3.append(CLOSE);
         return var3.toString();
      }
   }
}
