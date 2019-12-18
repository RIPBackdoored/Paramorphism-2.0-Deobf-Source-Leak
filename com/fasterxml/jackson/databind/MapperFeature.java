package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.cfg.ConfigFeature;

public final class MapperFeature extends Enum implements ConfigFeature {
   public static final MapperFeature USE_ANNOTATIONS = new MapperFeature("USE_ANNOTATIONS", 0, true);
   public static final MapperFeature USE_GETTERS_AS_SETTERS = new MapperFeature("USE_GETTERS_AS_SETTERS", 1, true);
   public static final MapperFeature PROPAGATE_TRANSIENT_MARKER = new MapperFeature("PROPAGATE_TRANSIENT_MARKER", 2, false);
   public static final MapperFeature AUTO_DETECT_CREATORS = new MapperFeature("AUTO_DETECT_CREATORS", 3, true);
   public static final MapperFeature AUTO_DETECT_FIELDS = new MapperFeature("AUTO_DETECT_FIELDS", 4, true);
   public static final MapperFeature AUTO_DETECT_GETTERS = new MapperFeature("AUTO_DETECT_GETTERS", 5, true);
   public static final MapperFeature AUTO_DETECT_IS_GETTERS = new MapperFeature("AUTO_DETECT_IS_GETTERS", 6, true);
   public static final MapperFeature AUTO_DETECT_SETTERS = new MapperFeature("AUTO_DETECT_SETTERS", 7, true);
   public static final MapperFeature REQUIRE_SETTERS_FOR_GETTERS = new MapperFeature("REQUIRE_SETTERS_FOR_GETTERS", 8, false);
   public static final MapperFeature ALLOW_FINAL_FIELDS_AS_MUTATORS = new MapperFeature("ALLOW_FINAL_FIELDS_AS_MUTATORS", 9, true);
   public static final MapperFeature INFER_PROPERTY_MUTATORS = new MapperFeature("INFER_PROPERTY_MUTATORS", 10, true);
   public static final MapperFeature INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES = new MapperFeature("INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES", 11, true);
   public static final MapperFeature CAN_OVERRIDE_ACCESS_MODIFIERS = new MapperFeature("CAN_OVERRIDE_ACCESS_MODIFIERS", 12, true);
   public static final MapperFeature OVERRIDE_PUBLIC_ACCESS_MODIFIERS = new MapperFeature("OVERRIDE_PUBLIC_ACCESS_MODIFIERS", 13, true);
   public static final MapperFeature USE_STATIC_TYPING = new MapperFeature("USE_STATIC_TYPING", 14, false);
   public static final MapperFeature USE_BASE_TYPE_AS_DEFAULT_IMPL = new MapperFeature("USE_BASE_TYPE_AS_DEFAULT_IMPL", 15, false);
   public static final MapperFeature DEFAULT_VIEW_INCLUSION = new MapperFeature("DEFAULT_VIEW_INCLUSION", 16, true);
   public static final MapperFeature SORT_PROPERTIES_ALPHABETICALLY = new MapperFeature("SORT_PROPERTIES_ALPHABETICALLY", 17, false);
   public static final MapperFeature ACCEPT_CASE_INSENSITIVE_PROPERTIES = new MapperFeature("ACCEPT_CASE_INSENSITIVE_PROPERTIES", 18, false);
   public static final MapperFeature ACCEPT_CASE_INSENSITIVE_ENUMS = new MapperFeature("ACCEPT_CASE_INSENSITIVE_ENUMS", 19, false);
   public static final MapperFeature USE_WRAPPER_NAME_AS_PROPERTY_NAME = new MapperFeature("USE_WRAPPER_NAME_AS_PROPERTY_NAME", 20, false);
   public static final MapperFeature USE_STD_BEAN_NAMING = new MapperFeature("USE_STD_BEAN_NAMING", 21, false);
   public static final MapperFeature ALLOW_EXPLICIT_PROPERTY_RENAMING = new MapperFeature("ALLOW_EXPLICIT_PROPERTY_RENAMING", 22, false);
   public static final MapperFeature ALLOW_COERCION_OF_SCALARS = new MapperFeature("ALLOW_COERCION_OF_SCALARS", 23, true);
   public static final MapperFeature IGNORE_DUPLICATE_MODULE_REGISTRATIONS = new MapperFeature("IGNORE_DUPLICATE_MODULE_REGISTRATIONS", 24, true);
   public static final MapperFeature IGNORE_MERGE_FOR_UNMERGEABLE = new MapperFeature("IGNORE_MERGE_FOR_UNMERGEABLE", 25, true);
   private final boolean _defaultState;
   private final int _mask;
   private static final MapperFeature[] $VALUES = new MapperFeature[]{USE_ANNOTATIONS, USE_GETTERS_AS_SETTERS, PROPAGATE_TRANSIENT_MARKER, AUTO_DETECT_CREATORS, AUTO_DETECT_FIELDS, AUTO_DETECT_GETTERS, AUTO_DETECT_IS_GETTERS, AUTO_DETECT_SETTERS, REQUIRE_SETTERS_FOR_GETTERS, ALLOW_FINAL_FIELDS_AS_MUTATORS, INFER_PROPERTY_MUTATORS, INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES, CAN_OVERRIDE_ACCESS_MODIFIERS, OVERRIDE_PUBLIC_ACCESS_MODIFIERS, USE_STATIC_TYPING, USE_BASE_TYPE_AS_DEFAULT_IMPL, DEFAULT_VIEW_INCLUSION, SORT_PROPERTIES_ALPHABETICALLY, ACCEPT_CASE_INSENSITIVE_PROPERTIES, ACCEPT_CASE_INSENSITIVE_ENUMS, USE_WRAPPER_NAME_AS_PROPERTY_NAME, USE_STD_BEAN_NAMING, ALLOW_EXPLICIT_PROPERTY_RENAMING, ALLOW_COERCION_OF_SCALARS, IGNORE_DUPLICATE_MODULE_REGISTRATIONS, IGNORE_MERGE_FOR_UNMERGEABLE};

   public static MapperFeature[] values() {
      return (MapperFeature[])$VALUES.clone();
   }

   public static MapperFeature valueOf(String var0) {
      return (MapperFeature)Enum.valueOf(MapperFeature.class, var0);
   }

   private MapperFeature(String var1, int var2, boolean var3) {
      super(var1, var2);
      this._defaultState = var3;
      this._mask = 1 << this.ordinal();
   }

   public boolean enabledByDefault() {
      return this._defaultState;
   }

   public int getMask() {
      return this._mask;
   }

   public boolean enabledIn(int var1) {
      return (var1 & this._mask) != 0;
   }
}
