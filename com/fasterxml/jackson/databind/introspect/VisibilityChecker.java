package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.*;
import java.lang.reflect.*;
import java.io.*;

public interface VisibilityChecker<T extends VisibilityChecker<T>>
{
    T with(final JsonAutoDetect p0);
    
    T withOverrides(final JsonAutoDetect.Value p0);
    
    T with(final JsonAutoDetect.Visibility p0);
    
    T withVisibility(final PropertyAccessor p0, final JsonAutoDetect.Visibility p1);
    
    T withGetterVisibility(final JsonAutoDetect.Visibility p0);
    
    T withIsGetterVisibility(final JsonAutoDetect.Visibility p0);
    
    T withSetterVisibility(final JsonAutoDetect.Visibility p0);
    
    T withCreatorVisibility(final JsonAutoDetect.Visibility p0);
    
    T withFieldVisibility(final JsonAutoDetect.Visibility p0);
    
    boolean isGetterVisible(final Method p0);
    
    boolean isGetterVisible(final AnnotatedMethod p0);
    
    boolean isIsGetterVisible(final Method p0);
    
    boolean isIsGetterVisible(final AnnotatedMethod p0);
    
    boolean isSetterVisible(final Method p0);
    
    boolean isSetterVisible(final AnnotatedMethod p0);
    
    boolean isCreatorVisible(final Member p0);
    
    boolean isCreatorVisible(final AnnotatedMember p0);
    
    boolean isFieldVisible(final Field p0);
    
    boolean isFieldVisible(final AnnotatedField p0);
    
    public static class Std implements VisibilityChecker<Std>, Serializable
    {
        private static final long serialVersionUID = 1L;
        protected static final Std DEFAULT;
        protected final JsonAutoDetect.Visibility _getterMinLevel;
        protected final JsonAutoDetect.Visibility _isGetterMinLevel;
        protected final JsonAutoDetect.Visibility _setterMinLevel;
        protected final JsonAutoDetect.Visibility _creatorMinLevel;
        protected final JsonAutoDetect.Visibility _fieldMinLevel;
        
        public static Std defaultInstance() {
            return Std.DEFAULT;
        }
        
        public Std(final JsonAutoDetect jsonAutoDetect) {
            super();
            this._getterMinLevel = jsonAutoDetect.getterVisibility();
            this._isGetterMinLevel = jsonAutoDetect.isGetterVisibility();
            this._setterMinLevel = jsonAutoDetect.setterVisibility();
            this._creatorMinLevel = jsonAutoDetect.creatorVisibility();
            this._fieldMinLevel = jsonAutoDetect.fieldVisibility();
        }
        
        public Std(final JsonAutoDetect.Visibility getterMinLevel, final JsonAutoDetect.Visibility isGetterMinLevel, final JsonAutoDetect.Visibility setterMinLevel, final JsonAutoDetect.Visibility creatorMinLevel, final JsonAutoDetect.Visibility fieldMinLevel) {
            super();
            this._getterMinLevel = getterMinLevel;
            this._isGetterMinLevel = isGetterMinLevel;
            this._setterMinLevel = setterMinLevel;
            this._creatorMinLevel = creatorMinLevel;
            this._fieldMinLevel = fieldMinLevel;
        }
        
        public Std(final JsonAutoDetect.Visibility fieldMinLevel) {
            super();
            if (fieldMinLevel == JsonAutoDetect.Visibility.DEFAULT) {
                this._getterMinLevel = Std.DEFAULT._getterMinLevel;
                this._isGetterMinLevel = Std.DEFAULT._isGetterMinLevel;
                this._setterMinLevel = Std.DEFAULT._setterMinLevel;
                this._creatorMinLevel = Std.DEFAULT._creatorMinLevel;
                this._fieldMinLevel = Std.DEFAULT._fieldMinLevel;
            }
            else {
                this._getterMinLevel = fieldMinLevel;
                this._isGetterMinLevel = fieldMinLevel;
                this._setterMinLevel = fieldMinLevel;
                this._creatorMinLevel = fieldMinLevel;
                this._fieldMinLevel = fieldMinLevel;
            }
        }
        
        public static Std construct(final JsonAutoDetect.Value value) {
            return Std.DEFAULT.withOverrides(value);
        }
        
