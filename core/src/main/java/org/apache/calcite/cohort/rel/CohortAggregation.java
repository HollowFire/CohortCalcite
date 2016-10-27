package org.apache.calcite.cohort.rel;


import java.util.List;

public class CohortAggregation extends RelCohortNode {
	
	private RelCohortNode input;
	private String birthAction;
	private List<String> cohortAtt;
	private List<AggFunc> aggFunc;
	
	public CohortAggregation(RelCohortNode input,
							String birthAction,
							List<String> cohortAtt,
							List<AggFunc> aggFunc) 
	{
		this.input = input;
		this.birthAction = birthAction;
		this.cohortAtt = cohortAtt;
		this.aggFunc = aggFunc;
	}
	
	@Override public RelCohortKind getKind() {
		return RelCohortKind.COHORT_AGGREGATION;
	}
	
	public RelCohortNode getInput() {
		return input;
	}
	
	public String getBirthAction() {
		return birthAction;
	}
	
	public List<String> getCohortAtt() {
		return cohortAtt;
	}
	
	public List<AggFunc> getAggFunc() {
		return aggFunc;
	}

} // End CohortAggregation.java