package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.lang.annotation.Annotation;

public abstract class SettableBeanProperty$Delegating extends SettableBeanProperty {
   protected final SettableBeanProperty delegate;

   protected SettableBeanProperty$Delegating(SettableBeanProperty var1) {
      super(var1);
      this.delegate = var1;
   }

   protected abstract SettableBeanProperty withDelegate(SettableBeanProperty var1);

   protected SettableBeanProperty _with(SettableBeanProperty var1) {
      return (SettableBeanProperty)(var1 == this.delegate ? this : this.withDelegate(var1));
   }

   public SettableBeanProperty withValueDeserializer(JsonDeserializer var1) {
      return this._with(this.delegate.withValueDeserializer(var1));
   }

   public SettableBeanProperty withName(PropertyName var1) {
      return this._with(this.delegate.withName(var1));
   }

   public SettableBeanProperty withNullProvider(NullValueProvider var1) {
      return this._with(this.delegate.withNullProvider(var1));
   }

   public void assignIndex(int var1) {
      this.delegate.assignIndex(var1);
   }

   public void fixAccess(DeserializationConfig var1) {
      this.delegate.fixAccess(var1);
   }

   protected Class getDeclaringClass() {
      return this.delegate.getDeclaringClass();
   }

   public String getManagedReferenceName() {
      return this.delegate.getManagedReferenceName();
   }

   public ObjectIdInfo getObjectIdInfo() {
      return this.delegate.getObjectIdInfo();
   }

   public boolean hasValueDeserializer() {
      return this.delegate.hasValueDeserializer();
   }

   public boolean hasValueTypeDeserializer() {
      return this.delegate.hasValueTypeDeserializer();
   }

   public JsonDeserializer getValueDeserializer() {
      return this.delegate.getValueDeserializer();
   }

   public TypeDeserializer getValueTypeDeserializer() {
      return this.delegate.getValueTypeDeserializer();
   }

   public boolean visibleInView(Class var1) {
      return this.delegate.visibleInView(var1);
   }

   public boolean hasViews() {
      return this.delegate.hasViews();
   }

   public int getPropertyIndex() {
      return this.delegate.getPropertyIndex();
   }

   public int getCreatorIndex() {
      return this.delegate.getCreatorIndex();
   }

   public Object getInjectableValueId() {
      return this.delegate.getInjectableValueId();
   }

   public AnnotatedMember getMember() {
      return this.delegate.getMember();
   }

   public Annotation getAnnotation(Class var1) {
      return this.delegate.getAnnotation(var1);
   }

   public SettableBeanProperty getDelegate() {
      return this.delegate;
   }

   public void deserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      this.delegate.deserializeAndSet(var1, var2, var3);
   }

   public Object deserializeSetAndReturn(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.delegate.deserializeSetAndReturn(var1, var2, var3);
   }

   public void set(Object var1, Object var2) throws IOException {
      this.delegate.set(var1, var2);
   }

   public Object setAndReturn(Object var1, Object var2) throws IOException {
      return this.delegate.setAndReturn(var1, var2);
   }
}
