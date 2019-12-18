package org.yaml.snakeyaml;

import java.util.Map;
import java.util.TimeZone;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.serializer.AnchorGenerator;
import org.yaml.snakeyaml.serializer.NumberAnchorGenerator;

public class DumperOptions {
   private DumperOptions$ScalarStyle defaultStyle;
   private DumperOptions$FlowStyle defaultFlowStyle;
   private boolean canonical;
   private boolean allowUnicode;
   private boolean allowReadOnlyProperties;
   private int indent;
   private int indicatorIndent;
   private int bestWidth;
   private boolean splitLines;
   private DumperOptions$LineBreak lineBreak;
   private boolean explicitStart;
   private boolean explicitEnd;
   private TimeZone timeZone;
   private DumperOptions$Version version;
   private Map tags;
   private Boolean prettyFlow;
   private AnchorGenerator anchorGenerator;

   public DumperOptions() {
      super();
      this.defaultStyle = DumperOptions$ScalarStyle.PLAIN;
      this.defaultFlowStyle = DumperOptions$FlowStyle.AUTO;
      this.canonical = false;
      this.allowUnicode = true;
      this.allowReadOnlyProperties = false;
      this.indent = 2;
      this.indicatorIndent = 0;
      this.bestWidth = 80;
      this.splitLines = true;
      this.lineBreak = DumperOptions$LineBreak.UNIX;
      this.explicitStart = false;
      this.explicitEnd = false;
      this.timeZone = null;
      this.version = null;
      this.tags = null;
      this.prettyFlow = false;
      this.anchorGenerator = new NumberAnchorGenerator(0);
   }

   public boolean isAllowUnicode() {
      return this.allowUnicode;
   }

   public void setAllowUnicode(boolean var1) {
      this.allowUnicode = var1;
   }

   public DumperOptions$ScalarStyle getDefaultScalarStyle() {
      return this.defaultStyle;
   }

   public void setDefaultScalarStyle(DumperOptions$ScalarStyle var1) {
      if (var1 == null) {
         throw new NullPointerException("Use ScalarStyle enum.");
      } else {
         this.defaultStyle = var1;
      }
   }

   public void setIndent(int var1) {
      if (var1 < 1) {
         throw new YAMLException("Indent must be at least 1");
      } else if (var1 > 10) {
         throw new YAMLException("Indent must be at most 10");
      } else {
         this.indent = var1;
      }
   }

   public int getIndent() {
      return this.indent;
   }

   public void setIndicatorIndent(int var1) {
      if (var1 < 0) {
         throw new YAMLException("Indicator indent must be non-negative.");
      } else if (var1 > 9) {
         throw new YAMLException("Indicator indent must be at most Emitter.MAX_INDENT-1: 9");
      } else {
         this.indicatorIndent = var1;
      }
   }

   public int getIndicatorIndent() {
      return this.indicatorIndent;
   }

   public void setVersion(DumperOptions$Version var1) {
      this.version = var1;
   }

   public DumperOptions$Version getVersion() {
      return this.version;
   }

   public void setCanonical(boolean var1) {
      this.canonical = var1;
   }

   public boolean isCanonical() {
      return this.canonical;
   }

   public void setPrettyFlow(boolean var1) {
      this.prettyFlow = var1;
   }

   public boolean isPrettyFlow() {
      return this.prettyFlow;
   }

   public void setWidth(int var1) {
      this.bestWidth = var1;
   }

   public int getWidth() {
      return this.bestWidth;
   }

   public void setSplitLines(boolean var1) {
      this.splitLines = var1;
   }

   public boolean getSplitLines() {
      return this.splitLines;
   }

   public DumperOptions$LineBreak getLineBreak() {
      return this.lineBreak;
   }

   public void setDefaultFlowStyle(DumperOptions$FlowStyle var1) {
      if (var1 == null) {
         throw new NullPointerException("Use FlowStyle enum.");
      } else {
         this.defaultFlowStyle = var1;
      }
   }

   public DumperOptions$FlowStyle getDefaultFlowStyle() {
      return this.defaultFlowStyle;
   }

   public void setLineBreak(DumperOptions$LineBreak var1) {
      if (var1 == null) {
         throw new NullPointerException("Specify line break.");
      } else {
         this.lineBreak = var1;
      }
   }

   public boolean isExplicitStart() {
      return this.explicitStart;
   }

   public void setExplicitStart(boolean var1) {
      this.explicitStart = var1;
   }

   public boolean isExplicitEnd() {
      return this.explicitEnd;
   }

   public void setExplicitEnd(boolean var1) {
      this.explicitEnd = var1;
   }

   public Map getTags() {
      return this.tags;
   }

   public void setTags(Map var1) {
      this.tags = var1;
   }

   public boolean isAllowReadOnlyProperties() {
      return this.allowReadOnlyProperties;
   }

   public void setAllowReadOnlyProperties(boolean var1) {
      this.allowReadOnlyProperties = var1;
   }

   public TimeZone getTimeZone() {
      return this.timeZone;
   }

   public void setTimeZone(TimeZone var1) {
      this.timeZone = var1;
   }

   public AnchorGenerator getAnchorGenerator() {
      return this.anchorGenerator;
   }

   public void setAnchorGenerator(AnchorGenerator var1) {
      this.anchorGenerator = var1;
   }
}