        protected Std _with(final JsonAutoDetect.Visibility visibility, final JsonAutoDetect.Visibility visibility2, final JsonAutoDetect.Visibility visibility3, final JsonAutoDetect.Visibility visibility4, final JsonAutoDetect.Visibility visibility5) {
            if (visibility == this._getterMinLevel && visibility2 == this._isGetterMinLevel && visibility3 == this._setterMinLevel && visibility4 == this._creatorMinLevel && visibility5 == this._fieldMinLevel) {
                return this;
            }
            return new Std(visibility, visibility2, visibility3, visibility4, visibility5);
        }
        
        @Override
        public Std with(final JsonAutoDetect jsonAutoDetect) {
            if (jsonAutoDetect != null) {
                return this._with(this._defaultOrOverride(this._getterMinLevel, jsonAutoDetect.getterVisibility()), this._defaultOrOverride(this._isGetterMinLevel, jsonAutoDetect.isGetterVisibility()), this._defaultOrOverride(this._setterMinLevel, jsonAutoDetect.setterVisibility()), this._defaultOrOverride(this._creatorMinLevel, jsonAutoDetect.creatorVisibility()), this._defaultOrOverride(this._fieldMinLevel, jsonAutoDetect.fieldVisibility()));
            }
            return this;
        }
        
        @Override
        public Std withOverrides(final JsonAutoDetect.Value value) {
            if (value != null) {
                return this._with(this._defaultOrOverride(this._getterMinLevel, value.getGetterVisibility()), this._defaultOrOverride(this._isGetterMinLevel, value.getIsGetterVisibility()), this._defaultOrOverride(this._setterMinLevel, value.getSetterVisibility()), this._defaultOrOverride(this._creatorMinLevel, value.getCreatorVisibility()), this._defaultOrOverride(this._fieldMinLevel, value.getFieldVisibility()));
            }
            return this;
        }
        
        private JsonAutoDetect.Visibility _defaultOrOverride(final JsonAutoDetect.Visibility visibility, final JsonAutoDetect.Visibility visibility2) {
            if (visibility2 == JsonAutoDetect.Visibility.DEFAULT) {
                return visibility;
            }
            return visibility2;
        }
        
        @Override
        public Std with(final JsonAutoDetect.Visibility visibility) {
            if (visibility == JsonAutoDetect.Visibility.DEFAULT) {
                return Std.DEFAULT;
            }
            return new Std(visibility);
        }
        
        @Override
        public Std withVisibility(final PropertyAccessor propertyAccessor, final JsonAutoDetect.Visibility visibility) {
            switch (propertyAccessor) {
                case GETTER: {
                    return this.withGetterVisibility(visibility);
                }
                case SETTER: {
                    return this.withSetterVisibility(visibility);
                }
                case CREATOR: {
                    return this.withCreatorVisibility(visibility);
                }
                case FIELD: {
                    return this.withFieldVisibility(visibility);
                }
                case IS_GETTER: {
                    return this.withIsGetterVisibility(visibility);
                }
                case ALL: {
                    return this.with(visibility);
                }
                default: {
                    return this;
                }
            }
        }
        
