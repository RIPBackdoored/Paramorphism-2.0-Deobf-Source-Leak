package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import java.io.IOException;
import java.io.Serializable;

public class DefaultPrettyPrinter implements PrettyPrinter, Instantiatable, Serializable {
   private static final long serialVersionUID = 1L;
   public static final SerializedString DEFAULT_ROOT_VALUE_SEPARATOR = new SerializedString(" ");
   protected DefaultPrettyPrinter$Indenter _arrayIndenter;
   protected DefaultPrettyPrinter$Indenter _objectIndenter;
   protected final SerializableString _rootSeparator;
   protected boolean _spacesInObjectEntries;
   protected transient int _nesting;
   protected Separators _separators;
   protected String _objectFieldValueSeparatorWithSpaces;

   public DefaultPrettyPrinter() {
      this((SerializableString)DEFAULT_ROOT_VALUE_SEPARATOR);
   }

   public DefaultPrettyPrinter(String var1) {
      this((SerializableString)(var1 == null ? null : new SerializedString(var1)));
   }

   public DefaultPrettyPrinter(SerializableString var1) {
      super();
      this._arrayIndenter = DefaultPrettyPrinter$FixedSpaceIndenter.instance;
      this._objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
      this._spacesInObjectEntries = true;
      this._rootSeparator = var1;
      this.withSeparators(DEFAULT_SEPARATORS);
   }

   public DefaultPrettyPrinter(DefaultPrettyPrinter var1) {
      this(var1, var1._rootSeparator);
   }

   public DefaultPrettyPrinter(DefaultPrettyPrinter var1, SerializableString var2) {
      super();
      this._arrayIndenter = DefaultPrettyPrinter$FixedSpaceIndenter.instance;
      this._objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
      this._spacesInObjectEntries = true;
      this._arrayIndenter = var1._arrayIndenter;
      this._objectIndenter = var1._objectIndenter;
      this._spacesInObjectEntries = var1._spacesInObjectEntries;
      this._nesting = var1._nesting;
      this._separators = var1._separators;
      this._objectFieldValueSeparatorWithSpaces = var1._objectFieldValueSeparatorWithSpaces;
      this._rootSeparator = var2;
   }

   public DefaultPrettyPrinter withRootSeparator(SerializableString var1) {
      return this._rootSeparator != var1 && (var1 == null || !var1.equals(this._rootSeparator)) ? new DefaultPrettyPrinter(this, var1) : this;
   }

   public DefaultPrettyPrinter withRootSeparator(String var1) {
      return this.withRootSeparator((SerializableString)(var1 == null ? null : new SerializedString(var1)));
   }

   public void indentArraysWith(DefaultPrettyPrinter$Indenter var1) {
      this._arrayIndenter = (DefaultPrettyPrinter$Indenter)(var1 == null ? DefaultPrettyPrinter$NopIndenter.instance : var1);
   }

   public void indentObjectsWith(DefaultPrettyPrinter$Indenter var1) {
      this._objectIndenter = (DefaultPrettyPrinter$Indenter)(var1 == null ? DefaultPrettyPrinter$NopIndenter.instance : var1);
   }

   public DefaultPrettyPrinter withArrayIndenter(DefaultPrettyPrinter$Indenter var1) {
      if (var1 == null) {
         var1 = DefaultPrettyPrinter$NopIndenter.instance;
      }

      if (this._arrayIndenter == var1) {
         return this;
      } else {
         DefaultPrettyPrinter var2 = new DefaultPrettyPrinter(this);
         var2._arrayIndenter = (DefaultPrettyPrinter$Indenter)var1;
         return var2;
      }
   }

   public DefaultPrettyPrinter withObjectIndenter(DefaultPrettyPrinter$Indenter var1) {
      if (var1 == null) {
         var1 = DefaultPrettyPrinter$NopIndenter.instance;
      }

      if (this._objectIndenter == var1) {
         return this;
      } else {
         DefaultPrettyPrinter var2 = new DefaultPrettyPrinter(this);
         var2._objectIndenter = (DefaultPrettyPrinter$Indenter)var1;
         return var2;
      }
   }

   public DefaultPrettyPrinter withSpacesInObjectEntries() {
      return this._withSpaces(true);
   }

   public DefaultPrettyPrinter withoutSpacesInObjectEntries() {
      return this._withSpaces(false);
   }

   protected DefaultPrettyPrinter _withSpaces(boolean var1) {
      if (this._spacesInObjectEntries == var1) {
         return this;
      } else {
         DefaultPrettyPrinter var2 = new DefaultPrettyPrinter(this);
         var2._spacesInObjectEntries = var1;
         return var2;
      }
   }

   public DefaultPrettyPrinter withSeparators(Separators var1) {
      this._separators = var1;
      this._objectFieldValueSeparatorWithSpaces = " " + var1.getObjectFieldValueSeparator() + " ";
      return this;
   }

   public DefaultPrettyPrinter createInstance() {
      return new DefaultPrettyPrinter(this);
   }

   public void writeRootValueSeparator(JsonGenerator var1) throws IOException {
      if (this._rootSeparator != null) {
         var1.writeRaw(this._rootSeparator);
      }

   }

   public void writeStartObject(JsonGenerator var1) throws IOException {
      var1.writeRaw('{');
      if (!this._objectIndenter.isInline()) {
         ++this._nesting;
      }

   }

   public void beforeObjectEntries(JsonGenerator var1) throws IOException {
      this._objectIndenter.writeIndentation(var1, this._nesting);
   }

   public void writeObjectFieldValueSeparator(JsonGenerator var1) throws IOException {
      if (this._spacesInObjectEntries) {
         var1.writeRaw(this._objectFieldValueSeparatorWithSpaces);
      } else {
         var1.writeRaw(this._separators.getObjectFieldValueSeparator());
      }

   }

   public void writeObjectEntrySeparator(JsonGenerator var1) throws IOException {
      var1.writeRaw(this._separators.getObjectEntrySeparator());
      this._objectIndenter.writeIndentation(var1, this._nesting);
   }

   public void writeEndObject(JsonGenerator var1, int var2) throws IOException {
      if (!this._objectIndenter.isInline()) {
         --this._nesting;
      }

      if (var2 > 0) {
         this._objectIndenter.writeIndentation(var1, this._nesting);
      } else {
         var1.writeRaw(' ');
      }

      var1.writeRaw('}');
   }

   public void writeStartArray(JsonGenerator var1) throws IOException {
      if (!this._arrayIndenter.isInline()) {
         ++this._nesting;
      }

      var1.writeRaw('[');
   }

   public void beforeArrayValues(JsonGenerator var1) throws IOException {
      this._arrayIndenter.writeIndentation(var1, this._nesting);
   }

   public void writeArrayValueSeparator(JsonGenerator var1) throws IOException {
      var1.writeRaw(this._separators.getArrayValueSeparator());
      this._arrayIndenter.writeIndentation(var1, this._nesting);
   }

   public void writeEndArray(JsonGenerator var1, int var2) throws IOException {
      if (!this._arrayIndenter.isInline()) {
         --this._nesting;
      }

      if (var2 > 0) {
         this._arrayIndenter.writeIndentation(var1, this._nesting);
      } else {
         var1.writeRaw(' ');
      }

      var1.writeRaw(']');
   }

   public Object createInstance() {
      return this.createInstance();
   }
}
