package org.yaml.snakeyaml.serializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions$Version;
import org.yaml.snakeyaml.emitter.Emitable;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.AliasEvent;
import org.yaml.snakeyaml.events.DocumentEndEvent;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.StreamEndEvent;
import org.yaml.snakeyaml.events.StreamStartEvent;
import org.yaml.snakeyaml.nodes.AnchorNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.resolver.Resolver;

public final class Serializer {
   private final Emitable emitter;
   private final Resolver resolver;
   private boolean explicitStart;
   private boolean explicitEnd;
   private DumperOptions$Version useVersion;
   private Map useTags;
   private Set serializedNodes;
   private Map anchors;
   private AnchorGenerator anchorGenerator;
   private Boolean closed;
   private Tag explicitRoot;

   public Serializer(Emitable var1, Resolver var2, DumperOptions var3, Tag var4) {
      super();
      this.emitter = var1;
      this.resolver = var2;
      this.explicitStart = var3.isExplicitStart();
      this.explicitEnd = var3.isExplicitEnd();
      if (var3.getVersion() != null) {
         this.useVersion = var3.getVersion();
      }

      this.useTags = var3.getTags();
      this.serializedNodes = new HashSet();
      this.anchors = new HashMap();
      this.anchorGenerator = var3.getAnchorGenerator();
      this.closed = null;
      this.explicitRoot = var4;
   }

   public void open() throws IOException {
      if (this.closed == null) {
         this.emitter.emit(new StreamStartEvent((Mark)null, (Mark)null));
         this.closed = Boolean.FALSE;
      } else if (Boolean.TRUE.equals(this.closed)) {
         throw new SerializerException("serializer is closed");
      } else {
         throw new SerializerException("serializer is already opened");
      }
   }

   public void close() throws IOException {
      if (this.closed == null) {
         throw new SerializerException("serializer is not opened");
      } else {
         if (!Boolean.TRUE.equals(this.closed)) {
            this.emitter.emit(new StreamEndEvent((Mark)null, (Mark)null));
            this.closed = Boolean.TRUE;
         }

      }
   }

   public void serialize(Node var1) throws IOException {
      if (this.closed == null) {
         throw new SerializerException("serializer is not opened");
      } else if (this.closed) {
         throw new SerializerException("serializer is closed");
      } else {
         this.emitter.emit(new DocumentStartEvent((Mark)null, (Mark)null, this.explicitStart, this.useVersion, this.useTags));
         this.anchorNode(var1);
         if (this.explicitRoot != null) {
            var1.setTag(this.explicitRoot);
         }

         this.serializeNode(var1, (Node)null);
         this.emitter.emit(new DocumentEndEvent((Mark)null, (Mark)null, this.explicitEnd));
         this.serializedNodes.clear();
         this.anchors.clear();
      }
   }

   private void anchorNode(Node var1) {
      // $FF: Couldn't be decompiled
   }

   private void serializeNode(Node var1, Node var2) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
