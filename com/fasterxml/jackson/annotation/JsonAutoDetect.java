package com.fasterxml.jackson.annotation;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.io.*;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonAutoDetect {
    Visibility getterVisibility() default Visibility.DEFAULT;
    
    Visibility isGetterVisibility() default Visibility.DEFAULT;
    
    Visibility setterVisibility() default Visibility.DEFAULT;
    
    Visibility creatorVisibility() default Visibility.DEFAULT;
    
    Visibility fieldVisibility() default Visibility.DEFAULT;
    
    public enum Visibility
    {
        ANY, 
        NON_PRIVATE, 
        PROTECTED_AND_PUBLIC, 
        PUBLIC_ONLY, 
        NONE, 
        DEFAULT;
        
        private static final Visibility[] $VALUES;
        
        public static Visibility[] values() {
            return Visibility.$VALUES.clone();
        }
        
        public static Visibility valueOf(final String s) {
            return Enum.valueOf(Visibility.class, s);
        }
        
        public boolean isVisible(final Member member) {
            switch (this) {
                case ANY: {
                    return true;
                }
                case NONE: {
                    return false;
                }
                case NON_PRIVATE: {
                    return !Modifier.isPrivate(member.getModifiers());
                }
                case PROTECTED_AND_PUBLIC: {
                    if (Modifier.isProtected(member.getModifiers())) {
                        return true;
                    }
                    return Modifier.isPublic(member.getModifiers());
                }
                case PUBLIC_ONLY: {
                    return Modifier.isPublic(member.getModifiers());
                }
                default: {
                    return false;
                }
            }
        }
        
        static {
            $VALUES = new Visibility[] { Visibility.ANY, Visibility.NON_PRIVATE, Visibility.PROTECTED_AND_PUBLIC, Visibility.PUBLIC_ONLY, Visibility.NONE, Visibility.DEFAULT };
        }
    }
    
    public static class Value implements JacksonAnnotationValue<JsonAutoDetect>, Serializable
    {
        private static final long serialVersionUID = 1L;
        private static final Visibility DEFAULT_FIELD_VISIBILITY;
        protected static final Value DEFAULT;
        protected static final Value NO_OVERRIDES;
        protected final Visibility _fieldVisibility;
        protected final Visibility _getterVisibility;
        protected final Visibility _isGetterVisibility;
        protected final Visibility _setterVisibility;
        protected final Visibility _creatorVisibility;
        
        private Value(final Visibility fieldVisibility, final Visibility getterVisibility, final Visibility isGetterVisibility, final Visibility setterVisibility, final Visibility creatorVisibility) {
            super();
            this._fieldVisibility = fieldVisibility;
            this._getterVisibility = getterVisibility;
            this._isGetterVisibility = isGetterVisibility;
            this._setterVisibility = setterVisibility;
            this._creatorVisibility = creatorVisibility;
        }
        
        public static Value defaultVisibility() {
            return Value.DEFAULT;
        }
        
        public static Value noOverrides() {
            return Value.NO_OVERRIDES;
        }
        
        public static Value from(final JsonAutoDetect jsonAutoDetect) {
            return construct(jsonAutoDetect.fieldVisibility(), jsonAutoDetect.getterVisibility(), jsonAutoDetect.isGetterVisibility(), jsonAutoDetect.setterVisibility(), jsonAutoDetect.creatorVisibility());
        }
        
        public static Value construct(final PropertyAccessor propertyAccessor, final Visibility visibility) {
            Visibility default1 = Visibility.DEFAULT;
            Visibility default2 = Visibility.DEFAULT;
            Visibility default3 = Visibility.DEFAULT;
            Visibility default4 = Visibility.DEFAULT;
            Visibility default5 = Visibility.DEFAULT;
            switch (propertyAccessor) {
                case CREATOR: {
                    default5 = visibility;
                    break;
                }
                case FIELD: {
                    default1 = visibility;
                    break;
                }
                case GETTER: {
                    default2 = visibility;
                    break;
                }
                case IS_GETTER: {
                    default3 = visibility;
                }
                case SETTER: {
                    default4 = visibility;
                    break;
                }
                case ALL: {
                    default5 = visibility;
                    default4 = visibility;
                    default3 = visibility;
                    default2 = visibility;
                    default1 = visibility;
                    break;
                }
            }
            return construct(default1, default2, default3, default4, default5);
        }
        
        public static Value construct(final Visibility visibility, final Visibility visibility2, final Visibility visibility3, final Visibility visibility4, final Visibility visibility5) {
            Value predefined = _predefined(visibility, visibility2, visibility3, visibility4, visibility5);
            if (predefined == null) {
                predefined = new Value(visibility, visibility2, visibility3, visibility4, visibility5);
            }
            return predefined;
        }
        
        public Value withFieldVisibility(final Visibility visibility) {
            return construct(visibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
        }
        
        public Value withGetterVisibility(final Visibility visibility) {
            return construct(this._fieldVisibility, visibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
        }
        
        public Value withIsGetterVisibility(final Visibility visibility) {
            return construct(this._fieldVisibility, this._getterVisibility, visibility, this._setterVisibility, this._creatorVisibility);
        }
        
        public Value withSetterVisibility(final Visibility visibility) {
            return construct(this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, visibility, this._creatorVisibility);
        }
        
        public Value withCreatorVisibility(final Visibility visibility) {
            return construct(this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, visibility);
        }
        
        public static Value merge(final Value value, final Value value2) {
            return (value == null) ? value2 : value.withOverrides(value2);
        }
        
        public Value withOverrides(final Value value) {
            if (value == null || value == Value.NO_OVERRIDES || value == this) {
                return this;
            }
            if (_equals(this, value)) {
                return this;
            }
            Visibility visibility = value._fieldVisibility;
            if (visibility == Visibility.DEFAULT) {
                visibility = this._fieldVisibility;
            }
            Visibility visibility2 = value._getterVisibility;
            if (visibility2 == Visibility.DEFAULT) {
                visibility2 = this._getterVisibility;
            }
            Visibility visibility3 = value._isGetterVisibility;
            if (visibility3 == Visibility.DEFAULT) {
                visibility3 = this._isGetterVisibility;
            }
            Visibility visibility4 = value._setterVisibility;
            if (visibility4 == Visibility.DEFAULT) {
                visibility4 = this._setterVisibility;
            }
            Visibility visibility5 = value._creatorVisibility;
            if (visibility5 == Visibility.DEFAULT) {
                visibility5 = this._creatorVisibility;
            }
            return construct(visibility, visibility2, visibility3, visibility4, visibility5);
        }
        
        @Override
        public Class<JsonAutoDetect> valueFor() {
            return JsonAutoDetect.class;
        }
        
        public Visibility getFieldVisibility() {
            return this._fieldVisibility;
        }
        
        public Visibility getGetterVisibility() {
            return this._getterVisibility;
        }
        
        public Visibility getIsGetterVisibility() {
            return this._isGetterVisibility;
        }
        
        public Visibility getSetterVisibility() {
            return this._setterVisibility;
        }
        
        public Visibility getCreatorVisibility() {
            return this._creatorVisibility;
        }
        
        protected Object readResolve() {
            final Value predefined = _predefined(this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
            return (predefined == null) ? this : predefined;
        }
        
        @Override
        public String toString() {
            return String.format("JsonAutoDetect.Value(fields=%s,getters=%s,isGetters=%s,setters=%s,creators=%s)", this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
        }
        
        @Override
        public int hashCode() {
            return 1 + this._fieldVisibility.ordinal() ^ 3 * this._getterVisibility.ordinal() - 7 * this._isGetterVisibility.ordinal() + 11 * this._setterVisibility.ordinal() ^ 13 * this._creatorVisibility.ordinal();
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || (o != null && o.getClass() == this.getClass() && _equals(this, (Value)o));
        }
        
        private static Value _predefined(final Visibility visibility, final Visibility visibility2, final Visibility visibility3, final Visibility visibility4, final Visibility visibility5) {
            if (visibility == Value.DEFAULT_FIELD_VISIBILITY) {
                if (visibility2 == Value.DEFAULT._getterVisibility && visibility3 == Value.DEFAULT._isGetterVisibility && visibility4 == Value.DEFAULT._setterVisibility && visibility5 == Value.DEFAULT._creatorVisibility) {
                    return Value.DEFAULT;
                }
            }
            else if (visibility == Visibility.DEFAULT && visibility2 == Visibility.DEFAULT && visibility3 == Visibility.DEFAULT && visibility4 == Visibility.DEFAULT && visibility5 == Visibility.DEFAULT) {
                return Value.NO_OVERRIDES;
            }
            return null;
        }
        
        private static boolean _equals(final Value value, final Value value2) {
            return value._fieldVisibility == value2._fieldVisibility && value._getterVisibility == value2._getterVisibility && value._isGetterVisibility == value2._isGetterVisibility && value._setterVisibility == value2._setterVisibility && value._creatorVisibility == value2._creatorVisibility;
        }
        
        static {
            DEFAULT_FIELD_VISIBILITY = Visibility.PUBLIC_ONLY;
            DEFAULT = new Value(Value.DEFAULT_FIELD_VISIBILITY, Visibility.PUBLIC_ONLY, Visibility.PUBLIC_ONLY, Visibility.ANY, Visibility.PUBLIC_ONLY);
            NO_OVERRIDES = new Value(Visibility.DEFAULT, Visibility.DEFAULT, Visibility.DEFAULT, Visibility.DEFAULT, Visibility.DEFAULT);
        }
    }
}
