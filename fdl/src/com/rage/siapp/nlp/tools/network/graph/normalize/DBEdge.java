package com.rage.siapp.nlp.tools.network.graph.normalize;

import java.util.Vector;

import com.rage.siapp.nlp.tools.network.EdgeName;
import com.rage.siapp.nlp.tools.network.EdgeType;

public class DBEdge 
{
	private Long edgeID ;
	private EdgeName edgeName ;
	private EdgeType edgeType ;
	private String cuePhrase ;
	
	private Vector<DBAttribute> attributes ;
	
	public DBEdge(Long edgeID, EdgeName edgeName, EdgeType edgeType, String cuePhrase, Vector<DBAttribute> attributes)
	{
		setEdgeID(edgeID) ;
		setEdgeName(edgeName) ;
		setEdgeType(edgeType) ;
		setCuePhrase(cuePhrase) ;
		setAttributes(attributes) ;
	}
	
	@Override
	public String toString() 
	{
		return getEdgeName() + " [" + getEdgeID() + ", " + getEdgeType() + ", " + getCuePhrase() + ", " + getAttributes() + "]" ;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((cuePhrase == null) ? 0 : cuePhrase.hashCode());
		result = prime * result + ((edgeID == null) ? 0 : edgeID.hashCode());
		result = prime * result + ((edgeName == null) ? 0 : edgeName.hashCode());
		result = prime * result + ((edgeType == null) ? 0 : edgeType.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		DBEdge other = (DBEdge) obj;
		
		if (attributes == null) 
		{
			if (other.attributes != null)
				return false;
		}
		else if (!attributes.equals(other.attributes))
			return false;
		
		if (cuePhrase == null) 
		{
			if (other.cuePhrase != null)
				return false;
		}
		else if (!cuePhrase.equals(other.cuePhrase))
			return false;
		
		if (edgeID == null) 
		{
			if (other.edgeID != null)
				return false;
		}
		else if (!edgeID.equals(other.edgeID))
			return false;
		
		if (edgeName != other.edgeName)
			return false;
		
		if (edgeType != other.edgeType)
			return false;
		
		return true;
	}

	public Long getEdgeID() {
		return edgeID;
	}

	public void setEdgeID(Long edgeID) {
		this.edgeID = edgeID;
	}

	public EdgeName getEdgeName() {
		return edgeName;
	}

	public void setEdgeName(EdgeName edgeName) {
		this.edgeName = edgeName;
	}

	public EdgeType getEdgeType() {
		return edgeType;
	}

	public void setEdgeType(EdgeType edgeType) {
		this.edgeType = edgeType;
	}

	public String getCuePhrase() {
		return cuePhrase;
	}

	public void setCuePhrase(String cuePhrase) {
		this.cuePhrase = cuePhrase;
	}

	public Vector<DBAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Vector<DBAttribute> attributes) {
		this.attributes = attributes;
	}
}
