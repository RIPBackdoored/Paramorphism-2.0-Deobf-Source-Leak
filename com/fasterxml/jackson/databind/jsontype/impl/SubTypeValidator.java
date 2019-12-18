package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SubTypeValidator {
   protected static final String PREFIX_SPRING = "org.springframework.";
   protected static final String PREFIX_C3P0 = "com.mchange.v2.c3p0.";
   protected static final Set DEFAULT_NO_DESER_CLASS_NAMES;
   protected Set _cfgIllegalClassNames;
   private static final SubTypeValidator instance;

   protected SubTypeValidator() {
      super();
      this._cfgIllegalClassNames = DEFAULT_NO_DESER_CLASS_NAMES;
   }

   public static SubTypeValidator instance() {
      return instance;
   }

   public void validateSubType(DeserializationContext var1, JavaType var2, BeanDescription var3) throws JsonMappingException {
      Class var4 = var2.getRawClass();
      String var5 = var4.getName();
      if (!this._cfgIllegalClassNames.contains(var5)) {
         if (var4.isInterface()) {
            return;
         }

         if (var5.startsWith("org.springframework.")) {
            Class var6 = var4;

            while(true) {
               if (var6 == null || var6 == Object.class) {
                  return;
               }

               String var7 = var6.getSimpleName();
               if ("AbstractPointcutAdvisor".equals(var7) || "AbstractApplicationContext".equals(var7)) {
                  break;
               }

               var6 = var6.getSuperclass();
            }
         } else if (!var5.startsWith("com.mchange.v2.c3p0.") || !var5.endsWith("DataSource")) {
            return;
         }
      }

      var1.reportBadTypeDefinition(var3, "Illegal type (%s) to deserialize: prevented for security reasons", var5);
   }

   static {
      HashSet var0 = new HashSet();
      var0.add("org.apache.commons.collections.functors.InvokerTransformer");
      var0.add("org.apache.commons.collections.functors.InstantiateTransformer");
      var0.add("org.apache.commons.collections4.functors.InvokerTransformer");
      var0.add("org.apache.commons.collections4.functors.InstantiateTransformer");
      var0.add("org.codehaus.groovy.runtime.ConvertedClosure");
      var0.add("org.codehaus.groovy.runtime.MethodClosure");
      var0.add("org.springframework.beans.factory.ObjectFactory");
      var0.add("com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl");
      var0.add("org.apache.xalan.xsltc.trax.TemplatesImpl");
      var0.add("com.sun.rowset.JdbcRowSetImpl");
      var0.add("java.util.logging.FileHandler");
      var0.add("java.rmi.server.UnicastRemoteObject");
      var0.add("org.springframework.beans.factory.config.PropertyPathFactoryBean");
      var0.add("org.apache.tomcat.dbcp.dbcp2.BasicDataSource");
      var0.add("com.sun.org.apache.bcel.internal.util.ClassLoader");
      var0.add("org.hibernate.jmx.StatisticsService");
      var0.add("org.apache.ibatis.datasource.jndi.JndiDataSourceFactory");
      var0.add("org.apache.ibatis.parsing.XPathParser");
      var0.add("jodd.db.connection.DataSourceConnectionProvider");
      var0.add("oracle.jdbc.connector.OracleManagedConnectionFactory");
      var0.add("oracle.jdbc.rowset.OracleJDBCRowSet");
      DEFAULT_NO_DESER_CLASS_NAMES = Collections.unmodifiableSet(var0);
      instance = new SubTypeValidator();
   }
}
