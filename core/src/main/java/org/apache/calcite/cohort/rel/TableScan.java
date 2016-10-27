package org.apache.calcite.cohort.rel;


public class TableScan extends RelCohortNode {
	
	private String tableName;
	
	public TableScan(String tableName) {
		this.tableName = tableName;
	}
	
	@Override public RelCohortKind getKind() {
		return RelCohortKind.TABLE_SCAN;
	}
	
	
	public String getTableName() {
		return tableName;
	}
	
} // End TableScan.java