        @Override
        public Std withGetterVisibility(JsonAutoDetect.Visibility getterMinLevel) {
            if (getterMinLevel == JsonAutoDetect.Visibility.DEFAULT) {
                getterMinLevel = Std.DEFAULT._getterMinLevel;
            }
            if (this._getterMinLevel == getterMinLevel) {
                return this;
            }
            return new Std(getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
        }
        
        @Override
        public Std withIsGetterVisibility(JsonAutoDetect.Visibility isGetterMinLevel) {
            if (isGetterMinLevel == JsonAutoDetect.Visibility.DEFAULT) {
                isGetterMinLevel = Std.DEFAULT._isGetterMinLevel;
            }
            if (this._isGetterMinLevel == isGetterMinLevel) {
                return this;
            }
            return new Std(this._getterMinLevel, isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
        }
        
        @Override
        public Std withSetterVisibility(JsonAutoDetect.Visibility setterMinLevel) {
            if (setterMinLevel == JsonAutoDetect.Visibility.DEFAULT) {
                setterMinLevel = Std.DEFAULT._setterMinLevel;
            }
            if (this._setterMinLevel == setterMinLevel) {
                return this;
            }
            return new Std(this._getterMinLevel, this._isGetterMinLevel, setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
        }
        
        @Override
        public Std withCreatorVisibility(JsonAutoDetect.Visibility creatorMinLevel) {
            if (creatorMinLevel == JsonAutoDetect.Visibility.DEFAULT) {
                creatorMinLevel = Std.DEFAULT._creatorMinLevel;
            }
            if (this._creatorMinLevel == creatorMinLevel) {
                return this;
            }
            return new Std(this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, creatorMinLevel, this._fieldMinLevel);
        }
        
        @Override
        public Std withFieldVisibility(JsonAutoDetect.Visibility fieldMinLevel) {
            if (fieldMinLevel == JsonAutoDetect.Visibility.DEFAULT) {
                fieldMinLevel = Std.DEFAULT._fieldMinLevel;
            }
            if (this._fieldMinLevel == fieldMinLevel) {
                return this;
            }
            return new Std(this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, fieldMinLevel);
        }
        
        @Override
        public boolean isCreatorVisible(final Member member) {
            return this._creatorMinLevel.isVisible(member);
        }
        
        @Override
        public boolean isCreatorVisible(final AnnotatedMember annotatedMember) {
            return this.isCreatorVisible(annotatedMember.getMember());
        }
        
        @Override
        public boolean isFieldVisible(final Field field) {
            return this._fieldMinLevel.isVisible(field);
        }
        
        @Override
        public boolean isFieldVisible(final AnnotatedField annotatedField) {
            return this.isFieldVisible(annotatedField.getAnnotated());
        }
        
        @Override
        public boolean isGetterVisible(final Method method) {
            return this._getterMinLevel.isVisible(method);
        }
        
        @Override
        public boolean isGetterVisible(final AnnotatedMethod annotatedMethod) {
            return this.isGetterVisible(annotatedMethod.getAnnotated());
        }
        
        @Override
        public boolean isIsGetterVisible(final Method method) {
            return this._isGetterMinLevel.isVisible(method);
        }
        
        @Override
        public boolean isIsGetterVisible(final AnnotatedMethod annotatedMethod) {
            return this.isIsGetterVisible(annotatedMethod.getAnnotated());
        }
        
        @Override
        public boolean isSetterVisible(final Method method) {
            return this._setterMinLevel.isVisible(method);
        }
        
        @Override
        public boolean isSetterVisible(final AnnotatedMethod annotatedMethod) {
            return this.isSetterVisible(annotatedMethod.getAnnotated());
        }
        
        @Override
        public String toString() {
            return String.format("[Visibility: getter=%s,isGetter=%s,setter=%s,creator=%s,field=%s]", this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
        }
        
        @Override
        public VisibilityChecker withFieldVisibility(final JsonAutoDetect.Visibility visibility) {
            return this.withFieldVisibility(visibility);
        }
        
        @Override
        public VisibilityChecker withCreatorVisibility(final JsonAutoDetect.Visibility visibility) {
            return this.withCreatorVisibility(visibility);
        }
        
        @Override
        public VisibilityChecker withSetterVisibility(final JsonAutoDetect.Visibility visibility) {
            return this.withSetterVisibility(visibility);
        }
        
        @Override
        public VisibilityChecker withIsGetterVisibility(final JsonAutoDetect.Visibility visibility) {
            return this.withIsGetterVisibility(visibility);
        }
        
        @Override
        public VisibilityChecker withGetterVisibility(final JsonAutoDetect.Visibility visibility) {
            return this.withGetterVisibility(visibility);
        }
        
        @Override
        public VisibilityChecker withVisibility(final PropertyAccessor propertyAccessor, final JsonAutoDetect.Visibility visibility) {
            return this.withVisibility(propertyAccessor, visibility);
        }
        
        @Override
        public VisibilityChecker with(final JsonAutoDetect.Visibility visibility) {
            return this.with(visibility);
        }
        
        @Override
        public VisibilityChecker withOverrides(final JsonAutoDetect.Value value) {
            return this.withOverrides(value);
        }
        
        @Override
        public VisibilityChecker with(final JsonAutoDetect jsonAutoDetect) {
            return this.with(jsonAutoDetect);
        }
        
        static {
            DEFAULT = new Std(JsonAutoDetect.Visibility.PUBLIC_ONLY, JsonAutoDetect.Visibility.PUBLIC_ONLY, JsonAutoDetect.Visibility.ANY, JsonAutoDetect.Visibility.ANY, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        }
    }
}
