package com.rage.siapp.nlp.tools.network.graph;

public class Pair<A,B> 
{
	private A aValue ;
	private B bValue ;
	
	public Pair(A a, B b)
	{
		setAValue(a) ;
		setBValue(b) ;
	}
	
	@Override
	public String toString() 
	{
		return "[" + getAValue() + ", " + getBValue() + "]" ;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aValue == null) ? 0 : aValue.hashCode());
		result = prime * result + ((bValue == null) ? 0 : bValue.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		
		if (aValue == null) 
		{
			if (other.aValue != null)
				return false;
		} else if (!aValue.equals(other.aValue))
			return false;
		if (bValue == null) {
			if (other.bValue != null)
				return false;
		} else if (!bValue.equals(other.bValue))
			return false;
		return true;
	}

	public A getAValue() {
		return aValue;
	}

	public void setAValue(A aValue) {
		this.aValue = aValue;
	}

	public B getBValue() {
		return bValue;
	}

	public void setBValue(B bValue) {
		this.bValue = bValue;
	}
}
