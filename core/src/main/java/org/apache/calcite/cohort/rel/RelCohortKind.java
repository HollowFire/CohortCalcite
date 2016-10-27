package org.apache.calcite.cohort.rel;



/**
 * Enumerates the types of RelCohortNode.
 */
 
public enum RelCohortKind {
	
	TABLE_SCAN,
	
	BIRTH_SELECTION,
	
	AGE_SELECTION,
	
	COHORT_AGGREGATION;

} // End RelCohortKind.java
