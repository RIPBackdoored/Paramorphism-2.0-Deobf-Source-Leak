package org.eclipse.aether.spi.connector;

import java.io.File;
import java.util.Collections;
import java.util.List;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.transfer.MetadataTransferException;
import org.eclipse.aether.transfer.TransferListener;

public final class MetadataDownload extends MetadataTransfer {
   private String checksumPolicy = "";
   private String context = "";
   private List repositories = Collections.emptyList();

   public MetadataDownload() {
      super();
   }

   public MetadataDownload(Metadata var1, String var2, File var3, String var4) {
      super();
      this.setMetadata(var1);
      this.setFile(var3);
      this.setChecksumPolicy(var4);
      this.setRequestContext(var2);
   }

   public MetadataDownload setMetadata(Metadata var1) {
      super.setMetadata(var1);
      return this;
   }

   public MetadataDownload setFile(File var1) {
      super.setFile(var1);
      return this;
   }

   public String getChecksumPolicy() {
      return this.checksumPolicy;
   }

   public MetadataDownload setChecksumPolicy(String var1) {
      this.checksumPolicy = var1 != null ? var1 : "";
      return this;
   }

   public String getRequestContext() {
      return this.context;
   }

   public MetadataDownload setRequestContext(String var1) {
      this.context = var1 != null ? var1 : "";
      return this;
   }

   public List getRepositories() {
      return this.repositories;
   }

   public MetadataDownload setRepositories(List var1) {
      if (var1 == null) {
         this.repositories = Collections.emptyList();
      } else {
         this.repositories = var1;
      }

      return this;
   }

   public MetadataDownload setException(MetadataTransferException var1) {
      super.setException(var1);
      return this;
   }

   public MetadataDownload setListener(TransferListener var1) {
      super.setListener(var1);
      return this;
   }

   public MetadataDownload setTrace(RequestTrace var1) {
      super.setTrace(var1);
      return this;
   }

   public String toString() {
      return this.getMetadata() + " - " + this.getFile();
   }

   public MetadataTransfer setException(MetadataTransferException var1) {
      return this.setException(var1);
   }

   public MetadataTransfer setFile(File var1) {
      return this.setFile(var1);
   }

   public MetadataTransfer setMetadata(Metadata var1) {
      return this.setMetadata(var1);
   }

   public Transfer setTrace(RequestTrace var1) {
      return this.setTrace(var1);
   }

   public Transfer setListener(TransferListener var1) {
      return this.setListener(var1);
   }
}
