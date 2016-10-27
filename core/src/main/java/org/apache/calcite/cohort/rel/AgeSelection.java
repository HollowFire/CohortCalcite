package org.apache.calcite.cohort.rel;

import java.util.List;


public class AgeSelection extends RelCohortNode {
	
	
	
	private RelCohortNode input;
	private String birthAction;
	private List<CohortFilter> ageFilters;
	
	public AgeSelection(RelCohortNode input,
						String birthAction,
						List<CohortFilter> ageFilters) 
	{
		this.input = input;
		this.birthAction = birthAction;
		this.ageFilters = ageFilters;
	}
	
	@Override public RelCohortKind getKind() {
		return RelCohortKind.AGE_SELECTION;
	}
	
	
	public RelCohortNode getInput() {
		return input;
	}
	
	public String getBirthAction() {
		return birthAction;
	}
	
	public List<CohortFilter> getAgeFilters() {
		return ageFilters;
	}
	
	
	
} // End AgeSelection.java