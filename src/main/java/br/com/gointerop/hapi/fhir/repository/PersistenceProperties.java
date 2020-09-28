package br.com.gointerop.hapi.fhir.repository;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import ca.uhn.fhir.context.ConfigurationException;

public class PersistenceProperties {
	static final String PERSISTENCE_PROPERTIES = "persistence.properties";
	static final String DATASOURCE_DRIVER = "datasource.driver";
	static final String DATASOURCE_MAX_POOL_SIZE = "datasource.max_pool_size";
	static final String DATASOURCE_PASSWORD = "datasource.password";
	static final String DATASOURCE_URL = "datasource.url";
	static final String DATASOURCE_USERNAME = "datasource.username";

	private static Properties ourProperties;

	private static String getProperty(String propertyName) {
		String env = "HAPI_" + propertyName.toUpperCase(Locale.US);
		env = env.replace(".", "_");
		env = env.replace("-", "_");

		String propertyValue = System.getenv(env);
		if (propertyValue != null) {
			return propertyValue;
		}

		Properties properties = PersistenceProperties.getProperties();
		if (properties != null) {
			propertyValue = properties.getProperty(propertyName);
		}

		return propertyValue;
	}

	private static Properties getProperties() {
		if (ourProperties == null) {
			Properties properties = loadProperties();
			PersistenceProperties.ourProperties = properties;
		}

		return ourProperties;
	}

	@NotNull
	private static Properties loadProperties() {
		// Load the configurable properties file
		Properties properties;
		try (InputStream in = PersistenceProperties.class.getClassLoader().getResourceAsStream(PERSISTENCE_PROPERTIES)) {
			properties = new Properties();
			properties.load(in);
		} catch (Exception e) {
			throw new ConfigurationException("Could not load HAPI properties", e);
		}

		Properties overrideProps = loadOverrideProperties();
		if (overrideProps != null) {
			properties.putAll(overrideProps);
		}
		properties.putAll(System.getenv().entrySet().stream()
				.filter(e -> e.getValue() != null && properties.containsKey(e.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		return properties;
	}

	private static Properties loadOverrideProperties() {
		String confFile = System.getProperty(PERSISTENCE_PROPERTIES);
		if (confFile != null) {
			try {
				Properties props = new Properties();
				props.load(new FileInputStream(confFile));
				return props;
			} catch (Exception e) {
				throw new ConfigurationException("Could not load HAPI properties file: " + confFile, e);
			}
		}

		return null;
	}

	private static String getProperty(String propertyName, String defaultValue) {
		String value = getProperty(propertyName);

		if (value != null && value.length() > 0) {
			return value;
		}

		return defaultValue;
	}

	public static String getDataSourceDriver() {
		return PersistenceProperties.getProperty(DATASOURCE_DRIVER, "org.postgresql.Driver");
	}

	public static Integer getDataSourceMaxPoolSize() {
		return PersistenceProperties.getIntegerProperty(DATASOURCE_MAX_POOL_SIZE, 10);
	}
	
	private static Integer getIntegerProperty(String propertyName, Integer defaultValue) {
	    String value = PersistenceProperties.getProperty(propertyName);

	    if (value == null || value.length() == 0) {
	      return defaultValue;
	    }

	    return Integer.parseInt(value);
	  }

	public static String getDataSourceUrl() {
		return PersistenceProperties.getProperty(DATASOURCE_URL,
				"jdbc:postgresql://localhost:5433/esus");
	}

	public static String getDataSourceUsername() {
		return PersistenceProperties.getProperty(DATASOURCE_USERNAME, "postgres");
	}

	public static String getDataSourcePassword() {
		return PersistenceProperties.getProperty(DATASOURCE_PASSWORD, "esus");
	}
}
