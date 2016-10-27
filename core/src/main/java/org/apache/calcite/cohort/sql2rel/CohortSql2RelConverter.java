package org.apache.calcite.cohort.sql2rel;

import org.apache.calcite.cohort.rel.*;
import org.apache.calcite.cohort.sql.*;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.sql.SqlNode;

import java.util.List;
import java.util.ArrayList;

public class CohortSql2RelConverter {
	
	private SqlNode cohortSelect;
	
	private RelNode root;
	private RelNode tableScan;
	private RelNode birthSelection;
	private RelNode ageSelection;
	private RelNode cohortAgg;
	
	
	public CohortSql2RelConverter(SqlNode q) {
		this.cohortSelect = q;
	}
	
	public void setInput(SqlNode q) {
		this.cohortSelect = q;
	}
	
	public SqlNode getInput() {
		return cohortSelect;
	}
	
	public RelNode convert() {
		try {
			tableScan = convertTableScan();
			birthSelection = convertBirthSelection();
			ageSelection = convertAgeSelection();
			cohortAgg = convertCohortAgg();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		root = cohortAgg;
		return root;
	}
	
	private RelNode convertTableScan() throws ConvertNodeException {
		if (cohortSelect == null) {
			throw new ConvertNodeException("Input SqlNode is null!");
		}
		String tableName = ((CohortSelectNode)cohortSelect).getFrom();
		return new TableScan(tableName);
	}
	
	private RelNode convertBirthSelection() 
					throws ConvertNodeException {
		if (cohortSelect == null) {
			throw new ConvertNodeException("Input SqlNode is null!");
		}
		String birthAction = ((CohortSelectNode)cohortSelect).getBirthFrom();
		List<SqlNode> birthFilter = ((CohortSelectNode)cohortSelect).getBirthFilter();
		List<CohortFilter> cohortFilters;
		if (birthFilter != null) {
			cohortFilters = new ArrayList<CohortFilter>();
			for (SqlNode e: birthFilter) {
				cohortFilters.add(convertFilter((CohortFilterNode)e));
			}
		} else {
			cohortFilters = null;
		}
		return new BirthSelection((RelCohortNode)tableScan, birthAction, cohortFilters);
	}
	
	private RelNode convertAgeSelection() 
					throws ConvertNodeException {
		if (cohortSelect == null) {
			throw new ConvertNodeException("Input SqlNode is null!");
		}
		String birthAction = ((CohortSelectNode)cohortSelect).getBirthFrom();
		List<SqlNode> ageFilter = ((CohortSelectNode)cohortSelect).getAgeFilter();
		List<CohortFilter> cohortFilters;
		if (ageFilter != null) {
			cohortFilters = new ArrayList<CohortFilter>();
			for (SqlNode e: ageFilter) {
				cohortFilters.add(convertFilter((CohortFilterNode)e));
			}
		} else {
			cohortFilters = null;
		}
		return new AgeSelection((RelCohortNode)birthSelection, birthAction, cohortFilters);
	}
	
	private RelNode convertCohortAgg() 
					throws ConvertNodeException {
		if (cohortSelect == null) {
			throw new ConvertNodeException("Input SqlNode is null!");
		}
		String birthAction = ((CohortSelectNode)cohortSelect).getBirthFrom();
		List<SqlNode> selectList = ((CohortSelectNode)cohortSelect).getSelectList();
		List<String> cohortAtt = new ArrayList<String>();
		List<AggFunc> aggFunc = new ArrayList<AggFunc>();
		for (SqlNode e: selectList) {
			if (e instanceof CohortAggItemNode) {
				AggFunc x = convertAggItem((CohortAggItemNode)e);
				if (x != null) {
					aggFunc.add(x);
				}
			} else if (e instanceof CohortIdentifierNode) {
				cohortAtt.add(((CohortIdentifierNode)e).getValue());
			} else {
				throw new ConvertNodeException("SelectList type error!");
			}
		}
		if (cohortAtt.size() == 0) cohortAtt = null;
		if (aggFunc.size() == 0) aggFunc = null;
		return new CohortAggregation((RelCohortNode)ageSelection, birthAction, 
										cohortAtt, aggFunc);
	}
	
	private AggFunc convertAggItem(CohortAggItemNode e) 
					throws ConvertNodeException {
		String type = e.getType();
		String fieldName = e.getFieldName();
		List<String> argList = null;
		if (fieldName != null) {
			argList = new ArrayList<String>();
			argList.add(fieldName);
		}
		String alias = e.getAlias();
		AggFuncKind kind = null;
		switch (type) {
			case "AGE":
			case "COHORTSIZE":
				return null;
			case "BIRTH":
				kind = AggFuncKind.BIRTH;
				break;
			case "USERCOUNT":
				kind = AggFuncKind.USERCOUNT;
				break;
			case "MAX":
				kind = AggFuncKind.MAX;
				break;
			case "MIN":
				kind = AggFuncKind.MIN;
				break;
			case "SUM":
				kind = AggFuncKind.SUM;
				break;
			case "AVG":
				kind = AggFuncKind.AVG;
				break;
			default:
				throw new ConvertNodeException("Aggregation type unknown");
		}
		return new AggFunc(kind, argList, alias);
	
	}
	
	
	private CohortFilter convertFilter(CohortFilterNode e) 
						throws ConvertNodeException {
		String type = e.getType();
		String dimension = e.getDim();
		List<String> values = e.getValues();
		boolean birthFlag = e.getBirthFlag();
		CohortOperatorKind kind = null;
		switch (type) {
			case "BETWEEN":
				kind = CohortOperatorKind.BETWEEN;
				break;
			case "IN":
				kind = CohortOperatorKind.IN;
				break;
			case "EQ":
				kind = CohortOperatorKind.EQ;
				break;
			case "GT":
				kind = CohortOperatorKind.GT;
				break;
			case "GE":
				kind = CohortOperatorKind.GE;
				break;
			case "LT":
				kind = CohortOperatorKind.LT;
				break;
			case "LE":
				kind = CohortOperatorKind.LE;
				break;
			case "NE":
				kind = CohortOperatorKind.NE;
				break;
			default:
				throw new ConvertNodeException("Filter type unknown");
		}
		if (birthFlag == true) {
			AggFunc func = new AggFunc(AggFuncKind.BIRTH, values);
			List<Object> lst = new ArrayList<Object>();
			lst.add(func);
			return new CohortFilter(kind, dimension, lst);
		} else {
			return new CohortFilter(kind, dimension, new ArrayList<Object>(values));
		}
	}
	
	public RelNode getRoot() {
		return root;
	}
} // End CohortSql2RelConverter.java	