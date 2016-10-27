package org.apache.calcite.cohort.rel;

public enum CohortOperatorKind {
	
	//eg. time between '2013-05-21' and '2013-05-27'
	BETWEEN,
	//eg. country in ['China', 'America', 'Australia']
	IN,
	//eg. country=Birth(country)
	EQ,
	//eg. AGE>10
	GT,
	//eg. AGE>=10
	GE,
	//eg. AGE<10
	LT,
	//eg. AGE<=10
	LE,
	//eg. AGE!=10
	NE;
}