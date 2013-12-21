package org.pac.tapestry.oauth.models;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
	@author vladimir
 */

/**
 * @author vladimir
 *
 */
public class ApiFieldValue {

	private final Object value;
	
	public ApiFieldValue(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	
	public String getStringValue() {
		return this.getValue(String.class);
	}

	public long getLongValue() {
		return this.getValue(Long.class);
	}

	public int getIntValue() {
		return this.getValue(Integer.class);
	}

	public boolean getBooleanValue() {
		return this.getValue(Boolean.class);
	}

	public double getDoubleValue() {
		return this.getValue(Double.class);
	}

	public float getFloatValue() {
		return this.getValue(Float.class);
	}

	public List<?> getList() {
		return this.getValue(List.class);
	}

	public Set<?> getSet() {
		return this.getValue(Set.class);
	}

	public Date getDate() {
		return this.getValue(Date.class);
	}

	public URI getUri() {
		return this.getValue(URI.class);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getValue(Class<T> expectedType) {
		
		Object _object = value;

		if (_object == null) {
			return null;
		}

		if (expectedType.isAssignableFrom(_object.getClass())) {
			return (T) _object;
		}

		throw new IllegalArgumentException(String.format("invalid parameter type encountered for parameter %s - expected %s, but got %s", 
				_object, expectedType, _object.getClass()));
	}

	@Override
	public String toString() {
		return "ApiFieldValue [value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ApiFieldValue))
			return false;
		ApiFieldValue other = (ApiFieldValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
