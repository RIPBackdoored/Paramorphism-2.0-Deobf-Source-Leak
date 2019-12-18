package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.PropertyName;

public final class POJOPropertyBuilder$Linked {
   public final Object value;
   public final POJOPropertyBuilder$Linked next;
   public final PropertyName name;
   public final boolean isNameExplicit;
   public final boolean isVisible;
   public final boolean isMarkedIgnored;

   public POJOPropertyBuilder$Linked(Object var1, POJOPropertyBuilder$Linked var2, PropertyName var3, boolean var4, boolean var5, boolean var6) {
      super();
      this.value = var1;
      this.next = var2;
      this.name = var3 != null && !var3.isEmpty() ? var3 : null;
      if (var4) {
         if (this.name == null) {
            throw new IllegalArgumentException("Cannot pass true for 'explName' if name is null/empty");
         }

         if (!var3.hasSimpleName()) {
            var4 = false;
         }
      }

      this.isNameExplicit = var4;
      this.isVisible = var5;
      this.isMarkedIgnored = var6;
   }

   public POJOPropertyBuilder$Linked withoutNext() {
      return this.next == null ? this : new POJOPropertyBuilder$Linked(this.value, (POJOPropertyBuilder$Linked)null, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
   }

   public POJOPropertyBuilder$Linked withValue(Object var1) {
      return var1 == this.value ? this : new POJOPropertyBuilder$Linked(var1, this.next, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
   }

   public POJOPropertyBuilder$Linked withNext(POJOPropertyBuilder$Linked var1) {
      return var1 == this.next ? this : new POJOPropertyBuilder$Linked(this.value, var1, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
   }

   public POJOPropertyBuilder$Linked withoutIgnored() {
      if (this.isMarkedIgnored) {
         return this.next == null ? null : this.next.withoutIgnored();
      } else {
         if (this.next != null) {
            POJOPropertyBuilder$Linked var1 = this.next.withoutIgnored();
            if (var1 != this.next) {
               return this.withNext(var1);
            }
         }

         return this;
      }
   }

   public POJOPropertyBuilder$Linked withoutNonVisible() {
      POJOPropertyBuilder$Linked var1 = this.next == null ? null : this.next.withoutNonVisible();
      return this.isVisible ? this.withNext(var1) : var1;
   }

   protected POJOPropertyBuilder$Linked append(POJOPropertyBuilder$Linked var1) {
      return this.next == null ? this.withNext(var1) : this.withNext(this.next.append(var1));
   }

   public POJOPropertyBuilder$Linked trimByVisibility() {
      if (this.next == null) {
         return this;
      } else {
         POJOPropertyBuilder$Linked var1 = this.next.trimByVisibility();
         if (this.name != null) {
            return var1.name == null ? this.withNext((POJOPropertyBuilder$Linked)null) : this.withNext(var1);
         } else if (var1.name != null) {
            return var1;
         } else if (this.isVisible == var1.isVisible) {
            return this.withNext(var1);
         } else {
            return this.isVisible ? this.withNext((POJOPropertyBuilder$Linked)null) : var1;
         }
      }
   }

   public String toString() {
      String var1 = String.format("%s[visible=%b,ignore=%b,explicitName=%b]", this.value.toString(), this.isVisible, this.isMarkedIgnored, this.isNameExplicit);
      if (this.next != null) {
         var1 = var1 + ", " + this.next.toString();
      }

      return var1;
   }
}
