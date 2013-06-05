package com.rage.siapp.nlp.tools.network.graph.normalize;

import com.rage.siapp.nlp.tools.network.AttributeType;

public class DBAttribute 
{
	private AttributeType type ;
	private String value ;
	
	public DBAttribute(AttributeType type, String value)
	{
		setType(type) ;
		setValue(value) ;
	}
	
	@Override
	public String toString() 
	{
		return getValue() + " [" + getType() + "]" ;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBAttribute other = (DBAttribute) obj;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public AttributeType getType() {
		return type;
	}

	public void setType(AttributeType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
