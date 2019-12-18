package org.jline.reader;

import java.util.Objects;

public class Candidate implements Comparable {
   private final String value;
   private final String displ;
   private final String group;
   private final String descr;
   private final String suffix;
   private final String key;
   private final boolean complete;

   public Candidate(String var1) {
      this(var1, var1, (String)null, (String)null, (String)null, (String)null, true);
   }

   public Candidate(String var1, String var2, String var3, String var4, String var5, String var6, boolean var7) {
      super();
      Objects.requireNonNull(var1);
      this.value = var1;
      this.displ = var2;
      this.group = var3;
      this.descr = var4;
      this.suffix = var5;
      this.key = var6;
      this.complete = var7;
   }

   public String value() {
      return this.value;
   }

   public String displ() {
      return this.displ;
   }

   public String group() {
      return this.group;
   }

   public String descr() {
      return this.descr;
   }

   public String suffix() {
      return this.suffix;
   }

   public String key() {
      return this.key;
   }

   public boolean complete() {
      return this.complete;
   }

   public int compareTo(Candidate var1) {
      return this.value.compareTo(var1.value);
   }

   public int compareTo(Object var1) {
      return this.compareTo((Candidate)var1);
   }
}
