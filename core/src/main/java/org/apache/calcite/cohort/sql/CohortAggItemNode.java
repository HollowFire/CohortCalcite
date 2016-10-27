package org.apache.calcite.cohort.sql;

import org.apache.calcite.sql.SqlCohortNode;


public class CohortAggItemNode extends SqlCohortNode {
	private String type;
	//USERCOUNT, LONG_SUM, DOUBLE_SUM, MIN, MAX, AVG
	private String fieldName;	
	private String alias;
	

	public CohortAggItemNode(String type) {
		this.setType(type);
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getAlias() {
		return alias;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
	
	