package org.apache.calcite.cohort.sql;

import org.apache.calcite.sql.SqlCohortNode;
import org.apache.calcite.sql.SqlNode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A <code>CohortSelectNode</code> is a node of a parse tree which represents a 
 * cohort-style select statement.
 */
 
public class CohortSelectNode extends SqlCohortNode {
	
	private List<SqlNode> selectList;
	private String from;
	private String birthFrom;
	private List<SqlNode>	birthFilter;
	private List<SqlNode> ageFilter;
	private List<String> cohortBy;

	

	//~ Constructors ------------------------------------------------------------
	public CohortSelectNode(List<SqlNode> selectList,
							String from,
							String birthFrom,
							List<SqlNode> birthFilter,
							List<SqlNode> ageFilter,
							List<String> cohortBy) {
		this.selectList = selectList;
		this.from = from;
		this.birthFrom = birthFrom;
		this.birthFilter = birthFilter;
		this.ageFilter = ageFilter;
		this.cohortBy = cohortBy;	
	}
	
	public CohortSelectNode() {
		this(null, null, null, null, null, null);
	}
	
 
	//~ Methods
	
	public final List<SqlNode> getSelectList() {
		return selectList;
	}
	
	public void setSelectList(List<SqlNode> selectList) {
		this.selectList = selectList;
	}

	public final String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public final String getBirthFrom() {
		return birthFrom;
	}
	
	public void setBirthFrom(String birthFrom) {
		this.birthFrom = birthFrom;
	}
	
	public final List<SqlNode> getBirthFilter() {
		return birthFilter;
	}
	
	public void setBirthFilter(List<SqlNode> birthFilter) {
		this.birthFilter = birthFilter;
	}
	
	public final List<SqlNode> getAgeFilter() {
		return ageFilter;
	}
	
	public void setAgeFilter(List<SqlNode> ageFilter) {
		this.ageFilter = ageFilter;
	}
	
	public final List<String> getCohortBy() {
		return cohortBy;
	}
	
	public void setCohortBy(List<String> cohortBy) {
		this.cohortBy = cohortBy;
	}
			
	public boolean hasBirthFilter() {
		return birthFilter != null;
	}
	
	public boolean hasAgeFilter() {
		return ageFilter != null;
	}
	
/*	public String toString() {
		return getJson().toString(2);
	}
	
	public JSONObject getJson() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("dataSource", from);
		map.put("appKey", "todo");
		map.put("sinceEvent", birthFrom);
		if (birthFilter != null) {
			JSONArray filters = new JSONArray();
			for (SqlNode a:birthFilter) {
				filters.put(((CohortFilterNode)a).getJson());
			}
			map.put("birthFilters", filters);
		}
		if (ageFilter != null) {
			JSONArray filters = new JSONArray();
			for (SqlNode a:ageFilter) {
				filters.put(((CohortFilterNode)a).getJson());
			}
			map.put("ageFilters", filters);
		}
		map.put("cohortBy", new JSONArray(cohortBy));
		return new JSONObject(map);
	}	
	
*/
}



// End CohortSelectNode.java