package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.util.Instantiatable;
import java.io.Serializable;

public final class ObjectWriter$GeneratorSettings implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final ObjectWriter$GeneratorSettings empty = new ObjectWriter$GeneratorSettings((PrettyPrinter)null, (FormatSchema)null, (CharacterEscapes)null, (SerializableString)null);
   public final PrettyPrinter prettyPrinter;
   public final FormatSchema schema;
   public final CharacterEscapes characterEscapes;
   public final SerializableString rootValueSeparator;

   public ObjectWriter$GeneratorSettings(PrettyPrinter var1, FormatSchema var2, CharacterEscapes var3, SerializableString var4) {
      super();
      this.prettyPrinter = var1;
      this.schema = var2;
      this.characterEscapes = var3;
      this.rootValueSeparator = var4;
   }

   public ObjectWriter$GeneratorSettings with(PrettyPrinter var1) {
      if (var1 == null) {
         var1 = ObjectWriter.NULL_PRETTY_PRINTER;
      }

      return var1 == this.prettyPrinter ? this : new ObjectWriter$GeneratorSettings(var1, this.schema, this.characterEscapes, this.rootValueSeparator);
   }

   public ObjectWriter$GeneratorSettings with(FormatSchema var1) {
      return this.schema == var1 ? this : new ObjectWriter$GeneratorSettings(this.prettyPrinter, var1, this.characterEscapes, this.rootValueSeparator);
   }

   public ObjectWriter$GeneratorSettings with(CharacterEscapes var1) {
      return this.characterEscapes == var1 ? this : new ObjectWriter$GeneratorSettings(this.prettyPrinter, this.schema, var1, this.rootValueSeparator);
   }

   public ObjectWriter$GeneratorSettings withRootValueSeparator(String var1) {
      if (var1 == null) {
         return this.rootValueSeparator == null ? this : new ObjectWriter$GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, (SerializableString)null);
      } else {
         return var1.equals(this._rootValueSeparatorAsString()) ? this : new ObjectWriter$GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, new SerializedString(var1));
      }
   }

   public ObjectWriter$GeneratorSettings withRootValueSeparator(SerializableString var1) {
      if (var1 == null) {
         return this.rootValueSeparator == null ? this : new ObjectWriter$GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, (SerializableString)null);
      } else {
         return var1.equals(this.rootValueSeparator) ? this : new ObjectWriter$GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, var1);
      }
   }

   private final String _rootValueSeparatorAsString() {
      return this.rootValueSeparator == null ? null : this.rootValueSeparator.getValue();
   }

   public void initialize(JsonGenerator var1) {
      PrettyPrinter var2 = this.prettyPrinter;
      if (this.prettyPrinter != null) {
         if (var2 == ObjectWriter.NULL_PRETTY_PRINTER) {
            var1.setPrettyPrinter((PrettyPrinter)null);
         } else {
            if (var2 instanceof Instantiatable) {
               var2 = (PrettyPrinter)((Instantiatable)var2).createInstance();
            }

            var1.setPrettyPrinter(var2);
         }
      }

      if (this.characterEscapes != null) {
         var1.setCharacterEscapes(this.characterEscapes);
      }

      if (this.schema != null) {
         var1.setSchema(this.schema);
      }

      if (this.rootValueSeparator != null) {
         var1.setRootValueSeparator(this.rootValueSeparator);
      }

   }
}
