package com.fasterxml.jackson.databind;

import java.io.*;
import com.fasterxml.jackson.databind.cfg.*;
import com.fasterxml.jackson.databind.introspect.*;

public class PropertyNamingStrategy implements Serializable
{
    public static final PropertyNamingStrategy SNAKE_CASE;
    public static final PropertyNamingStrategy UPPER_CAMEL_CASE;
    public static final PropertyNamingStrategy LOWER_CAMEL_CASE;
    public static final PropertyNamingStrategy LOWER_CASE;
    public static final PropertyNamingStrategy KEBAB_CASE;
    @Deprecated
    public static final PropertyNamingStrategy CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES;
    @Deprecated
    public static final PropertyNamingStrategy PASCAL_CASE_TO_CAMEL_CASE;
    
    public PropertyNamingStrategy() {
        super();
    }
    
    public String nameForField(final MapperConfig<?> mapperConfig, final AnnotatedField annotatedField, final String s) {
        return s;
    }
    
    public String nameForGetterMethod(final MapperConfig<?> mapperConfig, final AnnotatedMethod annotatedMethod, final String s) {
        return s;
    }
    
    public String nameForSetterMethod(final MapperConfig<?> mapperConfig, final AnnotatedMethod annotatedMethod, final String s) {
        return s;
    }
    
    public String nameForConstructorParameter(final MapperConfig<?> mapperConfig, final AnnotatedParameter annotatedParameter, final String s) {
        return s;
    }
    
    static {
        SNAKE_CASE = new SnakeCaseStrategy();
        UPPER_CAMEL_CASE = new UpperCamelCaseStrategy();
        LOWER_CAMEL_CASE = new PropertyNamingStrategy();
        LOWER_CASE = new LowerCaseStrategy();
        KEBAB_CASE = new KebabCaseStrategy();
        CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES = PropertyNamingStrategy.SNAKE_CASE;
        PASCAL_CASE_TO_CAMEL_CASE = PropertyNamingStrategy.UPPER_CAMEL_CASE;
    }
    
    public abstract static class PropertyNamingStrategyBase extends PropertyNamingStrategy
    {
        public PropertyNamingStrategyBase() {
            super();
        }
        
        @Override
        public String nameForField(final MapperConfig<?> mapperConfig, final AnnotatedField annotatedField, final String s) {
            return this.translate(s);
        }
        
        @Override
        public String nameForGetterMethod(final MapperConfig<?> mapperConfig, final AnnotatedMethod annotatedMethod, final String s) {
            return this.translate(s);
        }
        
        @Override
        public String nameForSetterMethod(final MapperConfig<?> mapperConfig, final AnnotatedMethod annotatedMethod, final String s) {
            return this.translate(s);
        }
        
        @Override
        public String nameForConstructorParameter(final MapperConfig<?> mapperConfig, final AnnotatedParameter annotatedParameter, final String s) {
            return this.translate(s);
        }
        
        public abstract String translate(final String p0);
    }
    
    public static class SnakeCaseStrategy extends PropertyNamingStrategyBase
    {
        public SnakeCaseStrategy() {
            super();
        }
        
        @Override
        public String translate(final String s) {
            if (s == null) {
                return s;
            }
            final int length = s.length();
            final StringBuilder sb = new StringBuilder(length * 2);
            int n = 0;
            int n2 = 0;
            for (int i = 0; i < length; ++i) {
                char c = s.charAt(i);
                if (i > 0 || c != '_') {
                    if (Character.isUpperCase(c)) {
                        if (n2 == 0 && n > 0 && sb.charAt(n - 1) != '_') {
                            sb.append('_');
                            ++n;
                        }
                        c = Character.toLowerCase(c);
                        n2 = 1;
                    }
                    else {
                        n2 = 0;
                    }
                    sb.append(c);
                    ++n;
                }
            }
            return (n > 0) ? sb.toString() : s;
        }
    }
    
    public static class UpperCamelCaseStrategy extends PropertyNamingStrategyBase
    {
        public UpperCamelCaseStrategy() {
            super();
        }
        
        @Override
        public String translate(final String s) {
            if (s == null || s.length() == 0) {
                return s;
            }
            final char char1 = s.charAt(0);
            final char upperCase = Character.toUpperCase(char1);
            if (char1 == upperCase) {
                return s;
            }
            final StringBuilder sb = new StringBuilder(s);
            sb.setCharAt(0, upperCase);
            return sb.toString();
        }
    }
    
    public static class LowerCaseStrategy extends PropertyNamingStrategyBase
    {
        public LowerCaseStrategy() {
            super();
        }
        
        @Override
        public String translate(final String s) {
            return s.toLowerCase();
        }
    }
    
    public static class KebabCaseStrategy extends PropertyNamingStrategyBase
    {
        public KebabCaseStrategy() {
            super();
        }
        
        @Override
        public String translate(final String s) {
            if (s == null) {
                return s;
            }
            final int length = s.length();
            if (length == 0) {
                return s;
            }
            final StringBuilder sb = new StringBuilder(length + (length >> 1));
            int n = 0;
            for (int i = 0; i < length; ++i) {
                final char char1 = s.charAt(i);
                final char lowerCase = Character.toLowerCase(char1);
                if (lowerCase == char1) {
                    if (n > 1) {
                        sb.insert(sb.length() - 1, '-');
                    }
                    n = 0;
                }
                else {
                    if (n == 0 && i > 0) {
                        sb.append('-');
                    }
                    ++n;
                }
                sb.append(lowerCase);
            }
            return sb.toString();
        }
    }
    
    @Deprecated
    public static class LowerCaseWithUnderscoresStrategy extends SnakeCaseStrategy
    {
        public LowerCaseWithUnderscoresStrategy() {
            super();
        }
    }
    
    @Deprecated
    public static class PascalCaseStrategy extends UpperCamelCaseStrategy
    {
        public PascalCaseStrategy() {
            super();
        }
    }
}
