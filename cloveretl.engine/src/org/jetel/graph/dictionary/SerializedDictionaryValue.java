package org.jetel.graph.dictionary;

import java.util.Properties;

/**
 * Dictionary value in a simple "serialized" form. This form contains the dictionary key, type
 * of the dictionary value, and the value represented as properties.
 * 
 * @author Jaroslav Urban (jaroslav.urban@javlinconsulting.cz)
 *         (c) Javlin Consulting (www.javlinconsulting.cz)
 *
 * @since Apr 14, 2008
 */
public class SerializedDictionaryValue {
	/** Separator of properties in the serialized string form. */
	public static final String PROPERTY_SEPARATOR = "#";
	private String key;
	private String type;
	private Properties properties;

	/**
	 * Allocates a new <tt>SerializedDictionaryValue</tt> object.
	 *
	 * @param key
	 * @param type
	 * @param properties
	 */
	public SerializedDictionaryValue(String key, String type, Properties properties) {
		super();
		this.key = key;
		this.type = type;
		this.properties = properties;
	}

	/**
	 * Parses the dictionary value from a string. The string must be in the form of
	 * key:dictionary_type:property1=value1#property2=value2#...
	 * 
	 * @param serializedValue
	 * @return parsed value.
	 * @throws IllegalArgumentException the the string is in invalid format.
	 */
	public static final SerializedDictionaryValue fromString(String serializedValue) throws IllegalArgumentException {
			String[] parts = serializedValue.split(":", 3);
			
			if (parts.length != 3) {
				throw new IllegalArgumentException("Invalid dictionary value: " + serializedValue);
			}
			
			String key = parts[0];
			String type = parts[1];
			
			Properties properties = new Properties();
			String props = parts[2];
			String[] pairs = props.split(PROPERTY_SEPARATOR);
			for (String pair : pairs) {
				if (pair.equals("")) {
					continue;
				}
				if (!pair.contains("=")) {
					throw new IllegalArgumentException("Invalid dictionary value: " + serializedValue);
				}
				
				int index = pair.indexOf("=");
				if (index == 0 || index == (pair.length() - 1)) {
					throw new IllegalArgumentException("Invalid dictionary value: " + serializedValue);
				}
				properties.setProperty(pair.substring(0, index), pair.substring(index + 1));
			}
			
			return new SerializedDictionaryValue(key, type, properties);
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return key + ":" + type + ":" + properties.toString();
	} 

	
	
}
