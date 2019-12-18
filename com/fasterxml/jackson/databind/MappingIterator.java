package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MappingIterator implements Iterator, Closeable {
   protected static final MappingIterator EMPTY_ITERATOR = new MappingIterator((JavaType)null, (JsonParser)null, (DeserializationContext)null, (JsonDeserializer)null, false, (Object)null);
   protected static final int STATE_CLOSED = 0;
   protected static final int STATE_NEED_RESYNC = 1;
   protected static final int STATE_MAY_HAVE_VALUE = 2;
   protected static final int STATE_HAS_VALUE = 3;
   protected final JavaType _type;
   protected final DeserializationContext _context;
   protected final JsonDeserializer _deserializer;
   protected final JsonParser _parser;
   protected final JsonStreamContext _seqContext;
   protected final Object _updatedValue;
   protected final boolean _closeParser;
   protected int _state;

   protected MappingIterator(JavaType var1, JsonParser var2, DeserializationContext var3, JsonDeserializer var4, boolean var5, Object var6) {
      super();
      this._type = var1;
      this._parser = var2;
      this._context = var3;
      this._deserializer = var4;
      this._closeParser = var5;
      if (var6 == null) {
         this._updatedValue = null;
      } else {
         this._updatedValue = var6;
      }

      if (var2 == null) {
         this._seqContext = null;
         this._state = 0;
      } else {
         JsonStreamContext var7 = var2.getParsingContext();
         if (var5 && var2.isExpectedStartArrayToken()) {
            var2.clearCurrentToken();
         } else {
            JsonToken var8 = var2.getCurrentToken();
            if (var8 == JsonToken.START_OBJECT || var8 == JsonToken.START_ARRAY) {
               var7 = var7.getParent();
            }
         }

         this._seqContext = var7;
         this._state = 2;
      }

   }

   protected static MappingIterator emptyIterator() {
      return EMPTY_ITERATOR;
   }

   public boolean hasNext() {
      boolean var10000;
      try {
         var10000 = this.hasNextValue();
      } catch (JsonMappingException var2) {
         return (Boolean)this._handleMappingException(var2);
      } catch (IOException var3) {
         return (Boolean)this._handleIOException(var3);
      }

      return var10000;
   }

   public Object next() {
      Object var10000;
      try {
         var10000 = this.nextValue();
      } catch (JsonMappingException var2) {
         throw new RuntimeJsonMappingException(var2.getMessage(), var2);
      } catch (IOException var3) {
         throw new RuntimeException(var3.getMessage(), var3);
      }

      return var10000;
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   public void close() throws IOException {
      if (this._state != 0) {
         this._state = 0;
         if (this._parser != null) {
            this._parser.close();
         }
      }

   }

   public boolean hasNextValue() throws IOException {
      switch(this._state) {
      case 0:
         return false;
      case 1:
         this._resync();
      case 2:
         break;
      case 3:
      default:
         return true;
      }

      JsonToken var1 = this._parser.getCurrentToken();
      if (var1 == null) {
         var1 = this._parser.nextToken();
         if (var1 == null || var1 == JsonToken.END_ARRAY) {
            this._state = 0;
            if (this._closeParser && this._parser != null) {
               this._parser.close();
            }

            return false;
         }
      }

      this._state = 3;
      return true;
   }

   public Object nextValue() throws IOException {
      switch(this._state) {
      case 0:
         return this._throwNoSuchElement();
      case 1:
      case 2:
         if (!this.hasNextValue()) {
            return this._throwNoSuchElement();
         }
      case 3:
      default:
         byte var1 = 1;
         boolean var6 = false;

         Object var3;
         try {
            var6 = true;
            Object var2;
            if (this._updatedValue == null) {
               var2 = this._deserializer.deserialize(this._parser, this._context);
            } else {
               this._deserializer.deserialize(this._parser, this._context, this._updatedValue);
               var2 = this._updatedValue;
            }

            var1 = 2;
            var3 = var2;
            var6 = false;
         } finally {
            if (var6) {
               this._state = var1;
               this._parser.clearCurrentToken();
            }
         }

         this._state = var1;
         this._parser.clearCurrentToken();
         return var3;
      }
   }

   public List readAll() throws IOException {
      return this.readAll((List)(new ArrayList()));
   }

   public List readAll(List var1) throws IOException {
      while(this.hasNextValue()) {
         var1.add(this.nextValue());
      }

      return var1;
   }

   public Collection readAll(Collection var1) throws IOException {
      while(this.hasNextValue()) {
         var1.add(this.nextValue());
      }

      return var1;
   }

   public JsonParser getParser() {
      return this._parser;
   }

   public FormatSchema getParserSchema() {
      return this._parser.getSchema();
   }

   public JsonLocation getCurrentLocation() {
      return this._parser.getCurrentLocation();
   }

   protected void _resync() throws IOException {
      JsonParser var1 = this._parser;
      if (var1.getParsingContext() != this._seqContext) {
         JsonToken var2;
         label30:
         do {
            while(true) {
               while(true) {
                  var2 = var1.nextToken();
                  if (var2 != JsonToken.END_ARRAY && var2 != JsonToken.END_OBJECT) {
                     if (var2 != JsonToken.START_ARRAY && var2 != JsonToken.START_OBJECT) {
                        continue label30;
                     }

                     var1.skipChildren();
                  } else if (var1.getParsingContext() == this._seqContext) {
                     var1.clearCurrentToken();
                     return;
                  }
               }
            }
         } while(var2 != null);

      }
   }

   protected Object _throwNoSuchElement() {
      throw new NoSuchElementException();
   }

   protected Object _handleMappingException(JsonMappingException var1) {
      throw new RuntimeJsonMappingException(var1.getMessage(), var1);
   }

   protected Object _handleIOException(IOException var1) {
      throw new RuntimeException(var1.getMessage(), var1);
   }
}
