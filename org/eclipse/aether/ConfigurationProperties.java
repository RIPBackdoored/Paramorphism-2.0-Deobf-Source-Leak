package org.eclipse.aether;

public final class ConfigurationProperties {
   private static final String PREFIX_AETHER = "aether.";
   private static final String PREFIX_CONNECTOR = "aether.connector.";
   public static final String PREFIX_PRIORITY = "aether.priority.";
   public static final String IMPLICIT_PRIORITIES = "aether.priority.implicit";
   public static final boolean DEFAULT_IMPLICIT_PRIORITIES = false;
   public static final String INTERACTIVE = "aether.interactive";
   public static final boolean DEFAULT_INTERACTIVE = false;
   public static final String USER_AGENT = "aether.connector.userAgent";
   public static final String DEFAULT_USER_AGENT = "Aether";
   public static final String CONNECT_TIMEOUT = "aether.connector.connectTimeout";
   public static final int DEFAULT_CONNECT_TIMEOUT = 10000;
   public static final String REQUEST_TIMEOUT = "aether.connector.requestTimeout";
   public static final int DEFAULT_REQUEST_TIMEOUT = 1800000;
   public static final String HTTP_HEADERS = "aether.connector.http.headers";
   public static final String HTTP_CREDENTIAL_ENCODING = "aether.connector.http.credentialEncoding";
   public static final String DEFAULT_HTTP_CREDENTIAL_ENCODING = "ISO-8859-1";
   public static final String PERSISTED_CHECKSUMS = "aether.connector.persistedChecksums";
   public static final boolean DEFAULT_PERSISTED_CHECKSUMS = true;

   private ConfigurationProperties() {
      super();
   }
}
