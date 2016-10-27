package org.apache.calcite.cohort.rel;

import java.util.List;


public class AggFunc {
	
	
	private AggFuncKind type;
	
	private List<String> argList;
	
	private String alias;
	
	
	public AggFunc(AggFuncKind type,
				List<String> argList,
				String alias)
	{
		this.type = type;
		this.argList = argList;
		this.alias = alias;
	}
	
	public AggFunc(AggFuncKind type,
				List<String> argList)
	{
		this(type, argList, null);
	}
	
	public AggFuncKind getAggFuncKind() {
		return type;
	}
	
	public List<String> getArgList() {
		return argList;
	}
	
	public String getAlias() {
		return alias;
	}

} //End AggFunc.java	