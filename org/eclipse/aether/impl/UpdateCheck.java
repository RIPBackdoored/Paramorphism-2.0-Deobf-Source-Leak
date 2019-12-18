package org.eclipse.aether.impl;

import java.io.File;
import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.repository.RemoteRepository;

public final class UpdateCheck {
   private long localLastUpdated;
   private Object item;
   private File file;
   private boolean fileValid = true;
   private String policy;
   private RemoteRepository repository;
   private RemoteRepository authoritativeRepository;
   private boolean required;
   private RepositoryException exception;

   public UpdateCheck() {
      super();
   }

   public long getLocalLastUpdated() {
      return this.localLastUpdated;
   }

   public UpdateCheck setLocalLastUpdated(long var1) {
      this.localLastUpdated = var1;
      return this;
   }

   public Object getItem() {
      return this.item;
   }

   public UpdateCheck setItem(Object var1) {
      this.item = var1;
      return this;
   }

   public File getFile() {
      return this.file;
   }

   public UpdateCheck setFile(File var1) {
      this.file = var1;
      return this;
   }

   public boolean isFileValid() {
      return this.fileValid;
   }

   public UpdateCheck setFileValid(boolean var1) {
      this.fileValid = var1;
      return this;
   }

   public String getPolicy() {
      return this.policy;
   }

   public UpdateCheck setPolicy(String var1) {
      this.policy = var1;
      return this;
   }

   public RemoteRepository getRepository() {
      return this.repository;
   }

   public UpdateCheck setRepository(RemoteRepository var1) {
      this.repository = var1;
      return this;
   }

   public RemoteRepository getAuthoritativeRepository() {
      return this.authoritativeRepository != null ? this.authoritativeRepository : this.repository;
   }

   public UpdateCheck setAuthoritativeRepository(RemoteRepository var1) {
      this.authoritativeRepository = var1;
      return this;
   }

   public boolean isRequired() {
      return this.required;
   }

   public UpdateCheck setRequired(boolean var1) {
      this.required = var1;
      return this;
   }

   public RepositoryException getException() {
      return this.exception;
   }

   public UpdateCheck setException(RepositoryException var1) {
      this.exception = var1;
      return this;
   }

   public String toString() {
      return this.getPolicy() + ": " + this.getFile() + " < " + this.getRepository();
   }
}
