package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.core.*;
import java.util.*;

public abstract class PropertyBindingException extends MismatchedInputException
{
    protected final Class<?> _referringClass;
    protected final String _propertyName;
    protected final Collection<Object> _propertyIds;
    protected transient String _propertiesAsString;
    private static final int MAX_DESC_LENGTH = 1000;
    
    protected PropertyBindingException(final JsonParser jsonParser, final String s, final JsonLocation jsonLocation, final Class<?> referringClass, final String propertyName, final Collection<Object> propertyIds) {
        super(jsonParser, s, jsonLocation);
        this._referringClass = referringClass;
        this._propertyName = propertyName;
        this._propertyIds = propertyIds;
    }
    
    @Deprecated
    protected PropertyBindingException(final String s, final JsonLocation jsonLocation, final Class<?> clazz, final String s2, final Collection<Object> collection) {
        this(null, s, jsonLocation, clazz, s2, collection);
    }
    
    public String getMessageSuffix() {
        String propertiesAsString = this._propertiesAsString;
        if (propertiesAsString == null && this._propertyIds != null) {
            final StringBuilder sb = new StringBuilder(100);
            final int size = this._propertyIds.size();
            if (size == 1) {
                sb.append(" (one known property: \"");
                sb.append(String.valueOf(this._propertyIds.iterator().next()));
                sb.append('\"');
            }
            else {
                sb.append(" (").append(size).append(" known properties: ");
                final Iterator<Object> iterator = this._propertyIds.iterator();
                while (iterator.hasNext()) {
                    sb.append('\"');
                    sb.append(String.valueOf(iterator.next()));
                    sb.append('\"');
                    if (sb.length() > 1000) {
                        sb.append(" [truncated]");
                        break;
                    }
                    if (!iterator.hasNext()) {
                        continue;
                    }
                    sb.append(", ");
                }
            }
            sb.append("])");
            propertiesAsString = (this._propertiesAsString = sb.toString());
        }
        return propertiesAsString;
    }
    
    public Class<?> getReferringClass() {
        return this._referringClass;
    }
    
    public String getPropertyName() {
        return this._propertyName;
    }
    
    public Collection<Object> getKnownPropertyIds() {
        if (this._propertyIds == null) {
            return null;
        }
        return Collections.unmodifiableCollection((Collection<?>)this._propertyIds);
    }
}
