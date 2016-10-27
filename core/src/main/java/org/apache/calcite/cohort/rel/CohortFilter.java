package org.apache.calcite.cohort.rel;

import java.util.List;

public class CohortFilter {
	
	private CohortOperatorKind type;
	
	private String dimension;
	
	private List<Object> values;
	
	public CohortFilter(CohortOperatorKind type, 
						String dimension,
						List<Object> values)
	{
		this.type = type;
		this.dimension = dimension;
		this.values = values;
	}
	
	public CohortOperatorKind getKind() {
		return type;
	}
	
	public String getDim() {
		return dimension;
	}
	
	public List<Object> getValues() {
		return values;
	}
	
	
} // End CohortFilter.java

	
	