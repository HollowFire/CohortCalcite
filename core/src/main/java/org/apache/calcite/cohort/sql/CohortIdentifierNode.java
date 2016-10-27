package org.apache.calcite.cohort.sql;

import org.apache.calcite.sql.SqlCohortNode;

public class CohortIdentifierNode extends SqlCohortNode {
	
	private String value;
	private String alias;
	
	public CohortIdentifierNode(String value, String alias) {
		this.value = value;
		this.alias = alias;
	}
	
	public CohortIdentifierNode(String value) {
		this(value, null);
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
}