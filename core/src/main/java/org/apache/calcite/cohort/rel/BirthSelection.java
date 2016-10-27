package org.apache.calcite.cohort.rel;


import java.util.List;

public class BirthSelection extends RelCohortNode {
	
	
	private RelCohortNode input;
	private String birthAction;
	private List<CohortFilter> birthFilters;
	
	public BirthSelection(RelCohortNode input, 
					String birthAction,
					List<CohortFilter> birthFilters) 
	{
		this.input = input;
		this.birthAction = birthAction;
		this.birthFilters = birthFilters;
	}
	
	@Override public RelCohortKind getKind() {
		return RelCohortKind.BIRTH_SELECTION;
	}
	
	
	public RelCohortNode getInput() {
		return input;
	}
	
	public String getBirthAction() {
		return birthAction;
	}
	
	public List<CohortFilter> getBirthFilters() {
		return birthFilters;
	}
	
	
} // End BirthSelection.java
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	